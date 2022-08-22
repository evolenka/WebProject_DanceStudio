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
						<fmt:message key="viewEnrolledClients" bundle="${ rb }" />
					</h1>
				</div>
				<div class="col-lg-2">
					<u:adminmenu />
				</div>  
				<div class="col-lg-10">
					<h5>
						<fmt:message key="chooseDate" bundle="${ rb }" />
					</h5>
					<c:set var="currentdate">
							<ctg:currentdate />
						</c:set>
					<form method="post" action="action">
						 
						<input type="date" name="classDate"
							value=${not empty classDate ? classDate : currentdate} required>
						<br>
						<button type="submit" class="btn colorBtn" name="command"
							value="READACTIVECLASSESBYDATE">
							<fmt:message key="choose" bundle="${ rb }" />
						</button>
					</form>

					<c:if test="${not empty danceClasses}">
					
						<table>
							<tr>
								<th></th>

								<th id=group class="rowgroup"><fmt:message key="time"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="group"
										bundle="${ rb }" /></th>
								<th></th>
								<th></th>
							</tr>
							<c:forEach var="danceClass" items="${danceClasses}">

								<tr>
									<td><input type="radio" required /></td>

									<td><input type=hidden id="time" name="time"
										value="${danceClass.schedule.time}" /> <c:out
											value="${danceClass.schedule.time}" /></td>
									<td><input type=hidden id="group" name="group"
										value="${danceClass.schedule.group.title}" /> <c:out
											value="${danceClass.schedule.group.title}" /></td>
									<td>
										<form method="post" action="action">
											<input type=hidden id="danceClassId" name="danceClassId"
												value="${danceClass.id}" /> <input type=hidden id="time"
												name="time" value="${danceClass.schedule.time}" /> <input
												type=hidden id="group" name="group"
												value="${danceClass.schedule.group.title}" />

											<button type="submit" class="btn colorBtn" name="command"
												value="READCLIENTSBYDANCECLASS">
												<fmt:message key="enrolledClients" bundle="${ rb }" />
											</button>
										</form>
									</td>

								</tr>
							</c:forEach>
						</table>
					</c:if>
					<p>
					<c:out value="${noClasses}"></c:out>
					</p>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>