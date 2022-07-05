<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" var="rb" />
<c:url value="enrollmentFirstStep.jsp" var="enrollmentFirstStep" />
<c:url value="myVisits.jsp" var="myVisits" />
<c:url value="myMemberships.jsp" var="myMemberships" />
<c:url value="changePassword.jsp" var="changePass" />
<c:url value="updateClient.jsp" var="updateClient" />

<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>


<form method="post" action="action">
		<ul class="navbar-nav" style = "margin-left:10px">
			<li class="dropdown dropright"  style="margin-top: 40px">
				<button type="button"
					class="btn dropdown-toggle clientMenuBtn btn-light"
					data-toggle="dropdown">
					<fmt:message key="clientMain.chooseGroup" bundle="${ rb }" />
				</button>
				<div class="dropdown-menu">
					<button type="submit" class="btn clientMenuBtn btn-light dropdown-item"
						name="command" value="READALLSTYLE">
						<fmt:message key="byStyle" bundle="${ rb }" />
					</button>
					<button type="submit" class="btn clientMenuBtn btn-light dropdown-item"
						name="command" value="READALLWEEKDAY">
						<fmt:message key="byWeekDay" bundle="${ rb }" />
					</button>
					<button type="submit" class="btn clientMenuBtn btn-light dropdown-item"
						name="command" value="READALLLEVEL">
						<fmt:message key="byLevel" bundle="${ rb }" />
					</button>
				</div>
			</li>
			<li class="nav-item "><a class="btn clientMenuBtn btn-light"
				href='<c:out value="${enrollmentFirstStep}"/>'> <fmt:message
						key="clientMain.enrollment" bundle="${ rb }" /></a></li>
			<li class="nav-item" style="margin-bottom: 30px">

				<button type="submit" class="btn clientMenuBtn btn-light"
					name="command" value="READALLMEMBERSHIPTYPES">
					<fmt:message key="clientMain.buymembership" bundle="${ rb }" />
				</button>
			</li>
			<li class="nav-item">
				<button type="submit" class="btn clientMenuBtn btn-light"
					name="command" value="PLANNEDVISITS">
					<fmt:message key="clientMain.plannedclasses" bundle="${ rb }" />
				</button>
			</li>
			<li class="nav-item"><a class="btn clientMenuBtn btn-light"
				href='<c:out value="${myVisits}"/>'><fmt:message
						key="clientMain.myvisits" bundle="${ rb }" /> </a></li>
			<li class="nav-item" style="margin-bottom: 30px"><a
				class="btn clientMenuBtn btn-light"
				href='<c:out value="${myMemberships}"/>'><fmt:message
						key="clientMain.mymemberships" bundle="${ rb }" /><br> </a></li>
			<li class="nav-item"><a class="btn clientMenuBtn btn-light"
				href='<c:out value="${updateClient}"/>'><fmt:message
						key="clientMain.updateClient" bundle="${ rb }" /> </a></li>
			<li class="nav-item"><a class="btn clientMenuBtn btn-light"
				href='<c:out value="${changePass}"/>'><fmt:message
						key="clientMain.changePassword" bundle="${ rb }" /> </a></li>
			<li class="nav-item">
				<button type="submit" class="btn clientMenuBtn btn-light"
					name="command" value="LOGOUT">
					<fmt:message key="logout" bundle="${ rb }" />
				</button>
			</li>
		</ul>
	</form>
