package com.tpgr06.coronatickets.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoronaServices port;

	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		Boolean isMobile = request.getHeader("User-Agent")
				.contains("Mobile");
		if (isMobile) {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/mobile/index.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	

}
