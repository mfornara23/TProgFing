package tpgr06.coronatickets.sys.usuario;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.usuario.artista.Artista;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;

public class UsuarioController implements IUsuarioController {

	private UsuarioHandler usuarioHandler;

	public UsuarioController() {
		this.usuarioHandler = UsuarioHandler.getInstance();
	}

	public UsuarioDTO getUsuarioByNickname(String nickname) {
		Usuario usuario = usuarioHandler.getByNickname(nickname);
		if (usuario == null) {
			return null;
		} else if (usuario instanceof Artista) {
			Artista artista = (Artista) usuario;
			return new ArtistaDTO(artista);
		} else {
			return new EspectadorDTO((Espectador) usuario);
		}
	}

	public UsuarioDTO getUsuarioByEmail(String email) {
		Usuario usuario = usuarioHandler.getByEmail(email);
		if (usuario == null) {
			return null;
		} else if (usuario instanceof Artista) {
			Artista artista = (Artista) usuario;
			return new ArtistaDTO(artista);
		} else {
			return new EspectadorDTO((Espectador) usuario);
		}
	}

	public void altaUsuario(UsuarioDTO usuario) throws BadRequestException {
		if(usuarioHandler.getByEmail(usuario.getEmail())!=null) {
			throw new BadRequestException("El usuario ya se encuentra registrado en el sistema");
		}
		if(usuarioHandler.getByNickname(usuario.getNickname())!=null) {
			throw new BadRequestException("El usuario ya se encuentra registrado en el sistema");
		}
		if (usuario instanceof ArtistaDTO) {
			ArtistaDTO art = (ArtistaDTO) usuario;
			Artista artista = new Artista(art.getNickname(), art.getNombre(), art.getApellido(), art.getEmail(),
					art.getPassword(), art.getFechaNac(), art.getDescripcion(), art.getBio(), art.getSitioWeb(), new HashMap<>(), new HashMap<>());
			artista.setImagen(usuario.getImagen());
			usuarioHandler.add(artista);
		} else {
			EspectadorDTO esp = (EspectadorDTO) usuario;
			Espectador espectador = new Espectador(esp.getNickname(), esp.getNombre(), esp.getApellido(), esp.getEmail(),
					esp.getFechaNac(), esp.getPassword(), new HashMap<>(), new HashMap<>());
			espectador.setImagen(usuario.getImagen());
			usuarioHandler.add(espectador);
		}
	}

	public List<UsuarioDTO> listarUsuario() {
		List<Usuario> users = new LinkedList<Usuario>(usuarioHandler.getAll());
		List<UsuarioDTO> usersDto = new LinkedList<UsuarioDTO>();

		for (Usuario u : users) {
			if (u instanceof Artista) {
				usersDto.add(new ArtistaDTO((Artista) u));
			}else {
				usersDto.add(new EspectadorDTO((Espectador) u));
			}
		}

		return usersDto;
	}

	public void actualizarUsuario(UsuarioDTO usuario) throws NotFoundException {
		if (usuario instanceof ArtistaDTO) {
			ArtistaDTO art = (ArtistaDTO) usuario;
			Artista artistaAActualizar = (Artista) usuarioHandler.getByNickname(usuario.getNickname());
			if (artistaAActualizar == null) {
				throw new NotFoundException(String.format("Usuario %s no encontrado",  usuario.getNickname()));
			}
			Artista artista = new Artista(art.getNickname(), art.getNombre(), art.getApellido(), art.getEmail(),
					art.getPassword(), art.getFechaNac(), art.getDescripcion(), art.getBio(), art.getBio(), artistaAActualizar.getSeguidos(), artistaAActualizar.getSeguidores());
			artista.setImagen(art.getImagen());
			usuarioHandler.update(artista);
		} else {
			EspectadorDTO esp = (EspectadorDTO) usuario;
			Espectador espectadorAActualizar = (Espectador) usuarioHandler.getByNickname(usuario.getNickname());
			if (espectadorAActualizar == null) {
				throw new NotFoundException(String.format("Usuario %s no encontrado",  usuario.getNickname()));
			}
			Espectador espectador = new Espectador(esp.getNickname(), esp.getNombre(), esp.getApellido(), esp.getEmail(),
					esp.getFechaNac(), esp.getPassword(), espectadorAActualizar.getSeguidos(), espectadorAActualizar.getSeguidores());
			espectador.setImagen(esp.getImagen());
			usuarioHandler.update(espectador);
		}
	}

