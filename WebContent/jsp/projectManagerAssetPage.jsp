<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Modal For Adding asset Track -->
<script type="text/javascript">

<%-- $(document).ready(function() {
	$.ajax({
	    url:"laptopupdate.html",
	    type: 'GET',
	    dataType: "text/html", 
	    success: function(data) { }
	  
	 });
	});
function check()
{
	if(addAssetTrack.projectName.value=="Project Name Exists,Please Enter Another Name")
		return false;
	else if(addAssetTrack.ciscoManagerName.value=="Invalid CEC ID")
		return false;
	else
		return true;
}

function managerName()
{
	var id=addAssetTrack.ciscoManagerId.value;
          
$.ajax({
url: "getManagerName.html?empId="+id,
type: "GET",
success: function(data) {
	
	if(data=="Invalid CEC ID")
		{
		addAssetTrack.ciscoManagerName.value=data;
		return false;
		}
	else
		{
		addAssetTrack.ciscoManagerName.value=data;
		return true;
		} 
},error: function() {
	alert( "Sorry, there was a problem!" );
	}
});           
}

function getAllManagersName(){
	var programManagerId="<%out.print(((EmployeeBean)request.getSession().getAttribute("employee")).getManagerId());%>";
	var deliveryHeadId="<%out.print(((EmployeeBean)request.getSession().getAttribute("employee")).getManagerId2Up());%>";
	$.ajax({
		dataType: "json",
		url: "getAllManagerName.html?programManagerId="+programManagerId+"&deliveryHeadId="+deliveryHeadId,
		type: "GET",
		success: function(data) {
				addAssetTrack.programManager.value=data.projectManager;
				addAssetTrack.deliveryHead.value=data.programManager; 
		},error: function(data) {
			alert( "Sorry, there was a problem!" );
			}
		});
}
function checkProjectName(){
	$.ajax({
		url:"checkProjectName.html?projectName="+addAssetTrack.projectName.value,
		type:"GET",
		success: function(data){
			if(data=="true")
			addAssetTrack.projectName.value="Project Name Exists,Please Enter Another Name";
			return false;
		},error: function(){
			
		}
	}); 
}
function warningSubmit(){
	var status=confirm("Do You Want To Submit");
	if(status==true){
		return true;
	}else{
		return false;
	}
} --%>
</script>
<jsp:include page="assetDetails.jsp"></jsp:include>
<div class="modal fade" id="exportCSV" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Export CSV To Local</h4>
      </div>
      <form action="downloadExcel.html" method="post" class="form-inline">
      <div class="modal-body">
				<div class="form-group">
					<label for="exportYear">Enter Year</label>
					<select class="form-control" id="exportYear" name="exportYear" required="required">
					<option value="" disabled selected>Please Select Year</option>
						<%int currentYear=(Integer)request.getSession().getAttribute("year");
							for(int i=currentYear;i>currentYear-4;i--){%>
								<option value="<%=i%>"><%=i%></option>
							<% }%>
					</select>
					<label>Select Quarter</label>
						<select class="form-control" id="exportQuarter" name="exportQuarter" required="required" title="Please select Quarter">
						<option value="" disabled selected>Please Select Quarter</option>
							<option value="QTR1">QTR1</option>
							<option value="QTR2">QTR2</option>
							<option value="QTR3">QTR3</option>
							<option value="QTR4">QTR4</option>
							<option value="ALL">YEARLY</option> 
						</select>
        			</div>
	  </div>
      <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<input type="submit" class="btn btn-primary" value="Export"/>
      </div>
      	</form>
    </div>
  </div>
</div>
<div class="heading" style="text-align: center;">
	<h4 style="color: #336699"><b><c:if test="${not empty asset}">${asset.projectName}</c:if>
	${success}
	<span style="color:#E74C3C;">${message}</span>
	</b></h4>
</div>
<jsp:include page="projectDataTable.jsp"></jsp:include>
		