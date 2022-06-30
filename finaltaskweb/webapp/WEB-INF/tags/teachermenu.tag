<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" var="rb"/>

<c:url value="changePassword.jsp" var="changePass"/>
<c:url value="visitStatisticsForTeacher.jsp" var="visitStatistics"/>

<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>


<form method="post" action="action">
		<ul class="navbar-nav" style = "margin-left:10px">
			<li class="nav-item">
				<button type="submit" class="btn clientMenuBtn btn-light"
					name="command" value="READPLANNEDCLASSESBYTEACHER">
					<fmt:message key="teacherMain.markPresence" bundle="${ rb }" />
				</button>
			</li>
			<li class="nav-item" style="margin-top: 10px">
			
				<button type="submit" class="btn clientMenuBtn btn-light"
					name="command" value="READGROUPSBYTEACHER">
					<fmt:message key="teacherMain.visits" bundle="${ rb }" />
				</button>
			</li>
			<li class="nav-item" style="margin-top: 10px">
			<a class="btn clientMenuBtn btn-light"
				href='<c:out value="${visitStatistics}"/>'><fmt:message
						key="teacherMain.visitsForPeriod" bundle="${ rb }" /> </a></li>
			<li class="nav-item" style="margin-top: 10px"><a class="btn clientMenuBtn btn-light"
				href='<c:out value="${changePass}"/>'><fmt:message
						key="clientMain.changePassword" bundle="${ rb }" /> </a></li>
			<li class="nav-item" style="margin-top: 10px">
				<button type="submit" class="btn clientMenuBtn btn-light"
					name="command" value="LOGOUT">
					<fmt:message key="logout" bundle="${ rb }" />
				</button>
			</li>
		</ul>
	</form>