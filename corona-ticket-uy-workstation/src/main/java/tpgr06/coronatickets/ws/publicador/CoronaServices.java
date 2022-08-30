package tpgr06.coronatickets.ws.publicador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.Endpoint;

import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.Utils;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.espectaculo.IEspectaculoController;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.funcion.premio.PremioDTO;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.paquete.PaqueteDTO;
import tpgr06.coronatickets.sys.usuario.IUsuarioController;
import tpgr06.coronatickets.sys.usuario.UsuarioAuthDTO;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncionDTO;
import tpgr06.coronatickets.ws.config.ConfigUtils;
import tpgr06.coronatickets.ws.data.DataCategoria;
import tpgr06.coronatickets.ws.data.DataEspectaculo;
import tpgr06.coronatickets.ws.data.DataFuncion;
import tpgr06.coronatickets.ws.data.DataPaquete;
import tpgr06.coronatickets.ws.data.DataPlataforma;
import tpgr06.coronatickets.ws.data.DataUsuario;
import tpgr06.coronatickets.ws.data.StringListWrapper;


@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class CoronaServices {

	private Endpoint endpoint = null;
	private IEspectaculoController espectaculoController = null;
	private IPaqueteController paqueteController = null;
	private IUsuarioController usuarioController = null;

	public CoronaServices() throws BadRequestException, NotFoundException {
		Utils.cargarDatosDePrueba();
		espectaculoController = Factory.getInstance().getIEspectaculoController();
		paqueteController = Factory.getInstance().getIPaqueteController();
		usuarioController = Factory.getInstance().getIUsuarioController();
	}

	@WebMethod(exclude = true)
	public void publicar() throws FileNotFoundException, IOException {
		endpoint = Endpoint.publish("http://" + ConfigUtils.getInstance().getProperty("service.ip") + ":"
				+ ConfigUtils.getInstance().getProperty("service.port") + "/"
				+ ConfigUtils.getInstance().getProperty("service.path"), this);
	}

	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return endpoint;
	}

	// ************ Operaciones ***********//

	// EspectaculoController
	@WebMethod
	public DataEspectaculo buscarEspectaculos(String key) {

		DataEspectaculo dataEspec = new DataEspectaculo();
		dataEspec.setEspectaculo(espectaculoController.buscarEspectaculos(key));
		return dataEspec;

	}

	@WebMethod
	public DataCategoria listarCategorias() {

		DataCategoria dataCat = new DataCategoria();
		dataCat.setCategoria(espectaculoController.listarCategorias());
		return dataCat;

	}

	@WebMethod
	public EspectaculoDTO consultaEspectaculo(String nombreEspectaculo) throws NotFoundException {

		return espectaculoController.consultaEspectaculo(nombreEspectaculo);

	}

	@WebMethod
	public void altaEspectaculo(String nombre, String descripcion, Integer duracion, Integer cantMin, Integer cantMax,
			String url, Integer costo, Date fechaReg, String nombrePlataforma, String emailArtista,
			StringListWrapper categorias, String img, String descripcionPremio, Integer cantidadPremios, String video) throws BadRequestException, NotFoundException {

		espectaculoController.altaEspectaculo(nombre, descripcion, duracion, cantMin, cantMax, url, costo, fechaReg,
				nombrePlataforma, emailArtista,
				categorias.getDatos(), img, descripcionPremio, cantidadPremios, video);

	}
	
	@WebMethod
	public DataEspectaculo listarEspectaculosAceptados() throws NotFoundException {

		DataEspectaculo dataEspec = new DataEspectaculo();
		dataEspec.setEspectaculo(espectaculoController.listarEspectaculosAceptados());
		return dataEspec;

	}
	
	@WebMethod
	public DataEspectaculo listarEspectaculosFinalizados(String nicknameArtista) throws NotFoundException {

		DataEspectaculo dataEspec = new DataEspectaculo();
		dataEspec.setEspectaculo(espectaculoController.listarEspectaculosFinalizados(nicknameArtista));
		return dataEspec;

	}
	
	@WebMethod
	public void finalizarEspectaculo(String nombreEspectaculo) throws NotFoundException, BadRequestException {
		
		espectaculoController.finalizarEspectaculo(nombreEspectaculo);
	}


	@WebMethod
	public DataEspectaculo listarEspectaculos(String plataforma) throws NotFoundException {

		DataEspectaculo dataEspec = new DataEspectaculo();
		dataEspec.setEspectaculo(espectaculoController.listarEspectaculos(plataforma));
		return dataEspec;

	}

	@WebMethod
	public DataEspectaculo listarEspectaculosByArtista(String nickname) throws NotFoundException {
		DataEspectaculo dataEspec = new DataEspectaculo();
		dataEspec.setEspectaculo(espectaculoController.listarEspectaculosByArtista(nickname));
		return dataEspec;

	}

	@WebMethod
	public DataFuncion listarFuncionesByEspectaculo(String espectaculo) {

		DataFuncion dataFun = new DataFuncion();
		dataFun.setFuncion(espectaculoController.listarFuncionesByEspectaculo(espectaculo));
		return dataFun;

	}
	
	@WebMethod
	public DataFuncion listarFuncionesByEspectador(String nickname) throws NotFoundException {

		DataFuncion dataFun = new DataFuncion();
		dataFun.setFuncion(espectaculoController.listarFuncionesByEspectador(nickname));
		return dataFun;

	}


	@WebMethod
	public FuncionDTO getFuncionEspectaculo(String nombreFuncion, String nombreEspectaculo) throws NotFoundException {

		return espectaculoController.getFuncionEspectaculo(nombreFuncion, nombreEspectaculo);

	}

	@WebMethod
	public void altaFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion, Date fecha,
			StringListWrapper nicknameArtistasInvitados, Date fechaAlta, String img)
			throws NotFoundException, BadRequestException {

		String[] invitados = nicknameArtistasInvitados.getDatos()!=null? nicknameArtistasInvitados.getDatos().toArray(new String[0]) : null;
		espectaculoController.altaFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion, fecha,
				invitados, fechaAlta, img);

	}

	@WebMethod
	public RegistroFuncionDTO registroFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion,
			String espectadorEmail, Date date, String idregistro1, String idregistro2, String idregistro3)
			throws NotFoundException, BadRequestException {

		System.out.println("WS - r1> " + idregistro1 + " r2> " + idregistro2 + "r3> "+ idregistro3);
		return espectaculoController.registroFuncion(nombrePlataforma, nombreEspectaculo, nombreFuncion, espectadorEmail, date,
				idregistro1, idregistro2, idregistro3);

	}

	@WebMethod
	public RegistroFuncionDTO registroFuncionPaquete(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion,
			String espectadorEmail, Date date, String nombrePaquete) throws NotFoundException, BadRequestException {

		return espectaculoController.registroFuncionPaquete(nombrePlataforma, nombreEspectaculo, nombreFuncion,
				espectadorEmail, date, nombrePaquete);

	}
	

	@WebMethod
	public void valorarEspectaculo(String nombreEspectaculo, String nickNameEspectador, int puntaje) throws NotFoundException {
		
		espectaculoController.valorarEspectaculo(nombreEspectaculo, nickNameEspectador, puntaje);
		
	}
	
	@WebMethod
	public DataEspectaculo listarEspectaculosParaValorar(String nickNameEspectador) throws NotFoundException {
		
		DataEspectaculo espectaculos = new DataEspectaculo();
		espectaculos.setEspectaculo(espectaculoController.listarEspectaculosParaValorar(nickNameEspectador));
		return espectaculos;
		
	}
	
	// PaqueteController
	
	@WebMethod
	public void agregarEspectaculoAPaquete(String nombreEspectaculo, String nombrePaquete) throws NotFoundException, BadRequestException {
		
		paqueteController.agregarEspectaculoAPaquete(nombreEspectaculo, nombrePaquete);
		
	}

	@WebMethod
	public DataPaquete buscarPaquetes(String key) {

		DataPaquete dataPaq = new DataPaquete();
		dataPaq.setPaquete(paqueteController.buscarPaquetes(key));
		return dataPaq;

	}

	@WebMethod
	public DataPlataforma listarPlataformas() {

		DataPlataforma dataPlat = new DataPlataforma();
		dataPlat.setPlataforma(paqueteController.listarPlataformas());
		return dataPlat;

	}

	@WebMethod
	public DataPaquete listarPaquetesByEspectaculo(String nombreEspectaculo) {

		DataPaquete dataPaq = new DataPaquete();
		dataPaq.setPaquete(paqueteController.listarPaquetesByEspecatculo(nombreEspectaculo));
		return dataPaq;

	}

	@WebMethod
	public PaqueteDTO consultaPaquete(String nombrePaquete) throws NotFoundException {

		return paqueteController.consultaPaquete(nombrePaquete);

	}

	@WebMethod
	public DataCategoria listarCategoriasByPaquete(String nombrePaquete) throws NotFoundException {

		DataCategoria dataCat = new DataCategoria();
		dataCat.setCategoria(paqueteController.listarCategoriasByPaquete(nombrePaquete));
		return dataCat;

	}

	@WebMethod
	public DataPaquete listarPaquetes() {

		DataPaquete dataPaq = new DataPaquete();
		dataPaq.setPaquete(paqueteController.listarPaquetes());
		return dataPaq;

	}

	@WebMethod
	public void altaPaquete(String nombre, String descripcion, Date fechaInicio, Date fechaFin, Integer descuento,
			String imagen) throws BadRequestException {

		paqueteController.altaPaquete(nombre, descripcion, fechaInicio, fechaFin, descuento, imagen);

	}
	
	@WebMethod
	public void compraPaquete(String nombrePaquete, String emailEspectador, Date fechaCompra) throws NotFoundException, BadRequestException {
		
		paqueteController.compraPaquete(nombrePaquete, emailEspectador, fechaCompra);
		
	}
	
	@WebMethod
	public DataEspectaculo listarEspectaculosNoEnPaquete(String nombrePaquete, String nombrePlataforma) throws NotFoundException {

		DataEspectaculo dataEspec = new DataEspectaculo();
		dataEspec.setEspectaculo(paqueteController.listarEspectaculosNoEnPaquete(nombrePaquete,nombrePlataforma));
		return dataEspec;

	}
	
	
	// UsuarioController
	@WebMethod
	public UsuarioDTO getUsuarioByEmail(String email) {
		
		return usuarioController.getUsuarioByEmail(email);
		
	}
	
	@WebMethod
	public UsuarioDTO getUsuarioByNickname(String nickname) {
		
		return usuarioController.getUsuarioByNickname(nickname);
	}
	
	@WebMethod
	public DataUsuario listarUsuarios() {
		
		DataUsuario dataUser = new DataUsuario();
		dataUser.setUsuario(usuarioController.listarUsuario());
		return dataUser;
		
	}
	
	@WebMethod
	public boolean login(String email, String password, Boolean rememberMe, String selector, String hashedValidator) {
		
		return usuarioController.login(email, password, rememberMe, selector, hashedValidator);
		
	}

	@WebMethod
	public boolean logout(String selector) {

		return usuarioController.logout(selector);

	}

	@WebMethod
	public UsuarioAuthDTO getAuthToken(String selector) {

		return usuarioController.getAuthToken(selector);

	}

	@WebMethod
	public void updateToken(String selector, String validator) {

		usuarioController.updateToken(selector, validator);

	}
	
	@WebMethod
	public void actualizarUsuario(UsuarioDTO usuario) throws NotFoundException {
		
		usuarioController.actualizarUsuario(usuario);
		
	}
	
	@WebMethod
	public void altaUsuario(UsuarioDTO usuario) throws BadRequestException {
		
		usuarioController.altaUsuario(usuario);
		
	}
	
	@WebMethod
	public void seguirUsuario(String seguidor, String seguido) throws NotFoundException, BadRequestException {
		
		usuarioController.seguirUsuario(seguidor, seguido);
		
	}
	
	@WebMethod
	public void dejarSeguirUsuario(String seguidor, String seguido) throws NotFoundException, BadRequestException {
		
		usuarioController.dejarSeguirUsuario(seguidor, seguido);
		
	}
	
	@WebMethod
	public PremioDTO sortearPremios(String nombreEspectaculo, String nombreFuncion) throws NotFoundException, BadRequestException {
		
		return espectaculoController.sortearPremios(nombreEspectaculo, nombreFuncion);
	}
	
	@WebMethod
	public void marcarFavorito(String nombreEspectaculo, String nicknameEspectador) {
		
		espectaculoController.marcarFavorito(nombreEspectaculo, nicknameEspectador);
	}
	
	@WebMethod
	public void desmarcarFavorito(String nombreEspectaculo, String nicknameEspectador) {
		
		espectaculoController.desmarcarFavorito(nombreEspectaculo, nicknameEspectador);
	}

}
