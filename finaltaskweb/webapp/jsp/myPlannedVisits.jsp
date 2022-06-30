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
					<u:clientmenu />
				</div>
				<div class="col-lg-10">
					<h1 class="subtitle">
						<fmt:message key="clientMain.plannedclasses" bundle="${ rb }" />
					</h1>
					<form method="post" action="action">
						<table>
							<tr>
								<th></th>
								<th><fmt:message key="date" bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="time"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="group"
										bundle="${ rb }" /></th>

								<th id=group class="rowgroup"><fmt:message key="style"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="level"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="teacher"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message
										key="membership.startdate" bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message
										key="membership.enddate" bundle="${ rb }" /></th>
								<th></th>
							</tr>
							<c:forEach var="plannedVisit" items="${plannedVisits}">
								<tr>
									<td class="pt-3"><input type="radio" id="plannedvisitId"
										name="plannedvisitId" value="${plannedVisit.id}" required /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.danceClass.date}" /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.danceClass.schedule.time}" /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.danceClass.schedule.group.title}" /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.danceClass.schedule.group.teacher.danceStyle}" /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.danceClass.schedule.group.level}" /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.danceClass.schedule.group.teacher.surname}" />
										<c:out
											value="${plannedVisit.danceClass.schedule.group.teacher.name}" /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.membership.startDate}" /></td>
									<td class="pt-3"><c:out
											value="${plannedVisit.membership.endDate}" /></td>

									<td>
										<button type="submit" class="btn colorBtn" name="command"
											value="CANCELVISIT">
											<fmt:message key="myPlannedVisits.cancelVisit"
												bundle="${ rb }" />
										</button>
									</td>
								</tr>
							</c:forEach>
						</table>
					</form>
					
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>