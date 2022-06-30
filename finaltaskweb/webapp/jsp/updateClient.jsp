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
				<div class="col-lg-10">
					<h1 class="subtitle">
						<fmt:message key="clientMain.updateClient" bundle="${ rb }" />
					</h1>
					<div class="row">
						<div class="col-sm-2 col-lg-4"></div>
						<div class="col-sm-8 col-lg-4">
							<form method="post" action="action" class="needs-validation">
								<label for="surname"><fmt:message
										key="updateProfile.surname" bundle="${ rb }" /></label><br> <input
									type="text" class="form-control" id="surname" name="surname"
									value="${client.surname }" readonly> <label for="name"><fmt:message
										key="updateProfile.name" bundle="${ rb }" /></label><br> <input
									type="text" class="form-control" id="name" name="name"
									value="${client.name }" readonly> <label
									for="patronymic"><fmt:message
										key="registration.patronymic" bundle="${ rb }" /></label><br> <input
									type="text" class="form-control" id="patronymic"
									name="patronymic" value="${client.patronymic}"
									readonly="readonly"> <label for="email"><fmt:message
										key="updateProfile.email" bundle="${ rb }" /></label><br> <input
									type="email" class="form-control" id="email" name="email"
									value="${client.email}" readonly> <label for="phone"><fmt:message
										key="registration.phone" bundle="${ rb }" /></label><br> <input
									type="text" class="form-control" id="phone" name="phone"
									value="${client.phone}" readonly> <br>
								<button type="button" class="btn colorBtn" onclick="isEdit()">
									<fmt:message key="edit" bundle="${ rb }" />
								</button>
								<button type="submit" class="btn colorBtn" name="command"
									value="UPDATECLIENT">
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