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
<c:url value="danceClasses.jsp" var="danceClasses" />
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<c:url value="groupRegistration.jsp" var="groupRegistration" />


</head>
<body>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<div class=row>
				<div class="col-lg-12">
					<h1 class="subtitle">
						<fmt:message key="enrolledClients" bundle="${ rb }" />
					</h1>
					<h3 class="subtitle">
						<fmt:message key="toDanceClass" bundle="${ rb }" />
						<c:out value="${classDate}"/>
						,
						<c:out value="${time}" />
						,
						<c:out value="${group}" />:
					</h3>
				</div>
				<div class="col-lg-2">
					<u:adminmenu />
				</div>
				<div class="col-lg-10">
					<c:if test="${not empty clients}">
						<table>
							<tr>
								<th>N</th>
								<th id=group class="rowgroup"><fmt:message key="name"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="surname"
										bundle="${ rb }" /></th>
								<th></th>
								<th></th>
							</tr>
							<c:forEach var="client" items="${clients}" varStatus="index">
								<tr>
									<td><c:out value="${ index.count }" /></td>

									<td><c:out value="${client.surname}" /></td>
									<td><c:out value="${client.name}" /></td>
									<td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${empty clients}">
					<p>
						<c:out value="${noClients}" />
					</p>
					</c:if>
					<form method="post" action="action">
						<a class="btn  colorBtn" href='<c:out value="${danceClasses}"/>'>
							<fmt:message key="back" bundle="${ rb }" />
						</a>
						<button type="submit" class="btn colorBtn" name="command"
							value="CLOSEENROLLMENT"
							onclick="return confirm('Are you sure?/Вы уверены?')">
							<fmt:message key="closeForEnrollment" bundle="${ rb }" />
						</button>
					</form>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>