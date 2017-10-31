package com.zensar.afnls.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.zensar.afnls.beans.AssetBean;
import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.daoservices.AssetForecastingDAOServicesImpl;
import com.zensar.afnls.exception.AssetTrackNotUpdatedException;
import com.zensar.afnls.exception.EmployeeNotActivatedException;
import com.zensar.afnls.exception.EmployeeNotUpdatedException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.util.AFMSConstant;

@Service
public class LoginServiceForAFMS {
	public String []  designatioarray =AFMSConstant.designatioarray;
	@Autowired
	private AssetForecastingDAOServicesImpl daoServicesImpl;

	public EmployeeBean getEmployeeBean(EmployeeBean employeeBean) throws DataAccessException, SQLException, EmployeeNotActivatedException ,Exception{
		
		employeeBean =  daoServicesImpl.getEmployeeFromCredentials(employeeBean);
		if(employeeBean!=null && employeeBean.getUserId()!=null&&employeeBean.getUserId().length()>0 && employeeBean.getStatus().equalsIgnoreCase("A")){
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[0]))
			{
				employeeBean =	getLoginDetailsforProjectManager(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[1]))
			{
				employeeBean =	getLoginDetailsforProgramManager(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[2]))
			{
				employeeBean =	getLoginDetailsforDeliveryHead(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[3]))
			{
				employeeBean =	getLoginDetailsforCiscoODC(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[4]))
			{
				employeeBean =	getLoginDetailsforLPT(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[5]))
			{
				employeeBean=getLoginDetailsforPCO(employeeBean);
				return employeeBean;
			}
		}
		else {	
			if(employeeBean==null)
			{
				throw new Exception("Incorrect Username or Password");
			}
			else{
			throw new EmployeeNotActivatedException(employeeBean.getEmployeeName() +" is deactivated,please contact PCO");
			}
		}
		return employeeBean;
	}
	
