<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>CoronaTicket.uy :: Error</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/styles/global.css" rel="stylesheet">

</head>

<body>
<jsp:include page="/WEB-INF/mobile/template/header.jsp"/>
<div class="main-container">
    <div onclick="goBack()" id="go-back">
        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-left" fill="currentColor"
             xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd"
                  d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
        </svg>
        Ir atrás
    </div>
    <div style="justify-content: center">
        <h1 class="title">Oops, parece que algo salió mal</h1>
    </div>
    <div class="subtitle-container">
        <p class="subtitle">No tienes acceso a este sitio</p></br>
    </div>
</div>
<jsp:include page="/WEB-INF/mobile/template/footer.jsp"/>

<!-- Bootstrap core JavaScript -->
<script src="../../../vendor/jquery/jquery.min.js"></script>
<script src="../../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>
<script>
    function goBack() {
        window.history.back();
    }
</script>
</body>
</html>
