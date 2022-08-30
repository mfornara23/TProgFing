<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>CoronaTicket.uy :: Alta de Paquete</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/styles/global.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/styles/paquetes.css"
	rel="stylesheet">

</head>

<body>

	<jsp:include page="/WEB-INF/jsp/template/header.jsp" />
	<div class="d-flex" id="wrapper">
		<jsp:include page="/WEB-INF/jsp/template/sidebar.jsp" />

		<div id="page-content-wrapper">
			<button class="btn btn-primary" id="menu-toggle">
				<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-bar-left" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
          			<path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z" />
                </svg>
			</button>
			
			
		<div class="main-container">
         <div class="title-container">
          <div id="go-back" onclick="goBack()">
            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-left" fill="currentColor"
                 xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
                    d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
            </svg>
            Ir atrás
          </div>
          <h1 style="margin: 15px" class="title">Alta de Paquete</h1>
         </div>
         <form action="/corona-ticket-web/PaqueteServlet" method="POST" enctype="multipart/form-data" style="display: flex; justify-content: center;" >
            <div style="width: 70%; padding: 20px; border: 1px solid black; border-radius: 6px;">
	            <div>
	              <label for="nombre">Nombre:</label><br>
	              <input type="text" id="nombre" name="nombre_paquete" required><br><br>
	              <label for="descripcion">Descripción:</label><br>
                  <textarea id="descripcion" name="descripcion_paquete" cols="40" rows="5" required></textarea><br><br>
	            </div>
	            <div style="display: flex">
	              <div id="col1">
	                <label for="vigencia">Período de Vigencia:</label><br><br>
	                <label for="imagen">Adjuntar Imagen:</label><br>
	              </div>
	              <div id="col2">
	                <label for="vigencia_desde">Desde:</label><br>
	                <input type="date" id="vigencia_desde" name="vigencia_desde_paq" required><br><br>
	                <input type="file" id="imagen" name="imagen_paquete" style="border: none; background-color: inherit"><br><br>
	              </div>
	              <div id="col3">
	                <label for="vigencia_hasta">Hasta:</label><br>
	                <input type="date" id="vigencia_hasta" name="vigencia_hasta_paq" required><br><br>
	                <label for="descuento">Descuento (%):</label><br>
	                <input type="text" id="descuento" name="descuento_paquete" required><br><br>
	              </div>
	            </div>
	            <div style="display: flex; justify-content: flex-end; align-items: center;">
	              <button class="btn btn-primary button" type="submit" style="margin: 5px">Confirmar</button>
	              <button class="btn btn-primary button-cancel" onclick="goBack()" style="margin: 5px">Cancelar</button>
	            </div>
            </div>
         </form>
        </div>
     </div>
    </div>

	<jsp:include page="/WEB-INF/jsp/template/footer.jsp" />

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
	<script>
    function goBack() {
      window.history.back();
    }
  </script>
</body>
</html>
