package tpgr06.coronatickets.sys.usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Optional;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.usuario.artista.Artista;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;

public class UsuarioHandler {

	private static UsuarioHandler instance = null;
	private Map<String, Usuario> usuarios;
	private List<UsuarioAuth> usuarioAuthList;

	private UsuarioHandler() {
		this.usuarios = new HashMap<String, Usuario>();
		this.usuarioAuthList = new ArrayList<>();
	}

	public static UsuarioHandler getInstance() {
		if (instance == null) {
			instance = new UsuarioHandler();
		}

		return instance;
	}

	/**
	 * Agrega un Usuario al mapa de usuarios.
	 * 
	 * @param u
	 */
	public void add(Usuario usuario) {
		if (usuario instanceof Artista) {
			this.usuarios.put(usuario.getEmail(), (Artista) usuario);
		} else {
			this.usuarios.put(usuario.getEmail(), (Espectador) usuario);
		}

	}

	/**
	 * Retorna Usuario por Email. Si no lo encuentra retorna null
	 *
	 * @param email
	 * @return Usuario
	 */
	public Usuario getByEmail(String email) {
		Usuario usuario = usuarios.get(email);
		if (usuario instanceof Artista) {
			return (Artista) usuario;
		} else {
			return (Espectador) usuario;
		}
	}

	/**
	 * Retorna Usuario por Nickname. Si no lo encuentra retorna null
	 *
	 * @param nickname
	 * @return Usuario
	 */
	public Usuario getByNickname(String nickname) {
		Iterator<Entry<String, Usuario>> iterator = usuarios.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Usuario> pair = (Map.Entry<String, Usuario>) iterator.next();
			if (pair.getValue().getNickname().equals(nickname)) {
				return pair.getValue() instanceof Artista ? (Artista) pair.getValue() : (Espectador) pair.getValue();
			}
		}
		return null;
	}

	/**
	 * Actualiza la informacion de un Usuario u
	 *
	 * @param usuario
	 */
	public void update(Usuario usuario) throws NotFoundException {

		Usuario userToUpdate = usuarios.get(usuario.getEmail());

		if (userToUpdate == null)
			throw new NotFoundException(String.format("El usuario de email %s no fue encontrado", usuario.getEmail()));

		userToUpdate.setNombre(usuario.getNombre());
		userToUpdate.setApellido(usuario.getApellido());
		userToUpdate.setFechaNac(usuario.getFechaNac());

		if (userToUpdate instanceof Artista) {
			((Artista) userToUpdate).setDescripcion(((Artista) usuario).getDescripcion());
			((Artista) userToUpdate).setBio(((Artista) usuario).getBio());
			((Artista) userToUpdate).setSitioWeb(((Artista) usuario).getSitioWeb());
		}
	}

	/**
	 * Obtiene todos los usuarios del sistema.
	 *
	 * @return List<Usuario> con todos los usuarios del Sistema.
	 */
	public List<Usuario> getAll() {
		List<Usuario> users = new LinkedList<Usuario>(usuarios.values());
		return users;
	}

	/**
	 * Obtiene todos los Artistas del sistema.
	 *
	 * @return List<Artista> con todos los Artistas del Sistema.
	 */
	public List<Artista> getArtistas() {
		List<Artista> artists = new LinkedList<Artista>();

		Iterator<Entry<String, Usuario>> iterador = usuarios.entrySet().iterator();
		while (iterador.hasNext()) {
			Map.Entry<String, Usuario> pair = (Map.Entry<String, Usuario>) iterador.next();
			if (pair.getValue() instanceof Artista) {
				artists.add((Artista) pair.getValue());
			}
		}

		return artists;
	}

	/**
	 * Obtiene todos los Espectador del sistema.
	 *
	 * @return List<Espectador> con todos los Espectadores del Sistema.
	 */
	public List<Espectador> getEspectadores() {
		List<Espectador> viewers = new LinkedList<Espectador>();

		Iterator<Entry<String, Usuario>> iterador = usuarios.entrySet().iterator();
		while (iterador.hasNext()) {
			Map.Entry<String, Usuario> pair = (Map.Entry<String, Usuario>) iterador.next();
			if (pair.getValue() instanceof Espectador) {
				viewers.add((Espectador) pair.getValue());
			}
		}

		return viewers;
	}

	/**
	 * Guarda la sesión del usuario logueado
	 *
	 * @param usuario
	 * @param selector
	 * @param hashedValidator
	 */
	public void saveLogin(Usuario usuario, String selector, String hashedValidator) {
		if (getByEmail(usuario.getEmail())!=null && isLogged(usuario.getEmail()) == null) {
			// Guarda token para la sesión iniciada
			UsuarioAuth newToken = new UsuarioAuth();
			Usuario usr = getByEmail(usuario.getEmail());

			newToken.setSelector(selector);
			newToken.setValidator(hashedValidator);

			newToken.setUsuario(usr);
			this.usuarioAuthList.add(newToken);
		}
	}

	/**
	 * Chequea si el usuario está logueado y lo devuelve
	 * @return Usuario
	 */
	public UsuarioDTO isLogged(String email) {
		Optional<UsuarioAuth> usuarioAuth = this.usuarioAuthList.stream()
				.filter(auth -> email.equals(auth.getUsuario().getEmail()))
				.findFirst();
		return usuarioAuth.map(auth -> new UsuarioDTO(auth.getUsuario()))
				.orElse(null);
	}

	/**
	 * Update auth token
	 * @param selector
	 * @param validator
	 */
	public void updateAuthToken(String selector, String validator) {
		Optional<UsuarioAuth> usuarioAuth = this.usuarioAuthList.stream()
				.filter(auth -> selector.equals(auth.getSelector()))
				.findFirst();
		usuarioAuth.ifPresent(auth -> auth.setValidator(validator));
	}

	/**
	 * Get auth token
	 * @param selector
	 * @return
	 */
	public UsuarioAuthDTO getAuthToken(String selector) {
		Optional<UsuarioAuth> usuarioAuth = this.usuarioAuthList.stream()
				.filter(auth -> selector.equals(auth.getSelector()))
				.findFirst();
		return usuarioAuth.map(UsuarioAuthDTO::new)
				.orElse(null);
	}
	
	/**
	 * Get selector
	 * @param email
	 * @return
	 */
	public String getSelectorByEmail(String email) {
		return this.usuarioAuthList.stream()
				.filter(auth -> email.equals(auth.getUsuario().getEmail()))
				.findFirst()
				.map(auth -> auth.getSelector())
				.orElse(null);
	}

	/**
	 * Borra la sesión con el selector enviado
	 * @param selector
	 * @return si el usuario estaba logueado
	 */
	public boolean deleteActiveSession(String selector) {
		Optional<UsuarioAuth> usuarioAuth = this.usuarioAuthList.stream()
				.filter(auth -> selector.equals(auth.getSelector()))
				.findFirst();
		return usuarioAuth.map(auth -> this.usuarioAuthList.remove(auth))
				.orElse(false);
	}

	public void clear() {
		this.usuarios.clear();
		this.usuarioAuthList.clear();
	}

}
