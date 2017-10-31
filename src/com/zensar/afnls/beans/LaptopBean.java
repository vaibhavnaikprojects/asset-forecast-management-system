package com.zensar.afnls.beans;

import com.zensar.afnls.init.InitiliazeResourceAtServerStartup;
import com.zensar.afnls.util.LaptopUtility;

public class LaptopBean {
	private String uniquelaptopid;
	public String getUniquelaptopid() {
		return uniquelaptopid;
	}

	public void setUniquelaptopid(String uniquelaptopid) {
		this.uniquelaptopid = uniquelaptopid;
	}

	private int laptopId;
	private String ownerName;
	private String reason;
	private String requestType;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String projectName;
	private String createdDate;
	private String status;
	private String requestStatus;
	private String employeeId;
	private String userId;
	private String fromemployeeId;
	private String stock;
	private String remarksFromLA;
	private String touserId;
	private String statusFlagValue ;
	private String stockValue;
	private String add_info;

	public String getAdd_info() {
		return add_info;
	}

	public void setAdd_info(String add_info) {
		this.add_info = add_info;
	}

	public String getStockValue() {
		return stockValue;
	}

	public void setStockValue(String stockValue) {
		this.stockValue = stockValue;
	}

	public String getTouserId() {
		return touserId;
	}

	public void setTouserId(String touserId) {
		this.touserId = touserId;
	}

	

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	

	public String getFromemployeeId() {
		return fromemployeeId;
	}

	public void setFromemployeeId(String fromemployeeId) {
		this.fromemployeeId = fromemployeeId;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public LaptopBean() {
		// TODO Auto-generated constructor stub
	}

	public LaptopBean(String ownerName, String reason, String requestType,
			String createdDate, String status) {
		super();
		this.ownerName = ownerName;
		this.reason = reason;
		this.requestType = requestType;
		this.createdDate = createdDate;
		this.status = status;
	}

	public LaptopBean(int laptopId, String ownerName, String reason,
			String requestType, String createdDate, String status,String projectName,String requestStatus) {
		super();
		
		this.laptopId = laptopId;
		this.ownerName = ownerName;
		this.reason = reason;
		this.requestType = requestType;
		this.createdDate = createdDate;
		this.status = status;
		this.projectName=projectName;
		this.requestStatus=requestStatus;
		//zyuu5678
		this.uniquelaptopid =  LaptopUtility.splitOwnerName(this.ownerName)+"/"+this.laptopId;
	}

	public LaptopBean(int laptopId, String ownerName, String reason,
			String requestType, String createdDate, String status,String projectName,String requestStatus,String stock,String userid,String RemarksFromLA,String name) {
		super();
		this.laptopId = laptopId;
		this.ownerName = ownerName;
		this.reason = reason;
		this.requestType = requestType;
		this.createdDate = createdDate;
		this.status = status;
		this.projectName=projectName;
		this.requestStatus=requestStatus;
		this.stock =  stock;
		this.userId =  userid;
		this.employeeId = name;// LaptopUtility.getEmployeeNamefromid(userid);
		this.remarksFromLA = RemarksFromLA;
		this.statusFlagValue =  InitiliazeResourceAtServerStartup.statusValue.get(this.requestStatus).toString();
		this.stockValue =  InitiliazeResourceAtServerStartup.stockValue.get(this.stock).toString();
		this.uniquelaptopid =  LaptopUtility.splitOwnerName(this.ownerName)+"/"+this.laptopId;
		
	}
	public LaptopBean(int laptopId, String ownerName, String reason,
			String requestType, String createdDate, String status,String projectName,String requestStatus,String stock,String userid,String RemarksFromLA,String name,String addInfo,String fromemployeeId) {
		super();
		this.laptopId = laptopId;
		this.ownerName = ownerName;
		this.reason = reason;
		this.requestType = requestType;
		this.createdDate = createdDate;
		this.status = status;
		this.projectName=projectName;
		this.requestStatus=requestStatus;
		this.stock =  stock;
		this.userId =  userid;
		this.employeeId = name;// LaptopUtility.getEmployeeNamefromid(userid);
		this.remarksFromLA = RemarksFromLA;
		this.statusFlagValue =  InitiliazeResourceAtServerStartup.statusValue.get(this.requestStatus).toString();
		this.stockValue =  InitiliazeResourceAtServerStartup.stockValue.get(this.stock).toString();
		this.uniquelaptopid =  LaptopUtility.splitOwnerName(this.ownerName)+"/"+this.laptopId;
		this.add_info=addInfo;
		this.fromemployeeId=fromemployeeId;
		
	}
	
	public String getStatusFlagValue() {
		return statusFlagValue;
	}

	public void setStatusFlagValue(String statusFlagValue) {
		this.statusFlagValue = statusFlagValue;
	}

	public String getRemarksFromLA() {
		return remarksFromLA;
	}

	public void setRemarksFromLA(String remarksFromLA) {
		this.remarksFromLA = remarksFromLA;
	}

	public int getLaptopId() {
		return laptopId;
	}
	public void setLaptopId(int laptopId) {
		this.laptopId = laptopId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "LaptopBean [uniquelaptopid=" + uniquelaptopid + ", laptopId="
				+ laptopId + ", ownerName=" + ownerName + ", reason=" + reason
				+ ", requestType=" + requestType + ", projectName="
				+ projectName + ", createdDate=" + createdDate + ", status="
				+ status + ", requestStatus=" + requestStatus + ", employeeId="
				+ employeeId + ", userId=" + userId + ", fromemployeeId="
				+ fromemployeeId + ", stock=" + stock + ", remarksFromLA="
				+ remarksFromLA + ", touserId=" + touserId
				+ ", statusFlagValue=" + statusFlagValue + ", stockValue="
				+ stockValue + ", add_info=" + add_info + "]";
	}



}
