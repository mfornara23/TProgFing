package com.tpgr06.coronatickets.servlets.funciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import tpgr06.coronatickets.ws.publicador.BadRequestException_Exception;
import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;
import tpgr06.coronatickets.ws.publicador.EspectaculoDTO;
import tpgr06.coronatickets.ws.publicador.EspectadorDTO;
import tpgr06.coronatickets.ws.publicador.FuncionDTO;
import tpgr06.coronatickets.ws.publicador.NotFoundException_Exception;
import tpgr06.coronatickets.ws.publicador.PaqueteDTO;
import tpgr06.coronatickets.ws.publicador.RegistroFuncionDTO;

/**
 * Servlet implementation class FuncionServlet
 */
@WebServlet("/RegistroFuncionServlet")
public class RegistroFuncionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoronaServices port;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroFuncionServlet() {
		super();
	}

	@Override
	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Despacho jsp de Registro a Funcion de Espectaculo
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (isMobile(request)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
			dispatcher.forward(request, response);
		} else {
			try {
				HttpSession session = request.getSession();

				//Control de permisos
				if (session.getAttribute("tipousuario") == null || !"espectador".equals(session.getAttribute("tipousuario")
						.toString())) {
					request.setAttribute("errorMsg", "No tiene permisos para acceder a este recurso");
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
					dispatcher.forward(request, response);
				}

				FuncionDTO funcion = null;
				funcion = port.getFuncionEspectaculo(request.getParameter("nombre_funcion"), request.getParameter("nombre_espec"));

				EspectaculoDTO espectaculo = null;
				espectaculo = port.consultaEspectaculo((String) request.getParameter("nombre_espec"));

				List<RegistroFuncionDTO> registros = null;
				registros = ((EspectadorDTO) port.getUsuarioByEmail((String) session.getAttribute("email"))).getRegistros()
						.stream()
						.filter(registroDTO -> !(registroDTO.getCosto()
								                         .equals(0)))
						.collect(Collectors.toList());

				EspectadorDTO espectador = (EspectadorDTO) port.getUsuarioByEmail((String) session.getAttribute("email"));
				List<PaqueteDTO> paquetesUsuario = espectador.getPaquetes();
				List<PaqueteDTO> paquetes = new ArrayList<PaqueteDTO>();
				for (PaqueteDTO p : paquetesUsuario) {
					for (EspectaculoDTO e : p.getEspectaculos()) {
						if (e.getNombre()
								.equalsIgnoreCase(espectaculo.getNombre())) {
							paquetes.add(p);
							break;
						}
					}
				}

				session.setAttribute("nombre_espec", espectaculo.getNombre());
				session.setAttribute("nombre_plataforma", espectaculo.getPlataforma()
						.getNombre());
				session.setAttribute("nombre_funcion", funcion.getNombre());
				request.setAttribute("funcion", funcion);
				request.setAttribute("registroList", registros);
				request.setAttribute("paqueteList", paquetes);
				request.setAttribute("espectaculo", espectaculo);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/registro-funcion.jsp");
				dispatcher.forward(request, response);

			} catch (NotFoundException_Exception e) {
				request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: " + e.getMessage());
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
				dispatcher.forward(request, response);
			}
		}

	}

	/**
	 * Recibe los parametros para el Registro a Funcion de Espectaculo
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();

			//Control de permisos
			if (session.getAttribute("tipousuario")==null ||!"espectador".equals((String) session.getAttribute("tipousuario"))) {
				request.setAttribute("errorMsg", "No tiene permisos para acceder a este recurso");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
				dispatcher.forward(request, response);
			}

			//Obtengo parametros
			String nombrePlataforma = (String) session.getAttribute("nombre_plataforma");
			String nombreEspectaculo = (String) session.getAttribute("nombre_espec");
			String nombreFuncion = (String) session.getAttribute("nombre_funcion");
			String emailEspectador = (String) session.getAttribute("email");
			String nombrePaquete = request.getParameter("nombre_paquete");
			List<String> registros = new ArrayList<String>();
			if (request.getParameterValues("registro") != null) {
				registros = Arrays.asList(request.getParameterValues("registro"));
			}
			RegistroFuncionDTO registro = null;
			GregorianCalendar gregory = new GregorianCalendar();
			gregory.setTime(new Date());
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);

			if (nombrePaquete == null || "".equals(nombrePaquete) || "none".equals(nombrePaquete)) {

				if (registros.size() == 3) {
					registro = port.registroFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion, emailEspectador, calendar, registros.get(0), registros.get(1), registros.get(2));
				} else if (request.getParameterValues("registro") == null) {
					registro = port.registroFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion, emailEspectador, calendar, "", "", "");
				} else {
					throw new BadRequestException_Exception("Debe seleccionar tres registros o ninguno", null);
				}
				
			} else {
				registro = port.registroFuncionPaquete(nombrePlataforma, nombreEspectaculo, nombreFuncion, emailEspectador, calendar, nombrePaquete);
			}
			
			request.setAttribute("registro", registro);
			request.setAttribute("nombre_funcion", nombreFuncion);
			request.setAttribute("funcion", port.getFuncionEspectaculo(nombreFuncion, nombreEspectaculo));
			session.removeAttribute("nombre_espec");
			session.removeAttribute("nombre_plataforma");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/funciones/registro-funcion-success.jsp");
			dispatcher.forward(request, response);
			
		} catch (NotFoundException_Exception | BadRequestException_Exception | DatatypeConfigurationException e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: "+e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}

	}

	private Boolean isMobile(HttpServletRequest request) {
		return request.getHeader("User-Agent").contains("Mobile");
	}
	
}