	public List<EspectadorDTO> listarEspectadores() {
		List<Usuario> users = new LinkedList<Usuario>(usuarioHandler.getAll());
		List<EspectadorDTO> usersDto = new LinkedList<EspectadorDTO>();
		for (Usuario u : users) {
			if (u instanceof Espectador) 
				usersDto.add(new EspectadorDTO((Espectador) u));
		}

		return usersDto;
	}

	public boolean puedeCanjear(String email) throws NotFoundException {
		Espectador usr = (Espectador) usuarioHandler.getByEmail(email);
		if (usr == null) {
			throw new NotFoundException(String.format("El usuario de email %s no fue encontrado", email));
		}
		return usr.puedeCanjear();
	}

	public List<String> getRegistrosSinCanjear(String email) throws NotFoundException {
		Espectador usr = (Espectador) usuarioHandler.getByEmail(email);
		if (usr == null) {
			throw new NotFoundException(String.format("El usuario de email %s no fue encontrado", email));
		}
		return usr.getRegistrosSinCanjear();
	}

	@Override
	public boolean login(String email, String password, Boolean rememberMe, String selector, String hashedValidator) {
		Usuario usr = usuarioHandler.getByEmail(email);
		if (usr == null) {
			return false;
		}
		if (usr.getPassword().equals(password) && rememberMe) {
			usuarioHandler.saveLogin(usr, selector, hashedValidator);
			return true;
		} else if (usr.getPassword().equals(password) && !rememberMe) {
			return true;
		}
		return false;
	}
	
	public void updateToken(String selector, String validator) {
		usuarioHandler.updateAuthToken(selector, validator);
	}

	public UsuarioAuthDTO getAuthToken(String selector) {
		return usuarioHandler.getAuthToken(selector);
	}

	public boolean logout(String selector) {
		return usuarioHandler.deleteActiveSession(selector);
	}

	public void seguirUsuario(String nickUsuarioSeguidor, String nickUsuarioSeguido) throws NotFoundException, BadRequestException {
		Usuario usuarioSeguidor = usuarioHandler.getByNickname(nickUsuarioSeguidor);
		if (usuarioSeguidor == null) {
			throw new NotFoundException(String.format("El usuario de nickname %s no fue encontrado", nickUsuarioSeguidor));
		}
		Usuario usuarioSeguido = usuarioHandler.getByNickname(nickUsuarioSeguido);
		if (usuarioSeguido == null) {
			throw new NotFoundException(String.format("El usuario de nickname %s no fue encontrado", nickUsuarioSeguido));
		}

		Optional<Usuario> usuarioEncontrado = usuarioSeguidor.getSeguidos()
				.values()
				.stream()
				.filter(u -> u.equals(usuarioSeguido))
				.findFirst();
		if (usuarioEncontrado.isPresent()) {
			throw new BadRequestException(String.format("El usuario ya esta siguiendo al usuario de nickname %s", nickUsuarioSeguido));
		} else {
			usuarioSeguidor.addSeguido(usuarioSeguido);
			usuarioSeguido.addSeguidor(usuarioSeguidor);
		}
	}

	public void dejarSeguirUsuario(String nickUsuarioSeguidor, String nickUsuarioSeguido) throws NotFoundException, BadRequestException {
		Usuario usuarioSeguidor = usuarioHandler.getByNickname(nickUsuarioSeguidor);
		if (usuarioSeguidor == null) {
			throw new NotFoundException(String.format("El usuario de nickname %s no fue encontrado", nickUsuarioSeguidor));
		}
		Usuario usuarioSeguido = usuarioHandler.getByNickname(nickUsuarioSeguido);
		if (usuarioSeguido == null) {
			throw new NotFoundException(String.format("El usuario de nickname %s no fue encontrado", nickUsuarioSeguido));
		}

		Optional<Usuario> usuarioEncontrado = usuarioSeguidor.getSeguidos()
				.values()
				.stream()
				.filter(u -> u.equals(usuarioSeguido))
				.findFirst();
		if (usuarioEncontrado.isPresent()) {
			usuarioSeguidor.removeSeguido(nickUsuarioSeguido);
			usuarioSeguido.removeSeguidor(nickUsuarioSeguidor);	
		} else {
			throw new BadRequestException(String.format("El usuario no est� siguiendo al usuario de nickname %s", nickUsuarioSeguido));
		}
	}

}
