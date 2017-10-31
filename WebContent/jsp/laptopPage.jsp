<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
     <link href="<c:url value="/resources/css/afmscustom.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/fonts/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/animate.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/dataTable.css"/>" rel="stylesheet" type="text/css" />
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
    <script>
        NProgress.start();
    </script>
<title>AssetForecastingSystem::Laptop</title>
<script type="text/javascript">
	function alertFilename()
	{
		document.getElementById('ciscoManagerMail').onchange = function () {
			var fileName=this.value;
			var fileExt=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length);
			if(fileExt!="msg")
				{
				alert("Kindly Attach a Mail file(.msg)");
				return false;
				}
			};
	}
	$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})
</script>
</head>
<body>
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
		<jsp:include page="headerPage.jsp"></jsp:include>
		            <div class="right_col" role="main">
                <!-- top tiles -->
                <!-- /top tiles -->
				<div class="row">
		    		<div class="col-md-12 col-sm-12 col-xs-12">
		    		
		    		<center><h4><font color="green">${success}</font></h4>
		    		<h4><font color="red">${message}</font></h4>
		    		</center>
		    		<div class="x_panel">
		    		<jsp:include page="laptopDetails.jsp"></jsp:include>
		    		<c:choose>
					    <c:when test="${sessionScope.employee.designation eq 'Project Manager'}">
				      	<jsp:include page="projectManagerLaptopPage.jsp"/>
					    </c:when>
					    <c:when test="${sessionScope.employee.designation eq 'Program Manager'}">
					     	<jsp:include page="programManagerLaptopPage.jsp"/>
					    </c:when>
					    <c:when test="${sessionScope.employee.designation eq 'Delivery Head'}">
					       	<jsp:include page="deliveryHeadLaptopPage.jsp"/>
					       	 
					    </c:when>
					    <c:when test="${sessionScope.employee.designation eq 'Cisco ODC Head'}">
					       	<jsp:include page="odcLaptopPage.jsp"/>
					      
					    </c:when>
					    
					    <c:when test="${sessionScope.employee.designation eq 'PCO Team'}">
					    </c:when>
					    <c:when test="${sessionScope.employee.designation eq 'Admin'}">
					    </c:when>
					    <c:when test="${sessionScope.employee.designation eq 'LPT'}">
				        <jsp:include page="LPTLaptopPage.jsp"/>
				    	</c:when>
					    <c:otherwise>
					        No comment sir...
					    </c:otherwise>
						</c:choose>
						</div>
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