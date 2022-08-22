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
<c:url value="index.jsp" var="main" />
</head>
<body>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<h1 class="subtitle">
				<fmt:message key="registrationForm" bundle="${ rb }" />
			</h1>
			<div class="row">
				<div class="col-sm-2 col-lg-4">
				<c:choose>
					<c:when test="${role == 'CLIENT'}">
						<u:clientmenu />
					</c:when>
					<c:when test="${role == 'ADMIN'}">
						<u:adminmenu />
					</c:when>
				</c:choose>
			</div>
			<div class="col-sm-8 col-lg-4">
				<form method="post" action="action" class="needs-validation">
					<label for="login"><fmt:message key="registration.login"
							bundle="${ rb }" /></label><br> <input type="text"
						class="form-control" id="login" name="login" required>
					<p class=error>
						<c:out value="${errorLoginMessage}" />
					</p>
					<label for="password"><fmt:message
							key="registration.password" bundle="${ rb }" /></label><br> <input
						type="password" class="form-control" id="password" name="password"
						required> <label for="confirmPassword"><fmt:message
							key="registration.confirmPassword" bundle="${ rb }" /></label><br>
					<input type="password" class="form-control" id="confirmPassword"
						name="confirmPassword" required>
					<p class=error>
						<c:out value="${errorPassMatchMessage}" />
					</p>
					<label for="surname"><fmt:message
							key="registration.surname" bundle="${ rb }" /></label><br> <input
						type="text" class="form-control" id="surname" name="surname"
						required> <label for="name"><fmt:message
							key="registration.name" bundle="${ rb }" /></label><br> <input
						type="text" class="form-control" id="name" name="name" required>
					<label for="patronymic"><fmt:message
							key="registration.patronymic" bundle="${ rb }" /></label><br> <input
						type="text" class="form-control" id="patronymic" name="patronymic">
					<label for="email"><fmt:message key="registration.email"
							bundle="${ rb }" /></label><br> <input type="email"
						class="form-control" id="email" name="email" required> <label
						for="phone"><fmt:message key="registration.phone"
							bundle="${ rb }" /></label><br> <input type="text"
						class="form-control" id="phone" name="phone"> <br>
					<button type="submit" class="btn btn-secondary pl-3" name="command"
						value="REGISTRATION">
						<fmt:message key="register" bundle="${ rb }" />
					</button>
					<a class="btn btn-light text-dark"
						href='<c:out value="${regLink}"/>'><fmt:message key="resert"
							bundle="${ rb }" /></a>
				</form>
				<p class=success>
					<c:out value="${successRegMessage}" />
				</p>
				<p class=error>
					<c:out value="${errorRegMessage }" />
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
