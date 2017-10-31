package com.zensar.afnls.util;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.daoservices.AssetForecastingDAOServicesImpl;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;

public class LaptopUtility {
	@Autowired
	private AssetForecastingDAOServicesImpl daoServicesImpl;
	
	public static String []  designatioarray =AFMSConstant.designatioarray;

	private LaptopUtility() {
	}

	public static void main(String[] args) {
		getdaterange();
	}

	public static String uniqueLaptopId(String ownername, int laptopid) {
		StringBuffer uniid = new StringBuffer(
				LaptopUtility.splitOwnerName(ownername));
		uniid.append("/");
		uniid.append(laptopid);
		return uniid.toString();
	}

	public static String[] getdaterange() {

		Date date = new Date();
		String[] daterange = new String[2];
		DateFormat dateTimeformatter = new SimpleDateFormat("yyyy-MM-dd");
		
		daterange[0] = dateTimeformatter.format(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DATE, -90);
		daterange[1] = dateTimeformatter.format(cal.getTime());

		return daterange;
	}

	public static String dateConvert(String D) {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
			Date date = null;
			if(D==null) return null;	
			try {
				date = format1.parse(D);
				System.out.println(date);
			} catch (ParseException e) {
				System.out.println("parse error"+date);
				e.printStackTrace();
			}
			String dateString = format2.format(date);
			dateString = dateString.replace("-", " ");
			System.out.println(dateString);
			return ((dateString));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return D;
	}

	public static String splitOwnerName(String ownername) {
		char s1 = ownername.charAt(0);
		char s2 = ownername.charAt(ownername.length() - 1);
		String combine = String.valueOf(s1 + s2);
		return combine;
	}

	public static String getEmployeeNamefromid(String ccid) {
		/*try{
			LDAP ldap=new LDAP();
			return ldap.getEmpNames(ccid) ;
			}catch(Exception e)
		{
				return "Invalid CEC ID"; 
		}*/
		return ccid;
	}
	

	public  EmployeeBean  Updatedlaptop(EmployeeBean employeeBean) throws DataAccessException, SQLException
	{	

		if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[0]))
		{
			employeeBean.setLaptopBeans(daoServicesImpl.getLaptopsPerEmployee(employeeBean.getUserId()));
			return employeeBean;

		}
		if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[1]))
		{
			employeeBean.setLaptopBeans(daoServicesImpl.getLaptopasPerDesg(employeeBean.getUserId(),2));
			return employeeBean;

		}
		if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[2]))
		{
			employeeBean.setLaptopBeans(daoServicesImpl.getLaptopasPerDesg(employeeBean.getUserId(),3));
			return employeeBean;

		}
		if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[3]))
		{
			employeeBean.setLaptopBeans(daoServicesImpl.getAllLaptops("I"));
			return employeeBean;

		}
		if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[4]))
		{
			employeeBean.setLaptopBeans(daoServicesImpl.getAllLaptops("A"));
			return employeeBean;

		}
		if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[5]))
		{
			//employeeBean =	getLoginDetailsforPCO(employeeBean);
			return employeeBean;
		}



		return employeeBean;
	}

	public static void updatedLaptopBean(HttpServletRequest request,
			EmployeeBean nonfinalBean,
			AssetForecastingSystemServicesImpl services)
			throws DataAccessException, SQLException {
		nonfinalBean = (EmployeeBean) request.getSession().getAttribute(
				"employee");
		/*LaptopUtility l = new LaptopUtility();
		nonfinalBean  =  Updatedlaptop(nonfinalBean);*/
		request.getSession().setAttribute("employee", nonfinalBean);
	}

}
