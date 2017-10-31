/**
 * 
 */

$(document).ready(function() {
	$('#selectstatusFilter').on('change', function() {

		document.forms["statusFilter"].submit();
	});
});

$(document).ready(function() {
	$('#selectall').click(function(event) { // on click
		if (this.checked) { // check select status
			$('.case').each(function() { // loop through each checkbox
				this.checked = true; // select all checkboxes with class
				// "checkbox1"
			});
		} else {
			$('.case').each(function() { // loop through each checkbox
				this.checked = false; // deselect all checkboxes with class
				// "checkbox1"
			});
		}
	});

});

$(document).ready(function() {
	$('#allselect').click(function(event) { // on click
		if (this.checked) { // check select status
			$('.empSub').each(function() { // loop through each checkbox
				this.checked = true; // select all checkboxes with class
				// "checkbox1"
			});
		} else {
			$('.empSub').each(function() { // loop through each checkbox
				this.checked = false; // deselect all checkboxes with class
				// "checkbox1"
			});
		}
	});

});
$(document).ready(function() {
	$("#downloadimage").tooltip();
	$("#Remindersforlpapproval").tooltip();

});

/*
 * $(document).ready(function() {
 * 
 * $("table#assets tr:odd").css("background-color", "#EFF1F1"); });
 * $(document).ready(function() {
 * 
 * $("table#laptopRequests tr:odd").css("background-color", "#EFF1F1"); });
 * $(document).ready(function() { $("#laptopRequests").freezeHeader({ 'height' :
 * '400px' });
 * 
 * });
 * 
 * $(document).ready(function() { $("table#employee
 * tr:odd").css("background-color", "#EFF1F1"); $("table#assets12
 * tr:odd").css("background-color", "#EFF1F1"); $("table#assets11
 * tr:odd").css("background-color", "#EFF1F1"); $("table#assets10
 * tr:odd").css("background-color", "#EFF1F1"); $("table#employee10
 * tr:odd").css("background-color", "#EFF1F1");
 * 
 * 
 * 
 * 
 * }); $(document).ready(function() {
 * 
 * $("#employee").freezeHeader({ 'height' : '400px' });
 * 
 * 
 * 
 * }); $(document).ready(function() {
 * 
 * $("#employeemanagement").freezeHeader({ 'height' : '200px' });
 * 
 * $("table#employeemanagement tr:odd").css("background-color", "#EFF1F1");
 * 
 * });
 */

/*
 * $(document).ready(function() {
 * 
 * $("#quarterSubmission").freezeHeader({ 'height' : '200px' });
 * 
 * $("table#employeemanagement tr:odd").css("background-color", "#EFF1F1");
 * 
 * });
 * 
 * 
 * 
 * $(document).ready(function() { $("table#employee2
 * tr:odd").css("background-color", "#EFF1F1"); $("#employee2").freezeHeader({
 * 'height' : '400px' }); });
 * 
 * $(document).ready(function() { $("table#employee1
 * tr:odd").css("background-color", "#EFF1F1"); $("#employee1").freezeHeader({
 * 'height' : '400px' }); });
 * 
 * 
 * $(document).ready(function() { $("table#employee3
 * tr:odd").css("background-color", "#EFF1F1"); $("#employee3").freezeHeader({
 * 'height' : '400px' }); });
 * 
 * 
 * $(document).ready(function() { $("table#employee4
 * tr:odd").css("background-color", "#EFF1F1"); $("#employee4").freezeHeader({
 * 'height' : '400px' }); });
 * 
 * $(document).ready(function() { $("table#employee5
 * tr:odd").css("background-color", "#EFF1F1"); $("#employee5").freezeHeader({
 * 'height' : '400px' }); });
 * 
 * 
 * 
 * $(document).ready(function() {
 * 
 * $("table#laptopRequests tr:odd").css("background-color", "#EFF1F1");
 * $("#laptopRequests").freezeHeader({ 'height' : '400px' }); });
 * 
 * $(document).ready(function() {
 * 
 * $("table#assets1 tr:odd").css("background-color", "#EFF1F1");
 * $("#assets1").freezeHeader({ 'height' : '400px' }); });
 * $(document).ready(function() {
 * 
 * $("#assets").freezeHeader({ 'height' : '400px', }); });
 * $(document).ready(function() {
 * 
 * $("#assets5").freezeHeader({ 'height' : '400px', }); });
 * 
 * $(document).ready(function() {
 * 
 * $("#assets2").freezeHeader({ 'height' : '400px', }); });
 */


$(document).ready(function() { 
	$('#submitQuarterlyReports').click(function(e) {
		e.preventDefault();
		$('#savetrack').submit();
		$.get( "submitQuarterlyReports.html" );
	});
});

$(document).ready(function() { 
	$( "#addUser").submit(function( event ) {
		var designation =  $('#designation').val();
		var userId = $.trim( $('#userId').val());
		var managerId =  $.trim($('#managerId').val());
		var managerId2Up =  $.trim($('#managerId2Up').val());


		if((designation =='Project Manager') && (userId==managerId))
		{
			alert("User and Manager id can't be same");
			event.preventDefault();
		}
		else if((userId==managerId || userId== managerId2Up)&&(designation == 'Project Manager' || designation == 'Program Manager' ) )
		{
			alert("User and Manager id  or Manager and 2Up Manager Id can't be same");
			event.preventDefault();
		}
	});

});// Unrelated to sticky header function

