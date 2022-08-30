<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>CoronaTicket.uy :: Error</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/styles/global.css"
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

			<!--Inicio: Insertar ac� el c�digo de la p�gina-->

			<div class="main-alta-container">
				<div class="alta-title">
					<div onclick="goBack()" id="go-back">
						<svg width="1em" height="1em" viewBox="0 0 16 16"
							class="bi bi-chevron-left" fill="currentColor"
							xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
								d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" />
            </svg>
						Ir atr�s
					</div>
					<h1 style="font-size: 24px">500 - Hubo un problema con tu solicitud </h1>
				</div>
				<p>
					Si quieres volver al inicio has click <a href="/corona-ticket-web/">aqu�</a>
				</p>
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
