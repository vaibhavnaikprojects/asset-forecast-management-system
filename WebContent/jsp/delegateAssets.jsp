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
	    <link href="<c:url value="/resources/css/dataTable.css"/>" rel="stylesheet" type="text/css" />
<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/nprogress.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-1.11.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.freezeheader.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.fixedheadertable.js"/>"></script>
<script src="<c:url value="/resources/js/script.js"/>"></script>
    <script  type="text/javascript" src="<c:url value="/resources/js/helper.js"/>"></script>
<script  type="text/javascript" src="<c:url value="/resources/js/dataTable.js"/>"></script>
	<link href="<c:url value="/resources/css/dataTable.css"/>" rel="stylesheet" type="text/css" />
	
    
<script>
	NProgress.start();
</script>
<script type="text/javascript">
	function employeeDetails(userId) {
		$.ajax({
			dataType : "json",
			url : "getEmployeeDetailsFromId.html?userId=" + userId,
			type : "GET",
			success : function(data) {
				if (data != null) {
					updateEmployee.employeeName.value = data.employeeName;
					updateEmployee.designation.value = data.designation;
					updateEmployee.userId.value = data.userId;
					updateEmployee.managerId.value = data.managerId;
					updateEmployee.managerId2Up.value = data.managerId2Up;
					updateEmployee.CHC.value = data.CHC;
					updateEmployee.EG.value = data.EG;
					updateEmployee.ED.value = data.ED;
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
						<div class="heading" style="text-align: center;">

					<h4 style="color: #336699">
						<center><b>${success} <span style="color: #1ABB9C;">${message}</span> </b></center>
					</h4>
					<h4 style="color: #336699; position: absolute;">
						<b>Project Tracks </b>
					</h4>
						</div>
						<div class="x_panel">
						<c:choose>
							<c:when
								test="${sessionScope.employee.designation eq 'Program Manager'}">
								<jsp:include page="programManagerDelegateAssetPage.jsp" />
							</c:when>
							<c:when
								test="${sessionScope.employee.designation eq 'Delivery Head'}">
								<jsp:include page="deliveryHeadDelegateAssetPage.jsp" />
							</c:when>
							<c:when
								test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
								<jsp:include page="odcDelegateAssetPage.jsp" />
							</c:when>
							<c:when test="${sessionScope.employee.designation eq 'PCO Team'}">
								<jsp:include page="pcoDelegateAssetPage.jsp" />
							</c:when>
						</c:choose>
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