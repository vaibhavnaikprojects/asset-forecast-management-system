package com.zensar.afnls.scheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.poi.util.Beta;
import org.springframework.beans.factory.annotation.Autowired;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;


public class SchedulerThread implements Callable<Map<String,String>> {
	
	AssetForecastingSystemServicesImpl services;
	public SchedulerThread(AssetForecastingSystemServicesImpl  servicesImpl)
	{
		services = servicesImpl;
	}
	
	@Override
	public Map<String, String> call() throws Exception {
		  Map<String,String> empIdEpmname = new HashMap<String,String>();
		List<EmployeeBean>  employeeb = services.getallemployeebean();
		Iterator<EmployeeBean> itr =  employeeb.iterator();
		while(itr.hasNext())
		{
			EmployeeBean temp =itr.next();
			empIdEpmname.put(temp.getUserId(), temp.getEmployeeName());			
		}
		return empIdEpmname;
	}

}
