<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

function catchEvent()
{
	return confirm("Are you sure you want to delete ?");
	}
</script>

<div class="modal fade" id="assetDetails" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Update Asset Track</h4>
      </div>
      <form id="editAssetTrack" action="updateAssetTrack.html" method="post">
      <div class="modal-body">
      <input type="hidden" class="form-control" name="projectId" id="projectId"/>
      <input type="hidden" class="form-control" name="ciscoManagerId" id="ciscoManagerId"/>
      <input type="hidden" class="form-control" name="ciscoManagerName" id="ciscoManagerName"/>
      <input type="hidden" class="form-control" name="previousquarter" id="previousquarter" required="required" readonly="readonly"/>
	  <input type="hidden" class="form-control" name="userId" id="userId" required="required" readonly="readonly"/>			
      	<table class="table-hover table">
					<tr>
					<td>Project Name</td>
					<td><input type="text" class="form-control" name="projectName" id="projectName" required="required" readonly="readonly"/></td>
					</tr>
					<tr>
					<td>Current Head Count</td>
					<td><input type="number" class="form-control" name="currentHeadCount" max="500" id="currentHeadCount" required="required" readonly="readonly"/></td>
					</tr>
					<tr>
					<td>Growth/Decline Count</td>
					<td><c:if test="${sessionScope.employee.designation ne 'Cisco ODC Head'}">
					<input type="number" class="form-control" name="growthCount" id="growthCount" max="100" required="required" placeholder="Add New Quarters Count"/></c:if>
					<c:if test="${sessionScope.employee.designation eq 'Cisco ODC Head'}"><input type="number" class="form-control" name="growthCount" id="growthCount" required="required" placeholder="Add New Quarters Count" readonly="readonly"/></c:if>
					</td>
					</tr>
					<tr>
					<td>Quarter</td>
					<td><input type="text" class="form-control" name="quarter" id="quarter" required="required" value="${sessionScope.quarter}" readonly="readonly"/></td>
					</tr>
					<tr>
					<td>Project Location</td>
					<td><select id="projectLocation" name="projectLocation" class="form-control" required="required" disabled="disabled">
						<option value="none" disabled>Please select Status</option>
						<option value="Pune">Pune</option>
						<option value="Bangalore">Bangalore</option>
					</select></td>
					</tr>
					<tr>
        			<td>Project Manager</td>
					<td><input type="text" class="form-control" name="projectManager" id="projectManager"  required="required" readonly="readonly"/></td>
        			</tr>
        			<tr>
        			<td>Program Manager</td>
					<td><input type="text" class="form-control" name="programManager" id="programManager" required="required" readonly="readonly"/></td>
        			</tr>
        			<tr>
        			<td>Delivery Head</td>
					<td><input type="text" class="form-control" name="deliveryHead" id="deliveryHead" required="required" readonly="readonly"/></td>
        			</tr>
        </table>
	  </div>
	   <div class="modal-footer" >
	   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
       <c:if test="${sessionScope.employee.designation ne 'Cisco ODC Head'}"> <button type="submit" class="btn btn-primary">Update Track</button></c:if>
        
      	 <%-- <form id="deleteAssetTrack" method="post" action="deleteAssetTrack.html">
      			<input type="hidden" class="form-control" name="projectId" id="projectId"/>
      			<c:if test="${sessionScope.employee.designation ne 'Cisco ODC Head'}"> <button type="submit" class="btn btn-danger " style="position: absolute; top:93.5%;left:3%;" onclick="return catchEvent()">Delete Track</button></c:if>
      		</form> --%>
       </div>
       </form>
   	</div>
    </div>
  </div>
  <script>
  $('#editAssetTrack').submit(function() {
	    $('#projectLocation').removeAttr('disabled');
	});
  </script> 
  