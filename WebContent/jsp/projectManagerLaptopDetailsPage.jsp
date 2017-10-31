<%@page import="com.zensar.afnls.beans.EmployeeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<table id="laptopRequests" class="table table-hover">
		<thead>
			<tr>
				<th>Request Id</th>
				<th>Request Type</th>
				<th>Associate Name</th>
				<th>Request Date</th>
				<th>Project Name</th>
				<th>Reason</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty requestScope.laptopDetails}">
				<c:forEach var="laptop" items="${requestScope.laptopDetails}">
					<tr class="grid">
						<td><c:out value="${laptop.uniquelaptopid}" /></td>
						<td><c:out value="${laptop.requestType}" /></td>
						<td><c:out value="${laptop.ownerName}" /></td>
						<td><c:out value="${laptop.createdDate}" /></td>
						<td><c:out value="${laptop.projectName}" /></td>
						<td><c:out value="${laptop.reason}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
