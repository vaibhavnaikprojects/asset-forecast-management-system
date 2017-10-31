<%@page import="com.zensar.afnls.controller.LoginController"%>
<%@page import="java.util.List"%>
<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@page import="com.zensar.afnls.beans.LaptopBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script  type="text/javascript" src="<c:url value="/resources/js/helper.js"/>"></script>
<link href="<c:url value="/resources/css/dataTable.css"/>" rel="stylesheet" type="text/css" />

<style type="text/css">
.search_field {
    display: inline-block;
   
}

/* .search_field input {
    border: none;
    padding: 0;
}

.search_field button {
    border: none;
    background: none;
} */




</style>
<script type="text/javascript">
$(document).ready(function() {
	$(":checkbox").click(function(event) {

	var id = this.value;

	if (this.checked) {
	$("#remarks"+id).attr("required", true); 
	} 

	});

});

$(document).ready(function() {
$("#closedallrequest").click(function(event) {
	if ($(":text").val()!=undefined) {
		var data =$(":text").attr("id");
		var id=data.substring(7);
	$("#case"+id).attr("required", true); 
	} 

	});
});


</script>
<div class="row tile_count">
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-clock-o"></i>Today's date</span>
                            <div class="count">
                            <jsp:useBean id="now" class="java.util.Date"/>
                            <fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />
                            </div>                           
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Approved Requests</span>
                            <div class="count green">${sessionScope.approved}</div>
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Pending Approval</span>
                             <div class="count red">${sessionScope.pending}</div>
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Closed Requests</span>
                            
							        <div class="count green">${sessionScope.completed}</div>
							                            
                        </div>
                    </div>
                    
                     <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Need Justification</span>
                            
							        <div class="count">${sessionScope.moreinfo}</div>
							                            
                        </div>
                    </div>
                            <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Total Requests</span>
                            <div class="count green">${sessionScope.total}</div>
                       </div>
                    </div>
                    
                 
                </div>

