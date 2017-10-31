package com.zensar.afnls.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.LaptopBean;
import com.zensar.afnls.daoservices.AssetForecastingDAOServicesImpl;
import com.zensar.afnls.exception.AccountInactiveException;
import com.zensar.afnls.exception.AssetTrackNotUpdatedException;
import com.zensar.afnls.exception.IncorrectCredentialsException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;
import com.zensar.afnls.util.AFMSConstant;
import com.zensar.afnls.util.CryptograpgyUtility;
import com.zensar.afnls.util.MailSender;
import com.zensar.afnls.util.MailUitility;

@Controller
public class LaptopApproveController {

	@Autowired
	AssetForecastingSystemServicesImpl services;

	@Autowired
	private AssetForecastingDAOServicesImpl daoServicesImpl;

	@RequestMapping(value = "/raiseNewHireLaptopRequestApprove.html")
	public void approveLaptopNewHire(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse rs) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {

		String laptopid = request.getParameter("laptopid");

		laptopid = CryptograpgyUtility.decrypt(laptopid);
		final String delaptopid = laptopid;
		LaptopBean b = new LaptopBean();
		b.setLaptopId(Integer.valueOf(laptopid));
		b.setRequestStatus("A");
		try {
			if(services.getlaptopStatus(b.getLaptopId())){
				if (services.updatelaptopstatus(b)) {
					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(new Runnable() {
						public void run() {
							LaptopBean lpbean;
							try {
								lpbean = services.getLaptopsbylaptopid(Integer
										.valueOf(delaptopid));
								final String []ccarrayList = new String[2];


								String mgrId = services.getEmployeeId(delaptopid);
								ccarrayList[1]= daoServicesImpl.getEmployeeFromUserId(new EmployeeBean(mgrId)).getManagerId()+AFMSConstant.ciscodomain;
								mgrId += AFMSConstant.ciscodomain;
								ccarrayList[0] =mgrId;

								MailSender mailSender = new MailSender();
								String subject = "Laptop Request For "
										+ lpbean.getOwnerName()
										+ " for Project "
										+ lpbean.getProjectName()
										+ " Is Approved From "
										+ MailSender.raiseNewHireLaptopRequest
										.getODCName();

								String body = MailUitility
										.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
												+ "ApprovefromODC.html");
								body=MailUitility.ApprovedAndRejectMailTemplate(body,lpbean);
								mailSender.sendMailInTableFormat(subject, body,	MailSender.raiseNewHireLaptopRequest.getOdcHead(),
										MailSender.raiseNewHireLaptopRequest.getAmitemail(), ccarrayList);
							} catch (DataAccessException | NumberFormatException
									| SQLException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} 
						}
					});
					// subject="Acknowledgement : "+subject;
					// body="New Hire Laptop Request sent to avinash and cc "+bean.getManagerId()+" For "+laptopBean.getOwnerName()+" Of "+laptopBean.getProjectName();
					// mailSender.sendAcknowledmentMail(subject,
					// body,bean.getUserId(), "vanaik","vanaik");
					try{
						PrintWriter rt = rs.getWriter();
						rt.print("Laptop request approved");
						request.setAttribute("message", "Laptop request approved");

					}catch(Exception e){}

				}}else{
					PrintWriter rt = rs.getWriter();
					rt.print("Laptop Request is closed");
					request.setAttribute("message", "Laptop Request closed");
				}

		} catch (ServicesNotAvailableException | AssetTrackNotUpdatedException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}




