package com.zensar.afnls.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

import com.zensar.afnls.beans.AssetBean;
import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.LaptopBean;
import com.zensar.afnls.beans.ValidateEmployee;
import com.zensar.afnls.exception.AssetTrackNotAddedException;
import com.zensar.afnls.exception.AssetTrackNotDeletedException;
import com.zensar.afnls.exception.AssetTrackNotFetchedException;
import com.zensar.afnls.exception.AssetTrackNotUpdatedException;
import com.zensar.afnls.exception.ConnectionNotEstablishedException;
import com.zensar.afnls.exception.EmployeeNotUpdatedException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;
import com.zensar.afnls.services.LoginServiceForAFMS;
import com.zensar.afnls.util.AFMSConstant;
import com.zensar.afnls.util.LDAP;
import com.zensar.afnls.util.MailSender;
import com.zensar.afnls.util.MailUitility;

@Controller
public class AssetController {
	@Autowired
	AssetForecastingSystemServicesImpl services;
	@Autowired
	LoginServiceForAFMS loginservices;


	public AssetForecastingSystemServicesImpl getServices() {
		return services;
	}

	private AssetBean getAssetBeanFromRequest(HttpServletRequest request) {
		AssetBean assetBean = new AssetBean();
		String projectId = request.getParameter("projectId");
		if (projectId != null)
			assetBean.setProjectId(Integer.parseInt(request
					.getParameter("projectId")));
		assetBean.setProjectName(request.getParameter("projectName"));
		assetBean.setCurrentHeadCount(Integer.parseInt(request
				.getParameter("currentHeadCount")));
		assetBean.setGrowthCount(Integer.parseInt(request
				.getParameter("growthCount")));
		assetBean.setGrowthStatus(request.getParameter("growthStatus"));
		assetBean.setCiscoManagerId(request.getParameter("ciscoManagerId"));
		assetBean.setCiscoManagerName(request.getParameter("ciscoManagerName"));
		assetBean.setQuarter(request.getParameter("quarter"));
		assetBean.setProjectLocation(request.getParameter("projectLocation"));
		assetBean.setProjectManager(request.getParameter("projectManager"));
		assetBean.setProgramManager(request.getParameter("programManager"));
		assetBean.setDeliveryHead(request.getParameter("deliveryHead"));
		System.out.println("asset "+assetBean);
		return assetBean;
	}

