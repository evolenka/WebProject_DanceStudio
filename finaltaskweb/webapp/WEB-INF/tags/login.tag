<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}"
	scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="pagecontent" var="rb" />
<c:url value="registration.jsp" var="regLink" />
<c:url value="changePassword.jsp" var="changePassword" />


<div class="form-group">
	<form method="post" action="action" class="needs-validation">
		<label for="login"><fmt:message key="login" bundle="${ rb }" /></label>
		<input type="text" class="form-control" id="login" name="login"
			required> <label for="password"><fmt:message
				key="password" bundle="${ rb }" /></label> <input type="password"
			class="form-control" id="password" name="password" required>
		<br>
		<button type="submit" class="btn btn-sm loginformBtn"
			name="command" value="LOGINATION">
			<fmt:message key="logIn" bundle="${rb}" />
		</button>
		<a href='<c:out value="${regLink}"/>'>
			<button type="button" class="btn btn-sm loginformBtn">
				<fmt:message key="register" bundle="${rb}" />
			</button>
		</a>
	</form>
	<span> <a style="text-align: left; color: white"
		href='<c:out value="${changePassword}"/>'><fmt:message
				key="messagePass" bundle="${ rb }" /></a></span>

	<p class="error">
		<c:out value="${errorLoginOrPassword}" />
	</p>

</div>