	@RequestMapping(value = "/raiseNewHireLaptopRequestReject.html")
	public void rejectLaptopNewHire(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse rs) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {

		String laptopid = request.getParameter("laptopid");

		laptopid = CryptograpgyUtility.decrypt(laptopid);

		LaptopBean b = new LaptopBean();
		b.setLaptopId(Integer.valueOf(laptopid));
		b.setRequestStatus("R");
		final String deLaptopid = laptopid;
		try {
			if(services.getlaptopStatus(b.getLaptopId())){
				final	String[]ccarrayList = new String[2];
				if (services.updatelaptopstatus(b)) {
					Executor executor = Executors.newSingleThreadExecutor();
					executor.execute(new Runnable() {
						public void run() {
							try {
								LaptopBean lpbean = services
										.getLaptopsbylaptopid(Integer
												.valueOf(deLaptopid));
								String mgrId = services.getEmployeeId(deLaptopid);
								ccarrayList[1]= daoServicesImpl.getEmployeeFromUserId(new EmployeeBean(mgrId)).getManagerId()+AFMSConstant.ciscodomain;
								mgrId += AFMSConstant.ciscodomain;
								ccarrayList[0] =mgrId;
								MailSender mailSender = new MailSender();
								String subject = MailSender.raiseNewHireLaptopRequest.getODCName()+" has Rejected "+ lpbean.getOwnerName()+ "'s Laptop Request for Project "+ lpbean.getProjectName();

								String body = MailUitility
										.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
												+ "rejectfromODC.html");
								body=MailUitility.ApprovedAndRejectMailTemplate(body,lpbean);
								mailSender.sendMailInTableFormat(subject, body,
										MailSender.raiseNewHireLaptopRequest
										.getOdcHead(),
										MailSender.raiseNewHireLaptopRequest
										.getAmitemail(), ccarrayList);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});
					// subject="Acknowledgement : "+subject;
					// body="New Hire Laptop Request sent to avinash and cc "+bean.getManagerId()+" For "+laptopBean.getOwnerName()+" Of "+laptopBean.getProjectName();
					// mailSender.sendAcknowledmentMail(subject,
					// body,bean.getUserId(), "vanaik","vanaik");
					PrintWriter rt = rs.getWriter();
					rt.print("Laptop Transfer request Reject");
					request.setAttribute("message", "Laptop request rejected");
				}
			}else{
				PrintWriter rt = rs.getWriter();
				rt.print("Laptop Request is closed");
				request.setAttribute("message", "Laptop Request closed");
			}

		} catch (ServicesNotAvailableException | AssetTrackNotUpdatedException e) {
			request.setAttribute("message", "Laptop Request is closed and can't be Modified");
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch(Exception e)
		{
			PrintWriter rt = rs.getWriter();
			//rt.print("Laptop Request is closed");
			request.setAttribute("message", "technical error is occured.");
		}

	}

	@RequestMapping(value = "/raiseNewHireLaptopRequestMoreInfo.html")
	public ModelAndView moreInfoLaptopNewHire(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse rs) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			String laptopid1 = request.getParameter("laptopid");
			System.out.println("moreInfoLaptopNewHire");
			laptopid1 = CryptograpgyUtility.decrypt(laptopid1);
			if(services.getlaptopStatus(Integer
					.valueOf(laptopid1))){
				request.setAttribute("laptopid", request.getParameter("laptopid"));

				LaptopBean lpbean = services
						.getLaptopsbylaptopid(Integer
								.valueOf(laptopid1));
				request.setAttribute("lpbean", lpbean);
				return new ModelAndView("moreinfo");
			}else{
				PrintWriter rt = rs.getWriter();
				rt.print("Laptop Request is closed");
				request.setAttribute("message", "Laptop request closed");
			}
		} catch (DataAccessException | NumberFormatException | SQLException e) {
			e.printStackTrace();
		} catch (ServicesNotAvailableException e) {
			e.printStackTrace();
		} catch (AssetTrackNotUpdatedException e) {
			e.printStackTrace();
		}
		return null;
		/*

		request.setAttribute("laptopid", request.getParameter("laptopid"));
		return new ModelAndView("moreinfo");
		String laptopid = request.getParameter("laptopid");

		laptopid = CryptograpgyUtility.decrypt(laptopid);

		LaptopBean b = new LaptopBean();
		b.setLaptopId(Integer.valueOf(laptopid));
		b.setRequestStatus("M");
		final String deLaptopid = laptopid;
		try {

			if (services.updatelaptopstatus(b)) {
				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {
						try {
							LaptopBean lpbean = services
									.getLaptopsbylaptopid(Integer
											.valueOf(deLaptopid));
							String mgrId = services.getEmployeeId(deLaptopid);
							mgrId += AFMSConstant.ciscodomain;

							MailSender mailSender = new MailSender();
							String subject = MailSender.raiseNewHireLaptopRequest
									.getODCName()
									+ " Ask More information for "
									+ "Laptop Request  "
									+ lpbean.getOwnerName()
									+ " for Project "
									+ lpbean.getProjectName();

							String body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "ApprovefromODC.html");
							mailSender.sendMailInTableFormat(subject, body,
									MailSender.raiseNewHireLaptopRequest
											.getOdcHead(),
									MailSender.raiseNewHireLaptopRequest
											.getAmitemail(), mgrId);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				// subject="Acknowledgement : "+subject;
				// body="New Hire Laptop Request sent to avinash and cc "+bean.getManagerId()+" For "+laptopBean.getOwnerName()+" Of "+laptopBean.getProjectName();
				// mailSender.sendAcknowledmentMail(subject,
				// body,bean.getUserId(), "vanaik","vanaik");
				PrintWriter rt = rs.getWriter();
				rt.print("Require More Information");
			}

		} catch (ServicesNotAvailableException | AssetTrackNotUpdatedException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		 */}

	
	@RequestMapping(value = "/raiseNewHireLaptopRequestCommentsApproval.html")
	public ModelAndView commentsLaptopNewHire(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse rs) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			String laptopid1 = request.getParameter("laptopid");
			System.out.println("commentsLaptopNewHire");
			laptopid1 = CryptograpgyUtility.decrypt(laptopid1);
			System.out.println("laptop ID"+laptopid1);
			if(services.getlaptopStatus(Integer.valueOf(laptopid1))){
				request.setAttribute("laptopid", request.getParameter("laptopid"));

				LaptopBean lpbean = services
						.getLaptopsbylaptopid(Integer
								.valueOf(laptopid1));
				request.setAttribute("lpbean", lpbean);
				return new ModelAndView("LaptopApproveComments");
			}else{
				
			}
		} catch (DataAccessException | NumberFormatException | SQLException e) {
			e.printStackTrace();
		} catch (ServicesNotAvailableException e) {
			e.printStackTrace();
		} catch (AssetTrackNotUpdatedException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/approvalComments.html")
	public ModelAndView ApprovalComments(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse rs) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		final	String add_info=request.getParameter("moreinfo").trim();
		String laptopid = request.getParameter("laptopid");

		laptopid = CryptograpgyUtility.decrypt(laptopid);

		LaptopBean b = new LaptopBean();
		b.setLaptopId(Integer.valueOf(laptopid));
		b.setRequestStatus("A");
		b.setAdd_info(add_info);
		final String deLaptopid = laptopid;
		try {

			if (services.updatelaptopstatus_MoreInfo(b)) {
				final String[] ccarrayList = new String[2];
				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {
						try {
							LaptopBean lpbean = services
									.getLaptopsbylaptopid(Integer
											.valueOf(deLaptopid));

							String mgrId = services.getEmployeeId(deLaptopid);
							ccarrayList[1]= daoServicesImpl.getEmployeeFromUserId(new EmployeeBean(mgrId)).getManagerId()+AFMSConstant.ciscodomain;
							mgrId += AFMSConstant.ciscodomain;
							ccarrayList[0] =mgrId;
							MailSender mailSender = new MailSender();
							String subject ="Laptop Request  approved for "
									+ lpbean.getOwnerName()
									+ " ("
									+ lpbean.getProjectName()+")";

							String body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "raiseNewHireLaptopRequestComments.html");
							body =  MailUitility.commentsBody(body,lpbean ,add_info);
							mailSender.sendMailInTableFormat(subject, body,
									MailSender.raiseNewHireLaptopRequest
									.getOdcHead(),
									MailSender.raiseNewHireLaptopRequest
									.getAmitemail(), ccarrayList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				// subject="Acknowledgement : "+subject;
				// body="New Hire Laptop Request sent to avinash and cc "+bean.getManagerId()+" For "+laptopBean.getOwnerName()+" Of "+laptopBean.getProjectName();
				// mailSender.sendAcknowledmentMail(subject,
				// body,bean.getUserId(), "vanaik","vanaik");
				/*	PrintWriter rt = rs.getWriter();
				rt.f
				rt.print();*/


			}

		} catch (ServicesNotAvailableException | AssetTrackNotUpdatedException e) {
			e.printStackTrace();
			return new	ModelAndView("../indexPage", "message", "Please Try Again Later");
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new	ModelAndView("../indexPage", "message", "Please Try Again Later");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new	ModelAndView("../indexPage", "message", "Please Try Again Later");
		}
		return new	ModelAndView("../indexPage", "message", "Laptop request approved with additional information");
	}


	@RequestMapping(value = "/NeedAdditionalInfo.html")
	public ModelAndView NeedAdditionalInfo(HttpServletRequest request,
			RedirectAttributes ra, HttpServletResponse rs) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {


		final	String add_info=request.getParameter("moreinfo").trim();
		String laptopid = request.getParameter("laptopid");

		laptopid = CryptograpgyUtility.decrypt(laptopid);

		LaptopBean b = new LaptopBean();
		b.setLaptopId(Integer.valueOf(laptopid));
		b.setRequestStatus("M");
		b.setAdd_info(add_info);
		final String deLaptopid = laptopid;
		try {

			if (services.updatelaptopstatus_MoreInfo(b)) {
				final String[] ccarrayList = new String[2];
				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {
						try {
							LaptopBean lpbean = services
									.getLaptopsbylaptopid(Integer
											.valueOf(deLaptopid));

							String mgrId = services.getEmployeeId(deLaptopid);
							ccarrayList[1]= daoServicesImpl.getEmployeeFromUserId(new EmployeeBean(mgrId)).getManagerId()+AFMSConstant.ciscodomain;
							mgrId += AFMSConstant.ciscodomain;
							ccarrayList[0] =mgrId;
							MailSender mailSender = new MailSender();
							String subject = MailSender.raiseNewHireLaptopRequest
									.getODCName()
									+ " Ask More information for "
									+ "Laptop Request  "
									+ lpbean.getOwnerName()
									+ " for Project "
									+ lpbean.getProjectName();

							String body = MailUitility
									.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
											+ "additional_info_template.html");
							body =  MailUitility.commentsBody(body,lpbean ,add_info);
							mailSender.sendMailInTableFormat(subject, body,
									MailSender.raiseNewHireLaptopRequest
									.getOdcHead(),
									MailSender.raiseNewHireLaptopRequest
									.getAmitemail(), ccarrayList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				// subject="Acknowledgement : "+subject;
				// body="New Hire Laptop Request sent to avinash and cc "+bean.getManagerId()+" For "+laptopBean.getOwnerName()+" Of "+laptopBean.getProjectName();
				// mailSender.sendAcknowledmentMail(subject,
				// body,bean.getUserId(), "vanaik","vanaik");
				/*	PrintWriter rt = rs.getWriter();
				rt.f
				rt.print();*/


			}

		} catch (ServicesNotAvailableException | AssetTrackNotUpdatedException e) {
			return new	ModelAndView("../indexPage", "message", "Please Try Again Later");
		} catch (DataAccessException e) {
			return new	ModelAndView("../indexPage", "message", "Please Try Again Later");
		} catch (NumberFormatException e) {
			return new	ModelAndView("../indexPage", "message", "Please Try Again Later");
		}
		return new	ModelAndView("../indexPage", "message", "Mail sent to respective Manager for additional information");
	}




}
