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

  <title>CoronaTicket.uy :: Espectáculos</title>

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
        <div class="title-container" style="align-items: flex-end;justify-content: space-between;padding: 0 50px 0 0;">
          <h1 class="title">Espectáculos</h1>
          <div style="display: flex; justify-content: flex-end; width: 80%; visibility: ${altaVisible};">
        	<button class="btn btn-primary button" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?page=finalizar'">Ver finalizados</button>
          </div>
          <div style="display: flex; justify-content: flex-end; width: 80%; visibility: ${valorarVisible};">
        	<button class="btn btn-primary button" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?page=valorar'">Valorar Espectaculos</button>
          </div>
          <div style="display: flex; justify-content: flex-end; width: 80%; visibility: ${altaVisible};">
        	<button class="btn btn-primary button" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?page=alta'">Nuevo Espectáculo</button>
          </div>
        </div>
        <form action="/corona-ticket-web/EspectaculoServlet" method = "GET">
	        <div style="display: flex; align-items: flex-end">
	          <div style="margin-right: 10px">
	            <label>Plataforma:</label><br>
	            <select id="plataforma" name= "plat_espec" class="plataforma">
	              <option value="all" selected="selected">Todas</option>
				  <c:forEach items="${platformaList}" var="plataforma">
				  	<option value="${plataforma.nombre}">${plataforma.nombre}</option>
				  </c:forEach>
	            </select>
	          </div>
	          <div style="margin-right: 10px">
	            <label>Categorías:</label><br>
	            <select id="categoria" name= "cats_espec" class="categorias">
	              <option value="all" selected="selected">Todas</option>
	              <c:forEach items="${categoriaList}" var="categoria">
				  	<option value="${categoria.nombre}">${categoria.nombre}</option>
				  </c:forEach>
	            </select>
	          </div>
	          <div>
	          	<button class="btn btn-primary button" type="submit">Buscar</button>
	          </div>
	        </div>
        </form>
        <c:forEach items="${espectaculosWrapper}" var="wrapper">
	        <div style="padding: 3px 0;">
	          <div
					  id="e_${fn:trim(fn:toLowerCase(wrapper.espectaculo.nombre))}"
					  class="espectaculo-row list-row"
					  onclick="event.stopPropagation();window.location.href='/corona-ticket-web/EspectaculoServlet?nombre_espec=${wrapper.espectaculo.nombre}&page=detalle'">
	            <img src="${pageContext.request.contextPath}${wrapper.espectaculo.imagen}" alt="logo" width="36">
	            <span class="espectaculo-row-data">${wrapper.espectaculo.nombre}</span>
	            <span class="espectaculo-row-data">${wrapper.espectaculo.artista.nombre} ${wrapper.espectaculo.artista.apellido}</span>
	            <span class="espectaculo-row-data">${wrapper.espectaculo.plataforma.nombre}</span>
				  <c:choose>
					  <c:when test="${wrapper.esFavorito}">
						  <form action="EspectaculoServlet" method="post">
							  <button class="btn btn-outline-primary">
								  <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-heart-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
									  <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
								  </svg>
							  </button>
							  <input type="hidden" name="nombre_espectaculo" value="${wrapper.espectaculo.nombre}">
							  <input type="hidden" name="fav" value="false">
						  </form>
					  </c:when>
					  <c:otherwise>
						  <form action="EspectaculoServlet" method="post">
							  <button class="btn btn-outline-primary">
								  <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-heart" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
									  <path fill-rule="evenodd" d="M8 2.748l-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
								  </svg>
							  </button>
							  <input type="hidden" name="nombre_espectaculo" value="${wrapper.espectaculo.nombre}">
							  <input type="hidden" name="fav" value="true">
						  </form>
					  </c:otherwise>
				  </c:choose>
	       	</div>


	        </div>
	        <br>
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
