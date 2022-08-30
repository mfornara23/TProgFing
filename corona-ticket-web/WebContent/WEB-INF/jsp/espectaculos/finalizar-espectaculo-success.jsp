<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>CoronaTicket.uy :: Finalizar Espectáculo</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="styles/global.css" rel="stylesheet">

</head>

<body>

	<jsp:include page="/WEB-INF/jsp/template/header.jsp" />

	<div class="d-flex" id="wrapper">
		<!-- Sidebar -->
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
				<div align="center">
					<h1 class="subtitle">Se ha finalizado correctamente el espectáculo.</h1>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/template/footer.jsp" />


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