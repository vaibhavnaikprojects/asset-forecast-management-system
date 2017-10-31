<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
     <link href="<c:url value="/resources/css/afmscustom.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/fonts/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/animate.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet">
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/maps/jquery-jvectormap-2.0.1.css"/>" />
    <link href="<c:url value="/resources/css/icheck/flat/green.css"/>" rel="stylesheet" />
    <link href="<c:url value="/resources/css/floatexamples.css"/>" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/js/nprogress.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery-1.11.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.freezeheader.js"/>"></script>
	<script src="<c:url value="/resources/js/jquery.fixedheadertable.js"/>"></script>
	<script src="<c:url value="/resources/js/script.js"/>"></script> 
	
	
    <script>
        NProgress.start();
     
    </script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AssetForecastingSystem::AAT</title>

</head>
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
		<jsp:include page="headerPage.jsp"></jsp:include>
            <div class="right_col" role="main">
				<div class="row">
		    		<div class="col-md-12 col-sm-12 col-xs-12">
						<form id="aatDetails">
						<input type="hidden" id="rockiesAllocatedSpace" name="rockiesAllocatedSpace" value="${requestScope.rockiesAllocatedSpace}"/>
						<input type="hidden" id="rockiesFreeSpace" name="rockiesFreeSpace" value="${requestScope.rockiesFreeSpace}"/>
						<input type="hidden" id="fujiAllocatedSpace" name="fujiAllocatedSpace" value="${requestScope.fujiAllocatedSpace}"/>
						<input type="hidden" id="fujiFreeSpace" name="fujiFreeSpace" value="${requestScope.fujiFreeSpace}"/>
						</form>
							<div class="col-md-6">
							 <div class="x_panel">
							 	<div class="aat-details">
							 	<p ><span><b> Rockies Details:</b></span></p>
								 	<p id="rockiesTotal">Rockies Total Space : ${requestScope.rockiesTotalSpace}</p>
									<p id="rockiesAllocated">Rockies Allocated Space : ${requestScope.rockiesAllocatedSpace}</p>
									<p id="rockiesFree">Rockies Free Space : ${requestScope.rockiesFreeSpace}</p>
								</div>
								<div id="piechart1" style="height: 300px;"></div>
								</div>
							 </div>
							 <div class="col-md-6">
							 <div class="x_panel">
							 	<div class="aat-details">
							 	<p><span><b>Fuji Details:</b></span></p>
							 	<p id="fujiTotal" title="${requestScope.fujiTotalSpace}">Fuji Total Space	: ${requestScope.fujiTotalSpace}</p>
								<p id="fujiAllocated" title="${requestScope.fujiAllocatedSpace}">Fuji Allocated Space : ${requestScope.fujiAllocatedSpace}</p>
								<p id="fujiFree" title="${requestScope.fujiFreeSpace}">Fuji Free Space : ${requestScope.fujiFreeSpace}</p>
								</div>
								<div id="piechart2" style="height: 300px;" ></div>
								</div>
							 </div> 
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="footerPage.jsp"/>
 <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/progressbar/bootstrap-progressbar.min.js"/>"></script>
    <script src="<c:url value="/resources/js/nicescroll/jquery.nicescroll.min.js"/>"></script>
    <script src="<c:url value="/resources/js/icheck/icheck.min.js"/>"></script>
    <script src="<c:url value="/resources/js/custom.js"/>"></script>
     <script>
        NProgress.done();
    </script>
    
    <script type="text/javascript">
	google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(rockiesChart);
      function rockiesChart() {
    	  var ras=aatDetails.rockiesAllocatedSpace.value;
    	  var rfs=aatDetails.rockiesFreeSpace.value;
    	 // alert(ras+"  "+rfs);
        var data = google.visualization.arrayToDataTable([
          ['Details', 'Space'],
          ['Rockies Allocated Space',parseInt(ras)],
          ['Rockies Free Space',parseInt(rfs)]
        ]);
        var options = {
        		title: 'Rockies Allocated Space',
                is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart1'));

        chart.draw(data, options);
      }
      google.setOnLoadCallback(fujiChart);
      function fujiChart() {
    	  var fas=aatDetails.fujiAllocatedSpace.value;
    	  var ffs=aatDetails.fujiFreeSpace.value;
    	 // alert(fas+" "+ffs);
        var data = google.visualization.arrayToDataTable([
          ['Details', 'Space'],
          ['Fuji Allocated Space',parseInt(fas)],
          ['Fuji Free Space',parseInt(ffs)]
        ]);
        var options = {
        		title: 'Fuji Allocated Space',
                is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart2'));

        chart.draw(data, options);
      }
      
    </script>
    
    
</body>
</html>