<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="usuario" scope="request" type="tpgr06.coronatickets.ws.publicador.UsuarioDTO"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>CoronaTicket.uy :: Ver Perfil</title>

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
          <div onclick="goBack()" id="go-back">
            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-chevron-left" fill="currentColor"
                 xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
                    d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
            </svg>
            Ir atrás
          </div>
          <h1 class="title">Información de usuario</h1>
        </div>
        <div style="margin: 10px 90px">
          <div class="tab">
            <button class="tablinks" onclick="openTab(event, 'Perfil')">Perfil</button>
            <button class="tablinks" onclick="openTab(event, 'Social')">Social</button>
            <c:if test = "${requestScope.tipoUsuario != null && requestScope.tipoUsuario == 'espectador'}">
              <button class="tablinks" onclick="openTab(event, 'Funciones')">Funciones</button>
              <c:if test = "${requestScope.miPerfil != null && requestScope.miPerfil == true}">
                <button class="tablinks" onclick="openTab(event, 'Paquetes')">Paquetes</button>
                <button class="tablinks" onclick="openTab(event, 'Premios')">Premios</button>                
              </c:if>
            </c:if>
            <c:if test = "${requestScope.tipoUsuario != null && requestScope.tipoUsuario == 'artista'}">
              <button class="tablinks" onclick="openTab(event, 'Espectaculos')">Espectáculos</button>
            </c:if>
          </div>
          <div id="Perfil" class="tabcontent" style="display: block;">
            <div style="display: flex">
              <img src="${pageContext.request.contextPath}${usuario.imagen}" alt="logo" width="200">
              <div style="display: flex;
                      flex-direction: column;
                      padding: 20px 100px;"
              >
                <span style="font-weight: 600; font-size: 24px;">${usuario.nombre}</span>
                <span style="font-style: italic">${usuario.nickname}</span>
              </div>
 				<c:if test="${requestScope.visitante==false && sessionScope.email != usuario.email}">
            
              		<div>
 				        <form action="UsuarioServlet" method="post">   
							<input type="hidden" name="nickname" value="${usuario.nickname}"/>
 				        
 				    <c:choose>
            		
				    	<c:when test="${requestScope.soySeguidor != null && requestScope.soySeguidor == false}">	       
	          				<input type="hidden" name="seguir" value="seguir"/>
	          		<button class="btn btn-primary button" type="submit">Seguir</button>
				  
				    	</c:when>    
				    <c:otherwise>
	          				<input type="hidden" name="seguir" value="dejardeseguir"/>
	          				
	          		<button class="btn btn-primary button" type="submit">Dejar de Seguir</button>
	          				
				    </c:otherwise>
				</c:choose>
				        </form>
				
              </div>
              </c:if>
            </div>
            <div class="perfil-data-container">
              <span style="font-weight: 600">Email</span>
              <span>${usuario.email}</span>
            </div>
            <div class="perfil-data-container">
              <span style="font-weight: 600">Fecha de nacimiento</span>
              <span><fmt:formatDate pattern = "dd/MM/yyyy" value = "${usuario.fechaNac.toGregorianCalendar().time}"/></span>
            </div>
            <c:if test = "${requestScope.tipoUsuario != null && requestScope.tipoUsuario == 'artista'}">
              <div class="perfil-data-container">
                <span style="font-weight: 600">Sitio Web</span>
                <span>${usuario.sitioWeb}</span>
              </div>
              <div class="perfil-data-container">
                <span style="font-weight: 600">Descripción General</span>
                <p style="font-size: 14px; width: 400px;">
                  ${usuario.descripcion}
                </p>
              </div>
            </c:if>
          </div>
          <div id="Social" class="tabcontent">
            <div style="display: flex; justify-content: space-between">
              <div class="seguidores-seguidos-container">
                <span class="seguidores-seguidos">Seguidores</span>
                <c:if test = "${usuario.seguidores != null && usuario.seguidores.size() > 0}">
                  <c:forEach items="${usuario.seguidores}" var="seguidor">
                    <div class="social-data-container">
                      <span onclick="window.location.href='/corona-ticket-web/UsuarioServlet?page=perfil&nickname=${seguidor}'" >${seguidor}</span>
                    </div>
                  </c:forEach>
                </c:if>
              </div>
              <div class="seguidores-seguidos-container">
                <span class="seguidores-seguidos">Seguidos</span>
                <c:if test = "${usuario.seguidos != null && usuario.seguidos.size() > 0}">
                  <c:forEach items="${usuario.seguidos}" var="seguido">
                    <div class="social-data-container">
                      <span onclick="window.location.href='/corona-ticket-web/UsuarioServlet?page=perfil&nickname=${seguido}'" >${seguido}</span>
                    </div>
                  </c:forEach>
                </c:if>
              </div>
            </div>
          </div>
          <c:if test = "${requestScope.tipoUsuario != null && requestScope.tipoUsuario == 'artista'}">
            <div id="Espectaculos" class="tabcontent">
              <c:if test = "${requestScope.miPerfil == null || requestScope.miPerfil == false}">
              <div style="display: flex; justify-content: center; padding: 50px;">
                <div class="seguidores-seguidos-container">
                  <span class="seguidores-seguidos">Espectáculos del artista</span>
                  <c:if test = "${requestScope.espectaculos != null && requestScope.espectaculos.size() > 0}">
                    <c:forEach items="${requestScope.espectaculos}" var="espectaculo">
                      <div class="social-data-container" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle'">
                        ${espectaculo.nombre}
                      </div>
                    </c:forEach>
                  </c:if>
                </div>
              </div>
              </c:if>
              <c:if test = "${requestScope.miPerfil != null && requestScope.miPerfil == true}">
                <div style="display: flex; justify-content: center; padding: 20px;">
                  <div class="seguidores-seguidos-container">
                    <span class="seguidores-seguidos">Espectáculos aprobados</span>
                    <c:forEach items="${requestScope.espectaculos}" var="espectaculo">
                      <c:if test = "${espectaculo.estado == 'ACEPTADO'}">
                        <div class="social-data-container" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle'" >
                          ${espectaculo.nombre}
                        </div>
                      </c:if>
                    </c:forEach>
                  </div>
                  <div class="seguidores-seguidos-container">
                    <span class="seguidores-seguidos">Espectáculos pendientes</span>
                    <c:forEach items="${requestScope.espectaculos}" var="espectaculo">
                      <c:if test = "${espectaculo.estado == 'INGRESADO'}">
                        <div class="social-data-container" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle'">
                            ${espectaculo.nombre}
                        </div>
                      </c:if>
                    </c:forEach>
                  </div>
                  <div class="seguidores-seguidos-container">
                    <span class="seguidores-seguidos">Espectáculos rechazados</span>
                    <c:forEach items="${requestScope.espectaculos}" var="espectaculo">
                      <c:if test = "${espectaculo.estado == 'RECHAZADO'}">
                        <div class="social-data-container" onclick="window.location.href='/corona-ticket-web/EspectaculoServlet?nombre_espec=${espectaculo.nombre}&page=detalle'">
                            ${espectaculo.nombre}
                        </div>
                      </c:if>
                    </c:forEach>
                  </div>
                </div>
              </c:if>
            </div>
          </c:if>
          <c:if test = "${requestScope.tipoUsuario != null && requestScope.tipoUsuario == 'espectador'}">
            <div id="Funciones" class="tabcontent">
              <div style="display: flex; justify-content: center; padding: 50px;">
                <div class="seguidores-seguidos-container">
                  <span class="seguidores-seguidos">Funciones registradas</span>
                  <c:forEach items="${requestScope.funciones}" var="funcion">
                    <div class="funciones-data-container">
                      ${funcion.nombre}
                    </div>
                  </c:forEach>
                </div>
              </div>
            </div>
          </c:if>
          <c:if test = "${requestScope.tipoUsuario != null && requestScope.tipoUsuario == 'espectador' && requestScope.miPerfil != null && requestScope.miPerfil == true}">
            <div id="Paquetes" class="tabcontent">
              <div style="display: flex; justify-content: center; padding: 50px;">
                <div class="seguidores-seguidos-container">
                  <span class="seguidores-seguidos">Paquetes Comprados</span>
                  <c:forEach items="${requestScope.paquetes}" var="paquete">
                    <div class="social-data-container" onclick="window.location.href='/corona-ticket-web/PaqueteServlet?nombre_paquete=${paquete.nombre}&page=detalle'">
                      ${paquete.nombre}
                    </div>
                  </c:forEach>
                </div>
              </div>
            </div>
            <div id="Premios" class="tabcontent">
              <div style="display: flex; justify-content: center; padding: 50px;">
                <div class="seguidores-seguidos-container">
                  <span class="seguidores-seguidos">Premios Ganados</span>
                  <c:forEach items="${requestScope.premios}" var="premio">
                    <div class="funciones-data-container">
                        ${premio.funcion}
                          <a href="/corona-ticket-web/pdf?nombre_espec=${premio.espectaculo}&nickname=${request}">Comprobante PDF</a></p>
                    </div>
                  </c:forEach>
                </div>
              </div>
            </div>            
          </c:if>
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
      console.dir("go-back-click")
      window.history.back();
    }
  </script>

  <script>
    function openTab(evt, tabName) {
      evt.preventDefault();
      var i, tabcontent, tablinks;
      tabcontent = document.getElementsByClassName("tabcontent");
      for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
      }
      tablinks = document.getElementsByClassName("tablinks");
      for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
      }
      document.getElementById(tabName).style.display = "block";
      evt.currentTarget.className += " active";
    }
  </script>
</body>
</html>
