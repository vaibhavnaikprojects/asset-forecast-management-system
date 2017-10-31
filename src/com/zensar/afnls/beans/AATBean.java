package com.zensar.afnls.beans;

import org.springframework.stereotype.Component;

@Component
public class AATBean {
	private String urlName;
	private String url;
	
	public AATBean(){}
	
	public AATBean(String urlName, String url) {
		super();
		this.urlName = urlName;
		this.url = url;
	}
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "AATBean [urlName=" + urlName + ", url=" + url + "]";
	}
}
