package tpgr06.coronatickets.sys.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.usuario.IUsuarioController;
import tpgr06.coronatickets.sys.usuario.Usuario;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.UsuarioHandler;
import tpgr06.coronatickets.sys.usuario.artista.Artista;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncion;

public class UsuariosTests {
	
	private Factory factory;
	private IUsuarioController usuarioCtrll;
	private UsuarioHandler usuarioHandler;
	private String nickname1 = "poc1";
	private String email1 = "poc1@test.com";
	private String password = "password";
	private String nickname2 = "poc2";
	private String email2 = "poc2@test.com";
	private Date birth = Date.from(Instant.now());
	UsuarioDTO espectador;
	UsuarioDTO artista;

	@Before
	public void init() {
		this.factory = Factory.getInstance();
		this.usuarioCtrll = factory.getIUsuarioController();
		this.usuarioHandler = UsuarioHandler.getInstance();
		this.usuarioHandler.clear();
		espectador = new EspectadorDTO(nickname1, "nombre1", "apellido1", email1, birth, password, null, null);
		artista = new ArtistaDTO(nickname2, "Art", "Ista", email2, birth,password,null,null, "shortDesc", "bioioio", "test.com");
	}
	
	@Test
	public void addAndUpdateUsers() throws NotFoundException, BadRequestException {
		
		usuarioCtrll.altaUsuario(espectador);
		usuarioCtrll.altaUsuario(artista);
		
		assertEquals(nickname1, usuarioHandler.getByNickname(nickname1).getNickname());
		assertEquals(email1, usuarioHandler.getByEmail(email1).getEmail());
		
		assertEquals(nickname2, usuarioHandler.getByNickname(nickname2).getNickname());
		assertEquals(email2, usuarioHandler.getByEmail(email2).getEmail());
		
		String updatedInfo = "modificado";
		
		usuarioCtrll.actualizarUsuario( new EspectadorDTO(nickname1, updatedInfo, updatedInfo, email1, birth,password,null,null) );
		usuarioCtrll.actualizarUsuario( new ArtistaDTO(nickname2, updatedInfo, updatedInfo, email2, birth,password,null,null, updatedInfo, updatedInfo, updatedInfo) );
		
		assertEquals(nickname1, usuarioHandler.getByNickname(nickname1).getNickname());
		assertEquals(email1, usuarioHandler.getByEmail(email1).getEmail());
		assertEquals(updatedInfo,usuarioHandler.getByEmail(email1).getNombre());
		assertEquals(updatedInfo,usuarioHandler.getByEmail(email1).getApellido());	
		assertEquals(birth,usuarioHandler.getByEmail(email1).getFechaNac());
		
		ArtistaDTO modArtist = new ArtistaDTO((Artista) usuarioHandler.getByEmail(email2));
		assertEquals(nickname2, usuarioHandler.getByNickname(nickname2).getNickname());
		assertEquals(email2, usuarioHandler.getByEmail(email2).getEmail());
		assertEquals(updatedInfo,modArtist.getNombre());
		assertEquals(updatedInfo,modArtist.getApellido());	
		assertEquals(updatedInfo,modArtist.getDescripcion());	
		assertEquals(updatedInfo,modArtist.getBio());	
		assertEquals(updatedInfo,modArtist.getSitioWeb());	
		assertEquals(birth,usuarioHandler.getByEmail(email1).getFechaNac());
	}
	
	@Test
	public void getUsers() throws BadRequestException {
				
		usuarioCtrll.altaUsuario(espectador);
		usuarioCtrll.altaUsuario(artista);
		
		List<UsuarioDTO> espectadors = usuarioCtrll.listarUsuario();
		List<Espectador> especs = usuarioHandler.getEspectadores();
		List<Artista> artists = usuarioHandler.getArtistas();
		
		assertEquals(nickname1, usuarioCtrll.getUsuarioByNickname(nickname1).getNickname());
		assertEquals(email1, usuarioCtrll.getUsuarioByEmail(email1).getEmail());
		assertEquals(nickname2, usuarioCtrll.getUsuarioByNickname(nickname2).getNickname());
		assertEquals(email2, usuarioCtrll.getUsuarioByEmail(email2).getEmail());
		
		assertEquals(2, espectadors.size());
		assertEquals(1, especs.size());
		assertEquals(1, artists.size());
		
		assertNull(usuarioCtrll.getUsuarioByEmail("email@noexiste.com"));
		assertNull(usuarioCtrll.getUsuarioByNickname("NickNoExiste"));
	}

