package tpgr06.coronatickets.sys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tpgr06.coronatickets.sys.categoria.Categoria;
import tpgr06.coronatickets.sys.categoria.CategoriaHandler;
import tpgr06.coronatickets.sys.espectaculo.Espectaculo;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoHandler;
import tpgr06.coronatickets.sys.espectaculo.EstadoEspectaculo;
import tpgr06.coronatickets.sys.espectaculo.IEspectaculoController;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.Funcion;
import tpgr06.coronatickets.sys.funcion.premio.Premio;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.paquete.PaqueteHandler;
import tpgr06.coronatickets.sys.usuario.IUsuarioController;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.UsuarioHandler;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncionDTO;

public class Utils {

	private static Boolean datosCargados = false;

	@SuppressWarnings({ "deprecation", "unused" })
	public static void cargarDatosDePrueba() throws BadRequestException, NotFoundException {

		if (!datosCargados) {
			datosCargados = true;
			Calendar calendar = Calendar.getInstance();
			Factory factory = Factory.getInstance();
			IUsuarioController usrController = factory.getIUsuarioController();
			IEspectaculoController espcController = factory.getIEspectaculoController();
			IPaqueteController pqtController = factory.getIPaqueteController();
			EspectaculoHandler espectaculoHandler = EspectaculoHandler.getInstance();
			PaqueteHandler paqueteHandler = PaqueteHandler.getInstance();
			CategoriaHandler categoriaHandler = CategoriaHandler.getInstance();
			UsuarioHandler usuarioHandler = UsuarioHandler.getInstance();
			
			calendar.set(1971, 11, 31);
			UsuarioDTO el = new EspectadorDTO("eleven11", "Eleven", "Ten", "eleven11@gmail.com", calendar.getTime(), "lkj34df", new ArrayList<String>(), new ArrayList<String>());
			el.setImagen("/media/img/user-generico.png");
			
			calendar.set(1983, 10, 15);
			UsuarioDTO co = new EspectadorDTO("costas", "Gerardo", "Costas", "gcostas@gmail.com", calendar.getTime(), "poke579", new ArrayList<String>(), new ArrayList<String>());
			co.setImagen("/media/img/user-generico.png");
			
			calendar.set(1990, 3, 15);
			UsuarioDTO ew = new EspectadorDTO("waston", "Emma", "Watson", "e.watson@gmail.com", calendar.getTime(), "mkji648", new ArrayList<String>(), new ArrayList<String>());
			ew.setImagen("/media/img/emma-watson.png");

			calendar.set(1959, 4, 15);
			UsuarioDTO gh = new EspectadorDTO("house", "Gregory", "House", "greghouse@alpanpan.com.uy", calendar.getTime(), "fcku0123", new ArrayList<String>(), new ArrayList<String>());
			gh.setImagen("/media/img/gregory-house.png");

			calendar.set(1950, 0, 28);
			UsuarioDTO sp = new EspectadorDTO("sergiop", "Sergio", "Puglia", "puglia@alpanpan.com.uy", calendar.getTime(), "vbmn4r", new ArrayList<String>(), new ArrayList<String>());
			sp.setImagen("/media/img/sergio-puglia.png");

			calendar.set(1976, 2, 17);
			UsuarioDTO ar = new EspectadorDTO("chino", "Alvaro", "Recoba", "chino@trico.org.uy", calendar.getTime(), "ncnl123", new ArrayList<String>(), new ArrayList<String>());
			ar.setImagen("/media/img/alvaro-recoba.png");

			calendar.set(1955, 1, 14);
			UsuarioDTO ap = new EspectadorDTO("tonyp", "Antonio", "Pacheco", "eltony@manya.org.uy", calendar.getTime(), "mny101", new ArrayList<String>(), new ArrayList<String>());
			ap.setImagen("/media/img/antonio-pacheco.png");

			calendar.set(1927, 1, 23);
			UsuarioDTO ml = new EspectadorDTO("lachiqui", "Mirtha", "Legrand", "lachiqui@hotmail.com.ar", calendar.getTime(), "1o1vbm", new ArrayList<String>(), new ArrayList<String>());
			ml.setImagen("/media/img/user-generico.png");
			
			calendar.set(1937, 4, 8);
			UsuarioDTO cb = new EspectadorDTO("cbochinche", "Cacho", "Bochinche", "cbochinche@vera.com.uy", calendar.getTime(), "ultraton01", new ArrayList<String>(), new ArrayList<String>());
			cb.setImagen("/media/img/user-generico.png");
			
			usrController.altaUsuario(el);
			usrController.altaUsuario(co);
			usrController.altaUsuario(ew);
			usrController.altaUsuario(gh);
			usrController.altaUsuario(sp);
			usrController.altaUsuario(ar);
			usrController.altaUsuario(ap);
			usrController.altaUsuario(ml);
			usrController.altaUsuario(cb);

			calendar.set(1977, 0, 1);
			UsuarioDTO vp = new ArtistaDTO("vpeople", "Village", "People", "vpeople@tuta.io", calendar.getTime(), "asdfg456", new ArrayList<String>(), new ArrayList<String>(),
					"Village People es una innovadora formacion musical\n" + "de estilo disco de finales de los anios 70. Fue famosa\n" + "tanto por sus peculiares disfraces, como por sus\n" +
					"canciones pegadizas, con letras sugerentes y llenas de\n" + "dobles sentidos.",
					"Grupo americano del disco creado\n" + "por Jacques Morali y Henry Belolo\n" + "en 1977. Segun Marjorie Burgess,\n" + "todo comenzo cuando Morali fue a\n" + "un bar gay de Nueva "
					+ "York una\n" + "noche y noto al bailarin Felipe Rose\n" + "vestido como un nativo americano.",
					"http://www.officialvillagepeople.com");
			vp.setImagen("/media/img/village-people.png");

			calendar.set(1980, 5, 14);
			UsuarioDTO dm = new ArtistaDTO("dmode", "Depeche", "Mode", "dmode@tuta.io", calendar.getTime(), "123rtgfdv", new ArrayList<String>(), new ArrayList<String>(),
					"Depeche Mode es un grupo ingles de musica\n" + "electronica formado en Basildon, Essex, en 1980 por\n" + "Vicent Clarke y Andrew John Fletcher, a los que se\n" + "unieron Martin"
					+ " Lee Gore y poco despues David Gahan.\n" + "Actualmente se le considera como grupo de musica\n" + "alternativa.",
					null, "http://www.depechemode.com");
			dm.setImagen("/media/img/depeche-mode.png");

			calendar.set(1953, 5, 2);
			UsuarioDTO cl = new ArtistaDTO("clauper", "Cyndi", "Lauper", "clauper@hotmail.com", calendar.getTime(), "poiuy086", new ArrayList<String>(), new ArrayList<String>(),
					"Cynthia Ann Stephanie Lauper, conocida simplemente\n" + "como Cyndi Lauper, es una cantautora, actriz y\n" + "empresaria estadounidense. Despues de participar en\n" + "el grupo "
					+ "musical, Blue Angel, en 1983 firmo con\n" + "Portrait Records (filial de Epic Records) y lanzo su\n" + "exitoso Album debut She's So Unusual a finales de ese\n" + "mismo anio."
					+ " Siguio lanzando una serie de Albumes en\n" + "los que encontro una inmensa popularidad, superando\n" + "los limites de contenido de las letras de sus canciones.",
					"Cynthia Ann Stephanie Lauper\n" + "(Brooklyn, Nueva York; 22 de junio\n" + "de 1953). ", "cyndilauper.com");
			cl.setImagen("/media/img/cyndi-lauper.png");

			calendar.set(1949, 8, 23);
			UsuarioDTO bs = new ArtistaDTO("bruceTheBoss", "Bruce", "Springsteen", "bruceTheBoss@gmail.com", calendar.getTime(), "GTO468", new ArrayList<String>(), new ArrayList<String>(),
					"Bruce Frederick Joseph Springsteen (Long Branch,\n" + "Nueva Jersey, 23 de septiembre de 1949), mas conocido\n" + "como Bruce Springsteen, es un cantante, musico y\n" +
					"compositor estadounidense. ",
					null, "brucespringsteen.net");
			bs.setImagen("/media/img/bruce-springsteen.png");

			calendar.set(1998, 0, 1);
			UsuarioDTO tn = new ArtistaDTO("tripleNelson", "La Triple", "Nelson", "tripleNelson@tuta.io", calendar.getTime(), "HGF135", new ArrayList<String>(), new ArrayList<String>(),
					"La Triple Nelson es un grupo de rock uruguayo\n" + "formado en enero de 1998 e integrado inicialmente por\n" + "Christian Cary (guitarra y voz), Fernando \"Paco\" Pintos\n" +
					"(bajo y coros) y Ruben Otonello (actualmente su nuevo\n" + "baterista es Rafael Ugo).",
					null, "http://www.latriplenelson.uy");
			tn.setImagen("/media/img/triple-nelson.png");

			calendar.set(1987, 1, 14);
			UsuarioDTO ll = new ArtistaDTO("la_ley", "La", "Ley", "la_ley@tuta.io", calendar.getTime(), "lkj65D", new ArrayList<String>(), new ArrayList<String>(),
					"La Ley fue una banda chilena de rock formada en 1987\n" + "por iniciativa del tecladista y guitarrista. En un\n" + "principio, La Ley tenia la aspiracion de ser un grupo de\n" + "musica tecno. Este disco resulta ser un exito de ventas\n" + "y reciben una invitacion al Festival Internacional de\n" + "Vinia del Mar de febrero de 1994.",
					null, "http://www.lasleyesdenewton.com");
			ll.setImagen("/media/img/la-ley.png");

			calendar.set(1981, 7, 13);
			UsuarioDTO pi = new ArtistaDTO("lospimpi", "Pimpinela", "Pimpinela", "lospimpi@gmail.com", calendar.getTime(), "jhvf395", new ArrayList<String>(), new ArrayList<String>(),
					"Pimpinela es un duo musical argentino compuesto por\n" + "los hermanos Lucia Galan y Joaquin Galan. Pimpinela\n" + "ha editado veinticuatro discos", null,
					"http://www.pimpinela.net");
			pi.setImagen("/media/img/pimpinela.png");

			calendar.set(1940, 2, 5);
			UsuarioDTO dy = new ArtistaDTO("dyangounchained", "Dyango", "Ango", "dyangounchained@gmail.com", calendar.getTime(), "ijngr024", new ArrayList<String>(), new ArrayList<String>(),
					"Jose Gomez Romero, conocido artisticamente como\n" + "Dyango es un cantante espaniol de musica romantica.", null, null);
			dy.setImagen("/media/img/dyango.png");

			calendar.set(1952, 6, 17);
			UsuarioDTO al = new ArtistaDTO("alcides", "Alcides", "Violeta", "alcides@tuta.io", calendar.getTime(), "987mnbgh", new ArrayList<String>(), new ArrayList<String>(),
					"Su carrera comienza en 1976 cuando forma la banda\n" + "Los Playeros junto a su hermano Victor. Al poco\n" + "tiempo se mudan a San Luis donde comienzan a\n" + "hacerse "
					+ "conocidos en la escena musical. Su exito a nivel\n" + "nacional llega a comienzos de los anios 1990 cuando\n" + "desembarca en Buenos Aires y graba el exito \"Violeta\",\n" +
					"originalmente compuesta e interpretada en 1985 por el\n" + "musico brasilenio Luiz Caldas bajo el titulo <Fricote>.",
					null, null);
			al.setImagen("/media/img/alcides-violeta.png");

			usrController.altaUsuario(vp);
			usrController.altaUsuario(dm);
			usrController.altaUsuario(cl);
			usrController.altaUsuario(bs);
			usrController.altaUsuario(tn);
			usrController.altaUsuario(ll);
			usrController.altaUsuario(pi);
			usrController.altaUsuario(dy);
			usrController.altaUsuario(al);

			pqtController.altaPlataforma("Instagram Live", "Funcionalidad de la red social Instagram, con la que\n" + "los usuarios pueden transmitir videos en vivo.",
					"https://www.instagram.com/liveoficial");
			pqtController.altaPlataforma("Facebook Watch", "Servicio de video bajo demanda operado por\n" + "Facebook. ", "https://www.facebook.com/watch/");
			pqtController.altaPlataforma("Twitter Live", "Aplicacion de Twitter para la transmision de video\n" + "en directo (streaming).", "https://twitter.com/");
			pqtController.altaPlataforma("Youtube", "Sitio web de origen estadounidense dedicado a\n" + "compartir videos. ", "https://www.youtube.com/");


			// Categorias

			Categoria c1 = new Categoria("Bandas Latinas");
			Categoria c2 = new Categoria("Solistas");
			Categoria c3 = new Categoria("Rock en Ingles");
			Categoria c4 = new Categoria("Musica Tropical");

			espcController.altaCategoria(c1.getNombre());
			espcController.altaCategoria(c2.getNombre());
			espcController.altaCategoria(c3.getNombre());
			espcController.altaCategoria(c4.getNombre());


			ArrayList<String> categorias1 = new ArrayList<String>();
			categorias1.add(c3.getNombre());
			calendar.set(2020, 2, 31);
			espcController.altaEspectaculo("Los Village Volvieron", "Espectaculo de retorno de los\n" + "Village People.", 90, 1, 800, "https://\n" + "www.instagram.com\n" + "/realvillagepeople/",
					550, calendar.getTime(), "Instagram Live", vp.getEmail(), categorias1, "/media/img/espectaculo-generico.png","Meet & greet (encuentro) virtual con integrantes de Village People y un\n" + 
							"accesorio de indumentaria de la banda que sera elegido por el ganador,\n" + 
							"como ser el penacho de plumas del jefe indio (sujeto a disponibilidad).\n" + 
							"Info: https://bit.ly/sorteovp",2,"https://www.youtube.com/embed/N8FxU1nmLWg");

			ArrayList<String> categorias2 = new ArrayList<String>();
			categorias2.add(c3.getNombre());
			calendar.set(2020, 3, 20);
			espcController.altaEspectaculo("Global Spirit", "Espectaculo donde se presenta\n" + "el Album Spirit. ", 120, 1, 1300, "https://es-la.facebook.com/depechemode", 750, calendar.getTime(),
					"Facebook Watch", dm.getEmail(), categorias2, "/media/img/espectaculo-generico.png","Box Set multimedia 'Depeche Mode: SPIRITS in the Forest', que\n" + 
							"sigue a la banda en su Global Spirit Tour 2017/2018, que vio a\n" + 
							"Depeche Mode tocar para mas de 3 millones de fanaticos en 115\n" + 
							"shows en todo el mundo. El Box Set contiene 2 CDs y 2 DVDs o 2\n" + 
							"CDs y 1 Blu-ray (a eleccion). Info: https://bit.ly/sorteodm",3,"https://www.youtube.com/embed/2qxcr6T9pNM");

			ArrayList<String> categorias3 = new ArrayList<String>();
			categorias3.add(c2.getNombre());
			calendar.set(2020, 4, 30);
			espcController.altaEspectaculo("Memphis Blues World", "Espectaculo promoviendo\n" + "Album Memphis Blues.", 110, 1, 1000, "https://twitter.com/cyndilauper", 800, calendar.getTime(),
					"Twitter Live", cl.getEmail(), categorias3, "/media/img/espectaculo-generico.png","Meet & greet (encuentro) virtual con la legendaria cantante e Ã­cono del\n" + 
							"Pop, que inspiro a tantas otras cantante femeninas como Madonna y\n" + 
							"Lady Gaga (aunque ellas jamas lo admitiran).",2,"https://www.youtube.com/embed/ivHp3_gYXIc");

			ArrayList<String> categorias4 = new ArrayList<String>();
			categorias4.add(c3.getNombre());
			calendar.set(2020, 5, 7);
			espcController.altaEspectaculo("Springsteen on Broadway",
					"Springsteen tocando guitarra o\n" + "piano y relatando anecdotas\n" + "recogidas en su autobiografia\n" + "de 2016, Born to Run. ", 100, 1, 1500,
					"https://\n" + "www.youtube.com/\n" + "BruceSpringsteen", 980, calendar.getTime(), "Youtube", bs.getEmail(), categorias4, "/media/img/espectaculo-generico.png","Album completo 'Springsteen On Broadway' en formato MP3 o CD (a\n" + 
							"eleccion). Info: https://bit.ly/sorteobs",2,"https://www.youtube.com/embed/M1xDzgob1JI");

			ArrayList<String> categorias5 = new ArrayList<String>();
			categorias5.add(c1.getNombre());
			calendar.set(2020, 6, 8);
			espcController.altaEspectaculo("Bien de Familia", "El duo estara presentando sus\n" + "mas sonados exitos y tambien\n" + "nuevas canciones . ", 150, 1, 500,
					"https://twitter.com/PimpinelaNet", 500, calendar.getTime(), "Twitter Live", pi.getEmail(), categorias5, "/media/img/espectaculo-generico.png","Es cierto que son hermanos? La voz de Lucia puede romper una copa\n" + 
							"de cristal? Joaquin quiere dejar Pimpinela y ser el vocalista de una\n" + 
							"banda de heavy metal? Todas estas preguntas y muchas mas podras\n" + 
							"hacerselas a tus idolos en un encuentro on-line exclusivo para los\n" + 
							"ganadores de este sorteo.",1,"https://www.youtube.com/embed/dPSlBRg0HeA");

			ArrayList<String> categorias6 = new ArrayList<String>();
			categorias6.add(c4.getNombre());
			calendar.set(2020, 6, 31);
			espcController.altaEspectaculo("30 anios", "Espectaculo conmemorando los\n" + "30 anios de Violeta.", 80, 3, 150, "https://twitter.com/alcides_Shows", 450, calendar.getTime(),
					"Twitter Live", al.getEmail(), categorias6, "/media/img/espectaculo-generico.png","Entrada en platea VIP para el primer show presencial que realice\n" + 
							"Alcides a partir de enero de 2021 (una vez que el artista haya recibido\n" + 
							"la vacuna contra el SARS-COV-2), mas 1 litro de Fernet de marca a\n" + 
							"confirmar.",3,"https://www.youtube.com/embed/65Pu6WP0bag");

			ArrayList<String> categorias7 = new ArrayList<String>();
			categorias7.add(c2.getNombre());
			calendar.set(2020, 0, 9);
			espcController.altaEspectaculo("Grandes Exitos 2020", "Espectaculo de gira con los\n" + "temas de siempre", 120, 3, 4, "https://www.youtube.com/c/dyangooficial", 550,
					calendar.getTime(), "Instagram Live", dy.getEmail(), categorias7, "/media/img/espectaculo-generico.png","Album completo 'Y Ahora Que' para descargar en formato FLAC (24\n" + 
							"bits, 44.1 kHz). Info: https://bit.ly/sorteody",2,"https://www.youtube.com/embed/NxFeibjFt3k");

			ArrayList<String> categorias8 = new ArrayList<String>();
			categorias8.add(c1.getNombre());
			calendar.set(2020, 4, 20);
			espcController.altaEspectaculo("Llega a Casa", "Primer Espectaculo con\n" + "transmision por streaming", 100, 100, 1500, "https://www.instagram.com/latriplenelson/", 550,
					calendar.getTime(),
					"Youtube", tn.getEmail(), categorias8, "/media/img/espectaculo-generico.png","Entrada doble para espectaculo 'Mi Bien' a realizarse en el Auditorio\n" + 
							"Nacional del SODRE.",2,"https://www.youtube.com/embed/m7r3YIFRI3k");

			ArrayList<String> categorias9 = new ArrayList<String>();
			categorias9.add(c4.getNombre());
			calendar.set(2020, 10, 25);
			espcController.altaEspectaculo("Nochebuena con Alcides y amigos", "Esta nochebuena, festejamos con Alcides y grandes invitados", 60, 1, 3, "https://twitter.com/alcides_shows", 600,
					calendar.getTime(),
					"Twitter Live", al.getEmail(), categorias9, "/media/img/espectaculo-generico.png","",0,"https://www.youtube.com/embed/65Pu6WP0bag");

			ArrayList<String> categorias10 = new ArrayList<String>();
			categorias10.add(c4.getNombre());
			calendar.set(2020, 10, 25);
			espcController.altaEspectaculo("Fin de anio con Alcides y amigos", "Esta fin de anio, festejamos con Alcides y grandes invitados", 60, 1, 3, "https://twitter.com/alcides_shows", 700,
					calendar.getTime(),
					"Twitter Live", al.getEmail(), categorias9, "/media/img/espectaculo-generico.png","",0,"https://www.youtube.com/embed/65Pu6WP0bag");

			
			Date inicio = new Date();
			Date fin = new Date();

			// Paquete de Espectaculo

			inicio.setDate(1);
			inicio.setMonth(4);
			inicio.setYear(-1900 + 2020);
			fin.setDate(31);
			fin.setMonth(6);
			fin.setYear(-1900 + 2020);
			pqtController.altaPaquete("Paquete de Bandas", "Paquete de bandas musicales.", inicio, fin, 20, "/media/img/paquete-de-bandas.png");

			inicio.setDate(1);
			inicio.setMonth(7);
			inicio.setYear(-1900 + 2020);
			fin.setDate(30);
			fin.setMonth(8);
			fin.setYear(-1900 + 2020);
			pqtController.altaPaquete("Paquete Solistas", "Paquete de solistas.", inicio, fin, 30, "/media/img/paquete-solistas.png");

			inicio.setDate(15);
			inicio.setMonth(7);
			inicio.setYear(-1900 + 2020);
			fin.setDate(25);
			fin.setMonth(10);
			fin.setYear(-1900 + 2020);
			pqtController.altaPaquete("Paquete Latino", "Paquete de espectaculos latinos.", inicio, fin, 15, "/media/img/paquete-latino.png");

			inicio.setDate(1);
			inicio.setMonth(10);
			inicio.setYear(-1900 + 2020);
			fin.setDate(23);
			fin.setMonth(11);
			fin.setYear(-1900 + 2020);
			pqtController.altaPaquete("La Triple Dyango", "Para los rockeros romanticos", inicio, fin, 10, "/media/img/paquete-latino.png");


			Espectaculo e1 = espectaculoHandler.getByNombre("Los Village Volvieron");
			Espectaculo e2 = espectaculoHandler.getByNombre("Global Spirit");
			Espectaculo e3 = espectaculoHandler.getByNombre("Memphis Blues World");
			Espectaculo e4 = espectaculoHandler.getByNombre("Springsteen on Broadway");
			Espectaculo e5 = espectaculoHandler.getByNombre("Bien de Familia");
			Espectaculo e6 = espectaculoHandler.getByNombre("30 anios");
			Espectaculo e7 = espectaculoHandler.getByNombre("Grandes Exitos 2020");
			Espectaculo e8 = espectaculoHandler.getByNombre("Llega a Casa");
			Espectaculo e9 = espectaculoHandler.getByNombre("Nochebuena con Alcides y amigos");
			Espectaculo e10 = espectaculoHandler.getByNombre("Fin de anio con Alcides y amigos");


			// Cambiar de Estado Espectaculo

			espcController.cambiarEstadoEspectaculo(e1, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e2, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e3, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e4, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e5, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e6, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e7, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e8, EstadoEspectaculo.ACEPTADO);
			espcController.cambiarEstadoEspectaculo(e9, EstadoEspectaculo.INGRESADO);
			espcController.cambiarEstadoEspectaculo(e10, EstadoEspectaculo.RECHAZADO);

			// Agregar Espectaculos a Paquete

			pqtController.agregarEspectaculoAPaquete("Los Village Volvieron", "Paquete de Bandas");
			pqtController.agregarEspectaculoAPaquete("Global Spirit", "Paquete de Bandas");

			pqtController.agregarEspectaculoAPaquete("Memphis Blues World", "Paquete Solistas");
			pqtController.agregarEspectaculoAPaquete("Springsteen on Broadway", "Paquete Solistas");

			pqtController.agregarEspectaculoAPaquete("Bien de Familia", "Paquete Latino");
			pqtController.agregarEspectaculoAPaquete("30 anios", "Paquete Latino");
			
			pqtController.agregarEspectaculoAPaquete(e7.getNombre(), "La Triple Dyango");
			pqtController.agregarEspectaculoAPaquete(e8.getNombre(), "La Triple Dyango");


			// Funciones

			calendar.set(2020, 3, 15, 15, 30);
			inicio.setDate(15);
			inicio.setMonth(3);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(15);
			inicio.setMinutes(30);

			fin.setYear(-1900 + 2020);
			fin.setDate(31);
			fin.setMonth(2);

			espcController.altaFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 1", inicio, new String[]{ dm.getNickname(), cl.getNickname() }, fin, "/media/img/funcion.png");


			inicio.setDate(1);
			inicio.setMonth(4);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(17);
			inicio.setMinutes(0);
			fin.setDate(31);
			fin.setMonth(2);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 2", inicio, new String[]{ bs.getNickname() }, fin, "/media/img/funcion.png");


			inicio.setDate(1);
			inicio.setMonth(5);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(17);
			inicio.setMinutes(0);
			fin.setDate(31);
			fin.setMonth(2);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 3", inicio, new String[]{ bs.getNickname(), cl.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(10);
			inicio.setMonth(5);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(19);
			inicio.setMinutes(0);
			fin.setDate(20);
			fin.setMonth(3);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Facebook Watch", "Global Spirit", "Global Spirit (I)", inicio, new String[]{ vp.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(10);
			inicio.setMonth(6);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(20);
			inicio.setMinutes(0);
			fin.setDate(20);
			fin.setMonth(3);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Facebook Watch", "Global Spirit", "Global Spirit (II)", inicio, new String[]{ cl.getNickname(), bs.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(10);
			inicio.setMonth(7);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(17);
			inicio.setMinutes(45);
			fin.setDate(20);
			fin.setMonth(3);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Facebook Watch", "Global Spirit", "Global Spirit (III)", inicio, new String[]{ pi.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(15);
			inicio.setMonth(7);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(16);
			inicio.setMinutes(30);
			fin.setDate(20);
			fin.setMonth(4);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "Memphis Blues World", "Memphis Blues World - A", inicio, new String[]{ bs.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(31);
			inicio.setMonth(7);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(19);
			inicio.setMinutes(30);
			fin.setDate(30);
			fin.setMonth(4);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "Memphis Blues World", "Memphis Blues World - B", inicio, new String[]{ bs.getNickname(), dm.getNickname() }, fin, "/media/img/funcion.png");


			inicio.setDate(30);
			inicio.setMonth(8);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(20);
			inicio.setMinutes(0);
			fin.setDate(30);
			fin.setMonth(4);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "Memphis Blues World", "Memphis Blues World - C", inicio, new String[]{ pi.getNickname(), bs.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(1);
			inicio.setMonth(8);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(19);
			inicio.setMinutes(30);
			fin.setDate(7);
			fin.setMonth(5);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - i", inicio, new String[]{ dm.getNickname(), tn.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(30);
			inicio.setMonth(8);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(17);
			inicio.setMinutes(0);
			fin.setDate(7);
			fin.setMonth(5);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - ii", inicio, new String[]{ tn.getNickname(), ll.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(15);
			inicio.setMonth(9);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(20);
			inicio.setMinutes(0);
			fin.setDate(7);
			fin.setMonth(5);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - iii", inicio, new String[]{ ll.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(25);
			inicio.setMonth(8);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(19);
			inicio.setMinutes(0);
			fin.setDate(8);
			fin.setMonth(6);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "Bien de Familia", "Bien de Familia - A", inicio, new String[]{ al.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(25);
			inicio.setMonth(9);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(18);
			inicio.setMinutes(30);
			fin.setDate(8);
			fin.setMonth(6);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "Bien de Familia", "Bien de Familia - B", inicio, new String[]{ tn.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(25);
			inicio.setMonth(10);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(17);
			inicio.setMinutes(45);
			fin.setDate(8);
			fin.setMonth(6);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "Bien de Familia", "Bien de Familia - C", inicio, new String[]{}, fin, "/media/img/funcion.png");


			inicio.setDate(1);
			inicio.setMonth(8);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(21);
			inicio.setMinutes(0);
			fin.setDate(31);
			fin.setMonth(6);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "30 anios", "30 anios - 1", inicio, new String[]{ dy.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(1);
			inicio.setMonth(9);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(21);
			inicio.setMinutes(0);
			fin.setDate(31);
			fin.setMonth(6);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "30 anios", "30 anios - 2", inicio, new String[]{ pi.getNickname(), dy.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(15);
			inicio.setMonth(10);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(21);
			inicio.setMinutes(0);
			fin.setDate(31);
			fin.setMonth(6);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Twitter Live", "30 anios", "30 anios - 3", inicio, new String[]{ pi.getNickname(), dy.getNickname() }, fin, "/media/img/funcion.png");

			inicio.setDate(19);
			inicio.setMonth(11);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(17);
			inicio.setMinutes(0);
			fin.setDate(25);
			fin.setMonth(10);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Youtube", e7.getNombre(), "Grandes Exitos 2020 - Dia", inicio, new String[]{ pi.getNickname()}, fin, "/media/img/funcion.png");

			inicio.setDate(19);
			inicio.setMonth(11);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(21);
			inicio.setMinutes(0);
			fin.setDate(25);
			fin.setMonth(10);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Youtube", e7.getNombre(), "Grandes Exitos 2020 - Noche", inicio, new String[]{ pi.getNickname()}, fin, "/media/img/funcion.png");

			inicio.setDate(18);
			inicio.setMonth(11);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(21);
			inicio.setMinutes(30);
			fin.setDate(24);
			fin.setMonth(10);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Instagram Live", e8.getNombre(), "Llega a Casa - 1", inicio, null, fin, "/media/img/funcion.png");

			inicio.setDate(19);
			inicio.setMonth(11);
			inicio.setYear(-1900 + 2020);
			inicio.setHours(21);
			inicio.setMinutes(30);
			fin.setDate(24);
			fin.setMonth(10);
			fin.setYear(-1900 + 2020);
			espcController.altaFuncion("Instagram Live", e8.getNombre(), "Llega a Casa - 2", inicio, null, fin, "/media/img/funcion.png");

			
						
			// Compras de Paquetes

			calendar.set(2020, 4, 1);
			pqtController.compraPaquete("Paquete de Bandas", ap.getEmail(), calendar.getTime());
			calendar.set(2020, 4, 20);
			pqtController.compraPaquete("Paquete de Bandas", ml.getEmail(), calendar.getTime());
			calendar.set(2020, 7, 9);
			pqtController.compraPaquete("Paquete Latino", co.getEmail(), calendar.getTime());
			calendar.set(2020, 7, 20);
			pqtController.compraPaquete("Paquete Solistas", el.getEmail(), calendar.getTime());
			calendar.set(2020, 7, 26);
			pqtController.compraPaquete("Paquete Solistas", ew.getEmail(), calendar.getTime());
			calendar.set(2020, 10, 25);
			pqtController.compraPaquete("La Triple Dyango", ml.getEmail(), calendar.getTime());
			calendar.set(2020, 10, 26);
			pqtController.compraPaquete("La Triple Dyango", cb.getEmail(), calendar.getTime());


			// Registro funciones


			calendar.set(2020, 3, 9);
			RegistroFuncionDTO r_uno = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 1", co.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 3, 10);
			RegistroFuncionDTO r_dos = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 1", sp.getEmail(), calendar.getTime(), null, null, null);


			calendar.set(2020, 3, 12);
			RegistroFuncionDTO r_tres = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 1", ar.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 3, 15);
			RegistroFuncionDTO r_cuatro = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 2", ar.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 3, 20);
			RegistroFuncionDTO r_cinco = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 2", ap.getEmail(), calendar.getTime(), null, null, null);


			calendar.set(2020, 3, 25);
			RegistroFuncionDTO r_seis = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 2", co.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 3, 28);
			RegistroFuncionDTO r_siete = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 2", ml.getEmail(), calendar.getTime(), null, null, null);


			calendar.set(2020, 3, 16);
			RegistroFuncionDTO r_ocho = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 3", cb.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 4, 15);
			RegistroFuncionDTO r_nueve = espcController.registroFuncion("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 3", co.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 4, 20);
			RegistroFuncionDTO r_diez = espcController.registroFuncionPaquete("Instagram Live", "Los Village Volvieron", "Los Village Volvieron - 3", ml.getEmail(), calendar.getTime(),
					"Paquete de Bandas");

			calendar.set(2020, 4, 5);
			RegistroFuncionDTO r_once = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (I)", el.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 4, 10);
			RegistroFuncionDTO r_doce = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (I)", ew.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 4, 15);
			RegistroFuncionDTO r_trece = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (I)", sp.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 4, 20);
			RegistroFuncionDTO r_catorce = espcController.registroFuncionPaquete("Facebook Watch", "Global Spirit", "Global Spirit (I)", ap.getEmail(), calendar.getTime(), "Paquete de Bandas");

			calendar.set(2020, 5, 8);
			RegistroFuncionDTO r_quince = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (II)", gh.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 5, 13);
			RegistroFuncionDTO r_dieciseis = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (II)", ew.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 5, 25);
			RegistroFuncionDTO r_diecisiete = espcController.registroFuncionPaquete("Facebook Watch", "Global Spirit", "Global Spirit (II)", ml.getEmail(), calendar.getTime(), "Paquete de Bandas");

			calendar.set(2020, 6, 5);
			RegistroFuncionDTO r_dieciocho = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (III)", cb.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 6, 11);
			RegistroFuncionDTO r_diecinueve = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (III)", sp.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 6, 18);
			RegistroFuncionDTO r_veinte = espcController.registroFuncion("Facebook Watch", "Global Spirit", "Global Spirit (III)", ar.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 6, 19);
			RegistroFuncionDTO r_veintiuno = espcController.registroFuncion("Twitter Live", "Memphis Blues World", "Memphis Blues World - A", ml.getEmail(), calendar.getTime(), r_siete.getIdentificador(),
					r_diez.getIdentificador(), r_diecisiete.getIdentificador());

			calendar.set(2020, 7, 17);
			RegistroFuncionDTO r_veintidos = espcController.registroFuncionPaquete("Twitter Live", "Memphis Blues World", "Memphis Blues World - B", el.getEmail(), calendar.getTime(),
					"Paquete Solistas");

			calendar.set(2020, 7, 20);
			RegistroFuncionDTO r_veintitres = espcController.registroFuncion("Twitter Live", "Memphis Blues World", "Memphis Blues World - B", gh.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 23);
			RegistroFuncionDTO r_veinticuatro = espcController.registroFuncion("Twitter Live", "Memphis Blues World", "Memphis Blues World - B", ar.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 15);
			RegistroFuncionDTO r_veinticinco = espcController.registroFuncion("Twitter Live", "Memphis Blues World", "Memphis Blues World - C", co.getEmail(), calendar.getTime(), r_uno.getIdentificador(),
					r_seis.getIdentificador(), r_nueve.getIdentificador());

			calendar.set(2020, 7, 26);
			RegistroFuncionDTO r_veintiseis = espcController.registroFuncionPaquete("Twitter Live", "Memphis Blues World", "Memphis Blues World - C", ew.getEmail(), calendar.getTime(),
					"Paquete Solistas");

			calendar.set(2020, 6, 19);
			RegistroFuncionDTO r_veintisiete = espcController.registroFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - i", ar.getEmail(), calendar.getTime(), r_tres.getIdentificador(),
					r_cuatro.getIdentificador(), r_veinte.getIdentificador());

			calendar.set(2020, 7, 16);
			RegistroFuncionDTO r_veintiocho = espcController.registroFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - i", ap.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 24);
			RegistroFuncionDTO r_veintinueve = espcController.registroFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - i", ml.getEmail(), calendar.getTime(), null, null,
					null);

			calendar.set(2020, 7, 1);
			RegistroFuncionDTO r_treinta = espcController.registroFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - ii", sp.getEmail(), calendar.getTime(), r_dos.getIdentificador(),
					r_trece.getIdentificador(), r_diecinueve.getIdentificador());

			calendar.set(2020, 7, 30);
			RegistroFuncionDTO r_treintaiuno = espcController.registroFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - ii", gh.getEmail(), calendar.getTime(), null, null,
					null);

			calendar.set(2020, 7, 16);
			RegistroFuncionDTO r_treintaidos = espcController.registroFuncionPaquete("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - iii", el.getEmail(), calendar.getTime(),
					"Paquete Solistas");

			calendar.set(2020, 7, 16);
			RegistroFuncionDTO r_treintaitres = espcController.registroFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - iii", co.getEmail(), calendar.getTime(), null, null,
					null);

			calendar.set(2020, 8, 5);
			RegistroFuncionDTO r_treintaicuatro = espcController.registroFuncionPaquete("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - iii", ew.getEmail(), calendar.getTime(),
					"Paquete Solistas");

			calendar.set(2020, 8, 5);
			RegistroFuncionDTO r_treintaicinco = espcController.registroFuncion("Youtube", "Springsteen on Broadway", "Springsteen on Broadway - iii", sp.getEmail(), calendar.getTime(), null, null,
					null);

			calendar.set(2020, 7, 16);
			RegistroFuncionDTO r_treintaiseis = espcController.registroFuncion("Twitter Live", "Bien de Familia", "Bien de Familia - A", gh.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 8, 3);
			RegistroFuncionDTO r_treintaisiete = espcController.registroFuncion("Twitter Live", "Bien de Familia", "Bien de Familia - A", cb.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 16);
			RegistroFuncionDTO r_treintaiocho = espcController.registroFuncion("Twitter Live", "Bien de Familia", "Bien de Familia - B", el.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 8, 6);
			RegistroFuncionDTO r_treintainueve = espcController.registroFuncion("Twitter Live", "Bien de Familia", "Bien de Familia - B", cb.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 8, 1);
			RegistroFuncionDTO r_cuarenta = espcController.registroFuncionPaquete("Twitter Live", "Bien de Familia", "Bien de Familia - C", co.getEmail(), calendar.getTime(), "Paquete Latino");

			calendar.set(2020, 7, 16);
			RegistroFuncionDTO r_cuarentaiuno = espcController.registroFuncion("Twitter Live", "30 anios", "30 anios - 1", sp.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 20);
			RegistroFuncionDTO r_cuarentaidos = espcController.registroFuncion("Twitter Live", "30 anios", "30 anios - 1", el.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 31);
			RegistroFuncionDTO r_cuarentaitres = espcController.registroFuncion("Twitter Live", "30 anios", "30 anios - 1", ap.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 16);
			RegistroFuncionDTO r_cuarentaicuatro = espcController.registroFuncion("Twitter Live", "30 anios", "30 anios - 2", ar.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 7, 20);
			RegistroFuncionDTO r_cuarentaicinco = espcController.registroFuncion("Twitter Live", "30 anios", "30 anios - 2", ap.getEmail(), calendar.getTime(), null, null, null);

			calendar.set(2020, 8, 2);
			RegistroFuncionDTO r_cuarentaiseis = espcController.registroFuncionPaquete("Twitter Live", "30 anios", "30 anios - 2", co.getEmail(), calendar.getTime(), "Paquete Latino");

			calendar.set(2020, 10, 26);
			RegistroFuncionDTO r_cuarentaisiete = espcController.registroFuncionPaquete("Youtube", "Grandes Exitos 2020", "Grandes Exitos 2020 - Dia", cb.getEmail(), calendar.getTime(), "La Triple Dyango");

			calendar.set(2020, 10, 27);
			RegistroFuncionDTO r_cuarentaiocho = espcController.registroFuncion("Youtube", "Grandes Exitos 2020", "Grandes Exitos 2020 - Dia", co.getEmail(), calendar.getTime(), null,null,null);

			calendar.set(2020, 10, 28);
			RegistroFuncionDTO r_cuarentainueve = espcController.registroFuncionPaquete("Youtube", "Grandes Exitos 2020", "Grandes Exitos 2020 - Dia", ml.getEmail(), calendar.getTime(), "La Triple Dyango");


			// Agregar Seguidores

			// Artistas
			usrController.seguirUsuario(vp.getNickname(), bs.getNickname());
			usrController.seguirUsuario(dm.getNickname(), cl.getNickname());
			usrController.seguirUsuario(dm.getNickname(), bs.getNickname());
			usrController.seguirUsuario(cl.getNickname(), vp.getNickname());
			usrController.seguirUsuario(cl.getNickname(), dm.getNickname());
			usrController.seguirUsuario(cl.getNickname(), dy.getNickname());
			usrController.seguirUsuario(bs.getNickname(), vp.getNickname());
			usrController.seguirUsuario(bs.getNickname(), dm.getNickname());
			usrController.seguirUsuario(bs.getNickname(), cl.getNickname());
			usrController.seguirUsuario(bs.getNickname(), gh.getNickname());
			usrController.seguirUsuario(tn.getNickname(), cl.getNickname());
			usrController.seguirUsuario(tn.getNickname(), ll.getNickname());
			usrController.seguirUsuario(tn.getNickname(), ew.getNickname());
			usrController.seguirUsuario(ll.getNickname(), dm.getNickname());
			usrController.seguirUsuario(ll.getNickname(), pi.getNickname());
			usrController.seguirUsuario(ll.getNickname(), ew.getNickname());
			usrController.seguirUsuario(pi.getNickname(), dm.getNickname());
			usrController.seguirUsuario(pi.getNickname(), dy.getNickname());
			usrController.seguirUsuario(pi.getNickname(), al.getNickname());
			usrController.seguirUsuario(dy.getNickname(), tn.getNickname());
			usrController.seguirUsuario(dy.getNickname(), pi.getNickname());
			usrController.seguirUsuario(al.getNickname(), pi.getNickname());
			usrController.seguirUsuario(al.getNickname(), sp.getNickname());

			// Espectadores
			usrController.seguirUsuario(el.getNickname(), pi.getNickname());
			usrController.seguirUsuario(el.getNickname(), dy.getNickname());
			usrController.seguirUsuario(el.getNickname(), ew.getNickname());
			usrController.seguirUsuario(el.getNickname(), ar.getNickname());
			usrController.seguirUsuario(el.getNickname(), ap.getNickname());
			usrController.seguirUsuario(co.getNickname(), vp.getNickname());
			usrController.seguirUsuario(co.getNickname(), dm.getNickname());
			usrController.seguirUsuario(co.getNickname(), cl.getNickname());
			usrController.seguirUsuario(co.getNickname(), bs.getNickname());
			usrController.seguirUsuario(co.getNickname(), tn.getNickname());
			usrController.seguirUsuario(co.getNickname(), ll.getNickname());
			usrController.seguirUsuario(co.getNickname(), pi.getNickname());
			usrController.seguirUsuario(co.getNickname(), dy.getNickname());
			usrController.seguirUsuario(co.getNickname(), al.getNickname());
			usrController.seguirUsuario(ew.getNickname(), dm.getNickname());
			usrController.seguirUsuario(ew.getNickname(), cl.getNickname());
			usrController.seguirUsuario(ew.getNickname(), bs.getNickname());
			usrController.seguirUsuario(ew.getNickname(), gh.getNickname());
			usrController.seguirUsuario(gh.getNickname(), bs.getNickname());
			usrController.seguirUsuario(gh.getNickname(), ll.getNickname());
			usrController.seguirUsuario(gh.getNickname(), dy.getNickname());
			usrController.seguirUsuario(sp.getNickname(), vp.getNickname());
			usrController.seguirUsuario(sp.getNickname(), ll.getNickname());
			usrController.seguirUsuario(sp.getNickname(), pi.getNickname());
			usrController.seguirUsuario(sp.getNickname(), ar.getNickname());
			usrController.seguirUsuario(sp.getNickname(), ap.getNickname());
			usrController.seguirUsuario(sp.getNickname(), ml.getNickname());
			usrController.seguirUsuario(ar.getNickname(), al.getNickname());
			usrController.seguirUsuario(ar.getNickname(), sp.getNickname());
			usrController.seguirUsuario(ap.getNickname(), al.getNickname());
			usrController.seguirUsuario(ap.getNickname(), sp.getNickname());
			usrController.seguirUsuario(ml.getNickname(), pi.getNickname());
			usrController.seguirUsuario(ml.getNickname(), al.getNickname());
			usrController.seguirUsuario(cb.getNickname(), ll.getNickname());
			usrController.seguirUsuario(cb.getNickname(), pi.getNickname());
			usrController.seguirUsuario(cb.getNickname(), al.getNickname());
			usrController.seguirUsuario(cb.getNickname(), ar.getNickname());
			usrController.seguirUsuario(cb.getNickname(), ap.getNickname());
			usrController.seguirUsuario(cb.getNickname(), ml.getNickname());

			//Sorteos
			List<Espectador> ganadores1 = new ArrayList<Espectador>();
			ganadores1.add((Espectador) usuarioHandler.getByEmail(ml.getEmail()));
			Funcion f7 = e3.getFuncion("Memphis Blues World - A");
			calendar.set(2020, 7, 17);
			Premio premio1 = new Premio(e3.getDescripcionPremios(),calendar.getTime(),f7,ganadores1);
			f7.setPremio(premio1);
			f7.setSorteoRealizado(true);
			for(Espectador e: ganadores1) {
				e.agregarPremio(premio1);
			}
			
			List<Espectador> ganadores2 = new ArrayList<Espectador>();
			ganadores2.add((Espectador) usuarioHandler.getByEmail(el.getEmail()));
			ganadores2.add((Espectador) usuarioHandler.getByEmail(gh.getEmail()));
			Funcion f8 = e3.getFuncion("Memphis Blues World - B");
			calendar.set(2020, 8, 1);
			Premio premio2 = new Premio(e3.getDescripcionPremios(),calendar.getTime(),f8,ganadores2);
			f8.setPremio(premio2);
			f8.setSorteoRealizado(true);
			for(Espectador e: ganadores2) {
				e.agregarPremio(premio2);
			}
			
			List<Espectador> ganadores3 = new ArrayList<Espectador>();
			ganadores3.add((Espectador) usuarioHandler.getByEmail(co.getEmail()));
			ganadores3.add((Espectador) usuarioHandler.getByEmail(ew.getEmail()));
			Funcion f9 = e3.getFuncion("Memphis Blues World - C");
			calendar.set(2020, 8, 30);
			Premio premio3 = new Premio(e3.getDescripcionPremios(),calendar.getTime(),f9,ganadores3);
			f9.setPremio(premio3);
			f9.setSorteoRealizado(true);
			for(Espectador e: ganadores3) {
				e.agregarPremio(premio3);
			}
			
			List<Espectador> ganadores4 = new ArrayList<Espectador>();
			ganadores4.add((Espectador) usuarioHandler.getByEmail(sp.getEmail()));
			ganadores4.add((Espectador) usuarioHandler.getByEmail(el.getEmail()));
			ganadores4.add((Espectador) usuarioHandler.getByEmail(ap.getEmail()));
			Funcion f16 = e6.getFuncion("30 anios - 1");
			calendar.set(2020, 8, 30);
			Premio premio4 = new Premio(e6.getDescripcionPremios(),calendar.getTime(),f16,ganadores4);
			f16.setPremio(premio4);
			f16.setSorteoRealizado(true);
			for(Espectador e: ganadores4) {
				e.agregarPremio(premio4);
			}
			
			List<Espectador> ganadores5 = new ArrayList<Espectador>();
			ganadores5.add((Espectador) usuarioHandler.getByEmail(ar.getEmail()));
			ganadores5.add((Espectador) usuarioHandler.getByEmail(ap.getEmail()));
			ganadores5.add((Espectador) usuarioHandler.getByEmail(co.getEmail()));
			Funcion f17 = e6.getFuncion("30 anios - 2");
			calendar.set(2020, 9, 30);
			Premio premio5 = new Premio(e6.getDescripcionPremios(),calendar.getTime(),f17,ganadores4);
			f17.setPremio(premio5);
			f17.setSorteoRealizado(true);
			for(Espectador e: ganadores5) {
				e.agregarPremio(premio5);
			}
			
			//Finalizar espectaculos
			espcController.finalizarEspectaculo(e3.getNombre());
			espcController.finalizarEspectaculo(e6.getNombre());

			
			//Favoritos
			
			espcController.marcarFavorito(e2.getNombre(), el.getNickname());
			espcController.marcarFavorito(e6.getNombre(), el.getNickname());
			espcController.marcarFavorito(e1.getNombre(), co.getNickname());
			espcController.marcarFavorito(e2.getNombre(), co.getNickname());
			espcController.marcarFavorito(e3.getNombre(), co.getNickname());
			espcController.marcarFavorito(e4.getNombre(), ew.getNickname());
			espcController.marcarFavorito(e3.getNombre(), gh.getNickname());
			espcController.marcarFavorito(e4.getNombre(), gh.getNickname());
			espcController.marcarFavorito(e4.getNombre(), sp.getNickname());
			espcController.marcarFavorito(e6.getNombre(), sp.getNickname());
			espcController.marcarFavorito(e1.getNombre(), ar.getNickname());
			espcController.marcarFavorito(e2.getNombre(), ar.getNickname());
			espcController.marcarFavorito(e6.getNombre(), ar.getNickname());
			espcController.marcarFavorito(e5.getNombre(), ap.getNickname());
			espcController.marcarFavorito(e1.getNombre(), ml.getNickname());
			espcController.marcarFavorito(e2.getNombre(), cb.getNickname());
			
			//Valoraciones
			
			espcController.valorarEspectaculo(e1.getNombre(), ar.getNickname(), 5);
			espcController.valorarEspectaculo(e1.getNombre(), ap.getNickname(), 2);
			espcController.valorarEspectaculo(e1.getNombre(), co.getNickname(), 3);
			espcController.valorarEspectaculo(e1.getNombre(), ml.getNickname(), 4);
			espcController.valorarEspectaculo(e2.getNombre(), el.getNickname(), 4);
			espcController.valorarEspectaculo(e2.getNombre(), ew.getNickname(), 1);
			espcController.valorarEspectaculo(e2.getNombre(), sp.getNickname(), 2);
			espcController.valorarEspectaculo(e2.getNombre(), ap.getNickname(), 2);
			espcController.valorarEspectaculo(e2.getNombre(), cb.getNickname(), 5);
			espcController.valorarEspectaculo(e2.getNombre(), ar.getNickname(), 5);
			espcController.valorarEspectaculo(e3.getNombre(), el.getNickname(), 2);
			espcController.valorarEspectaculo(e3.getNombre(), gh.getNickname(), 4);
			espcController.valorarEspectaculo(e3.getNombre(), ar.getNickname(), 2);
			espcController.valorarEspectaculo(e4.getNombre(), sp.getNickname(), 3);
			espcController.valorarEspectaculo(e4.getNombre(), gh.getNickname(), 4);
			espcController.valorarEspectaculo(e4.getNombre(), el.getNickname(), 2);
			espcController.valorarEspectaculo(e4.getNombre(), co.getNickname(), 1);
			espcController.valorarEspectaculo(e4.getNombre(), ew.getNickname(), 5);
			espcController.valorarEspectaculo(e5.getNombre(), gh.getNickname(), 1);
			espcController.valorarEspectaculo(e5.getNombre(), cb.getNickname(), 4);
			espcController.valorarEspectaculo(e6.getNombre(), ar.getNickname(), 5);
			espcController.valorarEspectaculo(e6.getNombre(), ap.getNickname(), 3);
			espcController.valorarEspectaculo(e6.getNombre(), co.getNickname(), 2);
		}
	}
}