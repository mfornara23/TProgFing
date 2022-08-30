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

<title>CoronaTicket.uy :: Alta Espectáculo</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/styles/global.css"
	rel="stylesheet">

<!-- Custom styles for this Espectaculos -->
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
			<div class="main-alta-container">
				<div class="alta-title">
					<div onclick="goBack()" id="go-back">
						<svg width="1em" height="1em" viewBox="0 0 16 16"
							class="bi bi-chevron-left" fill="currentColor"
							xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
								d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" />
            </svg>
						Ir atrás
					</div>
					<h1 style="font-size: 24px">Alta Espectáculo</h1>
				</div>
				<form action="/corona-ticket-web/EspectaculoServlet" method="POST"
					enctype="multipart/form-data" style="width: 100%">
					<div class="alta-sub-container">
						<div id="col1"
							style="width: 40%; display: flex; flex-direction: column; padding: 20px;">
							<span class="alta-input"> <label for="nombre">Nombre:</label>
								<input type="text" id="nombre" name="nombre_espec" required>
							</span> <span class="alta-input"> <label for="descripcion">Descripción:</label>
								<input type="text" id="descripcion" name="descripcion_espec"
								required>
							</span> <span class="alta-input"> <label for="descripcion">Espectadores
									min.:</label> <input type="number" id="espectadores_min"
								name="espec_min" required>
							</span> <span class="alta-input"> <label for="descripcion">Espectadores
									max.:</label> <input type="number" id="espectadores_max"
								name="espec_max" required>
							</span> <span class="alta-input"> <label for="descripcion">URL:</label>
								<input type="text" id="url" name="url_espec" required>
							</span> <span class="alta-input"> <label for="descripcion">Costo:</label>
								<input type="number" id="costo" name="costo_espec" required>
							</span> <span class="alta-input"> <label for="duracion">Duración:</label>
								<input type="number" id="duracion" name="duracion_espec"
								required>
							</span> <span class="alta-input"> <label for="descripicionPremios">Descripcion de premios:</label>
								<input type="text" id="premios" name="desc_premios"
								required>
							</span> <span class="alta-input"> <label for="cantidadPremios">Cantidad de premios:</label>
								<input type="number" id="cantidadpremios" name="cant_premios"
								required>
							</span> <span class="alta-input"> <label for="video">URL Video:</label>
								<input type="text" id="video" name="video_espec">												
							</span> <span class="alta-input"> <label for="imagen">Adjuntar
									Imagen:</label> <input type="file" id="imagen" name="imagen_espec"
								style="border: none; background-color: inherit">
							</span>
						</div>
						<div id="col2"
							style="width: 40%; display: flex; flex-direction: column; padding: 20px; justify-content: space-evenly">
							<div>
								<span class="alta-input"> <label for="plataforma">Plataforma:</label>
									<select name="plataforma_espec" id="plataforma"
									style="height: 30px;">
										<c:forEach items="${platformaList}" var="plataforma">
											<option value="${plataforma.nombre}">${plataforma.nombre}</option>
										</c:forEach>
								</select>
								</span>
							</div>
							<div style="display: flex; flex-direction: column;">
								<label>Categorías:</label>
								<c:forEach items="${categoriaList}" var="categoria">
									<span class="alta-input"
										style="flex-direction: row; justify-content: flex-start; align-items: center;">
										<input type="checkbox"
										id="c_${fn:trim(fn:toLowerCase(categoria.nombre))}"
										name="cats" value="${categoria.nombre}"> <label
										style="padding: 10px; margin: 0"
										for="c_${fn:trim(fn:toLowerCase(categoria.nombre))}">${categoria.nombre}</label>
									</span>
								</c:forEach>
							</div>
						</div>
					</div>
					<span
						style="display: flex; justify-content: flex-end; align-items: center">
						<button type="submit" class="btn btn-primary button"
							data-tog\	le="modal">Confirmar</button>
						<button class="btn btn-primary button-cancel" type="button"
							onclick="goBack()" style="margin: 15px">Cancelar</button>
					</span>
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
