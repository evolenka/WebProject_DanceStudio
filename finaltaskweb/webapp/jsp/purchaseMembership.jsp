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
				<div class="col-lg-6">
					<h1 class="subtitle">
						<fmt:message key="purchaseMembership.purchase" bundle="${ rb }" />
					</h1>
					<p>
						<fmt:message key="purchaseMembership.choice" bundle="${ rb }" />
					</p>
					<form method="post" action="action">
						<input type="hidden" id="membershipId" name="membershipTypeId"
							value="${membershipType.id}">
						<table class="table mx-auto">
							<tr>
								<th id=group class="rowgroup"><fmt:message
										key="membership.title" bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message
										key="membership.quantity" bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message
										key="membership.price" bundle="${ rb }" /></th>
							</tr>
							<tr>
								<td><c:out value="${membershipType.title}" /></td>
								<c:choose>
									<c:when test="${membershipType.maxClassQuantity !=  0 }">
										<td><c:out value="${membershipType.maxClassQuantity}" /></td>
									</c:when>
									<c:otherwise>
										<td>-</td>
									</c:otherwise>
								</c:choose>
								<td><fmt:formatNumber value="${membershipType.price}"
										type="currency" /></td>
							</tr>
						</table>
						<label for="membershipStartDate"><fmt:message
								key="purchaseMembership.startDateMessage" bundle="${ rb }"/></label><br>
						<input type="date" class="form-control" id="membershipStartDate"
							name="membershipStartDate" required>
						<button type="submit" class="btn colorBtn" name="command"
							value="CREATEMEMBERSHIP">
							<fmt:message key="purchase" bundle="${ rb }"/>
						</button>
					</form>
					<p>
						<c:out value="${successPurchaseMessage}"/>
						<c:out value="${errorPurchaseMessage}"/>
						<c:out value="${errorNoSession}"/>
					</p>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>