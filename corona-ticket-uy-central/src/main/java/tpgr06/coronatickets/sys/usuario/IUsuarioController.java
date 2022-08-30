package tpgr06.coronatickets.sys.usuario;

import java.util.List;

import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;

public interface IUsuarioController {

	/**
	 * Returns a User by nickname
	 * @param nickname
	 * @return UsuarioDTO
	 */
	UsuarioDTO getUsuarioByNickname(String nickname);
	
	/**
	 * Returns a User by email
	 * @param email
	 * @return
	 */
	UsuarioDTO getUsuarioByEmail(String email);
	
	/**
	 * Adds a new User
	 * @param usuario
	 * @throws BadRequestException 
	 */
	void altaUsuario(UsuarioDTO usuario) throws BadRequestException;
	
	/**
	 * Get all the Users of the system
	 * @return List<UsuarioDTO>
	 */
	List<UsuarioDTO> listarUsuario();
	
	/***
	 * Updates an User of the system by passing a UsuarioDTO
	 * @param usuario
	 * @throws NotFoundException
	 */
	void actualizarUsuario(UsuarioDTO usuario) throws NotFoundException;

	/**
	 * Gets all espectators of the system
	 * @return
	 */
	List<EspectadorDTO> listarEspectadores();

	/**
	 * Returns true if the user identified by email can exchange
	 * @param email
	 * @return
	 * @throws NotFoundException if there is no user with such an email
	 */
	boolean puedeCanjear(String email) throws NotFoundException;

	/**
	 * Gets all the registers that have not been exchanged
	 * @param email
	 * @return
	 * @throws NotFoundException if there is no user with such an email
	 */
	List<String> getRegistrosSinCanjear(String email) throws NotFoundException;

	/**
	 * Valida existencia de usuario de email y password
	 * @param email
	 * @param password
	 * @param rememberMe indica si el usuario quiere que sea recordado en la plataforma
	 * @param selector del token
	 * @param hashedValidator generado del lado del servlet
	 * @return si fue posible loguearse
	 */
	boolean login(String email, String password, Boolean rememberMe, String selector, String hashedValidator);

	/**
	 * Borra token de usuario guardado
	 * @param selector
	 * @return si el usuario estaba logueado
	 */
	boolean logout(String selector);

	/**
	 * Obtiene al usuario autenticado con el selector
	 * @param selector
	 * @return el usuario o null si no lo encuentra
	 */
	UsuarioAuthDTO getAuthToken(String selector);

	/**
	 * Updates token
	 * @param selector
	 * @param validator
	 */
	void updateToken(String selector, String validator);
	/**
	 * Usuario seguidor quiere seguir a otro Usuario
	 * 
	 * @param nickUsuarioSeguidor
	 * @param nickUsuarioSeguido
	 * @throws NotFoundException Si alguno de los Usuarios no se encuentran en el sistema
	 * @throws BadRequestException Si el Usuario seguidor ya esta siguiendo al Usuario seguido
	 */
	void seguirUsuario(String nickUsuarioSeguidor, String nickUsuarioSeguido) throws NotFoundException, BadRequestException;
	
	/**
	 * Usuario seguidor deja de seguir a otro Usuario
	 * 
	 * @param nickUsuarioSeguidor
	 * @param nickUsuarioSeguido
	 * @throws NotFoundException Si alguno de los Usuarios no se encuentran en el sistema
	 * @throws BadRequestException Si el Usuario seguidor no esta siguiendo al Usuario seguido
	 */
	void dejarSeguirUsuario(String nickUsuarioSeguidor, String nickUsuarioSeguido) throws NotFoundException, BadRequestException;
}
