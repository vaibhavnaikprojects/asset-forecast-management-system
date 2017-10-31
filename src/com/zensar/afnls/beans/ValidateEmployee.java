package com.zensar.afnls.beans;

import java.io.Serializable;

public class ValidateEmployee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String employeeName;
	private String validator;
	private String pgm_status;


	public String getPgm_status() {
		return pgm_status;
	}
	public void setPgm_status(String pgm_status) {
		this.pgm_status = pgm_status;
	}
	public ValidateEmployee() {
		super();
	}
	public ValidateEmployee(String userId, String employeeName, String validator) {
		super();
		this.userId = userId;
		this.employeeName = employeeName;
		this.validator = validator;
	}
	public ValidateEmployee(String userId, String employeeName, String validator,String pgm_status) {
		super();
		this.userId = userId;
		this.employeeName = employeeName;
		this.validator = validator;
		this.pgm_status = pgm_status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	@Override
	public String toString() {
		return "ValidateEmployee [userId=" + userId + ", employeeName="
				+ employeeName + ", validator=" + validator + "]";
	}

}
