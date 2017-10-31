package com.zensar.afnls.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.ValidateEmployee;
import com.zensar.afnls.exception.AssetTrackNotDeletedException;
import com.zensar.afnls.exception.AssetTrackNotUpdatedException;
import com.zensar.afnls.exception.ConnectionNotEstablishedException;
import com.zensar.afnls.exception.EmployeeNotUpdatedException;
import com.zensar.afnls.exception.SelectAtleastOneTrackException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.exception.freezeDateNotSetException;
import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;
import com.zensar.afnls.services.LoginServiceForAFMS;
import com.zensar.afnls.util.LDAP;
import com.zensar.afnls.util.LaptopUtility;
import com.zensar.afnls.util.MailSender;
import com.zensar.afnls.util.MailUitility;

@Controller
public class UserManagementController {

	@Autowired
	LoginServiceForAFMS loginservices;

	@Autowired
	AssetForecastingSystemServicesImpl services;

	@RequestMapping(value="/delegateAssets.html",method=RequestMethod.POST)
	public ModelAndView delegateAssets(HttpServletRequest request,RedirectAttributes ra){
		String values[]=request.getParameterValues("delegate");
		try {
			if(values==null) throw new SelectAtleastOneTrackException("Select at least one track");
			LDAP ldap=new LDAP();
			String projectManagerId=null;
			String projectManagerName=null;
			String programManagerName=null;
			String array[]=null;
			EmployeeBean bean=(EmployeeBean)request.getSession().getAttribute("employee");

			switch (bean.getDesignation()) {
			case "Program Manager":
				projectManagerId=request.getParameter("delegateTo");
				String managerid []=  projectManagerId.split("/",2);
				if(managerid.length==2)
				{
					projectManagerId =  managerid[0];
				}
				projectManagerName=projectManagerId;
				services.delegateProjectManager(projectManagerId,projectManagerId,values);
				break;
			case "Delivery Head":
				array=request.getParameter("delegateTo").split("/");
				System.out.println(request.getParameter("delegateTo"));
				projectManagerId=array[0];
				projectManagerName=projectManagerId;
				//programManagerName=ldap.getEmpNames(array[2]);
				services.delegateProgramManager(projectManagerId,projectManagerId,array[2],values);
				break;
			default:
				array=request.getParameter("delegateTo").split("/");
				System.out.println(request.getParameter("delegateTo"));
				projectManagerId=array[0];
				projectManagerName=projectManagerId;
				programManagerName=array[2];
				String DeliveryHeadName=array[3];
				services.delegateDeliveryhead(projectManagerId,projectManagerName,programManagerName,DeliveryHeadName,values);
				break;
			}
			request.getSession().setAttribute("employee", services.getUpdatedEmployeeBean(bean));
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html?key=delegateAssets"));
			String eName="";
			try{
				eName=ldap.getEmpNames(projectManagerName);
			}
			catch(ConnectionNotEstablishedException e){
				eName="";
			}
			ra.addFlashAttribute("success","Project track delegated to "+eName);
			return modelAndView;
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("delegateAssets","message",e.getMessage());
		} catch (EmployeeNotUpdatedException e) {
			return new ModelAndView("delegateAssets","message",e.getMessage());
		} catch (SelectAtleastOneTrackException e) {
			return new ModelAndView("delegateAssets","message",e.getMessage());
		}
	}

	@RequestMapping(value="/deleteAssets.html",method=RequestMethod.POST)
	public ModelAndView deleteAssets(HttpServletRequest request,RedirectAttributes ra){
		String values[]=request.getParameterValues("delete");
		try {
			if(values==null) throw new SelectAtleastOneTrackException("Select at least one track");
			EmployeeBean bean=(EmployeeBean)request.getSession().getAttribute("employee");
			for (String value : values) {
				services.deleteAssetTrack(Integer.parseInt(value));	
			}
			request.getSession().setAttribute("employee", services.getUpdatedEmployeeBean(bean));
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html?key=deleteAssets"));
			ra.addFlashAttribute("success","Project track deleted");
			return modelAndView;
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("deleteAssets","message",e.getMessage());
		} catch (EmployeeNotUpdatedException e) {
			return new ModelAndView("deleteAssets","message",e.getMessage());
		} catch (SelectAtleastOneTrackException e) {
			return new ModelAndView("deleteAssets","message",e.getMessage());
		} catch (NumberFormatException e) {
			return new ModelAndView("deleteAssets","message","Error trying to delete the project track");
		} catch (AssetTrackNotDeletedException e) {
			return new ModelAndView("deleteAssets","message",e.getMessage());
		}
	}

