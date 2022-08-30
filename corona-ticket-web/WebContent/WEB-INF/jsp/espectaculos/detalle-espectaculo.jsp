<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>CoronaTicket.uy :: Detalle Espectáculo</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/styles/global.css"
	rel="stylesheet">

<!-- Custom styles for Espectaculos -->
<link href="${pageContext.request.contextPath}/styles/espectaculos.css"
	rel="stylesheet">

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
					<div onclick="goBack()" id="go-back">
						<svg width="1em" height="1em" viewBox="0 0 16 16"
							class="bi bi-chevron-left" fill="currentColor"
							xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
								d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" />
            </svg>
						Ir atrás
					</div>
					<h1 class="title">Detalle Espectáculo</h1>
				</div>
				<div id="main">
					<div id="left-col">
						<div style="display: flex">
							<img
								src="${pageContext.request.contextPath}${espectaculo.imagen}"
								alt="logo" width="200">
							<div
								style="display: flex; flex-direction: column; padding: 20px 100px;">
								<span style="font-weight: 600; font-size: 24px;">${espectaculo.nombre}</span>
								<span style="font-style: italic">${espectaculo.plataforma.nombre}</span>
							</div>
							<div style="display: flex; justify-content: flex-end; width: 80%; visibility: ${finalizarVisible};">
        						<button class="btn btn-primary button" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?page=finalizar&nombre_espec=${espectaculo.nombre}'">Finalizar</button>
        					</div>
						</div>
						<div class="espectaculo-data-container">
							<span style="font-weight: 600">URL</span> <span>${espectaculo.url}</span>
						</div>
						<div class="espectaculo-data-container">
							<span style="font-weight: 600">Fecha de Registro</span> <span><fmt:formatDate
									pattern="dd/MM/yyyy" value="${espectaculo.fechaReg.toGregorianCalendar().time}" /></span>
						</div>
						<div class="espectaculo-data-container">
							<span style="font-weight: 600">Duración</span> <span>${espectaculo.duracion}
								minutos</span>
						</div>
						<div class="espectaculo-data-container">
							<span style="font-weight: 600">Descripción</span>
							<span style="font-size: 14px">
								${espectaculo.descripcion}</span>
						</div>
						<div style="display: flex; align-items: center">
							<span style="font-weight: 600">Espectadores</span>
							<div class="espectaculo-data-container"
								style="justify-content: space-evenly">
								<span style="font-weight: 600">Mínimo</span> <span>${espectaculo.cantMin}</span>
							</div>
							<div class="espectaculo-data-container"
								style="justify-content: space-evenly">
								<span style="font-weight: 600">Máximo</span> <span>${espectaculo.cantMax}</span>
							</div>
						</div>
						<div class="espectaculo-data-container">
							<span style="font-weight: 600">Descripción Premio</span>
							<span style="font-size: 14px; text-align: right">
								${espectaculo.descripcionPremios}</span>
						</div>
						<div class="espectaculo-data-container">
							<span style="font-weight: 600">Cantidad Premios</span>
							<span style="font-size: 14px">
								${espectaculo.cantidadPremios}</span>
						</div>
						<div class="espectaculo-data-container" style="width: 50%">
							<span
								style="font-weight: 600; font-size: 23px; align-self: center;">Costo</span>
							<span style="font-weight: 600; font-size: 30px">$${espectaculo.costo}</span>
						</div>
						<div class="espectaculo-data-container" style="width: 50%">
							<span
								style="font-weight: 600; font-size: 23px; align-self: center;">Favoritos</span>
							<span style="font-weight: 600; font-size: 30px">${espectaculo.espectadoresFavoritos.size()}</span>
						</div>
						<div class="espectaculo-data-container" style="width: 50%; visibility:${favoritoVisible};">
					      <c:choose>
					          <c:when test="${esFavorito}">
					          <form action="EspectaculoServlet" method="post">
					            <button class="btn btn-primary button">Quitar Favorito</button>
					            <input type="hidden" name="nombre_espec" value="${espectaculo.nombre}">
					            <input type="hidden" name="fav" value="false">
					            <input type="hidden" name="detalle" value="true">	            
					            </form>
					          </c:when>
					          <c:otherwise>
					          	<form action="EspectaculoServlet" method="post">
					            <button class="btn btn-primary button">Agregar Favorito</button>
					            <input type="hidden" name="nombre_espec" value="${espectaculo.nombre}">
					            <input type="hidden" name="fav" value="true">	  
					            <input type="hidden" name="detalle" value="true">	            					                
					            </form>
					          </c:otherwise>
					       </c:choose>						
						</div>	
											
						<div class="espectaculo-data-container" style="width: 50%">
							<c:choose>
								 <c:when test="${hayValoracionPromedio}">
					        		<div>
							 			<span style="font-weight: 600">Valoracion Promedio: </span> <span><fmt:formatNumber type="number" maxFractionDigits="2" value="${valorPromedio}"/></span>
							 			<br>
							 		</div>
							 	</c:when>
					            <c:otherwise>
					        		<div>
							 			<span style="font-weight: 600">Aun no hay Valoraciones</span><br>
							 		</div>							
					          	</c:otherwise>
							 </c:choose>
							 
							 <c:choose>
							 	<c:when test="${puedeValorar}">
						     <c:choose>
						         <c:when test="${hayValoracionPersonal}">
						         	<div>
										<span style="font-weight: 600">Valoracion Personal: </span> <span>${valorPersonal}</span>
									</div>
						         </c:when>
						          <c:otherwise>
						          	<form action="EspectaculoServlet" method="post">
											<div style="margin-right: 10px">
									        	<label>Valorar: </label>
									            <select name= "puntajeEspectaculo">
												  	<option value=1>1</option>
												  	<option value=2>2</option>
												  	<option value=3>3</option>
												  	<option value=4>4</option>
												  	<option value=5>5</option>
									         	</select>
									         </div>
									    <input type="hidden" name="nombre_espec" value="${espectaculo.nombre}">
						            	<input type="hidden" name="espectaculoValorado" value="true">
	           					        <button class="btn btn-primary button">Valorar</button>
						            </form>
						          </c:otherwise>
					       	  </c:choose>
					       	  </c:when>	
					       </c:choose>
					       	  					
						</div>						
						
					</div>
					
					<div id="right-col">
						<div>
							<span style="font-weight: 600">Funciones</span><br>
							<c:forEach items="${funciones}" var="funcion">
								<button class="span_right_item"
									style="text-align: initial; background-color: white;"
									onclick="window.location='FuncionServlet?nombre_espec=${espectaculo.nombre}&nombre_funcion=${funcion.nombre}&page=detalle'">${funcion.nombre}</button>
							</c:forEach>
						</div>
						<div>
							<span style="font-weight: 600">Paquetes</span><br>
							<c:forEach items="${paquetes}" var="paquete">
								<button class="span_right_item"
									style="text-align: initial; background-color: white;"
									onclick="window.location='/corona-ticket-web/PaqueteServlet?nombre_paquete=${paquete.nombre}'">${paquete.nombre}</button>
							</c:forEach>
						</div>
						<div>
							<span style="font-weight: 600">Categorias</span><br>
							<c:forEach items="${categorias}" var="categoria">
								<span class="span_right_item">${categoria}</span>
							</c:forEach>
						</div>
						<div>
   							<iframe src="${espectaculo.video}" allowfullscreen></iframe>
						</div>
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
      console.dir("go-back-click");
      window.history.back();
    }
  </script>
</body>
</html>
