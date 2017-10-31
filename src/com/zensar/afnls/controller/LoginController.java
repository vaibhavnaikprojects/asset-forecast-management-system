package com.zensar.afnls.controller;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.net.HttpHeaders;
import com.zensar.afnls.beans.AATBean;
import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.LaptopBean;
import com.zensar.afnls.beans.Quarters;
import com.zensar.afnls.beans.SystemFreezeDate;
import com.zensar.afnls.beans.ValidateEmployee;
import com.zensar.afnls.exception.AssetTrackNotFetchedException;
import com.zensar.afnls.exception.EmployeeNotActivatedException;
import com.zensar.afnls.exception.QuarterNotFetchedException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;
import com.zensar.afnls.services.LoginServiceForAFMS;
import com.zensar.afnls.util.AFMSConstant;
import com.zensar.afnls.util.LaptopUtility;
import com.zensar.afnls.util.MailSender;
import com.zensar.afnls.util.MailUitility;

@Controller
public class LoginController {
	public static String domain;

	static boolean flag = true;
	@Autowired
	AssetForecastingSystemServicesImpl services;
	@Autowired
	LoginServiceForAFMS loginservices;

	@RequestMapping(value = "/login.html")
	public ModelAndView loginCheck(HttpServletRequest request) throws AssetTrackNotFetchedException{
		try {
			domain = "http://" + InetAddress.getLocalHost().getHostAddress() + ":"
					+ request.getServerPort()
					+ request.getServletContext().getContextPath() + "/";
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		EmployeeBean bean = new EmployeeBean();
		bean.setUserId(request.getParameter("cecid"));
		bean.setPassword(request.getParameter("password"));

		try {
			if(request.getSession().getAttribute("employee")==null){
				bean = loginservices.getEmployeeBean(bean);
				LoginServiceForAFMS ad= new LoginServiceForAFMS();
				List<LaptopBean> pending =null;
				List<LaptopBean> completed = null;
				List<LaptopBean> moreinfo =null;
				List<LaptopBean> approved =null;

				if(bean.getDesignation().equalsIgnoreCase("LPT"));
				{
					try{

						pending = services.getlaptopfiltervalue("I");
						completed = services.getlaptopfiltervalue("C");
						moreinfo = services.getlaptopfiltervalue("M");
						approved = services.getlaptopfiltervalue("A");
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}

				if (bean != null) {
					request.getSession().invalidate();
					request.getSession(true);
					String quarter = services.getCurrentQuarter();
					int year = services.getCurrentFiscalYear();
					request.getSession().setAttribute("employee", bean);
					request.getSession().setAttribute("quarter", quarter);
					request.getSession().setAttribute("year", year);
					request.getSession().setAttribute("freezeDate",LaptopUtility.dateConvert(services.getFreezeDate(year, quarter)));
					request.getSession().setAttribute("pending", pending.size());
					request.getSession().setAttribute("completed", completed.size());
					request.getSession().setAttribute("moreinfo", moreinfo.size());
					request.getSession().setAttribute("approved", approved.size());
					request.getSession().setAttribute("total", pending.size()+completed.size()+moreinfo.size()+approved.size());
					if(bean.getDesignation().trim().equalsIgnoreCase("Program Manager")){
						try{
							boolean validatefalg=true;
							boolean pmvalidatefalg=true;
							HashMap<String,ValidateEmployee>  validatormap = new HashMap<String,ValidateEmployee>();
							List<ValidateEmployee> validatedEmployees = services.getvalidatedStatusforPGM(bean, year, quarter);
							if(validatedEmployees.isEmpty())
							{
								validatefalg=false;
							}
							Iterator itr = validatedEmployees.iterator();


							int counter=0;
							while(itr.hasNext())
							{
								ValidateEmployee validateemp = (ValidateEmployee)itr.next();
								validatormap.put(validateemp.getUserId(), validateemp);
								if((validateemp.getPgm_status()==null || validateemp.getPgm_status().equalsIgnoreCase("N") ))
								{

									validatefalg =false;	
								}


								if((validateemp.getValidator()==null || validateemp.getValidator().equalsIgnoreCase("invalidated") ))
									pmvalidatefalg = false;

							}
							request.getSession().setAttribute("employeeSubmission", validatormap);
							request.getSession().setAttribute("validatefalg", validatefalg);
							request.getSession().setAttribute("pmvalidatefalg", pmvalidatefalg);
						}
						catch (Exception e) {
						}
					}
					else if(bean.getDesignation().trim().equalsIgnoreCase("Project Manager"))
					{
						String assetValidation=services.getQuarterlyDetails(bean,(Integer)request.getSession().getAttribute("year"),(String)request.getSession().getAttribute("quarter"));
						request.getSession().setAttribute("assetValidation", assetValidation);
					}
				}
			}else if(request.getSession().getAttribute("employee")!=null)
			{
				return new ModelAndView(new RedirectView("/AFMS/asset.html"));
			}
		} catch (ServicesNotAvailableException e) {
			e.printStackTrace();
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (QuarterNotFetchedException e) {
			e.printStackTrace();
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch(EmployeeNotActivatedException e){
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message", e.getMessage());
		}
		catch(EmptyResultDataAccessException er)
		{
			er.printStackTrace();
			return new ModelAndView("../indexPage", "message", "You are not a Authorized Person , Please contact PCO team for further communication");
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message", "Please Try Again Later");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message", "Please Try Again Later");
		}
		catch(Exception e11){
			e11.printStackTrace();
			return new ModelAndView("../indexPage", "message", e11.getMessage());
		}
		return new ModelAndView("homePage", "employee", bean);
	}

	@RequestMapping(value = "/about.html", method = RequestMethod.GET)
	public ModelAndView aboutPage(HttpServletRequest request) {
		try {
			/*request.getSession().setAttribute(
					"employee",
					loginservices.getUpdatedEmployeeBean((EmployeeBean) request
							.getSession().getAttribute("employee"), request
							.getServletPath()));*/
		} catch (Exception e) {
			return new ModelAndView("../indexPage", "message",
					"Please Login Again");
		}
		return new ModelAndView("aboutPage");
	}

	@RequestMapping(value = "/help.html", method = RequestMethod.GET)
	public ModelAndView helpPage(HttpServletRequest request) {
		try {
			/*	request.getSession().setAttribute(
					"employee",
					loginservices.getUpdatedEmployeeBean((EmployeeBean) request
							.getSession().getAttribute("employee"), request
							.getServletPath()));*/
		} catch (Exception e) {
			return new ModelAndView("../indexPage", "message",
					"Please Login Again");
		}
		return new ModelAndView("helpPage");
	}

	@RequestMapping(value = "/asset.html", method = RequestMethod.GET)
	public ModelAndView homePage(HttpServletRequest request) {
		try {
			EmployeeBean bean = (EmployeeBean) request.getSession()
					.getAttribute("employee");
			loginservices.Updatedasset(bean);
			request.getSession().setAttribute("employee", bean);
		} catch (Exception e) {
			return new ModelAndView("../indexPage", "message",
					"Please Login Again");
		}
		return new ModelAndView("homePage");
	}

	@RequestMapping(value = "/laptop.html", method = RequestMethod.GET)
	public ModelAndView laptopPage(HttpServletRequest request) {
		try {
			boolean updateflag = Boolean.valueOf((String) request.getSession()
					.getAttribute("updateFlag"));
			EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
			if (!updateflag) {
				boolean updateflag1 = Boolean.valueOf((String) request
						.getSession().getAttribute("updateFlag"));
				if (!updateflag1) {
					bean = loginservices.Updatedlaptop(bean);
					request.getSession().setAttribute("updateFlag", "true");
				}
			System.out.println(bean.getLaptopBeans());
			
			}
			bean = loginservices.Updatedlaptop(bean);
			request.getSession().setAttribute("employee",bean);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message",
					"Please Login Again");
		}
		return new ModelAndView("laptopPage");
	}
	
	@RequestMapping(value = "/laptopDetails.html", method = RequestMethod.GET)
	public ModelAndView laptopDetailsPage(HttpServletRequest request) {
		try {
			EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
			List<LaptopBean> laptopBeans=new ArrayList<LaptopBean>(20);
			laptopBeans=services.getLaptopDetails(bean);
			System.out.println(laptopBeans);
			request.setAttribute("laptopDetails", laptopBeans);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message",
					"Please Login Again");
		}
		return new ModelAndView("laptopDetailsPage");
	}

	@RequestMapping(value = "/laptopupdate.html", method = RequestMethod.GET)
	public void laptopupdate(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			boolean updateflag = Boolean.valueOf((String) request.getSession()
					.getAttribute("updateFlag"));
			EmployeeBean bean = (EmployeeBean) request.getSession()
					.getAttribute("employee");
			bean = loginservices.Updatedlaptop(bean);
			session.setAttribute("employee", bean);
			session.setAttribute("updateFlag", "true");
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/getciscoReportingHierarchy.html", method = RequestMethod.GET)
	public List<String> getciscoReportingHierarchy(HttpServletRequest request) {
		return services.getReportingStructure((EmployeeBean) request
				.getSession().getAttribute("employee"));
	}

	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().setAttribute("employee", null);
		request.getSession().invalidate();
		return new ModelAndView("../indexPage", "message",
				"Logged out successFully");
	}

	@RequestMapping(value = "/userManagement.html", method = RequestMethod.GET)
	public ModelAndView userManagement(HttpServletRequest request) {
		try{
			EmployeeBean bean=services.getUpdatedEmployeeBean((EmployeeBean) request.getSession()
					.getAttribute("employee"));
			request.setAttribute("message", request.getParameter("message"));
			request.getSession().setAttribute("employee",bean);
			if(bean.getDesignation().equalsIgnoreCase("Project Manager"))	return new ModelAndView("homePage");
			if(bean.getDesignation().equalsIgnoreCase("PCO Team"))
			{
				List<ValidateEmployee> employeeBeans=services.getValidatedEmployees((Integer)request.getSession().getAttribute("year"),(String)request.getSession().getAttribute("quarter"));
				request.getSession().setAttribute("employeeSubmission", employeeBeans);
			}
			if(bean.getDesignation().equalsIgnoreCase("Program Manager"))
			{
				List<ValidateEmployee> employeeBeans=services.getvalidatedStatusforPGM(bean,(Integer)request.getSession().getAttribute("year"),(String)request.getSession().getAttribute("quarter"));
				request.getSession().setAttribute("employeeSubmission1", employeeBeans);
			}
			String key=request.getParameter("key");
			if(key==null){
				return new ModelAndView("MaintainUsers");
			}else{
				switch (key) {
				case "mantainUsers":
					return new ModelAndView("MaintainUsers");
				case "delegateAssets":
					return new ModelAndView("delegateAssets");
				case "quarterlyAssetUpdates":
					return new ModelAndView("quarterlyAssetUpdates");
				case "maintaindeactivatedUsers":
					return new ModelAndView("deactivatedUsers");
				case "uploadProjectTrack":
					return new ModelAndView("uploadProjectTrack");
				case "deleteAssets":
					return new ModelAndView("deleteAssets");
				default:
					break;
				}}
		}catch(Exception e){
			return new ModelAndView("../indexPage","message","Please Login Again");
		}
		return new ModelAndView("userManagementPage");
	}

	@RequestMapping(value = "/delegatemgr.html", method = RequestMethod.GET)
	public ModelAndView delegatemgr(HttpServletRequest request) {
		try{
			EmployeeBean bean=services.getUpdatedEmployeeBean((EmployeeBean) request.getSession()
					.getAttribute("employee"));
			request.getSession().setAttribute("employee",bean);
			request.setAttribute("req","delegatemgr");
			if(bean.getDesignation().equalsIgnoreCase("Project Manager"))	return new ModelAndView("homePage");
			if(bean.getDesignation().equalsIgnoreCase("PCO Team")){
				List<ValidateEmployee> employeeBeans=services.getValidatedEmployees((Integer)request.getSession().getAttribute("year"),(String)request.getSession().getAttribute("quarter"));
				request.getSession().setAttribute("employeeSubmission", employeeBeans);
			}
		}catch(Exception e){
			return new ModelAndView("../indexPage","message","Please Login Again");
		}
		return new ModelAndView("userManagementPage");
	}


	@RequestMapping(value = "/tools.html", method = RequestMethod.GET)
	public ModelAndView pocTools(HttpServletRequest request) {
		try {
			EmployeeBean bean = loginservices.getUpdatedEmployeeBean((EmployeeBean) request.getSession().getAttribute("employee"));
			request.getSession().setAttribute("employee", bean);
			if (!bean.getDesignation().equalsIgnoreCase("PCO Team"))
				return new ModelAndView("homePage");
			String key=request.getParameter("key");
			if(key!=null){
				switch(key){
				case "maintainQuarters":
					List<Quarters> quarters=services.getQuarters((Integer)request.getSession().getAttribute("year"));
					if(quarters!=null){
						request.setAttribute("quarters",quarters );
					}
					return new ModelAndView("maintainQuarters");
				case "maintainSystemFreezeDate":
					List<SystemFreezeDate> freezeDates=services.getFreezeDates((Integer)request.getSession().getAttribute("year"));
					request.setAttribute("freezeDates",freezeDates);
					return new ModelAndView("maintainSystemFreezeDate");
				case "maintainAATURLS":
					List<AATBean> aatbeans=services.getAATURLS();
					if(aatbeans!=null)
						request.setAttribute("urls", aatbeans);
					return new ModelAndView("maintainAATURLS");
				default:
					return new ModelAndView("maintainQuarters");
				}
			}
			else{
				return new ModelAndView("maintainQuarters");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message","Please Login Again");
		}
	}

	@RequestMapping(value="/aat.html",method=RequestMethod.GET)
	public ModelAndView AAT(HttpServletRequest request,RedirectAttributes ra){
		try {
			List<AATBean> aatBeans=services.getAATURLS();
			String rockiesURL=null;
			String fujiURL=null;
			
			for (AATBean aatBean : aatBeans) {
				if(aatBean.getUrlName().equalsIgnoreCase("rockies"))
					rockiesURL=aatBean.getUrl();
				else if(aatBean.getUrlName().equalsIgnoreCase("fuji"))
					fujiURL=aatBean.getUrl();
			}
			URL url = new URL(rockiesURL);
			HttpURLConnection connection =(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "text/plain");
			InputStream inputStream = connection.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "UTF-8");
			String[] arr=writer.toString().split(",");
			int rockiesTotalSpace = Integer.parseInt(arr[0].replaceAll("[^0-9]", ""));
			int rockiesAllocatedSpace=Integer.parseInt(arr[1].replaceAll("[^0-9]", ""));
			int rockiesFreeSpace=rockiesTotalSpace-rockiesAllocatedSpace;
			connection.disconnect();
			url = new URL(fujiURL);
			connection =(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "text/plain");
			inputStream = connection.getInputStream();
			writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "UTF-8");
			arr=writer.toString().split(",");
			int  fujiAllocatedSpace= Integer.parseInt(arr[0].replaceAll("[^0-9]", ""));
			int fujiTotalSpace=Integer.parseInt(arr[1].replaceAll("[^0-9]", ""));
			int fujiFreeSpace=fujiTotalSpace-fujiAllocatedSpace;
			connection.disconnect();
			request.setAttribute("rockiesTotalSpace", rockiesTotalSpace);
			request.setAttribute("rockiesAllocatedSpace", rockiesAllocatedSpace);
			request.setAttribute("rockiesFreeSpace", rockiesFreeSpace);
			request.setAttribute("fujiTotalSpace", fujiTotalSpace);
			request.setAttribute("fujiAllocatedSpace", fujiAllocatedSpace);
			request.setAttribute("fujiFreeSpace", fujiFreeSpace);
			return new ModelAndView("aatPage");
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView view=new ModelAndView(new RedirectView("/AFMS/asset.html"));
			ra.addFlashAttribute("message","AAT URls' are down");
			return view;
		}
	}


