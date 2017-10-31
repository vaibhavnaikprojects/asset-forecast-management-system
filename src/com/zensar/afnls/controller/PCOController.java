package com.zensar.afnls.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.zensar.afnls.beans.AATBean;
import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.Quarters;
import com.zensar.afnls.beans.SystemFreezeDate;
import com.zensar.afnls.exception.EmployeeNotActivatedException;
import com.zensar.afnls.exception.EmployeeNotAddedException;
import com.zensar.afnls.exception.EmployeeNotDeactivatedException;
import com.zensar.afnls.exception.EmployeeNotFetchedException;
import com.zensar.afnls.exception.EmployeeNotUpdatedException;
import com.zensar.afnls.exception.FreezeDateAlreadyPresentException;
import com.zensar.afnls.exception.NoQuarterFoundException;
import com.zensar.afnls.exception.OutdatedDatedException;
import com.zensar.afnls.exception.QuarterNotFetchedException;
import com.zensar.afnls.exception.RemoveProjectManagerAssetsException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;
import com.zensar.afnls.util.AFMSConstant;
import com.zensar.afnls.util.LaptopUtility;
import com.zensar.afnls.util.MailSender;

@Controller
public class PCOController {
	@Autowired
	AssetForecastingSystemServicesImpl services;

	private EmployeeBean getUserDetailsFromRequest(HttpServletRequest request){
		EmployeeBean employeeBean=new EmployeeBean();
		employeeBean.setEmployeeName(request.getParameter("employeeName"));
		employeeBean.setDesignation(request.getParameter("designation"));
		employeeBean.setUserId(request.getParameter("userId"));
		employeeBean.setManagerId(request.getParameter("managerId"));
		employeeBean.setManagerId2Up(request.getParameter("managerId2Up"));
		return employeeBean;		
	}

