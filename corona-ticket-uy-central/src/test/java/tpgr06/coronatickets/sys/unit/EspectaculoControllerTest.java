package tpgr06.coronatickets.sys.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
import tpgr06.coronatickets.sys.funcion.Funcion;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.funcion.premio.PremioDTO;
import tpgr06.coronatickets.sys.paquete.Paquete;
import tpgr06.coronatickets.sys.paquete.PaqueteHandler;
import tpgr06.coronatickets.sys.plataforma.Plataforma;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;
import tpgr06.coronatickets.sys.plataforma.PlataformaHandler;
import tpgr06.coronatickets.sys.usuario.UsuarioHandler;
import tpgr06.coronatickets.sys.usuario.artista.Artista;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncion;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncionDTO;

public class EspectaculoControllerTest {

	private Factory factory;
	private IEspectaculoController espectaculoController;
	private EspectaculoHandler espectaculoHandler;
	private PlataformaHandler plataformaHandler;
	private UsuarioHandler usuarioHandler;
	private CategoriaHandler categoriaHandler;
	private PaqueteHandler paqueteHandler;

	@Before
	public void init() {
		this.factory = Factory.getInstance();
		this.espectaculoController = factory.getIEspectaculoController();
		this.espectaculoHandler = EspectaculoHandler.getInstance();
		this.plataformaHandler = PlataformaHandler.getInstance();
		this.usuarioHandler = UsuarioHandler.getInstance();
		this.categoriaHandler = CategoriaHandler.getInstance();
		this.paqueteHandler = PaqueteHandler.getInstance();
		espectaculoHandler.clear();
		plataformaHandler.clear();
		usuarioHandler.clear();
		categoriaHandler.clear();
		paqueteHandler.clear();
	}

	@Test
	public void altaCategoria_Ok() throws BadRequestException, NotFoundException {
		Categoria cat1 = new Categoria("Categoria1");
		categoriaHandler.add(cat1);

		factory.getIEspectaculoController().altaCategoria("Categoria2");

		List<Categoria> categories = categoriaHandler.getAll();
		List<CategoriaDTO> categoriesDTO = factory.getIEspectaculoController().listarCategorias();

		assertEquals(2, categories.size());
		assertEquals(2, categoriesDTO.size());		

	}

	@Test
	public void altaCategoria_notOK() throws BadRequestException, NotFoundException {
		Categoria cat1 = new Categoria("Categoria1");
		categoriaHandler.add(cat1);

		assertThrows(BadRequestException.class, () -> categoriaHandler.add(cat1));		
		assertThrows(BadRequestException.class, () -> factory.getIEspectaculoController().altaCategoria("Categoria1"));

	}

	@Test
	public void getCategorias() throws BadRequestException, NotFoundException {
		Categoria cat1 = new Categoria("Categoria1");
		categoriaHandler.add(cat1);

		assertEquals("Categoria1", categoriaHandler.getByName("Categoria1").getNombre());
		assertEquals("Categoria1", factory.getIEspectaculoController().getCategoria("Categoria1").getNombre());

		assertThrows(NotFoundException.class, () -> factory.getIEspectaculoController().getCategoria("NoExiste"));			

	}

	@Test
	public void cambioDeEstado() throws BadRequestException, NotFoundException {

		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com", "password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		Categoria cat = new Categoria("Categoria1");
		categoriaHandler.add(cat);
		catList.add(cat);


		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		espectaculoController.altaEspectaculo("Espectaculo2", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), "Plataforma1", "artista1@email.com", Arrays.asList("Categoria1"), null, "Desc premios",5,null);

		assertEquals(espectaculoHandler.getByNombre("Espectaculo1").getEstado(), EstadoEspectaculo.INGRESADO);
		assertEquals(espectaculoHandler.getByNombre("Espectaculo2").getEstado(), EstadoEspectaculo.INGRESADO);

		factory.getIEspectaculoController().cambiarEstadoEspectaculo(new EspectaculoDTO(espectaculo1), EstadoEspectaculo.ACEPTADO);
		assertEquals(espectaculoHandler.getByNombre("Espectaculo1").getEstado(), EstadoEspectaculo.ACEPTADO);

