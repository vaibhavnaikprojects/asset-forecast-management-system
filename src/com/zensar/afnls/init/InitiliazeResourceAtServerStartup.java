package com.zensar.afnls.init;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.beans.ITOCCList;
import com.zensar.afnls.exception.AssetTrackNotFetchedException;
import com.zensar.afnls.exception.ServicesNotAvailableException;
import com.zensar.afnls.scheduler.SchedulerThread;
import com.zensar.afnls.services.AssetForecastingSystemServicesImpl;

public class InitiliazeResourceAtServerStartup  {
	@Autowired
	AssetForecastingSystemServicesImpl services;
	@Autowired
	ServletContext  context;
	public static List<ITOCCList> itoccList;
	public static String keypath;
	public static String staticPagepath ;
	static public Map<String,String> statusValue = new HashMap<String,String>();
	static public Map<String,String> stockValue = new HashMap<String,String>();
	public static Map<String,String> empIdEpmname = new ConcurrentHashMap<String,String>();
	
	static{
		statusValue.put("A", "Approved");
		statusValue.put("I", "Pending Approval");
		statusValue.put("R", "Reject");
		statusValue.put("M", "More Info");
		statusValue.put("C", "Closed");
		
		stockValue.put("S", "Yes") ;
		stockValue.put("N", "--") ;
	}
	
	public void refresh()
	{
		
	}
	
	public void init()
	{
		try {
			itoccList = services.getITOList();
			keypath = context.getRealPath("");
			
			staticPagepath =context.getRealPath("/resources")+File.separator+"htmlpages"+File.separator;
			System.out.println("INIT Properly"+staticPagepath);
			final SchedulerThread  scheduler = new SchedulerThread(services);
			
			try {
				ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
				executorService.scheduleAtFixedRate(new Runnable() {
				    public void run() {   
				    	try {
							empIdEpmname=  scheduler.call();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				}, 0, 1, TimeUnit.HOURS);}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		} catch (ServicesNotAvailableException | AssetTrackNotFetchedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	
}