	@Test
	public void listarEspectadores_ok() {
		Espectador espectador = new Espectador("Espectador1", "Nombre", "Apellido", "email", Date.from(Instant.now()),password,null,null);
		Espectador espectador2 = new Espectador("Espectador2", "Nombre", "Apellido", "email2", Date.from(Instant.now()),password,null,null);
		usuarioHandler.add(espectador);
		usuarioHandler.add(espectador2);

		List<EspectadorDTO> espectadores = usuarioCtrll.listarEspectadores();

		assertEquals(2, espectadores.size());
	}

	@Test
	public void listarEspectadores_empty() {
		List<EspectadorDTO> espectadores = usuarioCtrll.listarEspectadores();

		assertEquals(0, espectadores.size());
	}

	@Test
	public void puedeCanjear() throws NotFoundException {
		Espectador espectador = new Espectador("Espectador1", "Nombre", "Apellido", "email", Date.from(Instant.now()),password,null,null);
		RegistroFuncion registroFuncion = new RegistroFuncion();
		registroFuncion.setCanjeado(false);

		RegistroFuncion registroFuncion1 = new RegistroFuncion();
		registroFuncion1.setCanjeado(false);

		RegistroFuncion registroFuncion2 = new RegistroFuncion();
		registroFuncion2.setCanjeado(false);
		espectador.setRegistros(Arrays.asList(registroFuncion, registroFuncion1, registroFuncion2));

		usuarioHandler.add(espectador);

		assertTrue(usuarioCtrll.puedeCanjear(espectador.getEmail()));
		assertThrows(NotFoundException.class, () -> usuarioCtrll.puedeCanjear("email no existente"));

	}

	@Test
	public void getRegistrosSinCanjear() throws NotFoundException {
		Espectador espectador = new Espectador("Espectador1", "Nombre", "Apellido", "email", Date.from(Instant.now()),password,null,null);
		RegistroFuncion registroFuncion = new RegistroFuncion();
		registroFuncion.setCanjeado(false);
		registroFuncion.setId("registro1");

		RegistroFuncion registroFuncion1 = new RegistroFuncion();
		registroFuncion1.setCanjeado(false);
		registroFuncion1.setId("registro2");

		RegistroFuncion registroFuncion2 = new RegistroFuncion();
		registroFuncion2.setCanjeado(false);
		registroFuncion2.setId("registro3");

		espectador.setRegistros(Arrays.asList(registroFuncion, registroFuncion1, registroFuncion2));

		usuarioHandler.add(espectador);

		List<String> registrosSinCanjear = usuarioCtrll.getRegistrosSinCanjear(espectador.getEmail());
		assertEquals(3, registrosSinCanjear.size());
		assertThrows(NotFoundException.class, () -> usuarioCtrll.getRegistrosSinCanjear("email no existente"));
	}
	
	@Test
	public void seguirUsuario_ok() throws NotFoundException, BadRequestException {
		usuarioCtrll.altaUsuario(espectador);
		usuarioCtrll.altaUsuario(artista);
		
		usuarioCtrll.seguirUsuario(nickname1, nickname2);
		
		Map<String, Usuario> seguidos = usuarioHandler.getByNickname(nickname1).getSeguidos();
		Map<String, Usuario> seguidores = usuarioHandler.getByNickname(nickname2).getSeguidores();
		assertEquals(1, seguidos.size());
		assertEquals(1, seguidores.size());

		Optional<Usuario> usuarioEncontrado = seguidores
				.values()
				.stream()
				.filter(u -> u.equals(usuarioHandler.getByNickname(nickname1)))
				.findFirst();
		assertEquals(true, usuarioEncontrado.isPresent());
	}
	
