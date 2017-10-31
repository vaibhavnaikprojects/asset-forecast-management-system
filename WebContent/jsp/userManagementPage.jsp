<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>AssetForecastingSystem::User Management</title>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
     <link href="<c:url value="/resources/css/afmscustom.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/fonts/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/animate.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet">
     <link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/maps/jquery-jvectormap-2.0.1.css"/>" />
    <link href="<c:url value="/resources/css/icheck/flat/green.css"/>" rel="stylesheet" />
    <link href="<c:url value="/resources/css/floatexamples.css"/>" rel="stylesheet" type="text/css" />
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
</head>
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
		<jsp:include page="headerPage.jsp"></jsp:include>
            <div class="right_col" role="main">
                <!-- top tiles -->
                <div class="row tile_count">
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                    
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i> Current Year</span>
                            <div class="count">${sessionScope.year}</div>
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-clock-o"></i>Current Quarter</span>
                            <div class="count">${sessionScope.quarter}</div>                           
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Current Head Count</span>
                            <div class="count">${employee.CHC}</div>
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Expected Growth</span>
                            <div class="count green">${employee.EG}</div>
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Expected Decline</span>
                            <div class="count red">${employee.ED}</div>                            
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>System Freeze Date</span>
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
                <div class="row">
		    		<div class="col-md-12 col-sm-12 col-xs-12"> 
		    		<font size="2em" >${success} <font color="red">${message}</font></font>	
						<c:choose>
						    <c:when test="${sessionScope.employee.designation eq 'Project Manager'}">
						      	<jsp:include page="projectManagerAssetPage.jsp"/>
						    </c:when>
						    <c:when test="${sessionScope.employee.designation eq 'Program Manager'}">
						    	<%if(request.getAttribute("req")!=null && request.getAttribute("req").toString().length()>1){ %>
						    	<jsp:include page="pgm_delegateproject.jsp"/> <%}else{ %><jsp:include page="programManagerUserManagementPage.jsp"/><%} %>
						    </c:when>
						    <c:when test="${sessionScope.employee.designation eq 'Delivery Head'}">
						       <jsp:include page="deliveryHeadUserManagementPage.jsp"/>
						    </c:when>
						   	<c:when test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
						        <jsp:include page="odcUserManagementPage.jsp"/>
						    </c:when>
						    <c:when test="${sessionScope.employee.designation eq 'PCO Team'}">
						         <jsp:include page="pcoUserManagementPage.jsp"/>
						    </c:when>
						    <c:when test="${sessionScope.employee.designation eq 'Admin'}">
						        Salary is very good.
						    </c:when>
						    <c:when test="${sessionScope.employee.designation eq 'LPT'}">
						        <jsp:include page="LPTLaptopPage.jsp"/>
						    </c:when>
						    
						    <c:otherwise>
						        No comment sir...ddnnlll
						    </c:otherwise>
						</c:choose>
					</div>
				</div>
				</div>
		</div>	
	</div>
	 <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/progressbar/bootstrap-progressbar.min.js"/>"></script>
    <script src="<c:url value="/resources/js/nicescroll/jquery.nicescroll.min.js"/>"></script>
    <script src="<c:url value="/resources/js/icheck/icheck.min.js"/>"></script>
    <script src="<c:url value="/resources/js/custom.js"/>"></script>
    <script>
        NProgress.done();
    </script>
<jsp:include page="footerPage.jsp"/>
</body>
</html>