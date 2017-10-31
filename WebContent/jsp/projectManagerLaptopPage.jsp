<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	function warningSubmit() {
		var status = confirm("Do you want to send  reminder mail to Cisco ODC Head ? ");
		if (status == true) {
			return true;
		} else {
			return false;
		}
	}
	function Submit() {
		
		 var files = document.getElementById("ciscoManagerMail").files;
		for (var i = 0; i < files.length; i++)
			{
			
			var fileName=files[i].name;
			var fileExt=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length);
			if(fileExt!="msg")
				{
				alert("Kindly Attach a Mail file(.msg)");
				return false;
				}}
			
		
		var status = confirm("Do you want to Submit the request ?  Once Submitted you will not be able to alter the request ");
		if (status == true) {
			return true;
		} else {
			return false;
		}

	}
	function showFromDiv() {

		var a = $("#fromType :selected").text();

		if (a == "Associate (Transfer Request)") {
			$('label[name=fromAssociate]').show();
			$('input[name=fromAssociate]').show();
			$('input[name=fromAssociateName]').show();
		} else {
			$('label[name=fromAssociate]').hide();
			$('input[name=fromAssociate]').hide();
			$('input[name=fromAssociateName]').hide();

		}

	}
	function showFromDiv1() {

		var a = $("#fromType1 :selected").text();

		if (a == "Associate (Transfer Request)") {
			$('label[name=fromAssociate]').show();
			$('input[name=fromAssociate]').show();
			$('input[name=fromAssociateName]').show();
		} else {
			$('label[name=fromAssociate]').hide();
			$('input[name=fromAssociate]').hide();
			$('input[name=fromAssociateName]').hide();

		}

	}
	function managerName() {
		var id = requestEarlyRefreshRequest.toAssociate.value;

		$.ajax({
			url : "getManagerName.html?empId=" + id,
			type : "GET",
			success : function(data) {
				if (data == "Invalid CEC ID") {
					requestEarlyRefreshRequest.toAssociateName.value = data;
					return false;
				} else {
					requestEarlyRefreshRequest.toAssociateName.value = data;
					return true;
				}
			},
			error : function() {
				alert("Sorry, there was a problem!");
			}
		});
	}

	function mgrname() {
		var id = requestNewHireRequest.ownerNameId.value;

		$.ajax({
			url : "getManagerName.html?empId=" + id,
			type : "GET",
			success : function(data) {
				if (data == "Invalid CEC ID") {
					requestNewHireRequest.ownerName.value = data;
					return false;
				} else {
					requestNewHireRequest.ownerName.value = data;
					return true;
				}
			},
			error : function() {
				alert("Sorry, there was a problem!");
			}
		});
	}

	function frommanagerName() {
		var id = requestEarlyRefreshRequest.fromAssociate.value;

		$.ajax({
			url : "getManagerName.html?empId=" + id,
			type : "GET",
			success : function(data) {
				if (data == "Invalid CEC ID") {
					requestEarlyRefreshRequest.fromAssociateName.value = data;
					return false;
				} else {
					requestEarlyRefreshRequest.fromAssociateName.value = data;
					return true;
				}
			},
			error : function() {
				alert("Sorry, there was a problem!");
			}
		});
	}

	function managerNameH() {
		var id = requestNewHireRequest.toAssociate.value;

		$.ajax({
			url : "getManagerName.html?empId=" + id,
			type : "GET",
			success : function(data) {
				if (data == "Invalid CEC ID") {

					requestNewHireRequest.toAssociateName.value = '';

					return false;
				} else {
					requestNewHireRequest.toAssociateName.value = data;
					return true;
				}
			},
			error : function() {
				alert("Sorry, there was a problem!");
			}
		});
	}

	function mgrnameH() {
		var id = requestNewHireRequest.ownerNameId.value;

		$.ajax({
			url : "getManagerName.html?empId=" + id,
			type : "GET",
			success : function(data) {
				if (data == "Invalid CEC ID") {
					requestNewHireRequest.ownerName.value = data;
					return false;
				} else {
					requestNewHireRequest.ownerName.value = data;
					return true;
				}
			},
			error : function() {
				alert("Sorry, there was a problem!");
			}
		});
	}

	function frommanagerNameH() {
		var id = requestNewHireRequest.fromAssociate.value;

		$.ajax({
			url : "getManagerName.html?empId=" + id,
			type : "GET",
			success : function(data) {
				if (data == "Invalid CEC ID") {
					requestNewHireRequest.fromAssociateName.value = data;
					return false;
				} else {
					requestNewHireRequest.fromAssociateName.value = data;
					return true;
				}
			},
			error : function() {
				alert("Sorry, there was a problem!");
			}
		});
	}
</script>
<jsp:include page="laptopMoreInfoDetails.jsp"></jsp:include>

