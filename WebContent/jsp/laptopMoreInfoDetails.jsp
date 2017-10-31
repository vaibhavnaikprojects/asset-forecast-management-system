<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="laptopMoreInfoDetails" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Update Reason</h4>
			</div>
			<form id="editLaptopMoreInfoDetails"
				action="editLaptopMoreInfoDetails.html" method="post">
				<div class="modal-body">
					<input type="hidden" class="form-control" name="laptopId"
						id="laptopId" />
					<input type="hidden" class="form-control" name="uniquelaptopid"
						id="uniquelaptopid" /> 
					<table class="table-hover table">
						<tr>
							<td>Owner Name</td>
							<td><input type="text" class="form-control"
								name="ownerName" id="ownerName" required="required"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<td>Request Type</td>
							<td><input type="text" class="form-control"
								name="requestType" max="500" id="requestType"
								required="required" readonly="readonly"/></td>
						</tr>
						<tr>
							<td>Project Name</td>
							<td>
							<input type="text" class="form-control" name="projectName"
										id="projectName" max="100" required="required" readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td>Created Date</td>
							<td><input type="text" class="form-control" name="createdDate"
								id="createdDate" required="required"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<td>ODC Head Comments</td>
							<td>
							<textarea class="form-control" name="addInfo" id="addInfo" 
							required="required" cols="50" rows="2" readonly="readonly" maxlength="2000"></textarea>
						</tr>
						<tr>
							<td>Old Reason</td>
							<td>
							<textarea class="form-control" name="oldReason" id="oldReason" 
							required="required" cols="50" rows="3" readonly="readonly" maxlength="2000"></textarea>
						</tr>
						<tr>
							<td>New Reason</td>
							<td>
							<textarea class="form-control" name="reason" id="reason" maxlength="2000"
							required="required" cols="50" rows="3" ></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<input type="submit" class="btn btn-default" value="Update Reason">
				</div>
			</form>
		</div>
	</div>
</div>
