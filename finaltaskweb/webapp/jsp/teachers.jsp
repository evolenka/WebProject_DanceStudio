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

<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<c:url value="teacherRegistration.jsp" var="teacherRegistration" />

<script>
	function confirmDelete() {
		if (confirm("Are you sure?/Вы уверены?")) {
			return false;
		} else {
			return true;
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<div class=row>
				<div class="col-lg-12">
					<h1 class="subtitle">
						<fmt:message key="teachers" bundle="${ rb }" />
					</h1>
				</div>
				<div class="col-lg-2">
					<u:adminmenu />
				</div>
				<div class="col-lg-10">
					<c:if test="${not empty teachers}">
						<a class="btn colorBtn"
							href='<c:out value="${teacherRegistration}"/>'> <fmt:message
								key="addClient" bundle="${ rb }" />
						</a>
						<div class="table-responsive">
							<table>
								<tr>
									<th></th>
									<th><fmt:message key="surname" bundle="${ rb }" /></th>
									<th><fmt:message key="name" bundle="${ rb }" /></th>
									<th><fmt:message key="style" bundle="${ rb }" /></th>
									<th><fmt:message key="portfolio" bundle="${ rb }" /></th>
									<th><fmt:message key="login" bundle="${ rb }" /></th>
									<th></th>
									<th></th>
								</tr>
								<tbody>
									<c:forEach var="teacher" items="${teachers}" varStatus="number">
										<tr>
											<td><c:out value="${number.count}" /></td>
											<td><c:out value="${teacher.surname}" /></td>
											<td><c:out value="${teacher.name}" /></td>
											<td><c:out value="${teacher.danceStyle}" /></td>
											<td><c:out value="${teacher.portfolio}" /></td>
											<td><c:out value="${teacher.login}" /></td>
											<td>
												<form method="post" action="action">
													<input type="hidden" name="teacherId" value="${teacher.id}">
													<button type="submit" class="btn colorBtn" name="command"
														value="EDITTEACHER">
														<fmt:message key="edit" bundle="${ rb }" />
													</button>
												</form>
											</td>
											<td><form method="post" action="action">
													<input type="hidden" name="teacherId" value="${teacher.id}">
													<button type="submit" class="btn colorBtn" name="command"
														value="DELETETEACHER" onclick="confirmDelete()">
														<fmt:message key="delete" bundle="${ rb }" />
													</button>
												</form></td>
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