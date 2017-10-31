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
		$('#datetimepicker2').datepicker({
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
	function freezeDateDetails(freezeId){
		$.ajax({
			dataType: "json",
			url: "getSystemFreezeFromId.html?freezeId="+freezeId,
			type: "GET",	
			success: function(data) {
				if(data!=null){
					
					editFreezeDate.freezeId.value=data.freezeId;
					editFreezeDate.year.value=data.year;
					editFreezeDate.quarter.value=data.quarter;
					editFreezeDate.freezeDate.value=data.freezeDate;
				}
				else{alert("data not loaded");}
			},error: function(data) {
				alert( "Sorry, there was a problem!" );
				}
			});
	}
	function dateValidation(){
		var freezedate=editFreezeDate.freezeDate.value;
		if(freezedate.indexOf("-")>0){
			var dateArr=freezedate.split("-");
			freezedate=dateArr[1]+"/"+dateArr[2]+"/"+dateArr[0];
		}
		freezedateArr=freezedate.split("/");
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();
		if(dd<10) {
		    dd='0'+dd
		} 
		if(mm<10) {
		    mm='0'+mm
		} 
		if(freezedateArr[2]<yyyy){
			alert("Please choose forthcoming date");
			return false;
		}
		else if(freezedateArr[2]==yyyy && freezedateArr[0]<mm){
			alert("Please choose forthcoming date");
			return false;
		}
		else if(freezedateArr[2]==yyyy && freezedateArr[0]==mm && freezedateArr[1]<dd){
			alert("Please choose forthcoming date");
			return false;
		}
		return true;
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
				<div class="modal fade" id="freezeDateDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Update freeze date </h4>
      </div>
      <form id="editFreezeDate" action="updateFreezeDate.html" method="post">
      		<div class="modal-body">
      			<table class="table">
      			<tr>
					<td>Quarter
      				<input type="hidden" class="form-control" name="freezeId" id="freezeId"/>
					<input type="hidden" class="form-control" name="year" id="year"/>
					</td>
					<td>
					<input type="text" class="form-control" name="quarter" id="quarter" readonly="readonly"/>
					</td>
				</tr>
					<tr>
					<td>Freeze Date</td>
					<td><input type="text" class="form-control " name="freezeDate" id="datetimepicker1" required="required"/></td>
					</tr>
        </table>
	  </div>
	   <div class="modal-footer" >
	   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
       <c:if test="${sessionScope.employee.designation eq 'PCO Team'}"> <button type="submit" class="btn btn-primary" onclick="return dateValidation();">Update Quarter</button></c:if>
       </div>
       </form>
   	</div>
    </div>
  </div>
				<div class="modal fade" id="setSystemFreezeDate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  				<div class="modal-dialog" role="document">
    				<div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Set System Freeze Date</h4>
      </div>
      <form action="systemFreeze.html" method="post">
      <div class="modal-body">
				<div class="form-group">
								<label for="fiscalYear">Fiscal Year</label> 
								<select class="form-control" name="fiscalYear"
														id="fiscalYear" required="required">
														<option value="" disabled selected>Please Select
															Fiscal Year</option>
														<%
														int currentYear = (Integer) request.getSession().getAttribute(
																"year");
														for (int i = currentYear; i < currentYear + 2; i++) {
													%>
														<option value="<%=i%>"><%=i%></option>
														<%
														}
													%>
								</select>
								<label for="quarter">Quarter</label> 
								<select class="form-control" id="quarter" name="quarter" required="required" title="Please select Quarter">
									<option value="" disabled selected>Please Select Quarter</option>
									<option value="1">QTR1</option>
									<option value="2">QTR2</option>
									<option value="3">QTR3</option>
									<option value="4">QTR4</option>
								</select>
									<label for="freezeDate">Enter Date</label> 
									<input
										class="form-control" type="text" name="freezeDate"
										id="datetimepicker2" required="required" />
								</div>
	  </div>
      <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<input type="submit" class="btn btn-primary" onclick="return dateValidation();" value="Submit"/>
      </div>
      	</form>
    </div>
  </div>
</div>
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div id="systemFreeze">
							<h4 style="color: #336699">
								<b> ${success} <span style="color: red;">${message}</span>
								</b>
							</h4>
							<h4 style="color: #336699">System Freeze Dates :</h4>
							<button id="setSystemFreezeDate" class="btn btn-primary" data-toggle="modal" data-target="#setSystemFreezeDate">Set system freeze date</button>
							<div class="table-responsive">
									<table id="quarters" class="table table-hover">
										<thead>
											<tr>
												<th>Quarter</th>
												<th>Freeze Date</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="freezeDate" items="${requestScope.freezeDates}">

												<tr data-toggle="modal" data-id="${freezeDate.freezeId}"
													onclick="return freezeDateDetails(${freezeDate.freezeId})"
													data-target="#freezeDateDetails">
													<td><c:out value="${freezeDate.quarter}" /></td>
													<td><c:out value="${freezeDate.freezeDate}" /></td>
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