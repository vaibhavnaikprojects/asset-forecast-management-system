<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">

/* $('#'+userId).click(function(){
	
	
	$("#userId").freezeHeader({
		'height' : '400px',
	});
});
		 */

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
	/* $.ajax({
		url:"checkProjectName.html?projectName="+addAssetTrack.projectName.value,
		type:"GET",
		success: function(data){
			if(data=="true")
			addAssetTrack.projectName.value="Project Name Exists,Please Enter Another Name";
			return false;
		},error: function(){
			
		}
	});  */
}
function assetDetails(projectId){
	var currentQuarter="<%out.print(request.getSession().getAttribute("quarter"));%>";
	$.ajax({
		dataType: "json",
		url: "getAssetDetailsFromId.html?assetId="+projectId,
		type: "GET",
		success: function(data) {
			if(data!=null){
				editAssetTrack.projectId.value=data.projectId;
				editAssetTrack.projectName.value=data.projectName;
				if(data.quarter!=currentQuarter){
					if(data.growthStatus=='growth'){
						editAssetTrack.currentHeadCount.value=data.currentHeadCount+data.growthCount;
						editAssetTrack.growthCount.value="";
						editAssetTrack.growthCount.min=-data.currentHeadCount+data.growthCount;
						//editAssetTrack.growthStatus.value="";
					}
					else{
						editAssetTrack.currentHeadCount.value=data.currentHeadCount+data.growthCount;
						editAssetTrack.growthCount.value="";
						var count=data.currentHeadCount+data.growthCount;
						editAssetTrack.growthCount.min=-count;
						//editAssetTrack.growthStatus.value="";
					}
				}
				else{  
				editAssetTrack.currentHeadCount.value=data.currentHeadCount;
				editAssetTrack.growthCount.value=data.growthCount;
				editAssetTrack.growthCount.min=-data.currentHeadCount;
				//editAssetTrack.growthStatus.value=data.growthStatus;
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
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="block";
		document.getElementById("integrated").style.display="none";
		document.getElementById("DHView").style.display="none";
	}else if(selectedView=="join"){
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("integrated").style.display="block";
		document.getElementById("DHView").style.display="none";
	}else if(selectedView=="forkjoin"){
		document.getElementById("integrated").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("PMView").style.display="block";
		document.getElementById("DHView").style.display="none";
	}else{
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("integrated").style.display="none";
		document.getElementById("DHView").style.display="block";
	}
}
function programManagerValue(){
	
}


</script>

<c:set var="odcHead"/>
<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
<c:if test="${employee.designation eq 'Cisco ODC Head'}">
	<c:set var="odcHead" value="${employee}"/>
</c:if>
</c:forEach>
<jsp:include page="assetDetails.jsp"></jsp:include>
<!-- Modal For Adding asset Track -->
<div class="modal fade" id="addAsset" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add Asset Track</h4>
      </div>
      <form id="addAssetTrack" action="addAssetTrack.html" method="post">
      <div class="modal-body">
				<div class="form-group">
					<label for="projectName">Project Name</label>
					<input type="text" class="form-control" name="projectName" id="projectName" required="required" onblur="return checkProjectName()" maxlength="100"/>
					<label for="currentHeadCount">Current Head Count</label>
					<input type="number" class="form-control" name="currentHeadCount" id="currentHeadCount" required="required" max="500"/>
					<label for="growthCount">Growth/Decline Count</label>
					<input type="number" class="form-control" name="growthCount" id="growthCount" required="required" max="500"/>
					<!-- <label for="growthStatus">Growth/Decline Status</label>
					<select id="growthStatus" name="growthStatus" class="form-control" required="required">
					<option value="" selected disabled="disabled">Please Select Status</option>
						<option value="growth">Growth</option>
						<option value="decline">Decline</option>
					</select> -->
					<label for="ciscoManagerId">Cisco Manager Id</label>
					<input type="text" class="form-control" name="ciscoManagerId" id="ciscoManagerId" required="required" onblur="return managerName()" maxlength="45"/>
					<label for="ciscoManagerName">Cisco Manager Name</label>
					<input type="text" class="form-control" name="ciscoManagerName" id="ciscoManagerName" readonly="readonly"/>
					<label for="quarter">Quarter</label>
					<input type="text" class="form-control" name="quarter" id="quarter" required="required" value="${sessionScope.quarter}"/>
					<label for="projectLocation">Project Location</label>
					<select id="projectLocation" name="projectLocation" class="form-control" required="required">
						<option value="Pune">Pune</option>
						<option value="Bangalore">Bangalore</option>
						<option value="Hyderabad">Hyderabad</option>
					</select>
					<label for="projectManager">Project Manager</label>
					<select class="form-control" id="projectManager" name="projectManager" required="required" onchange="return programManagerValue()">
					<option value="" disabled selected>Select Project Manager</option>
						<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
						<c:forEach var="programManagers" items="${deliveryHead.employeeBeans}">
							<c:forEach var="projectManagers" items="${programManagers.employeeBeans}">
									<option value="${projectManagers.userId}">${projectManagers.employeeName}</option>
							</c:forEach>
						</c:forEach>
						</c:forEach>
					</select>
					<label for="programManager">Program Manager</label>
					<select class="form-control" id="programManager" name="programManager" required="required" onchange="return projectManagerValue()">
						<option value="" disabled selected>Select Program Manager</option>
						<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
						<c:forEach var="employee" items="${deliveryHead.employeeBeans}">
							<option value="${employee.userId}">${employee.employeeName}</option>
						</c:forEach>
						</c:forEach>
					</select>
					<label for="deliveryHead">Delivery Head</label>
					<select class="form-control" id="deliveryHead" name="deliveryHead" required="required" >
						<option value="" disabled selected>Select Delivery Head</option>
							<c:forEach var="employee" items="${odcHead.employeeBeans}">
								<option value="${employee.userId}">${employee.employeeName}</option>
							</c:forEach>
					</select>
        			</div>
	  </div>
      <div class="modal-footer">
      <p>Note : Disabled fields are auto populated</p>
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary" onclick="return check()">Add Track</button>
      </div>
      </form>
    </div>
  </div>
</div>
<!-- Download Excel -->
<div class="modal fade" id="exportCSV" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Export CSV To Local</h4>
      </div>
     
      <form action="downloadExcel.html" id="unique" name="unique" method="post" class="form">
       <script type="text/javascript">
      
     function selectall()
     {
    	 try {
    	
    	 var x = unique.deliveryHead.selectedIndex;
    	    var y = unique.deliveryHead.options;
    	    if(y[x].text=="All")
    	    	{
    	    	 
    	    	   unique.programManager.value ="ALL";
    	    	   unique.projectManager.value ="ALL";
    	    	}
    	    
     }
     catch(err) {
       alert( err.message);
     }
     }
    
      
      </script>
      <div class="modal-body">
				<div class="form-group">
				<label for="deliveryHead">Delivery Head</label>
					<select class="form-control" id="deliveryHead" name="deliveryHead" required="" onchange="javaScript:selectall()">
						<option value="" disabled selected>Select Delivery Head</option>
						<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
							<option value="${deliveryHead.userId}">${deliveryHead.employeeName}</option>
						</c:forEach>
						<option value="ALL">All</option>
					</select>
					<label for="projectManager">Program Manager</label>
					<select class="form-control" id="programManager" name="programManager" required="">
						<option value="" disabled selected>Select Program Manager</option>
						<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
							<c:forEach var="employee" items="${deliveryHead.employeeBeans}">
								<option value="${employee.userId}">${employee.employeeName}</option>
							</c:forEach>
						</c:forEach>
						<option value="ALL">All</option>
					</select>
					<label for="projectManager">Project Manager</label>
					<select class="form-control" id="projectManager" name="projectManager" required="">
						<option value="" disabled selected>Select Project Manager</option>
						<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
							<c:forEach var="programManagers" items="${deliveryHead.employeeBeans}">
								<c:forEach var="employee" items="${programManagers.employeeBeans}">
								<option value="${employee.userId}">${employee.employeeName}</option>
								</c:forEach>
							</c:forEach>
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
	<center><h4 style="color: #336699"><b><c:if test="${not empty asset}">${asset.projectName}</c:if> ${success}
	${message}</b></h4></center>
	
		<div class="view-filter ">
		<span  style="color: #336699;">View Filter</span> 
		<select name="viewFilter" id="viewFilter" onchange="viewchange()">
			<option value="dh">Individual DH View</option>
			<option value="fork">Individual PGM View</option>
			<option value="forkjoin">Individual PM View</option>
			<option value="join">Integrated View </option>
		</select>
	</div>
	
<div id="DHView">
<h4 style="color: #336699">Delivery Head View:</h4>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
<div class="panel panel-default">
    <div class="panel-heading" role="tab" id="${deliveryHead.employeeName}">
      <h4 class="panel-title" style="color: #336699">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#${deliveryHead.userId}" aria-expanded="true" aria-controls="${deliveryHead.employeeName}">
         ${deliveryHead.employeeName}
        </a>
       <c:set var="ED" /><c:set var="EG"/><c:set var="CHC"/>
        <c:forEach var="programManager" items="${deliveryHead.employeeBeans}">
        <c:forEach var="employee" items="${programManager.employeeBeans}">
        	<c:set var="CHC" value="${employee.CHC+CHC}" />
        	<c:set var="EG" value="${employee.EG+EG}" />
        	<c:set var="ED" value="${employee.ED+ED}" />
        	</c:forEach>
        </c:forEach>
      	<span class="ed" >ED : <c:out value="${ED}"/><img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
        <span class="eg">EG : <c:out value="${EG}"/><img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
        <span class="chc">CHC : <c:out value="${CHC}"/>&nbsp;&nbsp;&nbsp;</span>
      </h4>
    </div>
    <div id="${deliveryHead.userId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${deliveryHead.employeeName}">
      <div class="panel-body">
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<h5 style="color: #336699">Program Managers:</h5>
				<c:forEach var="programManager" items="${deliveryHead.employeeBeans}">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="${programManager.employeeName}">
		     				<h4 class="panel-title" style="color: #336699">
		        			<a role="button" data-toggle="collapse" data-parent="#accordion2" href="#${programManager.userId}" aria-expanded="true" aria-controls="${programManager.employeeName}">
		        			${programManager.employeeName}
		        			</a>
		        				<c:set var="ED" /><c:set var="EG"/><c:set var="CHC"/>
		        			 	<c:forEach var="employee" items="${programManager.employeeBeans}">
        							<c:set var="CHC" value="${employee.CHC+CHC}" />
        							<c:set var="EG" value="${employee.EG+EG}" />
				        			<c:set var="ED" value="${employee.ED+ED}" />
				        		</c:forEach>
		        				 <span class="ed" >ED : <c:out value="${ED}"/><img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
       							 <span class="eg">EG : <c:out value="${EG}"/><img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
        						 <span class="chc">CHC : <c:out value="${CHC}"/>&nbsp;&nbsp;&nbsp;</span>
       						</h4>
		      			</div>
		      			<div id="${programManager.userId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${programManager.employeeName}">
      						<div class="panel-body">
      						<h5 style="color: #336699">Project Managers:</h5>
      						<c:forEach var="projectManagers" items="${programManager.employeeBeans}">	
      							<div class="panel panel-default">
									<div class="panel-heading" role="tab" id="${projectManagers.employeeName}">
					     				<h4 class="panel-title" style="color: #336699">
					        			<a role="button" data-toggle="collapse" data-parent="#accordion2" href="#${projectManagers.userId}" aria-expanded="true" aria-controls="${projectManagers.employeeName}" onclick="return test('${projectManagers.userId}')">
					        			${projectManagers.employeeName}
					        			</a>
					        				 <span class="ed">ED : <c:out value="${projectManagers.ED}"/><img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
			       							 <span class="eg">EG : <c:out value="${projectManagers.EG}"/><img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
			        						 <span class="chc">CHC : <c:out value="${projectManagers.CHC}"/>&nbsp;&nbsp;&nbsp;</span>
			       						</h4>
			      					</div>
			      					<div id="${projectManagers.userId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${projectManagers.employeeName}">
	      								<div class="panel-body">
	      								<c:if test="${not empty projectManagers.assetBeans}">  
					      		<div class="x_panel">
								<table id="_${projectManagers.userId}" class="table table-hover">
								<thead>
									<tr>
									<th>Project Name</th>
									<th>Current Head Count</th>
									<th>Growth/Decline</th>
									<th>Cisco Manager Id</th>
									<th>Cisco Manager Name</th>
									<th>Quarter (${sessionScope.quarter})</th>
									<th>Project Location</th>
									
									</tr>
								</thead>
								<tbody>
									<c:forEach var="asset" items="${projectManagers.assetBeans}">
									<tr class="grid" data-toggle="modal" data-id="${asset.projectId}" onclick="return assetDetails(${asset.projectId})" data-target="#assetDetails">
							   			<td><c:out value="${asset.projectName}"/></td>
							   			<td class="align-center"><c:out value="${asset.currentHeadCount}"/></td>
							   			<td class="align-center"><c:out value="${asset.growthCount}"/>
							   				<c:if test="${asset.growthStatus eq 'growth'}">
		   										</c:if>
		   									<c:if test="${asset.growthStatus ne 'growth'}">
		   									</c:if>
							   			</td>
							   			<td><c:out value="${asset.ciscoManagerId}"/></td>
							   			<td><c:out value="${asset.ciscoManagerName}"/></td>
							   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   								<c:if test="${asset.quarter ne sessionScope.quarter}"><b>DATA NOT SET</b></c:if>
		   								</td>
							   			<td><c:out value="${asset.projectLocation}"/></td>
							   		
							   		</tr>
									</c:forEach>
								</tbody>
								</table>
								</div>
							</c:if>
							<c:if test="${empty projectManagers.assetBeans}">No Project Tracks Yet</c:if>
	      					</div>
	      				</div>
      				</div>
      			</c:forEach>	
      						</div>
      					</div>
    				</div>
				</c:forEach>
			</div>
      </div>
    </div>
  </div>
</c:forEach>
</div>
</div>
<!-- Individual PGM View	 -->
<div id="PGMView" style="display: none;">
<h4 style="color: #336699">Program Managers View:</h4>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
<c:forEach var="programManagers" items="${deliveryHead.employeeBeans}">
<div class="panel panel-default">
    <div class="panel-heading" role="tab" id="${programManagers.employeeName}">
      <h4 class="panel-title" style="color: #336699">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#${fn:replace(programManagers.employeeName,' ', 'a')}" aria-expanded="true" aria-controls="${programManagers.employeeName}">
         ${programManagers.employeeName}
        </a>
       <c:set var="ED" /><c:set var="EG"/><c:set var="CHC"/>
        <c:forEach var="employee" items="${programManagers.employeeBeans}">
        	<c:set var="CHC" value="${employee.CHC+CHC}" />
        	<c:set var="EG" value="${employee.EG+EG}" />
        	<c:set var="ED" value="${employee.ED+ED}" />
        </c:forEach>
      	<span class="ed">ED : <c:out value="${ED}"/><img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
        <span class="eg">EG : <c:out value="${EG}"/><img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
        <span class="chc">CHC : <c:out value="${CHC}"/>&nbsp;&nbsp;&nbsp;</span>
      </h4>
    </div>
    <div id="${fn:replace(programManagers.employeeName,' ', 'a')}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${programManagers.employeeName}">
      <div class="panel-body">
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<h5 style="color: #336699">Project Managers:</h5>
				<c:forEach var="projectManagers" items="${programManagers.employeeBeans}">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="${projectManagers.employeeName}">
		     				<h4 class="panel-title" style="color: #336699">
		        			<a role="button" class="panel-toggle" data-toggle="collapse" data-parent="#accordion2" href="#${fn:replace(projectManagers.employeeName,' ', 'a')}" aria-expanded="true" aria-controls="${projectManagers.employeeName}" onclick="return test1('${projectManagers.userId}')">
		        			${projectManagers.employeeName}
		        			</a>
		        				 <span class="ed">ED : <c:out value="${projectManagers.ED}"/><img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
       							 <span class="eg">EG : <c:out value="${projectManagers.EG}"/><img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
        						 <span class="chc">CHC : <c:out value="${projectManagers.CHC}"/>&nbsp;&nbsp;&nbsp;</span>
       						</h4>
		      			</div>
		      			<div id="${fn:replace(projectManagers.employeeName,' ', 'a')}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${projectManagers.employeeName}">
      						<div class="panel-body">
      							<c:if test="${not empty projectManagers.assetBeans}">  
					      		<div class="x_panel">
								<table id="123_${projectManagers.userId}" class="table table-hover">
								<thead>
									<tr>
									<th>Project Name</th>
									<th>Current Head Count</th>
									<th>Growth/Decline</th>
									<th>Cisco Manager Id</th>
									<th>Cisco Manager Name</th>
									<th>Quarter (${sessionScope.quarter})</th>
									<th>Project Location</th>
									<th>Project Manager</th>
									<th>Program Manager</th>
									<th>Delivery Head</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="asset" items="${projectManagers.assetBeans}">
									<tr class="grid" data-toggle="modal" data-id="${asset.projectId}" onclick="return assetDetails(${asset.projectId})" data-target="#assetDetails">
							   			<td><c:out value="${asset.projectName}"/></td>
							   			<td class="align-center"><c:out value="${asset.currentHeadCount}"/></td>
							   			<td class="align-center"><c:out value="${asset.growthCount}"/>
							   				<c:if test="${asset.growthStatus eq 'growth'}">
		   										</c:if>
		   									<c:if test="${asset.growthStatus ne 'growth'}">
		   									</c:if>
							   			</td>
							   			<td><c:out value="${asset.ciscoManagerId}"/></td>
							   			<td><c:out value="${asset.ciscoManagerName}"/></td>
							   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   								<c:if test="${asset.quarter ne sessionScope.quarter}"><b>DATA NOT SET</b></c:if>
		   								</td>
							   			<td><c:out value="${asset.projectLocation}"/></td>
							   			<td><c:out value="${asset.projectManager}"/></td>
		   								<td><c:out value="${asset.programManager}"/></td>
		   								<td><c:out value="${asset.deliveryHead}"/></td>
							   		</tr>
									</c:forEach>
								</tbody>
								</table>
								</div>
							</c:if>
							<c:if test="${empty projectManagers.assetBeans}">No Project Tracks Yet</c:if>
      						</div>
      					</div>
    				</div>
				</c:forEach>
			</div>
      </div>
    </div>
  </div>
</c:forEach>
</c:forEach>
</div>
</div>
<!-- Individual PM View -->
<div id="PMView" style="display:none;">
<h4 style="color: #336699">Project Managers View:</h4>
   <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    	<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
				<c:forEach var="programManager" items="${deliveryHead.employeeBeans}">
				<c:forEach var="projectManagers" items="${programManager.employeeBeans}">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="${projectManagers.employeeName}" onclick="callMe('${projectManagers.userId}')">
		     				<h4 class="panel-title" style="color: #336699">
		        			<a role="button" data-toggle="collapse" data-parent="#accordion2" href="#${fn:replace(projectManagers.employeeName,' ', '')}" aria-expanded="true" aria-controls="${projectManagers.employeeName}" onclick="return test2('${projectManagers.userId}')">
		        			${projectManagers.employeeName}
		        			</a>
		        				<span class="ed">ED : ${projectManagers.ED}<img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
        						<span class="eg">EG : ${projectManagers.EG}<img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
       							 <span class="chc">CHC : ${projectManagers.CHC}&nbsp;&nbsp;&nbsp;</span>
       						</h4>
		      			</div>
		      			<div id="${fn:replace(projectManagers.employeeName,' ', '')}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${projectManagers.employeeName}">
      						<div class="panel-body">
      							<c:if test="${not empty projectManagers.assetBeans}">  
					      		<div class="x_panel">
								<table id="456_${projectManagers.userId}" class="table table-hover" >
								<thead>
									<tr>
									<th>Project Name</th>
									<th>Current Head Count</th>
									<th>Growth/Decline</th>
									<th>Cisco Manager Id</th>
									<th>Cisco Manager Name</th>
									<th>Quarter (${sessionScope.quarter})</th>
									<th>Project Location</th>
								<!-- 	<th>Project Manager</th>
									<th>Program Manager</th>
									<th>Delivery Head</th> -->
									</tr>
								</thead>
								<tbody>
									<c:forEach var="asset" items="${projectManagers.assetBeans}">
									<tr data-toggle="modal" data-id="${asset.projectId}" onclick="return assetDetails(${asset.projectId})" data-target="#assetDetails">
							   			<td><c:out value="${asset.projectName}"/></td>
							   			<td class="align-center"><c:out value="${asset.currentHeadCount}"/></td>
							   			<td class="align-center"><c:out value="${asset.growthCount}"/>
							   				<c:if test="${asset.growthStatus eq 'growth'}">
		   										</c:if>
		   									<c:if test="${asset.growthStatus ne 'growth'}">
		   									</c:if>
							   			</td>
							   			<td><c:out value="${asset.ciscoManagerId}"/></td>
							   			<td><c:out value="${asset.ciscoManagerName}"/></td>
							   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   								<c:if test="${asset.quarter ne sessionScope.quarter}"><b>DATA NOT SET</b></c:if>
		   								</td>
							   			<td><c:out value="${asset.projectLocation}"/></td>
							   			<%-- <td><c:out value="${asset.projectManager}"/></td>
		   								<td><c:out value="${asset.programManager}"/></td>
		   								<td><c:out value="${asset.deliveryHead}"/></td> --%>
							   		</tr>
									</c:forEach>
								</tbody>
								</table>
								</div>
							</c:if>
							<c:if test="${empty projectManagers.assetBeans}">No Project Tracks Yet</c:if>
      						</div>
      					</div>
    				</div>
				</c:forEach>
				</c:forEach>
				</c:forEach>
			</div>
      </div>
<div id="integrated" style="display:none;">
<h4 style="color: #336699">Integrated View:</h4>
   <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="Integrated View">
		     				<h4 class="panel-title" style="color: #336699">
		        			<a role="button" data-toggle="collapse" data-parent="#accordion2" href="#integratedView" aria-expanded="true" aria-controls="integrated">
		        			Integrated View
		        			</a>
		        			<c:set var="ED" /><c:set var="EG"/><c:set var="CHC"/>
		        			<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
		        				<c:forEach var="programManagers" items="${deliveryHead.employeeBeans}">						 
        							<c:forEach var="employee" items="${programManagers.employeeBeans}">
	        						<c:set var="CHC" value="${employee.CHC+CHC}" />
	        						<c:set var="EG" value="${employee.EG+EG}" />
	        						<c:set var="ED" value="${employee.ED+ED}" />
	       							</c:forEach>
       							</c:forEach>
       						</c:forEach>
       						 <span class="ed">ED : ${ED}<img alt="down" src="<c:url value="/resources/images/downarrow.png"/>"/></span>
       						 <span class="eg">EG : ${EG}<img alt="up" src="<c:url value="/resources/images/uparrow.png"/>"/>&nbsp;&nbsp;&nbsp;</span>
        					 <span class="chc">CHC : ${CHC}&nbsp;&nbsp;&nbsp;</span>
       						</h4>
		      			</div>
		      			<div id="integratedView" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="integrated">
      							<div class="panel-body">
					      		<table id="assets" class="table table-hover">
										<thead>
										<tr>
										<th>Project Name</th>
										<th>Current Head Count</th>
										<th>Growth/Decline</th>
										<th>Cisco Manager Id</th>
										<!-- <th>Cisco Manager Name</th> -->
										<th>Quarter (${sessionScope.quarter})</th>
										<th>Project Location</th>
										<th>Project Manager</th>
										<th>Program Manager</th>
										<th>Delivery Head</th>
										</tr>
										</thead>
								<tbody>
								<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
								<c:forEach var="programManagers" items="${deliveryHead.employeeBeans}">
									<c:forEach var="projectManagers" items="${programManagers.employeeBeans}">
									<c:forEach var="asset" items="${projectManagers.assetBeans}">
									<tr data-toggle="modal" data-id="${asset.projectId}" onclick="return assetDetails(${asset.projectId})" data-target="#assetDetails">
							   			<td><c:out value="${asset.projectName}"/></td>
							   			<td class="align-center"><c:out value="${asset.currentHeadCount}"/></td>
							   			<td class="align-center"><c:out value="${asset.growthCount}"/>
							   				<c:if test="${asset.growthStatus eq 'growth'}">
		   										</c:if>
		   									<c:if test="${asset.growthStatus ne 'growth'}">
		   									</c:if>
							   			</td>
							   			<td><c:out value="${asset.ciscoManagerId}"/></td>
							   			<%-- <td><c:out value="${asset.ciscoManagerName}"/></td> --%>
							   			<td><c:if test="${asset.quarter eq sessionScope.quarter}"><c:out value="${asset.quarter}"/></c:if>
		   								<c:if test="${asset.quarter ne sessionScope.quarter}"><b>DATA NOT SET</b></c:if>
		   								</td>
							   			<td><c:out value="${asset.projectLocation}"/></td>
							   			<td><c:out value="${asset.projectManager}"/></td>
		   								<td><c:out value="${asset.programManager}"/></td>
		   								<td><c:out value="${asset.deliveryHead}"/></td>
							   		</tr>
									</c:forEach>
									</c:forEach>
								</c:forEach>
								</c:forEach>
								</tbody>
								</table>
      						</div>
      					</div>
    				</div>
			</div>
      </div>
<button id="addTrackPopUp" class="btn btn-primary" data-toggle="modal" data-target="#addAsset">Add Track</button>
<button id="exportCSVPopUp" class="btn btn-primary" data-toggle="modal" data-target="#exportCSV">Export</button>