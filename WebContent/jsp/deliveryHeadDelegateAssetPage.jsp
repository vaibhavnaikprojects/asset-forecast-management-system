<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
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
<div class="view-filter ">
	<span style="color: #336699;">View Filter</span> <select
		name="viewFilter" id="viewFilter" onchange="viewchange()">
		<option value="fork">PGM View</option>
		<option value="forkjoin">PM View</option>
		<option value="join">Project View</option>
	</select>
</div>
<div id="PGMView">
	<h4 style="color: #336699">Delegate Project Tracks Under Program
		manager</h4>
	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<c:forEach var="employee"
			items="${sessionScope.employee.employeeBeans}">
			<c:if test="${employee.status eq 'A'}">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title" style="color: #336699">
							<a role="button" data-toggle="collapse" data-parent="#accordion"
								href="#${employee.userId}" aria-expanded="true"
								aria-controls="collapseOne" onclick="return test('${employee.userId}')"> ${employee.employeeName} </a>
						</h4>
					</div>
					<div id="${employee.userId}" class="panel-collapse collapse"
						role="tabpanel" aria-labelledby="headingOne">
						<div class="panel-body">
							<form action="delegateAssets.html" id="delegeateAssets"
								method="post">
								<input type="submit" class="btn btn-primary" value="Delegate" style="margin-left: 88%">
								<label for="delegateTo">Delegate To</label> <select
									class="form-control" id="delegateTo" name="delegateTo"
									onchange="createJsonObject(this.options[this.selectedIndex].value)"
									required="required">
									<option value="" disabled selected>Please Select
										Project Manager</option>
									<c:forEach var="emp"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="emp1" items="${emp.employeeBeans}">
										<c:if test="${emp1.status eq 'A' }">
											<option
												value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}">${emp1.employeeName}</option>
										</c:if>
										</c:forEach>
									</c:forEach>
								</select>
								<div class="x_panel">
									<table id="_${employee.userId}" class="table table-hover" style="white-space: nowrap;">
										<thead>
											<tr>
												<th>Track Name</th>
												<th>Current Head Count</th>
												<th>Cisco Manager Name</th>
												<th>Project Manager</th>
												<th style="text-align: right;">Select Track &nbsp;<input
													type="checkbox" id="selectall" name="${employee.userId}" />
												</th>

											</tr>
										</thead>
										<c:forEach var="projectManager"
											items="${employee.employeeBeans}">
											<c:forEach var="asset" items="${projectManager.assetBeans}">
												<tr>
													<td><c:out value="${asset.projectName}" /></td>
													<td><c:out value="${asset.currentHeadCount}" /></td>
													<td><c:out value="${asset.ciscoManagerName}" /></td>
													<td><c:out value="${projectManager.employeeName}" /></td>
													<td style="text-align: right;"><input type="checkbox"
														class="${employee.userId}delegate" id="delegate"
														name="delegate" value="${asset.projectId}" style="text-align: right;"></td>

												</tr>
											</c:forEach>
										</c:forEach>
									</table>
									</div>
							</form>
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>

</div>
<div id="PMView" style="display: none;">
	<h4 style="color: #336699">Delegate Project Tracks Under Project
		Manager</h4>
	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<c:forEach var="programManagers"
			items="${sessionScope.employee.employeeBeans}">
			<c:forEach var="projectManagers"
				items="${programManagers.employeeBeans}">
				<c:if test="${projectManagers.status eq 'A'}">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab"
							id="${projectManagers.employeeName}">
							<h4 class="panel-title" style="color: #336699">
								<a role="button" data-toggle="collapse"
									data-parent="#accordion2"
									href="#${fn:replace(projectManagers.employeeName,' ', '')}"
									aria-expanded="true"
									aria-controls="${projectManagers.employeeName}" onclick="return test1('${projectManagers.userId}')">
									${projectManagers.employeeName} </a>
							</h4>
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
											<c:forEach var="emp"
												items="${sessionScope.employee.employeeBeans}">
												<c:forEach var="emp1" items="${emp.employeeBeans}">
												<c:if test="${emp1.status eq 'A' }">
													<option
														value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}">${emp1.employeeName}</option>
												</c:if>
												</c:forEach>
											</c:forEach>
										</select>
										<div class="x_panel">
											<table id="123_${projectManagers.userId}" class="table table-hover" style="white-space: nowrap;">
												<thead>
													<tr>
														<th>Project Name</th>
														<th>Current Head Count</th>
														<th>Cisco Manager Name</th>
														<th style="text-align: right;">Select Track &nbsp;
														<input type="checkbox"
															id="selectall" name="${projectManagers.userId}" style="text-align: right;"/>
														</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="asset"
														items="${projectManagers.assetBeans}">
														<tr>
															<td><c:out value="${asset.projectName}" /></td>
															<td class="align-center"><c:out
																	value="${asset.currentHeadCount}" /></td>
															<td><c:out value="${asset.ciscoManagerName}" /></td>

															<td style="text-align: right;"><input
																type="checkbox"
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
					<a role="button" data-toggle="collapse" data-parent="#accordion2"
						href="#integratedView" aria-expanded="true"
						aria-controls="integrated"> Integrated View </a>
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
							<c:forEach var="emp"
								items="${sessionScope.employee.employeeBeans}">
								<c:forEach var="emp1" items="${emp.employeeBeans}">
								<c:if test="${emp1.status eq 'A' }">
									<option
										value="${emp1.userId}/${emp1.employeeName}/${emp1.managerId}">${emp1.employeeName}</option>
								</c:if>
								</c:forEach>
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
									<c:forEach var="programManagers"
										items="${sessionScope.employee.employeeBeans}">
										<c:forEach var="projectManagers"
											items="${programManagers.employeeBeans}">
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