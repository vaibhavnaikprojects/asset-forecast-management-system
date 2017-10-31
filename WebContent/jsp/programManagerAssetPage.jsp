<%@page import="java.util.HashMap"%>
<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
	    url:"laptopupdate.html",
	    type: 'GET',
	    dataType: "text/html", 
	    success: function(data) { }
	  
	 });
	});
function check()
{
	if(addAssetTrack.projectName.value=="Project Name Exists,Please Enter Another Name")
		return false;
	else if(addAssetTrack.ciscoManagerName.value=="Invalid CEC ID")
		return false;
	else
		return true;
}

function managerName()
{
	var id=addAssetTrack.ciscoManagerId.value;
          
$.ajax({
url: "getManagerName.html?empId="+id,
type: "GET",
success: function(data) {
	
	if(data=="Invalid CEC ID")
		{
		addAssetTrack.ciscoManagerName.value=data;
		return false;
		}
	else
		{
		addAssetTrack.ciscoManagerName.value=data;
		return true;
		} 
},error: function() {
	alert( "Sorry, there was a problem!" );
	}
});           
}
function getAllManagersName(){
	var programManagerId="<%out.print(((EmployeeBean)request.getSession().getAttribute("employee")).getManagerId());%>";
	var deliveryHeadId="<%out.print(((EmployeeBean)request.getSession().getAttribute("employee")).getManagerId2Up());%>";
	$.ajax({
		dataType: "json",
		url: "getAllManagerName.html?programManagerId="+programManagerId+"&deliveryHeadId="+deliveryHeadId,
		type: "GET",
		success: function(data) {
				addAssetTrack.deliveryHead.value=data.projectManager; 
		},error: function(data) {
			alert( "Sorry, there was a problem!" );
			}
		});
}
function checkProjectName(){
	$.ajax({
		url:"checkProjectName.html?projectName="+addAssetTrack.projectName.value,
		type:"GET",
		success: function(data){
			if(data=="true")
			addAssetTrack.projectName.value="Project Name Exists,Please Enter Another Name";
			return false;
		},error: function(){
			
		}
	}); 
}
function assetDetails(projectId){
	var currentQuarter="<%out.print(request.getSession().getAttribute("quarter"));%>";
	$.ajax({
		dataType: "json",
		url: "getAssetDetailsFromId.html?assetId="+projectId,
		type: "GET",	
		success: function(data) {
			if(data!=null){
				deleteAssetTrack.projectId.value=data.projectId;
				editAssetTrack.projectId.value=data.projectId;
				editAssetTrack.projectName.value=data.projectName;
				if(data.quarter!=currentQuarter){
					if(data.growthStatus=='growth'){
						editAssetTrack.currentHeadCount.value=data.currentHeadCount+data.growthCount;
						editAssetTrack.growthCount.value="";
						editAssetTrack.growthStatus.value="";
					}
					else{
						editAssetTrack.currentHeadCount.value=data.currentHeadCount-data.growthCount;
						editAssetTrack.growthCount.value="Add New Quarters Count";
						editAssetTrack.growthStatus.value="";
					}
				}
				else{  
				editAssetTrack.currentHeadCount.value=data.currentHeadCount;
				editAssetTrack.growthCount.value=data.growthCount;
				editAssetTrack.growthStatus.value=data.growthStatus;
				}
				editAssetTrack.ciscoManagerId.value=data.ciscoManagerId;
				editAssetTrack.ciscoManagerName.value=data.ciscoManagerName;
				editAssetTrack.previousquarter.value=data.quarter;
				editAssetTrack.projectLocation.value=data.projectLocation;
				editAssetTrack.projectManager.value=data.projectManager;
				editAssetTrack.programManager.value=data.programManager;
				editAssetTrack.deliveryHead.value=data.deliveryHead;
				editAssetTrack.userId.value=data.userId;
			}
			else{alert("data not loaded");}
		},error: function(data) {
			alert( "Sorry, there was a problem!" );
			}
		});
}
function viewchange(){
	var selectedView=document.getElementById("viewFilter").value;
	if(selectedView=="fork"){
		document.getElementById("integrated").style.display="none";
		document.getElementById("individual").style.display="block";
	}else{
		document.getElementById("individual").style.display="none";
		document.getElementById("integrated").style.display="block";
	}
}
</script>
<jsp:include page="assetDetails.jsp"></jsp:include>
<!-- Download Excel-->
<div class="modal fade" id="exportCSV" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Export CSV To Local</h4>
      </div>
      <form action="downloadExcel.html" method="post" class="form">
      <div class="modal-body">
				<div class="form-group">
					<label for="projectManager">Project Manager</label>
					<select class="form-control" id="projectManager" name="projectManager" required="">
						<option value="" disabled selected>Select Project Manager</option>
						<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
							<option value="${employee.userId}">${employee.employeeName}</option>
						</c:forEach>
						<option value="ALL">All</option>
					</select>
					<label for="exportYear">Enter Year</label>
					<select class="form-control" id="exportYear" name="exportYear" required="required">
					<option value="" disabled selected>Please Select Year</option>
						<%int currentYear=(Integer)request.getSession().getAttribute("year");
							for(int i=currentYear;i>currentYear-4;i--){%>
								<option value="<%=i%>"><%=i%></option>
							<% }%>
					</select>
					<label>Select Quarter</label>
						<select class="form-control" id="exportQuarter" name="exportQuarter" required="" title="Please select Quarter">
						<option value="" disabled selected>Please Select Quarter</option>
							<option value="QTR1">QTR1</option>
							<option value="QTR2">QTR2</option>
							<option value="QTR3">QTR3</option>
							<option value="QTR4">QTR4</option>
							<option value="ALL">YEARLY</option> 
						</select>
        			</div>
	  </div>
      <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<input type="submit" class="btn btn-primary" value="export"/>
      </div>
      	</form>
    </div>
  </div>
