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
				<div class="col-lg-12">
					<h1 class="subtitle">
						<fmt:message key="openForEnrollment" bundle="${ rb }" />
					</h1>
				</div>
				<div class="col-lg-2">
					<u:adminmenu />
				</div>
				<div class="col-lg-10">
					<h5>
						<fmt:message key="visitsByTeacher.chooseGroupAndDate"
							bundle="${ rb }" />
					</h5>
					<form method="post" action="action">
						<c:set var="currentdate">
							<ctg:currentdate />
						</c:set>
						<input type="date" name="classDate"
							value=${not empty classDate ? classDate : currentdate} required>
						<br>
						<button type="submit" class="btn colorBtn" name="command"
							value="READGROUPSBYDATE">
							<fmt:message key="choose" bundle="${ rb }" />
						</button>
					</form>
					<form method="post" action="action">
						<c:if test="${not empty groupsBySchedule}">
							<label for="group"> <fmt:message key="groups"
									bundle="${ rb }" /></label>
							<br>
							<c:forEach var="group" items="${groupsBySchedule}">
								<input type="checkbox" id="group" name="groupId"
									value="${group.id}">
								<c:out value = "${group.title}"/>
								<br>
							</c:forEach>
							<button type="submit" class="btn colorBtn" name="command"
								value="CREATECLASSES">
								<fmt:message key="confirm" bundle="${ rb }" />
							</button>
						</c:if>
					</form>
			</div>
		</div>
	</div>
	<u:footer />
	</div>
</body>
</html>