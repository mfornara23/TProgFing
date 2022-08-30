<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sidebar</title>
<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="styles/mobile/global.css" rel="stylesheet">
</head>
<body>
	<div class="bg-light border-right" id="sidebar-wrapper">
		<div class="sidebar-heading">Menú</div>
		<div class="list-group list-group-flush">
			<a href="/corona-ticket-web/UsuarioServlet?page=lista"
				class="list-group-item list-group-item-action bg-light">Usuarios</a>
			<a href="/corona-ticket-web/EspectaculoServlet?page=listar"
				class="list-group-item list-group-item-action bg-light">Espectáculos</a>
			<a href="/corona-ticket-web/PaqueteServlet?page=listar"
				class="list-group-item list-group-item-action bg-light">Paquetes</a>
			<a href="/corona-ticket-web/FuncionServlet?page=listar"
				class="list-group-item list-group-item-action bg-light">Funciones</a>
		</div>
	</div>

</body>
</html>