</div>
<br>
<div class="heading" style="text-align: center;">

	<h4 style="color: #336699"><b><c:if test="${not empty asset}">${asset.projectName}</c:if>
	${success}
	<span style="color:#1ABB9C;">${message}</span>
	</b></h4>
	
	<div class="view-filter">
		<span  style="color: #336699;">View Filter</span> 
		<select name="viewFilter" id="viewFilter" onchange="viewchange()">
			<option value="fork">PM View</option>
			<option value="join">Integrated View </option>
		</select>
	</div>
</div>

<div id="individual">
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
<div class="panel panel-default">
<form action="saveProjectManagerTrack.html" id="savetrack" name="savetrack" method="post">
   <div class="panel-heading" role="tab" id="${employee.employeeName}">
      <h4 class="panel-title" style="color: #336699">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#${employee.userId}" aria-expanded="true" aria-controls="${employee.employeeName}" onclick="return test('${employee.userId}')">
         ${employee.employeeName}         
        </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${sessionScope.employeeSubmission[employee.userId].validator ne 'validated'}"> Data Not Validated ,
        To Validate <%-- <a href="validateEmployee.html?userId=${employee.userId}" class="btn btn-info" data-toggle="tooltip" data-placement="bottom" 
							title="Validates PM's project track">Validate</a> --%>
					<input type="Submit" class="btn btn-info" data-toggle="tooltip" data-placement="bottom" title="Validates PM's project track" name="validatePM" value="Validate">
							</c:if>
        <span class="ed">ED : ${employee.ED}<img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
        <span class="eg">EG : ${employee.EG}<img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
        <span class="chc">CHC : ${employee.CHC}&nbsp;&nbsp;&nbsp;</span>
      </h4>
    </div>
    <div id="${employee.userId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${employee.employeeName}">
      <div class="panel-body">
      
      	 <c:if test="${not empty employee.assetBeans}">  
      		<div class="table-responsive">
      		<div class="x_panel">
      		<!-- <form action="saveProjectManagerTrack.html" id="savetrack" name="savetrack" method="post"> -->
      		<input type="hidden" name="empUserId" name="empUserId" value="${employee.userId}">
			<table id="_${employee.userId}" class="table table-hover">
			<thead>
				<tr>
				<th>Project Name</th>
				<th>Current Head Count</th>
				<th>Growth/Decline</th>			
				<th>Quarter (${sessionScope.quarter})</th>
				<th>Project Location</th>
				<th>Project Manager</th>
				</tr>
			</thead>
			<tbody>
  
				<c:forEach var="asset" items="${employee.assetBeans}">
				
				<%if(!(Boolean)request.getSession().getAttribute("validatefalg")) {  %>
<%-- data-toggle="modal" data-id="${asset.projectId}" onclick="return assetDetails(${asset.projectId})" class="grid" data-target="#assetDetails" --%>
				<tr>
		   			<td><c:out value="${asset.projectName}"/></td>
		   			<c:if test="${asset.quarter ne sessionScope.quarter}">
		   				<td><c:out value="${asset.currentHeadCount + asset.growthCount}"/></td>
		   			</c:if>
		   			<c:if test="${asset.quarter eq sessionScope.quarter}">
		   				<td><c:out value="${asset.currentHeadCount}"/></td>
		   			</c:if>
		   			<c:if test="${sessionScope.assetValidation ne 'validated'}">
		   				<c:if test="${asset.quarter ne sessionScope.quarter}">
										<td class="align-center"><input type="hidden"
										name="projectId" name="projectId" value="${asset.projectId}">
									<input id="currentHeadCount"
										name="currentHeadCount" type="hidden" value="${asset.currentHeadCount + asset.growthCount}" required="required"
										value="${asset.growthCount}" />
									<input id="growthCount"
										name="growthCount" type="number" max="500" min="-${asset.currentHeadCount + asset.growthCount}" required="required"
										value="0" />
									</td>
						</c:if>
						<c:if test="${asset.quarter eq sessionScope.quarter}">
									<td class="align-center"><input type="hidden"
										name="projectId" name="projectId" value="${asset.projectId}">
									<input id="currentHeadCount"
										name="currentHeadCount" type="hidden" value="${asset.currentHeadCount}" required="required"
										value="${asset.growthCount}" />
									<input id="growthCount"
										name="growthCount" type="number" max="500" min="-${asset.currentHeadCount}" required="required"
										value="${asset.growthCount}" />
									</td>
						</c:if>
		   			</c:if>
		   			
		   			<c:if test="${sessionScope.assetValidation eq 'validated'}">
		   			<td class="align-center">
		   				<c:out value="${asset.growthCount}"/>
		   			</td>
		   			</c:if>
		   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   			<c:if test="${asset.quarter ne sessionScope.quarter}"><b><c:out value="Data not set"/></b>
		   			<input type="hidden" value="DATA NOT SET" id="quarter" name="quarter" readonly="readonly">
		   			</c:if>
		   			</td>
		   			<td><c:out value="${asset.projectLocation}"/></td>
		   			<td><c:out value="${asset.projectManager}"/></td>
		   		</tr>
		   		<%}else{ %>
<%-- 		   		data-toggle="modal" data-id="${asset.projectId}"  --%>
		   		<tr>
		   			<td><c:out value="${asset.projectName}"/></td>
		   			<td><c:out value="${asset.currentHeadCount}"/></td>
		   			<c:if test="${sessionScope.assetValidation ne 'validated'}">
		   			<td class="align-center">
		   			<input type="hidden" name="projectId" name="projectId" value="${asset.projectId}">
		   				<c:out value="${asset.growthCount}"/>
		   			</td>
		   			</c:if>
		   			<c:if test="${sessionScope.assetValidation eq 'validated'}">
		   			<td class="align-center">
		   				<c:out value="${asset.growthCount}"/>
		   			</td>
		   			</c:if>
		   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   				<c:if test="${asset.quarter ne sessionScope.quarter}"><b><c:out value="Data not set"/></b>
		   				<input type="hidden" value="DATA NOT SET" id="quarter" name="quarter" readonly="readonly"></c:if>
		   			</td>
		   			<td><c:out value="${asset.projectLocation}"/></td>
		   			<td><c:out value="${asset.projectManager}"/></td>
		   		</tr>
		   		
		   		<%} %>
				</c:forEach>
			</tbody>
			</table><%if(!(Boolean)request.getSession().getAttribute("validatefalg") ) {%>
			<input type="Submit" class="btn btn-primary" name="validatePM" value="Save">
			<%} %>
			<!-- </form> -->
			</div>
		</div>
		</c:if>
		<c:if test="${empty employee.assetBeans}">No Project Tracks Yet</c:if>
      </div>
    </div>
    </form>
  </div>
