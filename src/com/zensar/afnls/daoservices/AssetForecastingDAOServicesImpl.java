package com.zensar.afnls.daoservices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.apache.commons.collections.MultiHashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.zensar.afnls.beans.AATBean;
import com.zensar.afnls.beans.AssetBean;
import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.ITOCCList;
import com.zensar.afnls.beans.LaptopBean;
import com.zensar.afnls.beans.Laptopsmail;
import com.zensar.afnls.beans.Mail;
import com.zensar.afnls.beans.Quarters;
import com.zensar.afnls.beans.SystemFreezeDate;
import com.zensar.afnls.beans.ValidateEmployee;
import com.zensar.afnls.util.LaptopUtility;
import com.zensar.afnls.util.Queries;

@Repository
public class AssetForecastingDAOServicesImpl extends JdbcDaoSupport{



	public EmployeeBean getEmployeeFromCredentials(EmployeeBean employeeBean) throws SQLException,DataAccessException{
		//LDAP ldap=new LDAP();
		//if(ldap.validateUser(employeeBean.getUserId(), employeeBean.getPassword()))
		return getJdbcTemplate().queryForObject(Queries.getEmployeeFromCredentials,new Object[] {employeeBean.getUserId()},new EmployeeMapper());
		//else return null;
	}

	public EmployeeBean getEmployeeFromUserId(EmployeeBean employeeBean) throws SQLException,DataAccessException{
		return getJdbcTemplate().queryForObject(Queries.getEmployeeFromCredentials,new Object[] {employeeBean.getUserId()},new EmployeeMapper());
		//else return null;
	}

	public EmployeeBean getUpdatedEmployee(EmployeeBean employeeBean) throws SQLException,DataAccessException{
		return  getJdbcTemplate().queryForObject(Queries.getUpdatedEmployee,new Object[] {employeeBean.getUserId()},new AllEmployeeMapper());
	}

	public EmployeeBean getUpdatedPCOEmployee(EmployeeBean employeeBean) throws SQLException,DataAccessException{
		return getJdbcTemplate().queryForObject(Queries.getUpdatedEmployee,new Object[] {employeeBean.getUserId()},new PCOEmployeeMapper());
	}

	public EmployeeBean retrieveEmployeeById(String userId) throws SQLException,DataAccessException{
		return getJdbcTemplate().queryForObject(Queries.retrieveEmployeeById,new Object[]{userId}, new AllEmployeeMapper());
	}
	private int getDesignation(String designation){
		switch(designation){
		case "Project Manager":
			return 1;
		case "Program Manager":
			return 2;
		case "Delivery Head":
			return 3;
		case "Cisco ODC Head":
			return 4;
		case "LPT":
			return 5;
		case "PCO Team":
			return 6;

		}
		return 0;
	}

	public boolean updateEmployee(EmployeeBean bean) throws SQLException,DataAccessException{
		int a= getJdbcTemplate().update(Queries.updateEmployee,new Object[]{bean.getEmployeeName(),getDesignation(bean.getDesignation()),bean.getManagerId(),bean.getManagerId2Up(),bean.getUserId()});
		if(a>0)	return true;
		return false;
	}

