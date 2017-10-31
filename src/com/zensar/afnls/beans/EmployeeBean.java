package com.zensar.afnls.beans;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class EmployeeBean {
	
	private String employeeName;
	private String password;
	private String designation;  
	private String userId;
	private String managerId;
	private String managerId2Up;
	private String status;
	private int CHC;
	private int ED;
	private int EG;
	private List<AssetBean> assetBeans;
	private List<LaptopBean> laptopBeans;
	private List<EmployeeBean> employeeBeans;

	public EmployeeBean() {
		super();
	}
	
	
	
	public EmployeeBean(String employeeName,String userId, String managerId) {
		super();
		this.employeeName = employeeName;
		this.userId=userId;
		this.managerId = managerId;
	}



	public EmployeeBean( String employeeName,
			String designation, String userId, String managerId,String managerId2Up,String status) {
		super();
		
		this.employeeName = employeeName;
		this.designation = designation;
		
		this.userId = userId;
		this.managerId = managerId;
		this.managerId2Up=managerId2Up;
		this.status=status;
	}

	public EmployeeBean(int employeeId, String employeeName, String designation, int extension, String userId,
			String managerId,String managerId2Up,String status, List<AssetBean> assetBeans,List<LaptopBean> laptopBeans) {
		super();
		
		this.employeeName = employeeName;
		this.designation = designation;
		
		this.userId = userId;
		this.managerId = managerId;
		this.managerId2Up=managerId2Up;
		this.status=status;
		this.assetBeans = assetBeans;
		this.laptopBeans=laptopBeans;
	}
	
	
	public EmployeeBean(String employeeName, String designation, String userId,
			String managerId, String managerId2Up, String status,
			List<AssetBean> assetBeans, List<LaptopBean> laptopBeans,
			List<EmployeeBean> employeeBeans) {
		super();
		this.employeeName = employeeName;
		this.designation = designation;
		this.userId = userId;
		this.managerId = managerId;
		this.managerId2Up = managerId2Up;
		this.status = status;
		this.assetBeans = assetBeans;
		this.laptopBeans = laptopBeans;
		this.employeeBeans = employeeBeans;
	}
	
	

	public EmployeeBean(String userId) {
		super();
		this.userId = userId;
	}

	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public List<AssetBean> getAssetBeans() {
		return assetBeans;
	}
	public void setAssetBeans(List<AssetBean> assetBeans) {
		this.assetBeans = assetBeans;
	}

	public List<LaptopBean> getLaptopBeans() {
		return laptopBeans;
	}
	public void setLaptopBeans(List<LaptopBean> laptopBeans) {
		this.laptopBeans = laptopBeans;
	}

	public List<EmployeeBean> getEmployeeBeans() {
		return employeeBeans;
	}
	public void setEmployeeBeans(List<EmployeeBean> employeeBeans) {
		this.employeeBeans = employeeBeans;
	}
	
	public String getManagerId2Up() {
		return managerId2Up;
	}

	public void setManagerId2Up(String managerId2Up) {
		this.managerId2Up = managerId2Up;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCHC() {
		return CHC;
	}

	public void setCHC(int cHC) {
		CHC = cHC;
	}

	public int getED() {
		return ED;
	}

	public void setED(int eD) {
		ED = eD;
	}

	public int getEG() {
		return EG;
	}

	public void setEG(int eG) {
		EG = eG;
	}

	@Override
	public String toString() {
		return  "\""+employeeName+"\"";
	}

	
	

}
