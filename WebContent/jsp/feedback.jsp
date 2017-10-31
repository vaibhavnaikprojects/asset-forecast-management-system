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
<title>AssetForecastingSystem::Laptop</title>
<script type="text/javascript">
function checkDollerForAppreciation(){
	var reason1=appreciateForm.app.value;
	var reason2=helpForm.help.value;
	if(reason1.length>0){
		if(reason1.indexOf("$")!=-1){
			alert("cannot add $");
			return false;
		}
	}
	if(reason2.length>0){
		if(reason2.indexOf("$")!=-1){
			alert("cannot add $");
			return false;
		}
	}
	return true;
}
	$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})
</script>
</head>
<body class="nav-md">
    <div class="container body">
        <div class="main_container">
		<jsp:include page="headerPage.jsp"></jsp:include>
		            <div class="right_col" role="main">

	<br><br><br><br>
	<h4 style="color: #336699"><b>
	${success}</b></h4>
	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
	
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingOne">
				<h4 class="panel-title" style="color: #336699">
					<a role="button" data-toggle="collapse" data-parent="#accordion"
						href="#collapseOne" aria-expanded="true"
						aria-controls="collapseOne"> Appreciate System </a>
				</h4>
			</div>
			<div id="collapseOne" class="panel-collapse collapse" role="tabpanel"
				aria-labelledby="headingOne">
				<div class="panel-body">
					<div class="form-group">
						<div id="my-tab-content" class="tab-content">
        					<div class="tab-pane active" id="app">
        
        <!-- <h3><label for="rate">Rate This App</label></h3> -->
        
        <form action="feedbackaction.html" method="post" id="appreciateForm">
        <textarea rows="5" cols="40" class="form-control" name="app" id="app" placeholder="Few words of Appreciation"  maxlength="1999" required="required" style="border-radius:8px"></textarea>
        <br>
        
            <input type="submit" name="Submit" value="Submit" class="btn btn-primary" onclick="return checkDollerForAppreciation();"  id="appreciateSubmit">
            
            </form>
        </div>
        </div>		
        				
        				</div>

					</div>
				</div>
			</div>
		

		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingTwo">
				<h4 class="panel-title" style="color: #336699">
					<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapseTwo" aria-expanded="false"
						aria-controls="collapseTwo"> Help us Improve </a>
				</h4>
			</div>
			<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel"
				aria-labelledby="headingTwo">
				<div class="panel-body">
					<div class="form-group">
						
						<div class="tab-pane active" id="help">
        <form action="feedbackaction.html" method="post" id="helpForm" value="appreciate">
         
         <textarea rows="5" cols="40" class="form-control" name="help" id="help" placeholder="Help Us to Improve System" required="required"  maxlength="1999"  style="border-radius:8px"></textarea>
        <br>
            <input type="submit" name="Submit" value="Submit" onclick="return checkDollerForAppreciation();" class="btn btn-primary">
         
         </form>
        </div>
						
						
					</div>
				</div>
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