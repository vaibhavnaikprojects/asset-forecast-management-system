<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
function viewchange(){
	var selectedView=document.getElementById("viewFilter").value;
	if(selectedView=="fork"){
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="block";
		document.getElementById("integrated").style.display="none";
		document.getElementById("DHView").style.display="none";
	}else if(selectedView=="join"){
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("integrated").style.display="block";
		document.getElementById("DHView").style.display="none";
	}else if(selectedView=="forkjoin"){
		document.getElementById("integrated").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("PMView").style.display="block";
		document.getElementById("DHView").style.display="none";
	}else{
		document.getElementById("PMView").style.display="none";
		document.getElementById("PGMView").style.display="none";
		document.getElementById("integrated").style.display="none";
		document.getElementById("DHView").style.display="block";
	}
}
</script>
<div class="view-filter">
	<span style="color: #336699;">View Filter</span> <select
		name="viewFilter" id="viewFilter" onchange="viewchange()">
		<option value="dh">DH View</option>
		<option value="fork">PGM View</option>
		<option value="forkjoin">PM View</option>
		<option value="join">Integrated View</option>
	</select>
</div>
<c:set var="odcHead"/>
<c:forEach var="employee" items="${sessionScope.employee.employeeBeans}">
<c:if test="${employee.designation eq 'Cisco ODC Head'}">
	<c:set var="odcHead" value="${employee}"/>
</c:if>
</c:forEach>
<div id="DHView">
	<h4 style="color: #336699">Delete Project Tracks Under Delivery
		Head</h4>

	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<c:forEach var="deliveryHead"
			items="${odcHead.employeeBeans}">
			<c:if test="${deliveryHead.status eq 'A'}">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title" style="color: #336699">
							<a role="button" data-toggle="collapse" data-parent="#accordion"
								href="#${deliveryHead.userId}" aria-expanded="true"
								aria-controls="collapseOne" onclick="return test('${deliveryHead.userId}')"> ${deliveryHead.employeeName}  </a>
						</h4>
					</div>
					<div id="${deliveryHead.userId}" class="panel-collapse collapse"
						role="tabpanel" aria-labelledby="headingOne">
						<div class="panel-body">
							<form action="deleteAssets.html" id="delegeateAssets"
								method="post">
								<div class="x_panel">
									<table id="_${deliveryHead.userId}" class="table table-hover" style="white-space: nowrap;">
										<thead>
											<tr>
												<th>To Delete &nbsp;&nbsp;&nbsp;&nbsp;<input
													type="checkbox" id="selectall"
													name="${deliveryHead.userId}" /></th>
												<th>Track Name</th>
												<th>Current Head Count</th>
												<th>Cisco Manager Name</th>
												<th>Project Manager</th>
												
											</tr>
										</thead>
										<c:forEach var="programManager"
											items="${deliveryHead.employeeBeans}">
											<c:forEach var="projectManager"
												items="${programManager.employeeBeans}">
												<c:forEach var="asset" items="${projectManager.assetBeans}">
													<tr>
														<td><input type="checkbox"
															class="${deliveryHead.userId}delegate" id="delete"
															name="delete" value="${asset.projectId}"></td>
														<td><c:out value="${asset.projectName}" /></td>
														<td><c:out value="${asset.currentHeadCount}" /></td>
														<td><c:out value="${asset.ciscoManagerName}" /></td>
														<td><c:out value="${projectManager.employeeName}" /></td>
													</tr>
												</c:forEach>
											</c:forEach>
										</c:forEach>
									</table>
									</div>
								<input type="submit" class="btn btn-primary" value="Delete Track">
							</form>
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<div id="PGMView" style="display: none;">
	<h4 style="color: #336699">Delete Project Tracks Under Program
		manager</h4>

	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<c:forEach var="deliveryHead"
			items="${odcHead.employeeBeans}">
			<c:forEach var="employee" items="${deliveryHead.employeeBeans}">
				<c:if test="${employee.status eq 'A'}">
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="headingOne">
							<h4 class="panel-title" style="color: #336699">
								<a role="button" data-toggle="collapse" data-parent="#accordion"
									href="#${employee.userId}" aria-expanded="true"
									aria-controls="collapseOne" onclick="return test1('${employee.userId}')"> ${employee.employeeName} </a>
							</h4>
						</div>
						<div id="${employee.userId}" class="panel-collapse collapse"
							role="tabpanel" aria-labelledby="headingOne">
							<div class="panel-body">
								<form action="deleteAssets.html" id="delegeateAssets"
									method="post">
								<div class="x_panel">
									<table id="123_${employee.userId}" class="table table-hover" style="white-space: nowrap;">
										<thead>
											<tr>
												<th>Track Name</th>
												<th>Current Head Count</th>
												<th>Cisco Manager Name</th>
												<th>Project Manager</th>
												<th style="text-align: right;">To delete &nbsp;&nbsp;&nbsp;<input type="checkbox"
													id="selectall" name="${employee.userId}" />
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
														class="${employee.userId}delegate" id="delete"
														name="delete" value="${asset.projectId}"></td>

												</tr>
											</c:forEach>
										</c:forEach>
									</table>
									</div>
									<input type="submit" class="btn btn-primary" value="Delete Track">
								</form>
							</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</c:forEach>
	</div>
