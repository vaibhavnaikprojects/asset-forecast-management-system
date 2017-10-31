package com.zensar.afnls.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.LaptopBean;
import com.zensar.afnls.beans.RaiseNewHireLaptopRequest;
import com.zensar.afnls.controller.LoginController;
import com.zensar.afnls.exception.ConnectionNotEstablishedException;

public class MailUitility {


	public static String getEmailBody(String requestName ) throws IOException
	{
		BufferedReader br = null;
		StringBuffer sCurrentLine=new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(requestName));
			String sCurrentLine1;
			while ((sCurrentLine1 = br.readLine()) != null) {
				sCurrentLine.append(sCurrentLine1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sCurrentLine.toString();
	}

	public static String convertLaptopStaticRequesttoRuntime(String body,LaptopBean lp,String approve,String approveComments,String reject,String moreinfo) throws IOException
	{
		body=body.replaceAll("CREATEDDATE", lp.getCreatedDate());
		body=body.replaceAll("OWNERNAME", lp.getOwnerName());
		body=body.replaceAll("PROJECTNAME", lp.getProjectName());
		body=body.replaceAll("REQUESTTYPE", lp.getRequestType());
		String inventoryType="";
		if(lp.getStock()!=null && "S".equalsIgnoreCase(lp.getStock()))	inventoryType="<tr><td style='font-weight: bold; line-height: 30px;'>Inventory : Stock</td></tr>";
		else{
			LDAP ldap=new LDAP();
			String name="";
			try {
				name = ldap.getEmpNames(lp.getFromemployeeId().trim());
			} catch (ConnectionNotEstablishedException e) {
			}
			inventoryType="<tr><td style='font-weight: bold; line-height: 30px;'>Inventory : From Associate -"+name+"("+lp.getFromemployeeId()+")</td></tr>";
		}
		body=body.replaceAll("INVENTORYTYPE", inventoryType);
		body=body.replaceAll("REASON", lp.getReason());
		body=body.replaceAll("APPROVELINK", approve);
		body=body.replaceAll("APPROVECOMMENTS", approveComments);
		body=body.replaceAll("REJECTINK", reject);
		body=body.replaceAll("JUSTIFICATIONLINK", moreinfo);
		body = body.replaceAll("Requestor_Value",lp.getEmployeeId());
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		return body;
	}
	
	public static String convertclosedLaptopStaticRequesttoRuntime(String body,LaptopBean lp,String fromuserid) throws IOException
	{
		body=body.replaceAll("REQUESTER_NAME", lp.getOwnerName());
		body=body.replaceAll("CASE_NUMBER", lp.getRemarksFromLA());
		body=body.replaceAll("ITO_NAME", fromuserid);
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		return body;
	}
	
	public static String convertquartlysubmittedrequest(String body,EmployeeBean lp,String quarter,String year,String pgmname) throws IOException
	{
		body=body.replaceAll("PGM_Name", pgmname);
		body=body.replaceAll("PM_Name", lp.getEmployeeName());
		body=body.replaceAll("Quarter_Name", quarter);
		body=body.replaceAll("Year_Name", year);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		body = body.replaceAll("AFMSLink",LoginController.domain);
		return body;
	}
	
	public static String PGMUpdatedProjectTracks(String body,EmployeeBean lp,EmployeeBean bean,String quarter,String year) throws IOException
	{
		body=body.replaceAll("PM_Name", bean.getEmployeeName());
		body=body.replaceAll("PGM_Name", lp.getEmployeeName());
		body=body.replaceAll("Quarter_Name", quarter);
		body=body.replaceAll("Year_Name", year);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		body = body.replaceAll("AFMSLink",LoginController.domain);
		return body;
	}
	
	/*public static String convertquartlysubmittedrequest(String body,EmployeeBean lp,String quarter,String year,String pgmname) throws IOException
	{
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		body = body.replaceAll("AFMSLink",LoginController.domain);
		System.out.println(LoginController.domain);
		return body;
	}
	*/
	
	public static String ack_mail(String body,LaptopBean lp,String uniquelaptopid) throws IOException
	{
		body=body.replaceAll("OWNER_NAMe", lp.getOwnerName());
		body=body.replaceAll("REQUEST_ID", uniquelaptopid);
		body=body.replaceAll("Project_Name", lp.getProjectName());
		
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		return body;
	}
	
	
	public static String commentsBody(String body,LaptopBean lp,String comments) throws IOException
	{
		body=body.replaceAll("CREATEDDATE", lp.getCreatedDate());
		body=body.replaceAll("OWNERNAME", lp.getOwnerName());
		body=body.replaceAll("PROJECTNAME", lp.getProjectName());
		body=body.replaceAll("REQUESTTYPE", lp.getRequestType());
		body=body.replaceAll("REASON", lp.getReason());
		body=body.replaceAll("COMMENTS", comments);
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		System.out.println(LoginController.domain);
		return body;
	}
	
	

	public static RaiseNewHireLaptopRequest readpropfile()
	{
		InputStream in =MailUitility.class.getResourceAsStream("/com/zensar/afnls/properties/mailerList.properties");
		Properties properties = new Properties();
		RaiseNewHireLaptopRequest raise = new RaiseNewHireLaptopRequest();
		try {
			properties.load(in);

			raise.setCcList(properties.getProperty("raiseNewHireLaptopRequest.ccList"));
			raise.setFromList(properties.getProperty("raiseNewHireLaptopRequest.FromList"));
			raise.setToList(properties.getProperty("raiseNewHireLaptopRequest.ToList"));
			raise.setOdcHead(properties.getProperty("odc.head.email"));
			raise.setAmitemail(properties.getProperty("amit.email"));
			raise.setODCName(properties.getProperty("ODCName"));
			raise.setAck_mail_id(properties.getProperty("ack_mail_id"));
			raise.setFujiURL(properties.getProperty("fujiURL"));
			raise.setRockies(properties.getProperty("rockiesurl"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return raise;
	}

	public static String getEmailsbydomain(String to,String from ) throws IOException
	{
		return null;
	}

	public static String feedbackTemplate(String body, EmployeeBean bean,
			String app) {
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		body = body.replaceAll("PGM_Name", bean.getEmployeeName());
		body = body.replaceAll("USER_FEEDBACK", app);
		return body;
	}
	
	public static String reminderTemplate(String body,String freezeDate) {
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		body = body.replaceAll("FREEEZEEEE", freezeDate);
		return body;
	}
	
	public static String ApprovedAndRejectMailTemplate(String body,LaptopBean bean) {
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		body = body.replaceAll("OWNERNAME", bean.getOwnerName());
		return body;
	}
	
	public static String editLaptopReasonStaticRequesttoRuntime(String body,LaptopBean lp,String approve,String approveComments,String reject,String moreinfo,String oldReason) throws IOException
	{
		body=body.replaceAll("CREATEDDATE", lp.getCreatedDate());
		body=body.replaceAll("OWNERNAME", lp.getOwnerName());
		body=body.replaceAll("PROJECTNAME", lp.getProjectName());
		body=body.replaceAll("REQUESTTYPE", lp.getRequestType());
		body=body.replaceAll("OLDREASON", oldReason);
		body=body.replaceAll("REASON", lp.getReason());
		body=body.replaceAll("APPROVELINK", approve);
		body=body.replaceAll("APPROVECOMMENTS", approveComments);
		body=body.replaceAll("REJECTINK", reject);
		body=body.replaceAll("JUSTIFICATIONLINK", moreinfo);
		body = body.replaceAll("Requestor_Value",lp.getEmployeeId());
		body = body.replaceAll("AFMSLink",LoginController.domain);
		body = body.replaceAll("AFMS_LINk", LoginController.domain);
		return body;
	}
}
