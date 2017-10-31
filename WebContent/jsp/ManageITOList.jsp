<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<script type="text/javascript"
	src="<c:url value="/resources/js/helper.js"/>"></script>
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
                            <span class="count_top"><i class="fa fa-clock-o"></i>Today's date</span>
                            <div class="count">
                            <jsp:useBean id="now" class="java.util.Date"/>
                            <fmt:formatDate value="${now}" pattern="dd-MM-yyyy" />
                            </div>                           
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Approved Requests</span>
                            <div class="count green">${sessionScope.approved}</div>
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Pending Approval</span>
                             <div class="count red">${sessionScope.pending}</div>
                        </div>
                    </div>
                    <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Closed Requests</span>
                            
							        <div class="count green">${sessionScope.completed}</div>
							                            
                        </div>
                    </div>
                    
                     <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Need Justification</span>
                            
							        <div class="count">${sessionScope.moreinfo}</div>
							                            
                        </div>
                    </div>
                            <div class="animated flipInY col-md-2 col-sm-4 col-xs-4 tile_stats_count">
                        <div class="left"></div>
                        <div class="right">
                            <span class="count_top"><i class="fa fa-user"></i>Total Requests</span>
                            <div class="count green">${sessionScope.total}</div>
                       </div>
                    </div>
                    
                 
                </div>
                <br>
                <div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="modal fade" id="addMailerList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Add Mailer List/Id</h4>
      </div>
      <form action="addMailerList.html" method="post" class="form-inline">
      <div class="modal-body">
				<div class="form-group">
					<label for="exportYear">Enter Mailer alias/Id</label>
					<input type="text" class="form-control" name="mailerId" id="mailerId" />
				</div>
	  </div>
      <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<input type="submit" class="btn btn-primary" value="Save"/>
      </div>
      	</form>
    </div>
  </div>
</div>

  <div class="table-responsive" style="margin-left: 5%">
		        
					<table id="mailerList" class="table table-hover scroller-size" style="width: 60%;">
					<thead >
						<tr>
						<th style="background-color: #2A3F54">Mailer List </th>
						
						<th style="background-color: #2A3F54">Delete Mailer Id</th>

						</tr>
					</thead>
					
					<tbody>
					  
					
						<c:forEach var="laptop" items="${requestScope.mailer_list}">
						<tr>
						<td><c:out value="${laptop.cclist}"/></td>
						<td>
						
						<a id="deletemailid" href="/AFMS/deletemailid.html?deletemailid=${laptop.cclist}" title="Delete Mail Id">
				   			<i class="fa fa-trash-o fa-2x" style="margin-left: 42%;color: #E74C3C"></i></a>
						</td>

	
				   			
				   		</tr>
						</c:forEach>	
					</tbody>
					<tfoot>
					<button id="addMailerList" class="btn btn-primary" data-toggle="modal" data-target="#addMailerList">Add Mailer List</button>
					</tfoot>
					</table>
					</div>

						</div>
						<c:if test="${empty requestScope.mailer_list}}">No Mailer List Created Yet</c:if>
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