package com.zensar.afnls.beans;

import java.sql.Blob;

public class Mail {
	private int laptopId;
	private String mailName;
	private Blob mail;
	private int mailSize;
	
	public Mail(){}
	
	public Mail(int laptopId, String mailName, Blob mail, int mailSize) {
		super();
		this.laptopId = laptopId;
		this.mailName = mailName;
		this.mail = mail;
		this.mailSize = mailSize;
	}
	public int getLaptopId() {
		return laptopId;
	}
	public void setLaptopId(int laptopId) {
		this.laptopId = laptopId;
	}
	public String getMailName() {
		return mailName;
	}
	public void setMailName(String mailName) {
		this.mailName = mailName;
	}
	public Blob getMail() {
		return mail;
	}
	public void setMail(Blob mail) {
		this.mail = mail;
	}
	public int getMailSize() {
		return mailSize;
	}
	public void setMailSize(int mailSize) {
		this.mailSize = mailSize;
	}
	
	

}
