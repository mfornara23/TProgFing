<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %> 
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
  <link href="${pageContext.request.contextPath}/styles/global.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/styles/funciones.css" rel="stylesheet">

</head>

<body>

  <jsp:include page="/WEB-INF/jsp/template/header.jsp" />
	<div class="d-flex" id="wrapper">
	<jsp:include page="/WEB-INF/jsp/template/sidebar.jsp" />

    <div id="page-content-wrapper">
      <button class="btn btn-primary" id="menu-toggle">
        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-bar-left" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z"/>
        </svg>
      </button>

    <!--Inicio: Insertar acá el código de la página-->

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
          <h1 style="margin: 15px" class="title">Información de Función</h1>
        </div>
        
        <div style="margin: 10px 110px" class="tabcontent">
			<div style="display: flex; flex-direction: row; width: 100%; justify-content: space-between;">
				<img src="${pageContext.request.contextPath}${funcion.imagen}" alt="logo" width="200">
				<div style="padding: 20px">
					<span style="font-weight: 600; font-size: 24px;">${funcion.nombre}</span><br>
					<span style="font-style: italic">${espectaculo.plataforma.nombre}</span><br>
					<button class="btn btn-primary button" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle'">Ver información del espectáculo</button>
				</div>	
				<div style="visibility: ${registroVisible};">
	        		<button class="btn btn-primary button" onclick="window.location.href='/corona-ticket-web/RegistroFuncionServlet?nombre_espec=${espectaculo.nombre}&nombre_funcion=${funcion.nombre}'">Registrarse</button>
	          	</div>
			</div>
			<div class="funcion-data-container">
              <span style="font-weight: 600">Artistas Invitados</span>
              <span><c:forEach items="${funcion.invitados}" var="invitado">
			            <span>${invitado.nombre} ${invitado.apellido}</span><br>
		  		    </c:forEach>
		  	  </span>
            </div>
            <div class="funcion-data-container">
              <span style="font-weight: 600">Fecha</span>
              <span><fmt:formatDate pattern = "dd/MM/yyyy" value = "${funcion.fecha.toGregorianCalendar().time}"/></span>
            </div>
            <div class="funcion-data-container">
              <span style="font-weight: 600">Hora Inicio</span>
              <span><fmt:formatDate pattern = "HH:mm" value = "${funcion.fecha.toGregorianCalendar().time}"/></span>
            </div>
            <div style="visibility: ${sorteoVisible};">
            <form method="get" action="/corona-ticket-web/FuncionServlet">
            <input type="hidden" name="nombre_espec" value="${espectaculo.nombre}">
            <input type="hidden" name="nombre_funcion" value="${funcion.nombre}">    
            <input type="hidden" name="page" value="sorteo">            
                    
            <button class="btn btn-primary button" type="submit">Sortear Premios</button>         
            </form>
            </div>  
        <div style="visibility: ${ganadoresVisible};">
         <div>
        <h1 class="title">Sorteo</h1>
        </div>
       <div>
        <h1 class="subtitle">Premio: ${funcion.premio.descripcion}</h1>
        </div>
        <div>
        <h1 class="subtitle">Ganadores:</h1>
        </div>        
        <c:forEach items="${requestScope.funcion.premio.ganadores}" var="espectador">
          <div class="user-row list-row">
            <span class="user-row-data">${espectador}</span>
            <span></span>
            <span class="user-row-data"></span>
            <span onclick="" style="justify-self: center;">
          </span>
          </div>
         </c:forEach>

        </div>
            
        </div>        
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
  <script>
    function goBack() {
      window.history.back();
    }
  </script>
</body>
</html>