</c:forEach>
</div>
</div>
<!-- Integrated View -->
<div id="integrated" style="display:none;">
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
<div class="panel panel-default">
   <div class="panel-heading" role="tab" id="${employee.employeeName}">
      <h4 class="panel-title" style="color: #336699">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#integratedView" aria-expanded="true" aria-controls="integratedView">
        Integrated View
        </a>
        <c:set var="ED" /><c:set var="EG"/><c:set var="CHC"/>
        <c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
        	<c:set var="CHC" value="${employee.CHC+CHC}" />
        	<c:set var="EG" value="${employee.EG+EG}" />
        	<c:set var="ED" value="${employee.ED+ED}" />
        </c:forEach>
      	<span class="ed">ED : <c:out value="${ED}"/><img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
        <span class="eg">EG : <c:out value="${EG}"/><img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
        <span class="chc">CHC : <c:out value="${CHC}"/>&nbsp;&nbsp;&nbsp;</span>
      </h4>
    </div>
    
    <div id="integratedView" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="integratedView">
   
      <div class="panel-body">
      		<div class="table-responsive">
      <div class="x_panel">
			<table id="assets" class="table table-hover">
			<thead>
				<tr>
				<th>Project Name</th>
				<th>Current Head Count</th>
				<th>Growth/Decline</th>			
				<th>Quarter (${sessionScope.quarter})</th>
				<th>Project Location</th>
				<th>Project Manager</th>
				</tr>
			</thead>
			<tbody>
  			<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
				<c:forEach var="asset" items="${employee.assetBeans}">			
				<%if((Boolean)request.getSession().getAttribute("validatefalg")) {  %>
<%-- 				data-toggle="modal" data-id="${asset.projectId}" class="grid" onclick="return assetDetails(${asset.projectId})" data-target="#assetDetails" --%>
				<tr >
		   			<td><c:out value="${asset.projectName}"/></td>
		   			<td class="align-center"><c:out value="${asset.currentHeadCount}"/></td>
		   			<td class="align-center"><c:out value="${asset.growthCount}"/></td>
		   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   			<c:if test="${asset.quarter ne sessionScope.quarter}"><b>DATA NOT SET</b></c:if></td>
		   			<td><c:out value="${asset.projectLocation}"/></td>
		   			<td><c:out value="${asset.projectManager}"/></td>
		   		</tr>
		   		<%}else{ %>
<%-- 		   		data-toggle="modal" data-id="${asset.projectId}" class="grid"  --%>
		   		<tr >
		   			<td><c:out value="${asset.projectName}"/></td>
		   			<td class="align-center"><c:out value="${asset.currentHeadCount}"/></td>
		   			<td class="align-center"><c:out value="${asset.growthCount}"/></td>
		   			
		   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   			<c:if test="${asset.quarter ne sessionScope.quarter}"><b>DATA NOT SET</b></c:if>
		   			</td>
		   			<td><c:out value="${asset.projectLocation}"/></td>
		   			<td><c:out value="${asset.projectManager}"/></td>
		   			
		   		</tr>
		   		
		   		
		   		<%} %> 
				</c:forEach>
			</c:forEach>
			</tbody>
			</table>
		</div>
      </div>
      </div>
    </div>
  </div>
</div>
</div>

<div style="display: inline-block;"><button id="exportCSVPopUp" class="btn btn-primary" data-toggle="modal" data-target="#exportCSV">Export</button>
<%if((Boolean)request.getSession().getAttribute("pmvalidatefalg") && (Boolean)request.getSession().getAttribute("validatefalg")) { %>
	<b style=" margin-top: 3%">Project tracks validated <i class="fa fa-check fa-lg" style="color: #1ABB9C"></i>
				</b>
	

							<% //} 
								}
else { 
	if((Boolean)request.getSession().getAttribute("pmvalidatefalg") ) { %>
<a href="finalizequarter.html" onclick="return warningSubmit()" class="btn btn-primary" data-toggle="tooltip" data-placement="bottom" 
							title="Once Submit , You will not be able to change Project Track ">Finalize project track</a>
							<%}%>




	<%}%>
</div>

