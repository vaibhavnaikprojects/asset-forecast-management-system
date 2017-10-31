package com.zensar.afnls.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.URLEncoder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.LaptopBean;
import com.zensar.afnls.beans.Laptopsmail;
import com.zensar.afnls.beans.Mail;
import com.zensar.afnls.beans.RaiseNewHireLaptopRequest;
import com.zensar.afnls.exception.AssetTrackNotFetchedException;
import com.zensar.afnls.exception.CannotAddAliasException;
import com.zensar.afnls.exception.LaptopNotAddedException;
import com.zensar.afnls.exception.LaptopRequestAlreadyPresentException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;
import com.zensar.afnls.services.LoginServiceForAFMS;
import com.zensar.afnls.util.AFMSConstant;
import com.zensar.afnls.util.CryptograpgyUtility;
import com.zensar.afnls.util.LaptopUtility;
import com.zensar.afnls.util.MailSender;
import com.zensar.afnls.util.MailUitility;

@Controller
public class LaptopController {
	@Autowired
	AssetForecastingSystemServicesImpl services;

	@Autowired
	LoginServiceForAFMS loginservices;


	RaiseNewHireLaptopRequest odchead =	MailUitility.readpropfile();

	private LaptopBean getLaptopBeanFromRequest(HttpServletRequest request) throws Exception {
		String stock = request.getParameter("fromType");
		LaptopBean laptopBean = new LaptopBean();
		laptopBean.setStock(stock);

		if (stock != null && stock.length() > 0 && stock.equalsIgnoreCase("N")) {

			laptopBean.setOwnerName(request.getParameter("toAssociateName"));
			laptopBean.setTouserId(request.getParameter("toAssociate"));
			laptopBean.setFromemployeeId(request.getParameter("fromAssociate"));
			if(laptopBean.getTouserId().equalsIgnoreCase(laptopBean.getFromemployeeId()))
			{
				throw new Exception("From and To user Id can't be same");
			}
			if(laptopBean.getOwnerName().equalsIgnoreCase("Invalid CEC ID"))
			{
				throw new LaptopNotAddedException("Please Enter Correct CEC Id");
			}

		} else if (stock != null && stock.length() > 0
				&& stock.equalsIgnoreCase("S")) {
			laptopBean.setOwnerName(request.getParameter("toAssociateName"));
			laptopBean.setEmployeeId(request.getParameter("toAssociate"));
			if(laptopBean.getOwnerName().equalsIgnoreCase("Invalid CEC ID"))
			{
				throw new LaptopNotAddedException("Please Enter Correct CEC Id");
			}
		}
		if(laptopBean.getOwnerName()==null || laptopBean.getOwnerName().equalsIgnoreCase("")){
			throw new Exception("Looks like your are not connected with cisco network");
		}
		laptopBean.setReason(request.getParameter("reason"));
		laptopBean.setRequestType(request.getParameter("requestType"));
		laptopBean.setStatus("Cisco ODC Approval");
		laptopBean.setProjectName(request.getParameter("projectName"));
		return laptopBean;
	}

	private LaptopBean getLaptopBeanFromEarlyRequest(HttpServletRequest request) throws Exception {
		LaptopBean laptopBean = new LaptopBean();
		String stock = request.getParameter("fromType1");

		laptopBean.setStock(stock);

		if (stock != null && stock.length() > 0 && stock.equalsIgnoreCase("N")) {

			laptopBean.setOwnerName(request.getParameter("toAssociateName"));
			laptopBean.setTouserId(request.getParameter("toAssociate"));
			laptopBean.setFromemployeeId(request.getParameter("fromAssociate"));
			if(laptopBean.getTouserId().equalsIgnoreCase(laptopBean.getFromemployeeId()))
			{
				throw new Exception("From and To user Id can't be same");
			}
			if(laptopBean.getOwnerName().equalsIgnoreCase("Invalid CEC ID"))
			{
				throw new LaptopNotAddedException("Please Enter Correct CEC Id");
			}
		} else if (stock != null && stock.length() > 0
				&& stock.equalsIgnoreCase("S")) {
			laptopBean.setOwnerName(request.getParameter("toAssociateName"));
			laptopBean.setEmployeeId(request.getParameter("toAssociate"));
			if(laptopBean.getOwnerName().equalsIgnoreCase("Invalid CEC ID"))
			{
				throw new LaptopNotAddedException("Please Enter Correct CEC Id");
			}
		}
		if(laptopBean.getOwnerName()==null || laptopBean.getOwnerName().equalsIgnoreCase("")){
			throw new Exception("Look like your are not connected with cisco network");
		}
		laptopBean.setReason(request.getParameter("reason"));
		laptopBean.setRequestType(request.getParameter("requestType"));
		laptopBean.setStatus("Cisco ODC Approval");
		laptopBean.setProjectName(request.getParameter("projectName"));
		return laptopBean;
	}

