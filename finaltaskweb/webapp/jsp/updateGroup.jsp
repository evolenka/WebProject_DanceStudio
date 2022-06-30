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

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.5.1.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
</head>
<body>
	<script type="text/javascript">
		function isEdit() {
			const submitList = document.querySelectorAll('input');
			for (let i = 0, length = submitList.length; i < length; i++) {
				submitList[i].removeAttribute('readonly');
			}
		};
	</script>
	<div class="wrapper">
		<u:mainmenu />
		<div class="content conteiner-fluid">
			<div class=row>
				<div class="col-lg-2">
					<u:adminmenu />
				</div>
				<div class="col-lg-10">
					<h1 class="subtitle">
						<fmt:message key="updateGroup" bundle="${ rb }" />
					</h1>
					<div class="row">
						<div class="col-sm-2 col-lg-4"></div>
						<div class="col-sm-8 col-lg-4">

							<form method="post" action="action" class="needs-validation">
								<label for="group"><fmt:message key="group.title"
										bundle="${ rb }" /></label><br> <input type="text"
									class="form-control" id="group" name="group"
									value="${group.title}" readonly> <label for="teacher">
									<fmt:message key="teacher" bundle="${ rb }" />
								</label><br> <select id="teacher" name="teacher">
									<c:forEach var="teacherItem" items="${teachers}">
										<option value="${teacherItem.surname}"
											${group.teacher.surname == teacherItem.surname ? 'selected' : ''}>${teacherItem.surname}</option>
									</c:forEach>
								</select> <br> <br> <label for="level"> <fmt:message
										key="level" bundle="${ rb }" /></label><br>
								<c:forEach var="level" items="${levels}">
									<input type="radio" id="level" name="level" value="${level}"
										${group.level == level ? 'checked' : ''}> ${level}
							<br>
								</c:forEach>
								<br> <label for="weekday"> <fmt:message
										key="weekday" bundle="${ rb }" /></label><br>
								<c:forEach var="weekday" items="${weekdays}">
								<input type="checkbox" id="weekday" name="weekday"
										<c:forEach var="scheduleItem" items="${group.schedule}">
								value="${weekday}" ${scheduleItem.weekDay == weekday ? 'checked' : ''}</c:forEach>>
									<fmt:message key="${weekday}" bundle="${ rb }" />
									<br>
								</c:forEach>
								<br> <label for="time"><fmt:message key="time"
										bundle="${ rb }" /></label><br> <input type="time"
									class="form-control" id="time" name="time"
									value="${group.schedule[0].time}" readonly> <label
									for="duration"><fmt:message key="duration"
										bundle="${ rb }" /></label><br> <input type="text"
									class="form-control" id="duration" name="duration"
									value="${group.schedule[0].duration}" readonly>


								<button type="button" class="btn colorBtn" onclick="isEdit()">
									<fmt:message key="edit" bundle="${ rb }" />
								</button>
								<button type="submit" class="btn colorBtn" name="command"
									value="UPDATEGROUP">
									<fmt:message key="save" bundle="${ rb }" />
								</button>
							</form>
							<p class=success>
								<c:out value="${successUpdateUserMessage}" />
							</p>
							<p class=error>
								<c:out value="${errorMessage }" />
								<c:out value="${errorNoSession}" />
							</p>
						</div>
						<div class="col-sm-2 col-lg-4"></div>
					</div>
				</div>
			</div>
		</div>
		<u:footer />
	</div>
</body>
</html>