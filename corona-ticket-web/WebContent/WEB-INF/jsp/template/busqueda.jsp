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

<title>CoronaTicket.uy :: Espectáculo y Paquetes</title>

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

			<div>
				<form action="search" method="GET">
					<div style="display: flex; align-items: flex-end">
						<div style="margin-right: 10px">
							<label>Plataforma:</label><br> <select id="plataforma"
								name="plataformafilter" class="plataforma">
								<option value="" selected="selected">Todas</option>
								<c:forEach items="${plataformas}" var="plataforma">
									<option value="${plataforma}">${plataforma}</option>
								</c:forEach>
							</select>
						</div>
						<div style="margin-right: 10px">
							<label>Categorías:</label><br> <select id="categoria"
								name="categoriafilter" class="categorias">
								<option value="" selected="selected">Todas</option>
								<c:forEach items="${categorias}" var="categoria">
									<option value="${categoria}">${categoria}</option>
								</c:forEach>
							</select>
						</div>
						<div style="margin-right: 10px">
							<label>Ordenar:</label><br> <select id="sort" name="sort">
								<option value="" selected="selected">Ordenar</option>
								<option value="fecha">fecha de ingreso</option>
								<option value="alfabetica">alfabéticamente</option>
							</select>
						</div>

						<input type="hidden" name="key" value="${key}" />

						<div>
							<button class="btn btn-primary button" type="submit">Buscar</button>
						</div>
					</div>
				</form>
			</div>
			<br>
			<div class="title-container">
				<h1 class="title">Espectáculos</h1>
			</div>

			<c:choose>
				<c:when test="${empty espectaculos}">
					<span>No se encontraron espectáculos</span>
				</c:when>
				<c:otherwise>
					<c:forEach items="${espectaculos}" var="espectaculo">
						<a class="espectaculo-row list-row"
							href="/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle">
							<img src="${pageContext.request.contextPath}${espectaculo.imagen}" alt="logo" width="36">
							<span class="espectaculo-row-data">${espectaculo.nombre}</span> <span
							class="espectaculo-row-data">${espectaculo.artista.nombre}
								${espectaculo.artista.apellido}</span> <span
							class="espectaculo-row-data">${espectaculo.costo}$</span> <span
							class="espectaculo-row-data">${espectaculo.plataforma.nombre}</span>
							<span class="espectaculo-row-data"><c:forEach
									items="${espectaculo.categorias}" var="categoria">
          	${categoria}   
          	</c:forEach> </span>

						</a>
					</c:forEach>
				</c:otherwise>
			</c:choose>

			<br> <br>

			<div class="title-container">
				<h1 class="title">Paquetes</h1>
			</div>

			<c:choose>
				<c:when test="${empty paquetes}">
			<span>No se encontraron paquetes</span>
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

		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/template/footer.jsp" />
</body>
</html>
