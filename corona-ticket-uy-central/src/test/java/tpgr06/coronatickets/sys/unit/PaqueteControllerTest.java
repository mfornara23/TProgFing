package tpgr06.coronatickets.sys.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.categoria.Categoria;
import tpgr06.coronatickets.sys.categoria.CategoriaDTO;
import tpgr06.coronatickets.sys.categoria.CategoriaHandler;
import tpgr06.coronatickets.sys.espectaculo.Espectaculo;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoHandler;
import tpgr06.coronatickets.sys.espectaculo.EstadoEspectaculo;
import tpgr06.coronatickets.sys.espectaculo.IEspectaculoController;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.paquete.Paquete;
import tpgr06.coronatickets.sys.paquete.PaqueteDTO;
import tpgr06.coronatickets.sys.paquete.PaqueteHandler;
import tpgr06.coronatickets.sys.plataforma.Plataforma;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;
import tpgr06.coronatickets.sys.plataforma.PlataformaHandler;
import tpgr06.coronatickets.sys.usuario.UsuarioHandler;
import tpgr06.coronatickets.sys.usuario.artista.Artista;
import tpgr06.coronatickets.sys.usuario.espectador.CompraPaquete;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;

public class PaqueteControllerTest {

	private Factory factory;
	private IPaqueteController paqueteController;
	private PaqueteHandler paqueteHandler;
	private IEspectaculoController espectaculoController;
	private PlataformaHandler plataformaHandler;
	private EspectaculoHandler espectaculoHandler;
	private CategoriaHandler categoriaHandler;
	private UsuarioHandler usuarioHandler;

	@Before
	public void init() {
		this.factory = Factory.getInstance();
		this.paqueteController = factory.getIPaqueteController();
		this.espectaculoController = factory.getIEspectaculoController();
		this.paqueteHandler = PaqueteHandler.getInstance();
		this.plataformaHandler = PlataformaHandler.getInstance();
		this.espectaculoHandler = EspectaculoHandler.getInstance();
		this.categoriaHandler = CategoriaHandler.getInstance();
		this.usuarioHandler = UsuarioHandler.getInstance();
		paqueteHandler.clear();
		plataformaHandler.clear();
		espectaculoHandler.clear();
		categoriaHandler.clear();
		usuarioHandler.clear();
	}

