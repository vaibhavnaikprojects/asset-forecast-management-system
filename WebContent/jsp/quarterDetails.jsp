<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<link rel="stylesheet" href="<c:url value="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/>"/>
<script type="text/javascript">
$(document).ready(function() {
    $('.datetimepicker1').datepicker({
          showOtherMonths: true,
          selectOtherMonths: true
    });
});
</script>
<div class="modal fade" id="quarterDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Update Quarter </h4>
      </div>
      <form id="editQuarter" action="updateQuarter.html" method="post">
					<input type="hidden" class="form-control" name="quarter" id="quarter"/>
					
					<input type="hidden" class="form-control" name="year" id="year"/>
      <div class="modal-body">
      	<table class="table">
					<tr>
					<td>Start Date</td>
					<td><input type="text" class="form-control datetimepicker1" name="startDate" id="startDate" required="required" /></td>
					</tr>
					<tr>
					<td>End Date</td>
					<td><input type="text" class="form-control datetimepicker1" name="endDate" id="endDate" required="required" /></td>
					</tr>
				
				
				
					
        </table>
	  </div>
	   <div class="modal-footer" >
	   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
       <c:if test="${sessionScope.employee.designation eq 'PCO Team'}"> <button type="submit" class="btn btn-primary">Update Quarter</button></c:if>
       </div>
       </form>
   	</div>
    </div>
  </div>