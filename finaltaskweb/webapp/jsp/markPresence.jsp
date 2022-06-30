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
					<h1 class="subtitle" style="margin-bottom: 50px">
						<fmt:message key="markPresence.unmarkedDanceClass"/>
					</h1>
				</div>
				<div class="col-lg-2">
					<u:teachermenu />
				</div>
				<div class="col-lg-10">
					<c:if test="${not empty plannedVisits}">
						<div class="table-responsive">
							<form method="post" action="action">
								<table>
									<tr>
										<th><fmt:message key="date" bundle="${ rb }" /></th>
										<th class="rowgroup"><fmt:message key="group"
												bundle="${ rb }" /></th>
										<th class="rowgroup"><fmt:message key="time"
												bundle="${ rb }" /></th>
										<th id=group class="rowgroup"><fmt:message key="surname"
												bundle="${ rb }" /></th>
										<th id=group class="rowgroup"><fmt:message key="name"
												bundle="${ rb }" /></th>
										<th id=group class="rowgroup"><fmt:message
												key="patronymic" bundle="${ rb }" /></th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
									<tbody>
										<c:forEach var="visit" items="${plannedVisits}">
											<tr>
												<td class="pt-3"><c:out
														value='${visit.danceClass.date}' /></td>
												<td class="pt-3"><c:out
														value="${visit.danceClass.schedule.group.title}" /></td>
												<td class="pt-3"><c:out
														value="${visit.danceClass.schedule.time}" /></td>

												<td class="pt-3"><c:out
														value="${visit.membership.client.surname}" /></td>
												<td class="pt-3"><c:out
														value="${visit.membership.client.name}" /></td>
												<td class="pt-3"><c:out
														value="${visit.membership.client.patronymic}" /></td>
												<td><input type="radio" id=attended name="status"
													value="ATTENDED" required> <label for="status">
														<fmt:message key="attended" bundle="${ rb }" />
												</label></td>
												<td><input type="radio" id="absent" name="status"
													value="ABSENT" required> <label for="status">
														<fmt:message key="absent" bundle="${ rb }" />
												</label></td>
												<td><input type="hidden" name="visitId"
													value="${visit.id}">
													<button type="submit" class="btn colorBtn" name="command"
														value="MARKPRESENCE">
														<fmt:message key="markPresence" bundle="${ rb }" />
													</button>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</form>
						</div>
						<p>
							<c:out value="${errorMessage }" />
						</p>
					</c:if>
					<c:if test="${empty plannedVisits}">
						<fmt:message key="noUnmarkedClasses" bundle="${ rb }" />
					</c:if>
					<p>
						<c:out value="${errorNoSession}" />
					</p>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>