</div>
<div id="PMView" style="display: none;">
	<h4 style="color: #336699">Delete Project Tracks Under Project
		Manager</h4>
	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<c:forEach var="deliveryHead"
			items="${odcHead.employeeBeans}">
			<c:forEach var="programManagers"
				items="${deliveryHead.employeeBeans}">
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
										aria-controls="${projectManagers.employeeName}" onclick="return test2('${projectManagers.userId}')">
										${projectManagers.employeeName} </a>
								</h4>
							</div>
							<div id="${fn:replace(projectManagers.employeeName,' ', '')}"
								class="panel-collapse collapse" role="tabpanel"
								aria-labelledby="${projectManagers.employeeName}">
								<div class="panel-body">
									<c:if test="${not empty projectManagers.assetBeans}">
										<form action="deleteAssets.html" id="delegeateAssets"
											method="post">
											<div class="x_panel">
												<table id="456_${projectManagers.userId}" class="table table-hover" style="white-space: nowrap;">
													<thead>
														<tr>
															<th>Project Name</th>
															<th>Current Head Count</th>
															<th>Cisco Manager Name</th>
															<th style="text-align: right;">To Delete (Please
																check CheckBox)&nbsp;&nbsp;<input type="checkbox"
																id="selectall" name="${projectManagers.userId}" />
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
																	class="${projectManagers.userId}delegate" id="delete"
																	name="delete" value="${asset.projectId}"></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												</div>
											<input type="submit" class="btn btn-primary" value="Delete Track">
										</form>
									</c:if>
									<c:if test="${empty projectManagers.assetBeans}">No Project Tracks Yet</c:if>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</div>
</div>
<div id="integrated" style="display: none;">
	<h4 style="color: #336699">Delete Project Tracks</h4>
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
					<form action="deleteAssets.html" id="delegeateAssets"
						method="post">
						<div class="x_panel">
							<table id="assets" class="table table-hover" style="white-space: nowrap;">
								<thead>
									<tr>
										<th>Project Name</th>
										<th>Current Head Count</th>
										<th>Cisco Manager Name</th>
										<th>Project Manager</th>
										<th style="text-align: right;">To Delete &nbsp;&nbsp;&nbsp;<input type="checkbox"
											id="selectallintegrated" />
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="deliveryHead"
										items="${odcHead.employeeBeans}">
										<c:forEach var="programManagers"
											items="${deliveryHead.employeeBeans}">
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
															class="delegateintegrated" id="delete" name="delete"
															value="${asset.projectId}"></td>
													</tr>
												</c:forEach>
											</c:forEach>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
							</div>
						<input type="submit" class="btn btn-primary" value="Delete Track">
					</form>
				</div>
			</div>
		</div>
	</div>
</div>