	@RequestMapping(value = "/addAssetTrack.html", method = RequestMethod.POST)
	public ModelAndView addAsset(HttpServletRequest request,
			RedirectAttributes ra) {
		AssetBean assetBean = getAssetBeanFromRequest(request);
		EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute(
				"employee");
		LDAP ldap = new LDAP();
		boolean status = false;
		try {
			if (bean.getDesignation().equalsIgnoreCase("Project Manager")) {
				status = services.addAssetTrack(assetBean, bean.getUserId());
			} else if (bean.getDesignation()
					.equalsIgnoreCase("Program Manager")) {
				// String
				// names=ldap.getEmpNames(request.getParameter("projectManager"));
				// assetBean.setProjectManager(names);
				status = services.addAssetTrack(assetBean,
						request.getParameter("projectManager"));
			} else if (bean.getDesignation().equalsIgnoreCase("Delivery Head")) {
				// String
				// names=ldap.getEmpNames(request.getParameter("projectManager"));
				// assetBean.setProjectManager(names);
				status = services.addAssetTrack(assetBean,
						request.getParameter("projectManager"));
			} else if (bean.getDesignation().equalsIgnoreCase("PCO Team")) {
				// String
				// names=ldap.getEmpNames(request.getParameter("projectManager"));
				// assetBean.setProjectManager(names);
				status = services.addAssetTrack(assetBean,
						request.getParameter("projectManager"));
			}
			if (status) {
				if (bean.getDesignation().equalsIgnoreCase("Project Manager"))
					services.updateassetCheck(
							bean,
							(Integer) request.getSession().getAttribute("year"),
							(String) request.getSession().getAttribute(
									"quarter"), "invalidated");
				else
					services.updateassetCheck(
							new EmployeeBean(request
									.getParameter("projectManager")),
									(Integer) request.getSession().getAttribute("year"),
									(String) request.getSession().getAttribute(
											"quarter"), "invalidated");
				request.getSession().setAttribute("employee",
						services.getUpdatedEmployeeBean(bean));
				ModelAndView modelAndView = new ModelAndView(new RedirectView(
						"/AFMS/asset.html"));
				ra.addFlashAttribute("asset", assetBean);
				ra.addFlashAttribute("success", "added");
				return modelAndView;
			}
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotAddedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (EmployeeNotUpdatedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		}
		return new ModelAndView("homePage", "message", "Project track not added");
	}

	@RequestMapping(value = "/updateAssetTrack.html", method = RequestMethod.POST)
	public ModelAndView updateAsset(HttpServletRequest request,
			RedirectAttributes ra) {
		AssetBean assetBean = getAssetBeanFromRequest(request);
		EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute(
				"employee");
		String previousQuarter = request.getParameter("previousquarter");
		int year = (int) request.getSession().getAttribute("year");
		String currentQuarter = (String) request.getSession().getAttribute(
				"quarter");
		boolean status = false;
		try {
			if (bean.getDesignation().equalsIgnoreCase("Project Manager")) {
				if (previousQuarter.equalsIgnoreCase(currentQuarter))
					status = services.updateAssetTrack(assetBean);
				else
					status = services
					.addAssetTrack(assetBean, bean.getUserId());
			} else if (bean.getDesignation()
					.equalsIgnoreCase("Program Manager")) {
				try {
					assetBean = services.getAssetByAssetId(Integer
							.valueOf(assetBean.getProjectId()));
				} catch (AssetTrackNotFetchedException e) {
					e.printStackTrace();
				}
				assetBean.setGrowthCount(Integer.parseInt(request
						.getParameter("growthCount")));
				assetBean.setCurrentHeadCount(Integer.parseInt(request
						.getParameter("currentHeadCount")));

				services.updateAssetTrack(assetBean);
				status = true;
			} else if (bean.getDesignation().equalsIgnoreCase("Delivery Head")) {
				status = services.updateAssetTrack(assetBean);

			} else if (bean.getDesignation().equalsIgnoreCase("PCO Team")) {
				status = services.updateAssetTrack(assetBean);

			}
			if (status) {
				if (bean.getDesignation().equalsIgnoreCase("Project Manager"))
					services.updateassetCheck(
							bean,
							(Integer) request.getSession().getAttribute("year"),
							(String) request.getSession().getAttribute(
									"quarter"), "invalidated");
				else
					services.updateassetCheck(
							new EmployeeBean(request.getParameter("userId")),
							(Integer) request.getSession().getAttribute("year"),
							(String) request.getSession().getAttribute(
									"quarter"), "invalidated");
				request.getSession().setAttribute("employee",
						services.getUpdatedEmployeeBean(bean));
				ModelAndView modelAndView = new ModelAndView(new RedirectView(
						"/AFMS/asset.html"));
				ra.addFlashAttribute("asset", assetBean);
				ra.addFlashAttribute("success", "updated");
				ra.addFlashAttribute("message", " Track updated successfully");
				return modelAndView;
			}
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotUpdatedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (EmployeeNotUpdatedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotAddedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		}
		return new ModelAndView("homePage", "message", "Asset Not Added");
	}

	@RequestMapping(value = "/savetrack.html", method = RequestMethod.POST)
	public ModelAndView savetrack(HttpServletRequest request,
			RedirectAttributes ra) {
		System.out.println("Saving Or Not");
		EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute(
				"employee");

		String[] projectId = request.getParameterValues("projectId");
		String currentHeadCount[] = request
				.getParameterValues("currentHeadCount");
		String growthCount[] = request.getParameterValues("growthCount");
		String quarter[] = request.getParameterValues("quarter");
		boolean status = false;
		String previousQuarter = request.getParameter("previousquarter");
		int year = (int) request.getSession().getAttribute("year");
		String currentQuarter = (String) request.getSession().getAttribute(
				"quarter");
		if (quarter != null && quarter.length >= 0)
			status = true;
		HashMap<Integer, AssetBean> temp = new HashMap<Integer, AssetBean>(50);
		List<AssetBean> assetbean = bean.getAssetBeans();
		Iterator<AssetBean> itr = assetbean.iterator();

		while (itr.hasNext()) {
			AssetBean asset = itr.next();
			temp.put(asset.getProjectId(), asset);
		}
		try {

			if (status) {
				for (int i = 0; i < projectId.length; i++) {
					if (temp.containsKey(Integer.valueOf(projectId[i]))) {
						AssetBean updatebean = temp.get(Integer
								.valueOf(projectId[i]));
						System.out.println(updatebean);
						updatebean.setGrowthCount(Integer
								.valueOf(growthCount[i]));
						updatebean.setGrowthStatus(checkgrowthordecline(Integer
								.valueOf(growthCount[i])));
						updatebean.setCurrentHeadCount((Integer
								.valueOf(currentHeadCount[i])));
						updatebean.setQuarter(currentQuarter);
						services.addAssetTrack(updatebean, bean.getUserId());
					}
				}

			} else {
				for (int i = 0; i < projectId.length; i++) {
					services.updateAssetbyId(new AssetBean(
							checkgrowthordecline(Integer
									.valueOf(growthCount[i])), Integer
									.valueOf(growthCount[i]), Integer.valueOf(
											projectId[i]).intValue()));
				}

			}
			request.getSession().setAttribute("employee",
					services.getUpdatedEmployeeBean(bean));
			if(request.getParameter("submitSave").equalsIgnoreCase("Save")){
				ModelAndView view= new ModelAndView(new RedirectView("/AFMS/asset.html"));
				ra.addFlashAttribute("success","Project track updated");
				return view;
			}
			else{
				return submitQuarterlyReports(request,ra);
			}
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotUpdatedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (EmployeeNotUpdatedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotAddedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		}

	}

	@RequestMapping(value = "/saveProjectManagerTrack.html", method = RequestMethod.POST)
	public ModelAndView saveProjectManagerTrack(HttpServletRequest request,
			RedirectAttributes ra) {
		final EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute(
				"employee");

		String[] projectId = request.getParameterValues("projectId");
		String currentHeadCount[] = request
				.getParameterValues("currentHeadCount");
		String growthCount[] = request.getParameterValues("growthCount");
		String quarter[] = request.getParameterValues("quarter");
		boolean status = false;

		String previousQuarter = request.getParameter("previousquarter");
		final int year = (int) request.getSession().getAttribute("year");
		final String currentQuarter = (String) request.getSession().getAttribute("quarter");
		if (quarter != null && quarter.length >= 0)
			status = true;
		HashMap<Integer, AssetBean> temp = new HashMap<Integer, AssetBean>(50);
		String userId=request.getParameter("empUserId");
		EmployeeBean bean2=null;
		for (EmployeeBean emp : bean.getEmployeeBeans()) {
			if (emp.getUserId().equalsIgnoreCase(userId)) {
				bean2=emp;
			}
		}
		List<AssetBean> assetbean =bean2.getAssetBeans();
		Iterator<AssetBean> itr = assetbean.iterator();

		while (itr.hasNext()) {
			AssetBean asset = itr.next();
			temp.put(asset.getProjectId(), asset);
		}
		try {
			if (status) {
				for (int i = 0; i < projectId.length; i++) {
					if (temp.containsKey(Integer.valueOf(projectId[i]))) {
						AssetBean updatebean = temp.get(Integer
								.valueOf(projectId[i]));
						updatebean.setGrowthCount(Integer
								.valueOf(growthCount[i]));
						updatebean.setGrowthStatus(checkgrowthordecline(Integer
								.valueOf(growthCount[i])));
						updatebean.setCurrentHeadCount((Integer
								.valueOf(currentHeadCount[i])));
						updatebean.setQuarter(currentQuarter);
						services.addAssetTrack(updatebean,userId);
					}
				}

			} else {
				for (int i = 0; i < projectId.length; i++) {
					services.updateAssetbyId(new AssetBean(
							checkgrowthordecline(Integer
									.valueOf(growthCount[i])), Integer
									.valueOf(growthCount[i]), Integer.valueOf(
											projectId[i]).intValue()));
				}
			}
			request.getSession().setAttribute("employee",services.getUpdatedEmployeeBean(bean));
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotUpdatedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (EmployeeNotUpdatedException e) {
			e.printStackTrace();
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotAddedException e) {
			e.printStackTrace();
			return new ModelAndView("homePage", "message", e.getMessage());
		}
		final EmployeeBean employeeBean=bean2;
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(new Runnable() {
			public void run() {
				try {

					MailSender mailSender = new MailSender();
					String subject = bean.getEmployeeName()
							+ " Updated Project Track for " + currentQuarter + "/ "
							+ year;

					String body = MailUitility
							.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
									+ "updateProjectTrackForPMTemplate.html");
					body = MailUitility.PGMUpdatedProjectTracks(body,
							bean,employeeBean,currentQuarter, year+"");
					mailSender.sendquartermails(subject, body, bean.getUserId()
							+ AFMSConstant.ciscodomain, employeeBean.getUserId()
							+ AFMSConstant.ciscodomain, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		if(request.getParameter("validatePM").equalsIgnoreCase("Save")){
			ModelAndView view= new ModelAndView(new RedirectView("/AFMS/asset.html"));
			ra.addFlashAttribute("success","Project track updated");
			return view;
		}
		else{
			return validateEmployee(request,userId,ra);
		}
	}

	public static String checkgrowthordecline(int number) {
		try {
			if (number >= 0) {
				return "growth";
			} else {
				return "decline";
			}
		} catch (Exception e) {
		}
		return "";
	}

	@RequestMapping(value = "/deleteAssetTrack.html", method = RequestMethod.POST)
	public ModelAndView deleteAssetTrack(HttpServletRequest request,
			RedirectAttributes ra) {
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		try {
			services.deleteAssetTrack(projectId);
			request.getSession().setAttribute(
					"employee",
					services.getUpdatedEmployeeBean((EmployeeBean) request
							.getSession().getAttribute("employee")));
			ModelAndView modelAndView = new ModelAndView(new RedirectView(
					"/AFMS/asset.html"));
			ra.addFlashAttribute("success", "Project track deleted");
			return modelAndView;
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotDeletedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (EmployeeNotUpdatedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		}
	}

	/*
	 * @RequestMapping(value="/sendReport.html",method=RequestMethod.POST)
	 * public ModelAndView sendReportToODCHead(HttpServletRequest request){ int
	 * year=Integer.parseInt(request.getParameter("reportYear")); String
	 * quarter=request.getParameter("reportQuarter"); try {
	 * if(services.sendMailToCiscoODCHead
	 * (year,quarter,(EmployeeBean)request.getSession
	 * ().getAttribute("employee"))){ return new
	 * ModelAndView("homePage","message","Report Sent"); } } catch
	 * (ServicesNotAvailableException e) { return new
	 * ModelAndView("homePage","message",e.getMessage()); } catch
	 * (AssetTrackNotFetchedException e) { return new
	 * ModelAndView("homePage","message",e.getMessage()); } return new
	 * ModelAndView("homePage","message","Error Occured,Report Not Sent"); }
	 */

	@RequestMapping(value = "/downloadExcel.html", method = RequestMethod.POST)
	public ModelAndView exportReport(HttpServletRequest request,
			HttpServletResponse response) {
		EmployeeBean employeeBean = (EmployeeBean) request.getSession(false)
				.getAttribute("employee");
		String projectManager = null;
		String programManager = null;
		String deliveryHead = null;
		int year = Integer.parseInt(request.getParameter("exportYear"));
		String quarter = request.getParameter("exportQuarter");
		List<AssetBean> assetBeans = null;
		try {
			if (employeeBean.getDesignation().equalsIgnoreCase(
					"Project Manager")) {
				assetBeans = services.getAssetsAsPerEmployeeRequest(
						employeeBean, year, quarter);
			} else if (employeeBean.getDesignation().equalsIgnoreCase(
					"Program Manager")) {
				projectManager = request.getParameter("projectManager");
				assetBeans = services.getAssetsForProgramManager(employeeBean,
						projectManager, year, quarter);
			} else if (employeeBean.getDesignation().equalsIgnoreCase(
					"Delivery Head")) {
				projectManager = request.getParameter("projectManager");
				programManager = request.getParameter("programManager");
				assetBeans = services.getAssetsForDeliveryHead(employeeBean,
						programManager, projectManager, year, quarter);
			} else if (employeeBean.getDesignation().equalsIgnoreCase(
					"Cisco ODC Head")) {
				projectManager = request.getParameter("projectManager");
				programManager = request.getParameter("programManager");
				deliveryHead = request.getParameter("deliveryHead");
				assetBeans = services.getAssetsForCiscoODCHead(employeeBean,
						deliveryHead, programManager, projectManager, year,
						quarter);
			} else if (employeeBean.getDesignation().equalsIgnoreCase(
					"PCO Team")) {
				projectManager = request.getParameter("projectManager");
				programManager = request.getParameter("programManager");
				deliveryHead = request.getParameter("deliveryHead");
				assetBeans = services.getAssetsForCiscoODCHead(employeeBean,
						deliveryHead, programManager, projectManager, year,
						quarter);
			} else if (employeeBean.getDesignation().equalsIgnoreCase("Admin")) {
				assetBeans = services.getAssetsForCiscoODCHead(employeeBean,
						deliveryHead, programManager, projectManager, year,
						quarter);
			} else
				assetBeans = null;
			if (assetBeans == null)
				throw new AssetTrackNotFetchedException();
			if (assetBeans.get(0) == null) {
			}
		} catch (ServicesNotAvailableException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (AssetTrackNotFetchedException e) {
			return new ModelAndView("homePage", "message", e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			return new ModelAndView("homePage", "message",
					"No tracks found, please select appropriate year and quarter");
		}
		response.setContentType("application/vnd.ms-excel");

		String fileName = "Reports_";
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + "_" + quarter + "_" + year + ".xls");

		return new ModelAndView("assetExcelReport", "assetsOfEmployee",
				assetBeans);
	}

	@RequestMapping(value = "/getManagerName.html", method = RequestMethod.GET)
	public @ResponseBody String getManagerName(HttpServletRequest request,
			HttpServletResponse response) {

		if (InitiliazeResourceAtServerStartup.empIdEpmname.get(request
				.getParameter("empId")) != null) {
			return InitiliazeResourceAtServerStartup.empIdEpmname.get(
					request.getParameter("empId")).toString();
		} else {
			String managerName = "";
			try {
				LDAP ldap = new LDAP();
				managerName = ldap.getEmpNames(request.getParameter("empId"));
				if (null == managerName
						|| (managerName != null && managerName.contains("null")))
					return "Invalid CEC ID";
			} catch (Exception e) {

			}
			return managerName;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllManagerName.html", method = RequestMethod.GET)
	public @ResponseBody String getAllManagerName(HttpServletRequest request,
			HttpServletResponse response) {
		LDAP ldap = new LDAP();
		String[] employeeNames = new String[2];
		String employeeName = "";
		try {
			employeeName=ldap.getEmpNames(request.getParameter("programManagerId"));
		} catch (ConnectionNotEstablishedException e) {
			employeeName=e.getMessage();
		}
		employeeNames[0] = employeeName;
		try {
			employeeName = ldap.getEmpNames(request.getParameter("deliveryHeadId"));
		} catch (ConnectionNotEstablishedException e) {
			employeeName=e.getMessage();
		}
		employeeNames[1] = employeeName;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("projectManager", employeeNames[0]);
		jsonObject.put("programManager", employeeNames[1]);
		return jsonObject.toJSONString();
	}

	@RequestMapping(value = "/checkProjectName.html", method = RequestMethod.GET)
	public @ResponseBody String checkManagerName(HttpServletRequest request,
			HttpServletResponse response) {
		if (services.checkAssetPresent(request.getParameter("projectName"))) {
			return "true";
		}
		return "false";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAssetDetailsFromId.html", method = RequestMethod.GET)
	public @ResponseBody String getAssetDetailsFromId(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			AssetBean assetBean = services.getAssetByAssetId(Integer
					.parseInt(request.getParameter("assetId")));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("projectId", assetBean.getProjectId());
			jsonObject.put("projectName", assetBean.getProjectName());
			jsonObject.put("currentHeadCount", assetBean.getCurrentHeadCount());
			jsonObject.put("growthCount", assetBean.getGrowthCount());
			jsonObject.put("growthStatus", assetBean.getGrowthStatus());
			jsonObject.put("ciscoManagerId", assetBean.getCiscoManagerId());
			jsonObject.put("ciscoManagerName", assetBean.getCiscoManagerName());
			jsonObject.put("quarter", assetBean.getQuarter());
			jsonObject.put("projectLocation", assetBean.getProjectLocation());
			jsonObject.put("projectManager", assetBean.getProjectManager());
			jsonObject.put("programManager", assetBean.getProgramManager());
			jsonObject.put("deliveryHead", assetBean.getDeliveryHead());
			jsonObject.put("userId",
					services.getUserIdOfProjectId(assetBean.getProjectId()));
			return jsonObject.toJSONString();
		} catch (NumberFormatException e) {
			return null;
		} catch (ServicesNotAvailableException e) {
			return null;
		} catch (AssetTrackNotFetchedException e) {
			return null;
		}
	}

	@RequestMapping(value = "/submitQuarterlyReports.html", method = RequestMethod.GET)
	public ModelAndView submitQuarterlyReports(HttpServletRequest request,
			RedirectAttributes ra) {
		final EmployeeBean bean = (EmployeeBean) request.getSession()
				.getAttribute("employee");
		String assetValidation = services.addQuarterlyDetails(bean,
				(Integer) request.getSession().getAttribute("year"),
				(String) request.getSession().getAttribute("quarter"),
				"validated");
		final String quarter = (String) request.getSession().getAttribute(
				"quarter");
		final String year = String.valueOf(request.getSession().getAttribute(
				"year"));
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(new Runnable() {
			public void run() {
				try {

					MailSender mailSender = new MailSender();
					String subject = bean.getEmployeeName()
							+ " Submitted Project Track for " + quarter + "/ "
							+ year;

					String body = MailUitility
							.getEmailBody(InitiliazeResourceAtServerStartup.staticPagepath
									+ "Quarter_Submitted_template.html");
					String pgmName = UserManagementController
							.getManagerName(bean.getManagerId());
					body = MailUitility.convertquartlysubmittedrequest(body,
							bean, quarter, year, pgmName);
					mailSender.sendquartermails(subject, body, bean.getUserId()
							+ AFMSConstant.ciscodomain, bean.getManagerId()
							+ AFMSConstant.ciscodomain, null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		request.getSession().setAttribute("assetValidation", assetValidation);
		return new ModelAndView("homePage", "success",
				"Quarterly report sent to your manager");
	}

	@RequestMapping(value = "/finalizequarter.html", method = RequestMethod.GET)
	public ModelAndView finalizequarter(HttpServletRequest request,
			RedirectAttributes ra) {
		if (services.finalizeQuarters((EmployeeBean) request.getSession()
				.getAttribute("employee"), (Integer) request.getSession()
				.getAttribute("year"), (String) request.getSession()
				.getAttribute("quarter"))) {
			try {
				try {
					boolean validatefalg = true;
					boolean pmvalidatefalg = true;
					HashMap<String, ValidateEmployee> validatormap = new HashMap<String, ValidateEmployee>();
					List<ValidateEmployee> validatedEmployees = services
							.getvalidatedStatusforPGM(
									(EmployeeBean) request.getSession()
									.getAttribute("employee"),
									(Integer) request.getSession()
									.getAttribute("year"),
									(String) request.getSession().getAttribute(
											"quarter"));
					if (validatedEmployees.isEmpty()) {
						validatefalg = false;
					}
					Iterator itr = validatedEmployees.iterator();

					int counter = 0;
					while (itr.hasNext()) {
						ValidateEmployee validateemp = (ValidateEmployee) itr
								.next();
						validatormap.put(validateemp.getUserId(), validateemp);
						if ((validateemp.getPgm_status() == null || validateemp
								.getPgm_status().equalsIgnoreCase("N"))) {

							validatefalg = false;
						}

						if ((validateemp.getValidator() == null || validateemp
								.getValidator().equalsIgnoreCase("invalidated")))
							pmvalidatefalg = false;

					}
					request.getSession().setAttribute("employeeSubmission",
							validatormap);
					request.getSession().setAttribute("validatefalg",
							validatefalg);
					request.getSession().setAttribute("pmvalidatefalg",
							pmvalidatefalg);
				} catch (Exception e) {
				}
			} catch (Exception e) {
			}
			return new ModelAndView("homePage", "success",
					"Project tracks validated for current quarter");
		} else
			return new ModelAndView("homePage", "message", "Project tracks not validated for current quarter");
	}
	@RequestMapping(value="/validateEmployee.html")
	public ModelAndView validateEmployee(HttpServletRequest request,String userId,RedirectAttributes ra){
		EmployeeBean employeebean = new EmployeeBean();
		employeebean.setUserId(userId);
		EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
		int year=(Integer)request.getSession().getAttribute("year");
		System.out.println("Year "+year);
		String quarter=(String)request.getSession().getAttribute("quarter");
		System.out.println("Quarter "+quarter);
		services.addQuarterlyDetails(employeebean,year,quarter,"validated");
		/*services.addProjectTrackifDataNotSet(employeebean,Integer.valueOf(request.getSession().getAttribute("year").toString()).intValue(),
				request.getSession().getAttribute("quarter").toString());*/

		try{
			boolean validatefalg=true;
			boolean pmvalidatefalg=true;
			HashMap<String,ValidateEmployee>  validatormap = new HashMap<String,ValidateEmployee>();
			List<ValidateEmployee> validatedEmployees = services.getvalidatedStatusforPGM(bean,Integer.valueOf(request.getSession().getAttribute("year").toString()).intValue(),
					request.getSession().getAttribute("quarter").toString());
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
		finally{
			EmployeeBean	beanupdated =new EmployeeBean();
			beanupdated.setUserId(bean.getUserId());
			try {
				beanupdated = loginservices.getUpdatedEmployeeBean(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getSession().setAttribute("employee", beanupdated);
		}
		return new ModelAndView("homePage");
	}
}
