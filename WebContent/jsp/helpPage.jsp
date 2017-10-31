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
	<link href="<c:url value="/resources/css/lightcase.css"/>"
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
<%-- <script src="<c:url value="/resources/js/lightcase.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.events.touch.js"/>"></script> --%>


<style type="text/css">
.container img { width:22%; margin:1%;}
.clear { clear:both;}
</style> 

<script>
	NProgress.start();
</script>

</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<jsp:include page="headerPage.jsp"></jsp:include>
			<div class="right_col" role="main">



				<div class="container">
					<div class="gallery-lightbox">
				<a href="resources/images/LoginCopy.jpg" id="example2" class="showcase"
							data-rel="lightcase:myCollection:slideshow" title="Login Screen">
							<img src='<c:url value="/resources/images/Login.jpg"></c:url>'/>
			</a> 
			
			<a href="resources/images/BeforeSaveCopy.jpg" id="example3"
							class="showcase" data-rel="lightcase:myCollection:slideshow"
							title="Before Saving data"> <img
							src='<c:url value="/resources/images/BeforeSave.jpg"></c:url>'/>
			</a> 
			
			<a href="resources/images/AfterSaveCopy.jpg" id="example4"
							class="showcase" data-rel="lightcase:myCollection:slideshow"
							title="After Saving data"> <img
							src='<c:url value="/resources/images/AfterSave.jpg"></c:url>'/>
						</a>
						
						
			<a href="resources/images/BeforeSubmitCopy.jpg" id="example1" class="showcase"
							data-rel="lightcase:myCollection:slideshow" title="After Submiting">
							<img src='<c:url value="/resources/images/BeforeSubmit.jpg"></c:url>'/>
			</a>
			
			 <a href="resources/images/SpecificExportCopy.jpg" id="example3"
							class="showcase" data-rel="lightcase:myCollection:slideshow"
							title="Export Tracks"> <img
							src='<c:url value="/resources/images/SpecificExport.jpg"></c:url>'/>
			</a> 
			
			
			<a href="resources/images/DefaultLaptopScreenCopy.jpg" id="example4"
							class="showcase" data-rel="lightcase:myCollection:slideshow"
							title="Default Laptop Screen"> <img
							src='<c:url value="/resources/images/DefaultLaptopScreen.jpg"></c:url>'/>
			</a>
						
						

					</div>
				</div>
				<script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
				<script type="text/javascript">
					var _gaq = _gaq || [];
					_gaq.push([ '_setAccount', 'UA-36251023-1' ]);
					_gaq.push([ '_setDomainName', 'jqueryscript.net' ]);
					_gaq.push([ '_trackPageview' ]);

					(function() {
						var ga = document.createElement('script');
						ga.type = 'text/javascript';
						ga.async = true;
						ga.src = ('https:' == document.location.protocol ? 'https://ssl'
								: 'http://www')
								+ '.google-analytics.com/ga.js';
						var s = document.getElementsByTagName('script')[0];
						s.parentNode.insertBefore(ga, s);
					})();
				</script>


			</div>
		</div>
	</div>

<script src="<c:url value="/resources/js/lightcase.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.events.touch.js"/>"></script>


			 	<script>
					$('.showcase').lightcase();
				</script> 


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