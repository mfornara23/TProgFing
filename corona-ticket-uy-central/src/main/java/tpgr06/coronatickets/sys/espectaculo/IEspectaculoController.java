package tpgr06.coronatickets.sys.espectaculo;

import java.util.Date;
import java.util.List;

import tpgr06.coronatickets.sys.categoria.CategoriaDTO;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.funcion.premio.PremioDTO;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncionDTO;

public interface IEspectaculoController {

	/**
	 * Crea un Espectaculo en el sistema
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param duracion
	 * @param cantMin
	 * @param cantMax
	 * @param url
	 * @param costo
	 * @param fechaReg
	 * @param nombrePlataforma
	 * @param emailArtista
	 * @param categorias
	 * @throws BadRequestException si ya existe un Espectaculo con el mismo nombre
	 *                             en el sistema
	 * @throws NotFoundException   si la Plataforma o el Artista organizador no
	 *                             existen en el sistema
	 */
	void altaEspectaculo(String nombre, String descripcion, Integer duracion, Integer cantMin, Integer cantMax,
			String url, Integer costo, Date fechaReg, String nombrePlataforma, String emailArtista,
			List<String> categorias, String img, String descripcionPremios, Integer cantidadPremios, String video) throws BadRequestException, NotFoundException;

	/**
	 * Actualiza el estado del espectaculo pasado por parametro al estado
	 * nuevoEstado
	 * 
	 * @param espectaculo
	 * @param nuevoEstado
	 */
	void cambiarEstadoEspectaculo(EspectaculoDTO espectaculo, EstadoEspectaculo nuevoEstado);

	/**
	 * 
	 * @param espectaculo
	 * @param nuevoEstado
	 */
	void cambiarEstadoEspectaculo(Espectaculo espectaculo, EstadoEspectaculo nuevoEstado);

	/**
	 * El sistema lista los Espectaculos existentes en esa plataforma
	 * 
	 * @param plataforma
	 * @return lista todos los Espectaculos de la plataforma
	 * @throws NotFoundException si la plataforma no se encuentra en el sistema
	 */
	List<EspectaculoDTO> listarEspectaculos(String plataforma) throws NotFoundException;

	/**
	 * El sistema lista los Espectaculos existentes en estado INGRESADO
	 * 
	 * @param plataforma
	 * @return Espectaculos en estado INGRESADO
	 * @throws NotFoundException si la plataforma no se encuentra en el sistema
	 */
	List<EspectaculoDTO> listarEspectaculosIngresados() throws NotFoundException;
	
	/**
	 * El sistema lista los Espectaculos existentes en estado INGRESADO
	 * 
	 * @param plataforma
	 * @return Espectaculos en estado INGRESADO
	 * @throws NotFoundException si la plataforma no se encuentra en el sistema
	 */
	List<EspectaculoDTO> listarEspectaculosAceptados() throws NotFoundException;

	/**
	 * Devuelve la informacion completa del Espectaculo seleccionado, con sus
	 * Funciones y Paquetes
	 * 
	 * @param nombre
	 * @return el Espectaculo con la info de sus Funciones y Paquetes
	 * @throws NotFoundException si el Espectaculo no existe en el sistema
	 */
	EspectaculoDTO consultaEspectaculo(String nombre) throws NotFoundException;

	/**
	 * 
	 * @param nickname
	 * @return
	 * @throws NotFoundException
	 */
	List<EspectaculoDTO> listarEspectaculosByArtista(String nickname) throws NotFoundException;

	/**
	 * 
	 * @param nickname
	 * @return
	 * @throws NotFoundException
	 */
	List<FuncionDTO> listarFuncionesByEspectador(String nickname) throws NotFoundException;

	/**
	 * 
	 * @param funcion
	 * @param espectaculo
	 * @return
	 * @throws NotFoundException
	 */
	FuncionDTO getFuncionEspectaculo(String funcion, String espectaculo) throws NotFoundException;

	/**
	 * 
	 * @param espectaculo
	 * @return
	 */
	List<FuncionDTO> listarFuncionesByEspectaculo(String espectaculo);