		factory.getIEspectaculoController().cambiarEstadoEspectaculo(new EspectaculoDTO(espectaculoHandler.getByNombre("Espectaculo2")), EstadoEspectaculo.RECHAZADO);
		assertEquals(espectaculoHandler.getByNombre("Espectaculo2").getEstado(), EstadoEspectaculo.RECHAZADO);


	}

	@Test
	public void altaEspectaculo_ok() throws BadRequestException, NotFoundException {
		// when
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		Categoria cat = new Categoria("Categoria1");
		categoriaHandler.add(cat);
		catList.add(cat);

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		// Do
		espectaculoController.altaEspectaculo("Espectaculo2", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), "Plataforma1", "artista1@email.com", Arrays.asList("Categoria1"), null, null ,0,null);

		// Then
		List<Espectaculo> espectaculos = espectaculoHandler.getEspectaculos();
		assertEquals(2, espectaculos.size());
		assertTrue(espectaculos.stream().anyMatch(espectaculo -> "Espectaculo2".equals(espectaculo.getNombre())));
	}

	@Test
	public void altaEspectaculo_throwsBadRequest() throws BadRequestException, NotFoundException {
		// when
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		// BadRequest_DuplicatedName
		assertThrows(BadRequestException.class, () -> espectaculoController.altaEspectaculo("Espectaculo1",
				"Descripcion", 1, 0, 10, "URL", 1, Date.from(Instant.now()), "Plataforma1", "artista1@email.com", Arrays.asList("Categoria1"), null,null,0,null));

		// BadRequest_Cantidades
		assertThrows(BadRequestException.class, () -> espectaculoController.altaEspectaculo("Espectaculo2",
				"Descripcion", 1, 10, 9, "URL", 1, Date.from(Instant.now()), "Plataforma1", "artista1@email.com", Arrays.asList("Categoria1"), null,null,0,null));

		// Then
		List<Espectaculo> espectaculos = espectaculoHandler.getEspectaculos();
		assertEquals(1, espectaculos.size());
	}

	@Test
	public void altaEspectaculo_throwsNotFound() throws BadRequestException, NotFoundException {
		// when
		Plataforma facebook = new Plataforma();
		facebook.setNombre("Facebook");
		plataformaHandler.add(facebook);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		// NotFound_Plataforma
		assertThrows(NotFoundException.class,
				() -> espectaculoController.altaEspectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
						Date.from(Instant.now()), "Plataforma no existente", "artista1@email.com", Arrays.asList("Categoria1"), null,null,0,null));

		// NotFound_Artista
		assertThrows(NotFoundException.class,
				() -> espectaculoController.altaEspectaculo("Espectaculo 2", "Descripcion", 1, 0, 10, "URL", 1,
						Date.from(Instant.now()), "Facebook", "artistanoexistente@email.com",  Arrays.asList("Categoria1"), null,null,0,null));

		// Then
		List<Espectaculo> espectaculos = espectaculoHandler.getEspectaculos();
		assertEquals(0, espectaculos.size());
	}

	@Test
	public void listarEspectaculos_ok() throws NotFoundException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);
		PlataformaDTO plataformaDto = new PlataformaDTO("Plataforma1", "Descripcion", "url");

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));
		List<Categoria> catList2 = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria2"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		Espectaculo espectaculo2 = new Espectaculo("Espectaculo 2", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList2);
		espectaculoHandler.add(espectaculo2);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		espectaculosEnPlataforma.put(espectaculo2.getNombre(), espectaculo2);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		// Do
		List<EspectaculoDTO> espectaculos = espectaculoController.listarEspectaculos(plataformaDto.getNombre());

		// Then
		assertEquals(2, espectaculos.size());
		assertTrue(
				espectaculos.stream().anyMatch(espectaculoDTO -> "Espectaculo 1".equals(espectaculoDTO.getNombre())));
	}

	@Test
	public void listarEspectaculos_throwsNotFound() throws NotFoundException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");

		Espectaculo espectaculo1 = new Espectaculo();
		espectaculo1.setNombre("Espectaculo 1");
		espectaculoHandler.add(espectaculo1);

		Espectaculo espectaculo2 = new Espectaculo();
		espectaculo2.setNombre("Espectaculo 2");
		espectaculoHandler.add(espectaculo2);

		// NotFound_Plataforma
		assertThrows(NotFoundException.class, () -> espectaculoController.listarEspectaculos(plataforma1.getNombre()));
	}

	@Test
	public void listarEspectaculos_vacio() throws NotFoundException {
		// when
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);
		PlataformaDTO plataformaDto = new PlataformaDTO("Plataforma1", "Descripcion", "url");

		// Do
		List<EspectaculoDTO> espectaculos = espectaculoController.listarEspectaculos(plataformaDto.getNombre());

		// Then
		assertEquals(0, espectaculos.size());
	}

	@Test
	public void consultaEspectaculo_ok() throws NotFoundException {
		// when
		Plataforma plataforma = new Plataforma("Plataforma1", "Descripcion", "url");

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		// Do
		EspectaculoDTO espectaculo = espectaculoController.consultaEspectaculo("Espectaculo1");

		// Then
		assertEquals("Espectaculo1", espectaculo.getNombre());
		assertEquals("Plataforma1", espectaculo.getPlataforma().getNombre());
	}

	@Test
	public void buscarEspectaculoNombre_ok() throws BadRequestException, NotFoundException {
		// when
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		Categoria cat = new Categoria("Categoria1");
		categoriaHandler.add(cat);
		catList.add(cat);

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion1", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setEstado(EstadoEspectaculo.ACEPTADO);
		Espectaculo espectaculo2 = new Espectaculo("Espectaculo2", "Descripcion2", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo2.setEstado(EstadoEspectaculo.ACEPTADO);
		
		espectaculoHandler.add(espectaculo1);

		espectaculoHandler.add(espectaculo2);

		// Do
		List<EspectaculoDTO> res = espectaculoController.buscarEspectaculos("spectaculo1");

		// Then
		assertEquals(1, res.size());
		assertEquals(espectaculo1.getNombre(), res.get(0).getNombre());
	}

	@Test
	public void buscarEspectaculoNombreNotFound_ok() throws BadRequestException, NotFoundException {
		// when
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		Categoria cat = new Categoria("Categoria1");
		categoriaHandler.add(cat);
		catList.add(cat);

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		// Do
		List<EspectaculoDTO> res = espectaculoController.buscarEspectaculos("Espectaculo2");

		// Then
		assertTrue(res.isEmpty());
	}

	@Test
	public void buscarEspectaculoDescripcion_ok() throws BadRequestException, NotFoundException {
		// when
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		Categoria cat = new Categoria("Categoria1");
		categoriaHandler.add(cat);
		catList.add(cat);

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion1", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setEstado(EstadoEspectaculo.ACEPTADO);
		Espectaculo espectaculo2 = new Espectaculo("Espectaculo2", "Descripcion2", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo2.setEstado(EstadoEspectaculo.ACEPTADO);

		espectaculoHandler.add(espectaculo1);

		espectaculoHandler.add(espectaculo2);

		// Do
		List<EspectaculoDTO> res = espectaculoController.buscarEspectaculos("cion1");

		// Then
		assertEquals(1, res.size());
		assertEquals(espectaculo1.getNombre(), res.get(0).getNombre());
	}

	@Test
	public void buscarEspectaculoDescripcionNotFound_ok() throws BadRequestException, NotFoundException {
		// when
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		Categoria cat = new Categoria("Categoria1");
		categoriaHandler.add(cat);
		catList.add(cat);

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		// Do
		List<EspectaculoDTO> res = espectaculoController.buscarEspectaculos("Descripcion2");

		// Then
		assertTrue(res.isEmpty());
	}

	@Test
	public void consultaEspectaculo_throwsNotFound() throws NotFoundException {
		// when
		Espectaculo espectaculo1 = new Espectaculo();
		espectaculo1.setNombre("Espectaculo1");
		espectaculoHandler.add(espectaculo1);

		// NotFound_Espectaculo
		assertThrows(NotFoundException.class, () -> espectaculoController.consultaEspectaculo("Espectaculo2"));
	}

	@Test
	public void altaFuncion_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setEstado(EstadoEspectaculo.ACEPTADO);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());

		// Do
		espectaculoController.altaFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion, date1, null,
				date2, null);

		// Then
		Espectaculo persistedEspectaculo = espectaculoHandler.getByNombre(espectaculo1.getNombre());
		FuncionDTO funcion = new FuncionDTO(persistedEspectaculo.getFunciones().get(nombreFuncion));
		assertEquals(nombreFuncion, funcion.getNombre());
		assertEquals(date1, funcion.getFecha());
		assertEquals(date2, funcion.getFechaReg());
		assertEquals(Integer.valueOf(0), funcion.getCantEspectadores());
		assertTrue(funcion.getInvitados().isEmpty());

	}

	@Test
	public void altaFuncion1invitado_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista autor = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(autor);

		Artista invitado = new Artista("artista2", "Artista2", "2", "artista2@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(invitado);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, autor, catList);
		espectaculo1.setEstado(EstadoEspectaculo.ACEPTADO);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now().plusSeconds(3600));
		Date date2 = Date.from(Instant.now());
		String[] invitados = { invitado.getNickname() };

		// Do
		espectaculoController.altaFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion, date1,
				invitados, date2, null);

		// Then
		Espectaculo persistedEspectaculo = espectaculoHandler.getByNombre(espectaculo1.getNombre());
		FuncionDTO funcion = new FuncionDTO(persistedEspectaculo.getFunciones().get(nombreFuncion));
		assertEquals(nombreFuncion, funcion.getNombre());
		assertEquals(date1, funcion.getFecha());
		assertEquals(date2, funcion.getFechaReg());
		assertEquals(Integer.valueOf(0), funcion.getCantEspectadores());
		assertEquals(invitado.getNombre(), funcion.getInvitados().get(0).getNombre());
	}

	@Test
	public void altaFuncion1invitadoInexistente() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista autor = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(autor);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, autor, catList);
		espectaculo1.setEstado(EstadoEspectaculo.ACEPTADO);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now().plusSeconds(3600));
		Date date2 = Date.from(Instant.now());
		String[] invitados = { "invalido" };

		assertThrows(NotFoundException.class, () -> espectaculoController.altaFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion, date1, invitados, date2, null));

	}

	// El autor del espectaculo no puede ser invitado
	@Test
	public void altaFuncion1invitadoAutor() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista autor = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(autor);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, autor, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		String[] invitados = { autor.getNickname() };

		assertThrows(BadRequestException.class, () -> espectaculoController.altaFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion, date1, invitados, date2, null));
	}

	@Test
	public void altaFuncion1EspectaculoInexistente() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista autor = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(autor);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, autor, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());

		assertThrows(NotFoundException.class, () -> espectaculoController.altaFuncion(plataforma1.getNombre(),
				"invalido", nombreFuncion, date1, null, date2, null));

	}

	@Test
	public void altaFuncion1PlataformaInexistente() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista autor = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(autor);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, autor, catList);
		espectaculo1.setEstado(EstadoEspectaculo.ACEPTADO);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now().plusSeconds(3600));
		Date date2 = Date.from(Instant.now());

		assertThrows(NotFoundException.class, () -> espectaculoController.altaFuncion("invalid",
				espectaculo1.getNombre(), nombreFuncion, date1, null, date2, null));

	}

	@Test
	public void registroFuncionSinCanje_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		// Do
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), date1, null, null, null);

		// Then
		assertEquals(Integer.valueOf(1), nuevaFuncion.getCantEspectadores());
		assertEquals(nuevaFuncion.getNombre(), espectador.getFunciones().get(0).getNombre());
		RegistroFuncion registro = espectador.getRegistros().get(0);
		assertEquals(false, registro.getCanjeado());
		assertEquals(espectaculo1.getCosto(), registro.getCosto());
		assertEquals(date1, registro.getFechaReg());
		assertEquals(null, registro.getRegistrosCanje());

	}

	@Test
	public void registroFuncionConCanje_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion1 = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion1, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		String nombreFuncion2 = "funcion2";
		Funcion nuevaFuncion2 = new Funcion(nombreFuncion2, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion2);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		String id1 = "id1";
		String id2 = "id2";
		String id3 = "id3";
		RegistroFuncionDTO r1 = espectador.agregarRegistro(id1, Date.from(Instant.now()), espectaculo1.getCosto(),
				nuevaFuncion, null, false, null);
		RegistroFuncionDTO r2 = espectador.agregarRegistro(id2, Date.from(Instant.now()), espectaculo1.getCosto(),
				nuevaFuncion, null, false, null);
		RegistroFuncionDTO r3 = espectador.agregarRegistro(id3, Date.from(Instant.now()), espectaculo1.getCosto(),
				nuevaFuncion, null, false, null);

		List<String> registros = espectador.getRegistrosSinCanjear();
		// Do
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion2,
				espectador.getEmail(), date1, registros.get(0), registros.get(1), registros.get(2));

		// Then
		assertEquals(Integer.valueOf(1), nuevaFuncion2.getCantEspectadores());
		RegistroFuncion registro = espectador.getRegistros().get(3);
		assertEquals(true, registro.getCanjeado());
		assertEquals(Integer.valueOf(0), registro.getCosto());
		assertEquals(date1, registro.getFechaReg());
		assertEquals(r1.getIdentificador(), registro.getRegistrosCanje().get(0));
		assertEquals(r2.getIdentificador(), registro.getRegistrosCanje().get(1));
		assertEquals(r3.getIdentificador(), registro.getRegistrosCanje().get(2));
		assertTrue(espectador.getRegistros().get(0).getCanjeado());
		assertTrue(espectador.getRegistros().get(1).getCanjeado());
		assertTrue(espectador.getRegistros().get(2).getCanjeado());
		assertTrue(espectador.getRegistrosSinCanjear().size() == 0);
	}

	@Test
	public void registroFuncionConCanjeRepetido() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion1 = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion1, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		String nombreFuncion2 = "funcion2";
		Funcion nuevaFuncion2 = new Funcion(nombreFuncion2, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion2);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		String id1 = "id1";
		String id2 = "id2";
		String id3 = "id3";
		espectador.agregarRegistro(id1, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);
		espectador.agregarRegistro(id2, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);
		espectador.agregarRegistro(id3, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);

		// Do
		assertThrows(BadRequestException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion2, espectador.getEmail(), date1, id1, id2, id2));

	}

	@Test
	public void registroFuncionConCanjeIncompleto() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion1 = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion1, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		String nombreFuncion2 = "funcion2";
		Funcion nuevaFuncion2 = new Funcion(nombreFuncion2, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion2);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		String id1 = "id1";
		String id2 = "id2";
		String id3 = "id3";
		espectador.agregarRegistro(id1, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);
		espectador.agregarRegistro(id2, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);
		espectador.agregarRegistro(id3, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);

		// Do
		assertThrows(BadRequestException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion2, espectador.getEmail(), date1, id1, id2, null));

	}

	@Test
	public void registroFuncionConCanjeInvalido() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion1 = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion1, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		String nombreFuncion2 = "funcion2";
		Funcion nuevaFuncion2 = new Funcion(nombreFuncion2, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion2);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		String id1 = "id1";
		String id2 = "id2";
		String id3 = "id3";
		espectador.agregarRegistro(id1, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);
		espectador.agregarRegistro(id2, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);
		espectador.agregarRegistro(id3, Date.from(Instant.now()), espectaculo1.getCosto(), nuevaFuncion, null, false, null);

		// Do
		assertThrows(BadRequestException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion2, espectador.getEmail(), date1, id1, "invalido", null));

	}

	@Test
	public void registroFuncionPlataformaInexistente() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion1 = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion1, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		String nombreFuncion2 = "funcion2";
		Funcion nuevaFuncion2 = new Funcion(nombreFuncion2, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion2);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		// Do
		assertThrows(NotFoundException.class, () -> espectaculoController.registroFuncion("invalid",
				espectaculo1.getNombre(), nombreFuncion2, espectador.getEmail(), date1, null, null, null));

	}

	@Test
	public void registroFuncionEspectaculoInexistente() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion1 = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion1, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		String nombreFuncion2 = "funcion2";
		Funcion nuevaFuncion2 = new Funcion(nombreFuncion2, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion2);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		// Do
		assertThrows(NotFoundException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				"invalid", nombreFuncion2, espectador.getEmail(), date1, null, null, null));

	}

	@Test
	public void registroFuncionInexistente() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		Date date1 = Date.from(Instant.now());

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		// Do
		assertThrows(NotFoundException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), "invalid", espectador.getEmail(), date1, null, null, null));

	}

	@Test
	public void registroFuncionEspectadorInexistente() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion1 = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion1, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		// Do
		assertThrows(NotFoundException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion1, "invalid", date1, null, null, null));

	}

	@Test
	public void registroFuncionAgotada() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		Espectador espectador2 = new Espectador("espectador2", "Juan", "Perez", "juan@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador2);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 1, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), date1, null, null, null);

		// Do
		assertThrows(BadRequestException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion, espectador2.getEmail(), date1, null, null, null));

	}

	@Test
	public void registroFuncionDoble() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), date1, null, null, null);

		// Do
		assertThrows(BadRequestException.class, () -> espectaculoController.registroFuncion(plataforma1.getNombre(),
				espectaculo1.getNombre(), nombreFuncion, espectador.getEmail(), date1, null, null, null));

	}

	@Test
	public void registroFuncionPaquete_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 20,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()), Date.from(Instant.now()
				.plusSeconds(2000)), 50);
		paqueteHandler.add(paquete);

		paquete.addEspectaculo(espectaculo1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		espectador.comprarPaquete(paquete, Date.from(Instant.now()));

		// Do
		espectaculoController.registroFuncionPaquete(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), Date.from(Instant.now()), paquete.getNombre());

		// Then
		assertEquals(Integer.valueOf(1), nuevaFuncion.getCantEspectadores());
		assertEquals(nuevaFuncion.getNombre(), espectador.getFunciones().get(0).getNombre());
		RegistroFuncion registro = espectador.getRegistros().get(0);
		assertEquals(false, registro.getCanjeado());
		Integer costo = espectaculo1.getCosto()/2;
		assertEquals(costo, registro.getCosto());
		assertEquals(null, registro.getRegistrosCanje());
		assertEquals(paquete.getNombre(), registro.getCompraPaquete().getPaquete().getNombre());
	}

	@Test
	public void registroFuncionPaqueteNoEstaVigente() throws NotFoundException, BadRequestException, InterruptedException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 20,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		Date fechaAnterior = Date.from(Instant.now());
		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now().plusSeconds(1000)), Date.from(Instant.now()
				.plusSeconds(2000)), 50);
		paqueteHandler.add(paquete);

		paquete.addEspectaculo(espectaculo1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		espectador.comprarPaquete(paquete, Date.from(Instant.now()));

		// Do
		assertThrows(BadRequestException.class, ()->espectaculoController.registroFuncionPaquete(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), fechaAnterior, paquete.getNombre()));
	}
	
	@Test
	public void registroFuncionPaqueteDosVeces() throws NotFoundException, BadRequestException, InterruptedException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 20,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()), Date.from(Instant.now()
				.plusSeconds(2000)), 50);
		paqueteHandler.add(paquete);

		paquete.addEspectaculo(espectaculo1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		espectador.comprarPaquete(paquete, Date.from(Instant.now()));

		// Do
		espectaculoController.registroFuncionPaquete(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), Date.from(Instant.now()), paquete.getNombre());

		// Do
		assertThrows(BadRequestException.class, ()->espectaculoController.registroFuncionPaquete(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), Date.from(Instant.now()), paquete.getNombre()));
	}

	@Test
	public void registroFuncionEspectadorNoComproPaquete() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 20,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()), Date.from(Instant.now()
				.plusSeconds(2000)), 50);
		paqueteHandler.add(paquete);

		paquete.addEspectaculo(espectaculo1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		// Do
		assertThrows(BadRequestException.class, ()->espectaculoController.registroFuncionPaquete(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), Date.from(Instant.now()), paquete.getNombre()));
	}

	@Test
	public void registroFuncionPaqueteNoTieneEspectaculo() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 20,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		Espectaculo espectaculo2 = new Espectaculo("Espectaculo 2", "Descripcion", 1, 0, 10, "URL", 20,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo2);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		espectaculosEnPlataforma.put(espectaculo2.getNombre(), espectaculo2);

		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo2.agregarFuncion(nuevaFuncion);

		Paquete paquete = new Paquete("Paquete1", "Primer paquete", Date.from(Instant.now()), Date.from(Instant.now()
				.plusSeconds(2000)), 50);
		paqueteHandler.add(paquete);

		paquete.addEspectaculo(espectaculo1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		espectador.comprarPaquete(paquete, Date.from(Instant.now()));

		// Do
		assertThrows(BadRequestException.class, ()->espectaculoController.registroFuncionPaquete(plataforma1.getNombre(), espectaculo2.getNombre(), nombreFuncion,
				espectador.getEmail(), Date.from(Instant.now()), paquete.getNombre()));
	}



	@Test
	public void listarEspectaculosByArtista_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		artista1.agregarEspectaculo(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		// Do
		List<EspectaculoDTO> res = espectaculoController.listarEspectaculosByArtista(artista1.getNickname());

		assertEquals(espectaculo1.getNombre(), res.get(0).getNombre());
		assertThrows(NotFoundException.class, () -> espectaculoController.listarEspectaculosByArtista("no existente"));

	}

	@Test
	public void listarFuncionesByEspectador_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador.getEmail(), date1, null, null, null);

		// Do
		List<FuncionDTO> res = espectaculoController.listarFuncionesByEspectador(espectador.getNickname());

		assertEquals(nuevaFuncion.getNombre(), res.get(0).getNombre());
		assertThrows(NotFoundException.class, () -> espectaculoController.listarFuncionesByEspectador("no existente"));
	}

	@Test
	public void listarFuncionesByEspectaculo_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		// Do
		List<FuncionDTO> res = espectaculoController.listarFuncionesByEspectaculo(espectaculo1.getNombre());

		assertEquals(nuevaFuncion.getNombre(), res.get(0).getNombre());
	}

	@Test
	public void getFuncionEspectaculo_ok() throws NotFoundException, BadRequestException {

		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		// Do
		FuncionDTO res = espectaculoController.getFuncionEspectaculo(nombreFuncion, espectaculo1.getNombre());

		assertEquals(nombreFuncion, res.getNombre());
		assertThrows(NotFoundException.class,
				() -> espectaculoController.getFuncionEspectaculo("Funcion no existente", "Espectaculo 1"));
		assertThrows(NotFoundException.class,
				() -> espectaculoController.getFuncionEspectaculo("funcion1", "Espectaculo no existente"));
	}
	
	
	@Test
	public void valorarEspectaculo_ok() throws NotFoundException {
			// When
			Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
			plataformaHandler.add(plataforma1);
			Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),"Descripcion", "Biografia", "Sitio Web",null,null);
			usuarioHandler.add(artista1);
			List<Categoria> catList = new LinkedList<Categoria>();
			catList.add(new Categoria("Categoria1"));
			Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1, Date.from(Instant.now()), plataforma1, artista1, catList);
			espectaculoHandler.add(espectaculo1);
			Espectador espectador = new Espectador("espectador1", "Jorge", "Perez", "jorge@mail.com", Date.from(Instant.now()),"password",null,null);
			Espectador espectador2 = new Espectador("espectador2", "Jorge2", "Perez2", "jorge2@mail.com", Date.from(Instant.now()),"password",null,null);
			usuarioHandler.add(espectador);
			usuarioHandler.add(espectador2);
			
				
			// Do
			espectaculoController.valorarEspectaculo("Espectaculo 1", "espectador1", 3);
			espectaculoController.valorarEspectaculo("Espectaculo 1", "espectador2", 1);
			
			// Then
			List<Integer> valoraciones = espectaculo1.getValoraciones();
			assertEquals(valoraciones.size(),2);
			assertThrows(NotFoundException.class,
					() -> espectaculoController.valorarEspectaculo("Espectaculo 1", "espectador1", 4));
			assertThrows(NotFoundException.class, () -> espectaculoController.valorarEspectaculo("nombreInexistente","espectador1", 1) );
			assertThrows(NotFoundException.class, () -> espectaculoController.valorarEspectaculo("Espectaculo 1", "nombreInexistente", 1) );
		
		}
	
	@Test
	public void sortearPremiosFuncion_ok() throws NotFoundException, BadRequestException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador1 = new Espectador("espectador1", "Jorge1", "Perez1", "jorge1@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador2 = new Espectador("espectador2", "Jorge2", "Perez2", "jorge2@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador3 = new Espectador("espectador3", "Jorge3", "Perez3", "jorge3@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador1);
		usuarioHandler.add(espectador2);
		usuarioHandler.add(espectador3);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setDescripcionPremios("Descripcion premios");
		espectaculo1.setCantidadPremios(2);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador1.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador2.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador3.getEmail(), date1, null, null, null);
		
		//Do
		PremioDTO res = espectaculoController.sortearPremios(espectaculo1.getNombre(), nombreFuncion);

		// Then
		assertEquals(espectaculo1.getDescripcionPremios(), res.getDescripcion());
		assertEquals(espectaculo1.getNombre(), res.getEspectaculo());
		assertEquals(nombreFuncion, res.getFuncion());
		assertEquals(espectaculo1.getCantidadPremios(), (Integer)res.getGanadores().size());
		
		Integer premiosSorteados = espectador1.getPremios().size() + espectador2.getPremios().size() +espectador3.getPremios().size();

		assertEquals(espectaculo1.getCantidadPremios(), premiosSorteados);
	}

	@Test
	public void sortearMasPremiosQueEspectadoresFuncion_ok() throws NotFoundException, BadRequestException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador1 = new Espectador("espectador1", "Jorge1", "Perez1", "jorge1@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador2 = new Espectador("espectador2", "Jorge2", "Perez2", "jorge2@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador3 = new Espectador("espectador3", "Jorge3", "Perez3", "jorge3@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador1);
		usuarioHandler.add(espectador2);
		usuarioHandler.add(espectador3);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setDescripcionPremios("Descripcion premios");
		espectaculo1.setCantidadPremios(10);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador1.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador2.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador3.getEmail(), date1, null, null, null);
		
		//Do
		PremioDTO res = espectaculoController.sortearPremios(espectaculo1.getNombre(), nombreFuncion);

		// Then
		assertEquals(espectaculo1.getDescripcionPremios(), res.getDescripcion());
		assertEquals(espectaculo1.getNombre(), res.getEspectaculo());
		assertEquals(nombreFuncion, res.getFuncion());
		assertEquals(1, espectador1.getPremios().size());
		assertEquals(1, espectador2.getPremios().size());
		assertEquals(1, espectador3.getPremios().size());

	}
	
	@Test
	public void sortearPremiosFuncionDosVeces() throws NotFoundException, BadRequestException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador1 = new Espectador("espectador1", "Jorge1", "Perez1", "jorge1@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador2 = new Espectador("espectador2", "Jorge2", "Perez2", "jorge2@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador3 = new Espectador("espectador3", "Jorge3", "Perez3", "jorge3@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador1);
		usuarioHandler.add(espectador2);
		usuarioHandler.add(espectador3);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setDescripcionPremios("Descripcion premios");
		espectaculo1.setCantidadPremios(10);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador1.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador2.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador3.getEmail(), date1, null, null, null);
		
		espectaculoController.sortearPremios(espectaculo1.getNombre(), nombreFuncion);

		//Do
		assertThrows(BadRequestException.class, ()-> espectaculoController.sortearPremios(espectaculo1.getNombre(), nombreFuncion));
	}
	
	@Test
	public void sortearPremiosFuncionEspectaculoSinPremios() throws NotFoundException, BadRequestException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador1 = new Espectador("espectador1", "Jorge1", "Perez1", "jorge1@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador2 = new Espectador("espectador2", "Jorge2", "Perez2", "jorge2@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador3 = new Espectador("espectador3", "Jorge3", "Perez3", "jorge3@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador1);
		usuarioHandler.add(espectador2);
		usuarioHandler.add(espectador3);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setDescripcionPremios("Descripcion premios");
		espectaculo1.setCantidadPremios(0);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador1.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador2.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador3.getEmail(), date1, null, null, null);
		
		//Do
		assertThrows(BadRequestException.class, ()-> espectaculoController.sortearPremios(espectaculo1.getNombre(), nombreFuncion));
	}
	
	@Test
	public void sortearPremiosFuncionSinEspectadores() throws NotFoundException, BadRequestException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setDescripcionPremios("Descripcion premios");
		espectaculo1.setCantidadPremios(5);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now());
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);
		
		//Do
		assertThrows(BadRequestException.class, ()-> espectaculoController.sortearPremios(espectaculo1.getNombre(), nombreFuncion));
	}
	
	@Test
	public void sortearPremiosFuncionNoRealizada() throws NotFoundException, BadRequestException {
		// When
		Plataforma plataforma1 = new Plataforma("Plataforma1", "Descripcion", "url");
		plataformaHandler.add(plataforma1);

		Artista artista1 = new Artista("artista1", "Artista", "1", "artista1@email.com","password", Date.from(Instant.now()),
				"Descripcion", "Biografia", "Sitio Web",null,null);
		usuarioHandler.add(artista1);

		Espectador espectador1 = new Espectador("espectador1", "Jorge1", "Perez1", "jorge1@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador2 = new Espectador("espectador2", "Jorge2", "Perez2", "jorge2@mail.com",
				Date.from(Instant.now()),"password",null,null);
		Espectador espectador3 = new Espectador("espectador3", "Jorge3", "Perez3", "jorge3@mail.com",
				Date.from(Instant.now()),"password",null,null);
		usuarioHandler.add(espectador1);
		usuarioHandler.add(espectador2);
		usuarioHandler.add(espectador3);

		List<Categoria> catList = new LinkedList<Categoria>();
		catList.add(new Categoria("Categoria1"));

		Espectaculo espectaculo1 = new Espectaculo("Espectaculo 1", "Descripcion", 1, 0, 10, "URL", 1,
				Date.from(Instant.now()), plataforma1, artista1, catList);
		espectaculo1.setDescripcionPremios("Descripcion premios");
		espectaculo1.setCantidadPremios(10);
		espectaculoHandler.add(espectaculo1);

		HashMap<String, Espectaculo> espectaculosEnPlataforma = new HashMap<String, Espectaculo>();
		espectaculosEnPlataforma.put(espectaculo1.getNombre(), espectaculo1);
		plataforma1.setEspectaculos(espectaculosEnPlataforma);

		String nombreFuncion = "funcion1";
		Date date1 = Date.from(Instant.now().plusSeconds(10000));
		Date date2 = Date.from(Instant.now());
		List<Artista> invitados = new ArrayList<Artista>();
		Funcion nuevaFuncion = new Funcion(nombreFuncion, date1, date2, invitados, 0);
		espectaculo1.agregarFuncion(nuevaFuncion);

		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador1.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador2.getEmail(), date1, null, null, null);
		espectaculoController.registroFuncion(plataforma1.getNombre(), espectaculo1.getNombre(), nombreFuncion,
				espectador3.getEmail(), date1, null, null, null);
		
		//Do
		assertThrows(BadRequestException.class, ()-> espectaculoController.sortearPremios(espectaculo1.getNombre(), nombreFuncion));
	}	


}
