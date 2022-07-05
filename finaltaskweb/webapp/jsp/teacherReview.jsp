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
<c:url value="/img/teacher1.jpg" var="teacher1" />
<c:url value="/img/teacher2.jpg" var="teacher2" />
<c:url value="/img/teacher3.jpg" var="teacher3" />
<c:url value="/img/teacher4.jpg" var="teacher4" />
</head>
<body>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<h1 class="subtitle">
				<fmt:message key="teacher.title" bundle="${ rb }" />
			</h1>
			<div class="row">
				<div class="col-lg-4">
					<table>
						<tr>
							<td><img class="float-right"
								src="<c:out value="${ teacher1 }"/>" alt="teacher1" width="250"
								height="250"> <c:out value="${visits.danceclass.date}" /></td>
						</tr>
						<tr>
							<td><img class="float-right"
								src="<c:out value="${ teacher2 }"/>" alt="teacher2" width="250"
								height="250"></td>
						</tr>
						<tr>
							<td><img class="float-right"
								src="<c:out value="${ teacher3 }"/>" alt="teacher3" width="250"
								height="250"></td>
						</tr>
						<tr>
							<td><img class="float-right"
								src="<c:out value="${ teacher4 }"/>" alt="teacher4" width="250"
								height="250"></td>
						</tr>
					</table>
				</div>
				<div class="col-lg-6">
					<table>
						<c:forEach var="teacher" items="${teachers}">
							<tr>
								<td>
									<div class="card">
										<div class="card-header">
											<c:out value="${teacher.surname}" />
											<c:out value="${teacher.name}" />
										</div>
										<div class="card-body">
											<h3>
												<c:out value="${teacher.danceStyle}" />
											</h3>
											<p>
												<c:out value="${teacher.portfolio}" />
											</p>
										</div>
									</div>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>