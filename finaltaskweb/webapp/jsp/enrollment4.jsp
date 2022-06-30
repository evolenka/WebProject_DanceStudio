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
<c:url value="enrollment3.jsp" var="enrollment3" />
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
						<fmt:message key="clientMain.enrollment" bundle="${ rb }" />
					</h1>
					<h5>
						<fmt:message key="enrollment.forthStep" bundle="${ rb }" />
					</h5>
					<form method="post" action="action">
						<table class="table mx-auto">
							<tr>

								<th><fmt:message key="date" bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="group"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="style"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="level"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="teacher"
										bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message key="time"
										bundle="${ rb }" /></th>

								<th id=group class="rowgroup"><fmt:message
										key="membership.startdate" bundle="${ rb }" /></th>
								<th id=group class="rowgroup"><fmt:message
										key="membership.enddate" bundle="${ rb }" /></th>
							</tr>

							<tr>
								<td class="pt-3"><c:out value="${enrollmentDate}" /></td>
								<td class="pt-3"><c:out value="${danceClass.schedule.group.title}" /></td>
								<td class="pt-3"><c:out value="${danceClass.schedule.group.teacher.danceStyle}" /></td>
								<td class="pt-3"><c:out value="${danceClass.schedule.group.level}" /></td>
								<td class="pt-3"><c:out value="${danceClass.schedule.group.teacher.surname}" />
									<c:out value="${danceClass.schedule.group.teacher.name}" /></td>
								<td class="pt-3"><c:out value="${danceClass.schedule.time}" /></td>

								<td class="pt-3"><c:out value="${membership.startDate}" /></td>
								<td class="pt-3"><c:out value="${membership.endDate}" /></td>
							</tr>
						</table>
				
						<a class="btn  colorBtn" href='<c:out value="${enrollment3}"/>'>
							<fmt:message key="back" bundle="${ rb }" />
						</a>
						<button type="submit" class="btn colorBtn" name="command"
							value="CREATEVISIT">
							<fmt:message key="confirm" bundle="${ rb }" />
						</button>
					</form>

					<p>
						<c:out value="${successVisitMessage}" />
						<c:out value="${errorVisitMessage}" />
						<c:out value="${errorNoSession}" />
					</p>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>