	@RequestMapping(value="/addUser.html",method=RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request,RedirectAttributes ra){
		EmployeeBean employeeBean=getUserDetailsFromRequest(request);
		try {

			if((employeeBean.getUserId().equalsIgnoreCase(employeeBean.getManagerId())) &&  employeeBean.getDesignation().trim().equalsIgnoreCase(AFMSConstant.designatioarray[0]))
			{
				throw new Exception("User and Manager id can't be same");
			}
			if(((employeeBean.getUserId().equalsIgnoreCase(employeeBean.getManagerId())) &&(employeeBean.getManagerId2Up().equalsIgnoreCase(employeeBean.getManagerId())) )&&


					(employeeBean.getDesignation().trim().equalsIgnoreCase(AFMSConstant.designatioarray[0] )||employeeBean.getDesignation().trim().equalsIgnoreCase(AFMSConstant.designatioarray[1])))
			{
				throw new Exception("User and Manager id  or Manager and 2Up Manager Id can't be same");
			}
			if(employeeBean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[4]) || employeeBean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[5]))
			{
				if(employeeBean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[4])){
					if(services.getODCHeadUserId()!=null)	throw new Exception("Cisco ODC HEAD already present");
				}
				employeeBean.setManagerId("");
				employeeBean.setManagerId2Up("");
			}
			if(employeeBean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[2]))
			{
				employeeBean.setManagerId(MailSender.raiseNewHireLaptopRequest.getOdcHead().split("@")[0]);
				employeeBean.setManagerId2Up(MailSender.raiseNewHireLaptopRequest.getOdcHead().split("@")[0]);
			}
			if(employeeBean.getDesignation().equalsIgnoreCase("Cisco ODC Head")){
				if(services.checkODCHead(employeeBean))	throw new EmployeeNotAddedException("Cannot add ODC head until previous ODC Head is deactivated");
			}
			if(services.addEmployee(employeeBean)){
				request.getSession(false).setAttribute("employee",services.getUpdatedEmployeeBean((EmployeeBean)request.getSession(false).getAttribute("employee")));
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
				ra.addFlashAttribute("employeeBean",employeeBean);
				ra.addFlashAttribute("success","added");
				request.getSession().setAttribute("freezeDate",
						services.getFreezeDate((Integer)request.getSession().getAttribute("year"),(String)request.getSession().getAttribute("quarter")));
				return modelAndView;
			}
		} catch (ServicesNotAvailableException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (EmployeeNotUpdatedException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (EmployeeNotAddedException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (Exception e) {ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
		ra.addFlashAttribute("message",e.getMessage());
		return modelAndView;
		}
		return new ModelAndView("userManagementPage","message","User Not Added");		
	}

	@RequestMapping(value="/systemFreeze.html",method=RequestMethod.POST)
	public ModelAndView systemFreeze(HttpServletRequest request,RedirectAttributes ra){
		int year=Integer.parseInt(request.getParameter("fiscalYear"));
		int quarter=Integer.parseInt(request.getParameter("quarter"));
		String freezeDate=request.getParameter("freezeDate");
		try {
			if(services.freezeSystem(year,quarter,freezeDate)){
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"));
				ra.addFlashAttribute("success","System freeze from "+freezeDate);
				request.getSession().setAttribute("freezeDate",LaptopUtility.dateConvert(services.getFreezeDate(Integer.valueOf(request.getSession().getAttribute("year").toString()).intValue(),request.getSession().getAttribute("quarter").toString())));
				return modelAndView;
			}
		} catch (NoQuarterFoundException e) {
			return new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"),"message",e.getMessage());	
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"),"message",e.getMessage());
		} catch (FreezeDateAlreadyPresentException e) {
			return new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"),"message",e.getMessage());
		} catch (OutdatedDatedException e) {
			return new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"),"message",e.getMessage());
		}
		return new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"),"message","Something went wrong");	
	}

	@RequestMapping(value="/setQuarters.html",method=RequestMethod.POST)
	public ModelAndView setQuarter(HttpServletRequest request,RedirectAttributes ra){
		String year=request.getParameter("fiscalYear");
		String q1From=request.getParameter("quarter1from");
		String q1To=request.getParameter("quarter1To");
		String q2From=request.getParameter("quarter2from");
		String q2To=request.getParameter("quarter2To");
		String q3From=request.getParameter("quarter3from");
		String q3To=request.getParameter("quarter3To");
		String q4From=request.getParameter("quarter4from");
		String q4To=request.getParameter("quarter4To");
		try{
			if(services.setQuarterForYear(year,q1From,q1To,q2From,q2To,q3From,q3To,q4From,q4To)){
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainQuarters"));
				ra.addFlashAttribute("success","Quarters set for year "+year);
				return modelAndView;
			}
		}catch(Exception e){
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainQuarters"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		}
		return new ModelAndView("pcoToolsPage","message","Something went wrong");	
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getEmployeeDetailsFromId.html",method=RequestMethod.GET)
	public @ResponseBody String getEmployeeDetailsFromId(HttpServletRequest request){
		try {
			EmployeeBean bean=services.getEmployeeById(request.getParameter("userId"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("employeeName", bean.getEmployeeName());
			jsonObject.put("designation", bean.getDesignation());
			jsonObject.put("userId", bean.getUserId());
			jsonObject.put("managerId", bean.getManagerId());
			jsonObject.put("managerId2Up", bean.getManagerId2Up());
			jsonObject.put("status", bean.getStatus());
			jsonObject.put("CHC", bean.getCHC());
			jsonObject.put("EG", bean.getEG());
			jsonObject.put("ED", bean.getED());
			return jsonObject.toJSONString();
		} catch (ServicesNotAvailableException | EmployeeNotFetchedException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value="/updateUser.html",method=RequestMethod.POST)
	public ModelAndView updateUser(HttpServletRequest request,RedirectAttributes ra){
		EmployeeBean bean=getUserDetailsFromRequest(request);
		String checkDesig=request.getParameter("ogdesignation");
		try {
			if(checkDesig.equalsIgnoreCase("Project Manager") && (!bean.getDesignation().equalsIgnoreCase("Project Manager"))){
				if(services.checkAssetsForProjectManager(bean.getUserId())) throw new RemoveProjectManagerAssetsException("Cannot Promote Until Project Manager Project Tracks Are Migrated");
			}
			if((bean.getUserId().equalsIgnoreCase(bean.getManagerId())) &&  bean.getDesignation().trim().equalsIgnoreCase(AFMSConstant.designatioarray[0]))
			{
				throw new Exception("User and manager ID can't be same");
			}
			if(((bean.getUserId().equalsIgnoreCase(bean.getManagerId())) &&(bean.getManagerId2Up().equalsIgnoreCase(bean.getManagerId())) )&&


					(bean.getDesignation().trim().equalsIgnoreCase(AFMSConstant.designatioarray[0] )||bean.getDesignation().trim().equalsIgnoreCase(AFMSConstant.designatioarray[1])))
			{
				throw new Exception("User and manager ID  or manager and 2Up manager ID can't be same");
			}
			if(bean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[4]) || bean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[5]))
			{
				bean.setManagerId("");
				bean.setManagerId2Up("");
			}
			if(bean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[2]))
			{
				bean.setManagerId(MailSender.raiseNewHireLaptopRequest.getOdcHead().split("@")[0]);
				bean.setManagerId2Up(MailSender.raiseNewHireLaptopRequest.getOdcHead().split("@")[0]);
			}
			if(services.updateEmployeeDetails(bean)){
				request.getSession(false).setAttribute("employee",services.getUpdatedEmployeeBean((EmployeeBean)request.getSession(false).getAttribute("employee")));
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
				ra.addFlashAttribute("employeeBean",bean);
				ra.addFlashAttribute("success","updated");
				return modelAndView;
			}
		} catch (EmployeeNotUpdatedException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (ServicesNotAvailableException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (RemoveProjectManagerAssetsException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (Exception e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		}
		return new ModelAndView("userManagementPage");
	}

	@RequestMapping(value="/deactivateUser.html",method=RequestMethod.POST)
	public ModelAndView deactivateUser(HttpServletRequest request,RedirectAttributes ra){
		EmployeeBean bean=getUserDetailsFromRequest(request);
		String checkDesig=request.getParameter("ogdesignation");
		String CHC = request.getParameter("CHC");
		bean.setCHC(Integer.parseInt(CHC));

		try {

			/*if(checkDesig.equalsIgnoreCase("Project Manager")){
				if(services.checkAssetsForProjectManager(bean.getUserId())) throw new RemoveProjectManagerAssetsException("Cannot Deactivate Until Project Manager Assets Are Migrated");
			}
			if(services.deactivateEmoployee(bean.getUserId())){*/

			/*Start by amsalunk */
			if(bean.getCHC()>0){	
				/*if(services.checkAssetsForProjectManager(bean.getUserId()))*/ throw new RemoveProjectManagerAssetsException("Cannot Deactivate Until "+ checkDesig +" Project Tracks Are Migrated");
			}
			/*End by amsalunk */

			if(services.deactivateEmoployee(bean.getUserId())){
				request.getSession(false).setAttribute("employee",services.getUpdatedEmployeeBean((EmployeeBean)request.getSession(false).getAttribute("employee")));
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
				ra.addFlashAttribute("success",bean.getEmployeeName()+" deactivated");
				return modelAndView;
			}
		} catch (EmployeeNotUpdatedException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (ServicesNotAvailableException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (RemoveProjectManagerAssetsException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (EmployeeNotDeactivatedException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		}
		ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
		ra.addFlashAttribute("message","Employee already exist");
		return modelAndView;
	}

	@RequestMapping(value="/activateUser.html",method=RequestMethod.POST)
	public ModelAndView activateUser(HttpServletRequest request,RedirectAttributes ra){
		EmployeeBean bean=getUserDetailsFromRequest(request);
		try {
			if(services.activateEmoployee(bean.getUserId())){
				request.getSession(false).setAttribute("employee",services.getUpdatedEmployeeBean((EmployeeBean)request.getSession(false).getAttribute("employee")));
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
				ra.addFlashAttribute("success",bean.getEmployeeName()+" activated");
				return modelAndView;
			}
		} catch (EmployeeNotUpdatedException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (ServicesNotAvailableException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		} catch (EmployeeNotActivatedException e) {
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/userManagement.html"));
			ra.addFlashAttribute("message",e.getMessage());
			return modelAndView;
		}
		return new ModelAndView("userManagementPage");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getQuarterFromId.html",method = RequestMethod.GET)
	public @ResponseBody String getAssetDetailsFromId(HttpServletRequest request,HttpServletResponse response){
		try {
			Quarters quarter= services.getQuarterByQuarterId(Integer.parseInt(request.getParameter("quarterId")));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("startDate", quarter.getStartDate());
			jsonObject.put("endDate", quarter.getEndDate());
			jsonObject.put("quarter", quarter.getQuarter());
			jsonObject.put("year", quarter.getFiscalYear());
			return jsonObject.toJSONString();
		} catch (Exception e) {
			return null;
		} 
	}
	@RequestMapping(value="/updateQuarter.html",method=RequestMethod.POST)
	public ModelAndView updateQuarter(HttpServletRequest request,RedirectAttributes ra){
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		if(services.updateQuarter(startDate,endDate,request.getParameter("quarter"),request.getParameter("year"))){
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainQuarters"));
			ra.addFlashAttribute("success","System quarters updated for "+request.getParameter("quarter"));
			return modelAndView;
		}
		else{
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainQuarters"));
			ra.addFlashAttribute("message","System failed to update quarter for "+request.getParameter("quarter"));
			return modelAndView;
		}
			
	}
	
	@RequestMapping(value="/updateAATURL.html",method=RequestMethod.POST)
	public ModelAndView updateAATURL(HttpServletRequest request,RedirectAttributes ra){
		String urlName=request.getParameter("urlName");
		String url=request.getParameter("url");
		if(services.updateAATURL(new AATBean(urlName,url))){
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainAATURLS"));
			ra.addFlashAttribute("success","URL updated");
			return modelAndView;
		}
		else
			return new ModelAndView("maintainAATURLS");
	}
	
	@RequestMapping(value="/updateFreezeDate.html",method=RequestMethod.POST)
	public ModelAndView updateFreezeDate(HttpServletRequest request,RedirectAttributes ra){
		int freezeId=Integer.parseInt(request.getParameter("freezeId"));
		int year=Integer.parseInt(request.getParameter("year"));
		int quarter=Integer.parseInt(request.getParameter("quarter"));
		String freezeDate=request.getParameter("freezeDate");
		if(services.updateSystemFreezeDate(new SystemFreezeDate(freezeId, year, quarter, freezeDate))){
			try {
				String quarter1 = services.getCurrentQuarter();
				int year1 = services.getCurrentFiscalYear();
				request.getSession().setAttribute("freezeDate",LaptopUtility.dateConvert(services.getFreezeDate(year1, quarter1)));
			} catch (ServicesNotAvailableException | QuarterNotFetchedException e) {
			}
			ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"));
			ra.addFlashAttribute("success","Freeze date updated");
			return modelAndView;
		}
		ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/tools.html?key=maintainSystemFreezeDate"));
		ra.addFlashAttribute("message","Freeze date not updated");
		return modelAndView;
			
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getSystemFreezeFromId.html",method=RequestMethod.GET)
	public @ResponseBody String getSystemFreezeFromId(HttpServletRequest request,RedirectAttributes ra){
		int freezeId=Integer.parseInt(request.getParameter("freezeId"));
		SystemFreezeDate freezeDate=services.getSystemFreezeDate(freezeId);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("freezeId", freezeDate.getFreezeId());
		jsonObject.put("year", freezeDate.getYear());
		jsonObject.put("quarter", freezeDate.getQuarter());
		jsonObject.put("freezeDate", freezeDate.getFreezeDate());
		return jsonObject.toJSONString();
	}
	
	
	
	
	

}
