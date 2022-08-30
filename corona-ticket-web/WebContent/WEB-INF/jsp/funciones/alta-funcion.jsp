<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>CoronaTicket.uy :: Alta Función</title>

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
          <h1 style="margin: 15px" class="title">Alta de función de espectáculo</h1>
        </div>
        <form action="/corona-ticket-web/FuncionServlet"  class="tabcontent" method="POST" enctype="multipart/form-data" style="margin: 10px 110px">
          <div style="display: flex">
            <div id="col1"><label for="nombre">Nombre:</label><br>
              <input type="text" id="nombre" name="nombre_funcion" required><br><br>
              <label for="fecha">Fecha:</label><br>
              <input type="date" id="fecha" name="fecha_funcion" required><br><br>
              <label for="hora">Hora inicio:</label><br>
              <input type="time" id="hora" name="hora_funcion" required><br><br>
              <label for="imagen">Adjuntar Imagen:</label><br>
              <input type="file" id="imagen" name="imagen_funcion" style="border: none; background-color: inherit">
            </div>
            <div id="col2">
              <label>Artistas invitados:</label><br>
              <div style="display: flex; flex-direction: column">
              	<c:forEach items="${artistaList}" var="artistaInvitado">
	                <span class="alta-input" style="flex-direction: row; justify-content: flex-start; align-items: center;">
	                  <input style="margin-right: 10px" type="checkbox" id="artistaInvitado" name="artistaInvitado" value="${artistaInvitado.nickname}">
	                  <label style="margin-bottom: 0" for="artistaInvitado">${artistaInvitado.nickname}</label>
	                </span>
	            </c:forEach>
              </div>
              <br>
            </div>
          </div>
          <div style="display: flex; justify-content: flex-end; align-items: center">
            <button type="submit" class="btn btn-primary button">Confirmar</button>
            <button class="btn btn-primary button-cancel" style="margin: 5px" onclick="goBack()">Cancelar</button>
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
