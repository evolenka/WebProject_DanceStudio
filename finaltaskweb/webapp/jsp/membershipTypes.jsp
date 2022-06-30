<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" var="rb" />
<html lang="${language}">
<head>
<title>Dance studio</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:url
	value="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
	var="bootstrap"/>
<link rel="stylesheet" href="<c:out value="${bootstrap}"/>">
<c:url value="/css/style.css" var="css" />
<link rel="stylesheet" href="<c:out value="${css}"/>">
<c:url value="img/favicon.ico" var="icon" />
<link rel="icon" href="<c:out value="${icon}"/>">
<c:url value="index.jsp" var="main" />
</head>
<body>
	<div class="wrapper">
		<u:mainmenu/>
		<div class="content conteiner-fluid">
			<h1 class="subtitle">
				<fmt:message key="membershipTypes.title" bundle="${ rb }" />
			</h1>
			<form method="post" action="action">
				<table class="table mx-auto">
					<tr>
						<th></th>
						<th><fmt:message key="membership.title" bundle="${ rb }" /></th>
						<th><fmt:message key="membership.quantity" bundle="${ rb }" /></th>
						<th><fmt:message key="membership.price" bundle="${ rb }" /></th>
						<th></th>
					</tr>
					<c:forEach var="membershipType" items="${membershipTypes}">
						<tr>
							<td class="pt-3"><input type="radio" id="membershipTypeId"
								name="membershipTypeId" value="${membershipType.id}" required /></td>
							<td><c:out value="${membershipType.title}"/></td>
							<c:choose>
								<c:when test="${membershipType.maxClassQuantity !=  0 }">
									<td><c:out value="${membershipType.maxClassQuantity}"/></td>
								</c:when>
								<c:otherwise>
									<td>-</td>
								</c:otherwise>
							</c:choose>
							<td><c:out value="${membershipType.price}"/></td>
							<td>
								<button type="submit" class="btn colorBtn" name="command"
									value="CHOOSEMEMBERSHIP">
									<fmt:message key="purchase" bundle="${ rb }" />
								</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</form>
			</div>
			<u:footer />
		</div>
</body>
</html>