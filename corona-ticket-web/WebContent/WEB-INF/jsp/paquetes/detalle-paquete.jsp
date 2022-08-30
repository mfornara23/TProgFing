<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Ver Paquete</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href=".${pageContext.request.contextPath}/styles/global.css"
	rel="stylesheet">

<!-- Custom styles for paquetes -->
<link href="${pageContext.request.contextPath}/styles/paquetes.css"
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
				<div class="header-container">
					<div class="alta-title"
						style="justify-content: flex-start; align-items: center;">
						<div onclick="goBack()" id="go-back">
							<svg width="1em" height="1em" viewBox="0 0 16 16"
								class="bi bi-chevron-left" fill="currentColor"
								xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
									d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" />
              </svg>
							Ir atrás
						</div>
						<h1>Detalle de paquete</h1>
					</div>
				</div>
				<div id="main">
					<div id="left-col">
						<div style="display: flex; margin-bottom: 20px">
							<img src="${pageContext.request.contextPath}${paquete.imagen}"
								alt="logo" width="120">
							<div
								style="display: flex; flex-direction: column; padding: 20px 100px;">
								<span style="font-weight: 600; font-size: 24px;">${paquete.nombre}</span>
								<div class="status-badge ${cssStatus}">${vigencia}</div>
							</div>
						</div>
						<div class="paquete-data-container">
							<span style="font-weight: 600">Período de Vigencia</span> <span><fmt:formatDate
									pattern="dd/MM/yyyy" value="${paquete.fechaInicio.toGregorianCalendar().time}" /> - <fmt:formatDate
									pattern="dd/MM/yyyy" value="${paquete.fechaFin.toGregorianCalendar().time}" /></span>
						</div>
						<div class="paquete-data-container">
							<span style="font-weight: 600">Descripción</span>
							<p style="font-size: 14px; width: 400px;">${paquete.descripcion}</p>
						</div>
						<div class="paquete-data-container" style="width: 50%">
							<span style="font-weight: 600">Descuento</span> <span
								style="font-weight: 600">${paquete.descuento}%</span>
						</div>
					</div>
					<div id="right-col">
						<div>
							<span style="font-weight: 600">Espectáculos</span><br>
							<c:forEach items="${espectaculos}" var="espectaculo">
						  		<button class="span_right_item" style="text-align: initial; background-color: white;"
									onclick="window.location='/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle'"> ${espectaculo.nombre}</button>
						    </c:forEach>
						    
													
							
							
							


							<div style="display: flex; justify-content: flex-end; width: 80%; visibility: ${agregarEspectaculoVisible};">
        					<button class="btn btn-primary button" 
        					onclick="window.location.href='/corona-ticket-web/PaqueteServlet?page=agregar&paq=${paquete.nombre}'">Agregar Espectáculo</button>
       						</div>
							
							
		
		
		  
						</div>

						<div>
							<span style="font-weight: 600">Categorías</span><br>
								<c:forEach items="${categorias}" var="categoria">
									<span class="span_right_item">${categoria.nombre}</span>
							    </c:forEach>
						</div>
					</div>
				</div>
			     <div style="display: flex; justify-content: flex-end; width: 80%; visibility: ${compraVisible};">
 
		        <form action="/corona-ticket-web/PaqueteServlet" method = "POST">
			          	 <input type="hidden" name="compra" value="true">
			          	 <input type="hidden" name="paquete_name" value="${paquete.nombre}">
			          	 
			          	<button class="btn btn-primary button" type="submit">Comprar Paquete</button>		          	
        		</form>
        		 </div>
				
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