	@RequestMapping(value="/getDesigValues.html",method=RequestMethod.GET)
	public @ResponseBody String getDesigValues(HttpServletRequest request, HttpServletResponse res){
		try {
			String reqDesig=request.getParameter("desig");
			System.out.println("Designation is "+reqDesig);
			List<EmployeeBean> employeeBeans=null;
			EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
			switch (bean.getDesignation()) {
			case "Project Manager":	
				break;
			case "Program Manager":	
				employeeBeans=new ArrayList<EmployeeBean>();
				employeeBeans.add(bean);
				break;
			case "Delivery Head":
				if("Program Manager".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					employeeBeans.add(bean);
				}
				else if("Project Manager".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					for (EmployeeBean employeeBean : bean.getEmployeeBeans()) {
						employeeBeans.add(employeeBean);
					}
				}
				break;
			case "Cisco ODC Head":	
				if("Delivery Head".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					employeeBeans.add(bean);
				}
				else if("Program Manager".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					for (EmployeeBean employeeBean : bean.getEmployeeBeans()) {
						employeeBeans.add(employeeBean);
					}
				}
				else if("Project Manager".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					for (EmployeeBean employeeBean2 : bean.getEmployeeBeans()) {
						for (EmployeeBean employeeBean : employeeBean2.getEmployeeBeans()) {
							employeeBeans.add(employeeBean);
						}	
					}
				}
				break;
			case "PCO Team":
				for (EmployeeBean employeeBean : bean.getEmployeeBeans()) {
					if("Cisco ODC Head".equalsIgnoreCase(employeeBean.getDesignation()))
						bean=employeeBean;
				}
				if("Delivery Head".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					employeeBeans.add(bean);
				}
				else if("Program Manager".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					for (EmployeeBean employeeBean : bean.getEmployeeBeans()) {
						employeeBeans.add(employeeBean);
					}
				}
				else if("Project Manager".equalsIgnoreCase(reqDesig)){
					employeeBeans=new ArrayList<EmployeeBean>();
					for (EmployeeBean employeeBean2 : bean.getEmployeeBeans()) {
						for (EmployeeBean employeeBean : employeeBean2.getEmployeeBeans()) {
							employeeBeans.add(employeeBean);
						}	
					}
				}
				break;
			default:
				break;
			}
			if(employeeBeans!=null && employeeBeans.size()>0){
				JSONObject jsonObject=new JSONObject();
				StringBuilder employees=new StringBuilder("{\"employeeName\":");
				employees.append(employeeBeans.toString());
				employees.append("}");

				String emp="[";
				for(int i=0;i<employeeBeans.size();i++){
					EmployeeBean e=employeeBeans.get(i);
					emp+="{";
					emp+="\"employeeName\":"+"\""+e.getEmployeeName()+"\",";
					emp+="\"userId\":"+"\""+e.getUserId()+"\",";
					emp+="\"managerId\":"+"\""+e.getManagerId()+"\"";
					emp+="},";
				}
				emp=emp.substring(0,emp.lastIndexOf(","));
				emp+="]";
				jsonObject.put("employeeDesig",emp);
				return jsonObject.toJSONString();
			} 
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value="/upload.html",method=RequestMethod.POST)
	public ModelAndView uploadtrack(
			HttpServletRequest request,
			RedirectAttributes ra,
			final @RequestParam("uploadtrack") MultipartFile uploadtrack){

		List<String> batchquery;
		List<String> addemploee =  new ArrayList<String>();
		try {

			batchquery = 	this.getBatchquery(uploadtrack.getInputStream(),addemploee,MailSender.raiseNewHireLaptopRequest.getOdcHead().split("@")[0]);
			//batchquery = 	this.getBatchquery(uploadtrack.getInputStream(),addemploee,"sbaswant");
			services.addBatch(batchquery,addemploee);
			ra.addFlashAttribute("success", "Successfully uploaded");
			return new ModelAndView(new RedirectView("/AFMS/userManagement.html?key=uploadProjectTrack"),"message","Successfully uploaded");

		} catch (Throwable e) {
			e.printStackTrace();
			ra.addFlashAttribute("message", "Batch Failed , Please check input file");
			return new ModelAndView(new RedirectView("/AFMS/userManagement.html?key=uploadProjectTrack"),"message","Batch Failed , Please check input file");

		}






	}



	public  List<String> getBatchquery(InputStream is , List<String> addemployee,String ciscoODCHead) {
		List<String> batchquery = new ArrayList<String>();
		try {


			int i = 0;


			/* FileInputStream fis = null;

		 String ciscoODCHead="sbaswant".trim();

		             fis = new FileInputStream("C:\\Users\\ajnaik\\Desktop\\Copy of Q1FY16 Forecast - Project Wise.xlsx");*/




			Workbook     workbook = new XSSFWorkbook(is);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				int six = 0;
				String projectmanager = "";
				String projectManagerName="";
				String programManagerName="";
				String deliveryHeadName="";
				String fiscalyear = "";
				String pgm = "";
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				String temp="REPLACE  into AssetBean(projectName,currentHeadCount,"
						+ "growthCount,growthStatus,CiscoManagerName,CiscoManagerId,"
						+ "quarter,projectLocation,projectManager,programManager,"
						+ "deliveryHead,userId,fiscalYear,createdDate) "
						+ "values(";

				String projectmgr = "REPLACE  into employeeBean(employeeName,designation,userId,managerId,managerId2Up,status) values(";
				String pgmr = "REPLACE  into employeeBean(employeeName,designation,userId,managerId,managerId2Up,status) values(";
				String delvr = "REPLACE  into employeeBean(employeeName,designation,userId,managerId,managerId2Up,status) values(";
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						six++;
						if(six==6)
						{
							String temp1 =  cell.getStringCellValue().trim();
							fiscalyear = cell.getStringCellValue().trim();
							int index  = temp1.indexOf('Q');
							temp1 =temp1.substring(index+1,index+2);
							temp =temp + "'QTR" + temp1+"'";
							temp =temp+ " , ";
						}
						else if(six==8){
							projectManagerName=cell.getStringCellValue().trim();
							
							
						}
						else if(six==9)
						{
							projectmanager =  cell.getStringCellValue().trim();
							temp =temp +"'"+ projectmanager + "'";
							temp = temp + " , ";
							if(projectmanager!=null &&  projectmanager.equalsIgnoreCase("PM"))
							{
								break;
							}

							projectmgr =projectmgr+ "'"+ projectManagerName + "',1,'"+cell.getStringCellValue().trim() + "',";


						}
						else if(six==10){
							programManagerName=cell.getStringCellValue().trim();
							
							
						}
						else if(six==11)
						{
							pgm =  cell.getStringCellValue().trim();
							temp =temp +"'"+ pgm + "'";
							temp = temp + " , ";
							if(pgm!=null &&  pgm.equalsIgnoreCase("Pgm"))
							{
								break;
							}
							pgmr =pgmr+ "'"+ programManagerName + "',2,'"+cell.getStringCellValue().trim() + "',";
							projectmgr = projectmgr+"'"+cell.getStringCellValue().trim()+"',";
						}
						else if(six==12){
							deliveryHeadName=cell.getStringCellValue().trim();
							
							
						}
						else if(six==13)
						{
							temp =temp +"'"+ cell.getStringCellValue().trim() +"'";
							temp = temp + " , ";
							pgmr = pgmr+"'"+cell.getStringCellValue().trim()+"','"+ciscoODCHead+"','A')";
							projectmgr = projectmgr +"'"+cell.getStringCellValue().trim()+"','A')";
							if(cell.getStringCellValue()!=null &&  cell.getStringCellValue().trim().equalsIgnoreCase("DH"))
							{
								break;
							}
							delvr =delvr+ "'"+ deliveryHeadName + "',3,'"+cell.getStringCellValue().trim() + "','"+ciscoODCHead+"','"+ciscoODCHead+"','A')";
						}
						else{
							temp =temp +"'"+ cell.getStringCellValue().trim() +"'";
							temp = temp + " , ";
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						six++;
						if(six==3)
						{
							temp =temp + cell.getNumericCellValue();
							temp = temp + " , ";
							try{
								temp =temp +"'"+ checkgrowthordecline(Integer.valueOf((int) cell.getNumericCellValue())) +"'";
								temp = temp + " , ";
							}catch(Exception e)
							{
								temp =temp +"'',";
							}
						}else{
							temp =temp + cell.getNumericCellValue();
							temp = temp + " , ";
						}
						break;
					}

				}


				if(projectmanager!=null &&  !projectmanager.equalsIgnoreCase(""))
				{
					temp =  temp + "'" + projectmanager + "' ," ;

				}
				else{
					temp = temp + "'" + pgm + "' , ";
				}
				boolean flag =true;
				try{
					int index  = fiscalyear.indexOf("FY");
					String temp1 ="20"+fiscalyear.substring(index+2,index+4);
					temp = temp + temp1 +",curdate()"+  ");";

				}catch(StringIndexOutOfBoundsException e) {flag=false;}
				if(flag)
				{
					batchquery.add(temp);
					addemployee.add(projectmgr);
					addemployee.add(pgmr);
					addemployee.add(delvr);
				}
			}



		} catch (Throwable e) {
			e.printStackTrace();
		}


