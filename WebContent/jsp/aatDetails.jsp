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
<div class="modal fade" id="aatDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Update AAT URL </h4>
      </div>
      <form id="editAAT" action="updateAATURL.html" method="post">
      <div class="modal-body">
      	<table class="table">
					<tr>
					<td>URL Name</td>
					<td><input type="url" class="form-control" name="urlName" id="urlName" required="required" readonly="readonly"/></td>
					</tr>
					<tr>
					<td>URL</td>
					<td><input type="url" class="form-control" name="url" id="url" required="required" /></td>
					</tr>	
        </table>
	  </div>
	   <div class="modal-footer" >
	   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
       <c:if test="${sessionScope.employee.designation eq 'PCO Team'}"> <button type="submit" class="btn btn-primary">Update URL</button></c:if>
       </div>
       </form>
   	</div>
    </div>
  </div>