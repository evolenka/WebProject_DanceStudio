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
						<fmt:message key="clientMain.mymemberships" bundle="${ rb }" />
					</h1>
					<div class="col-lg-4">
						<form method="post" action="action" class="needs-validation">
							<c:set var="currentdate">
								<ctg:currentdate />
							</c:set>
							<c:set var="maxdate">
								<ctg:maxdate/>
							</c:set>
							<input class="form-check-input" type="radio" id="period"
								name="command" value="MYMEMBERSHIPSPERIOD"
								${not empty isCheckedPeriod ? 'checked' : ''} required>
							<fmt:message key="membership.forPeriod" bundle="${ rb }" />
							<br> <label for="startdate"><fmt:message
									key="from" bundle="${ rb }" /></label> <input
								type="date" id="startdate" name="startdate"
								value=${not empty startdate ? startdate : currentdate}>
							<br> <label for="enddate"><fmt:message key="till"
									bundle="${ rb }" /></label> <input type="date" id="enddate"
								name="enddate"
								value=${not empty enddate ? enddate : maxdate}><br>
								<input class="form-check-input" type="radio" id="valid"
								name="command" value="MYVALIDMEMBERSHIPS"
								${not empty isCheckedValid ? 'checked' : ''} required>
							<fmt:message key="myValidMemberships" bundle="${ rb }" /> <br>
								<input type=hidden name = page value = myMemberships>
							<input class="btn colorBtn" type="submit"
								value="<fmt:message key="choose" bundle="${ rb }"/>">
						</form>
					</div>
					<div class="col-lg-10">
						<table class="table mx-auto">
							<tr>
								<th><fmt:message key="membership.title" bundle="${ rb }" /></th>
								<th><fmt:message key="membership.balanceClassQuantity"
										bundle="${ rb }" /></th>
								<th><fmt:message key="membership.startdate"
										bundle="${ rb }" /></th>
								<th><fmt:message key="membership.enddate" bundle="${ rb }" /></th>
							</tr>
							<c:forEach var="membership" items="${memberships}">
								<tr>
									<td><c:out value="${membership.type.title}" /></td>
									<c:choose>
										<c:when test="${membership.type.title == 'UNLIM'}">
											<td>-</td>
										</c:when>
										<c:otherwise>
											<td><c:out
													value="${membership.balanceClassQuantity}" /></td>
										</c:otherwise>
									</c:choose>
									<td><c:out value="${membership.startDate}" /></td>
									<td><c:out value="${membership.endDate}" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>