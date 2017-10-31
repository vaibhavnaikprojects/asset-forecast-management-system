<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="x_panel">
	<c:if test="${not empty sessionScope.employee.assetBeans}">
		<form action="savetrack.html" id="savetrack" name="savetrack" method="post">
			
				<table id="assets">
					<thead>
						<tr class="grid">
							<th>Project Name</th>
							<th>Current Head Count</th>
							<th>Growth/Decline</th>
							<th>Quarter (${sessionScope.quarter})</th>
							<th>Cisco Manager Name</th>
							<th>Project Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="asset" items="${sessionScope.employee.assetBeans}">
							<tr class="grid">
								<td><c:out value="${asset.projectName}" /></td>
								<c:if test="${asset.quarter ne sessionScope.quarter}">
								<td><c:out value="${asset.currentHeadCount + asset.growthCount}" /></td>
								</c:if>
								<c:if test="${asset.quarter eq sessionScope.quarter}">
								<td><c:out value="${asset.currentHeadCount}" /></td>
								</c:if>
								<c:if test="${sessionScope.assetValidation ne 'validated'}">
									<c:if test="${asset.quarter ne sessionScope.quarter}">
										<td class="align-center"><input type="hidden"
										name="projectId" name="projectId" value="${asset.projectId}">
									<input id="currentHeadCount"
										name="currentHeadCount" type="hidden" value="${asset.currentHeadCount + asset.growthCount}" required="required"
										value="${asset.growthCount}" />
									<input id="growthCount"
										name="growthCount" type="number" max="500" min="-${asset.currentHeadCount + asset.growthCount}" required="required"
										value="0" />
									</td>
									</c:if>
									<c:if test="${asset.quarter eq sessionScope.quarter}">
									<td class="align-center"><input type="hidden"
										name="projectId" name="projectId" value="${asset.projectId}">
									<input id="currentHeadCount"
										name="currentHeadCount" type="hidden" value="${asset.currentHeadCount}" required="required"
										value="${asset.growthCount}" />
									<input id="growthCount"
										name="growthCount" type="number" max="500" min="-${asset.currentHeadCount}" required="required"
										value="${asset.growthCount}" />
									</td>
									</c:if>
								</c:if>
								<c:if test="${sessionScope.assetValidation eq 'validated'}">
									<td class="align-center"><c:out
											value="${asset.growthCount}" /></td>
								</c:if>
								<td><c:if test="${asset.quarter eq sessionScope.quarter}">
										<c:out value="${asset.quarter}" />
									</c:if>
									<c:if test="${asset.quarter ne sessionScope.quarter}"><b><c:out value="Data not set"/></b> 
									<input type="hidden" value="DATA NOT SET"
											id="quarter" name="quarter" readonly="readonly">
									</c:if></td>
								<td><c:out value="${asset.ciscoManagerName}" /></td>
								<td><c:out value="${asset.projectLocation}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
			<br/><br/>
			<div>
			<c:if test="${sessionScope.assetValidation eq 'validated'}">
				<i class="fa fa-check fa-lg"  style="color: #1ABB9C"></i><b style=" margin-top: 3%">Project tracks validated for the quarter</b>
			</c:if>
			<%
   			Date now = new Date();
   			DateFormat df  =new SimpleDateFormat("dd/MM/yyyy");
  			String date= String.valueOf(df.format(now));
	   		%>
				<c:if test="${sessionScope.assetValidation ne 'validated'}">
					<input type="submit" class="btn btn-primary"  name="submitSave" data-toggle="tooltip" data-placement="bottom"
					title="Once Submit , You will not be able to change Project Track " value="Submit quarterly updates" onclick="return warningSubmit()" class="btn btn-primary"
					/>
					
				
					<input type="Submit" class="btn btn-primary"  name="submitSave" value="Save">
				</c:if>
			</div>	
			</form>
			<button id="exportCSVPopUp" class="btn btn-primary" data-toggle="modal" data-target="#exportCSV">Export</button>	
	</c:if>
</div>
