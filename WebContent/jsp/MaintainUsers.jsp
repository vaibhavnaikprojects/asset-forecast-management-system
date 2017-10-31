<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AssetForecastingSystem::Asset</title>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/afmscustom.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/fonts/css/font-awesome.min.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/animate.min.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/maps/jquery-jvectormap-2.0.1.css"/>" />
<link href="<c:url value="/resources/css/icheck/flat/green.css"/>"
	rel="stylesheet" />
<link href="<c:url value="/resources/css/floatexamples.css"/>"
	rel="stylesheet" type="text/css" />

<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/nprogress.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-1.11.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.freezeheader.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.fixedheadertable.js"/>"></script>
    <script  type="text/javascript" src="<c:url value="/resources/js/helper.js"/>"></script>
	<script  type="text/javascript" src="<c:url value="/resources/js/dataTable.js"/>"></script>
<script src="<c:url value="/resources/js/script.js"/>"></script>
	<script  type="text/javascript" src="<c:url value="/resources/js/dataTable.js"/>"></script>
	<link href="<c:url value="/resources/css/dataTable.css"/>" rel="stylesheet" type="text/css" />
	
<script>
	NProgress.start();

</script>

<script type="text/javascript">
	function employeeDetails(userId) {
		$
				.ajax({
					dataType : "json",
					url : "getEmployeeDetailsFromId.html?userId=" + userId,
					type : "GET",
					success : function(data) {
						if (data != null) {
							deactivateEmployee.userId.value = data.userId;
							deactivateEmployee.employeeName.value = data.employeeName;
							deactivateEmployee.ogdesignation.value = data.designation;
							deactivateEmployee.CHC.value = data.CHC;
							activateEmployee.userId.value = data.userId;
							activateEmployee.employeeName.value = data.employeeName;
							activateEmployee.ogdesignation.value = data.designation;
							updateEmployee.employeeName.value = data.employeeName;
							updateEmployee.designation.value = data.designation;
							updateEmployee.ogdesignation.value = data.designation;
							updateEmployee.userId.value = data.userId;
							updateEmployee.managerId.value = data.managerId;
							updateEmployee.managerId2Up.value = data.managerId2Up;
							updateEmployee.CHC.value = data.CHC;
							updateEmployee.EG.value = data.EG;
							updateEmployee.ED.value = data.ED;
							var status = data.status;
							if (status === 'A') {
								document.getElementById("activate").style.display = "none";
								document.getElementById("deactivate").style.display = "block";
							} else {
								document.getElementById("deactivate").style.display = "none";
								document.getElementById("activate").style.display = "block";
							}

						} else {
							alert("data not loaded");
						}
					},
					error : function(data) {
						alert("Sorry, there was a problem!");
					}
				});
	}
	function managerName() {

		var id = document.getElementById("userId").value;

		$.ajax({
			url : "getManagerName.html?empId=" + id,
			type : "GET",
			success : function(data) {

				if (data == "Invalid CEC ID") {
					document.getElementById("employeeName").value = data;
					return false;
				} else {
					document.getElementById("employeeName").value = data;
					return true;
				}
			},
			error : function() {
				alert("Sorry, there was a problem!");
			}
		});
	}
	function viewchange() {
		var selectedView = document.getElementById("viewFilter").value;
		if (selectedView == "fork") {
			document.getElementById("PMView").style.display = "none";
			document.getElementById("PGMView").style.display = "block";
			document.getElementById("integrated").style.display = "none";
			document.getElementById("DHView").style.display = "none";
		} else if (selectedView == "join") {
			document.getElementById("PMView").style.display = "none";
			document.getElementById("PGMView").style.display = "none";
			document.getElementById("integrated").style.display = "block";
			document.getElementById("DHView").style.display = "none";
		} else if (selectedView == "forkjoin") {
			document.getElementById("integrated").style.display = "none";
			document.getElementById("PGMView").style.display = "none";
			document.getElementById("PMView").style.display = "block";
			document.getElementById("DHView").style.display = "none";
		} else {
			document.getElementById("PMView").style.display = "none";
			document.getElementById("PGMView").style.display = "none";
			document.getElementById("integrated").style.display = "none";
			document.getElementById("DHView").style.display = "block";
		}
	}
	$(document).ready(function() {
		$(":checkbox").change(function(event) {

			var name = this.name;

			if (this.checked) {

				$("." + name + "delegate").each(function() {

					this.checked = true;
				});
			} else {
				$("." + name + "delegate").each(function() {

					this.checked = false;
				});
			}
		});

	});

	$(document).ready(function() {
		$("#selectallintegrated").change(function(event) {

			if (this.checked) {

				$(".delegateintegrated").each(function() {

					this.checked = true;
				});
			} else {
				$(".delegateintegrated").each(function() {

					this.checked = false;
				});
			}
		});

	});
	$(document).ready(function() {
		var globalManagerIds;

		$('#allselect').click(function(event) {
			if (this.checked) {
				$('.empSub').each(function() {
					this.checked = true;
				});
			} else {
				$('.empSub').each(function() {
					this.checked = false;
				});
			}
		});

	});

	function addingUsermanagementCheck(designation) {

		var designation = $("#designation").val();
		if (designation == 'Cisco ODC Head') {
			$("#managerId").append(
					'<option value="null" disabled selected>'
							+ "" + '</option>');
			$("#managerId").removeAttr('required');
			return;
		}
		if (designation == 'PCO Team') {
			$("#managerId").append(
					'<option value="null" disabled selected>'
							+ "" + '</option>');
			$("#managerId").removeAttr('required');
			return;
		}

		/* if(designation =='Cisco ODC Head' ||  designation =='PCO Team' || designation=='Program Manager')
			return;
		 */

		$.ajax({
			dataType : "json",
			url : "getDesigValues.html?desig=" + designation,
			type : "GET",
			success : function(data) {
				var option = '', i = 0;

				$("#managerId2Up").val('');

				var text = JSON.stringify(data.employeeDesig);

				var obj = JSON.parse(data.employeeDesig);

				switch (designation) {
				case "Project Manager":
					$("#managerId").append(
							'<option value="" disabled selected>'
									+ "Select Program Manager" + '</option>')
					break;
				case "Program Manager":
					$("#managerId").append(
							'<option value="" disabled selected>'
									+ "Select Delivery Head" + '</option>')
					break;
				case "Delivery Head":
					$("#managerId").append(
							'<option value="" disabled selected>'
									+ "Select Cisco ODC Head" + '</option>')
					break;
				}
				for (var count = 0; count < obj.length; count++) {
					option += '<option value="'+ obj[count]["userId"] + '">'
							+ obj[count]["employeeName"] + '</option>';
				}

				/*  while(data.employeeDesig[i])
				{
				
				   option += '<option value="'+ data.employeeDesig.employeeName[i] + '">' + data.employeeDesig.employeeName[i] + '</option>';
				   i++;
				} */

				globalManagerIds = obj;
				$("#managerId").append(option);

			},
			error : function(data) {
				alert("Sorry, there was a problem!");
			}
		});

		$("#managerId")
				.change(
						function() {

							var tempObj = globalManagerIds;

							for (var count = 0; count < tempObj.length; count++) {

								if ("" + tempObj[count]["userId"] == ""
										+ $("#managerId").val()) {
									$("#managerId2Up").val('');
									$("#managerId2Up").val(
											tempObj[count]["managerId"]);
									break;
								}

							}

						});
	}
