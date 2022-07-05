<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" var="rb" />
<html lang="${language}">
<head>
<title>Dance studio</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:url
	value="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
	var="bootstrap" />
<link rel="stylesheet" href="<c:out value="${bootstrap}"/>">
<c:url value="/css/style.css" var="css" />
<link rel="stylesheet" href="<c:out value="${css}"/>">
<c:url value="img/favicon.ico" var="icon" />
<link rel="icon" href="<c:out value="${icon}"/>">
<c:url value="enrollmentSecondStep.jsp" var="enrollmentSecondStep.jsp" />
</head>
<body>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<div class=row>
				<div class="col-lg-2">
					<u:clientmenu />
				</div>
				<div class="col-lg-10">
					<h1 class="subtitle">
						<fmt:message key="clientMain.enrollment" bundle="${ rb }" />
					</h1>
					<h5>
						<fmt:message key="enrollment.thirdStep" bundle="${ rb }" />
					</h5>
					<c:if test="${not empty memberships}">
						<form method="post" action="action">
							<table class="table mx-auto">
								<tr>
									<th></th>
									<th><fmt:message key="membership.title" bundle="${ rb }" /></th>
									<th><fmt:message key="membership.balanceClassQuantity"
											bundle="${ rb }" /></th>
									<th><fmt:message key="membership.startdate"
											bundle="${ rb }" /></th>
									<th><fmt:message key="membership.enddate" bundle="${ rb }" /></th>
								</tr>
								<c:forEach var="membership" items="${memberships}">
									<tr>
										<td><input type="radio" id="membershipId"
											name="membershipId" value="${membership.id}" required /></td>
										<td><c:out value="${membership.type.title}" /></td>
										<c:choose>
											<c:when test="${membership.type.title == 'UNLIM'}">
												<td>-</td>
											</c:when>
											<c:otherwise>
												<td><c:out value="${membership.balanceClassQuantity}" /></td>
											</c:otherwise>
										</c:choose>
										<td><c:out value="${membership.startDate}" /></td>
										<td><c:out value="${membership.endDate}" /></td>
									</tr>
								</c:forEach>
							</table>
							<a class="btn  colorBtn" href='<c:out value="${enrollment2}"/>'>
								<fmt:message key="back" bundle="${ rb }" />
							</a>
							<button type="submit" class="btn colorBtn" name="command"
								value="CONFIRMVISIT">
								<fmt:message key="next" bundle="${ rb }" />
							</button>
						</form>
					</c:if>
					<c:if test="${empty memberships}">
						<form method="post" action="action">
							<p>
								<fmt:message key="enrollment.noMembership" bundle="${ rb }" />

								<button type="submit" class="btn clientMenuBtn btn-light"
									name="command" value="READALLMEMBERSHIPTYPES">
									<fmt:message key="here" bundle="${ rb }" />
								</button>

							</p>
						</form>
					</c:if>
					<p>
						<c:out value="${noMemberships}" />
					</p>
				</div>
			</div>
		</div>
	</div>
	<u:footer />
</body>
</html>