	@Test
	public void testListarPaquetes_ok() {
		// When
		paqueteHandler.add(new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));
		paqueteHandler.add(new Paquete("Paquete2", "Segundo paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));

		// Do
		List<PaqueteDTO> paquetes = paqueteController.listarPaquetes();

		// Then
		assertEquals(2, paquetes.size());
		assertTrue(paquetes.stream().anyMatch(paqueteDTO -> "Paquete1".equals(paqueteDTO.getNombre())));
	}

	@Test
	public void testListarPaquetes_vacio() {
		// when

		// Do
		List<PaqueteDTO> paquetes = paqueteController.listarPaquetes();

		// Then
		assertEquals(0, paquetes.size());
	}

	@Test
	public void consultaPaquete_ok() throws NotFoundException {
		// when
		paqueteHandler.add(new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));
		paqueteHandler.add(new Paquete("Paquete2", "Segundo paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));

		// Do
		PaqueteDTO paquete = paqueteController.consultaPaquete("Paquete1");

		// Then
		assertEquals("Paquete1", paquete.getNombre());
		assertEquals("Primer paquete", paquete.getDescripcion());
	}

	@Test
	public void consultaPaquete_throwsNotFound() throws NotFoundException {
		// when
		paqueteHandler.add(new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));

		assertThrows(NotFoundException.class, () -> paqueteController.consultaPaquete("Paquete3"));
	}

	@Test
	public void testBuscarPaqueteNombre_ok() {
		// When
		paqueteHandler.add(new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));
		paqueteHandler.add(new Paquete("Paquete2", "Segundo paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));

		// Do
		List<PaqueteDTO> paquetes = paqueteController.buscarPaquetes("quete1");

		// Then
		assertEquals(1, paquetes.size());
		assertEquals("Paquete1", paquetes.get(0).getNombre());
	}

	@Test
	public void testBuscarPaqueteNombreNotFound_ok() {
		// When
		paqueteHandler.add(new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));
		paqueteHandler.add(new Paquete("Paquete2", "Segundo paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));

		// Do
		List<PaqueteDTO> paquetes = paqueteController.buscarPaquetes("Paquete3");

		// Then
		assertTrue(paquetes.isEmpty());
	}

	@Test
	public void testBuscarPaqueteDescripcion_ok() {
		// When
		paqueteHandler.add(new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));
		paqueteHandler.add(new Paquete("Paquete2", "Segundo paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));

		// Do
		List<PaqueteDTO> paquetes = paqueteController.buscarPaquetes("primer");

		// Then
		assertEquals(1, paquetes.size());
		assertEquals("Paquete1", paquetes.get(0).getNombre());
	}

	@Test
	public void testBuscarPaqueteDescripcionNotFound_ok() {
		// When
		paqueteHandler.add(new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));
		paqueteHandler.add(new Paquete("Paquete2", "Segundo paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0));

		// Do
		List<PaqueteDTO> paquetes = paqueteController.buscarPaquetes("z");

		// Then
		assertTrue(paquetes.isEmpty());
	}

	@Test
	public void testListarPlataformas_ok() {
		// When
		plataformaHandler.add(new Plataforma("Plataforma1", "Descripcion", "url"));
		plataformaHandler.add(new Plataforma("Plataforma2", "Descripcion2", "url2"));

		// Do
		List<PlataformaDTO> plataformas = paqueteController.listarPlataformas();

		// Then
		assertEquals(2, plataformas.size());
		assertTrue(plataformas.stream().anyMatch(plataformaDTO -> "Plataforma1".equals(plataformaDTO.getNombre())));
	}

	@Test
	public void testListarPlataformas_vacio() {
		// when

		// Do
		List<PlataformaDTO> plataformas = paqueteController.listarPlataformas();

		// Then
		assertEquals(0, plataformas.size());
	}

	@Test
	public void listarEspectaculosNoEnPaquete() throws NotFoundException {
		// when
		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));
		List<Categoria> catList2 = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria2"));

		Plataforma facebook = new Plataforma();
		facebook.setNombre("Facebook");

		Plataforma youtube = new Plataforma();
		youtube.setNombre("YouTube");

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com", "pwd",
				Date.from(Instant.now()), "Descripcion", "Biografia", "Sitio Web", null, null);

		usuarioHandler.add(artista1);

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), facebook, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		Espectaculo espectaculo2 = new Espectaculo("Espectaculo 2", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), youtube, artista1, catList2);

		espectaculo1.setEstado(EstadoEspectaculo.ACEPTADO);
		espectaculo2.setEstado(EstadoEspectaculo.ACEPTADO);

		Map<String, Espectaculo> espectaculosMap = new HashMap<>();
		espectaculosMap.put(espectaculo1.getNombre(), espectaculo1);
		espectaculosMap.put(espectaculo2.getNombre(), espectaculo2);

		Paquete paquete1 = new Paquete();
		paquete1.setNombre("Paquete1");
		paquete1.setEspectaculos(Collections.singletonMap(espectaculo1.getNombre(), espectaculo1));

		Paquete paquete2 = new Paquete();
		paquete2.setNombre("Paquete2");

		facebook.setEspectaculos(espectaculosMap);

		paqueteHandler.add(paquete1);
		paqueteHandler.add(paquete2);
		plataformaHandler.add(facebook);
		plataformaHandler.add(youtube);
		espectaculoHandler.add(espectaculo1);
		espectaculoHandler.add(espectaculo2);

		// Do
		List<EspectaculoDTO> espectaculosNoEnPaquete1 = paqueteController.listarEspectaculosNoEnPaquete("Paquete1",
				"Facebook");
		List<EspectaculoDTO> espectaculosNoEnPaquete2 = paqueteController.listarEspectaculosNoEnPaquete("Paquete2",
				"Facebook");
		List<EspectaculoDTO> espectaculosEnYoutube = paqueteController.listarEspectaculosNoEnPaquete("Paquete1",
				"YouTube");

		// Then
		assertEquals(1, espectaculosNoEnPaquete1.size());
		assertTrue(espectaculosNoEnPaquete1.stream()
				.anyMatch(espectaculoDTO -> "Espectaculo 2".equals(espectaculoDTO.getNombre())));
		assertEquals(2, espectaculosNoEnPaquete2.size());
		assertEquals(0, espectaculosEnYoutube.size());

		// NotFoundException_paquete
		assertThrows(NotFoundException.class,
				() -> paqueteController.listarEspectaculosNoEnPaquete("Paquete inexistente", "Facebook"));

		// NotFoundException_plataforma
		assertThrows(NotFoundException.class,
				() -> paqueteController.listarEspectaculosNoEnPaquete("Paquete1", "Twitter"));
	}

