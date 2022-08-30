package com.tpgr06.coronatickets.servlets.espectaculos;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
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
import com.tpgr06.coronatickets.utils.model.EspectaculosFavoritosWrapper;

import tpgr06.coronatickets.ws.publicador.BadRequestException;
import tpgr06.coronatickets.ws.publicador.BadRequestException_Exception;
import tpgr06.coronatickets.ws.publicador.CategoriaDTO;
import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;
import tpgr06.coronatickets.ws.publicador.EspectaculoDTO;
import tpgr06.coronatickets.ws.publicador.EspectadorDTO;
import tpgr06.coronatickets.ws.publicador.EstadoEspectaculo;
import tpgr06.coronatickets.ws.publicador.NotFoundException_Exception;
import tpgr06.coronatickets.ws.publicador.PlataformaDTO;
import tpgr06.coronatickets.ws.publicador.StringListWrapper;
import tpgr06.coronatickets.ws.publicador.ValoracionDTO;

/**
 * Servlet implementation class EspectaculoServlet
 */
@WebServlet("/EspectaculoServlet")
@MultipartConfig
public class EspectaculoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<CategoriaDTO> categorias;
	List<PlataformaDTO> plataformas;
	List<EspectaculoDTO> espectaculos;
	private static Pattern LTRIM = Pattern.compile("^\\s+");
	private static Pattern RTRIM = Pattern.compile("\\s+$");
	CoronaServices port;
	Boolean isMobile;
	HttpSession session;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EspectaculoServlet() {
		super();
	}

	@Override
	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			session = request.getSession();
			isMobile = isMobile(request);

			String page = request.getParameter("page");
			if(page==null) {
				listarEspectaculo(request, response);
			}else {
				switch (page) {

				case "alta":
					getAltaEspectaculo(request, response);
					break;
				case "finalizar":
					getFinalizarEspectaculo(request, response);
					break;
				case "listar":
					listarEspectaculo(request, response);
					break;
				case "valorar":
					valorarEspectaculo(request, response);
					break;
				case "detalle":
					detalleEspectaculo(request, response);
					break;
				default:
					listarEspectaculo(request, response);
					break;
				}
			}
		}catch (NotFoundException_Exception e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: "+e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * Recibe los parametros para Alta espectaculo y Finalizar Espectaculo
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			String imgPath = "";
			String fav = request.getParameter("fav");

			if(request.getParameter("espectaculoValorado")!=null || "".equals(request.getParameter("espectaculoValorado"))){
				port.valorarEspectaculo(request.getParameter("nombre_espec"), (String) session.getAttribute("nickname"),
						Integer.parseInt((String) request.getParameter("puntajeEspectaculo")));
				detalleEspectaculo(request, response);
			}else {	
				if(fav!=null) {
					String espectaculo = null;
					String detalle = (String) request.getParameter("detalle");
					if("true".equals(detalle)) 
						espectaculo = request.getParameter("nombre_espec");
					else
						espectaculo = request.getParameter("nombre_espectaculo");
					String nickname = (String) session.getAttribute("nickname");
					if("true".contentEquals(fav)) {
						port.marcarFavorito(espectaculo,nickname);
						if("true".equals(detalle))
							detalleEspectaculo(request, response);
						else
							listarEspectaculo(request, response);
					}else if("false".equals(fav)) {
						port.desmarcarFavorito(espectaculo,nickname);
						if("true".equals(detalle))
							detalleEspectaculo(request, response);
						else
							listarEspectaculo(request, response);
					}
				}else {
					//Control de permisos
					if (session.getAttribute("tipousuario")==null || !"artista".equals(session.getAttribute("tipousuario").toString())) {
						request.setAttribute("errorMsg", "No tiene persmisos para acceder a este recurso");
						RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
						dispatcher.forward(request, response);
					}

					if (request.getParameter("page")!=null && "finalizar".equals(request.getParameter("page"))) {

						String nombreEspectaculo =  request.getParameter("nombre_espec");
						port.finalizarEspectaculo(nombreEspectaculo);
						RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/finalizar-espectaculo-success.jsp");
						dispatcher.forward(request, response);

					} else {

						//Obtengo parametros
						String nombre =  request.getParameter("nombre_espec");
						String desc = request.getParameter("descripcion_espec");
						int espec_min = Integer.parseInt(request.getParameter("espec_min"));
						int espec_max = Integer.parseInt(request.getParameter("espec_max"));
						String url = request.getParameter("url_espec");
						int costo = Integer.parseInt(request.getParameter("costo_espec"));
						int duraicion = Integer.parseInt(request.getParameter("duracion_espec"));
						String descPremios = request.getParameter("desc_premios");
						Integer cantPremios = Integer.parseInt(request.getParameter("cant_premios"));
						String urlVideo = request.getParameter("video_espec");
						String plataforma = request.getParameter("plataforma_espec");
						ArrayList<String> categorias = null;
						if (request.getParameterValues("cats")==null || "".equals(request.getParameterValues("cats"))) {
							throw new BadRequestException_Exception("Debe seleccionar al menos una categoria",new BadRequestException());
						} else {
							categorias = new ArrayList<String>(Arrays.asList(request.getParameterValues("cats")));
						}

						String emailArtista = session.getAttribute("email").toString();

						//Valido si es multipart
						if(!ServletFileUpload.isMultipartContent(request)){
							request.setAttribute("errorMsg", "El contenido no es multipart");
							RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
							dispatcher.forward(request, response);
							dispatcher.forward(request, response);
						}

						//Obtengo la imagen
						Part filePart = request.getPart("imagen_espec");
						if (filePart.getSize()<=0) {
							imgPath = "/media/img/espectaculo-generico.png";
						} else {
							InputStream fileContent = filePart.getInputStream();
							imgPath = ImgUtils.saveFile(fileContent);
						}
						StringListWrapper categoriasWrapper = new StringListWrapper();
						categoriasWrapper.setDatos(categorias);
						//Doy de alta el espectaculo y luego lo obtengo para mostrarlo
						final GregorianCalendar now = new GregorianCalendar();
						XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(now);
						port.altaEspectaculo(nombre, desc, duraicion, espec_min, espec_max, url, costo,
								date, plataforma, emailArtista, categoriasWrapper, imgPath, descPremios, cantPremios, urlVideo);
						EspectaculoDTO espec = port.consultaEspectaculo(nombre);
						request.setAttribute("espectaculo", espec);
						request.setAttribute("funciones", espec.getFunciones());
						request.setAttribute("paquetes", espec.getPaquetes());
						request.setAttribute("categorias", espec.getCategorias());
						request.setAttribute("favoritoVisible", "hidden");

						RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/detalle-espectaculo.jsp");
						dispatcher.forward(request, response);

					}
				}
			} 
		}catch (NotFoundException_Exception | BadRequestException_Exception | DatatypeConfigurationException e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: "+e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		} 
	}

	private void altaEspectaculoVisible(HttpServletRequest request) {

		if("artista".equals(session.getAttribute("tipousuario"))) {
			request.setAttribute("altaVisible", "visible");
		} else {
			request.setAttribute("altaVisible", "hidden");
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

	private void getAltaEspectaculo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cargarCategorias(request);
		cargarPlataformas(request);
		if (!isMobile) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/alta-espectaculo.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void getFinalizarEspectaculo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException_Exception {

		if (isMobile) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
			dispatcher.forward(request, response);
		} else if (request.getParameter("nombre_espec")==null || "".equals(request.getParameter("nombre_espec"))) {

			espectaculos = port.listarEspectaculosFinalizados((String) session.getAttribute("nickname")).getEspectaculo();
			request.setAttribute("espectaculoList", espectaculos);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/listar-finalizados.jsp");
			dispatcher.forward(request, response);

		} else {

			EspectaculoDTO espectaculo = port.consultaEspectaculo(request.getParameter("nombre_espec"));
			request.setAttribute("espectaculo", espectaculo);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/finalizar-espectaculo.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void listarEspectaculo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException_Exception {
		cargarCategorias(request);
		cargarPlataformas(request);
		altaEspectaculoVisible(request);

		espectaculos = getEspectaculosList(request.getParameter("plat_espec"), request.getParameter("cats_espec"));
		request.setAttribute("espectaculoList", espectaculos);

		if (isMobile) {
			if (isEspectador(session)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/espectaculos/listar-espectaculos.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/listar-espectaculos.jsp");
				dispatcher.forward(request, response);
			}
		} else if(request.getParameter("nombre_espec")==null || "".equals(request.getParameter("nombre_espec"))) {

			if (isMobile) {
				if (isEspectador(session)) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/espectaculos/listar-espectaculos.jsp");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
					dispatcher.forward(request, response);
				}
			} else if (isEspectador(session)) {
				EspectadorDTO espectador = (EspectadorDTO) port.getUsuarioByEmail((String) session.getAttribute("email"));
				List<EspectaculosFavoritosWrapper> espectaculosFavoritos = createEspectaculosFavoritosWrapper(espectador.getEspectaculosFavoritos(), espectaculos);
				request.setAttribute("espectaculosWrapper", espectaculosFavoritos);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/listar-espectaculos-favoritos.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/listar-espectaculos.jsp");
				dispatcher.forward(request, response);
			}
		}
	}


	private List<EspectaculosFavoritosWrapper> createEspectaculosFavoritosWrapper(List<String> espectaculosFavoritos, List<EspectaculoDTO> espectaculos) {

		List<EspectaculosFavoritosWrapper> res = new ArrayList<EspectaculosFavoritosWrapper>();
		boolean esFavorito=false;
		for(EspectaculoDTO e:espectaculos) {
			if(espectaculosFavoritos.contains(e.getNombre())) 
				esFavorito = true;
			else 
				esFavorito = false;
			res.add(new EspectaculosFavoritosWrapper(e, esFavorito));
		}
		return res;
	}

	private void detalleEspectaculo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException_Exception {

		//Le saco los espacios de la derecha y de la izquierda al espectaculo que viene por parametro
		//para evitar errores en la busqueda
		String nombreEspectaculo = RTRIM.matcher(LTRIM.matcher(request.getParameter("nombre_espec")).replaceAll("")).replaceAll("");
		EspectaculoDTO espec = port.consultaEspectaculo(nombreEspectaculo);
		Boolean esFavorito;
		request.setAttribute("espectaculo", espec);
		request.setAttribute("funciones", espec.getFunciones());
		request.setAttribute("paquetes", port.listarPaquetesByEspectaculo(nombreEspectaculo).getPaquete());
		request.setAttribute("categorias", espec.getCategorias());

		// Valorar Espectaculo Promedio
		float valorPromedio = 0;
		if(!espec.getValoraciones().isEmpty()) {
			Integer total = 0;
			for(Integer v: espec.getValoraciones()) {
				total+=v;
			}
			valorPromedio = (float)total/espec.getValoraciones().size();
		}

		request.setAttribute("valorPromedio", valorPromedio);

		boolean hayValoracionPromedio = true;
		if (valorPromedio == 0) {
			hayValoracionPromedio = false;
		}
		request.setAttribute("hayValoracionPromedio", hayValoracionPromedio);
		boolean puedeValorar = false;
		if(isEspectador(session)) {
		// Valoracion Espectaculo Personal
		EspectadorDTO espectador = (EspectadorDTO) port.getUsuarioByEmail(((String)session.getAttribute("email")));
		Integer valorPersonal = 0;
		for(ValoracionDTO v:espectador.getValoraciones()) {
			if(v.getEspectaculo().equals(espec.getNombre()))
				valorPersonal = v.getPuntaje();
		}
		request.setAttribute("valorPersonal", valorPersonal);

		boolean hayValoracionPersonal = true;
		if (valorPersonal == 0) {
			hayValoracionPersonal = false;
		}
		request.setAttribute("hayValoracionPersonal", hayValoracionPersonal);
		puedeValorar = espectador.getRegistros().stream()
				.filter(reg->reg.getFuncion().getFecha().toGregorianCalendar().getTime().before(new Date()))
				.map(reg->reg.getFuncion().getNombreEspectaculo()).
				anyMatch(esp->esp.equals(espec.getNombre()));
		}
		request.setAttribute("puedeValorar", puedeValorar);

		//Habilito o deshabilito el boton de finalizar espectaculo
		if(espec.getArtista().getEmail().equals(session.getAttribute("email")) && espec.getEstado().equals(EstadoEspectaculo.ACEPTADO)) {
			request.setAttribute("finalizarVisible", "visible");
		} else {
			request.setAttribute("finalizarVisible", "hidden");
		}

		request.setAttribute("favoritoVisible", "hidden");
		if (isEspectador(session)) {
			request.setAttribute("favoritoVisible", "visible");
			EspectadorDTO espectador2 = (EspectadorDTO) port.getUsuarioByEmail((String) session.getAttribute("email"));
			esFavorito = espectador2.getEspectaculosFavoritos().contains(espec.getNombre());
			if(esFavorito) {
				request.setAttribute("esFavorito", "true");
			}else {
				request.setAttribute("esFavorito", "false");
			}
		}

		if (isMobile) {
			if (isEspectador(session)) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/espectaculos/detalle-espectaculo.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/espectaculos/detalle-espectaculo.jsp");
			dispatcher.forward(request, response);
		}
	}

	private List<EspectaculoDTO> getEspectaculosList(String plataforma, String categoria) throws NotFoundException_Exception {
		List<EspectaculoDTO> especs;

		if(plataforma == null || "".equals(plataforma) || "all".equals(plataforma)) {
			especs = port.listarEspectaculosAceptados().getEspectaculo();
		} else {
			especs = port.listarEspectaculos(plataforma).getEspectaculo()
					.stream()
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

	private void valorarEspectaculo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NotFoundException_Exception {

		List<EspectaculoDTO> espectaculos = port
				.listarEspectaculosParaValorar((String) session.getAttribute("nickname")).getEspectaculo();
		request.setAttribute("espectaculoValorarList", espectaculos);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("WEB-INF/jsp/espectaculos/listar-para-valorar-espectaculo.jsp");
		dispatcher.forward(request, response);
	}

}
