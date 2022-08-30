package com.tpgr06.coronatickets.servlets.paquetes;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
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

import tpgr06.coronatickets.ws.publicador.BadRequestException_Exception;
import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;
import tpgr06.coronatickets.ws.publicador.EspectaculoDTO;
import tpgr06.coronatickets.ws.publicador.EstadoEspectaculo;
import tpgr06.coronatickets.ws.publicador.NotFoundException_Exception;
import tpgr06.coronatickets.ws.publicador.PaqueteDTO;

/**
 * Servlet implementation class PaqueteServlet
 */
@WebServlet("/PaqueteServlet")
@MultipartConfig
public class PaqueteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Pattern LTRIM = Pattern.compile("^\\s+");
	private static Pattern RTRIM = Pattern.compile("\\s+$");
	CoronaServices port;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaqueteServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (isMobile(request)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
			dispatcher.forward(request, response);
		} else {
			try {
				HttpSession session = request.getSession();

				if (request.getParameter("page") != null && "listar".equals(request.getParameter("page"))) {
					listarPaquetes(request, response);

				} else if (request.getParameter("page") != null && "agregar".equals(request.getParameter("page"))) {
					session.setAttribute("paquete", request.getParameter("paq"));
					request.setAttribute("plataformaList", port.listarPlataformas().getPlataforma());
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("WEB-INF/jsp/paquetes/agregar-espectaculo-a-paquete.jsp");
					dispatcher.forward(request, response);

				} else if (request.getParameter("plat_espec") != null) {

					List<EspectaculoDTO> espectaculos = port
							.listarEspectaculosNoEnPaquete((String) session.getAttribute("paquete"),
									request.getParameter("plat_espec"))
							.getEspectaculo();
					request.setAttribute("espectaculoNoEnPaqueteList", espectaculos);
					request.setAttribute("plataformaList", port.listarPlataformas().getPlataforma());

					RequestDispatcher dispatcher = request
							.getRequestDispatcher("WEB-INF/jsp/paquetes/agregar-espectaculo-a-paquete.jsp");
					dispatcher.forward(request, response);

				} else {

					if (request.getParameter("page") != null && "alta".equals(request.getParameter("page"))) {

						if (session.getAttribute("tipousuario") == null
								|| !"artista".equals(session.getAttribute("tipousuario").toString())) {
							request.setAttribute("errorMsg", "No tiene permisos para acceder a este recurso");
							RequestDispatcher dispatcher = request
									.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
							dispatcher.forward(request, response);
						}
						RequestDispatcher dispatcher = request
								.getRequestDispatcher("WEB-INF/jsp/paquetes/alta-paquete.jsp");
						dispatcher.forward(request, response);

					} else if (request.getParameter("nombre_paquete") == null
							|| "".equals(request.getParameter("nombre_paquete"))) {
						request.setAttribute("errorMsg", "La pagina se encuentra en construccion");
						RequestDispatcher dispatcher = request
								.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
						dispatcher.forward(request, response);

					} else {
						String nombrePaquete = RTRIM
								.matcher(LTRIM.matcher(request.getParameter("nombre_paquete")).replaceAll(""))
								.replaceAll("");

						PaqueteDTO paquete = port.consultaPaquete(nombrePaquete);
						request.setAttribute("espectaculos",
								paquete.getEspectaculos().stream()
										.filter(esp -> !EstadoEspectaculo.FINALIZADO.equals(esp.getEstado()))
										.collect(Collectors.toList()));
						request.setAttribute("paquete", paquete);
						request.setAttribute("categorias",
								port.listarCategoriasByPaquete(nombrePaquete).getCategoria());

						if (esVigente(new Date(), paquete)) {
							request.setAttribute("vigencia", "Vigente");
							request.setAttribute("cssStatus", "approved");
						} else {
							request.setAttribute("vigencia", "Inactivo");
							request.setAttribute("cssStatus", "rejected");
						}

						// Habilito o deshabilito el boton de comprar paquete
						if ("espectador".equals(session.getAttribute("tipousuario")) && 
								paquete.getFechaFin().toGregorianCalendar().getTime().after(new Date())) {
							request.setAttribute("compraVisible", "visible");
						} else {
							request.setAttribute("compraVisible", "hidden");
						}

						// Habilito o deshabilito el boton de agregar espectaculo a paquete
						if ("artista".equals(session.getAttribute("tipousuario"))) {
							request.setAttribute("agregarEspectaculoVisible", "visible");
						} else {
							request.setAttribute("agregarEspectaculoVisible", "hidden");
						}

						RequestDispatcher dispatcher = request
								.getRequestDispatcher("WEB-INF/jsp/paquetes/detalle-paquete.jsp");
						dispatcher.forward(request, response);

					}
				}
			} catch (Exception e) {
				request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: " + e.getMessage());
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
				dispatcher.forward(request, response);
			}

		}
	}

	private void listarPaquetes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();

			if ("artista".equals(session.getAttribute("tipousuario"))) {
				request.setAttribute("altaVisible", "visible");
			} else {
				request.setAttribute("altaVisible", "hidden");
			}
			List<PaqueteDTO> paquetes = port.listarPaquetes().getPaquete();
			request.setAttribute("paquetes", paquetes);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/paquetes/listar-paquetes.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			PaqueteDTO paquete = null;

			// Agregar espectaculo a paquete

			if (request.getParameter("espectaculoParaAgregar") != null) {

				port.agregarEspectaculoAPaquete(request.getParameter("espectaculoParaAgregar"),
						request.getParameter("paquete_nombre"));

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("WEB-INF/jsp/paquetes/confirmacion-espectaculo-paquete.jsp");
				dispatcher.forward(request, response);
			}

			if (request.getParameter("compra") != null) {
				compraPaquete(request, response);

			} else if (request.getParameter("espectaculoParaAgregar") == null) {
				// Control de permisos
				if (session.getAttribute("tipousuario") == null
						|| !"artista".equals(session.getAttribute("tipousuario").toString())) {
					request.setAttribute("errorMsg", "No tiene permisos para acceder a este recurso");
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
					dispatcher.forward(request, response);
				}

				// Obtengo parametros
				String imgPath = "";
				String nombrePaquete = request.getParameter("nombre_paquete");
				String descripcion = request.getParameter("descripcion_paquete");
				String fechaInicioString = request.getParameter("vigencia_desde_paq");
				String fechaFinString = request.getParameter("vigencia_hasta_paq");
				int descuento = Integer.parseInt(request.getParameter("descuento_paquete"));

				// Modificaciones necesarias para obtener las fechas correctas
				SimpleDateFormat sdfInicio = new SimpleDateFormat("yyyy-MM-dd");
				Date fechaInicio = null;
				fechaInicio = sdfInicio.parse(fechaInicioString);
				SimpleDateFormat sdfFin = new SimpleDateFormat("yyyy-MM-dd");
				Date fechaFin = null;
				fechaFin = sdfFin.parse(fechaFinString);

				final GregorianCalendar fechaIni = new GregorianCalendar();
				fechaIni.setTime(fechaInicio);
				XMLGregorianCalendar calendarIni = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaIni);

				final GregorianCalendar fechaFinal = new GregorianCalendar();
				fechaFinal.setTime(fechaFin);
				XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaFinal);

				// Valido si es multipart
				if (!ServletFileUpload.isMultipartContent(request)) {
					request.setAttribute("errorMsg", "El contenido no es multipart");
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
					dispatcher.forward(request, response);
				}

				// Obtengo la imagen
				Part filePart = request.getPart("imagen_paquete");
				if (filePart.getSize() <= 0) {
					imgPath = "/media/img/paquete-generico.png";
				} else {
					InputStream fileContent = filePart.getInputStream();
					imgPath = ImgUtils.saveFile(fileContent);
				}

				// Doy de alta el paquete y luego muestro su detalle
				port.altaPaquete(nombrePaquete, descripcion, calendarIni, calendarFin, descuento, imgPath);
				paquete = port.consultaPaquete(nombrePaquete);

				request.setAttribute("paquete", paquete);
				request.setAttribute("compraVisible", "hidden");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/paquetes/detalle-paquete.jsp");
				dispatcher.forward(request, response);
			}

		} catch (NotFoundException_Exception | BadRequestException_Exception | ParseException
				| DatatypeConfigurationException e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void compraPaquete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			GregorianCalendar gregory = new GregorianCalendar();
			gregory.setTime(new Date());
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);

			HttpSession session = request.getSession();
			port.compraPaquete(request.getParameter("paquete_name"), (String) session.getAttribute("email"), calendar);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/paquetes/compra-paquete.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}

	private boolean esVigente(Date fecha, PaqueteDTO paquete) {
		return paquete.getFechaInicio().toGregorianCalendar().getTime().compareTo(fecha) <= 0
				&& paquete.getFechaFin().toGregorianCalendar().getTime().compareTo(fecha) > 0;
	}

	private Boolean isMobile(HttpServletRequest request) {
		return request.getHeader("User-Agent").contains("Mobile");
	}
}