<form id="statusFilter" name="statusFilter" action="statusFilterRequest.html" method="post" >

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
		  <div class="panel panel-default">
		    <div class="panel-heading" role="tab" id="headingThree">
		      <h4 class="panel-title" style="color: #336699">
		        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
		         <label>Laptop Request DashBoard</label>
		        </a></h4>
		         <select id="selectstatusFilter" name="selectstatusFilter">
		         <%String filtervalue = (String) request.getAttribute("filterselect");%>
		         <% if(filtervalue!=null &&filtervalue.toString().equalsIgnoreCase("A"))
		         { %>
  					<option value="A" selected="selected" >Approved</option>
  					<%}else{ %>
  					<option value="A">Approved</option>
  				<%} %>
  				<%if(filtervalue!=null&& filtervalue.toString().equalsIgnoreCase("I")){ %>
  					<option value="I" selected="selected" >Pending Approval</option><%}else{ %><option value="I">Pending Approval</option>
  				<%} %>
  				<%if(filtervalue!=null && filtervalue.toString().equalsIgnoreCase("R")){ %>
  					<option value="R" selected="selected">Reject</option><%}else{ %><option value="R">Reject</option>
  				<%} %>
  				<%if(filtervalue!=null && filtervalue.toString().equalsIgnoreCase("M")){ %>
  				<option value="M" selected="selected">More Information</option><%}else{ %><option value="M">More Information</option><%} %>
  				
  				<%if(filtervalue!=null && filtervalue.toString().equalsIgnoreCase("C")){ %>
  				<option value="C" selected="selected">Closed</option><%}else{ %><option value="C">Closed</option><%} %>
  				
				</select>
				</div> 
		    </div>
		    <% 
					
				
		    List<LaptopBean> lpbean  =( List<LaptopBean>)request.getAttribute("filtervalue");
		    
			
			if(lpbean==null)
			{
				
				EmployeeBean bean = (EmployeeBean) request.getSession().getAttribute("employee");
				lpbean = bean.getLaptopBeans();
			}
					
					
					%>
		    <div id="collapseThree" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
		     <div class="panel-default">
		     	
		        <c:if test="<%=(lpbean!=null)%>">
		        
		        
		       
		         <div class="x_panel">
					<table id="laptopRequests" class="table table-hover" >
					
					 <thead >
		        
						<tr>
						<th>Request Id</th>
						<th>Request Type</th>
						<th>Owner Name</th>
						<th>Project Name</th>
						<th>Requester</th>
						<th>From</th>
						<th>Status</th>
						 <th>Cisco Mgr Mail</th>
					 <%if(filtervalue==null||filtervalue.equalsIgnoreCase("A") || filtervalue.equalsIgnoreCase("C")) {%>
					<th>Case#</th>
					<%} else {%><th></th><%} %>
						 <th>
						 <%if(filtervalue==null||filtervalue.equalsIgnoreCase("A") ||filtervalue.equalsIgnoreCase("M")) {%>
						 <input type="checkbox" id="selectall"/><%} %>
						 
						 </th>
						
						</tr>
					</thead>
					<tbody>
						<c:forEach var="laptop" items="<%=lpbean%>">
						<tr class="grid">
							<td class="filterable-cell"><c:out value="${laptop.uniquelaptopid}"/></td>
				   			<td class="filterable-cell"><c:out value="${laptop.requestType}"/></td>
				   			<td class="filterable-cell"><c:out value="${laptop.ownerName}"/></td>
				   			<td class="filterable-cell"><c:out value="${laptop.projectName}"/></td>
				   			<td class="filterable-cell"><c:out value="${laptop.employeeId}"/></td>
				   			<c:choose>
				   			<c:when test="${laptop.stock=='S'}">
    							<td class="filterable-cell"><c:out value="Stock"/></td>
							  </c:when>
				   			<c:otherwise>
				   			
				   			<td class="filterable-cell"><c:out value="Associate"/></td>
				   			</c:otherwise>
				   			
				   			</c:choose>
				   			<td class="filterable-cell"><c:out value="${laptop.statusFlagValue}"/></td>
							<td class="filterable-cell">
				   			<c:choose>
  								<c:when test="${laptop.requestType=='Early Refresh'}">
				   			<a href="/AFMS/downloadimage.html?laptopid=${laptop.laptopId}">
				   			<i class="fa fa-download fa-lg" style="margin-left: 40%"></i>
				   			</a>
				   			</c:when>
				   			  <c:otherwise>
							<label id="dash" style="margin-left: 41%">---</label>	  </c:otherwise>
								</c:choose></td>	
								
							<td class="filterable-cell">  
				   			<c:choose>
  								<c:when test="${laptop.requestStatus=='A' or laptop.requestStatus=='M'}">
				   					<c:set var="string1" value="remarks"/>
				   					 <input type="text" class="case2"  id="${string1}${laptop.laptopId}" name="${string1}${laptop.laptopId}"/>
				   			 </c:when>
				   			 <c:when test="${laptop.requestStatus=='C'}">
				   					
				   					 <c:out value="${laptop.remarksFromLA}"/>
				   			 </c:when>
				   			 
				   			</c:choose>	
				   			</td>
				   			<td class="filterable-cell">  
				   			<c:choose>
  								<c:when test="${laptop.requestStatus=='A' or laptop.requestStatus=='M'}">
				   					<input type="checkbox" class="case" id="case${laptop.laptopId}" name="case" value="${laptop.laptopId}">
				   			 </c:when>
				   			</c:choose>
				   			</td>
				   			
				   		</tr>
				   		
						</c:forEach>	
					</tbody>
							</table>
							</div>
				</c:if>
				</div>
				
				<c:if test="${empty sessionScope.employee.laptopBeans}"></c:if>
		      </div>
		
		<table>
<tfoot>
	
    <tr>
      
     <td colspan="7"><%if(filtervalue==null||filtervalue.equalsIgnoreCase("A") ||filtervalue.equalsIgnoreCase("M") ) {%>
      <button type="submit" id="closedallrequest" name="closedallrequest" value="Submit" class="btn btn-primary" style="margin-left: 90%">Closed</button>
      <%} %></td>
      
      
    </tr>
  </tfoot>
  </table>
		    </div>
		</form>
		