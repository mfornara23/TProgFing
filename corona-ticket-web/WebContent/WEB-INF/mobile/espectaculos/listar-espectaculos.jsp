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
  <link href="${pageContext.request.contextPath}/styles/mobile/global.css" rel="stylesheet">

  <!-- Custom styles for Espectaculos -->
  <link href="${pageContext.request.contextPath}/styles/mobile/espectaculos.css" rel="stylesheet">

</head>

<body>

  <jsp:include page="/WEB-INF/mobile/template/header.jsp" />
	<div class="main-container">
		<span class="title-container espectaculos-title">Espectáculos</span>
	<form action="/corona-ticket-web/EspectaculoServlet" method = "GET">
		<div class="filter-container">
		  <div class="filter">
			<label>Plataforma:</label><br>
			<select id="plataforma" name= "plat_espec" class="filter-input">
			  <option value="all" selected="selected">Todas</option>
			  <c:forEach items="${platformaList}" var="plataforma">
				<option value="${plataforma.nombre}">${plataforma.nombre}</option>
			  </c:forEach>
			</select>
		  </div>
		  <div class="filter">
			<label>Categorías:</label><br>
			<select id="categoria" name= "cats_espec" class="filter-input">
			  <option value="all" selected="selected">Todas</option>
			  <c:forEach items="${categoriaList}" var="categoria">
				<option value="${categoria.nombre}">${categoria.nombre}</option>
			  </c:forEach>
			</select>
		  </div>
		  <div>
			<button class="btn btn-primary button" type="submit">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" width="24px" height="24px">
					<path d="M0 0h24v24H0V0z" fill="none"/>
					<path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
				</svg>
			</button>
		  </div>
		</div>
	</form>
	<div class="espectaculos-container">
		<c:forEach items="${espectaculoList}" var="espectaculo">
			<a id="e_${fn:trim(fn:toLowerCase(espectaculo.nombre))}" class="espectaculo-row list-row" href="/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle">
				<img src="${pageContext.request.contextPath}${espectaculo.imagen}" alt="logo" width="35%">
				<span class="espectaculo-row-data">${espectaculo.nombre}</span>
			</a>
		</c:forEach>
	</div>
	</div>
  
<jsp:include page="/WEB-INF/mobile/template/footer.jsp" />
  <!-- /Fin -->

  <!-- Bootstrap core JavaScript -->
  <script src="../../../vendor/jquery/jquery.min.js"></script>
  <script src="../../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>
</html>
