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
	if(selectedView=="forkjoin"){
		document.getElementById("integrated").style.display="none";
		document.getElementById("PMView").style.display="block";
	}else{
		document.getElementById("PMView").style.display="none";
		document.getElementById("integrated").style.display="block";
	}
}
</script>
<!-- Modal For Adding User -->

<div class="col-xs-9">
    <!-- Tab panes -->
    <div class="tab-content">
     
      <div class="tab-pane active" >
       <div class="view-filter">
		<span  style="color: #336699;">View Filter</span> 
		<select name="viewFilter" id="viewFilter" onchange="viewchange()">
			<option value="forkjoin">PM View</option>
			<option value="join">Project View</option>
		</select>
		</div>
      <div id="PMView">
       <h4 style="color: #336699">Delegate Project Tracks Under Project Manager</h4>
      	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
      	<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
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
				<select class="form-control" id="delegateTo" name="delegateTo" required="">
						<option value="" disabled selected>Please Select Project Manager</option>
						<c:forEach var="emp" items="${sessionScope.employee.employeeBeans}">
						<c:choose>
						<c:when test="${employee.userId eq emp.userId}"></c:when>
						<c:otherwise>
						<c:choose>
						<c:when test="${employee.status ne 'A'}"></c:when>
						<c:otherwise><option value="${emp.userId}">${emp.employeeName}</option></c:otherwise>
						</c:choose>
						</c:otherwise>
						</c:choose>
							
						</c:forEach>
				</select>
			      <table id="employee" class="table table-hover">
						<thead>
							<tr>
							<th>Track Name</th>
							<th>Current Head Count</th>
							<th>Cisco Manager Name</th>
							<th style="text-align: right;">To Delegate (Please check CheckBox)&nbsp;&nbsp;<input type="checkbox" id="selectall" name="${employee.userId}"/></th>
							</tr>
						</thead>
			        	<c:forEach var="asset" items="${employee.assetBeans}">
			        		<tr>
			        		<td><c:out value="${asset.projectName}"/></td>
			        		<td><c:out value="${asset.currentHeadCount}"/></td>
			        		<td><c:out value="${asset.ciscoManagerName}"/></td>
			        		<td style="text-align: right;"><input type="checkbox" class="${employee.userId}delegate" id="delegate" name="delegate" value="${asset.projectId}"></td>
			        		</tr>
			        	</c:forEach>
			        </table>
			        <input type="submit" class="btn btn-primary" value="Delegate">
		        </form>
		      </div>
		    </div>
		  </div>
		  </c:if>
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
												<c:forEach var="emp" items="${sessionScope.employee.employeeBeans}">
													 	<option value="${emp.userId}">${emp.employeeName}</option>
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
								<c:forEach var="projectManagers" items="${sessionScope.employee.employeeBeans}">
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
