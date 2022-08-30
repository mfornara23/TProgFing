package com.tpgr06.coronatickets.servlets.funciones;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.tpgr06.coronatickets.utils.ImgUtils;

import tpgr06.coronatickets.ws.publicador.*;

/**
 * Servlet implementation class FuncionServlet
 */
@WebServlet("/FuncionServlet")
@MultipartConfig
public class FuncionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<CategoriaDTO> categorias;
	List<PlataformaDTO> plataformas;
	List<EspectaculoDTO> espectaculos;
	CoronaServices port;
	Boolean isMobile;
	HttpSession session;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FuncionServlet() {
		super();
	}

	@Override
	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * Listado de Funciones y Consulta de Funcion de Espectaculo 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			session = request.getSession();
			isMobile = isMobile(request);

			altaFuncionVisible(request);
			registroFuncionVisible(request);

			String page = request.getParameter("page");

			if(page==null) {
				listarFuncion(request, response);
			}else {
				switch(page) {
				case "alta":
					getAltaFuncion(request,response);
					break;
				case "listar":
					listarFuncion(request, response);
					break;
				case "sorteo":
					sorteo(request, response);
					break;
				case "detalle":
					detalleFuncion(request, response);
					break;
				default :
					listarFuncion(request, response);
					break;
				}
			}
		}catch (NotFoundException_Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: "+e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}
	
	/**
	 * Recibe los parametros para Alta de Funcion de Espectaculo o para sortear Premios
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();

			//Control de permisos
			if (session.getAttribute("tipousuario")==null ||!"artista".equals(session.getAttribute("tipousuario").toString())) {
				request.setAttribute("errorMsg", "No tiene permisos para acceder a este recurso");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
				dispatcher.forward(request, response);
			}
			//Realizar sorteo
			if("sorteo".equals(request.getParameter("page"))){
				String nombreEspectaculo = request.getParameter("nombre_espec");
				String nombreFuncion =  request.getParameter("nombre_funcion");
				PremioDTO premio = port.sortearPremios(nombreEspectaculo, nombreFuncion);
				request.setAttribute("premio", premio);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/ganadores-sorteo.jsp");
				dispatcher.forward(request, response);

			}else {
				//Obtengo parametros
				String imgPath = "";
				String nombrePlataforma = (String) session.getAttribute("nombre_plataforma");
				String nombreEspectaculo = (String) session.getAttribute("nombre_espec");
				String nombreFuncion =  request.getParameter("nombre_funcion");
				String fechaString = request.getParameter("fecha_funcion");
				String horaString = request.getParameter("hora_funcion");
				StringListWrapper nicknameArtistasInvitados = new StringListWrapper();
				if (request.getParameterValues("artistaInvitado") != null) {
					nicknameArtistasInvitados.setDatos(new ArrayList<String>( Arrays.asList(request.getParameterValues("artistaInvitado"))));
				}

				//Modificaciones necesarias para obtener la fecha correcta
				String fechaTotal = String.format("%s %s", fechaString, horaString);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date fecha = null;
				fecha = sdf.parse(fechaTotal);

				//Valido si es multipart
				if(!ServletFileUpload.isMultipartContent(request)){
					request.setAttribute("errorMsg", "El contenido no es multipart");
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
					dispatcher.forward(request, response);
				}

				//Obtengo la imagen
				Part filePart = request.getPart("imagen_funcion");
				if (filePart.getSize()<=0) {
					imgPath = "/media/img/funcion.png";
				} else {
					InputStream fileContent = filePart.getInputStream();
					imgPath = ImgUtils.saveFile(fileContent);
				}
				//Pasar las fechas a XMLGregorianCalendar
				GregorianCalendar calendarFuncion = new GregorianCalendar();
				calendarFuncion.setTime(fecha);
				XMLGregorianCalendar fechaFuncion = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarFuncion);

				GregorianCalendar calendarRegistro = new GregorianCalendar();
				XMLGregorianCalendar fechaRegistro = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarRegistro);

				//Doy de alta la funcion y luego muestro su informacion
				port.altaFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion, fechaFuncion,
						nicknameArtistasInvitados, fechaRegistro, imgPath);

				EspectaculoDTO espectaculo = null;
				FuncionDTO funcion = null;
				espectaculo = port.consultaEspectaculo(nombreEspectaculo);
				funcion = port.getFuncionEspectaculo(nombreFuncion, nombreEspectaculo);
				request.setAttribute("funcion", funcion);
				request.setAttribute("espectaculo", espectaculo);

				session.removeAttribute("nombre_espec");
				session.removeAttribute("nombre_plataforma");
				request.setAttribute("registroVisible", "hidden");
				request.setAttribute("sorteoVisible", "hidden");
				request.setAttribute("ganadoresVisible", "hidden");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/info-funcion.jsp");
				dispatcher.forward(request, response);
			}
		} catch (NotFoundException_Exception | BadRequestException_Exception | ParseException | DatatypeConfigurationException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: "+e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void cargarPlataformas(HttpServletRequest request) {
		plataformas = port.listarPlataformas().getPlataforma();
		request.setAttribute("platformaList", plataformas);

	}

	private void cargarCategorias(HttpServletRequest request) {
		categorias = port.listarCategorias().getCategoria();
		request.setAttribute("categoriaList", categorias);

	}

	private void altaFuncionVisible(HttpServletRequest request) {
		if("artista".equals(session.getAttribute(("tipousuario")))) {
			request.setAttribute("altaVisible", "visible");
		} else {
			request.setAttribute("altaVisible", "hidden");
		}
	}
	private void registroFuncionVisible(HttpServletRequest request) {

		if("espectador".equals(session.getAttribute(("tipousuario")))) {
			request.setAttribute("registroVisible", "visible");
		} else {
			request.setAttribute("registroVisible", "hidden");
		}
	}

	private void getAltaFuncion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException_Exception {

		String nombreEspectaculo = request.getParameter("nombre_espec");

		if (isMobile) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/funciones/seleccion-espectaculo.jsp");
			dispatcher.forward(request, response);
		}
		else if(nombreEspectaculo==null || "".equals(nombreEspectaculo)) {

			UsuarioDTO usuario = port.getUsuarioByEmail(session.getAttribute("email").toString());
			espectaculos = port.listarEspectaculosByArtista(usuario.getNickname()).getEspectaculo();
			request.setAttribute("espectaculoList", espectaculos);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/seleccion-espectaculo-artista.jsp");
			dispatcher.forward(request, response);

		} else {
			EspectaculoDTO espectaculo = null;
			espectaculo = port.consultaEspectaculo(nombreEspectaculo);
			UsuarioDTO usuario = port.getUsuarioByEmail(session.getAttribute("email").toString());
			List<UsuarioDTO> artistasInvitados = port.listarUsuarios().getUsuario()
					.stream()
					.filter(usuarioDTO -> usuarioDTO instanceof ArtistaDTO)
					.filter(usuarioDTO -> !(usuarioDTO.getNickname().equals(usuario.getNickname())))
					.collect(Collectors.toList());
			session.setAttribute("nombre_espec", espectaculo.getNombre());
			session.setAttribute("nombre_plataforma", espectaculo.getPlataforma().getNombre());
			request.setAttribute("artistaList", artistasInvitados);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/alta-funcion.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void listarFuncion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException_Exception {
		cargarCategorias(request);
		cargarPlataformas(request);

		espectaculos = getEspectaculosList(request.getParameter("plat_espec"), request.getParameter("cats_espec"));
		request.setAttribute("espectaculoList", espectaculos);

		if(request.getParameter("nombre_espec")==null || "".equals(request.getParameter("nombre_espec"))) {
			if (isMobile) {
				if (isEspectador(session)) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/funciones/seleccion-espectaculo.jsp");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/seleccion-espectaculo.jsp");
				dispatcher.forward(request, response);
			}

		} else if(request.getParameter("nombre_espec")!=null && (request.getParameter("nombre_funcion")==null || "".equals(request.getParameter("nombre_funcion")))) {

			List<FuncionDTO> funciones = port.listarFuncionesByEspectaculo(request.getParameter("nombre_espec")).getFuncion();
			request.setAttribute("funcionList", funciones);
			EspectaculoDTO espectaculo = port.consultaEspectaculo(request.getParameter("nombre_espec"));

			request.setAttribute("espectaculo", espectaculo);
			if (isMobile) {
				if (isEspectador(session)) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/funciones/listar-funciones.jsp");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/listar-funciones.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

	private void sorteo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException_Exception {

		EspectaculoDTO espectaculo = null;
		FuncionDTO funcion = null;
		String nombreEspectaculo = request.getParameter("nombre_espec");
		String nombreFuncion =  request.getParameter("nombre_funcion");
		espectaculo = port.consultaEspectaculo(nombreEspectaculo);
		funcion = port.getFuncionEspectaculo(nombreFuncion, nombreEspectaculo);
		request.setAttribute("funcion", funcion);
		request.setAttribute("espectaculo", espectaculo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/usuarios-sorteo.jsp");
		dispatcher.forward(request, response);
	}

	private void detalleFuncion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException_Exception {

		EspectaculoDTO espectaculo = null;
		FuncionDTO funcion = null;
		String nombreEspectaculo = request.getParameter("nombre_espec");
		String nombreFuncion =  request.getParameter("nombre_funcion");
		espectaculo = port.consultaEspectaculo(nombreEspectaculo);
		funcion = port.getFuncionEspectaculo(nombreFuncion, nombreEspectaculo);
		request.setAttribute("funcion", funcion);
		request.setAttribute("espectaculo", espectaculo);

		if (isMobile) {
			if (isEspectador(session)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/funciones/info-funcion.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			/*
			 * Sorteo visible
			 */
			request.setAttribute("sorteoVisible", "hidden");
			request.setAttribute("ganadoresVisible", "hidden");
			if("artista".equals(session.getAttribute("tipousuario"))) {
				String emailArtista = (String) session.getAttribute("email");
				if(espectaculo.getArtista().getEmail().equals(emailArtista)){
					if(!funcion.isSorteoRealizado()) {
						if(funcion.getFecha().toGregorianCalendar().getTime().before(new Date()))
							request.setAttribute("sorteoVisible", "visible");
					}else {
						request.setAttribute("ganadoresVisible", "visible");
					}
				}
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/info-funcion.jsp");
			dispatcher.forward(request, response);
		}
	}

	private List<EspectaculoDTO> getEspectaculosList(String plataforma, String categoria) throws NotFoundException_Exception {
		List<EspectaculoDTO> especs;

		if(plataforma == null || "".equals(plataforma) || "all".equals(plataforma)) {
			especs = port.listarEspectaculosAceptados().getEspectaculo();
		} else {
			especs = port.listarEspectaculos(plataforma).getEspectaculo().stream()
					.filter(esp -> EstadoEspectaculo.ACEPTADO.equals(esp.getEstado()))
					.collect(Collectors.toList());
		}

		if (categoria!=null && !"".equals(categoria) && !"all".equals(categoria)) {
			return especs
					.stream()
					.filter(espectaculoDTO -> espectaculoDTO.getCategorias().contains(categoria))
					.collect(Collectors.toList());
		} else {
			return especs;
		}
	}

	private Boolean isMobile(HttpServletRequest request) {
		return request.getHeader("User-Agent").contains("Mobile");
	}

	private Boolean isEspectador(HttpSession session) {
		return "espectador".equals(session.getAttribute("tipousuario"));
	}
}
