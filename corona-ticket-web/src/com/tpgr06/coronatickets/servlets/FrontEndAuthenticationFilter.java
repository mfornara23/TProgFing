package com.tpgr06.coronatickets.servlets;

import com.google.common.hash.Hashing;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import tpgr06.coronatickets.ws.publicador.ArtistaDTO;
import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;
import tpgr06.coronatickets.ws.publicador.UsuarioAuthDTO;
import tpgr06.coronatickets.ws.publicador.UsuarioDTO;

@WebFilter("/*")
public class FrontEndAuthenticationFilter implements Filter {
	private HttpServletRequest httpRequest;

	CoronaServices port;

	//@formatter:off
	private static final String[] LOGIN_REQUIRED_URL = {
			"/EspectaculoServlet?page=alta",
			"/EspectaculoServlet?page=finalizar",
			"/FuncionServlet?page=alta",
			"/FuncionServlet?page=sorteo",
			"/RegistroFuncionServlet",
			"/PaqueteServlet?page=agregar",
			"/PaqueteServlet?page=alta",
			"/UsuarioServlet?page=modificar"
	};
	//@formatter:on

	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession(false);

		boolean isLoggedIn = (session != null && session.getAttribute("email") != null);
		Cookie[] cookies = httpRequest.getCookies();


		String loginURI = httpRequest.getContextPath() + "/login";
		boolean isLoginRequest = httpRequest.getRequestURI()
				.equals(loginURI) && "GET".equals(httpRequest.getMethod());
		boolean isLoginPage = httpRequest.getRequestURI()
				.endsWith("/login") && "GET".equals(httpRequest.getMethod()) ;
		boolean isLoggingOut = httpRequest.getRequestURI()
				                       .endsWith("/login") && "POST".equals(httpRequest.getMethod()) && httpRequest.getAttribute("email") == null;

		if (isLoggedIn && (isLoginRequest || isLoginPage)) {
			// el usuario está logueado y está intentando de entrar de nuevo
			// se manda a homepage
			httpRequest.getRequestDispatcher("/")
					.forward(request, response);
		} else if (isLoggedIn && isLoggingOut) {
			// si el usuario se está deslogueando hace que continúe
			chain.doFilter(request, response);
		} else if (!isLoggedIn && isLoginRequired()) {
			// el usuario no está logueado y la página a la que quiere entrar
			// requiere autenticación


			String selector = "";
			String rawValidator = "";

			// revisa token de las cookies
			for (Cookie aCookie : cookies) {
				if (aCookie.getName().equals("selector")) {
					selector = aCookie.getValue();
				} else if (aCookie.getName().equals("validator")) {
					rawValidator = aCookie.getValue();
				}
			}
			// si hay cookies de autenticación, intenta validarlas
			if (!"".equals(selector) && !"".equals(rawValidator)) {
				UsuarioAuthDTO token = port.getAuthToken(selector);
				if (token != null) {
					String hashedValidatorDatabase = token.getValidator();
					String hashedValidatorCookie = Hashing.sha256().hashString(rawValidator, StandardCharsets.UTF_8).toString();

					if(hashedValidatorCookie.equals(hashedValidatorDatabase)) {
						// la sesión del usuario fue recordada y se loguea sin pedir credenciales
						UsuarioDTO usr = token.getUsuario();
						session = httpRequest.getSession();
						session.setAttribute("nickname", usr.getNickname());
						session.setAttribute("email", usr.getEmail());
						if(usr instanceof ArtistaDTO) {
							session.setAttribute("tipousuario","artista");
						}else {
							session.setAttribute("tipousuario", "espectador");
						}

						// update new token in storage
						String newSelector = RandomStringUtils.randomAlphanumeric(12);
						String newRawValidator =  RandomStringUtils.randomAlphanumeric(64);

						String newHashedValidator = Hashing.sha256().hashString(rawValidator, StandardCharsets.UTF_8).toString();

						token.setSelector(newSelector);
						token.setValidator(newHashedValidator);

						port.updateToken(newSelector, newHashedValidator);

						// update cookie
						Cookie cookieSelector = new Cookie("selector", newSelector);
						cookieSelector.setMaxAge(604800);

						Cookie cookieValidator = new Cookie("validator", newRawValidator);
						cookieValidator.setMaxAge(604800);

						// añade nuevas cookies a la respuesta
						httpResponse.addCookie(cookieSelector);
						httpResponse.addCookie(cookieValidator);

						chain.doFilter(request, response);
					}
				}
			} else {
				// caso contrario, pide login
				String loginPage = "/login";
				RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
				dispatcher.forward(request, response);
			}
		} else {
			// para otras páginas que no requieren autenticación o si el usuario
			// ya está logueado, continúa con el destino
			chain.doFilter(request, response);
		}
	}


	private boolean isLoginRequired() {
		String requestURL = httpRequest.getRequestURI();
		if (!"".equals(httpRequest.getQueryString())) {
			requestURL = requestURL + "?" + httpRequest.getQueryString();
		}

		for (String loginRequiredURL : LOGIN_REQUIRED_URL) {
			if (requestURL.contains(loginRequiredURL)) {
				return true;
			}
		}

		return false;
	}


	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