	@RequestMapping(value = "/feedback.html", method = RequestMethod.GET)
	public ModelAndView feedbackPage(HttpServletRequest request) {
		try {
			} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message",
					"Please Login Again");
		}
		return new ModelAndView("feedback");
	}
	@RequestMapping(value = "/feedbackaction.html", method = RequestMethod.POST)
	public ModelAndView feedBackAction(HttpServletRequest request,RedirectAttributes attributes) {
		try {

			final EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
			final String app = request.getParameter("app");
			final String help = request.getParameter("help");
			Executor executor = Executors.newSingleThreadExecutor();
			if(app!=null &&  !app.equalsIgnoreCase(""))
			{
				if(app.length()>2000) throw new Exception("Feedback can contain only 2000 characters");
				services.savefeedbackapp(bean.getUserId(),app);
				executor.execute(new Runnable() {
					public void run() {
						try {

							MailSender mailSender = new MailSender();
							String subject = bean.getEmployeeName()+ " appreciated AFMS";
							String body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "feedbackTemplate.html");
							body = MailUitility.feedbackTemplate(body,bean,app);
							mailSender.sendquartermails(subject, body, bean.getUserId()
									+ AFMSConstant.ciscodomain, "zensar-afms-it-team"
									+ AFMSConstant.ciscodomain, null);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
				attributes.addFlashAttribute("success", "Thank you for your kind words");
				return new ModelAndView(new RedirectView("/AFMS/feedback.html"));
			}else{
				if(help.length()>2000) throw new Exception("Feedback can contain only 2000 characters");
				services.savefeedback(bean.getUserId(),help);
				executor.execute(new Runnable() {
					public void run() {
						try {

							MailSender mailSender = new MailSender();
							String subject = bean.getEmployeeName()+ " feedback for AFMS";
							String body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "feedbackTemplate.html");
							body = MailUitility.feedbackTemplate(body,bean,help);
							mailSender.sendquartermails(subject, body, bean.getUserId()
									+ AFMSConstant.ciscodomain, "zensar-afms-it-team"
									+ AFMSConstant.ciscodomain, null);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
				attributes.addFlashAttribute("success", "Thank you for your feedback");
				return new ModelAndView(new RedirectView("/AFMS/feedback.html"));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("../indexPage", "message",
					"Please Login Again");
		}

		
	}





}
