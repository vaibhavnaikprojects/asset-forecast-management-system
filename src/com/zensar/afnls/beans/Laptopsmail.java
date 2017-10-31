package com.zensar.afnls.beans;

import java.io.InputStream;

public class Laptopsmail {
	Integer id;
Integer laptopId;
InputStream  mailData;
String MailName;
Integer imgsize;
public Integer getImgsize() {
	return imgsize;
}

public void setImgsize(Integer imgsize) {
	this.imgsize = imgsize;
}

public Laptopsmail(){}

public Laptopsmail(Integer id, Integer laptopId, InputStream mailData, String mailName,Integer imgsize) {
	super();
	this.id = id;
	this.laptopId = laptopId;
	this.mailData = mailData;
	MailName = mailName;
	this.imgsize=imgsize;
}



public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getLaptopId() {
	return laptopId;
}
public void setLaptopId(Integer laptopId) {
	this.laptopId = laptopId;
}
public InputStream getMailData() {
	return mailData;
}
public void setMailData(InputStream mailData) {
	this.mailData = mailData;
}
public String getMailName() {
	return MailName;
}
public void setMailName(String mailName) {
	MailName = mailName;
}
}