	@Test
	public void seguirUsuario_throwsNotFound1() throws NotFoundException, BadRequestException {
		Espectador usuarioSeguidor = new Espectador(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(),password,null,null);
		Artista usuarioSeguido = new Artista(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(),artista.getPassword(), artista.getFechaNac(), "shortDesc", "bioioio", "test.com",null,null);
		usuarioHandler.add(usuarioSeguido);
		
		// NotFound_usuarioSeguidor
		assertThrows(NotFoundException.class, () -> usuarioCtrll.seguirUsuario(nickname1, nickname2));
		
		Map<String, Usuario> seguidos = usuarioSeguidor.getSeguidos();
		Map<String, Usuario> seguidores = usuarioSeguido.getSeguidores();
		assertEquals(0, seguidos.size());
		assertEquals(0, seguidores.size());
	}
	
	@Test
	public void seguirUsuario_throwsNotFound2() throws NotFoundException, BadRequestException {
		Espectador usuarioSeguidor = new Espectador(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(),password,null,null);
		Artista usuarioSeguido = new Artista(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(),artista.getPassword(), artista.getFechaNac(), "shortDesc", "bioioio", "test.com",null,null);
		usuarioHandler.add(usuarioSeguidor);
		
		// NotFound_usuarioSeguido
		assertThrows(NotFoundException.class, () -> usuarioCtrll.seguirUsuario(nickname1, nickname2));

		Map<String, Usuario> seguidos = usuarioSeguidor.getSeguidos();
		Map<String, Usuario> seguidores = usuarioSeguido.getSeguidores();
		assertEquals(0, seguidos.size());
		assertEquals(0, seguidores.size());
	}
	
	@Test
	public void seguirUsuario_throwsBadRequest() throws NotFoundException, BadRequestException {
		Espectador usuarioSeguidor = new Espectador(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(),password,null,null);
		Artista usuarioSeguido = new Artista(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(),artista.getPassword(), artista.getFechaNac(), "shortDesc", "bioioio", "test.com",null,null);
		usuarioHandler.add(usuarioSeguidor);
		usuarioHandler.add(usuarioSeguido);
		
		usuarioSeguidor.addSeguido(usuarioSeguido);
		usuarioSeguido.addSeguidor(usuarioSeguidor);
		
		// BadRequest
		assertThrows(BadRequestException.class, () -> usuarioCtrll.seguirUsuario(nickname1, nickname2));

		Map<String, Usuario> seguidos = usuarioSeguidor.getSeguidos();
		Map<String, Usuario> seguidores = usuarioSeguido.getSeguidores();
		assertEquals(1, seguidos.size());
		assertEquals(1, seguidores.size());
		
		Optional<Usuario> usuarioEncontrado = seguidores
				.values()
				.stream()
				.filter(u -> u.equals(usuarioSeguidor))
				.findFirst();
		assertEquals(true, usuarioEncontrado.isPresent());
	}
	
	@Test
	public void dejarSeguirUsuario_ok() throws NotFoundException, BadRequestException {
		Espectador usuarioSeguidor = new Espectador(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(),password,null,null);
		Artista usuarioSeguido = new Artista(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(),artista.getPassword(), artista.getFechaNac(), "shortDesc", "bioioio", "test.com",null,null);
		usuarioHandler.add(usuarioSeguidor);
		usuarioHandler.add(usuarioSeguido);
		
		usuarioSeguidor.addSeguido(usuarioSeguido);
		usuarioSeguido.addSeguidor(usuarioSeguidor);
		
		usuarioCtrll.dejarSeguirUsuario(nickname1, nickname2);
		
		Map<String, Usuario> seguidos = usuarioHandler.getByNickname(nickname1).getSeguidos();
		Map<String, Usuario> seguidores = usuarioHandler.getByNickname(nickname2).getSeguidores();
		assertEquals(0, seguidos.size());
		assertEquals(0, seguidores.size());
	}
	
	@Test
	public void dejarSeguirUsuario_throwsNotFound1() throws NotFoundException, BadRequestException {
		Espectador usuarioSeguidor = new Espectador(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(),password,null,null);
		Artista usuarioSeguido = new Artista(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(),artista.getPassword(), artista.getFechaNac(), "shortDesc", "bioioio", "test.com",null,null);
		usuarioHandler.add(usuarioSeguido);
		
		usuarioSeguidor.addSeguido(usuarioSeguido);
		usuarioSeguido.addSeguidor(usuarioSeguidor);
		
		// NotFound_usuarioSeguidor
		assertThrows(NotFoundException.class, () -> usuarioCtrll.dejarSeguirUsuario(nickname1, nickname2));

		Map<String, Usuario> seguidos = usuarioSeguidor.getSeguidos();
		Map<String, Usuario> seguidores = usuarioSeguido.getSeguidores();
		assertEquals(1, seguidos.size());
		assertEquals(1, seguidores.size());
		
		Optional<Usuario> usuarioEncontrado = seguidores
				.values()
				.stream()
				.filter(u -> u.equals(usuarioSeguidor))
				.findFirst();
		assertEquals(true, usuarioEncontrado.isPresent());
	}
	
