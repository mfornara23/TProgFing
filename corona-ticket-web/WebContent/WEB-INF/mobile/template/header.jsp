<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Header</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="styles/mobile/global.css" rel="stylesheet">

</head>
<body>
<%
    String email = (String) session.getAttribute("email");
    String nickname = (String) session.getAttribute("nickname");
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom header-container">
    <div class="welcome-container">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <%
            if (email != null) {
        %>
        <span>Bienvenid@, <%=nickname%></span>
        <%
            }
        %>
        <img onclick="event.preventDefault();window.location.href='/corona-ticket-web/login?page=home'" src="media/img/logo.png" alt="logo" width="100">
    </div>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <div class="dropdown-divider"></div>
        <%
            if (email == null) {
        %>
        <a class="dropdown-item" href="/corona-ticket-web/login">Iniciar sesión</a>

        <%
        } else {
        %>
        <a class="dropdown-item" href="/corona-ticket-web/EspectaculoServlet?page=listar">Ver Espectáculos</a>
        <a class="dropdown-item" href="/corona-ticket-web/FuncionServlet?page=listar">Ver Funciones</a>
        <div class="dropdown-divider"></div>
        <form action="login" method="post">
            <button class="dropdown-item" type="submit">Cerrar sesión</button>
        </form>
    </div>
    <%
        }
    %>
</nav>
<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>

</body>
</html>