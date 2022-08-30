<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>CoronaTicket.uy :: Funciones</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/styles/mobile/global.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/mobile/funciones.css" rel="stylesheet">

</head>

<body>

<jsp:include page="/WEB-INF/mobile/template/header.jsp"/>
<div class="main-container">
    <span class="title-container funciones-title">Funciones</span>
    <div class="funciones-container">
        <c:forEach items="${funcionList}" var="funcion">
            <div id="e_${fn:trim(fn:toLowerCase(funcion.nombre))}" class="function-row list-row"
                 onclick="event.preventDefault();window.location.href='/corona-ticket-web/FuncionServlet?nombre_espec=${espectaculo.nombre}&nombre_funcion=${funcion.nombre}&page=detalle'">
                <img src="${pageContext.request.contextPath}${funcion.imagen}" alt="logo" width="35%">
                <span class="function-row-data">${funcion.nombre}</span>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="/WEB-INF/mobile/template/footer.jsp"/>
<!-- /Fin -->

<!-- Bootstrap core JavaScript -->
<script src="../../../vendor/jquery/jquery.min.js"></script>
<script src="../../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
