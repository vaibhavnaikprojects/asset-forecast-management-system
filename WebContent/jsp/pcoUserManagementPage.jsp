<%@page import="com.zensar.afnls.util.AFMSConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/dataTable.css"/>" rel="stylesheet" type="text/css" />
<script  type="text/javascript" src="<c:url value="/resources/js/dataTable.js"/>"></script>

<script type="text/javascript">
function employeeDetails(userId){
	$.ajax({
		dataType: "json",
		url: "getEmployeeDetailsFromId.html?userId="+userId,
		type: "GET",
		success: function(data) {
			if(data!=null){
				deactivateEmployee.userId.value=data.userId;
				deactivateEmployee.ogdesignation.value=data.designation;
				activateEmployee.userId.value=data.userId;
				activateEmployee.ogdesignation.value=data.designation;
				updateEmployee.employeeName.value=data.employeeName;
				updateEmployee.designation.value=data.designation;
				updateEmployee.ogdesignation.value=data.designation;
				updateEmployee.userId.value=data.userId;
				updateEmployee.managerId.value=data.managerId;
				updateEmployee.managerId2Up.value=data.managerId2Up;
				updateEmployee.CHC.value=data.CHC;
				updateEmployee.EG.value=data.EG;
				updateEmployee.ED.value=data.ED;
				var status=data.status;
				if(status==='A'){
					document.getElementById("activate").style.display="none";
					document.getElementById("deactivate").style.display="block";
				}
				else{
					document.getElementById("deactivate").style.display="none";
					document.getElementById("activate").style.display="block";
				}
				
			}
			else{alert("data not loaded");}
		},error: function(data) {
			alert( "Sorry, there was a problem!" );
			}
		});
}
function managerName()
{
	var id=document.getElementById("userId").value;
        
$.ajax({
url: "getManagerName.html?empId="+id,
type: "GET",
success: function(data) {
	
	if(data=="Invalid CEC ID")
		{
		document.getElementById("employeeName").value=data;
		return false;
		}
	else
		{
		document.getElementById("employeeName").value=data;
		return true;
		} 
},error: function() {
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
 $(document).ready(function() {
    $(":checkbox").change(function(event) { 
   		
   		
    	var name = this.name;
    	
    	if(this.checked) { 
    		
            $("."+name+"delegate").each(function() {
        
                this.checked = true;              
            });
        }else{
            $("."+name+"delegate").each(function() {
        
                this.checked = false;                       
            });        
        }
    });
   
}); 
 
 $(document).ready(function() {
	    $("#selectallintegrated").change(function(event) { 
	   		
	   		if(this.checked) { 
	    		
	            $(".delegateintegrated").each(function() {
	        
	                this.checked = true;              
	            });
	        }else{
	            $(".delegateintegrated").each(function() {
	        
	                this.checked = false;                       
	            });        
	        }
	    });
	   
	}); 
$(document).ready(function() {
    $('#allselect').click(function(event) { 
        if(this.checked) { 
            $('.empSub').each(function() { 
                this.checked = true;              
            });
        }else{
            $('.empSub').each(function() { 
                this.checked = false;                       
            });        
        }
    });
   
});

function addingUsermanagementCheck(designation){
	$.ajax({
		dataType: "json",
		url: "getDesigValues.html?desig="+designation,
		type: "GET",	
		success: function(data) {
			alert("Response is " +data.employeeDesig);
		},error: function(data) {
			alert( "Sorry, there was a problem!" );
		}
	});
}
</script>
<c:set var="odcHead"/>
<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
<c:if test="${employee.designation eq 'Cisco ODC Head'}">
	<c:set var="odcHead" value="${employee}"/>
</c:if>
</c:forEach>
<!-- Modal For Adding User -->
<div class="modal fade" id="addUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add User</h4>
      </div>
      <form id="addUser" name="addUser" action="addUser.html" method="post">
      <div class="modal-body">
      		<table class="table table-hover">
      			<tr>
      				<td>User ID</td>
      				<td><input type="text" class="form-control" name="userId" id="userId" required="required" onblur="return managerName()"/></td>
      			</tr>
      			<tr>
      				<td>Employee Name</td>
      				<td><input type="text" class="form-control" name="employeeName" id="employeeName" required="required" readonly="readonly"/></td>
      			</tr>
      			<tr>
      				<td>Designation</td>
      				<td>
      				<select class="form-control" id="designation" name="designation" required="required" onblur="return addingUsermanagementCheck()">
						<option value="" disabled selected>Please Select Designation</option>
							<option value="Project Manager">Project Manager</option>
							<option value="Program Manager">Program Manager</option>
							<option value="Delivery Head">Delivery Head</option>
							<option value="Cisco ODC Head">Cisco ODC Head</option>
							<option value="PCO Team">PCO Team</option>
					</select>
					</td>
      			</tr>
      			
      			<tr>
      				<td>Manager Id</td>
      				<td><input type="text" class="form-control" name="managerId" id="managerId" required="required" /></td>
      			</tr>
      			<tr>
      			<td>managerId 2Up</td>
      				<td><input type="text" class="form-control" name="managerId2Up" id="managerId2Up" required="required" readonly="readonly"/></td>
      			</tr>
        	</table>
	  </div>
      <div class="modal-footer">
	  			<p>Note : Disabled fields are auto populated</p>
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary">Add User</button>
      </div>
      </form>
    </div>
  </div>
</div> 
<!-- Modal For Updating User -->
<div class="modal fade" id="employeeDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Edit User</h4>
      </div>
      <form id="updateEmployee" action="updateUser.html" method="post">
       <input type="hidden" id="ogdesignation" name="ogdesignation"/>
      <div class="modal-body">
				<table class="table table-hover">
      			<tr>
      				<td>User ID</td>
      				<td><input type="text" class="form-control" name="userId" id="userId" required="required" /></td>
      			</tr>
      			<tr>
      				<td>Employee Name</td>
      				<td><input type="text" class="form-control" name="employeeName" id="employeeName" required="required" readonly="readonly"/></td>
      			</tr>
      			<tr>
      				<td>Designation</td>
      				<td>
      				<select class="form-control" id="designation" name="designation" required="required" onselect="javascript:">
						<option value="" disabled selected>Please Select Designation</option>
							<option value="Project Manager">Project Manager</option>
							<option value="Program Manager">Program Manager</option>
							<option value="Delivery Head">Delivery Head</option>
							<option value="Cisco ODC Head">Cisco ODC Head</option>
							<option value="PCO Team">PCO Team</option>
					</select>
					</td>
      			</tr>
      			<tr>
      				<td>Manager Id</td>
      				<td><input type="text" class="form-control" name="managerId" id="managerId" required="required" /></td>
      			</tr>
      			<tr>
      			<td>managerId 2Up</td>
      			<td><input type="text" class="form-control" name="managerId2Up" id="managerId2Up" required="required"/></td>
      			</tr>
      			<tr>
      			<td>Total Assets</td>
      			<td><input type="text" class="form-control" name="CHC" id="CHC" required="required" readonly="readonly"/></td>
      			</tr>
      			<tr>
      			<td>Expected Growth</td>
      			<td><input type="text" class="form-control" name="EG" id="EG" required="required" readonly="readonly"/></td>
      			</tr>
      			<tr>
      			<td>Expected Decline</td>
      			<td><input type="text" class="form-control" name="ED" id="ED" required="required" readonly="readonly"/></td>
      			</tr>
      			
        	</table>
	  </div>
      <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary">Update User</button>
        	</form>
        	<div id="deactivate">
        	<form id="deactivateEmployee" method="post" action="deactivateUser.html">
      			<input type="hidden" class="form-control" name="userId" id="userId"/>
      			<input type="hidden" class="form-control" name="ogdesignation" id="ogdesignation"/>
      			<c:if test="${sessionScope.employee.designation eq 'PCO Team'}">
      				<button type="submit" class="btn btn-danger " style="position: absolute; top:92.5%;left:3%;">Deactivate User</button>
      			</c:if>
      		</form>
      		</div>
      		<div id="activate">
        	<form id="activateEmployee" method="post" action="activateUser.html">
      			<input type="hidden" class="form-control" name="userId" id="userId"/>
      			<input type="hidden" class="form-control" name="ogdesignation" id="ogdesignation"/>
      			<c:if test="${sessionScope.employee.designation eq 'PCO Team'}">
      				<button type="submit" class="btn btn-danger " style="position: absolute; top:92.5%;left:3%;">Activate User</button>
      			</c:if>
      		</form>
      		</div>
      </div>
    </div>
  </div>

<div class="col-xs-3"> <!-- required for floating -->
  <!-- Nav tabs -->
  <ul class="nav tabs-left"><!-- 'tabs-right' for right tabs -->
      <li><a href="#uploadProjectTrack" data-toggle="tab">Upload Project Track</a></li>
    <li class="active"><a href="#maintainUsers" data-toggle="tab">Maintain Users</a></li>
    <li><a href="#maintainDeactivatedUsers" data-toggle="tab">Maintain Deactivated Users</a></li>
    <li><a href="#maintainDeactivatedAssets" data-toggle="tab">Delegate Assets</a></li>
    <li><a href="#quarterSubmission" data-toggle="tab">Quarterly Asset Updates</a></li>
  
  </ul>
</div>
<div class="col-xs-8">
    <!-- Tab panes -->
    <div class="tab-content">
    
    <div class="tab-pane " id="uploadProjectTrack">
      <h4 style="color: #336699"><b><c:if test="${not empty employeeBean}">${employeeBean.employeeName}</c:if>
		${success}
		<span style="color:red;">${message}</span>
		</b></h4>
		 <div class="panel-body">
		      <form action="upload.html" id="upload" method="post" enctype="multipart/form-data">
		     	<label for="Project">Bulk Upload :  Project Track</label>
						<input type="file" id="uploadtrack" name="uploadtrack"
							required="required" data-buttonName="btn-primary" class="btn-primary"/> 
							<input type="hidden"
							 name="requestType" id="requestType"
							value="2" />
		<button id="addUserPopUp" class="btn btn-primary" >Upload </button>
		</form></div>
      </div>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
      <div class="tab-pane active" id="maintainUsers">
      <h4 style="color: #336699"><b><c:if test="${not empty employeeBean}">${employeeBean.employeeName}</c:if>
		${success}
		<span style="color:red;">${message}</span>
		</b></h4>
		<h4 style="color: #336699">Users :</h4>
      	<div class="gridView">
			<table id="employee5" class="gridView">
			<thead>
				<tr>
				<th>Employee Name</th>
				<th>designation</th>
				<th>userId</th>
				<th>Manager Id</th>
				<th>2 UP Manager Id</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
				<c:if test="${employee.status eq 'A'}">
					<tr class="grid"  data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
			   			<td><c:out value="${employee.employeeName}"/></td>
			   			 
			   			 <c:choose>
    							<c:when test="${employee.designation == 'LPT'}">
						  <td><c:out value="<%=AFMSConstant.ITO %>"/></td>
						    </c:when>
						    <c:otherwise>
						      <td><c:out value="${employee.designation}"/></td>
						    </c:otherwise>
						</c:choose>
			   			
			   			<td><c:out value="${employee.userId}"/></td>
			   			<td><c:out value="${employee.managerId}"/></td>
			   			<td><c:out value="${employee.managerId2Up}"/></td>
			   		</tr>
				</c:if>
				</c:forEach>
				<c:forEach var="employee" items="${odcHead.employeeBeans}">
				<c:if test="${employee.status eq 'A'}">
					<tr data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
			   			<td><c:out value="${employee.employeeName}"/></td>
			   			<c:choose>
    							<c:when test="${employee.designation == 'LPT'}">
						  <td><c:out value="<%=AFMSConstant.ITO %>"/></td>
						    </c:when>
						    <c:otherwise>
						      <td><c:out value="${employee.designation}"/></td>
						    </c:otherwise>
						</c:choose>
			   			<td><c:out value="${employee.userId}"/></td>
			   			<td><c:out value="${employee.managerId}"/></td>
			   			<td><c:out value="${employee.managerId2Up}"/></td>
			   		</tr>
				</c:if>
				</c:forEach>
				<c:forEach var="prograManager" items="${odcHead.employeeBeans}">
				<c:forEach var="employee" items="${prograManager.employeeBeans}">
				<c:if test="${employee.status eq 'A'}">
					<tr data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
			   			<td><c:out value="${employee.employeeName}"/></td>
			   			<c:choose>
    							<c:when test="${employee.designation == 'LPT'}">
						  <td><c:out value="<%=AFMSConstant.ITO %>"/></td>
						    </c:when>
						    <c:otherwise>
						      <td><c:out value="${employee.designation}"/></td>
						    </c:otherwise>
						</c:choose>
			   			<td><c:out value="${employee.userId}"/></td>
			   			<td><c:out value="${employee.managerId}"/></td>
			   			<td><c:out value="${employee.managerId2Up}"/></td>
			   		</tr>
				</c:if>
				</c:forEach>
				</c:forEach>
				<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
					<c:forEach var="prograManager" items="${deliveryHead.employeeBeans}">
						<c:forEach var="employee" items="${prograManager.employeeBeans}">
						<c:if test="${employee.status eq 'A'}">
							<tr data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
					   			<td><c:out value="${employee.employeeName}"/></td>
					   			<c:choose>
    							<c:when test="${employee.designation == 'LPT'}">
						  <td><c:out value="<%=AFMSConstant.ITO %>"/></td>
						    </c:when>
						    <c:otherwise>
						      <td><c:out value="${employee.designation}"/></td>
						    </c:otherwise>
						</c:choose>
					   			<td><c:out value="${employee.userId}"/></td>
					   			<td><c:out value="${employee.managerId}"/></td>
					   			<td><c:out value="${employee.managerId2Up}"/></td>
					   		</tr>
						</c:if>	
						</c:forEach>
					</c:forEach>
				</c:forEach>
			</tbody>
			</table>
		</div>
		<button id="addUserPopUp" class="btn btn-primary" data-toggle="modal" data-target="#addUser">Add User</button>
      </div>
      
      
      
      
      
      
      
      
      
      
      
      <!-- maintain deactive users -->
      
      
      
      
      
      <div class="tab-pane" id="maintainDeactivatedAssets">
      <h4 style="color: #336699"><b><c:if test="${not empty asset}">${asset.projectName}</c:if>
	${delegatemessage}</b></h4>
		<div class="view-filter">
		<span  style="color: #336699;">View Filter</span> 
		<select name="viewFilter" id="viewFilter" onchange="viewchange()">
			<option value="dh">DH View</option>
			<option value="fork">PGM View</option>
			<option value="forkjoin">PM View</option>
			<option value="join">Integrated View </option>
		</select>
	</div>
	<div id="DHView">
		<h4 style="color: #336699">Delegate Project Tracks Under Delivery Head</h4>
      	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
      	 <c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
      	 <c:if test="${deliveryHead.status eq 'A'}">
      	<div class="panel panel-default">
		    <div class="panel-heading" role="tab" id="headingOne">
		      <h4 class="panel-title" style="color: #336699">
		        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#${deliveryHead.userId}" aria-expanded="true" aria-controls="collapseOne">
		         ${deliveryHead.employeeName}
		        </a>
		      </h4>
		    </div>
		    <div id="${deliveryHead.userId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
		      <div class="panel-body">
		      <form action="delegateAssets.html" id="delegeateAssets" method="post">
		      <label for="delegateTo">Delegate To</label>
				<select class="form-control" id="delegateTo" name="delegateTo" onchange="createJsonObject(this.options[this.selectedIndex].value)" required="required">
						<option value="" disabled selected>Please Select Project Manager</option>
						<c:forEach var="e1" items="${odcHead.employeeBeans}">
							<c:forEach var="emp" items="${e1.employeeBeans}">
								<c:forEach var="emp1" items="${emp.employeeBeans}">
										<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
								</c:forEach>
						</c:forEach>
						</c:forEach>
				</select>
				<div class="table-responsive">
			      <table id="employee3" class="table table-hover">
						<thead>
							<tr>
							<th>Track Name</th>
							<th>Current Head Count</th>
							<th>Cisco Manager Name</th>
							<th>Project Manager</th>
							<th style="text-align: right;">To Delegate &nbsp;<input type="checkbox" id="selectall"  name="${deliveryHead.userId}" /></th>
							
							</tr>
						</thead>
						<c:forEach var="programManager" items="${deliveryHead.employeeBeans}">
						<c:forEach var="projectManager" items="${programManager.employeeBeans}">
			        	<c:forEach var="asset" items="${projectManager.assetBeans}">
			        		<tr >
			        		<td><c:out value="${asset.projectName}"/></td>
			        		<td><c:out value="${asset.currentHeadCount}"/></td>
			        		<td><c:out value="${asset.ciscoManagerName}"/></td>
			        		<td><c:out value="${projectManager.employeeName}"/></td>
			        		<td style="text-align: right;"><input type="checkbox" class="${deliveryHead.userId}delegate" id="delegate" name="delegate" value="${asset.projectId}"></td>
			        		
			        		</tr>
			        	</c:forEach>
			        	</c:forEach>
			        	</c:forEach>
			        </table></div>
			        <input type="submit" class="btn btn-primary" value="Delegate">
			        </form>
		      </div>
		    </div>
		  </div>
		  </c:if>
		  </c:forEach>
       </div>
       </div>
		<div id="PGMView" style="display:none;">
		<h4 style="color: #336699">Delegate Project Tracks Under Program manager</h4>
     
      	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
      	 <c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
       		<c:forEach var="employee" items="${deliveryHead.employeeBeans}">
       		<c:if test="${employee.status eq 'A'}">
      	<div class="panel panel-default">
		    <div class="panel-heading" role="tab" id="headingOne">
		      <h4 class="panel-title" style="color: #336699">
		        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#${employee.userId}" aria-expanded="true" aria-controls="collapseOne">
		         ${employee.employeeName}
		        </a>
		      </h4>
		    </div>
		    <div id="${employee.userId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
		      <div class="panel-body">
		      <form action="delegateAssets.html" id="delegeateAssets" method="post">
		      <label for="delegateTo">Delegate To</label>
				<select class="form-control" id="delegateTo" name="delegateTo" onchange="createJsonObject(this.options[this.selectedIndex].value)" required="required">
						<option value="" disabled selected>Please Select Project Manager</option>
						<c:forEach var="e1" items="${odcHead.employeeBeans}">
							<c:forEach var="emp" items="${e1.employeeBeans}">
								<c:forEach var="emp1" items="${emp.employeeBeans}">
										<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
								</c:forEach>
						</c:forEach>
						</c:forEach>
				</select>
			      <table id="employee4" class="gridView">
						<thead>
							<tr class="grid">
							<th>Track Name</th>
							<th>Current Head Count</th>
							<th>Cisco Manager Name</th>
							<th>Project Manager</th>
							<th style="text-align: right;">To Delegate (Please check CheckBox)&nbsp;&nbsp;<input type="checkbox" id="selectall" name="${employee.userId}"/></th>
							
							</tr>
						</thead>
						<c:forEach var="projectManager" items="${employee.employeeBeans}">
			        	<c:forEach var="asset" items="${projectManager.assetBeans}">
			        		<tr class="grid">
			        		<td><c:out value="${asset.projectName}"/></td>
			        		<td><c:out value="${asset.currentHeadCount}"/></td>
			        		<td><c:out value="${asset.ciscoManagerName}"/></td>
			        		<td><c:out value="${projectManager.employeeName}"/></td>
			        		<td style="text-align: right;"><input type="checkbox" class="${employee.userId}delegate" id="delegate" name="delegate" value="${asset.projectId}"></td>
			        		
			        		</tr>
			        	</c:forEach>
			        	</c:forEach>
			        </table>
			        <input type="submit" class="btn btn-primary" value="Delegate">
			        </form>
		      </div>
		    </div>
		  </div>
		  </c:if>
		  </c:forEach>
		  </c:forEach>
       </div>
       
       </div>
       <div id="PMView" style="display:none;">
       <h4 style="color: #336699">Delegate Project Tracks Under Project Manager</h4>
          <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
       <c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
    	<c:forEach var="programManagers" items="${deliveryHead.employeeBeans}">
				<c:forEach var="projectManagers" items="${programManagers.employeeBeans}">
				<c:if test="${projectManagers.status eq 'A'}">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="${projectManagers.employeeName}">
		     				<h4 class="panel-title" style="color: #336699">
		        			<a role="button" data-toggle="collapse" data-parent="#accordion2" href="#${fn:replace(projectManagers.employeeName,' ', '')}" aria-expanded="true" aria-controls="${projectManagers.employeeName}">
		        			${projectManagers.employeeName}
		        			</a>
		        			</h4>
		      			</div>
		      			<div id="${fn:replace(projectManagers.employeeName,' ', '')}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="${projectManagers.employeeName}">
      						<div class="panel-body">
      							<c:if test="${not empty projectManagers.assetBeans}">
      							<form action="delegateAssets.html" id="delegeateAssets" method="post">
      							  <label for="delegateTo">Delegate To</label>
									<select class="form-control" id="delegateTo" name="delegateTo" required="required" onchange="createJsonObject(this.options[this.selectedIndex].value)">
											<option value="" disabled selected>Please Select Project Manager</option>
											<c:forEach var="e1" items="${odcHead.employeeBeans}">
												<c:forEach var="emp" items="${e1.employeeBeans}">
													<c:forEach var="emp1" items="${emp.employeeBeans}">
														<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
													</c:forEach>
											</c:forEach>
											</c:forEach>
									</select>
					      		<div class="table-responsive">
								<table id="assets20" class="table table-hover">
								<thead>
									<tr>
									<th>Project Name</th>
									<th>Current Head Count</th>
									<th>Cisco Manager Name</th>
									<th style="text-align: right;">To Delegate (Please check CheckBox)&nbsp;&nbsp;<input type="checkbox" id="selectall" name="${projectManagers.userId}"/></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="asset" items="${projectManagers.assetBeans}">
									<tr>
							   			<td><c:out value="${asset.projectName}"/></td>
							   			<td class="align-center"><c:out value="${asset.currentHeadCount}"/></td>
							   			<td><c:out value="${asset.ciscoManagerName}"/></td>
							   			
							   			<td style="text-align: right;"><input type="checkbox" class="${projectManagers.userId}delegate" id="delegate" name="delegate" value="${asset.projectId}"></td>	
							   		</tr>
									</c:forEach>
								</tbody>
								</table>
							</div>
							<input type="submit" class="btn btn-primary" value="Delegate">
							</form>
							</c:if>
							<c:if test="${empty projectManagers.assetBeans}">No Project Tracks Yet</c:if>
      						</div>
      					</div>
    				</div>
    				</c:if>
				</c:forEach>
				</c:forEach>
				</c:forEach>
			</div>
       </div>
     <div id="integrated" style="display:none;">
     <h4 style="color: #336699">Delegate Project Tracks</h4>
   <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="${projectManagers.employeeName}">
		     				<h4 class="panel-title" style="color: #336699">
		        			<a role="button" data-toggle="collapse" data-parent="#accordion2" href="#integratedView" aria-expanded="true" aria-controls="integrated">
		        			Integrated View
		        			</a>
       						</h4>
		      			</div>
		      			<div id="integratedView" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="integrated">
      							<div class="panel-body">
      							<form action="delegateAssets.html" id="delegeateAssets" method="post">
      							<label for="delegateTo">Delegate To</label>
									<select class="form-control" id="delegateTo" name="delegateTo" required="required" onchange="createJsonObject(this.options[this.selectedIndex].value)">
											<option value="" disabled selected>Please Select Project Manager</option>
											<c:forEach var="e1" items="${odcHead.employeeBeans}">
											<c:forEach var="emp" items="${e1.employeeBeans}">
													<c:forEach var="emp1" items="${emp.employeeBeans}">
													 	<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
													</c:forEach>
											</c:forEach>
											</c:forEach>
									</select>
					      			<div class="table-responsive">
										<table id="assets20" class="gridView">
										<thead>
										<tr>
										<th>Project Name</th>
										<th>Current Head Count</th>
										<th>Cisco Manager Name</th>
										<th>Project Manager</th>
										<th style="text-align: right;">To Delegate (Please check CheckBox)&nbsp;&nbsp;<input type="checkbox" id="selectallintegrated" /></th>
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
							   			<td><c:out value="${asset.ciscoManagerName}"/></td>
							   			<td><c:out value="${asset.projectManager}"/></td>
							   			<td style="text-align: right;"><input type="checkbox" class="delegateintegrated" id="delegate" name="delegate" value="${asset.projectId}"></td>
							   		</tr>
									</c:forEach>
									</c:forEach>
								</c:forEach>
								</c:forEach>
								</tbody>
								</table>
							</div>
      						<input type="submit" class="btn btn-primary" value="Delegate">
      						</form>
      						</div>
      					</div>
    				</div>
			</div>
      </div>
      
      
      </div>
      <div class="tab-pane" id="maintainDeactivatedUsers">
      <h4 style="color: #336699"><b><c:if test="${not empty employeeBean}">${employeeBean.employeeName}</c:if>
				${success}
				<span style="color:red;">${message}</span>
				</b></h4>
				<h4 style="color: #336699">Deactivated Users :</h4>
      	 <div class="table-responsive">
					<table id="employee" class="gridView">
					<thead>
						<tr>
						<th>Employee Name</th>
						<th>designation</th>
						<th>userId</th>
						<th>Manager Id</th>
						<th>2 UP Manager Id</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
						<c:if test="${employee.status ne 'A'}">
							<tr data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
					   			<td><c:out value="${employee.employeeName}"/></td>
					   			<td><c:out value="${employee.designation}"/></td>
					   			<td><c:out value="${employee.userId}"/></td>
					   			<td><c:out value="${employee.managerId}"/></td>
					   			<td><c:out value="${employee.managerId2Up}"/></td>
					   		</tr>
						</c:if>
						</c:forEach>
						<c:forEach var="employee" items="${odcHead.employeeBeans}">
				<c:if test="${employee.status ne 'A'}">
					<tr class="grid" data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
			   			<td><c:out value="${employee.employeeName}"/></td>
			   			<td><c:out value="${employee.designation}"/></td>
			   			<td><c:out value="${employee.userId}"/></td>
			   			<td><c:out value="${employee.managerId}"/></td>
			   			<td><c:out value="${employee.managerId2Up}"/></td>
			   		</tr>
				</c:if>
				</c:forEach>
				<c:forEach var="prograManager" items="${odcHead.employeeBeans}">
				<c:forEach var="employee" items="${prograManager.employeeBeans}">
				<c:if test="${employee.status ne 'A'}">
					<tr data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
			   			<td><c:out value="${employee.employeeName}"/></td>
			   			<td><c:out value="${employee.designation}"/></td>
			   			<td><c:out value="${employee.userId}"/></td>
			   			<td><c:out value="${employee.managerId}"/></td>
			   			<td><c:out value="${employee.managerId2Up}"/></td>
			   		</tr>
				</c:if>
				</c:forEach>
				</c:forEach>
				<c:forEach var="deliveryHead" items="${odcHead.employeeBeans}">
					<c:forEach var="prograManager" items="${deliveryHead.employeeBeans}">
						<c:forEach var="employee" items="${prograManager.employeeBeans}">
						<c:if test="${employee.status ne 'A'}">
							<tr data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
					   			<td><c:out value="${employee.employeeName}"/></td>
					   			<td><c:out value="${employee.designation}"/></td>
					   			<td><c:out value="${employee.userId}"/></td>
					   			<td><c:out value="${employee.managerId}"/></td>
					   			<td><c:out value="${employee.managerId2Up}"/></td>
					   		</tr>
						</c:if>	
						</c:forEach>
					</c:forEach>
				</c:forEach>
					</tbody>
				</table>
			</div>
      </div>
      <div class="tab-pane" id="quarterSubmission">
        <h4 style="color: #336699"><b><c:if test="${not empty employeeBean}">${employeeBean.employeeName}</c:if>
				${success}
				<span style="color:red;">${message}</span>
				</b></h4>
				<h4 style="color: #336699">Quarterly Asset Submission :</h4>
      	<div class="table-responsive">
      		<form action="reminderMail.html" id="reminderMail" method="post">
      		<input type="submit" class="btn btn-primary" value="reminder">
					<table id="employee1" class="gridView">
					<thead>
						<tr>
						<th>User Id</th>
						<th>Employee Name</th>
						<th>Submission</th>
						<th style="text-align: right;">Reminder&nbsp;&nbsp;<input type="checkbox" id="allselect"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="employee" items="${sessionScope.employeeSubmission}">
							<tr class="grid">
					   			<td><c:out value="${employee.userId}"/></td>
					   			<td><c:out value="${employee.employeeName}"/></td>
					   			<td><c:out value="${employee.validator}"/></td>
					   			<c:choose>
					   				<c:when test="${employee.validator ne 'validated'}">
					   				<td style="text-align: right;"><input type="checkbox" class="empSub" id="empSub" name="empSub" value="${employee.userId}"></td>
					   				</c:when>
					   				<c:when test="${employee.validator eq 'validated'}">
					   				<td style="text-align: right;"><img src="<c:url value="/resources/images/check.png"/>" alt="submited"></td>
					   				</c:when>
					   			</c:choose>
					   		</tr>
						</c:forEach>
					</tbody>
				</table>
				</form>
			</div>
      </div>
    </div>
</div>
