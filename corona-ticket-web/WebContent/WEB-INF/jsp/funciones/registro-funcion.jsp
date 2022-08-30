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

  <title>CoronaTicket.uy :: Registro a Función</title>

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
          <h1 style="margin: 15px" class="title">Registro a función de espectáculo</h1>
        </div>
        <form action="/corona-ticket-web/RegistroFuncionServlet" class="tabcontent" method="POST" style="margin: 10px 110px; min-height: 0px">
          <div style="display: flex">
            <div id="col1">
              <div style="display: flex">
				<img src="${pageContext.request.contextPath}${funcion.imagen}" alt="logo" width="120" style="border-radius: 4px">
				<div style="display: flex;
							flex-direction: column;
							padding: 10px 40px;">
					<span style="font-weight: 600; font-size: 22px;">${funcion.nombre}</span>
				</div>
			  </div>
			  <div style="margin: 20px 0px">
             	<span style="font-weight: 600">Descuento:</span>
              	<span style="margin: 0 60px; font-weight: 600; font-size: 20px">Se aplicará el descuento del paquete seleccionado</span>
              </div>
              <div style="font-weight: 600; margin: 20px 0px">
             	<span style="font-weight: 600">Costo:</span>
              	<span style="margin: 0px 100px; font-weight: 600; font-size: 20px">$${espectaculo.costo} (En caso de canje el costo será $0)</span>
              </div>
              <div style="margin: 20px 0px">
                <button type="submit" class="btn btn-primary button">Confirmar</button>
                <button class="btn btn-primary button-cancel" type="button" onclick="goBack()">Cancelar</button>
              </div>
            </div>
            <div id="col2">
              <label for="comprados">Paquetes comprados:</label><br>
              <select id="comprados" name= "nombre_paquete" class="paquetes">
              	  <option value="none">Ninguno</option>
				  <c:forEach items="${paqueteList}" var="paquete">
				  	<option value="${paquete.nombre}">${paquete.nombre}</option>
				  </c:forEach>
	          </select>
              <br><br>
              <label>Seleccione 3 registros a canjear:</label><br>
              <div style="display: flex; flex-direction: column">
              	<c:forEach items="${registroList}" var="registro">
	                <span class="alta-input" style="flex-direction: row; justify-content: flex-start; align-items: center;">
	                  <input style="margin-right: 10px" type="checkbox" name="registro" value="${registro.identificador}">
	                  <label style="margin-bottom: 0" for="registro">${registro.identificador}</label>
	                </span>
	            </c:forEach>
              </div>
              <br>
            </div>
          </div>
        </form>
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