	@RequestMapping(value = "/raiseNewHireLaptopRequest.html", method = RequestMethod.POST)
	public ModelAndView addLaptopNewHire(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse res) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {

			LaptopBean laptopBean = getLaptopBeanFromRequest(request);
			final LaptopBean nonfinallaptopbean;
			final EmployeeBean bean = (EmployeeBean) request.getSession()
					.getAttribute("employee");
			EmployeeBean nonfinalBean = bean;
			if(services.checkLaptopRequest(laptopBean))	throw new LaptopRequestAlreadyPresentException("Laptop request already raised");

			if (services.addLaptopRequestNewHire(laptopBean, bean.getUserId())) {

				final int laptopid = services.getMaxbylaptopid();


				laptopBean = services.getLaptopsbylaptopid(laptopid);
				nonfinallaptopbean = laptopBean;
				System.out.println("laptop "+nonfinallaptopbean);
				final String uniquelaptopid = LaptopUtility.uniqueLaptopId(laptopBean.getOwnerName(), laptopBean.getLaptopId());
				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {
						try {
							String encryptedtext = CryptograpgyUtility
									.encrypt(String.valueOf(nonfinallaptopbean
											.getLaptopId()));

							URLEncoder rl = new URLEncoder();
							encryptedtext = rl.encode(encryptedtext);

							MailSender mailSender = new MailSender();
							String subject="";
							if(nonfinallaptopbean.getStock()!=null && "S".equalsIgnoreCase(nonfinallaptopbean.getStock()))
								subject = "Laptop Request For "
										+ nonfinallaptopbean.getOwnerName()
										+ "("
										+ nonfinallaptopbean.getProjectName()+")";
							else 
								subject = "Laptop Transfer Request For "
										+ nonfinallaptopbean.getOwnerName()
										+ "("
										+ nonfinallaptopbean.getProjectName()+")";

							String approveLink = LoginController.domain
									+ "raiseNewHireLaptopRequestApprove.html?laptopid="
									+ encryptedtext;
							String approveWithComments = LoginController.domain
									+ "raiseNewHireLaptopRequestCommentsApproval.html?laptopid="
									+ encryptedtext;
							String rejectLink = LoginController.domain
									+ "raiseNewHireLaptopRequestReject.html?laptopid="
									+ encryptedtext;
							String moreinfoLink = LoginController.domain
									+ "raiseNewHireLaptopRequestMoreInfo.html?laptopid="
									+ encryptedtext;
							String body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "raiseNewHireLaptopRequest.html");
							body = MailUitility
									.convertLaptopStaticRequesttoRuntime(body,
											nonfinallaptopbean, approveLink,approveWithComments,
											rejectLink, moreinfoLink);
							mailSender.sendMailInTableFormat(
									subject,
									body,
									bean.getUserId() + AFMSConstant.ciscodomain,
									MailSender.raiseNewHireLaptopRequest.getOdcHead(),
									null);

							subject = "Acknowledgement : " + subject+"";
							String ack_body = MailUitility.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath+"ack_mail.html");
							ack_body = MailUitility.ack_mail(ack_body, nonfinallaptopbean, uniquelaptopid);

							mailSender.sendAcknowledmentMail(subject, ack_body,
									"afms@cisco.com", bean.getUserId()+AFMSConstant.ciscodomain, bean.getManagerId()+AFMSConstant.ciscodomain,uniquelaptopid);
						} catch (Exception e) {
							e.printStackTrace();
						}

					/* do something */}
				});

				updateLaptopbean(request, nonfinalBean, services);
				ModelAndView modelAndView = new ModelAndView(new RedirectView(
						"/AFMS/laptop.html"));
				ra.addFlashAttribute("success", "New hire  request raised");

				ra.addFlashAttribute("laptop", laptopBean);
				return modelAndView;
			}
		} catch (ServicesNotAvailableException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message", e.getMessage());
		} catch (LaptopNotAddedException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message", e.getMessage());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message","Please try again later");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message", "Please try again later");
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("laptopPage", "message", e1.getMessage());
		}
		return new ModelAndView("laptopPage", "message","Laptop request was not raised");
	}

	@RequestMapping(value = "raiseEarlyRefreshLaptopRequest.html", method = RequestMethod.POST)
	public ModelAndView addLaptop(
			HttpServletRequest request,
			RedirectAttributes ra,
			final @RequestParam("ciscoManagerMail") MultipartFile ciscoManagerMail)
					throws IOException, InvalidKeyException, NoSuchAlgorithmException,
					NoSuchPaddingException, IllegalBlockSizeException,
					BadPaddingException {
		try {
			LaptopBean laptopBean = getLaptopBeanFromEarlyRequest(request);
			if(services.checkLaptopRequest(laptopBean))	throw new LaptopRequestAlreadyPresentException("Laptop request already raised");
			Laptopsmail laptopsmail = new Laptopsmail();
			laptopsmail.setLaptopId(laptopBean.getLaptopId());
			laptopsmail.setImgsize((int) ciscoManagerMail.getSize());
			laptopsmail.setMailData(ciscoManagerMail.getInputStream());
			laptopsmail.setMailName(ciscoManagerMail.getOriginalFilename());
			final EmployeeBean bean = (EmployeeBean) request.getSession()
					.getAttribute("employee");

			if (!ciscoManagerMail.isEmpty()) {
			}
			if(validate(laptopsmail)){
				if (services.addLaptopRequest(laptopBean, bean.getUserId(),
						laptopsmail)) {

					final LaptopBean finallaptopbean;

					laptopBean = services.getLaptopsbylaptopid(laptopsmail
							.getLaptopId());
					finallaptopbean = laptopBean;

					final String uniquelaptopid = LaptopUtility.uniqueLaptopId(laptopBean.getOwnerName(), laptopBean.getLaptopId());

					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(new Runnable() {
						public void run() {

							try {
								String encryptedtext = CryptograpgyUtility
										.encrypt(String.valueOf(finallaptopbean
												.getLaptopId()));

								URLEncoder rl = new URLEncoder();
								encryptedtext = rl.encode(encryptedtext);

								MailSender mailSender = new MailSender();
								String subject="";
								if(finallaptopbean.getStock()!=null && "S".equalsIgnoreCase(finallaptopbean.getStock()))
									subject = "Laptop Request For "
											+ finallaptopbean.getOwnerName()
											+ "("
											+ finallaptopbean.getProjectName()+")";
								else 
									subject = "Laptop Transfer Request For "
											+ finallaptopbean.getOwnerName()
											+ "("
											+ finallaptopbean.getProjectName()+")";
								String approveLink = LoginController.domain
										+ "raiseNewHireLaptopRequestApprove.html?laptopid="
										+ encryptedtext;
								String approveWithComments = LoginController.domain
										+ "raiseNewHireLaptopRequestCommentsApproval.html?laptopid="
										+ encryptedtext;
								String rejectLink = LoginController.domain
										+ "raiseNewHireLaptopRequestReject.html?laptopid="
										+ encryptedtext;
								String moreinfoLink = LoginController.domain
										+ "raiseNewHireLaptopRequestMoreInfo.html?laptopid="
										+ encryptedtext;
								String body = MailUitility
										.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
												+ "raiseNewHireLaptopRequest.html");

								body = MailUitility
										.convertLaptopStaticRequesttoRuntime(body,
												finallaptopbean, approveLink,approveWithComments,
												rejectLink, moreinfoLink);

								File dir = File.createTempFile(
										ciscoManagerMail.getOriginalFilename(), "");
								BufferedOutputStream stream = new BufferedOutputStream(
										new FileOutputStream(dir));
								IOUtils.copy(ciscoManagerMail.getInputStream(),
										stream);
								stream.close();



								mailSender.sendMailInTableFormatWithAttachment(
										subject, body, bean.getUserId()
										+ AFMSConstant.ciscodomain,
										MailSender.raiseNewHireLaptopRequest.getOdcHead(),
										null,
										dir.getAbsolutePath(),
										ciscoManagerMail.getOriginalFilename());
								subject = "Acknowledgement : " + subject+"";
								String ack_body = MailUitility.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath+"ack_mail.html");
								ack_body = MailUitility.ack_mail(ack_body, finallaptopbean, uniquelaptopid);



								mailSender.sendAcknowledmentMail(subject, ack_body,
										"afms@cisco.com", bean.getUserId()+AFMSConstant.ciscodomain, bean.getManagerId()+AFMSConstant.ciscodomain,uniquelaptopid);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});
					updateLaptopbean(request, bean, services);
					ModelAndView modelAndView = new ModelAndView(new RedirectView(
							"/AFMS/laptop.html"));
					ra.addFlashAttribute("success", "Early refresh laptop  request raised");
					ra.addFlashAttribute("laptop", laptopBean);

					return modelAndView;
				}
			}else
			{
				ModelAndView modelAndView = new ModelAndView(new RedirectView(
						"/AFMS/laptop.html"));
				ra.addFlashAttribute("message", "Kindly attach a mail file(.msg)");
				ra.addFlashAttribute("laptop", laptopBean);

				return modelAndView;

			}
		} catch (ServicesNotAvailableException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message", e.getMessage());
		}  catch (LaptopNotAddedException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message", e.getMessage());
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			return new ModelAndView("laptopPage", "message", "Please try again later");
		} catch (SQLException e1) {
			e1.printStackTrace();
			return new ModelAndView("laptopPage", "message", "Please try again later");
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("laptopPage", "message", e1.getMessage());
		}
		return new ModelAndView("laptopPage", "message",
				"Laptop request was not raised");
	}

	private boolean validate(Laptopsmail laptopBean) {
		String fileName = laptopBean.getMailName();
		String farray []=  fileName.split("\\.");
		if(farray!=null&& farray.length>1&& (farray[1].equalsIgnoreCase("msg")))
		{
			return true;
		}
		return false;

	}


	@RequestMapping(value = "/Remindersforlpapproval.html", method = RequestMethod.GET)
	public ModelAndView Remindersforlpapproval(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse res) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {

		boolean flag = false;

		Integer laptopid = Integer.valueOf(request.getParameter("laptopid"));
		final EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute(
				"employee");
		Iterator itr = bean.getLaptopBeans().iterator();
		while (itr.hasNext()) {
			LaptopBean laptopBean = (LaptopBean) itr.next();
			if (laptopBean.getLaptopId() == laptopid) {
				flag = true;
			}
		}

		try {

			if (flag) {

				final	LaptopBean laptopBean = services.getLaptopsbylaptopid(laptopid);
				final String uniquelaptopid = LaptopUtility.uniqueLaptopId(laptopBean.getOwnerName(), laptopBean.getLaptopId());
				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {

						String encryptedtext="";
						try {
							encryptedtext = CryptograpgyUtility.encrypt(String
									.valueOf(laptopBean.getLaptopId()));
						} catch (InvalidKeyException | NoSuchAlgorithmException
								| NoSuchPaddingException | IllegalBlockSizeException
								| BadPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						URLEncoder rl = new URLEncoder();
						encryptedtext = rl.encode(encryptedtext);



						MailSender mailSender = new MailSender();
						String subject="";
						if(laptopBean.getStock()!=null && "S".equalsIgnoreCase(laptopBean.getStock()))
							subject = "Laptop Request For "
									+ laptopBean.getOwnerName()
									+ "("
									+ laptopBean.getProjectName()+")"+ "   ** Reminder **";
						else 
							subject = "Laptop Transfer Request For "
									+ laptopBean.getOwnerName()
									+ "("
									+ laptopBean.getProjectName()+")"+ "   ** Reminder **";

						String approveLink = LoginController.domain
								+ "raiseNewHireLaptopRequestApprove.html?laptopid="
								+ encryptedtext;
						String approveWithComments = LoginController.domain
								+ "raiseNewHireLaptopRequestCommentsApproval.html?laptopid="
								+ encryptedtext;
						String rejectLink = LoginController.domain
								+ "raiseNewHireLaptopRequestReject.html?laptopid="
								+ encryptedtext;
						String moreinfoLink = LoginController.domain
								+ "raiseNewHireLaptopRequestMoreInfo.html?laptopid="
								+ encryptedtext;
						String body="";
						try {
							body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "raiseNewHireLaptopRequest.html");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							body = MailUitility.convertLaptopStaticRequesttoRuntime(body,
									laptopBean, approveLink,approveWithComments ,rejectLink, moreinfoLink);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						mailSender.sendMailInTableFormat(subject, body,
								bean.getUserId() + AFMSConstant.ciscodomain,MailSender.raiseNewHireLaptopRequest.getOdcHead(), null);
						subject = "Acknowledgement : " + subject+"";
						String ack_body;
						try {
							ack_body = MailUitility.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath+"reminder_mail.html");

							ack_body = MailUitility.ack_mail(ack_body, laptopBean, uniquelaptopid);
							mailSender.sendAcknowledmentMail(subject, ack_body,
									"afms@cicoc.com", bean.getUserId()+AFMSConstant.ciscodomain, bean.getManagerId()+AFMSConstant.ciscodomain,uniquelaptopid);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}});
				//request.getSession().setAttribute("employee",
				//	services.getUpdatedEmployeeBean(bean));
				ModelAndView modelAndView = new ModelAndView(new RedirectView("/AFMS/laptop.html"));
				ra.addFlashAttribute("success","Reminder mail sent successfully");
				ra.addFlashAttribute("laptop", laptopBean);
				return modelAndView;
			}
		} catch (DataAccessException e) {
			return new ModelAndView("laptopPage", "message","Please try again later");
		} catch (SQLException e) {
			return new ModelAndView("laptopPage", "message","Please try again later");
		}
		return new ModelAndView("laptopPage", "success","Reminder mail sent successfully");
	}
	public void updateLaptopbean(HttpServletRequest request,EmployeeBean bean,AssetForecastingSystemServicesImpl services) throws DataAccessException, SQLException
	{
		//LaptopUtility.updatedLaptopBean(request, bean,services);
		bean =  loginservices.Updatedlaptop(bean);
	}



	@RequestMapping(value = "/manageitolist.html", method = RequestMethod.GET)
	public ModelAndView manageitolist(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse res) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			request.setAttribute("mailer_list", InitiliazeResourceAtServerStartup.itoccList);

		} catch (DataAccessException e) {
			return new ModelAndView("ManageITOList","message","Please Try Again");
		}
		return new ModelAndView("ManageITOList");
	}
	@RequestMapping(value = "/addMailerList.html", method = RequestMethod.POST)
	public ModelAndView addMailerList(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse res) {
		try {
			String mailerId=request.getParameter("mailerId");
			if(null!=mailerId &&  mailerId.trim().length()>0)
			{
				mailerId = mailerId+AFMSConstant.ciscodomain;
			}
			if(services.addMailerAlias(mailerId)){
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/manageitolist.html"));
				ra.addFlashAttribute("success","Alias added");
				InitiliazeResourceAtServerStartup.itoccList  =  services.getITOList();

				return modelAndView;}

		} catch (DataAccessException | CannotAddAliasException | ServicesNotAvailableException | AssetTrackNotFetchedException e) {
			return new ModelAndView("ManageITOList","message","Please Try Again");
		}
		return new ModelAndView("ManageITOList");
	}

	@RequestMapping(value = "/deletemailid.html", method = RequestMethod.GET)
	public ModelAndView deletemailid(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse res) {
		try {
			String mailerId=request.getParameter("deletemailid");
			if(services.deleteMailerAlias(mailerId)){
				ModelAndView modelAndView= new ModelAndView(new RedirectView("/AFMS/manageitolist.html"));
				ra.addFlashAttribute("message","Alias delete");
				InitiliazeResourceAtServerStartup.itoccList  =  services.getITOList();

				return modelAndView;}

		} catch (DataAccessException | ServicesNotAvailableException | AssetTrackNotFetchedException e) {
			return new ModelAndView("ManageITOList","message","Please Try Again");
		}
		return new ModelAndView("ManageITOList");
	}


	@RequestMapping(value = "/editLaptopMoreInfoDetails.html", method = RequestMethod.POST)
	public ModelAndView updateLaptopRequest(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse res) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			int laptopId=Integer.parseInt(request.getParameter("laptopId"));
			String ownerName=request.getParameter("ownerName");
			String requestType=request.getParameter("requestType");
			String projectName=request.getParameter("projectName");
			final String oldReason=request.getParameter("oldReason");
			String createdDate=request.getParameter("createdDate");
			String newReason=request.getParameter("reason");
			final String uniquelaptopid=request.getParameter("uniquelaptopid");
			final EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
			EmployeeBean nonfinalBean = bean;
			LaptopBean laptopBean = new LaptopBean();
			laptopBean.setLaptopId(laptopId);
			laptopBean.setOwnerName(ownerName);
			laptopBean.setProjectName(projectName);
			laptopBean.setCreatedDate(createdDate);
			laptopBean.setRequestType(requestType);
			laptopBean.setReason(newReason);
			laptopBean.setEmployeeId(bean.getEmployeeName());
			final LaptopBean nonfinallaptopbean=laptopBean;
			Executor executor = Executors.newSingleThreadExecutor();
			if(services.updateLaptopBeanReason(nonfinallaptopbean)){
				executor.execute(new Runnable() {
					public void run() {
						try {
							String encryptedtext = CryptograpgyUtility.encrypt(String.valueOf(nonfinallaptopbean.getLaptopId()));
							URLEncoder rl = new URLEncoder();
							encryptedtext = rl.encode(encryptedtext);
							MailSender mailSender = new MailSender();
							String subject = "Laptop Request For "
									+ nonfinallaptopbean.getOwnerName()
									+ " for "
									+ nonfinallaptopbean.getProjectName();

							String approveLink = LoginController.domain
									+ "raiseNewHireLaptopRequestApprove.html?laptopid="
									+ encryptedtext;
							String approveWithComments = LoginController.domain
									+ "raiseNewHireLaptopRequestCommentsApproval.html?laptopid="
									+ encryptedtext;
							String rejectLink = LoginController.domain
									+ "raiseNewHireLaptopRequestReject.html?laptopid="
									+ encryptedtext;
							String moreinfoLink = LoginController.domain
									+ "raiseNewHireLaptopRequestMoreInfo.html?laptopid="
									+ encryptedtext;
							String body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "reasonEditForRequest.html");
							body = MailUitility
									.editLaptopReasonStaticRequesttoRuntime(body,
											nonfinallaptopbean, approveLink,approveWithComments,
											rejectLink, moreinfoLink,oldReason);
							if("New Hire".equalsIgnoreCase(nonfinallaptopbean.getRequestType()))
								mailSender.sendMailInTableFormat(subject,body,bean.getUserId() + AFMSConstant.ciscodomain,MailSender.raiseNewHireLaptopRequest.getOdcHead(),null);
							else{
								Mail ciscoManagerMail=services.getCiscoManagerMailFromLaptopId(nonfinallaptopbean.getLaptopId()); 
								File dir = File.createTempFile(
										ciscoManagerMail.getMailName(), "");
								BufferedOutputStream stream = new BufferedOutputStream(
										new FileOutputStream(dir));
								IOUtils.copy(ciscoManagerMail.getMail().getBinaryStream(),
										stream);
								stream.close();
								mailSender.sendMailInTableFormatWithAttachment(subject, body, bean.getUserId()+ AFMSConstant.ciscodomain,
										MailSender.raiseNewHireLaptopRequest.getOdcHead(),null,dir.getAbsolutePath(),ciscoManagerMail.getMailName());
							}

							subject = "Acknowledgement : " + subject+"";
							String ack_body = MailUitility.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath+"ack_mail.html");
							ack_body = MailUitility.ack_mail(ack_body, nonfinallaptopbean, uniquelaptopid);

							mailSender.sendAcknowledmentMail(subject, ack_body,
									"afms@cisco.com", bean.getUserId()+AFMSConstant.ciscodomain, bean.getManagerId()+AFMSConstant.ciscodomain,uniquelaptopid);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			updateLaptopbean(request, nonfinalBean, services);
			ModelAndView modelAndView = new ModelAndView(new RedirectView(
					"/AFMS/laptop.html"));
			ra.addFlashAttribute("success", "Request updated,mail sent to ODC Head");
			ra.addFlashAttribute("laptop", laptopBean);
			return modelAndView;
		}  catch (DataAccessException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message","Please try again later");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("laptopPage", "message", "Please try again later");
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("laptopPage", "message", e1.getMessage());
		}
	}



}
