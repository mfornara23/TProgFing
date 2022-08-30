<%@page import="tpgr06.coronatickets.ws.publicador.PlataformaDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>CoronaTicket.uy :: Paquetes</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="styles/global.css" rel="stylesheet">

<!-- Custom styles for Espectaculos -->
<link href="styles/busqueda.css" rel="stylesheet">

</head>

<body>

	<jsp:include page="/WEB-INF/jsp/template/header.jsp" />
	<div class="d-flex" id="wrapper">
		<jsp:include page="/WEB-INF/jsp/template/sidebar.jsp" />


		<div class="main-container">

			<div class="title-container">
				<h1 class="title">Paquetes</h1>
			</div>

			<c:choose>
				<c:when test="${empty paquetes}">
			<span>No existen paquetes en el sistema</span>
				</c:when>
				<c:otherwise>
					<c:forEach items="${paquetes}" var="paquete">
				<a class="paquete-row list-row" href="/corona-ticket-web/PaqueteServlet?nombre_paquete=${paquete.nombre}">
				<img src="${pageContext.request.contextPath}${paquete.imagen}" alt="logo" width="36">
				<span class="espectaculo-row-data">${paquete.nombre}</span> <span
				class="espectaculo-row-data">${paquete.descuento}%</span>
				<span class="espectaculo-row-data">${paquete.descripcion}</span>
			</a>

					</c:forEach>
				</c:otherwise>
			</c:choose>

		<div>
		<br><br>
		

			     <div style="display: flex; justify-content: flex-end; width: 80%; visibility: ${altaVisible};">
 
		        <form action="/corona-ticket-web/PaqueteServlet" method = "GET">
			          	 <input type="hidden" name="page" value="alta">
			          	<button class="btn btn-primary button" type="submit">Crear Paquete</button>		          	
        		</form>
        		 </div>
        		

				
		
		</div>
	</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/template/footer.jsp" />
	<!-- Bootstrap core JavaScript -->
	<script src="../../../vendor/jquery/jquery.min.js"></script>
	<script src="../../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>



