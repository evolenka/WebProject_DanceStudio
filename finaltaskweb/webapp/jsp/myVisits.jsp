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
				<div class="col-lg-2">
					<u:clientmenu />
				</div>
				<div class="col-lg-10">
					<h1 class="subtitle">
						<fmt:message key="myVisits.visits" bundle="${ rb }" />
					</h1>
					<div class="col-lg-4">
						<p>
							<fmt:message key="myVisits.choosePeriod" bundle="${ rb }" />
						</p>
						<form method="post" action="action" class="needs-validation">
							<c:set var="currentdate">
								<ctg:currentdate />
							</c:set>
							<c:set var="maxdate">
								<ctg:maxdate/>
							</c:set>
							<label for="startDateVisit"><fmt:message key="startdate"
									bundle="${ rb }" /></label>
							<input type="date" class="form-control"
								id="startDateVisit" name="startDateVisit"
								value=${not empty startDateVisit ? startDateVisit : currentdate}>
							<label for="endDateVisit"><fmt:message key="enddate"
									bundle="${ rb }" /></label>
							<input type="date" class="form-control"
								id="endDateVisit" name="endDateVisit"
								value=${not empty endDateVisit ? endDateVisit : maxdate}><br>

							<button type="submit" class="btn colorBtn" name="command"
								value="MYVISITS">
								<fmt:message key="choose" bundle="${ rb }" />
							</button>
						</form>
						 
					</div>
					<div class="col-lg-12">
						<table class="table mx-auto">
							<tr>
								<th id=group class="rowgroup"><fmt:message key="date"
										bundle="${ rb }" /></th>
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
								<th id=group class="rowgroup"><fmt:message key="status"
										bundle="${ rb }" /></th>
							</tr>
							<c:forEach var="visit" items="${visits}">
								<tr>
									<td class="pt-3"><c:out value="${visit.danceClass.date}" /></td>
									<td class="pt-3"><c:out
											value="${visit.danceClass.schedule.time}" /></td>
									<td class="pt-3"><c:out
											value="${visit.danceClass.schedule.group.title}" /></td>
									<td class="pt-3"><c:out
											value="${visit.danceClass.schedule.group.teacher.danceStyle}" /></td>
									<td class="pt-3"><c:out
											value="${visit.danceClass.schedule.group.level}" /></td>
									<td class="pt-3"><c:out
											value="${visit.danceClass.schedule.group.teacher.surname}" />
										<c:out value="${visit.danceClass.schedule.group.teacher.name}" /></td>
									<td class="pt-3"><c:out
											value="${visit.membership.startDate}" /></td>
									<td class="pt-3"><c:out
											value="${visit.membership.endDate}" /></td>
									<td class="pt-3"><c:out value="${visit.status}" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>