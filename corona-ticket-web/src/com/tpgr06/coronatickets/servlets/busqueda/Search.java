package com.tpgr06.coronatickets.servlets.busqueda;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tpgr06.coronatickets.ws.publicador.BadRequestException_Exception;
import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;
import tpgr06.coronatickets.ws.publicador.EspectaculoDTO;
import tpgr06.coronatickets.ws.publicador.NotFoundException_Exception;
import tpgr06.coronatickets.ws.publicador.PaqueteDTO;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoronaServices port;

	/**
	 * @throws NotFoundException 
	 * @throws BadRequestException 
	 * @see HttpServlet#HttpServlet()
	 */
	public Search() throws BadRequestException_Exception, NotFoundException_Exception {
		super();
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	protected void processRequestSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try { 
			String key = request.getParameter("key");
			request.setAttribute("key", key);

			List<EspectaculoDTO> espectaculos = port.buscarEspectaculos(key).getEspectaculo();
			List<PaqueteDTO> paquetes = port.buscarPaquetes(key).getPaquete();
			List<String> plataformas = port.listarPlataformas().getPlataforma().stream()
					.map(plat->plat.getNombre())
					.collect(Collectors.toList());

			List<String> categorias = port.listarCategorias().getCategoria().stream()
					.map(cat->cat.getNombre())
					.collect(Collectors.toList());

			String plataformaFilter= request.getParameter("plataformafilter");
			String categoriaFilter = request.getParameter("categoriafilter");
			String sort = request.getParameter("sort");

			if(plataformaFilter!= null && !plataformaFilter.equalsIgnoreCase("") && !plataformaFilter.equalsIgnoreCase("all")) {
				espectaculos = espectaculos.stream()
						.filter(esp->esp.getPlataforma().getNombre().equalsIgnoreCase(plataformaFilter))
						.collect(Collectors.toList());

				paquetes = paquetes.stream()
						.filter(paq->paq.getEspectaculos().stream().anyMatch(esp->esp.getPlataforma().getNombre().equalsIgnoreCase(plataformaFilter)))
						.collect(Collectors.toList());

			}

			if(categoriaFilter!=null && !categoriaFilter.equalsIgnoreCase("") && !categoriaFilter.equalsIgnoreCase("all")) {
				espectaculos = espectaculos.stream()
						.filter(esp->esp.getCategorias().stream().anyMatch(cat->cat.equalsIgnoreCase(categoriaFilter)))
						.collect(Collectors.toList());	

				paquetes = paquetes.stream()
						.filter(paq->paq.getEspectaculos().stream().anyMatch(esp->esp.getCategorias().stream().anyMatch(cat->cat.equalsIgnoreCase(categoriaFilter))))
						.collect(Collectors.toList());
			}

			if(sort!=null && !sort.equalsIgnoreCase("")) {
				if(sort.equalsIgnoreCase("alfabetica")) {
					espectaculos = espectaculos.stream()
							.sorted(Comparator.comparing(EspectaculoDTO::getNombre))
							.collect(Collectors.toList());

					paquetes = paquetes.stream()
							.sorted(Comparator.comparing(PaqueteDTO::getNombre))
							.collect(Collectors.toList());

				}else if(sort.equalsIgnoreCase("fecha")) {
					espectaculos = espectaculos.stream()
							.sorted(Comparator.comparing(e -> e.getFechaReg().toGregorianCalendar().getTime()))
							.collect(Collectors.toList());

					paquetes = paquetes.stream()
							.sorted(Comparator.comparing(p -> p.getFechaInicio().toGregorianCalendar().getTime()))
							.collect(Collectors.toList());

				}
			}

			request.setAttribute("espectaculos", espectaculos);
			request.setAttribute("paquetes", paquetes);
			request.setAttribute("plataformas", plataformas);
			request.setAttribute("categorias", categorias);

			ServletContext context = getServletContext();

			RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/template/busqueda.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			request.setAttribute("errorMsg", "Hubo un problema al procesar su solicitud: "+e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (isMobile(request)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/mobile/errores/error-permisos.jsp");
			dispatcher.forward(request, response);
		} else {
			processRequestSearch(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Boolean isMobile(HttpServletRequest request) {
		return request.getHeader("User-Agent").contains("Mobile");
	}
}
