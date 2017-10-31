<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
function employeeDetails(userId){
	$.ajax({
		dataType: "json",
		url: "getEmployeeDetailsFromId.html?userId="+userId,
		type: "GET",
		success: function(data) {
			if(data!=null){
				updateEmployee.employeeName.value=data.employeeName;
				updateEmployee.designation.value=data.designation;
				updateEmployee.ogdesignation.value=data.designation;
				updateEmployee.userId.value=data.userId;
				updateEmployee.managerId.value=data.managerId;
				updateEmployee.managerId2Up.value=data.managerId2Up;
				updateEmployee.CHC.value=data.CHC;
				updateEmployee.EG.value=data.EG;
				updateEmployee.ED.value=data.ED;
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
</script>

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
      				<td><input type="text" class="form-control" name="userId" id="userId" required="" onblur="return managerName()"/></td>
      			</tr>
      			<tr>
      				<td>Employee Name</td>
      				<td><input type="text" class="form-control" name="employeeName" id="employeeName" required="" readonly="readonly"/></td>
      			</tr>
      			<tr>
      				<td>Designation</td>
      				<td>
						<select class="form-control" id="designation" name="designation" required="">
							<option value="" disabled selected>Please Select Designation</option>
							<option value="Project Manager">Project Manager</option>
							<option value="Program Manager">Program Manager</option>
							<option value="Delivery Head">Delivery head</option>
						</select>
					</td>
      			</tr>
      			<tr>
      				<td>Manager Id</td>
      				<td><input type="text" class="form-control" id="managerId" name="managerId" required="required"/></td>
      			</tr>
      			<tr>
      			<td>managerId 2Up</td>
      				<td><input type="text" class="form-control" name="managerId2Up" id="managerId2Up" /></td>
      			</tr>
        	</table>
	  </div>
      <div class="modal-footer">
			<p>Note : Disabled fields are auto populated/p>
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
      				<td><input type="text" class="form-control" name="userId" id="userId" required="" /></td>
      			</tr>
      			<tr>
      				<td>Employee Name</td>
      				<td><input type="text" class="form-control" name="employeeName" id="employeeName" required="" readonly="readonly"/></td>
      			</tr>
      			<tr>
      				<td>Designation</td>
      				<td>
      				<select class="form-control" id="designation" name="designation" required="">
						<option value="" disabled selected>Please Select Designation</option>
							<option value="Project Manager">Project Manager</option>
							<option value="Program Manager">Program Manager</option>
					</select>
					</td>
      			</tr>
      			<tr>
      				<td>Manager Id</td>
      				<td><input type="text" class="form-control" name="managerId" id="managerId" required="" /></td>
      			</tr>
      			<tr>
      			<td>managerId 2Up</td>
      			<td><input type="text" class="form-control" name="managerId2Up" id="managerId2Up" required=""/></td>
      			</tr>
      			<tr>
      			<td>Total Assets</td>
      			<td><input type="text" class="form-control" name="CHC" id="CHC" required="" readonly="readonly"/></td>
      			</tr>
      			<tr>
      			<td>Expected Growth</td>
      			<td><input type="text" class="form-control" name="EG" id="EG" required="" readonly="readonly"/></td>
      			</tr>
      			<tr>
      			<td>Expected Decline</td>
      			<td><input type="text" class="form-control" name="ED" id="ED" required="" readonly="readonly"/></td>
      			</tr>
      			
        	</table>
	  </div>
      <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary">Update User</button>
      </div>
      </form>
    </div>
  </div>
</div> 
<div class="col-xs-3"> <!-- required for floating -->
  <!-- Nav tabs -->
  <ul class="nav tabs-left">
    	<li class="active"><a href="#maintainUsers" data-toggle="tab">Maintain Users</a></li>
	    <li><a href="#maintainDeactivatedAssets" data-toggle="tab">Delegate Assets</a></li>
  </ul>
</div>
<div class="col-xs-9">
    <!-- Tab panes -->
    <div class="tab-content">
      <div class="tab-pane active" id="maintainUsers">
      <h4 style="color: #336699"><b><c:if test="${not empty employeeBean}">${employeeBean.employeeName}</c:if>
			${success}
			<span style="color:red;">${message}</span>
	</b></h4>
	<h4 style="color: #336699">Users :</h4>
      	<div class="gridView">
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
				<c:if test="${employee.status eq 'A'}">
					<tr class="grid" data-toggle="modal" data-id="${employee.userId}" onclick="return employeeDetails('${employee.userId}')" data-target="#employeeDetails">
			   			<td><c:out value="${employee.employeeName}"/></td>
			   			<td><c:out value="${employee.designation}"/></td>
			   			<td><c:out value="${employee.userId}"/></td>
			   			<td><c:out value="${employee.managerId}"/></td>
			   			<td><c:out value="${employee.managerId2Up}"/></td>
			   		</tr>
				</c:if>
				</c:forEach>
				<c:forEach var="prograManager" items="${sessionScope.employee.employeeBeans}">
				<c:forEach var="employee" items="${prograManager.employeeBeans}">
				<c:if test="${employee.status eq 'A'}">
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
				<c:forEach var="deliveryHead" items="${sessionScope.employee.employeeBeans}">
					<c:forEach var="prograManager" items="${deliveryHead.employeeBeans}">
						<c:forEach var="employee" items="${prograManager.employeeBeans}">
						<c:if test="${employee.status eq 'A'}">
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
		<button id="addUserPopUp" class="btn btn-primary" data-toggle="modal" data-target="#addUser">Add User</button>
      </div>
      <div class="tab-pane" id="maintainDeactivatedAssets">
	<h4 style="color: #336699"><b><c:if test="${not empty asset}">${asset.projectName}</c:if>
	${success}
	<span style="color:red;">${message}</span>
	</b></h4>
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
      	 <c:forEach var="deliveryHead" items="${sessionScope.employee.employeeBeans}">
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
						<c:forEach var="e1" items="${sessionScope.employee.employeeBeans}">
							<c:forEach var="emp" items="${e1.employeeBeans}">
								<c:forEach var="emp1" items="${emp.employeeBeans}">
										<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
								</c:forEach>
						</c:forEach>
						</c:forEach>
				</select>
				<div class="table-responsive">
			      <table id="employee" class="table table-hover">
						<thead>
							<tr>
							<th>Track Name</th>
							<th>Current Head Count</th>
							<th>Cisco Manager Name</th>
							<th>Project Manager</th>
							<th style="text-align: right;">To Delegate &nbsp;<input type="checkbox" id="selectall" name="${deliveryHead.userId}"/></th>
							
							</tr>
						</thead>
						<c:forEach var="programManager" items="${deliveryHead.employeeBeans}">
						<c:forEach var="projectManager" items="${programManager.employeeBeans}">
			        	<c:forEach var="asset" items="${projectManager.assetBeans}">
			        		<tr>
			        		<td><c:out value="${asset.projectName}"/></td>
			        		<td><c:out value="${asset.currentHeadCount}"/></td>
			        		<td><c:out value="${asset.ciscoManagerName}"/></td>
			        		<td><c:out value="${projectManager.employeeName}"/></td>
			        		<td style="text-align: right;"><input type="checkbox" class="${deliveryHead.userId}delegate" id="delegate" name="delegate" value="${asset.projectId}"></td>
			        		
			        		</tr>
			        	</c:forEach>
			        	</c:forEach>
			        	</c:forEach>
			        </table>
			        </div>
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
      	 <c:forEach var="deliveryHead" items="${sessionScope.employee.employeeBeans}">
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
						<c:forEach var="e1" items="${sessionScope.employee.employeeBeans}">
							<c:forEach var="emp" items="${e1.employeeBeans}">
								<c:forEach var="emp1" items="${emp.employeeBeans}">
										<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
								</c:forEach>
						</c:forEach>
						</c:forEach>
				</select>
			      <table id="employee" class="table table-hover">
						<thead>
							<tr>
							<th>Track Name</th>
							<th>Current Head Count</th>
							<th>Cisco Manager Name</th>
							<th>Project Manager</th>
							<th style="text-align: right;">To Delegate (Please check CheckBox)&nbsp;&nbsp;<input type="checkbox" id="selectall" name="${employee.userId}"/></th>
							
							</tr>
						</thead>
						<c:forEach var="projectManager" items="${employee.employeeBeans}">
			        	<c:forEach var="asset" items="${projectManager.assetBeans}">
			        		<tr>
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
       <c:forEach var="deliveryHead" items="${sessionScope.employee.employeeBeans}">
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
									<select class="form-control" id="delegateTo" name="delegateTo" required="" onchange="createJsonObject(this.options[this.selectedIndex].value)">
											<option value="" disabled selected>Please Select Project Manager</option>
											<c:forEach var="e1" items="${sessionScope.employee.employeeBeans}">
												<c:forEach var="emp" items="${e1.employeeBeans}">
													<c:forEach var="emp1" items="${emp.employeeBeans}">
														<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
													</c:forEach>
											</c:forEach>
											</c:forEach>
									</select>
					      		<div class="table-responsive">
								<table id="assets" class="table table-hover">
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
									<select class="form-control" id="delegateTo" name="delegateTo" required="" onchange="createJsonObject(this.options[this.selectedIndex].value)">
											<option value="" disabled selected>Please Select Project Manager</option>
											<c:forEach var="e1" items="${sessionScope.employee.employeeBeans}">
											<c:forEach var="emp" items="${e1.employeeBeans}">
													<c:forEach var="emp1" items="${emp.employeeBeans}">
													 	<option value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}/${emp1.managerId2Up}">${emp1.employeeName}</option>
													</c:forEach>
											</c:forEach>
											</c:forEach>
									</select>
					      			<div class="table-responsive">
										<table id="assets" class="table table-hover">
										<thead>
										<tr>
										<th>Project Name</th>
										<th>Current Head Count</th>
										<th>Cisco Manager Name</th>
										<th>Project Manager</th>
										<th style="text-align: right;">To Delegate (Please check CheckBox)&nbsp;&nbsp;<input type="checkbox" id="selectallintegrated"/></th>
										</tr>
										</thead>
								<tbody>
								<c:forEach var="deliveryHead" items="${sessionScope.employee.employeeBeans}">
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
</div>
</div>
