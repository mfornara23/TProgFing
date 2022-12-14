<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>CoronaTicket.uy :: Usuarios</title>

  <!-- Bootstrap core CSS -->
  <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/styles/global.css" rel="stylesheet">

  <!-- Custom styles for Espectaculos -->
  <link href="${pageContext.request.contextPath}/styles/usuarios.css" rel="stylesheet">

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
          <h1 class="title">Usuarios</h1>
        </div>
        <c:forEach items="${requestScope.usuarios}" var="usuario">
          <div id="${usuario.nickname}" onclick="window.location.href='/corona-ticket-web/UsuarioServlet?page=perfil&nickname=${usuario.nickname}'" class="user-row list-row">
            <img src="${pageContext.request.contextPath}${usuario.imagen}" alt="logo" width="36">
            <span class="user-row-data">${usuario.nombre} ${usuario.apellido}</span>
            <span class="user-row-data">${usuario.nickname}</span>
            <span class="user-row-data">${usuario.email}</span>
            <span></span>
            <span class="user-row-data"></span>
            <span onclick="" style="justify-self: center;">
          </span>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
  <!-- Footer -->
  <jsp:include page="/WEB-INF/jsp/template/footer.jsp" />
  <!-- Modal -->
  <button id="triggerModal" type="button" class="btn btn-primary" data-toggle="modal" data-target="#followUser"></button>
  <div class="modal fade" id="followUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">?Desea seguir a este usuario?</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">Confirmar</button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
        </div>
      </div>
    </div>
  </div>
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