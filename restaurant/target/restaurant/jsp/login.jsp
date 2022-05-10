<%@ include file="../jspf/directive/page.jspf"%>
<%@ include file="../jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<title>Restaurant | Login</title>
<link rel="icon" type="image/png" sizes="16x16" href="img/favicon-16x16.png">
</head>
<body>
	
	<fmt:setLocale value="${defaultLocale}" />	

	<div align=center>
		<form action="${app}/controller" method="post">
			<input type="hidden" name="command" value="login" />
			<table>
				<tr>
					<th align=center><fmt:message key="authorization" /></th>
				</tr>
				<tr>
					<td align=center><input type="text"
						pattern="^([a-zA-Z0-9]{4,10}$)"
						title='<fmt:message key="validation_validPass"/>'
						style="text-align: center;"
						placeholder='<fmt:message key="validation_login"/>' required
						name="login"></td>
				</tr>

				<tr>
					<td><input type="password" style="text-align: center;"
						pattern="^([a-zA-Z0-9]{4,10}$)"
						title='<fmt:message key="validation_validPass"/>'
						placeholder='<fmt:message key="validation_pass"/>' required
						name="password"></td>
				</tr>

				<tr>
					<td align=center><button type="submit">
							<fmt:message key="enter" />
						</button>
						<button type="button"
							onclick="window.location.href = '/restaurant/index';">
							<fmt:message key="registration_back" />
						</button></td>
				</tr>
			</table>
			<p style="color: red">${error}</p>
			<c:remove var="error" />
		</form>
	</div>

</body>
</html>