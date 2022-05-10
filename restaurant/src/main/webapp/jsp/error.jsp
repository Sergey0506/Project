<%@ include file="../jspf/directive/page.jspf"%>
<%@ include file="../jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<title>Restaurant | Error</title>
<link rel="icon" type="image/png" sizes="16x16" href="img/favicon-16x16.png">
</head>
<body>
	
	<fmt:setLocale value="${defaultLocale}" />
	<c:if test="${not empty user}">
		<fmt:setLocale value="${user.locale}" />
	</c:if>
	
	<h1 align="center">
		<fmt:message key="error_appEr" />
	</h1>

	<p align="center">
		<fmt:message key="error_what" />
	</p>
	<p align="center">${error}</p>
	<c:if test="${empty error}">
		<p align="center">
			<fmt:message key="error_access" />
		</p>
	</c:if>

	<p align="center">
		<button onclick="window.location.href = '${app}/index';">
			<fmt:message key="error_toIndex" />
		</button>
	</p>




</body>
</html>