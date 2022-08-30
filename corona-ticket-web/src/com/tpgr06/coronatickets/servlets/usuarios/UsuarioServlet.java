package com.tpgr06.coronatickets.servlets.usuarios;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.tpgr06.coronatickets.utils.ImgUtils;

import tpgr06.coronatickets.ws.publicador.ArtistaDTO;
import tpgr06.coronatickets.ws.publicador.BadRequestException_Exception;
import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;
import tpgr06.coronatickets.ws.publicador.EspectaculoDTO;
import tpgr06.coronatickets.ws.publicador.EspectadorDTO;
import tpgr06.coronatickets.ws.publicador.FuncionDTO;
import tpgr06.coronatickets.ws.publicador.NotFoundException_Exception;
import tpgr06.coronatickets.ws.publicador.PaqueteDTO;
import tpgr06.coronatickets.ws.publicador.UsuarioDTO;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/UsuarioServlet")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoronaServices port;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsuarioServlet() {
		super();
	}

	@Override
	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Boolean isMobile = isMobile(request);

		String page = request.getParameter("page");
		if (page == null) {
			page = "lista";
		}

		if (!isMobile) {
			switch (page) {
				case "modificar":
					getModificar(request, response, session);
					break;
				case "registro":
					getRegistro(request, response, session);
					break;
				case "lista":
					getLista(request, response, session);
					break;
				default:
					getPerfil(request, response, session);
			}
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * Recibe los parametros para registro de usuario o para seguir/dejar de seguir
	 * usuario
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String page = request.getParameter("page");

		String imgPath = "";
		boolean esArtista = "artista".equals(session.getAttribute("tipousuario"));
		boolean esArtistaAlta = request.getParameter("artista") != null;
		String seguir = request.getParameter("seguir");

		try {
			if (seguir != null) {
				seguirUsuario(request, response);

			} else {
				String nickname = request.getParameter("nickname");
				if (nickname == null) {
					nickname = (String) session.getAttribute("nickname");
				}
				String email = request.getParameter("email");
				if (email == null) {
					email = (String) session.getAttribute("email");
				}

				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");

				Date fechaNacimiento = new Date();
				fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha"));
				String password = request.getParameter("password");
				String confirmPassword = request.getParameter("password2");
				if (!password.equals(confirmPassword)) {
					throw new BadRequestException_Exception("Las claves deben coincidir", null);
				}

				String descripcion = "";
				String url = "";
				String biografia = "";
				if (esArtista) {
					descripcion = request.getParameter("descripcion");
					biografia = request.getParameter("biografia");
					url = request.getParameter("url");
				}

				if (!ServletFileUpload.isMultipartContent(request)) {
					request.setAttribute("errorMsg",
							"Hubo un error en el sistema, Content type is not multipart/form-data");
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
					dispatcher.forward(request, response);
				}

				Part filePart = request.getPart("imagen");
				if (filePart.getSize() <= 0) {
					imgPath = "/media/img/user-generico.png";
				} else {
					InputStream fileContent = filePart.getInputStream();
					imgPath = ImgUtils.saveFile(fileContent);
				}

				String tipoUsuario = "";
				UsuarioDTO usuarioDTO = null;

				switch (page) {
				case "modificar":
					if (esArtista) {
						ArtistaDTO artista = new ArtistaDTO();
						artista.setNickname(nickname);
						artista.setNombre(nombre);
						artista.setApellido(apellido);
						artista.setEmail(email);
						GregorianCalendar gregory = new GregorianCalendar();
						gregory.setTime(fechaNacimiento);
						XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
						artista.setFechaNac(calendar);
						artista.setPassword(password);
						artista.setDescripcion(descripcion);
						artista.setBio(biografia);
						artista.setSitioWeb(url);
						artista.setImagen(imgPath);
						tipoUsuario = "artista";
						usuarioDTO = artista;
						port.actualizarUsuario(artista);
					} else {
						EspectadorDTO espectador = new EspectadorDTO();
						espectador.setNickname(nickname);
						espectador.setNombre(nombre);
						espectador.setApellido(apellido);
						espectador.setEmail(email);
						GregorianCalendar gregory = new GregorianCalendar();
						gregory.setTime(fechaNacimiento);
						XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
						espectador.setFechaNac(calendar);
						espectador.setPassword(password);
						espectador.setImagen(imgPath);
						tipoUsuario = "espectador";
						usuarioDTO = espectador;
						port.actualizarUsuario(espectador);

					}
					break;
				case "crear":
					if (esArtistaAlta) {
						ArtistaDTO artista = new ArtistaDTO();
						artista.setNickname(nickname);
						artista.setNombre(nombre);
						artista.setApellido(apellido);
						artista.setEmail(email);
						GregorianCalendar gregory = new GregorianCalendar();
						gregory.setTime(fechaNacimiento);
						XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
						artista.setFechaNac(calendar);
						artista.setPassword(password);
						artista.setDescripcion(descripcion);
						artista.setBio(biografia);
						artista.setSitioWeb(url);
						artista.setImagen(imgPath);
						tipoUsuario = "artista";
						usuarioDTO = artista;
						port.altaUsuario(artista);
					} else {
						EspectadorDTO espectador = new EspectadorDTO();
						espectador.setNickname(nickname);
						espectador.setNombre(nombre);
						espectador.setApellido(apellido);
						espectador.setEmail(email);
						GregorianCalendar gregory = new GregorianCalendar();
						gregory.setTime(fechaNacimiento);
						XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
						espectador.setFechaNac(calendar);
						espectador.setPassword(password);
						espectador.setImagen(imgPath);
						tipoUsuario = "espectador";
						usuarioDTO = espectador;
						port.altaUsuario(espectador);

					}
					break;
				}

				session.setAttribute("email", email);
				session.setAttribute("nickname", nickname);
				session.setAttribute("tipousuario", tipoUsuario);
				request.setAttribute("miPerfil", true);
				request.setAttribute("usuario", usuarioDTO);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("WEB-INF/jsp/usuarios/registro-exitoso.jsp");
				dispatcher.forward(request, response);

			}

		} catch (Exception e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void seguirUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NotFoundException_Exception, BadRequestException_Exception {
		String seguirUsuario = request.getParameter("seguir");
		HttpSession session = request.getSession();
		if (seguirUsuario != null) {
			String nicknameASeguir = request.getParameter("nickname");
			String emailUsuarioLogueado = (String) session.getAttribute("email");
			UsuarioDTO usuarioLogueado = port.getUsuarioByEmail(emailUsuarioLogueado);

			if (seguirUsuario.equals("seguir")) {
				port.seguirUsuario(usuarioLogueado.getNickname(), nicknameASeguir);
			} else if (seguirUsuario.equals("dejardeseguir")) {
				port.dejarSeguirUsuario(usuarioLogueado.getNickname(), nicknameASeguir);
			}
			getPerfil(request, response, session);
		}

	}

	private void getRegistro(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		if (session.getAttribute("email") != null) {
			dispatchError(request, response, "No puedes ingresar aquí estando logueado", "");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/usuarios/registro.jsp");
		dispatcher.forward(request, response);
	}

	private void getPerfil(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		try {
			String tipoUsuario = "artista";
			UsuarioDTO usuarioDTO = port.getUsuarioByNickname(request.getParameter("nickname"));
			boolean esMiPerfil = false;
			boolean visitante = true;
			boolean soySeguidor = false;
			String emailUsuarioLogueado = (String) session.getAttribute("email");
			if (emailUsuarioLogueado != null) {
				UsuarioDTO usuarioLogueado = port.getUsuarioByEmail(emailUsuarioLogueado);
				esMiPerfil = usuarioDTO.getEmail().equals(emailUsuarioLogueado);
				soySeguidor = usuarioDTO.getSeguidores().stream()
						.anyMatch(usr -> usr.equals(usuarioLogueado.getNickname()));
				request.setAttribute("soySeguidor", soySeguidor);

				visitante = false;
			}

			if (usuarioDTO instanceof EspectadorDTO) {
				tipoUsuario = "espectador";
			}

			if (tipoUsuario.equals("artista")) {
				List<EspectaculoDTO> espectaculosDeArtista = port.listarEspectaculosByArtista(usuarioDTO.getNickname()).getEspectaculo();
				request.setAttribute("espectaculos", espectaculosDeArtista);
			} else {
				List<FuncionDTO> funciones = port.listarFuncionesByEspectador(usuarioDTO.getNickname()).getFuncion();
				request.setAttribute("funciones", funciones);
				if (esMiPerfil) {
					EspectadorDTO espectador = (EspectadorDTO) usuarioDTO;
					List<PaqueteDTO> paquetesComprados = new ArrayList<>(espectador.getPaquetes());
					request.setAttribute("paquetes", paquetesComprados);
					espectador.getPremios().stream()
					.sorted(Comparator.comparing(p -> p.getFechaSorteo().toGregorianCalendar().getTime()))
					.collect(Collectors.toList());
					request.setAttribute("premios", espectador.getPremios());
				}
			}

			request.setAttribute("visitante", visitante);
			request.setAttribute("tipoUsuario", tipoUsuario);
			request.setAttribute("miPerfil", esMiPerfil);
			request.setAttribute("usuario", usuarioDTO);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/usuarios/detalle-usuario.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void getLista(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		List<UsuarioDTO> usuarios = port.listarUsuarios().getUsuario();

		request.setAttribute("usuarios", usuarios);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/usuarios/lista-usuarios.jsp");
		dispatcher.forward(request, response);
	}

	private void getModificar(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String usuarioLogueado = (String) session.getAttribute("email");

		if (usuarioLogueado == null) {
			dispatchError(request, response, "No puedes ingresar si no estás logueado", "");
		}

		String tipoUsuario = "espectador";
		UsuarioDTO usuarioDTO = port.getUsuarioByEmail(usuarioLogueado);
		if (usuarioDTO instanceof ArtistaDTO) {
			tipoUsuario = "artista";
		}

		request.setAttribute("usuario", usuarioDTO);
		request.setAttribute("tipoUsuario", tipoUsuario);

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String fechaNacimiento = "mm/dd/yyyy";

		fechaNacimiento = format1.format(usuarioDTO.getFechaNac().toGregorianCalendar().getTime());
		request.setAttribute("fechaNacimiento", fechaNacimiento);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/usuarios/modificar.jsp");
		dispatcher.forward(request, response);
	}

	private void dispatchError(HttpServletRequest request, HttpServletResponse response, String errorMessage,
			String exceptionMessage) throws ServletException, IOException {
		request.setAttribute("errorMsg", String.format("%s: %s", errorMessage, exceptionMessage));
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
		dispatcher.forward(request, response);
	}

	private Boolean isMobile(HttpServletRequest request) {
		return request.getHeader("User-Agent").contains("Mobile");
	}
}
