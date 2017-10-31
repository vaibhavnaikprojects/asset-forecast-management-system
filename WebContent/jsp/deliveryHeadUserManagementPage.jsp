<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
function employeeDetails(userId){
	$.ajax({
		dataType: "json",
		url: "getEmployeeDetailsFromId.html?userId="+userId,
		type: "GET",
		success: function(data) {
			if(data!=null){
				updateEmployee.employeeName.value=data.employeeName;
				updateEmployee.designation.value=data.designation;
				updateEmployee.ogdesignation.value=data.designation;
				updateEmployee.userId.value=data.userId;
				updateEmployee.managerId.value=data.managerId;
				updateEmployee.managerId2Up.value=data.managerId2Up;
				updateEmployee.CHC.value=data.CHC;
				updateEmployee.EG.value=data.EG;
				updateEmployee.ED.value=data.ED;
			}
			else{alert("data not loaded");}
		},error: function(data) {
			alert( "Sorry, there was a problem!" );
			}
		});
}
function managerName()
{
	
	var id=document.getElementById("userId").value;
    
$.ajax({
url: "getManagerName.html?empId="+id,
type: "GET",
success: function(data) {
	
	if(data=="Invalid CEC ID")
		{
		document.getElementById("employeeName").value=data;
		return false;
		}
	else
		{
		document.getElementById("employeeName").value=data;
		return true;
		} 
},error: function() {
	alert( "Sorry, there was a problem!" );
	}
});     
}
$(document).ready(function() {
    $(":checkbox").change(function(event) { 
   		
   		
    	var name = this.name;
    	
    	if(this.checked) { 
    		
            $("."+name+"delegate").each(function() {
        
                this.checked = true;              
            });
        }else{
            $("."+name+"delegate").each(function() {
        
                this.checked = false;                       
            });        
        }
    });
   
}); 

$(document).ready(function() {
    $("#selectallintegrated").change(function(event) { 
   		
   		if(this.checked) { 
    		
            $(".delegateintegrated").each(function() {
        
                this.checked = true;              
            });
        }else{
            $(".delegateintegrated").each(function() {
        
                this.checked = false;                       
            });        
        }
    });
   
}); 
function viewchange(){
	var selectedView=document.getElementById("viewFilter").value;
	if(selectedView=="fork"){
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="block";
		document.getElementById("integrated").style.display="none";
	}else if(selectedView=="join"){
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("integrated").style.display="block";
	}else{
		document.getElementById("integrated").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("PMView").style.display="block";
	}
}
</script>

<!-- Modal For Adding User -->


<div class="col-xs-3"> <!-- required for floating -->
  <!-- Nav tabs -->
  <ul class="nav tabs-left">
    	<li class="active"><a href="#maintainUsers" data-toggle="tab">Maintain Users</a></li>
	    <li><a href="#maintainDeactivatedAssets" data-toggle="tab">Delegate Assets</a></li>
  </ul>
</div>
<div class="col-xs-9">
    <!-- Tab panes -->
    <div class="tab-content">

</div>
