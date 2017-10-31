package com.zensar.afnls.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.LaptopBean;
import com.zensar.afnls.beans.Laptopsmail;
import com.zensar.afnls.exception.AccountInactiveException;
import com.zensar.afnls.exception.AssetTrackNotFetchedException;
import com.zensar.afnls.exception.AssetTrackNotUpdatedException;
import com.zensar.afnls.exception.IncorrectCredentialsException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;
import com.zensar.afnls.util.AFMSConstant;
import com.zensar.afnls.util.LaptopUtility;
import com.zensar.afnls.util.MailSender;
import com.zensar.afnls.util.MailUitility;

@Controller
public class FunctionalityFilter {
	@Autowired
	AssetForecastingSystemServicesImpl services;

	public AssetForecastingSystemServicesImpl getServices() {
		return services;
	}

	public void setServices(AssetForecastingSystemServicesImpl services) {
		this.services = services;
	}

	@RequestMapping(value = "/statusFilterRequest.html", method = RequestMethod.POST)
	public ModelAndView loginCheck(HttpServletRequest request,RedirectAttributes ra) {
		String filtervalue = request.getParameter("selectstatusFilter");
		String submitbutton = request.getParameter("closedallrequest");
		String laptopid = request.getParameter("searchlaptopid");
		
 		EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute(
				"employee");
		try {
			if (submitbutton != null && submitbutton.equalsIgnoreCase("Submit")) {
				String[] value = request.getParameterValues("case");
			
				EmployeeBean  employee = (EmployeeBean)request.getSession().getAttribute("employee");
				final String fromuserid = employee.getUserId();
				final String fromusername = employee.getEmployeeName();
				if (value != null) {
					for (int i=0;i<value.length ;i++) {
						LaptopBean b = new LaptopBean();
						b.setLaptopId(Integer.valueOf(value[i].trim()));
						b.setRequestStatus("C");
						
						
						b.setRemarksFromLA(request.getParameter("remarks"+b.getLaptopId()));
							try {
							if(services.Closelaptoprequest(b)){
								
								final LaptopBean nonfinallaptopbean =services.getLaptopsbylaptopid(b.getLaptopId());
								if(nonfinallaptopbean.getRequestType().equalsIgnoreCase("New Hire")){
								Executor executor = Executors.newSingleThreadExecutor();
								executor.execute(new Runnable() {
									public void run() {
										try {
											StringBuffer uniid= new StringBuffer(LaptopUtility.splitOwnerName(nonfinallaptopbean.getOwnerName()));
											uniid.append("/");uniid.append(nonfinallaptopbean.getLaptopId());
											final String uniquelaptopid = uniid.toString();
											MailSender mailSender = new MailSender();
											String subject = "Laptop / VPN Approval :"+ nonfinallaptopbean.getOwnerName();

											
											String body = MailUitility
													.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
															+ "closeLaptopRequestNewHire.html");
											body = MailUitility
													.convertclosedLaptopStaticRequesttoRuntime(body,
															nonfinallaptopbean, fromusername);
											
											mailSender.sendLaptopClosedMail(subject, body,
													fromuserid,nonfinallaptopbean.getUserId()+AFMSConstant.ciscodomain);
										} catch (Exception e) {
											e.printStackTrace();
										}
										

										/* do something */}
								});
							}else{
								
								Executor executor = Executors.newSingleThreadExecutor();
								executor.execute(new Runnable() {
									public void run() {
										try {
											StringBuffer uniid= new StringBuffer(LaptopUtility.splitOwnerName(nonfinallaptopbean.getOwnerName()));
											uniid.append("/");uniid.append(nonfinallaptopbean.getLaptopId());
											final String uniquelaptopid = uniid.toString();
											MailSender mailSender = new MailSender();
											String subject = "Laptop / VPN Approval :"+ nonfinallaptopbean.getOwnerName();

											
											String body = MailUitility
													.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
															+ "closeLaptoprequest.html");
											body = MailUitility
													.convertclosedLaptopStaticRequesttoRuntime(body,
															nonfinallaptopbean, fromusername);
											
											mailSender.sendLaptopClosedMail(subject, body,
													fromuserid,nonfinallaptopbean.getUserId()+AFMSConstant.ciscodomain);
										} catch (Exception e) {
											e.printStackTrace();
										}
										

										/* do something */}
								});	
								
								
								
							}
								
							}
						} catch (AssetTrackNotUpdatedException e) {
							e.printStackTrace();
						}
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
							request.getSession().setAttribute("pending", pending.size());
							request.getSession().setAttribute("completed", completed.size());
							request.getSession().setAttribute("moreinfo", moreinfo.size());
							request.getSession().setAttribute("approved", approved.size());
						List<LaptopBean> lpbean = services
								.getlaptopfiltervalue(filtervalue);
						bean.setLaptopBeans(lpbean);
						request.getSession().setAttribute("employee", bean);
						request.setAttribute("filterselect", filtervalue);
					}
				}
			} else if (submitbutton != null && submitbutton.equalsIgnoreCase("Approve")) {
				String[] value = request.getParameterValues("case");
				System.out.println("value "+value);
				if (value != null) {
					for (String temp : value) {
						System.out.println("temp "+temp);
						LaptopBean b = new LaptopBean();
						b.setLaptopId(Integer.valueOf(temp));
						b.setRequestStatus("A");
						try {
							if(services.updatelaptopstatus(b)){
								
								Executor executor = Executors.newSingleThreadExecutor();
								final String c_laptopid =temp;
								executor.execute(new Runnable() {
									public void run() {
										LaptopBean lpbean;
										 final String []ccarrayList = new String[2];
										try {
											lpbean = services.getLaptopsbylaptopid(Integer
													.valueOf(c_laptopid));
											
											String mgrId =lpbean.getUserId();
											String mgrIdWithEmail = mgrId+AFMSConstant.ciscodomain;
											ccarrayList[0] =mgrIdWithEmail;
											try {
												ccarrayList[1]= services.getEmployeeThroughLogin(new EmployeeBean(mgrId)).getManagerId()+AFMSConstant.ciscodomain;
											} catch (IncorrectCredentialsException e) {
												e.printStackTrace();
											} catch (ServicesNotAvailableException e) {
												e.printStackTrace();
											} catch (AccountInactiveException e) {
												e.printStackTrace();
											}
											MailSender mailSender = new MailSender();
											String subject =lpbean.getOwnerName()+"'s Laptop Request has been approved for "+ lpbean.getProjectName()+" project";
											String body = MailUitility
													.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
															+ "ApprovefromODC.html");
											body=MailUitility.ApprovedAndRejectMailTemplate(body,lpbean);
											
											mailSender.sendMailInTableFormat(subject, body,
													MailSender.raiseNewHireLaptopRequest
															.getOdcHead(),
													MailSender.raiseNewHireLaptopRequest
															.getAmitemail(), ccarrayList);
										} catch (DataAccessException | NumberFormatException
												| SQLException e) {
											e.printStackTrace();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								});
								
								
							}
						} catch (AssetTrackNotUpdatedException e) {
							e.printStackTrace();
						}

						List<LaptopBean> lpbean = services
								.getlaptopfiltervalue(filtervalue);
						bean.setLaptopBeans(lpbean);
						request.getSession().setAttribute("employee", bean);
						request.setAttribute("filterselect", filtervalue);
						
					}
				}
				ModelAndView view= new ModelAndView(new RedirectView("/AFMS/laptop.html"));
				ra.addFlashAttribute("success","Laptop request approved");
				return view;
			}else if (submitbutton != null && submitbutton.equalsIgnoreCase("Reject")) {
				String[] value = request.getParameterValues("case");
				if (value != null) {
					for (String temp : value) {

						LaptopBean b = new LaptopBean();
						b.setLaptopId(Integer.valueOf(temp));
						b.setRequestStatus("R");
						try {
							if(services.updatelaptopstatus(b)){
								
								Executor executor = Executors.newSingleThreadExecutor();
								final String c_laptopid =temp;
								executor.execute(new Runnable() {
									public void run() {
										LaptopBean lpbean;
										 final String []ccarrayList = new String[2];
										try {
											lpbean = services.getLaptopsbylaptopid(Integer
													.valueOf(c_laptopid));

											String mgrId =lpbean.getUserId();
											String mgrIdWithEmail = mgrId+AFMSConstant.ciscodomain;
											ccarrayList[0] =mgrIdWithEmail;
											try {
												ccarrayList[1]= services.getEmployeeThroughLogin(new EmployeeBean(mgrId)).getManagerId()+AFMSConstant.ciscodomain;
											} catch (IncorrectCredentialsException e) {
												e.printStackTrace();
											} catch (ServicesNotAvailableException e) {
												e.printStackTrace();
											} catch (AccountInactiveException e) {
												e.printStackTrace();
											}
											MailSender mailSender = new MailSender();
											String subject =MailSender.raiseNewHireLaptopRequest.getODCName()+" has Rejected "+ lpbean.getOwnerName()+ "'s Laptop Request for Project "+ lpbean.getProjectName();
											String body = MailUitility.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
															+ "rejectfromODC.html");
											body=MailUitility.ApprovedAndRejectMailTemplate(body,lpbean);
											mailSender.sendMailInTableFormat(subject, body,
													MailSender.raiseNewHireLaptopRequest
															.getOdcHead(),
													MailSender.raiseNewHireLaptopRequest
															.getAmitemail(), ccarrayList);
										} catch (DataAccessException | NumberFormatException
												| SQLException e) {
											e.printStackTrace();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								});
							}
						} catch (AssetTrackNotUpdatedException e) {
							e.printStackTrace();
						}

						List<LaptopBean> lpbean = services
								.getlaptopfiltervalue(filtervalue);
						bean.setLaptopBeans(lpbean);
						request.getSession().setAttribute("employee", bean);
						request.setAttribute("filterselect", filtervalue);
						
					}
				}
				ModelAndView view= new ModelAndView(new RedirectView("/AFMS/laptop.html"));
				ra.addFlashAttribute("success","Laptop request rejected");
				return view;
			}
			
			else if(laptopid!=null&&laptopid.length()>0)
				{
						String split[] =  laptopid.split("/");
						if(split.length<2)
						{
							request.setAttribute("message", "Invalid Request Id");
						}
						
						
						request.setAttribute("filterselectbylaptopid", "test");
					
						
						try {
							List<LaptopBean> lpbean = new ArrayList<LaptopBean>();
							LaptopBean laptopBean= services.getLaptopsbylaptopid(Integer.valueOf(split[1]));
							String dbname = LaptopUtility.splitOwnerName(laptopBean.getOwnerName());
							if(split[0].equalsIgnoreCase(dbname)){
							lpbean.add(laptopBean);
							request.setAttribute("filtervalue", lpbean);
							}else{
								request.setAttribute("message", "Invalid Request Id");
							}
							
						}  catch (Exception e) {
							e.printStackTrace();
						}
						return new ModelAndView("laptopPage", "employee", bean);
				}else {
				List<LaptopBean> lpbean = services
						.getlaptopfiltervalue(filtervalue);
				//bean.setLaptopBeans(lpbean);
				request.setAttribute("filtervalue", lpbean);
				request.setAttribute("filterselect", filtervalue);
				
			}

		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("laptopPage", "message", e.getMessage());
		} catch (DataAccessException e) {
			return new ModelAndView("laptopPage", "message", "Please Try Again");
		} catch (AssetTrackNotFetchedException e) {
			return new ModelAndView("laptopPage", "message","Please Try Again");
		} catch (SQLException e) {
			return new ModelAndView("laptopPage", "message", "Please Try Again");
		}
		return new ModelAndView("laptopPage");
	}

