<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>CoronaTicket.uy :: Info Función</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/styles/mobile/global.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/mobile/funciones.css" rel="stylesheet">

</head>

<body>

<jsp:include page="/WEB-INF/mobile/template/header.jsp"/>
<div class="main-container">
    <div class="title-container">
        <div onclick="goBack()" id="go-back">
            <svg width="1em" height="1em" viewBox="0 0 16 16"
                 class="bi bi-chevron-left" fill="currentColor"
                 xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
            </svg>
            Ir atrás
        </div>
        <span style="font-weight: 600; font-size: 20px;">${funcion.nombre}</span>
    </div>
    <div class="main-detalle">
        <div class="left-column">
            <div class="main-info">
                <img src="${pageContext.request.contextPath}${funcion.imagen}" alt="logo" width="50%">
                <div style="padding: 20px">
                    <span style="font-style: italic">${espectaculo.plataforma.nombre}</span><br>
                    <a style="font-style: italic" href="/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle">
                        ${espectaculo.nombre}
                    </a>
                </div>
            </div>
            <div class="funcion-data-container">
                <span style="font-weight: 600">Fecha</span>
                <span><fmt:formatDate pattern="dd/MM/yyyy" value="${funcion.fecha.toGregorianCalendar().time}"/></span>
            </div>
            <div class="funcion-data-container">
                <span style="font-weight: 600">Hora Inicio</span>
                <span><fmt:formatDate pattern="HH:mm" value="${funcion.fecha.toGregorianCalendar().time}"/></span>
            </div>
        </div>
        <div class="right-column">
            <div class="additional-data-container">
                <span class="subtitle">Artistas invitados</span>
                    <c:forEach items="${funcion.invitados}" var="invitado">
                        <span class="span_right_item">${invitado.nombre} ${invitado.apellido}</span>
                    </c:forEach>
                <div class="dropdown-divider"></div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/mobile/template/footer.jsp"/>
<!-- /Fin -->

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