public EmployeeBean getEmployeeBeanwithoutpassword(EmployeeBean employeeBean) throws DataAccessException, SQLException, EmployeeNotActivatedException ,Exception{
		
		employeeBean =  daoServicesImpl.getEmployeeFromUserId(employeeBean);
		if(employeeBean!=null && employeeBean.getUserId()!=null&&employeeBean.getUserId().length()>0 && employeeBean.getStatus().equalsIgnoreCase("A")){
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[0]))
			{
				employeeBean =	getLoginDetailsforProjectManager(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[1]))
			{
				employeeBean =	getLoginDetailsforProgramManager(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[2]))
			{
				employeeBean =	getLoginDetailsforDeliveryHead(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[3]))
			{
				employeeBean =	getLoginDetailsforCiscoODC(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[4]))
			{
				employeeBean =	getLoginDetailsforLPT(employeeBean);
				return employeeBean;

			}
			if(employeeBean.getDesignation().equalsIgnoreCase(designatioarray[5]))
			{
				employeeBean=getLoginDetailsforPCO(employeeBean);
				return employeeBean;
			}
		}
		else {	
			if(employeeBean==null)
			{
				throw new Exception("Incorrect Username or Password");
			}
			else{
			throw new EmployeeNotActivatedException(employeeBean.getEmployeeName() +" is deactivated,please contact PCO");
			}
		}
		return employeeBean;
	}
	
	private EmployeeBean getLoginDetailsforProjectManager(EmployeeBean employeeBean) throws DataAccessException, SQLException
	{
		employeeBean.setAssetBeans(daoServicesImpl.getAssetsAsPerEmployee(employeeBean.getUserId()));
		//	employeeBean.setLaptopBeans(daoServicesImpl.getLaptopsPerEmployee(employeeBean.getUserId()));
		int ED=0,EG = 0,CHC=0;
		for (AssetBean assetBean : employeeBean.getAssetBeans()) {
			CHC+=assetBean.getCurrentHeadCount();
			if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
			else	ED+=assetBean.getGrowthCount();
		}
		employeeBean.setED(ED);
		employeeBean.setEG(EG);
		employeeBean.setCHC(CHC);
		//	employeeBean.setEmployeeBeans(daoServicesImpl.getEmployeesForEmployee(employeeBean.getUserId()));
		return employeeBean;
	}
	private EmployeeBean getLoginDetailsforProgramManager(EmployeeBean employeeBean) throws DataAccessException, SQLException
	{
		//employeeBean.setAssetBeans(daoServicesImpl.getAssetsAsPerEmployee(employeeBean.getUserId()));
		employeeBean.setLaptopBeans(daoServicesImpl.getLaptopasPerDesg(employeeBean.getUserId(),2));
		employeeBean.setEmployeeBeans(daoServicesImpl.getEmployeesForEmployee(employeeBean.getUserId()));

		return employeeBean;
	}
	private EmployeeBean getLoginDetailsforDeliveryHead(EmployeeBean employeeBean) throws DataAccessException, SQLException
	{
		employeeBean.setAssetBeans(daoServicesImpl.getAssetsAsPerEmployee(employeeBean.getUserId()));
		//	employeeBean.setLaptopBeans(daoServicesImpl.getLaptopasPerDesg(employeeBean.getUserId(),3));
		employeeBean.setEmployeeBeans(daoServicesImpl.getEmployeesForEmployee(employeeBean.getUserId()));	
		int ED=0,EG = 0,CHC=0;
		for (AssetBean assetBean : employeeBean.getAssetBeans()) {
			CHC+=assetBean.getCurrentHeadCount();
			if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
			else	ED+=assetBean.getGrowthCount();
		}
		employeeBean.setED(ED);
		employeeBean.setEG(EG);
		employeeBean.setCHC(CHC);
		return employeeBean;
	}
	private EmployeeBean getLoginDetailsforCiscoODC(EmployeeBean employeeBean) throws DataAccessException, SQLException
	{
		employeeBean.setAssetBeans(daoServicesImpl.getAssetsAsPerEmployee(employeeBean.getUserId()));
		//	employeeBean.setLaptopBeans(daoServicesImpl.getAllLaptops("I"));
		employeeBean.setEmployeeBeans(daoServicesImpl.getEmployeesForEmployee(employeeBean.getUserId()));	
		int ED=0,EG = 0,CHC=0;
		for (AssetBean assetBean : employeeBean.getAssetBeans()) {
			CHC+=assetBean.getCurrentHeadCount();
			if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
			else	ED+=assetBean.getGrowthCount();
		}
		employeeBean.setED(ED);
		employeeBean.setEG(EG);
		employeeBean.setCHC(CHC);
		return employeeBean;
	}
	private EmployeeBean getLoginDetailsforLPT(EmployeeBean employeeBean) throws DataAccessException, SQLException
	{
		//employeeBean.setAssetBeans(daoServicesImpl.getAssetsAsPerEmployee(employeeBean.getUserId()));
		employeeBean.setLaptopBeans(daoServicesImpl.getAllLaptops("A"));
		//employeeBean.setEmployeeBeans(daoServicesImpl.getEmployeesForEmployee(employeeBean.getUserId()));	
		return employeeBean;
	}
	private EmployeeBean getLoginDetailsforPCO(EmployeeBean employeeBean) throws DataAccessException, SQLException
	{
		//employeeBean.setAssetBeans(daoServicesImpl.getAssetsAsPerEmployee(employeeBean.getUserId()));
		//employeeBean.setLaptopBeans(daoServicesImpl.getAllLaptops(employeeBean.getUserId()));
		employeeBean.setEmployeeBeans(daoServicesImpl.getAllEmployeesForPCO());	
		return employeeBean;
	}

	public EmployeeBean Updatedasset(EmployeeBean employeeBean) throws ServicesNotAvailableException, AssetTrackNotUpdatedException
	{
		try {
			employeeBean.setAssetBeans(daoServicesImpl.getAssetsAsPerEmployee(employeeBean.getUserId()));
		} catch (DataAccessException e) {
			throw new AssetTrackNotUpdatedException("Cannot Update Employee");
		}catch (SQLException e) {
			throw new ServicesNotAvailableException("Services Not Available");
		}
		int ED=0,EG = 0,CHC=0;
		for (AssetBean assetBean : employeeBean.getAssetBeans()) {
			CHC+=assetBean.getCurrentHeadCount();
			if(assetBean.getGrowthStatus().equals("growth"))	EG+=assetBean.getGrowthCount();
			else	ED+=assetBean.getGrowthCount();
		}
		employeeBean.setED(ED);
		employeeBean.setEG(EG);
		employeeBean.setCHC(CHC);
		return employeeBean;
	}

	
	
	public EmployeeBean  Updatedlaptop(EmployeeBean employeeBean) throws DataAccessException, SQLException
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


	public EmployeeBean getUpdatedEmployeeBean(EmployeeBean employeeBean) throws EmployeeNotActivatedException, EmployeeNotUpdatedException,Exception
	{	
		try {
			employeeBean =	getEmployeeBeanwithoutpassword(employeeBean);
			System.out.println(employeeBean);
		} catch (DataAccessException | SQLException e) {
			throw new EmployeeNotUpdatedException("Cannot Update Employee");
		}
		return employeeBean;
	}
}
