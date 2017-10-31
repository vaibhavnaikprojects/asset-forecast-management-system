package com.zensar.afnls.beans;

import org.springframework.stereotype.Component;
@Component
public class SystemFreezeDate {
	private int freezeId;
	private int year;
	private int quarter;
	private String freezeDate;

	public SystemFreezeDate() {
		// TODO Auto-generated constructor stub
	}


	public SystemFreezeDate(int freezeId,int year, int quarter, String freezeDate) {
		super();
		this.freezeId=freezeId;
		this.year = year;
		this.quarter = quarter;
		this.freezeDate = freezeDate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}



	public int getQuarter() {
		return quarter;
	}



	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}



	public String getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(String freezeDate) {
		this.freezeDate = freezeDate;
	}

	


	public int getFreezeId() {
		return freezeId;
	}


	public void setFreezeId(int freezeId) {
		this.freezeId = freezeId;
	}


	@Override
	public String toString() {
		return "SysetmFreezeDate [freezeId=" + freezeId + ", year=" + year
				+ ", quarter=" + quarter + ", freezeDate=" + freezeDate + "]";
	}



}
