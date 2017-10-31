package com.zensar.afnls.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.cisco.iag.ldap.DirectoryService;
import com.zensar.afnls.exception.ConnectionNotEstablishedException;


public class LDAP {

	public boolean validateUser(String username ,String password){
		Hashtable env = new Hashtable(5, 0.75f);
		boolean authenticateflag = false;
		String dn = "uid=" + username + "," + LDAPEnvironment.MY_SEARCHBASE;
		//env.put(Context.SECURITY_PRINCIPAL, dn);
		//env.put(Context.SECURITY_CREDENTIALS, password);
		//"pv2.gen@cisco.com"
		env.put(Context.INITIAL_CONTEXT_FACTORY, LDAPEnvironment.INITCTX);
		env.put(Context.PROVIDER_URL, LDAPEnvironment.MY_SERVICE);
		env.put(Context.REFERRAL, "follow");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, username+"@cisco.com");
		env.put(Context.SECURITY_CREDENTIALS, password);
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			authenticateflag = true;
		} 
		catch (AuthenticationException authEx) {
			authenticateflag = false;
		}
		catch (NamingException e) {
			authenticateflag = false;
		}
		return authenticateflag;
	}



	public String getEmpNames(DirContext ctx, String filter)
			throws NamingException {
		String[] name = new String[2];
		try {
			/* specify search constraints to search subtree */
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			/* search for all entries with given uid*/
			NamingEnumeration results = ctx.search(LDAPEnvironment.MY_SEARCHBASE,filter, constraints);
			/* for each entry print out name + all attrs and values */
			while (results != null && results.hasMore()) {
				SearchResult si = (SearchResult) results.next();
				Attributes attrs = si.getAttributes();
				if (attrs == null) {
					System.out.println("No attributes");
				} else {
					/* Set the specified attribute*/
					String retAttr1 = new String("givenName");
					String retAttr2 = new String("sn");
					Attribute attr1 = (Attribute) attrs.get(retAttr1);
					Attribute attr2 = (Attribute) attrs.get(retAttr2);
					name[0] = (String) attr1.get();
					name[1] = (String) attr2.get();
				}
			}
		} catch (NamingException e) {
			System.out.println("Exception occured in getEmpNames " +e.getMessage());
			throw e;
		}
		return name[0]+" "+name[1];
	}
	public String getEmpNames(String empId) throws ConnectionNotEstablishedException{
		try {
			return getEmpNames(getContext(), "(&(cn=" + empId + ")(objectclass=user)(objectCategory=person))");
		} catch (NamingException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public List<String> getReporting(String empId) {
		List<String> reportingStructure=new ArrayList<String>();
		String sEmployeeNumber = new String("");
		String sCiscoUserId = empId;
		try {
			reportingStructure.add(empId);
			while (!sCiscoUserId.equals("chambers")) {
				sEmployeeNumber = getManagerEmployeeNumber(getContext(), "(uid=" + sCiscoUserId + ")");
				sCiscoUserId = getCiscoUserId(new Integer(sEmployeeNumber).intValue());
				reportingStructure.add(sCiscoUserId);
			}
		} catch (NamingException eX) {

		} catch (Exception tEx) {

		}
		return reportingStructure;
	}
	public String getManagerEmployeeNumber(DirContext ctx, String filter){
		String sManagerEmployeeNumber = new String("");
		try {
			/* specify search constraints to search subtree */
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			/* search for all entries with given uid*/
			NamingEnumeration results
			= ctx.search(LDAPEnvironment.MY_SEARCHBASE, filter, constraints);
			/* for each entry print out name + all attrs and values */
			while (results != null && results.hasMore()) {
				SearchResult si = (SearchResult) results.next();
				Attributes attrs = si.getAttributes();
				if (attrs == null) {
					System.out.println("No attributes");
				} else {
					/* Set the specified attribute*/
					String retAttr = new String("manageruid");
					Attribute attr = (Attribute) attrs.get(retAttr);
					if (attr != null) {
						String attrId = attr.getID();
						sManagerEmployeeNumber = (String) attr.get();
					}
				}
			}
		} catch (NamingException e) {
		}
		return sManagerEmployeeNumber;
	}
	public String getCiscoUserId(int employeeNumber) throws NamingException {
		String employeeId = null;
		String ldapURL = "ldap.cisco.com"; //Cisco Directory URL
		//String base = "cn=users,dc=cisco,dc=com"; //Employee context
		DirContext ldapConnection = null;
		NamingEnumeration entries = null;
		try {
			DirectoryService directoryService = new DirectoryService();
			ldapConnection = directoryService.connect(ldapURL);
			String base = "ou=active, ou=employees, ou=people, o=cisco.com"; //Search done on active employee only
			entries = DirectoryService.getEntry(ldapConnection, base, "(employeenumber=" + employeeNumber + ")"); //Research on Cisco User Id
			//entries = directoryService.getEntry(ldapConnection, base, "(uid=ncoulet)"); //Research on Cisco User Id
			while (entries.hasMore()) {
				SearchResult employee = (SearchResult) entries.next();
				employeeId = directoryService.getSingleValAttribute(employee, "uid");
			}
			return employeeId;
		} finally {
			try {
				if (entries != null) {
					entries.close();
				}
			} finally {
				if (ldapConnection != null) {
					ldapConnection.close();
				}
			}
		}
	}
	private DirContext getContext() throws ConnectionNotEstablishedException {
		Hashtable env = new Hashtable(5, 0.75f);
		env.put(Context.INITIAL_CONTEXT_FACTORY, LDAPEnvironment.INITCTX);
		/* Specify host and port to use for directory service */
		env.put(Context.PROVIDER_URL, LDAPEnvironment.MY_SERVICE);
		/* Set the referral property to "follow" referrals automatically */
		env.put(Context.REFERRAL, "follow");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, "pv2.gen@cisco.com");
	    env.put(Context.SECURITY_CREDENTIALS, "First@123");
		DirContext ctx = null;
		try {
			/* get a handle to an Initial DirContext */
			ctx = new InitialDirContext(env);

		} catch (NamingException e) {
			throw new ConnectionNotEstablishedException("No connection found");
		}
		return ctx;
	}
}