$(document).ready(function() {
	$( "#designation").change(function( event ) {


		$("#managerId").empty();

		var option='';

		var designation =  $('#designation').val();
		var userId = $.trim( $('#userId').val());
		var managerId =  $.trim($('#managerId').val());
		var managerId2Up =  $.trim($('#managerId2Up').val());
		if(designation =='Cisco ODC Head' ||  designation =='PCO Team')
		{
			$('#oneUp').hide();
			$('#twoUp').hide();
		}
		else if(designation =='Delivery Head')
		{
			$('#oneUp').show();
			$('#1up').text("Cisco ODC Head");
			$('#twoUp').hide();
		}else if(designation=='Project Manager'){
			$('#oneUp').show();
			$('#1up').text("Program Manager");
			$('#twoUp').show();
			$('#2up').text("Delivery Head");

		}
		else if(designation=='Program Manager')
		{
			$('#oneUp').show();
			$('#1up').text("Delivery Head");
			$('#twoUp').show();
			$('#2up').text("Cisco ODC Head");

		}
	});	
});

$(document).ready(function() { 
	$( "#updateEmployee").submit(function( event ) {
		var designation =  $('#designation').val();
		var userId = $.trim( $('#userId').val());
		var managerId =  $.trim($('#managerId').val());
		var managerId2Up =  $.trim($('#managerId2Up').val());


		if((designation =='Project Manager') && (userId==managerId))
		{
			alert("User and Manager id can't be same");
			event.preventDefault();
		}
		else if((userId==managerId || userId== managerId2Up)&&(designation == 'Project Manager' || designation == 'Program Manager' ) )
		{
			alert("User and Manager id  or Manager and 2Up Manager Id can't be same");
			event.preventDefault();
		}
	});

});// Unrelated to sticky header function

$(document).ready(function() {
	$('#laptopRequests').DataTable( {
		"pagingType": "simple" /* full_numbers */
	} );
} );

function laptopDetails(uniquelaptopid,ownerName,requestType,reason,projectName,createdDate,requestStatus,stock,fromUserId,toUserId,RemarksFromLA,statusFlagValue){
	document.getElementById("uniquelaptopid").innerHTML=uniquelaptopid;
	document.getElementById("ownerName").innerHTML=ownerName;
	document.getElementById("requestType").innerHTML=requestType;
	document.getElementById("reason").innerHTML=reason;
	document.getElementById("projectName").innerHTML=projectName;
	document.getElementById("createdDate").innerHTML=createdDate;
	document.getElementById("requestStatus").innerHTML=requestStatus;
	document.getElementById("stock").innerHTML=stock;
	document.getElementById("fromUserId").innerHTML=fromUserId;
	document.getElementById("toUserId").innerHTML=toUserId;
	alert(statusFlagValue);
}
$(document).ready(function() {
	$('#assets').DataTable( {
		"pagingType": "simple" /* full_numbers */
	} );
} );

var clicked;
function test(id){

	if(clicked===id)
	{
		var table = $("#_"+id).DataTable();
		$('#'+id+" table").on('click', function () {
			table.destroy();
		} );		

	}
	else
	{
		clicked=id;
		$('#_'+id).DataTable( {
			"pagingType": "simple" /* full_numbers */
		} );
	}
}


function test1(id){
	if(clicked===id)
	{
		var table = $("#_"+id).DataTable();
		$('#'+id+" table").on('click', function () {
			table.destroy();
		} );		

	}
	else
	{
		clicked=id;
		$('#123_'+id).DataTable( {
			"pagingType": "simple" /* full_numbers */
		} );
	}
}

function test2(id){
	if(clicked===id)
	{
		var table = $("#_"+id).DataTable();
		$('#'+id+" table").on('click', function () {
			table.destroy();
		} );		

	}
	else
	{
		clicked=id;
		$('#456_'+id).DataTable( {
			"pagingType": "simple" /* full_numbers */
		} );
	}
}

$(document).ready(function() {

	$(".ed").attr("title", "Expected Decline (ED)");
	$(".ed").attr("data-toggle", "tooltip");
	$(".ed").attr("data-placement", "bottom");

	$(".eg").attr("title", "Expected Growth (EG)");
	$(".eg").attr("data-toggle", "tooltip");
	$(".eg").attr("data-placement", "bottom");

	$(".chc").attr("title", "Current Head Count (CHC)");
	$(".chc").attr("data-toggle", "tooltip");
	$(".chc").attr("data-placement", "bottom");
} );

function laptopDetailsForMoreInfo(laptopId,ownerName,requestType,projectName,createdDate,oldReason,uniquelaptopid,addInfo)
{
	editLaptopMoreInfoDetails.laptopId.value=laptopId;
	editLaptopMoreInfoDetails.ownerName.value=ownerName;
	editLaptopMoreInfoDetails.requestType.value=requestType;
	editLaptopMoreInfoDetails.oldReason.value=oldReason;
	editLaptopMoreInfoDetails.createdDate.value=createdDate;
	editLaptopMoreInfoDetails.projectName.value=projectName;
	editLaptopMoreInfoDetails.uniquelaptopid.value=uniquelaptopid;
	editLaptopMoreInfoDetails.addInfo.value=addInfo;
}


function checkDoller(){
	var reason1=requestNewHireRequest.reason.value;
	var reason2=requestEarlyRefreshRequest.reason.value;
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

