package com.zensar.afnls.services;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zensar.afnls.beans.*;
import com.zensar.afnls.daoservices.AssetForecastingDAOServicesImpl;
import com.zensar.afnls.exception.*;
import com.zensar.afnls.util.AFMSConstant;
import com.zensar.afnls.util.LDAP;

@Service
public class AssetForecastingSystemServicesImpl {
	@Autowired
	private AssetForecastingDAOServicesImpl daoServicesImpl;

	public EmployeeBean getEmployeeThroughLogin(EmployeeBean employeeBean) throws IncorrectCredentialsException,ServicesNotAvailableException, AccountInactiveException{
		EmployeeBean bean;
		try {
			bean = daoServicesImpl.getEmployeeFromCredentials(employeeBean);
			if(bean==null)	throw new IncorrectCredentialsException("Incorrect Credentials");
			if(bean.getStatus().equalsIgnoreCase("D"))	throw new AccountInactiveException("Account Inactive,Contact PCO Team");
			return bean;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new IncorrectCredentialsException("Incorrect Credentials");
		}catch(SQLException e){
			throw new ServicesNotAvailableException("Services not available");
		}

	}

	public LaptopBean getLaptopsbylaptopid(int laptopid) throws SQLException,DataAccessException{
		return daoServicesImpl.getLaptopsbylaptopid(laptopid);
	}
	public int getMaxbylaptopid() throws SQLException,DataAccessException{
		return daoServicesImpl.maxLaptopId();
	}
	public List<LaptopBean> getLaptopsPerEmployee(String employeeId) throws SQLException,DataAccessException{
		return daoServicesImpl.getLaptopsPerEmployee(employeeId);
	}
	public String getODCHeadUserId(){
		try {
			return daoServicesImpl.getODCUserId();
		} catch (DataAccessException e) {
			return null;
		}
	}
	public EmployeeBean getUpdatedEmployeeBean(EmployeeBean employeeBean) throws ServicesNotAvailableException, EmployeeNotUpdatedException{
		try{
			if(!employeeBean.getDesignation().equalsIgnoreCase(AFMSConstant.designatioarray[5]))
				return daoServicesImpl.getUpdatedEmployee(employeeBean);
			return daoServicesImpl.getUpdatedPCOEmployee(employeeBean);
		}catch (DataAccessException e) {
			throw new EmployeeNotUpdatedException("Cannot update employee");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public boolean updateEmployeeDetails(EmployeeBean employeeBean) throws EmployeeNotUpdatedException, ServicesNotAvailableException{
		try {
			return daoServicesImpl.updateEmployee(employeeBean);
		} catch (DataAccessException e) {
			throw new EmployeeNotUpdatedException("Employee details not updated");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public boolean deactivateEmoployee(String userId) throws EmployeeNotDeactivatedException, ServicesNotAvailableException{
		try {
			return daoServicesImpl.deactivateEmployee(userId);
		} catch (DataAccessException e) {
			throw new EmployeeNotDeactivatedException("Employee not deactivated");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	public boolean addAssetTrack(AssetBean assetBean,String userId) throws ServicesNotAvailableException, AssetTrackNotAddedException{
		try {
			/*Start by amslunk*/
			if(assetBean.getGrowthCount() >-1) assetBean.setGrowthStatus("growth");
			else  assetBean.setGrowthStatus("decline");
			/*End by amslunk*/
			if(daoServicesImpl.addAssetForEmployee(assetBean,userId))
				return true;
			//else throw new AssetTrackNotAddedException("Asset Was Not Added");
		}catch (DataAccessException e) {
			
			throw new AssetTrackNotAddedException("Project track was not added");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
		return false;
	}

	public boolean updateAssetbyId(AssetBean assetBean) throws ServicesNotAvailableException, AssetTrackNotUpdatedException{
		try {
			if(daoServicesImpl.updateAssetbyId(assetBean))
				return true;
			else throw new AssetTrackNotUpdatedException("Project track was not updated");
		}catch (DataAccessException e) {
			
			throw new AssetTrackNotUpdatedException("Project track was not updated");
		}
	}
	
	
	public boolean updateAssetTrack(AssetBean assetBean) throws ServicesNotAvailableException, AssetTrackNotUpdatedException{
		try {
			System.out.println(assetBean);
			/*Start by amslunk*/
			if(assetBean.getGrowthCount() >=0) assetBean.setGrowthStatus("growth");
			else  assetBean.setGrowthStatus("decline");
			/*End by amslunk*/
			if(daoServicesImpl.updateAssetForEmployee(assetBean))
				return true;
			else throw new AssetTrackNotUpdatedException("Project track was not updated");
		}catch (DataAccessException e) {
			
			throw new AssetTrackNotUpdatedException("Project track was not updated");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	


	public List<ITOCCList> getITOList() throws ServicesNotAvailableException, AssetTrackNotFetchedException{
		try {
			return daoServicesImpl.getITOList();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
		return null;
	}
	public boolean updatelaptopstatus(LaptopBean laptopBean) throws ServicesNotAvailableException, AssetTrackNotUpdatedException{
		try {
			if(daoServicesImpl.updatelaptopstatus(laptopBean))
				return true;
			else throw new AssetTrackNotUpdatedException("Laptop status not updated");
		}catch (DataAccessException e) {
			throw new AssetTrackNotUpdatedException("Project track was not updated");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	
	public boolean getlaptopStatus(int laptopBean) throws ServicesNotAvailableException, AssetTrackNotUpdatedException{
		try {
			if(daoServicesImpl.getlaptopStatus(laptopBean))
				return true;
			else throw new AssetTrackNotUpdatedException("Laptop request not found");
		}catch (DataAccessException e) {
			throw new AssetTrackNotUpdatedException("Laptop request not found");
		}
	}
	
	
	public boolean updatelaptopstatus_MoreInfo(LaptopBean laptopBean) throws ServicesNotAvailableException, AssetTrackNotUpdatedException{
		try {
			if(daoServicesImpl.updatelaptopstatus_MoreInfo(laptopBean))
				return true;
			else throw new AssetTrackNotUpdatedException("Laptop status not updated");
		}catch (DataAccessException e) {
			throw new AssetTrackNotUpdatedException("Project track was not updated");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	
	public boolean Closelaptoprequest(LaptopBean laptopBean) throws ServicesNotAvailableException, AssetTrackNotUpdatedException{
		try {
			if(daoServicesImpl.Closelaptoprequest(laptopBean))
				return true;
			else throw new AssetTrackNotUpdatedException("Laptop request not closed");
		}catch (DataAccessException e) {
			throw new AssetTrackNotUpdatedException("Laptop request not closed");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	

	public String getEmployeeId(String laptopid) throws SQLException,DataAccessException{
		return daoServicesImpl.getEmployeeId(laptopid);
	} 

	public boolean deleteAssetTrack(int projectId) throws ServicesNotAvailableException, AssetTrackNotDeletedException{
		try {
			return daoServicesImpl.deleteAssetForEmployee(projectId);
		}catch (DataAccessException e) {
			throw new AssetTrackNotDeletedException("Project track was not deleted");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public List<AssetBean> getAllEmployeeSpecificAssets(String userId) throws ServicesNotAvailableException, AssetTrackNotFetchedException{
		try {
			return daoServicesImpl.getAssetsAsPerEmployee(userId);
		} catch (DataAccessException e) {
			throw new AssetTrackNotFetchedException("Project tracks were not fetched");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public List<EmployeeBean> getEmployeesForPCOTeam() throws ServicesNotAvailableException, EmployeeNotFetchedException{
		try {
			return daoServicesImpl.getAllEmployeesForPCO();
		} catch (DataAccessException e) {
			throw new EmployeeNotFetchedException("Employees were not fetched");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public Laptopsmail getLaptopMails(Integer laptopid) throws DataAccessException, SQLException
	{
		return daoServicesImpl.getLaptopMails(laptopid);
	}

	public boolean addLaptopRequest(LaptopBean laptopBean,String employeeId,Laptopsmail ciscoManagerMail) throws ServicesNotAvailableException, LaptopNotAddedException{
		try {
			return daoServicesImpl.addLaptopRequestForEmployee(laptopBean, employeeId,ciscoManagerMail);
		}catch (DataAccessException e) {
			throw new LaptopNotAddedException("Early refresh laptop request was not added");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	public boolean addLaptopRequestNewHire(LaptopBean laptopBean,String userId) throws ServicesNotAvailableException, LaptopNotAddedException{
		try {
			return  daoServicesImpl.addLaptopRequestNewHireForEmployee(laptopBean, userId);
		} catch (DataAccessException e) {
e.printStackTrace();
			throw new LaptopNotAddedException("New hire laptop request was not added");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	public List<AssetBean> getAssetsAsPerEmployeeRequest(EmployeeBean employeeBean,int year,String quarter) throws ServicesNotAvailableException, AssetTrackNotFetchedException{
		try {
			return daoServicesImpl.getAssetsAsPerEmployeeRequest(employeeBean.getUserId(),year,quarter);
		} catch (DataAccessException e) {
			throw new AssetTrackNotFetchedException("No project tracks found for year "+year+ ((quarter.equalsIgnoreCase("ALL")) ?"":" quarter "+quarter));
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	public List<AssetBean> getAssetsForProgramManager(EmployeeBean employeeBean,String projectManager, int year, String quarter) throws AssetTrackNotFetchedException, ServicesNotAvailableException {
		try {
			return daoServicesImpl.getAssetsForProgramManager(employeeBean.getUserId(),projectManager,year,quarter);
		} catch (DataAccessException e) {
			throw new AssetTrackNotFetchedException("No project tracks found for year "+year+ ((quarter.equalsIgnoreCase("ALL")) ?"":" quarter "+quarter));
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}
	public List<AssetBean> getAssetsForDeliveryHead(EmployeeBean employeeBean,String programManager, String projectManager, int year,String quarter) throws AssetTrackNotFetchedException, ServicesNotAvailableException {
		try {
			return daoServicesImpl.getAssetsForDeliveryHead(employeeBean.getUserId(),programManager,projectManager,year,quarter);
		} catch (DataAccessException e) {
			throw new AssetTrackNotFetchedException("No project tracks found for year "+year+ ((quarter.equalsIgnoreCase("ALL")) ?"":" quarter "+quarter));
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public List<AssetBean> getAssetsForCiscoODCHead(EmployeeBean employeeBean,String deliveryHead, String programManager, String projectManager,int year, String quarter) throws AssetTrackNotFetchedException, ServicesNotAvailableException {
		try {
			return daoServicesImpl.getAssetsForCiscoODCHead(employeeBean.getUserId(),deliveryHead,programManager,projectManager,year,quarter);
		} catch (DataAccessException e) {
			throw new AssetTrackNotFetchedException("No project tracks found for year "+year+ ((quarter.equalsIgnoreCase("ALL")) ?"":" quarter "+quarter));
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public List<LaptopBean> getlaptopfiltervalue(String filtervalue) throws ServicesNotAvailableException, AssetTrackNotFetchedException, DataAccessException, SQLException{
		return daoServicesImpl.getLaptopsPerFilder(filtervalue);
	}


	public List<LaptopBean> getLaptopasPerDesg(String filtervalue,Integer designation) throws ServicesNotAvailableException, AssetTrackNotFetchedException, DataAccessException, SQLException{
		return daoServicesImpl.getLaptopasPerDesg(filtervalue,designation);
	}


	public boolean addEmployee(EmployeeBean employeeBean) throws ServicesNotAvailableException, EmployeeNotAddedException{
		try {
			return daoServicesImpl.insertEmployee(employeeBean);
		}catch (DataAccessException e) {
			System.out.println(e.getMessage());
			throw new EmployeeNotAddedException("Employee not added");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	/*	public boolean sendMailToCiscoODCHead(int year,String quarter,EmployeeBean employeeBean) throws ServicesNotAvailableException, AssetTrackNotFetchedException{
		List<AssetBean> assetBeans=null;
		assetBeans = getAssetsFromDaoAsPerEmployeeDesignation(employeeBean,year,quarter);
		MailSender mailSender=new MailSender();
		String subject="";
		String body="";
		if(quarter.equalsIgnoreCase("ALL")){
			subject=year+" Yearly Asset Report From "+employeeBean.getEmployeeName();
			body="<p style='color:blue;'><b>"+year+" Yearly Asset Report From "+employeeBean.getEmployeeName()+"</b><br/><br/> <table border='1'><tr><th>Project Name</th><th>Current Head Count</th><th>Growth Count</th><th>Growth Status</th>"+
					"<th>Cisco Manager Id</th><th>Cisco Manager Name</th><th>Quarter</th><th>Project Location</th></tr>";
		}
		else{
			subject="Asset Report From "+employeeBean.getEmployeeName()+"For "+year+" "+quarter;
			body="<p style='color:blue;'><b>Asset Report From "+employeeBean.getEmployeeName()+"For "+year+" "+quarter+"</b><br/><br/> <table border='1'><tr><th>Project Name</th><th>Current Head Count</th><th>Growth/Decline Count</th><th>Growth/Decline Status</th>"+
					"<th>Cisco Manager Id</th><th>Cisco Manager Name</th><th>Quarter</th><th>Project Location</th></tr>";
		}
		for(AssetBean assetBean:assetBeans){
			body +="<tr><td>"+assetBean.getProjectName()+"</td>"+
					"<td>"+assetBean.getCurrentHeadCount()+"</td>"+
					"<td>"+assetBean.getGrowthCount()+"</td>"+
					"<td>"+assetBean.getGrowthStatus()+"</td>"+
					"<td>"+assetBean.getCiscoManagerId()+"</td>"+
					"<td>"+assetBean.getCiscoManagerName()+"</td>"+
					"<td>"+assetBean.getQuarter()+"</td>"+
					"<td>"+assetBean.getProjectLocation()+"</td>"+
					"</tr>";
		}
		boolean b1=mailSender.sendMailInTableFormat(subject,body, employeeBean.getUserId(),"vanaik","vanaik");
		subject="Acknowledgement : "+subject;
		body="Asset Report sent to avinash and cc "+employeeBean.getManagerId();
		boolean b2=mailSender.sendAcknowledmentMail(subject,body,employeeBean.getUserId(), "vanaik", "vanaik");
		if(b1 && b2)	return true;
		return false;
	}
	 */
	public AssetBean getAssetByAssetId(int assetId) throws ServicesNotAvailableException, AssetTrackNotFetchedException{
		try {
			return daoServicesImpl.getAssetBeanById(assetId);
		}catch (DataAccessException e) {
			throw new AssetTrackNotFetchedException("Project track was not fetched");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public int getCurrentFiscalYear() throws  ServicesNotAvailableException, QuarterNotFetchedException{
		try {
			return daoServicesImpl.getCurrentfiscalYear();
		} catch (DataAccessException | SQLException e) {
			throw new QuarterNotFetchedException("Cannot fetch year");
		}
	}

	public String getCurrentQuarter() throws ServicesNotAvailableException, QuarterNotFetchedException{
		try {
			switch(daoServicesImpl.getCurrentQuarter()){
			case 1:
				return "QTR1";
			case 2: 
				return "QTR2";
			case 3:
				return "QTR3";
			case 4:
				return "QTR4";
			default:
				return null;
			}
		}catch (DataAccessException e) {
			throw new QuarterNotFetchedException("Cannot Fetch Quarter");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public boolean setQuarterForYear(String year,String q1From,String q1To,String q2From,String q2To,String q3From,String q3To,String q4From,String q4To) throws QuartersAlreadySetException, ServicesNotAvailableException{
		try {
			if(daoServicesImpl.checkQuartersForYear(year))	throw new QuartersAlreadySetException("Quarters already set for year "+year);
			if(daoServicesImpl.setQuartersForYear(year,q1From,q1To,q2From,q2To,q3From,q3To,q4From,q4To))
				return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new QuartersAlreadySetException("Quarters already set for year "+year);
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
		return false;		
	}

	/*LDAP Services*/
	public List<String> getReportingStructure(EmployeeBean employeeBean){
		LDAP ldap= new LDAP();
		return ldap.getReporting(employeeBean.getUserId());
	}
	public String getCiscoManagerDetailsThroughLDAP(String ciscoManagerId) throws EmployeeNameNotFoundException, ConnectionNotEstablishedException{
		LDAP ldap= new LDAP();
		return ldap.getEmpNames(ciscoManagerId);
	}


	public boolean freezeSystem(int year,int quarter,String freezeTime) throws NoQuarterFoundException, ServicesNotAvailableException, FreezeDateAlreadyPresentException, OutdatedDatedException{
		try {
			if(daoServicesImpl.checkFreezeTimeForQuarter(year,quarter))	throw new FreezeDateAlreadyPresentException("freeze date already present for this quarter");
			if(daoServicesImpl.addFreezeTime(year, quarter, freezeTime)) return true;
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Cannot add freeze time");
		}
		return false;
	}
	public EmployeeBean getEmployeeById(String userId) throws ServicesNotAvailableException, EmployeeNotFetchedException{
		try {
			return daoServicesImpl.retrieveEmployeeById(userId);
		} catch (DataAccessException e) {
			throw new EmployeeNotFetchedException("Employee not found");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Something went wrong");
		}
	}

	public boolean checkAssetPresent(String projectName){
		return daoServicesImpl.checkAsset(projectName);
	}

	public String getFreezeDate(int year, String quarter) {
		try {
			return daoServicesImpl.getCurrentfreezeDate(year,quarter);
		} catch (DataAccessException e) {
			return null;
		}
	}

	public String getUserIdOfProjectId(int projectId) {
		try {
			return daoServicesImpl.getUserIdOfProjectId(projectId);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean delegateProjectManager(String projectManagerId,String projectManagerName, String[] values) throws ServicesNotAvailableException {
		try {
			return daoServicesImpl.delegateProjectManager(projectManagerId,projectManagerName,values);
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Something went wrong");
		}
	}

	public boolean delegateProgramManager(String projectManagerId,String projectManagerName,String programManager, String[] values) throws ServicesNotAvailableException {
		try {
			return daoServicesImpl.delegateProgramManager(projectManagerId,projectManagerName,programManager,values);
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Something went wrong");
		}

	}

	public boolean checkAssetsForProjectManager(String userId) throws ServicesNotAvailableException {
		try{
			return daoServicesImpl.checkAssetsForProjectManager(userId);
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Something went wrong");
		}
	}

	public boolean delegateDeliveryhead(String projectManagerId,String projectManagerName, String programManagerName,String deliveryHeadName, String[] values) throws ServicesNotAvailableException {
		try{
			return daoServicesImpl.delegateDeliveryhead(projectManagerId,projectManagerName,programManagerName,deliveryHeadName,values);
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Something went wrong");
		}
	}

	public String addQuarterlyDetails(EmployeeBean employeebean,int year, String quarter,String validator) {
		try {
			if(daoServicesImpl.checkQuarterDetails(employeebean,year,quarter))
				daoServicesImpl.updateQuarterlyUpdates(employeebean,year,quarter,validator);
			else	
				daoServicesImpl.addQuarterlyDetails(employeebean,year,quarter,validator);
			return "validated";
		} catch (Exception e) {
			return "invalidate";
		}
	}
	public String getQuarterlyDetails(EmployeeBean employeebean,int year, String quarter) {
		try {
			return daoServicesImpl.getQuarterDetails(employeebean,year,quarter);
		} catch (Exception e) {
			return "invalidate";
		}
	}
	
	public boolean finalizeQuarters(EmployeeBean employeebean,int year, String quarter) {
		try {
			return daoServicesImpl.finalizeQuarters(employeebean,year,quarter);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

	public void updateassetCheck(EmployeeBean employeebean, Integer year,
			String quarter, String validator) {
		if(daoServicesImpl.checkQuarterDetails(employeebean,year,quarter)){
			System.out.println("Here");
			daoServicesImpl.updateQuarterlyUpdates(employeebean,year,quarter,validator);
			
		}
			
	}

	public String getAssetValidator(EmployeeBean bean, int year, String quarter) {
		if(daoServicesImpl.checkQuarterDetails(bean,year,quarter))	return daoServicesImpl.getQuarterlyDetails(bean,year,quarter);
		else
		return "invalidated";
	}

	public List<ValidateEmployee> getValidatedEmployees(Integer year,String quarter) {
		return daoServicesImpl.getValidatedEmployees(year,quarter);
	}
	public List<ValidateEmployee> getvalidatedStatusforPGM(EmployeeBean bean, int year,String quarter) {
		return daoServicesImpl.getvalidatedStatusforPGM( bean,  year, quarter);
	}
	public boolean reminderMail(String[] values) {
		return true;
	}

	public boolean activateEmoployee(String userId) throws EmployeeNotActivatedException, ServicesNotAvailableException {
		try {
			return daoServicesImpl.activateEmployee(userId);
		} catch (DataAccessException e) {
			throw new EmployeeNotActivatedException("Employee Details Not Deactivated");
		} catch (SQLException e) {
			throw new ServicesNotAvailableException("Services not available");
		}
	}

	public boolean addMailerAlias(String mailerId) throws CannotAddAliasException {
		try {
			return daoServicesImpl.insertMailerAlias(mailerId);
		} catch (SQLException e) {
			throw new CannotAddAliasException("Cannot Add Alias");
		}
	}

	public boolean deleteMailerAlias(String mailerId) {
		return daoServicesImpl.deletetMailerAlias(mailerId);
	}
	
	

	public List<Quarters> getQuarters(int fiscalYear) {
		try {
			return daoServicesImpl.getQuartersForYear(fiscalYear);
		} catch (DataAccessException | SQLException e) {
			return null;
		}
	}

	public Quarters getQuarterByQuarterId(int quarterId) {
		return daoServicesImpl.getQuarterByQuarterId(quarterId);
	}

	public void addBatch(List<String> batchquery,List<String> addemployye) {

		 daoServicesImpl.addBatch(batchquery,addemployye);
		
	}

	public boolean savefeedback(String userId,String app) {
		return daoServicesImpl.savefeedback(userId,app);
		
	}
	public boolean savefeedbackapp(String userId,String app) {
	return	daoServicesImpl.savefeedback(userId,app);
		
	}

	public boolean updateQuarter(String startDate, String endDate, String quarter, String fiscalYear) {
		System.out.println("start date "+startDate);
		System.out.println("end date "+endDate);
		startDate=convertDateToCorrectFormat(startDate);
		endDate=convertDateToCorrectFormat(endDate);
		System.out.println("start date "+startDate);
		System.out.println("end date "+endDate);
		try {
			return daoServicesImpl.updateQuarter(startDate,endDate,quarter,fiscalYear);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	private String convertDateToCorrectFormat(String date) {
		if(date.contains("-")){
			String dateArr[]=date.split("-");
			date=dateArr[1]+"/"+dateArr[2]+"/"+dateArr[0];
		}
		return date;
	}

	public void addProjectTrackifDataNotSet(EmployeeBean bean, int year,String quarter) {
		try {
			
			int currentquarter=daoServicesImpl.getCurrentQuarter();
			if(currentquarter==4)
			{
				currentquarter =1;
			}else{
			currentquarter-=1;}
			List<AssetBean> assetbeanforcurrentquarter = daoServicesImpl.getAssetsAsPerEmployeeRequest(bean.getUserId(), year, quarter);
			if(assetbeanforcurrentquarter!=null  && assetbeanforcurrentquarter.size()>1){}else{
			
			List<AssetBean> assetbean = daoServicesImpl.getAssetsAsPerEmployeeRequest(bean.getUserId(), year, "QTR"+currentquarter);
			Iterator<AssetBean> it = assetbean.iterator();
			while(it.hasNext())
			{
				AssetBean asbean = it.next();
				asbean.setQuarter(quarter);
				daoServicesImpl.addAssetForEmployee(asbean,bean.getUserId() );
			}
			}
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
		
	}


	public List<EmployeeBean> getallemployeebean() {
		return	daoServicesImpl.getallemployeebean();
			
		}

	public List<EmployeeBean> getManagersForUser(int i) {
		try {
			return daoServicesImpl.getEmployeesByDesignation(i);
		} catch (DataAccessException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}

	public List<AATBean> getAATURLS() {
		try {
			return daoServicesImpl.retrieveAATURLS();
		} catch (DataAccessException e) {
			return null;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public boolean updateAATURL(AATBean aatBean){
		try {
			return daoServicesImpl.updateAATURL(aatBean);
		} catch (DataAccessException e) {
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean checkLaptopRequest(LaptopBean laptopBean) {
		return daoServicesImpl.checkLaptopRequestInOpenState(laptopBean);
	}

	public List<LaptopBean> getLaptopDetails(EmployeeBean bean) {
		return daoServicesImpl.getLaptopDetailsForEmp(bean);
	}

	public boolean checkODCHead(EmployeeBean bean) {
		return daoServicesImpl.checkODCHead(bean);
	}

	public List<SystemFreezeDate> getFreezeDates(Integer year) {
		return daoServicesImpl.getFreezeDates(year);
	}

	public boolean updateSystemFreezeDate(SystemFreezeDate sysetmFreezeDate) {
		sysetmFreezeDate.setFreezeDate(convertDateToCorrectFormat(sysetmFreezeDate.getFreezeDate()));
		return daoServicesImpl.updateSystemFreezeDate(sysetmFreezeDate);
	}

	public SystemFreezeDate getSystemFreezeDate(int freezeId) {
		return daoServicesImpl.getSystemFreezeDate(freezeId);
	}
	
	public boolean updateLaptopBeanReason(LaptopBean laptopBean){
		return daoServicesImpl.updateLaptopBeanReason(laptopBean);
	}

	public Mail getCiscoManagerMailFromLaptopId(int laptopId) {
		return daoServicesImpl.getCiscoManagerMailFromLaptopId(laptopId);
	}

}
