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
		
			<h1 class = "subtitle" ><fmt:message key="schedule.title" bundle="${ rb }" /></h1>
			<div class="table-responsive">
			
				<table>
					<tr>
						<th rowspan="2"></th>
						<th id=group colspan="2" class="rowgroup">18:00</th>
						<th id=group colspan="2" class="rowgroup">19:00</th>
						<th id=group colspan="2" class="rowgroup">20:00</th>
					</tr>
					<tbody>
						<tr>
							<th rowspan="3"><fmt:message key="MONDAY" bundle="${ rb }" /></th>
							<c:forEach var="scheduleMonday" items="${scheduleMonday}">
								<td class="border-left text-left"><span
									class="badge badge-secondary"><c:out
											value='${scheduleMonday.group.level}' /></span></td>
								<td class="pl-4 text-left"><c:out
										value="${scheduleMonday.group.title}" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleMonday" items="${scheduleMonday}">
								<td class="border-left pl-4 text-center"><c:out
										value="${scheduleMonday.group.teacher.danceStyle}" /></td>
								<td class=""><c:out value="${scheduleMonday.duration}" /> <fmt:message
										key="min" bundle="${ rb }" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleMonday" items="${scheduleMonday}">
								<td class="border-bottom border-left pl-4 pb-3 text-right font-italic"><c:out
										value="${scheduleMonday.group.teacher.surname}" /> <c:out
										value="${scheduleMonday.group.teacher.name}" /></td>
								<td class= "border-bottom"/>
							</c:forEach>
						</tr>
						<tr>
							<th rowspan="3"><fmt:message key="TUESDAY" bundle="${ rb }" /></th>
							<c:forEach var="scheduleTuesday" items="${scheduleTuesday}">
								<td class="border-left text-left"><span
									class="badge badge-secondary"><c:out
											value="${scheduleTuesday.group.level}" /></span></td>
								<td class="pl-4 text-left"><c:out
										value="${scheduleTuesday.group.title}" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleTuesday" items="${scheduleTuesday}">
								<td class="border-left pl-4 text-center"><c:out
										value="${scheduleTuesday.group.teacher.danceStyle}" /></td>
								<td><c:out value="${scheduleTuesday.duration}" /> <fmt:message
										key="min" bundle="${ rb }" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleTuesday" items="${scheduleTuesday}">
								<td class="border-bottom border-left pl-4 pb-3 text-right font-italic"><c:out
										value="${scheduleTuesday.group.teacher.surname}" /> <c:out
										value="${scheduleTuesday.group.teacher.name}" /></td>
								<td class = "border-bottom"/>
							</c:forEach>
						</tr>
						<tr>
							<th rowspan="3"><fmt:message key="WEDNESDAY"
									bundle="${ rb }" /></th>
							<c:forEach var="scheduleWednesday" items="${scheduleWednesday}">
								<td class="border-left text-left"><span
									class="badge badge-secondary"><c:out
											value="${scheduleWednesday.group.level}" /></span></td>
								<td class="pl-4 text-left"><c:out
										value="${scheduleWednesday.group.title}" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleWednesday" items="${scheduleWednesday}">
								<td class="border-left pl-4 text-center"><c:out
										value="${scheduleWednesday.group.teacher.danceStyle}" /></td>
								<td><c:out value="${scheduleWednesday.duration}" /> <fmt:message
										key="min" bundle="${ rb }" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleWednesday" items="${scheduleWednesday}">
								<td class="border-bottom border-left pl-4 pb-3 text-right font-italic"><c:out
										value="${scheduleWednesday.group.teacher.surname}" /> <c:out
										value="${scheduleWednesday.group.teacher.name}" /></td>
								<td class="border-bottom"/>
							</c:forEach>
						</tr>
						<tr>
							<th rowspan="3"><fmt:message key="THURSDAY" bundle="${ rb }" /></th>
							<c:forEach var="scheduleThursday" items="${scheduleThursday}">
								<td class="border-left text-left"><span
									class="badge badge-secondary"><c:out
											value="${scheduleThursday.group.level}" /></span></td>
								<td class="pl-4 text-left"><c:out
										value="${scheduleThursday.group.title}" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleThursday" items="${scheduleThursday}">
								<td class="border-left pl-4 text-center"><c:out
										value="${scheduleThursday.group.teacher.danceStyle}" /></td>
								<td><c:out value="${scheduleThursday.duration}" /> <fmt:message
										key="min" bundle="${ rb }" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleThursday" items="${scheduleThursday}">
								<td class="border-bottom border-left pl-4 pb-3 text-right font-italic"><c:out
										value="${scheduleThursday.group.teacher.surname}" /> <c:out
										value="${scheduleThursday.group.teacher.name}" /></td>
								<td  class="border-bottom"/>
							</c:forEach>
						</tr>
						<tr>
							<th rowspan="3"><fmt:message key="FRIDAY" bundle="${ rb }" /></th>
							<c:forEach var="scheduleFriday" items="${scheduleFriday}">
								<td class="border-left text-left"><span
									class="badge badge-secondary"><c:out
											value="${scheduleFriday.group.level}" /></span></td>
								<td class="pl-4 text-left"><c:out
										value="${scheduleFriday.group.title}" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleFriday" items="${scheduleFriday}">
								<td class="border-left pl-4 text-center"><c:out
										value="${scheduleFriday.group.teacher.danceStyle}" /></td>
								<td><c:out value="${scheduleFriday.duration}" /> <fmt:message
										key="min" bundle="${ rb }" /></td>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="scheduleFriday" items="${scheduleFriday}">
								<td class="border-left pl-4 pb-3 text-right font-italic"><c:out
										value="${scheduleFriday.group.teacher.surname}" /> <c:out
										value="${scheduleFriday.group.teacher.name}" /></td>
								<td/>
							</c:forEach>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>