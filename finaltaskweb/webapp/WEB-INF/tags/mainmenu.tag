<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" var="rb" />
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<c:url value="index.jsp" var="main" />
<c:url value="clientMain.jsp" var="clientMain" />
<c:url value="teacherMain.jsp" var="teacherMain" />
<c:url value="adminMain.jsp" var="adminMain" />

<script>
	function replaceUrl() {
		var lang = document.getElementById('language').value;
		var url = "${pageContext.request.requestURL}";
		return url + '?&language=' + lang;
	};
</script>

<div class="conteiner-fluid">
	<nav class="navbar navbar-expand-sm navbar-dark">
		<c:choose>
			<c:when test="${role == 'CLIENT'}">
				<a class="navbar-brand" href='<c:out value="${clientMain}"/>'> <fmt:message
						key="main" bundle="${ rb }" /></a>
			</c:when>
			<c:when test="${role == 'TEACHER'}">
				<a class="navbar-brand" href='<c:out value="${teacherMain}"/>'>
					<fmt:message key="main" bundle="${ rb }" />
				</a>
			</c:when>
			<c:when test="${role == 'ADMIN'}">
				<a class="navbar-brand" href='<c:out value="${adminMain}"/>'> <fmt:message
						key="main" bundle="${ rb }" /></a>
			</c:when>
			<c:otherwise>
				<a class="navbar-brand" href='<c:out value="${main}"/>'> <fmt:message
						key="main" bundle="${ rb }" /></a>
			</c:otherwise>
		</c:choose>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav">
				<li class="nav-item">
					<form method="post" action="action">
						<button type="submit" class="btn" name="command"
							value="READALLSCHEDULE">
							<fmt:message key="schedule.title" bundle="${ rb }" />
						</button>
					</form>
				</li>
				<li class="nav-item">
					<form method="post" action="action">
						<button type="submit" class="btn" name="command"
							value="READALLTEACHER">
							<fmt:message key="teacher.title" bundle="${ rb }" />
						</button>
					</form>
				</li>
				<li class="nav-item">
					<form method="post" action="action">
						<button type="submit" class="btn" name="command"
							value="READALLMEMBERSHIPTYPES">
							<fmt:message key="membershiptypes" bundle="${ rb }" />
						</button>
					</form>
				</li>
			</ul>
		</div>
		<div class="dropdown">
			<form>
				<select id="language" name="language"
					onchange="document.location.replace(replaceUrl())">
					<option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>RU</option>
					<option value="en_US" ${language == 'en_US' ? 'selected' : ''}>EN</option>
					<option value="be_BY" ${language == 'be_BY' ? 'selected' : ''}>BY</option>
				</select>
			</form>
		</div>
	</nav>
</div>