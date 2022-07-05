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
<c:url value="groupRegistration.jsp" var="regLink" />
</head>
<body>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<div class=row>
				<div class="col-lg-2">
					<u:adminmenu />
				</div>
				<div class="col-lg-10">
					<h1 class="subtitle">
						<fmt:message key="registrationForm" bundle="${ rb }" />
					</h1>
					<div class="row">
						<div class="col-sm-2 col-lg-4"></div>
						<div class="col-sm-8 col-lg-4">
							<form method="post" action="action" class="needs-validation">
								<label for="group"><fmt:message key="group.title"
										bundle="${ rb }" /></label><br> <input type="text"
									class="form-control" id="group" name="group" required>
								<p class=error>
									<c:out value="${errorGroupTitleMessage}" />
								</p>
								<label for="teacher"> <fmt:message key="teacher"
										bundle="${ rb }" /></label><br> <select id="teacher"
									name="teacher">
									<c:forEach var="teacher" items="${teachers}">
										<option value="${teacher.surname}">${teacher.surname}</option>
									</c:forEach>
								</select> <br> <br> <label for="level"> <fmt:message
										key="level" bundle="${ rb }" /></label><br>
								<c:forEach var="level" items="${levels}">
									<input type="radio" id="level" name="level" value="${level}"
										required>
									<c:out value="${level}" />
									<br>
								</c:forEach>
								<br> <label for="weekday"> <fmt:message
										key="weekday" bundle="${ rb }" /></label><br>
								<c:forEach var="weekday" items="${weekdays}">
									<input type="checkbox" id="weekday" name="weekday"
										value="${weekday}">
									<fmt:message key="${weekday}" bundle="${ rb }" />
									<br>
								</c:forEach>
								<br> <label for="time"><fmt:message key="time"
										bundle="${ rb }" /></label><br> <input type="time"
									class="form-control" id="time" name="time" required> <label
									for="duration"><fmt:message key="duration"
										bundle="${ rb }" /></label><br> <input type="text"
									class="form-control" id="duration" name="duration" required>
								<br>
								<button type="submit" class="btn btn-secondary pl-3"
									name="command" value="CREATEGROUP">
									<fmt:message key="register" bundle="${ rb }" />
								</button>
								<a class="btn btn-light text-dark"
									href='<c:out value="${regLink}"/>'><fmt:message
										key="resert" bundle="${ rb }" /></a>
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
				</div>F
			</div>
			</div>
			<u:footer />
		</div>
</body>
</html>
