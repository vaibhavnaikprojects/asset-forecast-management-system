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
<script src="<c:url value="/resources/js/script.js"/>"></script>
<%-- <script type="text/javascript"
	src="<c:url value="/resources/js/helper.js"/>"></script> --%>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<link rel="stylesheet"
	href="<c:url value="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/>" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#datetimepicker1').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter1from').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter1To').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter2from').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter2To').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter3from').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter3To').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter4from').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

	$(document).ready(function() {
		$('#quarter4To').datepicker({
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});
	function aatDetails(urlName,url) {
		editAAT.urlName.value = urlName;
		editAAT.url.value = url;
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
						<jsp:include page="aatDetails.jsp"></jsp:include>
						<div id="maintainURLS">
							<h4 style="color: #336699">
								<b> ${success} <span style="color: red;">${message}</span>
								</b>
							</h4>
							<h4 style="color: #336699">AAT URLS :</h4>
							<c:if test="${not empty requestScope.urls}">
								<div class="table-responsive">
									<table id="aatURLS" class="table table-hover">
										<thead>
											<tr>
												<th>URL Name</th>
												<th>URL</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="aatbean" items="${requestScope.urls}">
												<tr data-toggle="modal" data-id="${aatbean.urlName}"
													onclick="return aatDetails('${aatbean.urlName}','${aatbean.url}')"
													data-target="#aatDetails">
													<td><c:out value="${aatbean.urlName}" /></td>
													<td><c:out value="${aatbean.url}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:if>
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