<%-- <center><h4><font color="green">${success}</font></h4></center> --%>
<div class="panel-group" id="accordion" role="tablist"
	aria-multiselectable="true">
	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="headingOne">
			<h4 class="panel-title" style="color: #336699">
				<a role="button" data-toggle="collapse" data-parent="#accordion"
					href="#collapseOne" aria-expanded="true"
					aria-controls="collapseOne"> Raise New Hire Request </a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse" role="tabpanel"
			aria-labelledby="headingOne">
			<div class="panel-body">
				<div class="form-group">
					<form id="requestNewHireRequest"
						action="raiseNewHireLaptopRequest.html" method="post">
						<label for="projectName">From : </label> <select
							class="form-control" id="fromType" name="fromType"
							required="required" onchange="showFromDiv()">
							<option value="" disabled selected>Select</option>
							<option value="N">Associate (Transfer Request)</option>
							<option value="S">Stock</option>

						</select> <br> <input type="text" class="form-control-half-name"
							name="fromAssociate" id="fromAssociate"
							onblur="return frommanagerNameH()" placeholder="From : CEC ID"
							style="display: none" /> <input type="text"
							class="form-control-half-name" name="fromAssociateName"
							id="fromAssociateName" placeholder="From Associate NAME"
							readonly="readonly" style="display: none" />
						<div class="form-control-clear"></div>

							<br> <input type="text" class="form-control-half-name"
								name="toAssociate" id="toAssociate"
								onblur="return managerNameH()" placeholder="To : CEC ID"
								required="required" /> <input type="text"
								class="form-control-half-name" name="toAssociateName"
								id="toAssociateName" placeholder="To Associate NAME"
								readonly="readonly" required="required" />
							<div class="form-control-clear"></div>
							<br /> <label for="reason">Reason</label> 
							 <textarea class="form-control" name="reason" id="reason" maxlength="2000"
								required="required"></textarea> 
								<label for="projectName">Project
								Name</label> <select class="form-control" id="projectName"
								name="projectName" required="required"
								title="Select Project Name">
								<option value="" disabled selected>Select Project Name</option>
								<%
									EmployeeBean bean = (EmployeeBean) session.getAttribute("employee");
								%>
								<c:forEach var="asset"
									items="${sessionScope.employee.assetBeans}">
									<option value="${asset.projectName}">${asset.projectName}</option>
								</c:forEach>
								<c:forEach var="asset1"
									items="${sessionScope.employee.employeeBeans}">
									<c:forEach var="assetsdetails" items="${asset1.assetBeans}">
										<option value="${assetsdetails.projectName}">${assetsdetails.projectName}</option>
									</c:forEach>
								</c:forEach>


						</select> <input type="hidden" class="form-control" name="requestType"
							id="requestType" value="1" /> <br />
						<button type="submit" class="btn btn-primary"
							onclick="return checkDoller();return Submit()">Raise Request</button>

					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="headingTwo">
			<h4 class="panel-title" style="color: #336699">
				<a class="collapsed" role="button" data-toggle="collapse"
					data-parent="#accordion" href="#collapseTwo" aria-expanded="false"
					aria-controls="collapseTwo"> Raise Early Refresh Request </a>
			</h4>
		</div>
		<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel"
			aria-labelledby="headingTwo">
			<div class="panel-body">
				<div class="form-group">
					<form id="requestEarlyRefreshRequest"
						action="raiseEarlyRefreshLaptopRequest.html" method="post"
						enctype="multipart/form-data">
						<label for="projectName">From : </label> <select
							class="form-control" id="fromType1" name="fromType1"
							required="required" onchange="showFromDiv1()">
							<option value="" disabled selected>Select</option>
							<option value="N">Associate (Transfer Request)</option>
							<option value="S">Stock</option>
						</select> <br>


							<!-- <label for="fromAssociate" style="display: none" name="fromAssociate">From Associate</label> -->
							<input type="text" class="form-control-half-name"
								name="fromAssociate" id="fromAssociate"
								onblur="return frommanagerName()" placeholder="From : CEC ID"
								style="display: none" /> <input type="text"
								class="form-control-half-name" name="fromAssociateName"
								id="fromAssociateName" placeholder="From : Associate NAME"
								style="display: none" readonly="readonly" />

							<div class="form-control-clear"></div>
							<br>
							<!-- <label for="toAssociate">To Associate</label> -->
							<input type="text" class="form-control-half-name"
								name="toAssociate" id="toAssociate"
								onblur="return managerName()" placeholder="To : CEC ID" /> <input
								type="text" class="form-control-half-name"
								name="toAssociateName" id="toAssociateName"
								placeholder="To : Associate NAME" readonly="readonly"/>
							<div class="form-control-clear"></div>
							<br /> <label for="reason">Reason</label>
							<textarea class="form-control" name="reason" id="reason" maxlength="2000"
								required="required"></textarea>  
								<label for="projectName">Project
								Name</label> <select class="form-control" id="projectName"
								name="projectName" required="required"
								title="Select Project Name">
								<option value="" disabled selected>Select Project Name</option>
								<c:forEach var="asset"
									items="${sessionScope.employee.assetBeans}">
									<option value="${asset.projectName}">${asset.projectName}</option>
								</c:forEach>
								<c:forEach var="asset1"
									items="${sessionScope.employee.employeeBeans}">
									<c:forEach var="assetsdetails" items="${asset1.assetBeans}">
										<option value="${assetsdetails.projectName}">${assetsdetails.projectName}</option>
									</c:forEach>
								</c:forEach>
							</select> <label for="ciscoManagerMail">Cisco Manager Approval
								Mail</label> <input type="file" id="ciscoManagerMail"
								name="ciscoManagerMail" required="required"
								onchange="alertFilename()" /> <input type="hidden"
								class="form-control" name="requestType" id="requestType"
								value="2" /> <br />
							<button type="submit" class="btn btn-primary"
								onclick="return checkDoller();return Submit()">Raise Request</button>

					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="headingThree">
			<h4 class="panel-title" style="color: #336699">
				<a class="collapsed" role="button" data-toggle="collapse"
					data-parent="#accordion" href="#collapseThree"
					aria-expanded="false" aria-controls="collapseThree"> Track
					Requests </a>
			</h4>
		</div>
		<div id="collapseThree" class="panel-collapse collapse in"
			role="tabpanel" aria-labelledby="headingThree">
			<div class="panel-body">
				<div class="x_panel">
					<table id="laptopRequests" class="table table-hover">
						<thead>
							<tr>
								<th>Request Id</th>
								<th>Request Type</th>
								<th>Associate Name</th>
								<th>Request Date</th>
								<th>ODC Head Status</th>
								<th>IMQT Status</th>
								<th>Reminders</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="laptop"
								items="${sessionScope.employee.laptopBeans}">
								<tr class="grid">
									<td><c:out value="${laptop.uniquelaptopid}" /></td>
									<td><c:out value="${laptop.requestType}" /></td>
									<td><c:out value="${laptop.ownerName}" /></td>
									<td><c:out value="${laptop.createdDate}" /></td>
									<%-- <c:choose>
												<c:when test="${laptop.stock=='S'}">
													<td><c:out value="Yes" /></td>
												</c:when>
												<c:otherwise>

													<td><c:out value="--" /></td>
												</c:otherwise>

											</c:choose> --%>
											<c:choose>
												<c:when test="${laptop.statusFlagValue=='Approved'}">
												<td><c:out value="${laptop.statusFlagValue}" /></td>
												</c:when>
												<c:when test="${laptop.statusFlagValue=='More Info'}">
												<td><c:out value="${laptop.statusFlagValue}" />&nbsp;&nbsp; <a href="" data-toggle="modal"
												onclick="return laptopDetailsForMoreInfo('${laptop.laptopId}','${laptop.ownerName}','${laptop.requestType}','${laptop.projectName}','${laptop.createdDate}','${laptop.reason}','${laptop.uniquelaptopid}','${laptop.add_info}')"
												data-target="#laptopMoreInfoDetails" class="grid"><i
													class="fa fa-pencil-square-o fa-lg" data-toggle="tooltip"
													title="Edit Request" data-placement="bottom"></i></a>
												</td>
												</c:when>
												<c:otherwise>
												<td><c:out value="${laptop.statusFlagValue}" /></td>
												</c:otherwise>
												</c:choose>
												<c:choose>
												<c:when test="${laptop.statusFlagValue=='Approved'}">
												<td><c:out value="Pending" /></td>
												</c:when>
												<c:otherwise>
												<td><c:out value="${laptop.statusFlagValue}" /></td>
												</c:otherwise>
												</c:choose>
											<c:choose>
												<c:when test="${laptop.statusFlagValue=='Approved'}">
													<td> <i class="fa fa-check fa-lg"  style="color: #1ABB9C"   title="Request approved "></i></td>
												</c:when>
												<c:when test="${laptop.statusFlagValue=='Reject'}">
													<td> <i class="fa fa-times fa-lg"  style="color: #1ABB9C"   title="Request rejected "></i></td>
												</c:when>
												<c:otherwise>
													<td><a
														href="/AFMS/Remindersforlpapproval.html?laptopid=${laptop.laptopId} "
														id="Remindersforlpapproval"
														title="Send a Reminder Mail to Cisco ODC Head"
														onclick="return warningSubmit()"> <i class="fa fa-bell fa-lg"></i></a></td>
												</c:otherwise>
											</c:choose>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						</div>
				</div>
			</div>
		</div>
	</div>
