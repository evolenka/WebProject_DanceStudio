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
						<fmt:message key="closeForEnrollment" bundle="${ rb }" />
					</h1>
				</div>
				<div class="col-lg-2">
					<u:adminmenu />
				</div>
				<div class="col-lg-10">
					<h5>
						<fmt:message key="chooseDate" bundle="${ rb }" />
					</h5>
					<form method="post" action="action">
						<c:set var="currentdate">
							<ctg:currentdate />
						</c:set>
						<input type="date" name="classDate"
							value=${not empty classDate ? classDate : currentdate} required>
						<br>
						<button type="submit" class="btn colorBtn" name="command"
							value="READACTIVECLASSESBYDATE">
							<fmt:message key="choose" bundle="${ rb }" />
						</button>
					</form>
					<form method="post" action="action">
						<c:if test="${not empty danceClasses}">

							<table>
								<tr>
									<th></th>
									<th><fmt:message key="date" bundle="${ rb }" /></th>
									<th id=group class="rowgroup"><fmt:message key="time"
											bundle="${ rb }" /></th>
									<th id=group class="rowgroup"><fmt:message key="group"
											bundle="${ rb }" /></th>
									<th></th>
								</tr>
								<c:forEach var="danceClass" items="${danceClasses}">
									<tr>
										<td class="pt-3"><input type="radio" id="danceClassId"
											name="danceClassId" value="${danceClass.id}" required /></td>
										<td class="pt-3"><c:out
												value="${danceClass.schedule.time}" /></td>
										<td class="pt-3"><c:out
												value="${danceClass.schedule.group.title}" /></td>

										<td>
											<button type="submit" class="btn colorBtn" name="command"
												value="CLOSEENROLLMENT">
												<fmt:message key="closeForEnrollment" bundle="${ rb }" />
											</button>
										</td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
					</form>
					<p>
						<c:out value="${noClasses}" />
					</p>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>