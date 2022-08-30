<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>   
<!DOCTYPE html>
<html lang="es">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>CoronaTicket.uy :: Agregar Espect�culos a Paquete</title>

  <!-- Bootstrap core CSS -->
  <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/styles/global.css" rel="stylesheet">

  <!-- Custom styles for Espectaculos -->
  <link href="${pageContext.request.contextPath}/styles/espectaculos.css" rel="stylesheet">

</head>

<body>

  <jsp:include page="/WEB-INF/jsp/template/header.jsp" />
	<div class="d-flex" id="wrapper">
	<jsp:include page="/WEB-INF/jsp/template/sidebar.jsp" />
	
	<div id="page-content-wrapper">
		<button class="btn btn-primary" id="menu-toggle">
			<svg width="1em" height="1em" viewBox="0 0 16 16"
				class="bi bi-arrow-bar-left" fill="currentColor"
				xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd"
					d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z" />
        </svg>
		</button>
		<div class="main-container">
        <div class="title-container">
          <h1 class="title">Paquete: ${paquete}</h1>
        </div>
        
        
        <form action="/corona-ticket-web/PaqueteServlet" method = "GET">
	        <div style="display: flex; align-items: flex-end">
	          <div style="margin-right: 10px">
	            <label>Plataforma:</label>
	            <br>
	            <select id="plataforma" name= "plat_espec" class="plataforma">
				  <c:forEach items="${plataformaList}" var="plataforma">
				  	<option value="${plataforma.nombre}">${plataforma.nombre}</option>
				  </c:forEach>
	            </select>
	          </div>
	          <div>
	          	<button class="btn btn-primary button" type="submit">Buscar</button>
	          </div>
	        </div>
        </form>
        
        
        <c:forEach items="${espectaculoNoEnPaqueteList}" var="espectaculo">
        	<form action="/corona-ticket-web/PaqueteServlet" method = "POST">
	        	<div style="padding: 3px 0;">
	          		<a id="e_${fn:trim(fn:toLowerCase(espectaculo.nombre))}" class="espectaculo-row list-row" 
	          			href="/corona-ticket-web/EspectaculoServlet">
	            	<img src="${pageContext.request.contextPath}${espectaculo.imagen}" alt="logo" width="36">
	            	<span class="espectaculo-row-data">${espectaculo.nombre}</span>
	           		<span class="espectaculo-row-data">${espectaculo.artista.nombre} ${espectaculo.artista.apellido}</span>
	          		<button class="btn btn-primary button" type="submit">Agregar</button>
	          	</a>
	        	</div>
 			<input type="hidden" name="espectaculoParaAgregar" value="${espectaculo.nombre}">
 			<input type="hidden" name="paquete_nombre" value="${paquete}">
	             </form >
	        
		</c:forEach>
      </div>
	</div>
 
    </div>
  
<jsp:include page="/WEB-INF/jsp/template/footer.jsp" />
  <!-- /Fin -->

  <!-- Bootstrap core JavaScript -->
  <script src="../../../vendor/jquery/jquery.min.js"></script>
  <script src="../../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>

</body>
</html>