</script>


</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<jsp:include page="headerPage.jsp"></jsp:include>
			<div class="right_col" role="main">
				<!-- top tiles -->

				<div class="row tile_count">
					<c:set var="EDMain" />
					<c:set var="EGMain" />
					<c:set var="CHCMain" />

					<div
						class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
						<div class="left"></div>
						<div class="right">
							<span class="count_top"><i class="fa fa-user"></i> Current
								Year</span>
							<div class="count">${sessionScope.year}</div>
						</div>
					</div>
					<div
						class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
						<div class="left"></div>
						<div class="right">
							<span class="count_top"><i class="fa fa-clock-o"></i>Current
								Quarter</span>
							<div class="count">${sessionScope.quarter}</div>
						</div>
					</div>
					<div
						class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
						<div class="left"></div>
						<div class="right">
							<span class="count_top"><i class="fa fa-user"></i>Current
								Head Count</span>
							<c:choose>
								<c:when
									test="${sessionScope.employee.designation eq 'Project Manager'}">
									<div class="count">${employee.CHC}</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Program Manager'}">
									<c:forEach var="employee"
										items="${sessionScope.employee.employeeBeans}">
										<c:set var="CHCMain" value="${employee.CHC+CHCMain}" />
									</c:forEach>
									<div class="count">
										<c:out value="${CHCMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Delivery Head'}">
									<c:forEach var="emp1"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="employee" items="${emp1.employeeBeans}">
											<c:set var="CHCMain" value="${employee.CHC+CHCMain}" />
										</c:forEach>
									</c:forEach>
									<div class="count">
										<c:out value="${CHCMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
									<c:forEach var="emp2"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="emp1" items="${emp2.employeeBeans}">
											<c:forEach var="employee" items="${emp1.employeeBeans}">
												<c:set var="CHCMain" value="${employee.CHC+CHCMain}" />
											</c:forEach>
										</c:forEach>
									</c:forEach>
									<div class="count">
										<c:out value="${CHCMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'PCO Team'}">
									<c:forEach var="emp2"
										items="${sessionScope.employee.employeeBeans}">
										<c:if test="${emp2.designation eq 'Cisco ODC Head'}">
											<c:forEach var="emp1" items="${emp2.employeeBeans}">
												<c:forEach var="employee" items="${emp1.employeeBeans}">
													<c:set var="CHCMain" value="${employee.CHC+CHCMain}" />
												</c:forEach>
											</c:forEach>
										</c:if>
									</c:forEach>
									<div class="count">
										<c:out value="${CHCMain}" />
									</div>
								</c:when>
								<c:when test="${sessionScope.employee.designation eq 'Admin'}">
							        Salary is very good.
							    </c:when>
								<c:when test="${sessionScope.employee.designation eq 'LPT'}">
									<div class="count">NA</div>
								</c:when>
							</c:choose>
						</div>
					</div>
					<div
						class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
						<div class="left"></div>
						<div class="right">
							<span class="count_top"><i class="fa fa-user"></i>Expected
								Growth</span>
							<c:choose>
								<c:when
									test="${sessionScope.employee.designation eq 'Project Manager'}">
									<div class="count green">${employee.EG}</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Program Manager'}">
									<c:forEach var="employee"
										items="${sessionScope.employee.employeeBeans}">
										<c:set var="EGMain" value="${employee.EG+EGMain}" />
									</c:forEach>
									<div class="count green">
										<c:out value="${EGMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Delivery Head'}">
									<c:forEach var="emp1"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="employee" items="${emp1.employeeBeans}">
											<c:set var="EGMain" value="${employee.EG+EGMain}" />
										</c:forEach>
									</c:forEach>
									<div class="count green">
										<c:out value="${EGMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
									<c:forEach var="emp2"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="emp1" items="${emp2.employeeBeans}">
											<c:forEach var="employee" items="${emp1.employeeBeans}">
												<c:set var="EGMain" value="${employee.EG+EGMain}" />
											</c:forEach>
										</c:forEach>
									</c:forEach>
									<div class="count green">
										<c:out value="${EGMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'PCO Team'}">
									<c:forEach var="emp2"
										items="${sessionScope.employee.employeeBeans}">
										<c:if test="${emp2.designation eq 'Cisco ODC Head'}">
											<c:forEach var="emp1" items="${emp2.employeeBeans}">
												<c:forEach var="employee" items="${emp1.employeeBeans}">
													<c:set var="EGMain" value="${employee.EG+EGMain}" />
												</c:forEach>

											</c:forEach>
										</c:if>
									</c:forEach>
									<div class="count green">
										<c:out value="${EGMain}" />
									</div>
								</c:when>
								<c:when test="${sessionScope.employee.designation eq 'Admin'}">
							        Salary is very good.
							    </c:when>
								<c:when test="${sessionScope.employee.designation eq 'LPT'}">
									<div class="count green">${employee.EG}</div>
								</c:when>
							</c:choose>
						</div>
					</div>
					<div
						class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
						<div class="left"></div>
						<div class="right">
							<span class="count_top"><i class="fa fa-user"></i>Expected
								Decline</span>
							<c:choose>
								<c:when
									test="${sessionScope.employee.designation eq 'Project Manager'}">
									<div class="count red">${employee.ED}</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Program Manager'}">
									<c:forEach var="employee"
										items="${sessionScope.employee.employeeBeans}">
										<c:set var="EDMain" value="${employee.ED+EDMain}" />
									</c:forEach>
									<div class="count red">
										<c:out value="${EDMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Delivery Head'}">
									<c:forEach var="emp1"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="employee" items="${emp1.employeeBeans}">
											<c:set var="EDMain" value="${employee.ED+EDMain}" />
										</c:forEach>
									</c:forEach>
									<div class="count red">
										<c:out value="${EDMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
									<c:forEach var="emp2"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="emp1" items="${emp2.employeeBeans}">
											<c:forEach var="employee" items="${emp1.employeeBeans}">
												<c:set var="EDMain" value="${employee.ED+EDMain}" />
											</c:forEach>
										</c:forEach>
									</c:forEach>
									<div class="count red">
										<c:out value="${EDMain}" />
									</div>
								</c:when>
								<c:when
									test="${sessionScope.employee.designation eq 'PCO Team'}">
									<c:forEach var="emp2"
										items="${sessionScope.employee.employeeBeans}">
										<c:if test="${emp2.designation eq 'Cisco ODC Head'}">
											<c:forEach var="emp1" items="${emp2.employeeBeans}">
												<c:forEach var="employee" items="${emp1.employeeBeans}">
													<c:set var="EDMain" value="${employee.ED+EDMain}" />
												</c:forEach>
											</c:forEach>
										</c:if>
									</c:forEach>
									<div class="count red">
										<c:out value="${EDMain}" />
									</div>
								</c:when>
								<c:when test="${sessionScope.employee.designation eq 'Admin'}">
							        Salary is very good.
							    </c:when>
								<c:when test="${sessionScope.employee.designation eq 'LPT'}">
									<div class="count red">NA</div>
								</c:when>
							</c:choose>
						</div>
					</div>
					<div
						class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
						<div class="left"></div>
						<div class="right">
							<span class="count_top"><i class="fa fa-user"></i>System
								Freeze Date</span>
							<c:if test="${not empty sessionScope.freezeDate}">
								<div class="count-freezedate red">${sessionScope.freezeDate}
								</div>
							</c:if>
							<c:if test="${empty sessionScope.freezeDate}">
								<div class="count-freezedate green">N.A</div>
							</c:if>

						</div>
					</div>
				</div>

				<!-- /top tiles -->
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="modal fade" id="addUser" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Add User</h4>
									</div>
									<form id="addUser" name="addUser" action="addUser.html"
										method="post">
										<div class="modal-body">
											<table class="table table-hover">
												<tr>
													<td><label style="color: #73879C">User ID</label></td>
													<td><input type="text" class="form-control"
														name="userId" id="userId" required="required"
														onblur="return managerName()" /></td>
												</tr>
												<tr>
													<td><label>Employee Name</label></td>
													<td><input type="text" class="form-control"
														name="employeeName" id="employeeName" required="required"
														readonly="readonly" /></td>
												</tr>
												<tr>
													<td><label>Designation</label></td>
													<td><select class="form-control" id="designation"
														name="designation" required="required"
														onchange="return addingUsermanagementCheck(this)">
															<option value="" disabled selected>Please Select
																Designation</option>
															<c:if
																test="${sessionScope.employee.designation eq 'Program Manager'}">
																<option value="Project Manager">Project Manager</option>
															</c:if>
															<c:if
																test="${sessionScope.employee.designation eq 'Delivery Head'}">
																<option value="Project Manager">Project Manager</option>
																<option value="Program Manager">Program Manager</option>
															</c:if>
															<c:if
																test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
																<option value="Project Manager">Project Manager</option>
																<option value="Program Manager">Program Manager</option>
																<option value="Delivery Head">Delivery Head</option>
															</c:if>
															<c:if
																test="${sessionScope.employee.designation eq 'PCO Team'}">
																<option value="Project Manager">Project Manager</option>
																<option value="Program Manager">Program Manager</option>
																<option value="Delivery Head">Delivery Head</option>
																<option value="Cisco ODC Head">Cisco ODC Head</option>
																<option value="PCO Team">PCO Team</option>
															</c:if>
													</select></td>
												</tr>
												<tr id="oneUp" hidden="">
													<td><label id="1up">Manager Id</label></td>

													<td><select class="form-control" id="managerId"
														name="managerId" required="required">

													</select></td>

													<%-- <td><input type="text" class="form-control"
														id="managerId" name="managerId" value="${sessionScope.employee.userId}"  /> --%>

												</tr>
												<tr id="twoUp" hidden="">
													<td><label id="2up">managerId 2Up</label></td>
													<td><input type="text" class="form-control"
														name="managerId2Up" id="managerId2Up" readonly="readonly"
														value="${sessionScope.employee.managerId2Up}" /></td>
												</tr>
											</table>
										</div>
										<div class="modal-footer">
											<p>Note : Disabled fields are auto populated</p>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">Close</button>
											<button type="submit" class="btn btn-primary">Add
												User</button>
										</div>
									</form>
								</div>
							</div>
						</div>
						<!-- Modal For Updating User -->
						<div class="modal fade" id="employeeDetails" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Edit User</h4>
									</div>
									<form id="updateEmployee" name="updateEmployee"
										action="updateUser.html" method="post">
										<input type="hidden" id="ogdesignation" name="ogdesignation" />
										<div class="modal-body">
											<table class="table table-hover">
												<tr>
													<td>User ID</td>
													<td><input type="text" class="form-control"
														name="userId" id="userId" onblur="return managerName()"
														required="required" /></td>
												</tr>
												<tr>
													<td>Employee Name</td>
													<td><input type="text" class="form-control"
														name="employeeName" id="employeeName" required="required"
														readonly="readonly" /></td>
												</tr>
												<tr>
													<td>Designation</td>
													<td><select class="form-control" id="designation"
														name="designation" required="required">
															<option value="" disabled selected>Please Select
																Designation</option>
															<option value="Project Manager">Project Manager</option>
															<c:if
																test="${sessionScope.employee.designation eq 'Delivery Head'}">
																<option value="Program Manager">Program Manager</option>
															</c:if>
															<c:if
																test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
																<option value="Program Manager">Program Manager</option>
																<option value="Delivery Head">Delivery Head</option>
															</c:if>
															<c:if
																test="${sessionScope.employee.designation eq 'PCO Team'}">
																<option value="Program Manager">Program Manager</option>
																<option value="Delivery Head">Delivery Head</option>
																<option value="Cisco ODC Head">Cisco ODC Head</option>
															</c:if>
													</select></td>
												</tr>
												<tr>
													<td>Manager Id</td>
													<td><input type="text" class="form-control"
														name="managerId" id="managerId" required="required" /></td>
												</tr>
												<tr>
													<td>managerId 2Up</td>
													<td><input type="text" class="form-control"
														name="managerId2Up" id="managerId2Up" required="required" /></td>
												</tr>
												<tr>
													<td>Total Assets</td>
													<td><input type="text" class="form-control" name="CHC"
														id="CHC" required="required" readonly="readonly" /></td>
												</tr>
												<tr>
													<td>Expected Growth</td>
													<td><input type="text" class="form-control" name="EG"
														id="EG" required="required" readonly="readonly" /></td>
												</tr>
												<tr>
													<td>Expected Decline</td>
													<td><input type="text" class="form-control" name="ED"
														id="ED" required="required" readonly="readonly" /></td>
												</tr>

											</table>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">Close</button>
											<button type="submit" class="btn btn-primary">Update
												User</button>
										</div>
									</form>
									<div id="deactivate">
										<form id="deactivateEmployee" method="post"
											action="deactivateUser.html">
											<input type="hidden" class="form-control" name="userId"
												id="userId" /> <input type="hidden" class="form-control"
												name="ogdesignation" id="ogdesignation" /> <input
												type="hidden" class="form-control" name="employeeName"
												id="employeeName" /> <input type="hidden"
												class="form-control" name="CHC" id="CHC" />
											<c:if
												test="${sessionScope.employee.designation eq 'PCO Team'}">
												<button type="submit" class="btn btn-danger "
													style="position: absolute; top: 92.5%; left: 3%;">Deactivate
													User</button>
											</c:if>
										</form>
									</div>
									<div id="activate">
										<form id="activateEmployee" method="post"
											action="activateUser.html">
											<input type="hidden" class="form-control" name="userId"
												id="userId" /> <input type="hidden" class="form-control"
												name="ogdesignation" id="ogdesignation" /> <input
												type="hidden" class="form-control" name="employeeName"
												id="employeeName" />
											<c:if
												test="${sessionScope.employee.designation eq 'PCO Team'}">
												<button type="submit" class="btn btn-danger "
													style="position: absolute; top: 92.5%; left: 3%;">Activate
													User</button>
											</c:if>
										</form>
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane active" id="maintainUsers">
							<h4 style="color: #336699">
								<b><c:if test="${not empty employeeBean}">${employeeBean.employeeName}</c:if>
									${success} <span style="color: #e74c3c;">${message}</span> </b>
							</h4>
							<div style="display: inline-block; width: 100%">
								<div style="float: left">
									<label style="color: #336699; font-size: large">Users :</label>
								</div>
								<div>
									<button id="addUserPopUp" class="btn btn-primary"
										data-toggle="modal" data-target="#addUser"
										style="margin-left: 84%">Add User</button>
								</div>
							</div>
									<div class="x_panel">
									<table id="laptopRequests" class="table table-hover">
										<thead>
											<tr class="grid">
												<th>Employee Name</th>
												<th>designation</th>
												<th>userId</th>
												<th>Manager Id</th>
												<th>2 UP Manager Id</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="employee"
												items="${sessionScope.employee.employeeBeans}">
												<c:if test="${employee.status eq 'A'}">
													<tr class="grid" data-toggle="modal"
														data-id="${employee.userId}"
														onclick="return employeeDetails('${employee.userId}')"
														data-target="#employeeDetails">
														<td><c:out value="${employee.employeeName}" /></td>
														<td><c:out value="${employee.designation}" /></td>
														<td><c:out value="${employee.userId}" /></td>
														<td><c:out value="${employee.managerId}" /></td>
														<td><c:out value="${employee.managerId2Up}" /></td>
													</tr>
												</c:if>
											</c:forEach>
											<c:if
												test="${sessionScope.employee.designation eq 'Delivery Head'}">
												<c:forEach var="prograManager"
													items="${sessionScope.employee.employeeBeans}">
													<c:forEach var="employee"
														items="${prograManager.employeeBeans}">
														<c:if test="${employee.status eq 'A'}">
															<tr class="grid" data-toggle="modal"
																data-id="${employee.userId}"
																onclick="return employeeDetails('${employee.userId}')"
																data-target="#employeeDetails">
																<td><c:out value="${employee.employeeName}" /></td>
																<td><c:out value="${employee.designation}" /></td>
																<td><c:out value="${employee.userId}" /></td>
																<td><c:out value="${employee.managerId}" /></td>
																<td><c:out value="${employee.managerId2Up}" /></td>
															</tr>
														</c:if>
													</c:forEach>
												</c:forEach>
											</c:if>
											<c:if
												test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
												<c:forEach var="deliveryHead"
													items="${sessionScope.employee.employeeBeans}">
												<c:forEach var="employee"
													items="${deliveryHead.employeeBeans}">
														<c:if test="${employee.status eq 'A'}">
															<tr class="grid" data-toggle="modal"
																data-id="${employee.userId}"
																onclick="return employeeDetails('${employee.userId}')"
																data-target="#employeeDetails">
																<td><c:out value="${employee.employeeName}" /></td>
																<td><c:out value="${employee.designation}" /></td>
																<td><c:out value="${employee.userId}" /></td>
																<td><c:out value="${employee.managerId}" /></td>
																<td><c:out value="${employee.managerId2Up}" /></td>
															</tr>
														</c:if>
													</c:forEach>
												</c:forEach>
												<c:forEach var="deliveryHead"
													items="${sessionScope.employee.employeeBeans}">
												<c:forEach var="prograManager"
													items="${deliveryHead.employeeBeans}">
													<c:forEach var="employee"
														items="${prograManager.employeeBeans}">
														<c:if test="${employee.status eq 'A'}">
															<tr class="grid" data-toggle="modal"
																data-id="${employee.userId}"
																onclick="return employeeDetails('${employee.userId}')"
																data-target="#employeeDetails">
																<td><c:out value="${employee.employeeName}" /></td>
																<td><c:out value="${employee.designation}" /></td>
																<td><c:out value="${employee.userId}" /></td>
																<td><c:out value="${employee.managerId}" /></td>
																<td><c:out value="${employee.managerId2Up}" /></td>
															</tr>
														</c:if>
													</c:forEach>
												</c:forEach>
												</c:forEach>
											</c:if>
											<c:if
												test="${sessionScope.employee.designation eq 'PCO Team'}">

											</c:if>
										</tbody>
									</table>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script
		src="<c:url value="/resources/js/progressbar/bootstrap-progressbar.min.js"/>"></script>
	<script
		src="<c:url value="/resources/js/nicescroll/jquery.nicescroll.min.js"/>"></script>
	<script src="<c:url value="/resources/js/icheck/icheck.min.js"/>"></script>
	<script src="<c:url value="/resources/js/custom.js"/>"></script>
	<script>
		NProgress.done();
	</script>
	<jsp:include page="footerPage.jsp" />
</body>
</html>