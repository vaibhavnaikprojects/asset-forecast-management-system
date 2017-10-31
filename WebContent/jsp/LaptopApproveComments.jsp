<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.min.js"/>"></script> 
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<title>Laptop Approve Comments</title>
</head>
<body>
<div id="banner-image">
<img src="<c:url value="/resources/images/ban.png"/>" alt="banner" class="img-responsive"/>
</div>

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
		  <div class="panel panel-default">
		    <div class="panel-heading" role="tab" id="headingOne">
		      <h4 class="panel-title" style="color: #336699">
		        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" >
		       Addition approval comments for laptop request
		        </a>
		      </h4>
		    </div>

<form id="NeedAdditionalCommentsForApproval" action="approvalComments.html" method="post" >


<div class="modal-body">
				
					
	<table>
<tr><td style='font-weight: bold; line-height: 30px;'>Created Date : </td><td>${lpbean.createdDate} </td></tr>
<tr><td style='font-weight: bold; line-height: 30px;'>Owner Name : </td><td>${lpbean.ownerName} </td></tr>
<tr><td style='font-weight: bold; line-height: 30px;'>Project Name : </td><td>${lpbean.projectName} </td></tr>
<tr><td style='font-weight: bold; line-height: 30px;'>Request Type : </td><td>${lpbean.requestType} </td></tr>
<tr><td style='font-weight: bold; line-height: 30px;'>Reason : </td><td>${lpbean.reason} </td></tr>
<tr><td style='font-weight: bold; line-height: 30px;'>Requester : </td><td>${lpbean.employeeId} </td></tr>
</table>				
					
        			

<div class="form-group">
					<label for="projectName">Enter Approval Comments</label>
<input type="text" class="form-control" name="moreinfo" id="moreinfo" placeholder="Please add laptop approval comments" required="required">
</div>
<input type="hidden" name="laptopid" id="laptopid" value="<%=request.getAttribute("laptopid") %>"/>
<input type="submit" class="btn btn-primary" value="submit comments"> 

</form>

</div></div>
<div align="justify" id="footer">
  <div class="container">
    <p class="text-muted credit">Copyright © 2015 Cisco Systems @ Zensar Technologies Inc. All right reserved.</p>
  </div>
</div>
</body>
</html>