package com.zensar.afnls.beans;

public class Quarters {
	private int quarterId;
	private int quarter;
	private String startDate;
	private String endDate;
	private int fiscalYear;
	
	public int getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public Quarters(){}
	public Quarters(int quarterId, int quarter, String startDate, String endDate , int fiscalYear) {
		super();
		this.quarterId = quarterId;
		this.quarter = quarter;
		this.startDate = startDate;
		this.endDate = endDate;
		this.fiscalYear = fiscalYear;
	}
	public int getQuarterId() {
		return quarterId;
	}
	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "Quarters [quarterId=" + quarterId + ", quarter=" + quarter
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
