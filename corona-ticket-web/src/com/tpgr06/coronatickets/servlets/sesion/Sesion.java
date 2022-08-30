package com.tpgr06.coronatickets.servlets.sesion;

import com.google.common.hash.Hashing;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import tpgr06.coronatickets.ws.publicador.*;

@WebServlet("/login")
public class Sesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CoronaServices port;


	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	protected void processRequestLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
		

		try {
			
			HttpSession session = request.getSession();
			ServletContext context = getServletContext();
			
			String selector = RandomStringUtils.randomAlphanumeric(12);
			String rawValidator =  RandomStringUtils.randomAlphanumeric(64);

			// Se intenta generar un hash a partir de un string (se puede usar otra librería si esta no anda)
			String hashedValidator = Hashing.sha256().hashString(rawValidator, StandardCharsets.UTF_8).toString();

			Cookie cookieSelector = new Cookie("selector", selector);
			cookieSelector.setMaxAge(604800);

			Cookie cookieValidator = new Cookie("validator", rawValidator);
			cookieValidator.setMaxAge(604800);
			
			if(port.login(email, password, rememberMe, selector, hashedValidator)) {
				//si quiere recordar entonces persisto las cookies en el cliente
				if (rememberMe) {
					response.addCookie(cookieSelector);
					response.addCookie(cookieValidator);
				}
				session.setAttribute("email",email);
				UsuarioDTO usr = port.getUsuarioByEmail(email);
				session.setAttribute("nickname", usr.getNickname());
				if(usr instanceof ArtistaDTO) {
					session.setAttribute("tipousuario","artista");
				}else {
					session.setAttribute("tipousuario", "espectador");
				}
				
				if (request.getHeader("User-Agent").contains("Mobi")) {
					if (session.getAttribute("tipousuario").equals("espectador")) {
						RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/mobile/usuario/loginsuccess.jsp");
						dispatcher.forward(request, response);
					} else {
						RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/mobile/errores/error-permisos.jsp");
						dispatcher.forward(request, response);
					}
				} else {
					RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/usuario/loginsuccess.jsp");
					dispatcher.forward(request, response);
				}
			} else {
				session = request.getSession();
				session.setAttribute("email", null);
				if (request.getHeader("User-Agent").contains("Mobi")) {
					RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/mobile/usuario/loginfail.jsp");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/usuario/loginfail.jsp");
					dispatcher.forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void processRequestLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession(false);
			Cookie[] cookies = request.getCookies();
			
			if (session!=null) session.invalidate();			
			ServletContext context = getServletContext();			
			
			//Si se quiere deslogear borro las cookies persistidas en el backend con port.logout
			// y las cookies persistidas en el cliente con setMaxAge(0)
			for (Cookie cookie : cookies) {
				if ("selector".equals(cookie.getName())) {
					port.logout(cookie.getValue());
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				} else if ("validator".equals(cookie.getName())) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			
			if (request.getHeader("User-Agent").contains("Mobi")) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/mobile/index.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/usuario/login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		Cookie[] cookies = request.getCookies();
		Boolean logged =  false;
		UsuarioAuthDTO user;
		HttpSession session = request.getSession();
		
		//Si desea recordar la sesion se redirige automaticamente al home con el usuario logeado
		for (Cookie cookie : cookies) {
			if ("selector".equals(cookie.getName())) {
				logged = true;
				user = port.getAuthToken(cookie.getValue());
				session.setAttribute("email",user.getUsuario().getEmail());
				UsuarioDTO usr = port.getUsuarioByEmail(user.getUsuario().getEmail());
				session.setAttribute("nickname", usr.getNickname());
				if(usr instanceof ArtistaDTO) {
					session.setAttribute("tipousuario","artista");
				}else {
					session.setAttribute("tipousuario", "espectador");
				}
				
			}
		}
		
		
		if (request.getHeader("User-Agent").contains("Mobi")) {
			if(logged) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("//WEB-INF/mobile/usuario/loginsuccess.jsp");
				dispatcher.forward(request, response);			
			} else if(request.getParameter("page") != null && request.getParameter("page")
					.equals("home")) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/mobile/index.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/mobile/usuario/login.jsp");
				dispatcher.forward(request, response);
			}

		} else {
			if(logged) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/usuario/loginsuccess.jsp");
				dispatcher.forward(request, response);			
			} else {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/WEB-INF/jsp/usuario/login.jsp");
				dispatcher.forward(request, response);
			}
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("email")!=null) {
			processRequestLogin(request, response);
		}
		else {
			processRequestLogout(request, response);
		}

	}
}