	public boolean deactivateEmployee(String userId) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.deactivateEmployee,new Object[]{userId});
		if(a>0) return true;
		return false;
	}
	public boolean activateEmployee(String userId) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.activateEmployee,new Object[]{userId});
		if(a>0) return true;
		return false;
	}

	public String getODCUserId(){
		return getJdbcTemplate().queryForObject(Queries.getODCUserId, String.class);
	}

	public List<AssetBean> getAssetsAsPerEmployee(String userId) throws SQLException,DataAccessException{
		int year=getCurrentfiscalYear();
		int quarter=getCurrentQuarter();
		String quarters="QTR"+quarter;
		List<AssetBean> assetBeansNew= getJdbcTemplate().query(Queries.getAssetsAsPerEmployee,new Object[]{userId,year,quarters}, new AssetMapper());
		List<AssetBean> assetBeansOld=null;
		if(quarters.equalsIgnoreCase("QTR1")){
			year-=1;
			quarters="QTR4";
			assetBeansOld=getJdbcTemplate().query(Queries.getAssetsAsPerEmployee,new Object[]{userId,year,quarters}, new AssetMapper());
		}
		else{
			quarter=quarter-1;
			quarters="QTR"+quarter;
			assetBeansOld=getJdbcTemplate().query(Queries.getAssetsAsPerEmployee,new Object[]{userId,year,quarters}, new AssetMapper());
		}
		return union(assetBeansNew, assetBeansOld);
	}

	private List<AssetBean> union(List<AssetBean> assetBeansNew,List<AssetBean> assetBeansOld){
		Map<String,AssetBean> union = new MultiHashMap();
		for (AssetBean assetBean : assetBeansNew) {
			union.put(assetBean.getProjectName(),assetBean);
		}
		if(assetBeansNew.size()>1){
			if(assetBeansNew.size()>=assetBeansOld.size())
			{
				return new ArrayList<AssetBean>(union.values());
			}
		}

		for (AssetBean assetBean : assetBeansOld) {
			union.put(assetBean.getProjectName(),assetBean);
		}
		return new ArrayList<AssetBean>(union.values());
	}
	public List<ValidateEmployee> getValidatedEmployees(int year, String quarter) {
		List<ValidateEmployee> employees= getJdbcTemplate().query(Queries.getValidatedEmployees, new ValidateToEmployeeMapper());
		List<ValidateEmployee> validateEmployees=getJdbcTemplate().query(Queries.getValidatedEmployeesFromMaster,new Object[]{year,quarter}, new ValidateEmployeeMapper());
		Map<String, ValidateEmployee> union=new LinkedHashMap<String, ValidateEmployee>();
		for(ValidateEmployee employee: employees){
			union.put(employee.getUserId(), employee);
		}
		for(ValidateEmployee employee:validateEmployees){
			union.put(employee.getUserId(), employee);
		}
		return new ArrayList<ValidateEmployee>(union.values());
	}

	public List<AssetBean> getAssetsAsPerEmployeeRequest(String userId,int year,String quarter) throws SQLException,DataAccessException{
		if(quarter.equalsIgnoreCase("ALL"))
			return getJdbcTemplate().query(Queries.getAssetsAsPerEmployeeRequestYearly,new Object[]{userId,year},new AssetMapper());
		else 
			return getJdbcTemplate().query(Queries.getAssetsAsPerEmployeeRequest,new Object[]{userId,quarter,year},new AssetMapper());
	}

	public List<AssetBean> getAssetsForProgramManager(String userId,String projectManager, int year, String quarter) throws SQLException,DataAccessException{
		if(projectManager.equalsIgnoreCase("ALL")){
			if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForProgramManagerYearly,new Object[]{userId,year},new AssetMapper());
			else	return getJdbcTemplate().query(Queries.getAssetsForProgramManager,new Object[]{userId,quarter,year},new AssetMapper());	
		}
		else{
			if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForProgramManagerwithProjectManageYearly,new Object[]{userId,projectManager,year},new AssetMapper());
			else	return getJdbcTemplate().query(Queries.getAssetsForProgramManagerwithProjectManager,new Object[]{userId,projectManager,quarter,year},new AssetMapper());
		}
	}

	public List<AssetBean> getAssetsForDeliveryHead(String userId,String programManager, String projectManager, int year,String quarter) throws SQLException,DataAccessException{
		if(programManager.equalsIgnoreCase("ALL")){
			if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForDeliveryHeadYearly,new Object[]{userId,year},new AssetMapper());
			else return getJdbcTemplate().query(Queries.getAssetsForDeliveryHead,new Object[]{userId,quarter,year},new AssetMapper());
		}
		else{
			if(projectManager.equals("ALL")){
				if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForDeliveryHeadWithPGMYearly,new Object[]{userId,programManager,year},new AssetMapper());
				else	return getJdbcTemplate().query(Queries.getAssetsForDeliveryHeadWithPGMYearly,new Object[]{userId,programManager,quarter,year},new AssetMapper());
			}
			else{
				if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForDeliveryHeadWithPGMPMYearly,new Object[]{projectManager,year},new AssetMapper());
				else	return getJdbcTemplate().query(Queries.getAssetsForDeliveryHeadWithPGMPM,new Object[]{projectManager,quarter,year},new AssetMapper());
			}
		}
	}

	public List<AssetBean> getAssetsForCiscoODCHead(String userId,String deliveryHead, String programManager, String projectManager,int year, String quarter) throws SQLException,DataAccessException{
		if(deliveryHead.equalsIgnoreCase("ALL")){
			if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForODCHeadYearly,new Object[]{userId,year},new AssetMapper());
			else return getJdbcTemplate().query(Queries.getAllAssetsForODCHeadQuarYearly,new Object[]{quarter,Integer.valueOf(year)},new AssetMapper());
		}
		else{
			if(programManager.equals("ALL")){
				if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForDeliveryHeadYearly,new Object[]{deliveryHead,year},new AssetMapper());
				else	return getJdbcTemplate().query(Queries.getAssetsForDeliveryHead,new Object[]{deliveryHead,quarter,year},new AssetMapper());
			}
			else{
				if(projectManager.equalsIgnoreCase("ALL")){
					if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsForProgramManagerYearly,new Object[]{programManager,year},new AssetMapper());
					else	return getJdbcTemplate().query(Queries.getAssetsForProgramManager,new Object[]{programManager,quarter,year},new AssetMapper());
				}
				else{
					if(quarter.equalsIgnoreCase("ALL"))	return getJdbcTemplate().query(Queries.getAssetsAsPerEmployeeRequestYearly,new Object[]{projectManager,year},new AssetMapper());
					else	return getJdbcTemplate().query(Queries.getAssetsAsPerEmployeeRequest,new Object[]{projectManager,quarter,year},new AssetMapper());
				}
			}
		}
	}

	public boolean addAssetForEmployee(AssetBean assetBean,String userId) throws SQLException,DataAccessException{
		int a= getJdbcTemplate().update(Queries.addAssetForEmployee,new Object[]{assetBean.getProjectName(),assetBean.getCurrentHeadCount(),assetBean.getGrowthCount(),assetBean.getGrowthStatus(),assetBean.getCiscoManagerName(),assetBean.getCiscoManagerId(),assetBean.getQuarter(),assetBean.getProjectLocation(),assetBean.getProjectManager(),assetBean.getProgramManager(),assetBean.getDeliveryHead(),userId,getCurrentfiscalYear()});
		if(a>0)	return true;
		return false;
	}

	public boolean deleteAssetForEmployee(int assetId) throws SQLException,DataAccessException{
		int a=0;
		List<Integer> projectIds=getJdbcTemplate().query(Queries.getProjectIdsForAsset,new Object[]{assetId},new ProjectId());
		for (Integer integer : projectIds) {
			a=getJdbcTemplate().update(Queries.updateAllDeletingAssets,new Object[]{3,integer});
		}
		a=getJdbcTemplate().update(Queries.deleteAssetForEmployee,new Object[]{assetId});
		if(a>0) return true;
		return false;
	}

	public boolean updateAssetForEmployee(AssetBean assetBean) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.updateAssetForEmployee,new Object[]{assetBean.getProjectName(),assetBean.getCurrentHeadCount(),assetBean.getGrowthCount(),assetBean.getGrowthStatus(),assetBean.getCiscoManagerName(),assetBean.getCiscoManagerId(),assetBean.getQuarter(),assetBean.getProjectLocation(),assetBean.getProjectId()});
		if(a>0) return true;
		return false;
	}


	public boolean updatelaptopstatus(LaptopBean laptopBean) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.updatelaptopstatus,new Object[]{laptopBean.getRequestStatus(),laptopBean.getLaptopId()});
		if(a>0) return true;
		return false;
	}

	public boolean updatelaptopstatus_MoreInfo(LaptopBean laptopBean) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.updatelaptopstatus_MoreInfo,new Object[]{laptopBean.getRequestStatus(),laptopBean.getAdd_info(),laptopBean.getLaptopId()});
		if(a>0) return true;
		return false;
	}

	public boolean Closelaptoprequest(LaptopBean laptopBean) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.Closelaptoprequest,new Object[]{laptopBean.getRequestStatus(),laptopBean.getRemarksFromLA(),laptopBean.getLaptopId()});
		if(a>0) return true;
		return false;
	}

	public AssetBean getAssetBeanById(int assetId) throws SQLException,DataAccessException{
		return (AssetBean) getJdbcTemplate().queryForObject(Queries.getAssetBeanById,new Object[]{assetId},new AssetMapper());
	}

	public int getCurrentQuarter() throws SQLException,DataAccessException{
		return getJdbcTemplate().queryForObject(Queries.getCurrentQuarter,Integer.class);
	}
	public int getCurrentfiscalYear() throws SQLException,DataAccessException{
		return getJdbcTemplate().queryForObject(Queries.getCurrentfiscalYear,Integer.class);
	}

	public String getEmployeeId(String laptopid) throws SQLException,DataAccessException{

		return  getJdbcTemplate().queryForObject(Queries.getEmployeeId, new Object[]{laptopid},new EmployeeID());
	}
	public boolean addLaptopRequestForEmployee(LaptopBean laptopBean,String employeeId,Laptopsmail ciscoManagerMail) throws SQLException,DataAccessException{
		int a=0;
		try {
			a = getJdbcTemplate().update(Queries.addLaptopRequestForEmployee,new Object[]{laptopBean.getOwnerName(),laptopBean.getReason(),laptopBean.getRequestType(),laptopBean.getStatus(),employeeId,laptopBean.getProjectName(),laptopBean.getStock(),laptopBean.getFromemployeeId(),laptopBean.getTouserId()});
			Connection con = null;
			PreparedStatement ps = null;
			if(ciscoManagerMail.getLaptopId()!=null)
			{
				try{
					ciscoManagerMail.setLaptopId(this.maxLaptopId());
					DataSource  ds = getJdbcTemplate().getDataSource();
					con = ds.getConnection();
					ps = con.prepareStatement("insert into laptopsmail(laptopId,mailData,MailName) values(?,?,?)");
					ps.setInt(1, ciscoManagerMail.getLaptopId());
					ps.setBinaryStream(2, ciscoManagerMail.getMailData(),1000000);
					ps.setString(3, ciscoManagerMail.getMailName());
					a = ps.executeUpdate();
				}catch(Exception e){}
				finally{
					con.close();ps.close();
				}
				//update(Queries.addLaptopRequestMails,new Object[]{ciscoManagerMail.getLaptopId(),ciscoManagerMail.getMailData(),ciscoManagerMail.getMailName()});
			}} catch (DataAccessException e) {
				e.printStackTrace();
			} 
		if(a>0) return true;
		return false;
	}
	public boolean addLaptopRequestNewHireForEmployee(LaptopBean laptopBean,String userId) throws SQLException,DataAccessException{
		int a=0;
		a = getJdbcTemplate().update(Queries.addLaptopRequestNewHireForEmployee,new Object[]{laptopBean.getOwnerName(),laptopBean.getReason(),laptopBean.getRequestType(),laptopBean.getStatus(),userId,laptopBean.getProjectName(),laptopBean.getStock(),laptopBean.getFromemployeeId(),laptopBean.getTouserId()});
		if(a>0) return true;
		return false;
	}
	public List<LaptopBean> getLaptopsPerEmployee(String employeeId) throws SQLException,DataAccessException{
		return getJdbcTemplate().query(Queries.getLaptopsPerEmployee,new Object[]{employeeId}, new LaptopMapper());
	}
	public List<LaptopBean> getLaptopasPerDesg(String employeeId,Integer Designation) throws SQLException,DataAccessException{
		return getJdbcTemplate().query(Queries.getLaptopasPerDesg,new Object[]{employeeId,employeeId,employeeId,Designation}, new LaptopMapper());
	}
	public List<LaptopBean> getAllLaptops(String requestStatus) throws SQLException,DataAccessException{
		String datearray [] = LaptopUtility.getdaterange(); 
		return getJdbcTemplate().query(Queries.getAllLaptops,new Object[]{requestStatus,datearray[1]}, new LaptopMapper());
	}
	public Laptopsmail getLaptopMails(Integer laptopid) throws SQLException,DataAccessException{

		Connection con=getJdbcTemplate().getDataSource().getConnection();
		String sql = " SELECT * FROM laptopsmail where laptopid=  "+laptopid;
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();
		Laptopsmail laptopsmail = new Laptopsmail();
		try{
			while (resultSet.next()) {
				laptopsmail.setId(resultSet.getInt("id"));
				laptopsmail.setLaptopId(resultSet.getInt("laptopId"));
				laptopsmail.setMailData(resultSet.getBinaryStream("mailData"));
				laptopsmail.setMailName(resultSet.getString("MailName"));
				laptopsmail.setImgsize(resultSet.getInt("imgsize"));
			}
		}catch(Exception e){}
		finally{
			con.close();
			stmt.close();
			resultSet.close();
		}
		return laptopsmail;

	}


	public List<LaptopBean> getLaptopsPerFilder(String filtervalue) throws SQLException,DataAccessException{
		String[] datarr =  LaptopUtility.getdaterange();
		return getJdbcTemplate().query(Queries.getAllLaptopsbyfilter,new Object[]{filtervalue,datarr[1]}, new LaptopMapper());

	}



	public LaptopBean getLaptopsbylaptopid(int laptopid) throws SQLException,DataAccessException{
		return getJdbcTemplate().queryForObject(Queries.getLaptopsbylaptopid,new Object[]{laptopid}, new LaptopMapper());
	}



	public boolean deleteLaptopForEmployee(int laptopId) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.deleteLaptopForEmployee,new Object[]{laptopId});
		if(a>0) return true;
		return false;
	}

	public List<EmployeeBean> getEmployeesForEmployee(String userId) throws SQLException,DataAccessException{
		return getJdbcTemplate().query(Queries.getEmployeesForEmployee,new Object[]{userId},new AllEmployeeMapper());
	}

	public List<ITOCCList> getITOList() throws SQLException,DataAccessException{
		return getJdbcTemplate().query(Queries.getITOList,new Object[]{},new ITOCCLISTMapper());
	}


	public List<EmployeeBean> getAllEmployeesForPCO() throws SQLException,DataAccessException{
		return getJdbcTemplate().query(Queries.getAllEmployeesForPCO, new AllEmployeeMapper());
	}

	public boolean insertEmployee(EmployeeBean bean) throws SQLException,DataAccessException{
		int	a=getJdbcTemplate().update(Queries.insertEmployee,new Object[]{bean.getEmployeeName(),getDesignation(bean.getDesignation()),bean.getUserId(),bean.getManagerId(),bean.getManagerId2Up()});
		if(a>0) return true;
		return false;
	}

	public boolean addFreezeTime(int year,int quarter,String freezeTime) throws SQLException,DataAccessException{
		int a=getJdbcTemplate().update(Queries.addFreezeTime,new Object[]{year,quarter,freezeTime});
		if(a>0)	return true;
		else return false;
	}

	public int getQuarterForYear(String requestDate) throws SQLException,DataAccessException{
		return getJdbcTemplate().queryForObject(Queries.getQuarterForYear, new Object[]{requestDate},Integer.class);
	}

	public boolean checkFreezeTimeForQuarter(int year,int quarter) throws SQLException,DataAccessException{
		int a= getJdbcTemplate().queryForObject(Queries.checkCountFreezeTimeForQuarter, new Object[]{year,quarter},Integer.class);
		if(a>0) return true;
		return false;
	}

	public int maxLaptopId()  throws SQLException{
		try {
			return	getJdbcTemplate().queryForObject(Queries.maxLaptopId,Integer.class);
		} catch (DataAccessException e) {
			if(e.getMessage().equalsIgnoreCase("Incorrect result size: expected 1, actual 0"))
				return 0;
		}
		return 1;
	}

	public boolean checkQuartersForYear(String year)  throws SQLException{
		try {
			int a= getJdbcTemplate().queryForObject(Queries.checkQuartersForYear,new Object[]{year},Integer.class);
			if(a>0) return true;
			return false;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkAssetsForProjectManager(String userId) throws SQLException{
		int a=getJdbcTemplate().queryForObject(Queries.checkAssetsForProjectManager,new Object[]{userId},Integer.class);
		if(a>0)	return true;
		return false;
	}

	public boolean delegateProjectManager(String projectManagerId,String projectManagerName,String[] values) throws SQLException{
		for (String value : values) {
			getJdbcTemplate().update(Queries.delegateProjectManager,new Object[]{projectManagerId,projectManagerName,Integer.parseInt(value)});
			List<Integer> projectIds=getJdbcTemplate().query(Queries.getProjectIdsForAsset,new Object[]{Integer.parseInt(value)},new ProjectId());
			for (Integer projectId : projectIds) {
				if(projectId!=Integer.parseInt(value))
					getJdbcTemplate().update(Queries.updateAllDeletingAssets,new Object[]{2,projectId});
			}
		}
		return true;
	}

	public boolean checkAsset(String projectName){
		int  check=getJdbcTemplate().queryForObject(Queries.getAssetBeanByName,new Object[]{projectName},Integer.class);
		if(check>0)	return true;
		return false;
	}

	public boolean setQuartersForYear(String year,String q1From,String q1To,String q2From,String q2To,String q3From,String q3To,String q4From,String q4To) throws SQLException,DataAccessException {
		getJdbcTemplate().update(Queries.setQuarter1ForYear,new Object[]{q1From,q1To,Integer.parseInt(year)});
		getJdbcTemplate().update(Queries.setQuarter2ForYear,new Object[]{q2From,q2To,Integer.parseInt(year)});
		getJdbcTemplate().update(Queries.setQuarter3ForYear,new Object[]{q3From,q3To,Integer.parseInt(year)});
		getJdbcTemplate().update(Queries.setQuarter4ForYear,new Object[]{q4From,q4To,Integer.parseInt(year)});
		return true;
	}
	public String getCurrentfreezeDate(int year, String quarter) {
		if(year!=0 && quarter!=null) 
			return getJdbcTemplate().queryForObject(Queries.getCurrentfreezeDate,new Object[]{year, Integer.parseInt(quarter.replaceAll("[^0-9]", ""))},String.class);
		return null;
	}

	public List<Quarters> getQuartersForYear(int fiscalYear) throws SQLException,DataAccessException{
		return getJdbcTemplate().query(Queries.getQuartersForYear,new Object[]{fiscalYear,fiscalYear+1},new QuarterMapper());
	}

	private class QuarterMapper implements RowMapper<Quarters>{

		@Override
		public Quarters mapRow(ResultSet rs, int count) throws SQLException {
			return new Quarters(rs.getInt("quarterId"),rs.getInt("quarter") ,rs.getString("startDate"),rs.getString("endDate"),rs.getInt("fiscalYear"));
		}

	}

	private class EmployeeMapper implements RowMapper<EmployeeBean>{
		@Override
		public EmployeeBean mapRow(ResultSet rs, int count){
			try {
				EmployeeBean bean= new EmployeeBean(rs.getString("employeeName"),
						rs.getString("designation"),rs.getString("userId"),rs.getString("managerId"),
						rs.getString("managerId2Up"),rs.getString("status"));
				return bean;
			} catch (SQLException e) {
				return null;
			}
		}
	}
	private class AllEmployeeMapper implements RowMapper<EmployeeBean>{
		@Override
		public EmployeeBean mapRow(ResultSet rs, int count){
			try {
				EmployeeBean bean= new EmployeeBean(rs.getString("employeeName"),
						rs.getString("designation"),rs.getString("userId"),rs.getString("managerId"),
						rs.getString("managerId2Up"),rs.getString("status"),getAssetsAsPerEmployee(rs.getString("userId")),
						null, getEmployeesForEmployee(rs.getString("userId")));
				int ED=0,EG = 0,CHC=0;
				if(bean.getDesignation().equalsIgnoreCase("Project Manager")){
					for (AssetBean assetBean : bean.getAssetBeans()) {
						CHC+=assetBean.getCurrentHeadCount();
						if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
						else	ED+=assetBean.getGrowthCount();
					}
				}
				else if(bean.getDesignation().equalsIgnoreCase("Program Manager")){
					for (EmployeeBean employeeBean : bean.getEmployeeBeans()) {
						for (AssetBean assetBean : employeeBean.getAssetBeans()) {	
							CHC+=assetBean.getCurrentHeadCount();
							if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
							else	ED+=assetBean.getGrowthCount();
						}
					}

				}
				else if(bean.getDesignation().equalsIgnoreCase("Delivery Head")){
					for (EmployeeBean employeeBean : bean.getEmployeeBeans()) {
						for (EmployeeBean bean2 : employeeBean.getEmployeeBeans()) {
							for (AssetBean assetBean : bean2.getAssetBeans()) {	
								CHC+=assetBean.getCurrentHeadCount();
								if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
								else	ED+=assetBean.getGrowthCount();
							}
						}
					}

				}
				else if(bean.getDesignation().equalsIgnoreCase("Cisco ODC Head")){
					for (EmployeeBean employeeBean : bean.getEmployeeBeans()) {
						for (EmployeeBean employeeBean2: employeeBean.getEmployeeBeans()) {
							for (EmployeeBean bean2 : employeeBean2.getEmployeeBeans()) {
								for (AssetBean assetBean : bean2.getAssetBeans()) {	
									CHC+=assetBean.getCurrentHeadCount();
									if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
									else	ED+=assetBean.getGrowthCount();
								}
							}
						}
					}
				}
				bean.setED(ED);
				bean.setEG(EG);
				bean.setCHC(CHC);
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}


	private class PCOEmployeeMapper implements RowMapper<EmployeeBean>{
		@Override
		public EmployeeBean mapRow(ResultSet rs, int count){
			try {
				EmployeeBean bean= new EmployeeBean(rs.getString("employeeName"),
						rs.getString("designation"),rs.getString("userId"),rs.getString("managerId"),
						rs.getString("managerId2Up"),rs.getString("status"));
				bean.setEmployeeBeans(getAllEmployeesForPCO());
				return bean;
			} catch (SQLException e) {
				return null;
			}
		}
	}
	private static final class AssetMapper implements RowMapper<AssetBean>{
		@Override
		public AssetBean mapRow(ResultSet rs, int count) throws SQLException {
			return new AssetBean(rs.getInt("projectId"),rs.getString("projectName"),rs.getInt("currentHeadCount"),rs.getInt("growthCount"),rs.getString("growthStatus"),rs.getString("CiscoManagerName"),rs.getString("CiscoManagerId"),rs.getString("quarter"),rs.getString("projectLocation"),rs.getString("projectManager"),rs.getString("programManager"),rs.getString("deliveryHead"));
		}
	}

	private static final class ITOCCLISTMapper implements RowMapper<ITOCCList>{
		@Override
		public ITOCCList mapRow(ResultSet rs, int count) throws SQLException {
			return new ITOCCList(rs.getString("mailer_list"));
		}
	}


	private static final class LaptopMapper implements RowMapper<LaptopBean>{
		@Override
		public LaptopBean mapRow(ResultSet rs, int count) throws SQLException {
			return new LaptopBean(rs.getInt("laptopId"),rs.getString("ownerName"),rs.getString("reason").trim(),rs.getString("requestType"),
					LaptopUtility.dateConvert(rs.getString("createdDate")),rs.getString("status"),rs.getString("projectName"),
					rs.getString("requestStatus"),rs.getString("stock"),rs.getString("userId"),rs.getString("RemarksFromLA"),rs.getString("Employee"),rs.getString("addInfo"),rs.getString("fromuserId"));
		}
	}
	private static final class EmployeeID implements RowMapper<String>{
		@Override
		public String mapRow(ResultSet rs, int count) throws SQLException {
			return new String(rs.getString(1));
		}
	}

	private static final class ProjectId implements RowMapper<Integer>{

		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			return new Integer(rs.getInt(1));
		}

	}



	public String getUserIdOfProjectId(int projectId) {
		return getJdbcTemplate().queryForObject(Queries.getUserIdOfProjectId, new Object[]{projectId},String.class);
	}
	private static final class ValidateEmployeeMapper implements RowMapper<ValidateEmployee>{

		@Override
		public ValidateEmployee mapRow(ResultSet rs, int arg1) throws SQLException {
			return new ValidateEmployee(rs.getString(1),rs.getString(2),rs.getString(3));
		}
	}
	private static final class ValidateToEmployeeMapper implements RowMapper<ValidateEmployee>{

		@Override
		public ValidateEmployee mapRow(ResultSet rs, int arg1) throws SQLException {
			return new ValidateEmployee(rs.getString(1),rs.getString(2),"invalidate");
		}
	}
	public boolean delegateProgramManager(String projectManagerId,String projectManagerName,String programManager, String[] values) throws SQLException{
		for (String value : values) {
			getJdbcTemplate().update(Queries.delegateProgramManager,new Object[]{projectManagerId,projectManagerName,programManager,Integer.parseInt(value)});
			List<Integer> projectIds=getJdbcTemplate().query(Queries.getProjectIdsForAsset,new Object[]{Integer.parseInt(value)},new ProjectId());
			for (Integer projectId : projectIds) {
				if(projectId!=Integer.parseInt(value))
					getJdbcTemplate().update(Queries.updateAllDeletingAssets,new Object[]{2,projectId});
			}
		}
		return true;
	}
	public boolean delegateDeliveryhead(String projectManagerId,String projectManagerName, String programManagerName,String deliveryHeadName, String[] values) throws SQLException{
		for (String value : values) {
			getJdbcTemplate().update(Queries.delegateDeliveryhead,new Object[]{projectManagerId,projectManagerName,programManagerName,deliveryHeadName,Integer.parseInt(value)});
			List<Integer> projectIds=getJdbcTemplate().query(Queries.getProjectIdsForAsset,new Object[]{Integer.parseInt(value)},new ProjectId());
			for (Integer projectId : projectIds) {
				if(projectId!=Integer.parseInt(value))
					getJdbcTemplate().update(Queries.updateAllDeletingAssets,new Object[]{2,projectId});
			}
		}
		return true;
	}
	public boolean checkQuarterDetails(EmployeeBean employeebean, int year,String quarter) {
		int a= getJdbcTemplate().queryForObject(Queries.checkQuarterDetails, new Object[]{employeebean.getUserId(),year,quarter},Integer.class);
		if(a>0)	return true;
		else return false;
	}
	public String getQuarterDetails(EmployeeBean employeebean, int year,String quarter) {
		return getJdbcTemplate().queryForObject(Queries.getQuarterDetails, new Object[]{employeebean.getUserId(),year,quarter},String.class);

	}

	public void updateQuarterlyUpdates(EmployeeBean employeebean, int year,String quarter,String validator) {
		getJdbcTemplate().update(Queries.updateQuarterlyUpdates,new Object[]{validator,employeebean.getUserId(),year,quarter});
	}

	public void addQuarterlyDetails(EmployeeBean employeebean, int year,String quarter, String validator) {
		getJdbcTemplate().update(Queries.addQuarterlyDetails,new Object[]{employeebean.getUserId(),year,quarter,validator});
	}
	public String getQuarterlyDetails(EmployeeBean bean, int year,String quarter) {
		return getJdbcTemplate().queryForObject(Queries.getQuarterlyDetails, new Object[]{bean.getUserId(),year,quarter},String.class);
	}

	public boolean insertMailerAlias(String mailerId) throws SQLException{
		int a= getJdbcTemplate().update(Queries.insertMailerAlias,new Object[]{mailerId});
		if(a>0) return true;
		return false;
	}

	public boolean deletetMailerAlias(String mailerId) {
		// TODO Auto-generated method stub
		int a= getJdbcTemplate().update(Queries.deleteMailerAlias,new Object[]{mailerId});
		if(a>0) return true;
		return false;
	}

	public boolean getlaptopStatus(int  laptopid) {
		String b =  getJdbcTemplate().queryForObject(Queries.getlaptopStatus, new Object[]{laptopid},String.class);
		if(b!=null && b.equalsIgnoreCase("C"))
		{
			return false;
		}else{
			return true;
		} 
	}
	public Quarters getQuarterByQuarterId(int quarterId) {
		return getJdbcTemplate().queryForObject(Queries.getQuarterByQuarterId,new Object[]{quarterId},new QuarterMapper());
	}

	public void addBatch(List<String> batchquery,List<String> employye) {
		Connection con;Statement pst = null;
		try {
			con = getJdbcTemplate().getDataSource().getConnection();

			try{
				pst = con.createStatement();
				Iterator<String> itr = employye.iterator();

				while(itr.hasNext())
				{
					try{
						pst.addBatch(itr.next());	
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}

				pst.executeBatch();

			}catch(Exception e)
			{
				e.printStackTrace();
			}
			finally{
				pst.clearBatch();
				pst.close();
			}


			pst = con.createStatement();
			Iterator<String> itr = batchquery.iterator();

			while(itr.hasNext())
			{
				try{
					pst.addBatch(itr.next());	
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}

			pst.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean updateAssetbyId(AssetBean assetBean) {
		// TODO Auto-generated method stub

		int a=getJdbcTemplate().update(Queries.updateAssetbyId,new Object[]{assetBean.getGrowthStatus(),assetBean.getGrowthCount(),assetBean.getProjectId()});
		if(a>0) return true;
		return false;


	}
	public List<ValidateEmployee> getvalidatedStatusforPGM(EmployeeBean bean, int year,String quarter) {





		return getJdbcTemplate().query(Queries.getvalidatedStatusforPGM, new Object[]{year,quarter,bean.getUserId()},new ValidateQuaterForPGM());


	}
	private static final class ValidateQuaterForPGM implements RowMapper<ValidateEmployee>{

		@Override
		public ValidateEmployee mapRow(ResultSet rs, int arg1) throws SQLException {
			return new ValidateEmployee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
		}
	}
	private static final class ValidateQuaterForPGM3 implements RowMapper<ValidateEmployee>{

		@Override
		public ValidateEmployee mapRow(ResultSet rs, int arg1) throws SQLException {
			return new ValidateEmployee(rs.getString(1),rs.getString(2),rs.getString(3));
		}
	}



	public boolean finalizeQuarters(EmployeeBean employeebean, int year,String quarter) {
		int a=0;

		List<ValidateEmployee> pr =  getJdbcTemplate().query(Queries.getpmforpgm, new Object[]{employeebean.getUserId()},new ValidateQuaterForPGM3());

		Iterator itr=  pr.iterator();
		while(itr.hasNext())
		{
			ValidateEmployee validate = (ValidateEmployee) itr.next();
			getJdbcTemplate().update(Queries.addPGMQuarterlyUpdates,new Object[]{validate.getUserId(),year,quarter});
		}
		a = getJdbcTemplate().update(Queries.addPGMQuarterlyUpdates,new Object[]{employeebean.getUserId(),year,quarter});
		/*if(checkQuarterlyUpdate(quarter, year, employeebean.getUserId()))
		a=getJdbcTemplate().update(Queries.updateStatusforPGM,new Object[]{employeebean.getUserId(),year,quarter});
		else
			a=getJdbcTemplate().update(Queries.addPGMQuarterlyUpdates,new Object[]{employeebean.getUserId(),year,quarter});*/
		if(a>0) return true;
		return false;
	}

	public boolean checkQuarterlyUpdate(String quarter,int year,String userId){
		int a= getJdbcTemplate().queryForObject(Queries.checkQuarterlyUpdates, new Object[]{},Integer.class);
		if(a>0) return true;
		return false;
	}

	public boolean savefeedback(String userId,String app) {

		int a= getJdbcTemplate().update(Queries.savefeedback,new Object[]{userId,app});
		if(a>0) return true;
		return false;


	}
	public boolean savefeedbackapp(String userId,String app) {

		int a= getJdbcTemplate().update(Queries.savefeedback,new Object[]{userId,app});
		if(a>0) return true;
		return false;


	}

	public boolean updateQuarter(String startDate, String enddate, String quarter, String fiscalYear) {
		int status=getJdbcTemplate().update(Queries.UPDATEQUARTER,new Object[]{startDate,enddate,quarter,Integer.parseInt(fiscalYear)});

		if(status>0)
			return true;
		else

			return false;
	}


	public List<EmployeeBean> getallemployeebean() {

		return getJdbcTemplate().query(Queries.getallemployeeBean,new EmployeeMapper());
	}

	public List<EmployeeBean> getEmployeesByDesignation(int desigNumber) throws DataAccessException,SQLException{
		return getJdbcTemplate().query(Queries.getEmployeesByDesignation, new Object[]{desigNumber},new ManagersEmployeeMapper());
	}
	private static final class ManagersEmployeeMapper implements RowMapper<EmployeeBean>{

		@Override
		public EmployeeBean mapRow(ResultSet rs, int arg1) throws SQLException {
			return new EmployeeBean(rs.getString(1),rs.getString(2),rs.getString(3));
		}
	}



	public List<AATBean> retrieveAATURLS() throws SQLException{
		return getJdbcTemplate().query(Queries.retrieveAATURLS,new AATMapper());
	}

	private static final class AATMapper implements RowMapper<AATBean>{

		@Override
		public AATBean mapRow(ResultSet rs, int count) throws SQLException {
			return new AATBean(rs.getString("urlName"),rs.getString("url"));
		}

	}

	public boolean updateAATURL(AATBean aatBean) throws SQLException{
		int a=getJdbcTemplate().update(Queries.updateAATURL,new Object[]{aatBean.getUrl(),aatBean.getUrlName()});
		if (a>0) {
			return true;
		}
		return false;
	}

	public boolean checkLaptopRequestInOpenState(LaptopBean laptopBean) {
		int a=getJdbcTemplate().queryForObject(Queries.checkLaptopRequestInOpenState,new Object[]{laptopBean.getOwnerName()} ,Integer.class);
		if(a>0)	return true;
		return false;
	}

	public List<LaptopBean> getLaptopDetailsForEmp(EmployeeBean bean) {
		switch(bean.getDesignation()){
		case "Project Manager":
			return getJdbcTemplate().query(Queries.getLaptopsProjectManager,new Object[]{bean.getUserId()},new LaptopMapper());
		case "Program Manager":
			return getJdbcTemplate().query(Queries.getLaptopsProgramManager,new Object[]{bean.getUserId()},new LaptopMapper());
		case "Delivery Head":
			return getJdbcTemplate().query(Queries.getLaptopsDeliveryHead,new Object[]{bean.getUserId()},new LaptopMapper());
		case "Cisco ODC Head":
			return getJdbcTemplate().query(Queries.getLaptopsODCHead,new LaptopMapper());
		case "LPT":
			return getJdbcTemplate().query(Queries.getLaptopsLPT,new LaptopMapper());
		case "PCO Team":
			return null;
		}
		return null;
	}

	public boolean checkODCHead(EmployeeBean bean) {
		int a=getJdbcTemplate().queryForObject(Queries.checkODCHead,Integer.class);		
		if(a>0)	return true;
		return false;
	}

	public List<SystemFreezeDate> getFreezeDates(Integer year) {
		return getJdbcTemplate().query(Queries.getFreezeDates,new Object[]{year} ,new SysetmFreezeDateMapper());
	}

	private static final class SysetmFreezeDateMapper implements RowMapper<SystemFreezeDate>{

		@Override
		public SystemFreezeDate mapRow(ResultSet rs, int arg1) throws SQLException {
			return new SystemFreezeDate(rs.getInt("freezeId"),rs.getInt("freezeYear"),rs.getInt("freezeQuarter"),rs.getString("freezeDate"));
		}
	}



	public boolean updateSystemFreezeDate(SystemFreezeDate systemFreezeDate) {
		int a=getJdbcTemplate().update(Queries.updateSystemFreezeDate,new Object[]{systemFreezeDate.getFreezeDate(),systemFreezeDate.getFreezeId()});
		if(a>0)	return true;
		return false;
	}

	public SystemFreezeDate getSystemFreezeDate(int freezeId) {
		return getJdbcTemplate().queryForObject(Queries.getSystemFreezeDate,new Object[]{freezeId},new SysetmFreezeDateMapper());
	}

	public boolean updateLaptopBeanReason(LaptopBean laptopBean){
		int a= getJdbcTemplate().update(Queries.updateLaptopBeanReason,new Object[]{laptopBean.getReason(),laptopBean.getLaptopId()});
		if(a>0) return true;
		return false;
	}

	public Mail getCiscoManagerMailFromLaptopId(int laptopId){
		return getJdbcTemplate().queryForObject(Queries.getlaptopsMailbylaptopId,new Object[]{laptopId},new RowMapper<Mail>() {
			@Override
			public Mail mapRow(ResultSet rs, int count) throws SQLException {
				return new Mail(rs.getInt("laptopId"),rs.getString("MailName"),rs.getBlob("mailData"),rs.getInt("imgsize"));
			}
		}
	);
	}

}
