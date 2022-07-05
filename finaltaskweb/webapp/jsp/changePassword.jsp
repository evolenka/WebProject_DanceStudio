<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
					<c:choose>
						<c:when test="${role == 'CLIENT'}">
							<u:clientmenu />
						</c:when>
						<c:when test="${role == 'TEACHER'}">
							<u:teachermenu />
						</c:when>
						<c:when test="${role == 'ADMIN'}">
							<u:adminmenu />
						</c:when>
					</c:choose>
				</div>
				<div class="col-lg-10">
					<h1 class="subtitle" style="margin-bottom: 50px">
						<fmt:message key="changePasswordForm" bundle="${ rb }" />
					</h1>
					<form method="post" action="action" class="needs-validation">
						<label for="login"><fmt:message key="registration.login"
								bundle="${ rb }" /></label><br> <input type="text"
							class="form-control" id="login" name="login" required>
						<p class=error>
							<c:out value="${errorLoginMessage}" />
						</p>
						<label for="currentPassword"><fmt:message
								key="changePassword.currentPassword" bundle="${ rb }" /></label><br>
						<input type="password" class="form-control" id="currentPassword"
							name="currentPassword" required> <label for="newPassword"><fmt:message
								key="changePassword.newPassword" bundle="${ rb }" /></label><br> <input
							type="password" class="form-control" id="newPassword"
							name="newPassword" required> <label for="confirmPassword"><fmt:message
								key="registration.confirmPassword" bundle="${ rb }" /></label><br>
						<input type="password" class="form-control" id="confirmPassword"
							name="confirmPassword" required>
						<p class=error>
							<c:out value="${errorPassMatchMessage}" />
						</p>
						<br>
						<button type="submit" class="btn btn-secondary pl-3"
							name="command" value="UPDATEPASSWORD">
							<fmt:message key="change" bundle="${ rb }" />
						</button>
						<a class="btn btn-light text-dark"
							href='<c:out value="${regLink}"/>'><fmt:message key="resert"
								bundle="${ rb }" /></a>
					</form>
					<p class=error>
						<c:out value="${errorLoginOrPassMessage}" />
					</p>
					<p class=success>
						<c:out value="${successUpdatePassMessage}" />
					</p>
					<p class="text-small">
						<fmt:message key="comment" bundle="${ rb }" />
					</p>
				</div>
				<div class="col-sm-2 col-lg-4"></div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>