	/**
	 * Registra una nueva funcion en el sistema
	 * 
	 * @param nombrePlataforma
	 * @param nombreEspectaculo
	 * @param nombreFuncion
	 * @param fechaFuncion
	 * @param artistasInvitados
	 * @param fechaAlta
	 * @param img
	 * @throws NotFoundException
	 * @throws BadRequestException
	 */
	void altaFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion, Date fechaFuncion,
			String[] artistasInvitados, Date fechaAlta, String img) throws NotFoundException, BadRequestException;

	/**
	 * Registra el espectador a la funcion del espectaculo
	 * 
	 * @param date
	 * @param plataforma
	 * @param espectaculo
	 * @param funcion
	 * @param email
	 * @param object
	 * @param object2
	 * @param object3
	 * @throws NotFoundException
	 * @throws BadRequestException
	 */
	RegistroFuncionDTO registroFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion,
			String espectadorEmail, Date date, String idregistro1, String idregistro2, String idregistro3)
			throws NotFoundException, BadRequestException;

	/**
	 * 
	 * @param nombrePlataforma
	 * @param nombreEspectaculo
	 * @param nombreFuncion
	 * @param espectadorEmail
	 * @param date
	 * @param nombrePaquete
	 * @return
	 * @throws NotFoundException
	 * @throws BadRequestException
	 */
	RegistroFuncionDTO registroFuncionPaquete(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion,
			String espectadorEmail, Date date, String nombrePaquete) throws NotFoundException, BadRequestException;

	/**
	 * Da de alta una categoria
	 * 
	 * @param category
	 */
	void altaCategoria(String category) throws BadRequestException;

	/**
	 * Devuelve una Categoria por nombre
	 * 
	 * @param name
	 * @return
	 */
	CategoriaDTO getCategoria(String name) throws NotFoundException;

	/**
	 * Devuelve todas las Categorias del sistema
	 * 
	 * @return
	 */
	List<CategoriaDTO> listarCategorias();
	
	/**
	 * Devuelve espectaculos que tienen key en el nombre o en la descripcion
	 * @param key
	 * @return
	 */
	List<EspectaculoDTO> buscarEspectaculos(String key);
	
	/**
	 * 
	 * @param nombreEspectaculo
	 * @param nicknameEspectador
	 */
	public void marcarFavorito(String nombreEspectaculo, String nicknameEspectador);
	
	/**
	 * 
	 * @param nombreEspectaculo
	 * @param nicknameEspectador
	 */
	public void desmarcarFavorito(String nombreEspectaculo, String nicknameEspectador);
	
	/**
	 * 
	 * @param nombreEspectaculo
	 * @param nicknameEspectador
	 * @return
	 */
	public boolean esFavorito(String nombreEspectaculo, String nicknameEspectador);
	
	/**
	 * 
	 * @param nombreEspectaculo
	 * @return
	 */
	public int cantidadDeFavorito(String nombreEspectaculo);
	
	/**
	 * Cambia el estado del Espectaculo a FINALIZADO
	 * 
	 * @param nombreEspectaculo
	 * @throws BadRequestException 
	 * @throws NotFoundException 
	 */
	void finalizarEspectaculo(String nombreEspectaculo) throws NotFoundException, BadRequestException;

	/**
	 * Lista los espectaculos finalizados del artista
	 * 
	 * @param nicknameArtista
	 * @return
	 * @throws NotFoundException
	 */
	List<EspectaculoDTO> listarEspectaculosFinalizados(String nicknameArtista) throws NotFoundException;

	/**
	 * 
	 * @param nombreEspectaculo
	 * @param nombreFuncion
	 * @return
	 * @throws NotFoundException
	 * @throws BadRequestException
	 */

	PremioDTO sortearPremios(String nombreEspectaculo, String nombreFuncion) throws NotFoundException, BadRequestException;
	
	/**
	 * 
	 * @param nickNameEspectador
	 * @return
	 * @throws NotFoundException
	 */
	List<EspectaculoDTO> listarEspectaculosParaValorar(String nickNameEspectador) throws NotFoundException;
	 
	/**
	 * 
	 * @param nombreEspectaculo
	 * @param nickNameEspectador
	 * @param puntaje
	 * @throws NotFoundException 
	 */
	void valorarEspectaculo(String nombreEspectaculo, String nickNameEspectador, int puntaje) throws NotFoundException;
	
}
