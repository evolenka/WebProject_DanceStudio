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
						<fmt:message key="teacherMain.visitsForPeriod" bundle="${ rb }" />
					</h1>
				</div>
				<div class="col-lg-2">
					<u:teachermenu />
				</div>
				<div class="col-lg-5">
					<h5>
						<fmt:message key="myVisits.choosePeriod" bundle="${ rb }" />
					</h5>
					<form method="post" action="action">
						<c:set var="currentdate">
							<ctg:currentdate />
						</c:set>
						<input type="date" name="startDate"
							value=${not empty startDate ? startDate : currentdate}> <input
							type="date" name="endDate"
							value=${not empty endDate ? endDate : currentdate}> <br>
						<button type="submit" class="btn colorBtn" name="command"
							value="READVISITCOUNTBYTEACHERGROUPSANDPERIOD">
							<fmt:message key="choose" bundle="${ rb }" />
						</button>
					</form>
				</div>
				<div class="col-lg-5">
					<c:if test="${not empty countVisitsByTeacherGroups}">
						<div class="table-responsive">
							<table>
								<tr>
									<th><fmt:message key="group" bundle="${ rb }" /></th>
									<th><fmt:message key="quantity" bundle="${ rb }" /></th>
								</tr>
								<tbody>
									<c:forEach items="${countVisitsByTeacherGroups}" var="entry">
										<tr>
											<td><c:out value="${entry.key}" /></td>
											<td><c:out value="${entry.value}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>