		return batchquery;
	}
	public static String checkgrowthordecline(int number)
	{
		try{
			if( number >= 0)
			{ return "growth"; }
			else 
			{ return "decline";}
		}catch(Exception e){}
		return "";
	}


	public  static String getManagerName(String empid){
		if(InitiliazeResourceAtServerStartup.empIdEpmname.get(empid)!=null){
			return InitiliazeResourceAtServerStartup.empIdEpmname.get(empid);
		}else{
			String managerName =null;
			try{
				LDAP ldap=new LDAP();

				managerName=ldap.getEmpNames(empid) ;
				if(null == managerName || (managerName!=null &&  managerName.contains("null")) )
					return "Invalid CEC ID";
			}catch(Exception e)
			{
				return empid;
			}

			return managerName;
		}
	}

	@RequestMapping(value="/reminderMail.html",method=RequestMethod.POST)
	public ModelAndView reminderMail(HttpServletRequest request,RedirectAttributes ra){
		String values[]=request.getParameterValues("empSub");
		if(values==null)
		{

			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message","Please select User Id");
			return modelAndView;
		}else{
			try {
				if(this.reminderMail(values,request)){
					ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html?key=quarterlyAssetUpdates"));
					ra.addFlashAttribute("message","Reminder Mail Sent To Users");
					return modelAndView;
				}
				else {
					ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html?key=quarterlyAssetUpdates"));
					ra.addFlashAttribute("message","Reminder Mail Not Sent To Users");
					return modelAndView;
				}
			} catch (freezeDateNotSetException e) {
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html?key=quarterlyAssetUpdates"));
				ra.addFlashAttribute("message",e.getMessage());
				return modelAndView;
			}
		}
	}
	public boolean reminderMail(String [] arg,final HttpServletRequest request) throws freezeDateNotSetException
	{
		EmployeeBean bean  =  (EmployeeBean) request.getSession().getAttribute("employee");
		final String freezdate =   (String) request.getSession().getAttribute("freezeDate");
		if(freezdate==null)	throw new freezeDateNotSetException("freeze date is not set");
		for(final String userid : arg)
		{
			Executor executor = Executors.newSingleThreadExecutor();
			executor.execute(new Runnable() {
				public void run() {
					EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
					String from = bean.getUserId();
					try {
						String subject = "Please Submit Project Track Before  " + LaptopUtility.dateConvert(freezdate);
						MailSender mailSender = new MailSender();
						String body = MailUitility
								.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
										+ "reminder_template_for_project_track.html");
						body=MailUitility.reminderTemplate(body,LaptopUtility.dateConvert(freezdate));		
						mailSender.remindermail(subject, body,
								bean.getUserId(),userid,null);
					} catch (DataAccessException | NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return true;
	}

	
}
