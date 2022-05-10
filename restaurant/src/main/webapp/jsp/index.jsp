<%@ include file="../jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<title>Restaurant</title>
<link rel="icon" type="image/png" sizes="16x16" href="img/favicon-16x16.png">
</head>
<body>
	<fmt:setLocale value="${defaultLocale}" />
	<c:if test="${not empty user}">
		<fmt:setLocale value="${user.locale}" />
	</c:if>

	<h1 align="center">Restaurant</h1>
	
	<%@ include file="/jspf/header.jspf"%>

	<c:if test="${not empty show.get('main')}">
		<%@ include file="/jspf/main.jspf"%>
	</c:if>

	<c:if test="${not empty show.get('basket')}">
		<%@ include file="/jspf/basket.jspf"%>
	</c:if>

	<c:if test="${not empty show.get('myOrder')}">
		<%@ include file="/jspf/myorder.jspf"%>
	</c:if>

	<c:if test="${not empty show.get('mySettings')}">
		<%@ include file="/jspf/mysettings.jspf"%>
	</c:if>

	<c:if test="${not empty show.get('allOrders')}">
		<%@ include file="/jspf/allorders.jspf"%>
	</c:if>

	<c:if test="${not empty show.get('addFood')}">
		<%@ include file="/jspf/addfood.jspf"%>
	</c:if>




</body>
</html>






















