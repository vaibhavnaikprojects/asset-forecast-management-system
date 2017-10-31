<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	function viewchange() {
		var selectedView = document.getElementById("viewFilter").value;
		if (selectedView == "forkjoin") {
			document.getElementById("integrated").style.display = "none";
			document.getElementById("PMView").style.display = "block";
		} else {
			document.getElementById("PMView").style.display = "none";
			document.getElementById("integrated").style.display = "block";
		}
	}
</script>
<div class="view-filter ">
	<span style="color: #336699;">View Filter</span> <select
		name="viewFilter" id="viewFilter" onchange="viewchange()">
		<option value="forkjoin">PM View</option>
		<option value="join">Project View</option>
	</select>
</div>
<div id="PMView">
	<h4 style="color: #336699">Delegate Project Tracks Under Project
		Manager</h4>
	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<c:forEach var="projectManagers"
			items="${sessionScope.employee.employeeBeans}">
			<c:if test="${projectManagers.status eq 'A'}">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab"
						id="${projectManagers.employeeName}">
						<span class="panel-title" style="color: #336699;" >
							<a style="font-family:Arial,sans-serif;" role="button" data-toggle="collapse" data-parent="#accordion"
								href="#${fn:replace(projectManagers.employeeName,' ', '')}"
								aria-expanded="true"
								aria-controls="${projectManagers.employeeName}" onclick="return test('${projectManagers.userId}')">
								${projectManagers.employeeName} </a>
						</span>
					</div>
					<div id="${fn:replace(projectManagers.employeeName,' ', '')}"
						class="panel-collapse collapse" role="tabpanel"
						aria-labelledby="${projectManagers.employeeName}">
						<div class="panel-body">
							<c:if test="${not empty projectManagers.assetBeans}">
								<form action="delegateAssets.html" id="delegeateAssets"
									method="post">
									<input type="submit" class="btn btn-primary" value="Delegate" style="margin-left: 88%">
									<label for="delegateTo">Delegate To</label> <select
										class="form-control" id="delegateTo" name="delegateTo"
										required="required"
										onchange="createJsonObject(this.options[this.selectedIndex].value)">
										<option value="" disabled selected>Please Select
											Project Manager</option>
											<c:forEach var="emp1" items="${sessionScope.employee.employeeBeans}">
											<c:if test="${emp1.status eq 'A' }">
												<option
													value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}">${emp1.employeeName}</option>
											</c:if>
											</c:forEach>
										
									</select>
									<div class="x_panel">
										<table id="_${projectManagers.userId}" class="table table-hover" style=" white-space: nowrap;">
											<thead>
												<tr>
													<th>Project Name</th>
													<th>Current Head Count</th>
													<th>Cisco Manager Name</th>
													<th style="text-align: right;">Select Track &nbsp;
													<input type="checkbox"
														id="selectall" name="${projectManagers.userId}" />
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="asset" items="${projectManagers.assetBeans}">
													<tr>
														<td><c:out value="${asset.projectName}" /></td>
														<td class="align-center"><c:out
																value="${asset.currentHeadCount}" /></td>
														<td><c:out value="${asset.ciscoManagerName}" /></td>

														<td style="text-align: right;"><input type="checkbox"
															class="${projectManagers.userId}delegate" id="delegate"
															name="delegate" value="${asset.projectId}"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										</div>
									
								</form>
								
							</c:if>
							<c:if test="${empty projectManagers.assetBeans}">No Project Tracks Yet</c:if>
						</div>
						
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<div id="integrated" style="display: none;">
	<h4 style="color: #336699">Delegate Project Tracks</h4>
	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab"
				id="${projectManagers.employeeName}">
				<h4 class="panel-title" style="color: #336699">
					<a role="button" data-toggle="collapse" data-parent="#accordion"
						href="#integratedView" aria-expanded="true"
						aria-controls="integrated"> Project View </a>
				</h4>
			</div>
			<div id="integratedView" class="panel-collapse collapse in"
				role="tabpanel" aria-labelledby="integrated">
				<div class="panel-body">
					<form action="delegateAssets.html" id="delegeateAssets"
						method="post">
						<input type="submit" class="btn btn-primary" value="Delegate" style="margin-left: 88%">
						<label for="delegateTo">Delegate To</label> <select
							class="form-control" id="delegateTo" name="delegateTo"
							required="required"
							onchange="createJsonObject(this.options[this.selectedIndex].value)">
							<option value="" disabled selected>Please Select Project
								Manager</option>
							<c:forEach var="emp1" items="${sessionScope.employee.employeeBeans}">
								<c:if test="${emp1.status eq 'A' }">
								<option
									value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}">${emp1.employeeName}</option>
								</c:if>
							</c:forEach>
						</select>
						<div class="x_panel">
							<table id="assets" class="table table-hover" style="white-space: nowrap;">
								<thead>
									<tr>
										<th>Project Name</th>
										<th>Current Head Count</th>
										<th>Cisco Manager Name</th>
										<th>Project Manager</th>
										<th style="text-align: right;">Select Track &nbsp;<input type="checkbox"
											id="selectallintegrated" />
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="projectManagers"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="asset" items="${projectManagers.assetBeans}">
											<tr data-toggle="modal" data-id="${asset.projectId}"
												onclick="return assetDetails(${asset.projectId})"
												data-target="#assetDetails">
												<td><c:out value="${asset.projectName}" /></td>
												<td class="align-center"><c:out
														value="${asset.currentHeadCount}" /></td>
												<td><c:out value="${asset.ciscoManagerName}" /></td>
												<td><c:out value="${asset.projectManager}" /></td>
												<td style="text-align: right;"><input type="checkbox"
													class="delegateintegrated" id="delegate" name="delegate"
													value="${asset.projectId}"></td>
											</tr>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>