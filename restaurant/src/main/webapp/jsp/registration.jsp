<%@ include file="../jspf/directive/page.jspf"%>
<%@ include file="../jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<title>Restaurant | Registration</title>
<link rel="icon" type="image/png" sizes="16x16" href="img/favicon-16x16.png">
<script type="text/javascript">
	window.onload = function() {
		document.getElementById("password").onchange = validatePassword;
		document.getElementById("confirm").onchange = validatePassword;
	}
	function validatePassword() {
		var pass2 = document.getElementById("confirm").value;
		var pass1 = document.getElementById("password").value;
		if (pass1 != pass2)
			document.getElementById("confirm").setCustomValidity(
					'<fmt:message key="validation_match"/>');
		else
			document.getElementById("confirm").setCustomValidity('');
		//empty string means no validation error
	}
</script>
</head>
<body>

	<fmt:setLocale value="${defaultLocale}" />	
	
	<div align=center>
		<form name="validate" action="/restaurant/controller" method="post">
			<input type="hidden" name="command" value="registration" />
			<table>
				<tr>
					<th align=center><fmt:message key="registration_createUser" /></th>
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
					<td align=center><input type="password"
						pattern="^([a-zA-Z0-9]{4,10}$)"
						title='<fmt:message key="validation_validPass"/>'
						style="text-align: center;"
						placeholder='<fmt:message key="validation_pass"/>' required
						Id="password" name="password"></td>
				</tr>
				<tr>
					<td align=center><input type="password"
						style="text-align: center;"
						placeholder='<fmt:message key="validation_confirmPass"/>' required
						Id="confirm" name="confirm"></td>
				</tr>
				<tr>
					<td align=center><button type="submit">
							<fmt:message key="registration_registration" />
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