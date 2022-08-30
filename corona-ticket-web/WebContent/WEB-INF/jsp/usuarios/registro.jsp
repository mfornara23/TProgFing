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

  <title>CoronaTicket.uy :: Registro</title>

  <!-- Bootstrap core CSS -->
  <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/styles/global.css" rel="stylesheet">

  <!-- Custom styles for Usuarios -->
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

    <div class="main-alta-container">
      <div class="alta-title" style="width: 100%;">
        <div onclick="goBack()" id="go-back">
          <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-left" fill="currentColor"
               xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd"
                  d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
          </svg>
          Ir atrás
        </div>
        </div>
        <h1 style="font-size: 24px">Registro de Usuario</h1>
              
        
        <form action="/corona-ticket-web/UsuarioServlet?page=crear" method="POST"
            enctype="multipart/form-data" style="width: 100%">
 
        <span class="alta-input" style="flex-direction: row; justify-content: flex-start; align-items: center;">
			  <label style="padding: 10px; margin: 0;">Espectador</label>
			  <input id="espectador" type="checkbox" name="espectador" style="cursor: pointer;" checked onchange="validateInput(event)">
			  <label style="padding: 10px; margin: 0">Artista</label>
			  <input id="artista" type="checkbox" name="artista" style="cursor: pointer;" onchange="validateInput(event)">	  
		  </span>
        <div class="alta-sub-container">
          <div id="col1" style="width: 40%; display: flex; flex-direction: column; padding: 20px;">
              	<span class="alta-input">
                  <label for="nickname">Nickname:</label>
                  <input type="text" id="nickname" name="nickname" required>
                </span>
            <span class="alta-input">
                  <label for="nombre">Nombre:</label>
                  <input type="text" id="nombre" name="nombre" required>
                </span>
            <span class="alta-input">
                  <label for="apellido">Apellido:</label>
                  <input type="text" id="apellido" name="apellido" required>
                </span>
            <span class="alta-input">
                  <label for="email">Email:</label>
                  <input type="text" id="email" name="email" required>
                <span class="registrationFormAlert" style="color:green;" id="checkEmailMatch"></span>
                </span>
          </div>
          <div id="col2" style="width: 40%; display: flex; flex-direction: column; padding: 20px;">
              	<span class="alta-input">
                  <label for="fecha">Fecha de nacimiento:</label>
                  <input type="date" id="fecha" name="fecha" required>
                </span>
            <span class="alta-input" style="display: flex; flex-direction: column;">
                  <span>
                  	<label for="password">Contraseña:</label><br>
                  	<input type="password" id="password" name="password" required><br>
                	<label for="password2">Confirmar contraseña:</label><br>
                  	<input type="password" id="password2" name="password2" required>
                      <span class="registrationFormAlert" style="color:green;" id="CheckPasswordMatch"></span>
                  </span>
                </span>
            <span class="alta-input">
                  <label for="imagen">Adjuntar Imagen:</label>
                  <input type="file" id="imagen" name="imagen" style="border: none; background-color: inherit">
                </span>
          </div>
        </div>
        <div id="artista-info" class="alta-sub-container" style="visibility: hidden;">
          <div id="col3" style="width: 40%; display: flex; flex-direction: column; padding: 20px;">
	          	<span class="alta-input">
	            	<label for="descripcion">Descripción general:</label>
	            	<textarea id="descripcion" name="descripcion" cols="40" rows="5"></textarea>
	          	</span>
          </div>
          <div id="col4" style="width: 40%; display: flex; flex-direction: column; padding: 20px;">
	          	<span class="alta-input">
	            	<label for="biografia">Biografía:</label>
	            	<textarea id="biografia" name="biografia" cols="40" rows="5"></textarea>
	          	</span>
            <span class="alta-input">
	            	<label for="url">Sitio web:</label>
	            	<input type="text" id="url" name="url">
	          	</span>
          </div>
        </div>
        <span style="display: flex; justify-content: flex-end">
            <button type="submit" class="btn btn-primary button" style="margin: 15px; padding: 5px 25px; height: 36px">Confirmar</button>
            <button class="btn btn-primary button-cancel" type="button" onclick="goBack()" style="margin: 15px; padding: 5px 25px;">Cancelar</button>
          </span>
      </form>
    </div>
  </div>
  </div>
  <!-- Footer -->
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
  <script>
    function handleConfirmar(e) {
      e.preventDefault();
      window.location.href = "listar-espectaculos.html";
    }
  </script>
  <script>
    function validateInput(e) {
      const artista = document.getElementById("artista").checked;
      if (artista) {
        document.getElementById("artista-info").style.visibility = "visible";
        document.getElementById("espectador").checked = false;
      } else {
        document.getElementById("artista-info").style.visibility = "hidden";
        document.getElementById("espectador").checked =true;

      }
    }
  </script>
  <script>
      function checkPasswordMatch() {
          var password = $("#password").val();
          var confirmPassword = $("#password2").val();
          if (password !== confirmPassword) {
              $("#CheckPasswordMatch").html("¡Las contraseñas no coinciden!");
              document.getElementById("CheckPasswordMatch").style.color = "red";
          }
          else {
              $("#CheckPasswordMatch").html("Correcto");
              document.getElementById("CheckPasswordMatch").style.color = "green";
          }
      }
      $(document).ready(function () {
          $("#password2").keyup(checkPasswordMatch);
      });
  </script>
  <script>
      $("#email").keyup(function() {
          var soapMessage = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pub="http://publicador.ws.coronatickets.tpgr06/"><soapenv:Header/><soapenv:Body><pub:getUsuarioByEmail><arg0>eleven11@gmail.com</arg0></pub:getUsuarioByEmail></soapenv:Body></soapenv:Envelope>';
          var username = $(this).val().trim();
          console.dir(username)
          if(username !== ''){
              soapMessage.replace("${email}",username)
              $.ajax({
                  url: 'http://localhost:8083/coronaservices',
                  type: 'GET',
                  dataType: "xml",
                  data: soapMessage,
                  contentType: "text/xml",
                  success: function(response){
                      $('#checkEmailMatch').html(response);
                      document.getElementById("checkEmailMatch").style.color = "red";

                  }
              });
          } else {
              $("#checkEmailMatch").html("");
          }
      })
  </script>
</body>
</html>