	@Test
	public void dejarSeguirUsuario_throwsNotFound2() throws NotFoundException, BadRequestException {
		Espectador usuarioSeguidor = new Espectador(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(),password,null,null);
		Artista usuarioSeguido = new Artista(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(),artista.getPassword(), artista.getFechaNac(), "shortDesc", "bioioio", "test.com",null,null);
		usuarioHandler.add(usuarioSeguidor);
		
		usuarioSeguidor.addSeguido(usuarioSeguido);
		usuarioSeguido.addSeguidor(usuarioSeguidor);
		
		// NotFound_usuarioSeguido
		assertThrows(NotFoundException.class, () -> usuarioCtrll.dejarSeguirUsuario(nickname1, nickname2));

		Map<String, Usuario> seguidos = usuarioSeguidor.getSeguidos();
		Map<String, Usuario> seguidores = usuarioSeguido.getSeguidores();
		assertEquals(1, seguidos.size());
		assertEquals(1, seguidores.size());
		
		Optional<Usuario> usuarioEncontrado = seguidores
				.values()
				.stream()
				.filter(u -> u.equals(usuarioSeguidor))
				.findFirst();
		assertEquals(true, usuarioEncontrado.isPresent());
	}
	
	@Test
	public void dejarSeguirUsuario_throwsBadRequest() throws NotFoundException, BadRequestException {
		Espectador usuarioSeguidor = new Espectador(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(),password,null,null);
		Artista usuarioSeguido = new Artista(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(),artista.getPassword(), artista.getFechaNac(), "shortDesc", "bioioio", "test.com",null,null);
		usuarioHandler.add(usuarioSeguidor);
		usuarioHandler.add(usuarioSeguido);
		
		// BadRequest
		assertThrows(BadRequestException.class, () -> usuarioCtrll.dejarSeguirUsuario(nickname1, nickname2));

		Map<String, Usuario> seguidos = usuarioSeguidor.getSeguidos();
		Map<String, Usuario> seguidores = usuarioSeguido.getSeguidores();
		assertEquals(0, seguidos.size());
		assertEquals(0, seguidores.size());
	}
	
	@Test
	public void login_ok() throws BadRequestException {	
		usuarioCtrll.altaUsuario(espectador);
		assertEquals(usuarioCtrll.login(espectador.getEmail(), espectador.getPassword(), false, "", ""),true);
	}
	
	@Test
	public void login_wrong_password() throws BadRequestException {
		usuarioCtrll.altaUsuario(espectador);
		assertEquals(usuarioCtrll.login(espectador.getEmail(), "wrong_password", false, "", ""),false);
	}
	
	@Test
	public void login_user_not_found() {	
		assertEquals(usuarioCtrll.login(espectador.getEmail(), espectador.getPassword(), false, "", ""),false);

	}
	
	@Test
	public void token_test() throws BadRequestException {
		usuarioCtrll.altaUsuario(espectador);
		assertEquals(usuarioCtrll.login(espectador.getEmail(), espectador.getPassword(),
				false, "OLd5gV396QNn", "TEmHcDV3oEVR0BNBFtIGVcmJan6DxiuHsMX08aqUqr9TNJeWBgLHyyo4GsVPgrdh"),true);
		assertNull(usuarioHandler.getAuthToken("OLd5gV396QNn"));
		assertEquals(usuarioCtrll.login(espectador.getEmail(), espectador.getPassword(),
				true, "OLd5gV396QNn", "TEmHcDV3oEVR0BNBFtIGVcmJan6DxiuHsMX08aqUqr9TNJeWBgLHyyo4GsVPgrdh"),true);
		assertEquals("OLd5gV396QNn", usuarioHandler.getAuthToken("OLd5gV396QNn").getSelector());
	}
		
		
	
	
	
}
