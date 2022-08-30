<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Inicio de sesión fallido</title>

<!-- Bootstrap core CSS -->
<link href="/corona-ticket-web/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="/corona-ticket-web/styles/mobile/global.css" rel="stylesheet">
	<link href="/corona-ticket-web/styles/mobile/login.css" rel="stylesheet">


</head>

<body>
	<jsp:include page="/WEB-INF/mobile/template/header.jsp" />
	<div class="login-container">
		<div class="login-box">
			<div id="go-back" onclick="goBack()">
				<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-left" fill="currentColor"
					 xmlns="http://www.w3.org/2000/svg">
					<path fill-rule="evenodd"
						  d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
				</svg>
				Ir atrás
			</div>
			<div class="title-container">
				<h1 class="title">Iniciar Sesión</h1>
			</div>
			<p style="color: red;">Email o password incorrectos, intente
				nuevamente por favor.</p>
			<div>
				<form style="display: flex; flex-direction: column;" action="login" method="post">
					<table style="with: 100%">
						<tr>
							<td>Email</td>
							<td><input type="text" name="email" /></td>
						</tr>
						<tr>
							<td>Password</td>
							<td><input type="password" name="password" /></td>
						</tr>

					</table>
					<span>
						<input type="checkbox" name="rememberMe" value="true">
						<label>Recordarme</label>
					</span>
					<br> <input class="boton-confirmar button btn-primary" type="submit" value="Confirmar" />
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/mobile/template/footer.jsp" />
	<script>
		function goBack() {
			window.history.back();
		}
	</script>
</body>
</html>
