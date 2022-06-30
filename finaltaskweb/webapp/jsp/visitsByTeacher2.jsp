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
<c:url value="visitsByTeacher1.jsp" var="visitsByTeacher1" />
</head>
<body>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<div class=row>
				<div class="col-lg-12">
					<h1 class="subtitle" style="margin-bottom: 50px">
					<fmt:message key="teacherMain.visits" bundle="${ rb }"/>
					</h1>
					</div>
				<div class="col-lg-2">
					<u:teachermenu />
				</div>
				<div class="col-lg-10">
					<h5>
						<fmt:message key="group" bundle="${ rb }" />
						<c:out value="${group.title}" />
					</h5>
					<h5>
						<fmt:message key="date" bundle="${ rb }" />
						<c:out value="${visitDate}" />
					</h5>
					<form method="post" action="action">
						<div class="table-responsive">
							<table>
								<tr>
									<th id=group class="rowgroup"><fmt:message key="surname"
											bundle="${ rb }" /></th>
									<th id=group class="rowgroup"><fmt:message key="name"
											bundle="${ rb }" /></th>
									<th id=group class="rowgroup"><fmt:message
											key="patronymic" bundle="${ rb }" /></th>
									<th id=group class="rowgroup"><fmt:message key="status"
											bundle="${ rb }" /></th>
									<th /></th>
								</tr>
								<tbody>
									<c:forEach var="visit" items="${danceClass.visits}">
										<tr>
											<td class="pt-3"><c:out
													value="${visit.membership.client.surname}" /></td>
											<td class="pt-3"><c:out
													value="${visit.membership.client.name}" /></td>
											<td class="pt-3"><c:out
													value="${visit.membership.client.patronymic}" /></td>
											<td class="pt-3"><c:out value="${visit.status}" /></td>
											<c:choose>
												<c:when test="${visit.status != 'PLANNED'}">
													<td><input type="hidden" id="visitId" name="visitId"
														value="${visit.id}">
														<button type="submit" class="btn colorBtn" name="command"
															value="CANCELMARKEDPRESENCE">
															<fmt:message key="cancelMarkedPresence" bundle="${ rb }" />
														</button></td>
												</c:when>
												<c:otherwise>
													<td></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</form>
					<a class="btn  colorBtn"
						href='<c:out value="${visitsByTeacher1}"/>'> <fmt:message
							key="back" bundle="${ rb }" />
					</a>
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