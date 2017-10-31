package com.zensar.afnls;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zensar.afnls.beans.EmployeeBean;
import com.zensar.afnls.util.AFMSConstant;

public class AuthenticationServlet implements  Filter {

	@Override
	public void destroy() {

		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		HttpSession session =httpRequest.getSession();
		EmployeeBean bean=new EmployeeBean();
		bean.setUserId(request.getParameter("cecid"));
		bean.setPassword(request.getParameter("password"));
		
		String urlpath =  httpRequest.getServletPath();
		
		for(String url :  AFMSConstant.ignoreurlarray)
		{
			if(url.equalsIgnoreCase(urlpath)){
				arg2.doFilter(request, arg1);
			}
			
		}
		
		
			if(session.getAttribute("employee")!=null || bean.getPassword()!=null || bean.getUserId()!=null){
				arg2.doFilter(request, arg1);
			}else{
				try{
				RequestDispatcher rd =	httpRequest.getRequestDispatcher("indexPage.jsp");
				rd.forward(request, arg1);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		
		
		
		
	
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
