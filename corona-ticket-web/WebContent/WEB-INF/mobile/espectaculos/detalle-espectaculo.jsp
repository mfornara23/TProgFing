<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>CoronaTicket.uy :: Detalle Espectáculo</title>

    <!-- Bootstrap core CSS -->
    <link
            href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css"
            rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/styles/mobile/global.css"
          rel="stylesheet">

    <!-- Custom styles for Espectaculos -->
    <link href="${pageContext.request.contextPath}/styles/mobile/espectaculos.css"
          rel="stylesheet">

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
        <span style="font-weight: 600; font-size: 20px;">${espectaculo.nombre}</span>
    </div>
    <div class="main-detalle">
        <div class="left-column">
            <div class="main-info">
                <img
                        src="${pageContext.request.contextPath}${espectaculo.imagen}"
                        alt="logo" width="50%">
                <div
                        style="display: flex; flex-direction: column; padding: 10px;">
                    <span style="font-style: italic">${espectaculo.plataforma.nombre}</span>
                    <a href="${espectaculo.url}" style="font-style: italic">Ir al sitio</a>
                </div>
            </div>
            <div>
                <div class="espectaculo-data-container">
                    <span style="font-size: 16px; font-style: italic">
                        ${espectaculo.descripcion}</span>
                </div>
                <div class="espectaculo-data-container">
                    <span style="font-weight: 600">Fecha de Registro</span>
                    <span><fmt:formatDate
                        pattern="dd/MM/yyyy" value="${espectaculo.fechaReg.toGregorianCalendar().time}"/></span>
                </div>
                <div class="espectaculo-data-container">
                    <span style="font-weight: 600">Duración</span> <span>${espectaculo.duracion}
							minutos</span>
                </div>
                <div class="espectaculo-data-container">
                	<span style="font-weight: 600">Descripción Premio</span>
                    <span style="font-size: 16px; font-style: italic; text-align: right">${espectaculo.descripcionPremios}</span>
                </div>
                <div class="espectaculo-data-container">
                    <span style="font-weight: 600">Cantidad de premios</span> <span>${espectaculo.cantidadPremios}</span>
                </div>
                <div class="espectadores-container">
                    <span style="font-weight: 600">Espectadores</span>
                    <div class="espectaculo-data-container espectadores-data">
                        <span style="font-weight: 600">Mínimo</span> <span>${espectaculo.cantMin}</span>
                    </div>
                    <div class="espectaculo-data-container espectadores-data">
                        <span style="font-weight: 600">Máximo</span> <span>${espectaculo.cantMax}</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="right-column">
            <div class="additional-data-container">
                <span class="subtitle">Funciones</span>
                <c:forEach items="${funciones}" var="funcion">
                    <a class="span_right_item"
                    href="FuncionServlet?nombre_espec=${espectaculo.nombre}&nombre_funcion=${funcion.nombre}&page=detalle">${funcion.nombre}</a>
                </c:forEach>
                <div class="dropdown-divider"></div>
            </div>
            <div class="additional-data-container">
                <span class="subtitle">Paquetes</span>
                <c:forEach items="${paquetes}" var="paquete">
                    <span class="span_right_item">${paquete.nombre}</span>
                </c:forEach>
                <div class="dropdown-divider"></div>
            </div>
            <div class="additional-data-container">
                <span class="subtitle">Categorias</span>
                <c:forEach items="${categorias}" var="categoria">
                    <span class="span_right_item">${categoria}</span>
                </c:forEach>
                <div class="dropdown-divider"></div>
            </div>
            <div class="espectaculo-data-container" style="width: 50%">
						<span
                                style="font-weight: 600; font-size: 23px; align-self: center;">Costo</span>
                <span style="font-weight: 600; font-size: 30px">$${espectaculo.costo}</span>
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
        console.dir("go-back-click");
        window.history.back();
    }
</script>
</body>
</html>
