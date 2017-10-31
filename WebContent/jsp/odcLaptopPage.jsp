<%@page import="com.zensar.afnls.controller.LoginController"%>
<%@page import="java.util.List"%>
<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@page import="com.zensar.afnls.beans.LaptopBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="statusFilter" name="statusFilter" action="statusFilterRequest.html" method="post" >
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

		  <div class="panel panel-default">
		    <div class="panel-heading" role="tab" id="headingThree">
		      <h4 class="panel-title" style="color: #336699">
		        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
		         Laptop Request DashBoard
		        </a>
		         <select id="selectstatusFilter" name="selectstatusFilter">
		         <%String filtervalue = (String) request.getAttribute("filterselect");%>
		        
  				<%if(filtervalue!=null&& filtervalue.toString().equalsIgnoreCase("I")){ %>
  					<option value="I" selected="selected" >Pending Approval</option><%}else{ %><option value="I">Pending Approval</option>
  				<%} %>
  				 <% if(filtervalue!=null &&filtervalue.toString().equalsIgnoreCase("A"))
		         { %>
  					<option value="A" selected="selected" >Approved</option>
  					<%}else{ %>
  					<option value="A">Approved</option>
  				<%} %>
  				<%if(filtervalue!=null && filtervalue.toString().equalsIgnoreCase("R")){ %>
  					<option value="R" selected="selected">Reject</option><%}else{ %><option value="R">Reject</option>
  				<%} %>
  				<%if(filtervalue!=null && filtervalue.toString().equalsIgnoreCase("M")){ %>
  				<option value="M" selected="selected">More Information</option><%}else{ %><option value="M">More Information</option><%} %>
  				
  				<%if(filtervalue!=null && filtervalue.toString().equalsIgnoreCase("C")){ %>
  				<option value="C" selected="selected">Closed</option><%}else{ %><option value="C">Closed</option><%} %>
  				
</select>
		      </h4>
		     
		    </div>
		    <% 
					
					List<LaptopBean> lpbean  =( List<LaptopBean>)request.getAttribute("filtervalue");
		    
					
					if(filtervalue==null)
					{
						
						EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
						lpbean = bean.getLaptopBeans();
					}
					
					%>
		   
		        <c:if test="<%=(lpbean!=null)%>">
		        <div class="x_panel">
					<table id="laptopRequests" class="table table-hover">
					<thead>
						<tr>
						<th>Request Id</th>
						<th>Request Type</th>
						<th>Owner Name</th>
						<th>Project Name</th>
						<th>Requester</th>
						<th>Request Date</th>
<!-- 						<th>Reason</th> -->
						 <th>
						 <%if(filtervalue==null||filtervalue.equalsIgnoreCase("A") ||filtervalue.equalsIgnoreCase("M") ||filtervalue.equalsIgnoreCase("C")) {%>
						 <input type="checkbox" id="selectall"/><%} %>
						 
						 </th>
						 <th>Cisco Mgr Mail</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="laptop" items="<%=lpbean%>">
						<tr class="grid" data-toggle="tooltip" title="${laptop.reason}" data-container="body" >
						
							<td><c:out value="${laptop.uniquelaptopid}"/></td>
				   			<td><c:out value="${laptop.requestType}"/></td>
				   			<td><c:out value="${laptop.ownerName}"/></td>
				   			<td><c:out value="${laptop.projectName}"/></td>
				   			<td><c:out value="${laptop.employeeId}"/></td>
				   			<td><c:out value="${laptop.createdDate}"/></td>
<%-- 				   			<td><c:out value="${laptop.reason}"/></td> --%>
				   			<td>  
				   			<c:choose>
  								<c:when test="${laptop.requestStatus=='I' || laptop.requestStatus=='A'||laptop.requestStatus=='M'}">
				   					<input type="checkbox" class="case" id="case" name="case" value="${laptop.laptopId}"></td>
				   			 </c:when>
				   			</c:choose>
				   			<td>
				   			<c:choose>
  								<c:when test="${laptop.requestType=='Early Refresh'}">
				   			<a href="/AFMS/downloadimage.html?laptopid=${laptop.laptopId}">
				   			<i class="fa fa-download fa-lg" style="margin-left: 40%"></i></a>
				   			</c:when>
				   			  <c:otherwise>
				   			  <label id="dash" style="margin-left: 41%">---</label>
				   				  </c:otherwise>
								</c:choose></td>	
				   		</tr>
				   		
						</c:forEach>	
					</tbody>
					</table>
					</div>
				</c:if>
		    
          
      
        </div>
        <br>
        <div id="submitButton" style="margin-left: 75%">
		<%if(filtervalue==null||filtervalue.equalsIgnoreCase("A") ||filtervalue.equalsIgnoreCase("M")||filtervalue.equalsIgnoreCase("I")) {%>
      <!-- <button type="submit" id="closedallrequest" name="closedallrequest" class="btn btn-primary" value="Submit">Closed</button> -->
      <%} %>
      <%if(filtervalue==null ||filtervalue.equalsIgnoreCase("I")) {%>
   &nbsp;&nbsp;&nbsp;  <button type="submit" id="closedallrequest"  class="btn btn-primary" name="closedallrequest" value="Approve">Approve</button>
   &nbsp;&nbsp;&nbsp;  <button type="submit" id="closedallrequest"  class="btn btn-primary" name="closedallrequest" value="Reject">Reject</button>
      <%} %>
		  </div>
		</div>
		</form>
		