	@Test
	public void agregarEspectaculoAPaquete_ok() throws NotFoundException, BadRequestException {
		// when
		Categoria cat = new Categoria("categoria1");
		List<Categoria> cats = new ArrayList<Categoria>();
		cats.add(cat);

		Artista art = new Artista("A", "a", "a", "a", "pwd", Date.from(Instant.now()), "a", "a", "a", null, null);
		Plataforma pla = new Plataforma("P", "p", "url");

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "descripcion", 120, 100, 200, "url", 200,
				Date.from(Instant.now()), pla, art, cats);

		this.espectaculoHandler.add(espectaculo1);

		EstadoEspectaculo nuevoEstado = EstadoEspectaculo.ACEPTADO;
		espectaculoController.cambiarEstadoEspectaculo(espectaculo1, nuevoEstado);

		Paquete paquete1 = new Paquete();
		Date fecha =  new Date();
		fecha.setYear(2030);
		paquete1.setFechaFin(fecha);
		paquete1.setNombre("Paquete1");

		paqueteHandler.add(paquete1);

		// Do
		paqueteController.agregarEspectaculoAPaquete("Espectaculo1", "Paquete1");

		// Then

		assertEquals(1, paquete1.getEspectaculos().size());
		assertNotNull(paquete1.getEspectaculos().get("Espectaculo1"));
	}

	@Test
	public void agregarEspectaculoAPaquete_throwsNotFound() throws NotFoundException {
		// when
		Espectaculo espectaculo1 = new Espectaculo();
		espectaculo1.setNombre("Espectaculo1");

		Paquete paquete1 = new Paquete();
		paquete1.setNombre("Paquete1");

		paqueteHandler.add(paquete1);
		espectaculoHandler.add(espectaculo1);

		// NotFound_espectaculo
		assertThrows(NotFoundException.class,
				() -> paqueteController.agregarEspectaculoAPaquete("Espectaculo no existente", "Paquete1"));

		// NotFound_Pauqete
		assertThrows(NotFoundException.class,
				() -> paqueteController.agregarEspectaculoAPaquete("Espectaculo1", "Paquete no existente"));

		// Then
		assertEquals(0, paquete1.getEspectaculos().size());
	}

	@Test
	public void altaPaquete_ok() throws BadRequestException {
		// when
		Paquete paquete1 = new Paquete();
		paquete1.setNombre("Paquete1");

		paqueteHandler.add(paquete1);

		// Do
		paqueteController.altaPaquete("Paquete2", "Descripcion", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(10000)), 0, null);

		// Then
		List<Paquete> paquetes = paqueteHandler.getPaquetes();
		assertEquals(2, paquetes.size());
		assertTrue(paquetes.stream().anyMatch(paquete -> "Paquete2".equals(paquete.getNombre())));
	}

	@Test
	public void altaPaquete_throwsBadRequest() {
		// when
		Paquete paquete1 = new Paquete();
		paquete1.setNombre("Paquete1");

		paqueteHandler.add(paquete1);

		// BadRequest_DuplicatedName
		assertThrows(BadRequestException.class, () -> paqueteController.altaPaquete("Paquete1", "Descripcion",
				Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(10000)), 0, null));

		// BadRequest_Date
		assertThrows(BadRequestException.class, () -> paqueteController.altaPaquete("Paquete2", "Descripcion",
				Date.from(Instant.now()), Date.from(Instant.now().minusSeconds(10000)), 0, null));

		// Then
		List<Paquete> paquetes = paqueteHandler.getPaquetes();
		assertEquals(1, paquetes.size());
	}

	@Test
	public void existePlataforma_ok() {
		// when
		Plataforma plataforma1 = new Plataforma();
		plataforma1.setNombre("Plataforma1");
		plataforma1.setDescripcion("Descripcion");
		plataforma1.setUrl("URL");
		plataformaHandler.add(plataforma1);

		// Then
		assertTrue(paqueteController.existePlataforma("Plataforma1"));
		assertFalse(paqueteController.existePlataforma("Plataforma ineistente"));

		// Clear
		plataformaHandler.clear();
	}

	@Test
	public void altaPlataforma_ok() throws BadRequestException {
		// when
		Plataforma plataforma1 = new Plataforma();
		plataforma1.setNombre("Plataforma1");
		plataforma1.setDescripcion("Descripcion");
		plataforma1.setUrl("URL");
		plataformaHandler.add(plataforma1);

		// Do
		paqueteController.altaPlataforma("Plataforma2", "Descripcion", "URL");

		// Then
		List<Plataforma> plataformas = plataformaHandler.getPlataformas();
		assertEquals(2, plataformas.size());
		assertTrue(plataformas.stream().anyMatch(plataforma -> "Plataforma2".equals(plataforma.getNombre())));

		// Clear
		plataformaHandler.clear();
	}

	@Test
	public void altaPlataforma_throwsBadRequest() {
		// when
		Plataforma plataforma1 = new Plataforma();
		plataforma1.setNombre("Plataforma1");
		plataformaHandler.add(plataforma1);

		// BadRequest_DuplicatedName
		assertThrows(BadRequestException.class,
				() -> paqueteController.altaPlataforma("Plataforma1", "Descripcion", "URL"));

		// Then
		List<Plataforma> plataformas = plataformaHandler.getPlataformas();
		assertEquals(1, plataformas.size());

		// Clear
		plataformaHandler.clear();
	}

	@Test // (expected = NotFoundException.class)
	public void altaPlataformaNoOk() {
		// Do
		assertThrows(NotFoundException.class, () -> paqueteController.consultaPlataforma("Nombre inexistente"));
	}

	@Test
	public void altaPlataformaOk() throws BadRequestException, NotFoundException {
		// Do
		paqueteController.altaPlataforma("Andres", "Estudiante", "Andres.com");
		PlataformaDTO plataforma_dto = paqueteController.consultaPlataforma("Andres");
		assertEquals(plataforma_dto.getNombre(), "Andres");
		assertEquals(plataforma_dto.getDescripcion(), "Estudiante");
		assertEquals(plataforma_dto.getUrl(), "Andres.com");

		paqueteController.altaPlataforma("Andres1", "Estudiante1", "Andres.com1");
		plataforma_dto = paqueteController.consultaPlataforma("Andres1");
		assertEquals(plataforma_dto.getNombre(), "Andres1");
		assertEquals(plataforma_dto.getDescripcion(), "Estudiante1");
		assertEquals(plataforma_dto.getUrl(), "Andres.com1");

		paqueteController.altaPlataforma("Andres2", "Estudiante1", "Andres.com1");
		plataforma_dto = paqueteController.consultaPlataforma("Andres2");
		assertEquals(plataforma_dto.getNombre(), "Andres2");
		assertEquals(plataforma_dto.getDescripcion(), "Estudiante1");
		assertEquals(plataforma_dto.getUrl(), "Andres.com1");

		// Then
		List<Plataforma> plataformas = plataformaHandler.getPlataformas();
		assertThrows(BadRequestException.class,
				() -> paqueteController.altaPlataforma("Andres1", "Estudiante1", "Andres.com1"));
		assertEquals(3, plataformas.size());

		// Clear
		plataformaHandler.clear();
	}

	@Test
	public void comprarPaqueteOk() throws NotFoundException, BadRequestException {
		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0);
		paqueteHandler.add(paquete);
		Espectador espectador = new Espectador("nickname", "Pedro", "Perez", "pedro@mail.com", Date.from(Instant.now()),
				"1234", null, null);
		usuarioHandler.add(espectador);

		paqueteController.compraPaquete(paquete.getNombre(), espectador.getEmail(), Date.from(Instant.now()));

		List<CompraPaquete> compras = espectador.getPaquetes();
		assertTrue(compras.size() == 1);
		assertEquals(paquete.getNombre(), compras.get(0).getPaquete().getNombre());
	}

	@Test
	public void comprarPaqueteEspectadorNotFound() throws NotFoundException, BadRequestException {
		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0);
		paqueteHandler.add(paquete);

		assertThrows(NotFoundException.class,
				() -> paqueteController.compraPaquete(paquete.getNombre(), "email", Date.from(Instant.now())));
	}

	@Test
	public void comprarPaqueteNotFound() throws NotFoundException, BadRequestException {
		Espectador espectador = new Espectador("nickname", "Pedro", "Perez", "pedro@mail.com", Date.from(Instant.now()),
				"1234", null, null);
		usuarioHandler.add(espectador);

		assertThrows(NotFoundException.class,
				() -> paqueteController.compraPaquete("paquete", espectador.getEmail(), Date.from(Instant.now())));
	}

	@Test
	public void comprarPaqueteDosVeces() throws NotFoundException, BadRequestException {
		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()),
				Date.from(Instant.now().plusSeconds(2000)), 0);
		paqueteHandler.add(paquete);
		Espectador espectador = new Espectador("nickname", "Pedro", "Perez", "pedro@mail.com", Date.from(Instant.now()),
				"1234", null, null);
		usuarioHandler.add(espectador);

		paqueteController.compraPaquete(paquete.getNombre(), espectador.getEmail(), Date.from(Instant.now()));

		assertThrows(BadRequestException.class, () -> paqueteController.compraPaquete(paquete.getNombre(),
				espectador.getEmail(), Date.from(Instant.now())));
	}

	@Test
	public void listarPaquetesVigentes_ok() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1971, 11, 31);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(1999, 11, 31);

		// When
		paqueteHandler.add(new Paquete("Paquete3", "Tercer paquete", calendar.getTime(), Date.from(Instant.now()), 0)); // Si
		paqueteHandler.add(new Paquete("Paquete4", "Cuarto paquete", calendar.getTime(), calendar2.getTime(), 0)); // Si
		paqueteHandler
				.add(new Paquete("Paquete5", "Cuarto paquete", Date.from(Instant.now()), Date.from(Instant.now()), 0)); // No
		paqueteHandler.add(new Paquete("Paquete6", "Cuarto paquete", calendar2.getTime(), Date.from(Instant.now()), 0)); // Si
		paqueteHandler.add(new Paquete("Paquete7", "Cuarto paquete", calendar.getTime(), calendar.getTime(), 0)); // No
		paqueteHandler.add(new Paquete("Paquete8", "Cuarto paquete", calendar2.getTime(), calendar2.getTime(), 0)); // Si

		Paquete paqueteRepetido = new Paquete("Paquete8", "Cuarto paquete", calendar2.getTime(), calendar2.getTime(),
				0);
		paqueteHandler.add(paqueteRepetido); // No

		// Do
		List<PaqueteDTO> paquetes = paqueteController.listarPaquetesVigentes(calendar2.getTime());

		// Then
		assertEquals(4, paquetes.size());
		assertTrue(paquetes.stream().anyMatch(paqueteDTO -> "Paquete3".equals(paqueteDTO.getNombre())));
		assertTrue(paquetes.stream().anyMatch(paqueteDTO -> "Paquete4".equals(paqueteDTO.getNombre())));
		assertTrue(!paquetes.stream().anyMatch(paqueteDTO -> "Paquete5".equals(paqueteDTO.getNombre())));
		assertTrue(paquetes.stream().anyMatch(paqueteDTO -> "Paquete6".equals(paqueteDTO.getNombre())));
		assertTrue(!paquetes.stream().anyMatch(paqueteDTO -> "Paquete7".equals(paqueteDTO.getNombre())));
		assertTrue(paquetes.stream().anyMatch(paqueteDTO -> "Paquete8".equals(paqueteDTO.getNombre())));
	}

	@Test
	public void listarCategoriasByPaquete_ok() throws NotFoundException, BadRequestException {
		// When
		Plataforma plataformaE = new Plataforma();
		Artista artistaE = new Artista("artista1", "Artista", "1", "artista1@email.com", "pwd",
				Date.from(Instant.now()), "Descripcion", "Biografia", "Sitio Web", null, null);
		;

		Paquete p = new Paquete("Paquete10", "Decimo paquete", Date.from(Instant.now()), Date.from(Instant.now()), 0);
		paqueteHandler.add(p);

		Categoria c2 = new Categoria("categoria2");
		Categoria c3 = new Categoria("categoria3");
		Categoria c4 = new Categoria("categoria4");
		List<Categoria> categorias1 = new ArrayList<Categoria>();
		List<Categoria> categorias2 = new ArrayList<Categoria>();
		categorias2.add(c2);
		categorias1.add(c4);
		categorias1.add(c3);

		Espectaculo e2 = new Espectaculo("Esp2", "Esp", 100, 0, 1000, "EspWeb.com", 120, Date.from(Instant.now()),
				plataformaE, artistaE, categorias1);
		Espectaculo e3 = new Espectaculo("Esp3", "Esp", 100, 0, 1000, "EspWeb.com", 120, Date.from(Instant.now()),
				plataformaE, artistaE, categorias2);

		espectaculoHandler.add(e2);
		espectaculoHandler.add(e3);
		EstadoEspectaculo nuevoEstado = EstadoEspectaculo.ACEPTADO;
		espectaculoController.cambiarEstadoEspectaculo(e2, nuevoEstado);
		espectaculoController.cambiarEstadoEspectaculo(e3, nuevoEstado);

		paqueteController.agregarEspectaculoAPaquete("Esp2", "Paquete10");
		paqueteController.agregarEspectaculoAPaquete("Esp3", "Paquete10");

		// Do
		List<CategoriaDTO> categorias = paqueteController.listarCategoriasByPaquete("Paquete10");

		// Then
		assertThrows(NotFoundException.class, () -> paqueteController.listarCategoriasByPaquete("Paquete inexistente"));
		assertEquals(3, categorias.size());
		assertTrue(categorias.stream().anyMatch(categoriaDTO -> "categoria2".equals(categoriaDTO.getNombre())));
		assertTrue(categorias.stream().anyMatch(categoriaDTO -> "categoria3".equals(categoriaDTO.getNombre())));
		assertTrue(categorias.stream().anyMatch(categoriaDTO -> "categoria4".equals(categoriaDTO.getNombre())));

	}
	
	@Test
	public void listarPaquetesByEspectaculo_ok()  throws NotFoundException, BadRequestException {
		Plataforma plataformaE = new Plataforma();
		Artista artistaE = new Artista("artista1", "Artista", "1", "artista1@email.com", "pwd",
				Date.from(Instant.now()), "Descripcion", "Biografia", "Sitio Web", null, null);
		;

		Paquete p = new Paquete("Paquete10", "Decimo paquete", Date.from(Instant.now()), Date.from(Instant.now()), 0);
		paqueteHandler.add(p);
		Paquete p1 = new Paquete("Paquete11", "Undecime paquete", Date.from(Instant.now()), Date.from(Instant.now()), 0);
		paqueteHandler.add(p1);

		Categoria c2 = new Categoria("categoria2");
		Categoria c3 = new Categoria("categoria3");
		Categoria c4 = new Categoria("categoria4");
		List<Categoria> categorias1 = new ArrayList<Categoria>();
		List<Categoria> categorias2 = new ArrayList<Categoria>();
		categorias2.add(c2);
		categorias1.add(c4);
		categorias1.add(c3);

		Espectaculo e2 = new Espectaculo("Esp2", "Esp", 100, 0, 1000, "EspWeb.com", 120, Date.from(Instant.now()),
				plataformaE, artistaE, categorias1);

		espectaculoHandler.add(e2);

		EstadoEspectaculo nuevoEstado = EstadoEspectaculo.ACEPTADO;
		espectaculoController.cambiarEstadoEspectaculo(e2, nuevoEstado);


		paqueteController.agregarEspectaculoAPaquete("Esp2", "Paquete10");
		paqueteController.agregarEspectaculoAPaquete("Esp2", "Paquete11");
		
		List<PaqueteDTO> paquetes = paqueteController.listarPaquetesByEspecatculo(e2.getNombre());
		
	    assertEquals(2, paquetes.size());
		
		
	}
}
