package com.zensar.afnls.beans;

import org.springframework.stereotype.Component;

@Component
public class AssetBean {

	private int projectId;
	private String projectName;
	private int currentHeadCount;
	private int growthCount;
	private String growthStatus;
	private String ciscoManagerName;
	private String ciscoManagerId;
	private String quarter;
	private String projectLocation;
	private String createdDate;
	private String projectManager;
	private String programManager;
	private String deliveryHead;

	public AssetBean(){}

	public AssetBean( 
			String growthstatus, int growthCount, int projectId) {
		super();
		this.projectId = projectId;
		this.growthCount = growthCount;
		this.growthStatus = growthstatus;
		
	}
	
	public AssetBean(int projectId, String projectName,
			int currentHeadCount, int growthCount, String growthStatus,
			String ciscoManagerName, String ciscoManagerId, String quarter,
			String projectLocation,String projectManager,String programManager,String deliveryHead) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.currentHeadCount = currentHeadCount;
		this.growthCount = growthCount;
		this.growthStatus = growthStatus;
		this.ciscoManagerName = ciscoManagerName;
		this.ciscoManagerId = ciscoManagerId;
		this.quarter = quarter;
		this.projectLocation = projectLocation;
		this.projectManager = projectManager;
		this.programManager = programManager;
		this.deliveryHead = deliveryHead;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getCurrentHeadCount() {
		return currentHeadCount;
	}
	public void setCurrentHeadCount(int currentHeadCount) {
		this.currentHeadCount = currentHeadCount;
	}
	public int getGrowthCount() {
		return growthCount;
	}
	public void setGrowthCount(int growthCount) {
		this.growthCount = growthCount;
	}
	public String getGrowthStatus() {
		return growthStatus;
	}
	public void setGrowthStatus(String growthStatus) {
		this.growthStatus = growthStatus;
	}
	public String getCiscoManagerName() {
		return ciscoManagerName;
	}
	public void setCiscoManagerName(String ciscoManagerName) {
		this.ciscoManagerName = ciscoManagerName;
	}
	public String getCiscoManagerId() {
		return ciscoManagerId;
	}
	public void setCiscoManagerId(String ciscoManagerId) {
		this.ciscoManagerId = ciscoManagerId;
	}
	public String getQuarter() {
		return quarter;
	}
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	public String getProjectLocation() {
		return projectLocation;
	}
	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getProgramManager() {
		return programManager;
	}

	public void setProgramManager(String programManager) {
		this.programManager = programManager;
	}

	public String getDeliveryHead() {
		return deliveryHead;
	}

	public void setDeliveryHead(String deliveryHead) {
		this.deliveryHead = deliveryHead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ciscoManagerId == null) ? 0 : ciscoManagerId.hashCode());
		result = prime
				* result
				+ ((ciscoManagerName == null) ? 0 : ciscoManagerName.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + currentHeadCount;
		result = prime * result
				+ ((deliveryHead == null) ? 0 : deliveryHead.hashCode());
		result = prime * result + growthCount;
		result = prime * result
				+ ((growthStatus == null) ? 0 : growthStatus.hashCode());
		result = prime * result
				+ ((programManager == null) ? 0 : programManager.hashCode());
		result = prime * result + projectId;
		result = prime * result
				+ ((projectLocation == null) ? 0 : projectLocation.hashCode());
		result = prime * result
				+ ((projectManager == null) ? 0 : projectManager.hashCode());
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssetBean other = (AssetBean) obj;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n AssetBean [projectId=" + projectId + ", projectName="
				+ projectName + ", currentHeadCount=" + currentHeadCount
				+ ", growthCount=" + growthCount + ", growthStatus="
				+ growthStatus + ", ciscoManagerName=" + ciscoManagerName
				+ ", ciscoManagerId=" + ciscoManagerId + ", quarter=" + quarter
				+ ", projectLocation=" + projectLocation + ", createdDate="
				+ createdDate + ", projectManager=" + projectManager
				+ ", programManager=" + programManager + ", deliveryHead="
				+ deliveryHead + "]";
	}

}
