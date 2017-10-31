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
<script type="text/javascript" src="<c:url value="/resources/js/helper.js"/>"></script>
<script  type="text/javascript" src="<c:url value="/resources/js/dataTable.js"/>"></script>
<script>
	NProgress.start();
</script>
<script type="text/javascript">
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

<style>
.carousel-inner>.item>img, .carousel-inner>.item>a>img {
	width: 70%;
	margin: auto;
}
</style>

</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<jsp:include page="headerPage.jsp"></jsp:include>
			<div class="right_col" role="main">
				<div class="row">
		    		<div class="col-md-12 col-sm-12 col-xs-12">
		    			<div class="x_panel">
		    				<h4>About AFMS</h4>
		    				<p>AFMS system facilitates automation of project asset forecasting workflow which is done essentially at the beginning of every quarter.This will enable PCO team to track anticipated growth/decline pertaining to the project.</p>

							<p>This system will help delivery personnel to manage Laptop requests and transfer requests for new hire and refresh cases respectively.For every new hire and early refresh within the projects, Laptop request is raised using email. No proper tracking and well-designed flow was present.</p> 

							<p>AFMS has implemented well defined procedure by which users can create and track laptop requests in just few seconds.</p>

							<p>AFMS will help users to create laptop request, send reminder to ODC head for the request.</p>
		    			</div>
		    			<div class="x_panel">
		    				<h4>Intiated By</h4>
		    				<h5>Satyajit Baswanti</h5>
		    				<h5>PCO TEAM</h5>
		    			</div>
		    			<div class="x_panel">
		    				<h4>Development Team</h4>
		    				<h5>Vaibhav Naik</h5>
		    				<h5>Ajay Naik</h5>
		    				<h5>Anand Dembla</h5>
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