	@RequestMapping(value = "/downloadimage.html", method = RequestMethod.GET)
	public void downloadimage(HttpServletRequest request,
			HttpServletResponse resp) throws DataAccessException, SQLException,
			IOException {
		BufferedOutputStream stream = null;

		try {
			String loptopId = request.getParameter("laptopid");
			Integer lpid = Integer.valueOf(loptopId);
			Laptopsmail mails = services.getLaptopMails(lpid);
			File dir = File.createTempFile(mails.getMailName(), "");
			stream = new BufferedOutputStream(new FileOutputStream(dir));
			IOUtils.copy(mails.getMailData(), stream);
			stream.close();

			resp.setContentType("application/octet-stream");

			resp.setHeader("Content-Disposition", "attachment; filename=\""
					+ mails.getMailName() + "\"");

			OutputStream outStream = resp.getOutputStream();

			FileInputStream input = new FileInputStream(dir);

			/*
			 * 486 byte[] outputByte = new byte['?'];
			 * 
			 * 488 while (((InputStream) inputStream).read(outputByte, 0, 4096)
			 * != -1) { 490 outStream.write(outputByte, 0, 4096); }
			 */

			byte[] array = IOUtils.toByteArray(input);

			outStream.write(array);
			resp.setContentType("text/application");
			resp.setHeader("Content-Disposition", "attachment;filename="
					+ mails.getMailName());
			resp.setHeader("Cache-Control", "private, max-age=0");
			((InputStream) input).close();
			outStream.flush();
			outStream.close();

		} catch (Exception e) {
			
		} finally {

		}

	}
	@RequestMapping(value = "/searchLaptopId.html")
	public ModelAndView searchLaptopId(HttpServletRequest request) throws Exception {
		String laptopid = request.getParameter("searchlaptopid");
		
		request.setAttribute("filterselectbylaptopid", "test");
	
		EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute(
				"employee");
		try {
			List<LaptopBean> lpbean = new ArrayList<LaptopBean>();
			LaptopBean laptopBean= services.getLaptopsbylaptopid(Integer.valueOf(laptopid.trim()));
			lpbean.add(laptopBean);
			request.setAttribute("filtervalue", lpbean);
			
		}  catch (Exception e) {
			return new ModelAndView("laptopPage", "message", "Please Try Again");
		}
		return new ModelAndView("laptopPage", "employee", bean);
	}
}
