package tpgr06.coronatickets.sys.espectaculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import tpgr06.coronatickets.sys.categoria.Categoria;
import tpgr06.coronatickets.sys.categoria.CategoriaDTO;
import tpgr06.coronatickets.sys.categoria.CategoriaHandler;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.Funcion;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.funcion.premio.PremioDTO;
import tpgr06.coronatickets.sys.paquete.Paquete;
import tpgr06.coronatickets.sys.paquete.PaqueteHandler;
import tpgr06.coronatickets.sys.plataforma.Plataforma;
import tpgr06.coronatickets.sys.plataforma.PlataformaHandler;
import tpgr06.coronatickets.sys.usuario.UsuarioHandler;
import tpgr06.coronatickets.sys.usuario.artista.Artista;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncion;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncionDTO;

public class EspectaculoController implements IEspectaculoController {

	private EspectaculoHandler espectaculoHandler;
	private PlataformaHandler plataformaHandler;
	private UsuarioHandler usuarioHandler;
	private CategoriaHandler categoriaHandler;
	private PaqueteHandler paqueteHandler;

	public EspectaculoController() {
		this.espectaculoHandler = EspectaculoHandler.getInstance();
		this.plataformaHandler = PlataformaHandler.getInstance();
		this.usuarioHandler = UsuarioHandler.getInstance();
		this.categoriaHandler = CategoriaHandler.getInstance();
		this.paqueteHandler = PaqueteHandler.getInstance();
	}

	public void altaEspectaculo(String nombre, String descripcion, Integer duracion, Integer cantMin, Integer cantMax,
			String url, Integer costo, Date fechaReg, String nombrePlataforma, String emailArtista, List<String> categorias,
			String img, String descripcionPremios, Integer cantidadPremios, String video)
			throws BadRequestException, NotFoundException {
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombre);
		if (espectaculo != null) {
			throw new BadRequestException(String.format("Ya existe el espectaculo de nombre %s en el sistema", nombre));
		}
		Plataforma plataforma = plataformaHandler.getByNombre(nombrePlataforma);
		if (plataforma == null) {
			throw new NotFoundException(
					String.format("La plataforma de nombre %s no se encuentra en el sistema", nombrePlataforma));
		}
		Artista artista = (Artista) usuarioHandler.getByEmail(emailArtista);
		if (artista == null) {
			throw new NotFoundException(
					String.format("El artista de email %s no se encuentra en el sistema", emailArtista));
		}
		if (cantMax < cantMin) {
			throw new BadRequestException("La cantidad maxima de espectadores es menor a la cantidad minima");
		}

		List<Categoria> catList = new LinkedList<Categoria>();
		for (String categoria : categorias) {
			Categoria cat = categoriaHandler.getByName(categoria);
			
			if (cat == null)  {
				throw new NotFoundException(String.format("No se pudo encontrar la categoria %s", categoria));
			} else {
				catList.add(cat);
			}
		}		
		
		Espectaculo nuevo = new Espectaculo(nombre, descripcion, duracion, cantMin, cantMax, url, costo, fechaReg,
				plataforma, artista, catList);
		nuevo.setImagen(img);
		nuevo.setDescripcionPremios(descripcionPremios);
		nuevo.setCantidadPremios(cantidadPremios);
		nuevo.setVideo(video);
		plataforma.agregarEspectaculo(nuevo);
		artista.agregarEspectaculo(nuevo);
		for (Categoria cat : catList) cat.addEspectaculo(nuevo);
		
		espectaculoHandler.add(nuevo);
	}

	public List<EspectaculoDTO> listarEspectaculos(String plataforma) throws NotFoundException {
		Plataforma plat = plataformaHandler.getByNombre(plataforma);
		if (plat == null) {
			throw new NotFoundException(
					String.format("La plataforma de nombre %s no se encuentra en el sistema", plataforma));
		}
		return plat.getEspectaculos()
				.values()
				.stream()
				.map(EspectaculoDTO::new)
				.filter(espectaculoDTO -> !(espectaculoDTO.getEstado().equals(EstadoEspectaculo.FINALIZADO)))
				.collect(Collectors.toList());
	}

	public EspectaculoDTO consultaEspectaculo(String nombre) throws NotFoundException {
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombre);
		if (espectaculo == null) {
			throw new NotFoundException(
					String.format("El espectaculo de nombre %s no se encuentra en el sistema", nombre));
		}
		return new EspectaculoDTO(espectaculo);
	}

	public List<EspectaculoDTO> listarEspectaculosByArtista(String nickname) throws NotFoundException {
		Artista usr = (Artista) this.usuarioHandler.getByNickname(nickname);
		if (usr == null) {
			throw new NotFoundException(String.format("Usuario %s no encontrado", nickname));
		}
		return usr.getEspectaculos()
				.stream()
				.filter(espectaculoDTO -> !espectaculoDTO.getEstado().equals(EstadoEspectaculo.FINALIZADO))
				.collect(Collectors.toList());
	}

	public List<FuncionDTO> listarFuncionesByEspectador(String nickname) throws NotFoundException {
		Espectador usr = (Espectador) this.usuarioHandler.getByNickname(nickname);
		if (usr == null) {
			throw new NotFoundException(String.format("Usuario %s no encontrado", nickname));
		}
		return usr.getFunciones();
	}

	public FuncionDTO getFuncionEspectaculo(String funcion, String espectaculo) throws NotFoundException {
		Espectaculo espec = this.espectaculoHandler.getByNombre(espectaculo);
		if (espec == null) {
			throw new NotFoundException(String.format("No se encontro el espectaculo: %s", espec));
		}
		List<FuncionDTO> func = espec.getFunciones().values().stream().map(FuncionDTO::new)
				.collect(Collectors.toList());
		for (FuncionDTO f : func) {
			if (f.getNombre().equals(funcion))
				return f;
		}
		throw new NotFoundException(String.format("No se encontro la funcion: %s", funcion));
	}

	public List<FuncionDTO> listarFuncionesByEspectaculo(String espectaculo) {
		Espectaculo espec = this.espectaculoHandler.getByNombre(espectaculo);
		return espec.getFunciones().values().stream().map(FuncionDTO::new).collect(Collectors.toList());
	}

	public void altaFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion, Date fecha,
			String[] nicknameArtistasInvitados, Date fechaAlta, String img) throws NotFoundException, BadRequestException {

		Espectaculo espec = espectaculoHandler.getByNombre(nombreEspectaculo);
		if (espec == null) {
			throw new NotFoundException(
					String.format("El espectaculo de nombre %s no se encuentra en el sistema", nombreEspectaculo));
		} else if (espec.getFuncion(nombreFuncion) != null) {
			throw new BadRequestException(
					String.format("La funcion %s ya existe en el espectaculo %s", nombreFuncion, espec.getNombre()));
		} else if (espec.getEstado()!=EstadoEspectaculo.ACEPTADO) {
			throw new BadRequestException(
					String.format("El espectaculo %s debe estar ACEPTADO", espec.getNombre()));
		}
		
		Plataforma plat = plataformaHandler.getByNombre(nombrePlataforma);
		if (plat == null) {
			throw new NotFoundException(
					String.format("La plataforma de nombre %s no se encuentra en el sistema", nombrePlataforma));
		}
		List<Artista> invitados = new ArrayList<Artista>();
		if (nicknameArtistasInvitados != null) {
			invitados = new ArrayList<Artista>();
			Artista artista = null;
			for (String nickname : nicknameArtistasInvitados) {
				artista = (Artista) usuarioHandler.getByNickname(nickname);
				if (artista == null)
					throw new NotFoundException(
							String.format("El artista de nickname %s no se encuentra en el sistema", nickname));
				if (espec.getArtista().getNickname().equals(artista.getNickname())) {
					throw new BadRequestException("El artista principal no puede ser invitado");
				}
				invitados.add(artista);
			}
		}
		Funcion nuevaFuncion = new Funcion(nombreFuncion, fecha, fechaAlta, invitados, 0);
		nuevaFuncion.setImagen(img);
		nuevaFuncion.setSorteoRealizado(false);
		nuevaFuncion.setNombreEspectaculo(espec.getNombre());
		espec.agregarFuncion(nuevaFuncion);
	}

	@Override
	public RegistroFuncionDTO registroFuncion(String nombrePlataforma, String nombreEspectaculo, String nombreFuncion,
			String espectadorEmail, Date date, String idregistro1, String idregistro2, String idregistro3)
			throws NotFoundException, BadRequestException {

		Espectaculo espec = espectaculoHandler.getByNombre(nombreEspectaculo);
		if (espec == null) {
			throw new NotFoundException(
					String.format("El espectaculo de nombre %s no se encuentra en el sistema", nombreEspectaculo));
		} else if (espec.getEstado()==EstadoEspectaculo.FINALIZADO) {
			throw new BadRequestException(
					String.format("El espectaculo %s se encuentra finalizado", espec.getNombre()));
		}
		Plataforma plat = plataformaHandler.getByNombre(nombrePlataforma);
		if (plat == null) {
			throw new NotFoundException(
					String.format("La plataforma de nombre %s no se encuentra en el sistema", nombrePlataforma));
		}

		Funcion funcion = espec.getFuncion(nombreFuncion);
		if (funcion == null) {
			throw new NotFoundException(
					String.format("La funcion de nombre %s no se encuentra en el sistema", nombreFuncion));
		}

		if (funcion.getCantEspectadores() >= espec.getCantMax()) {
			throw new BadRequestException("La funcion " + nombreFuncion + " esta agotada");
		}

		Espectador espectador = (Espectador) usuarioHandler.getByEmail(espectadorEmail);

		if (espectador == null)
			throw new NotFoundException(
					String.format("El espectador de email %s no se encuentra en el sistema", espectadorEmail));
		if (espectador.estaRegistradoEnFuncion(nombreFuncion)) {
			throw new BadRequestException("El espectador de email " + espectadorEmail + "\n"
					+ " ya esta registrado en la funcion " + nombreFuncion);
		}

		// Registro sin canje
		if ((idregistro1 == null || idregistro1.isEmpty()) && (idregistro2 == null || idregistro2.isEmpty()) 
				&& (idregistro3 == null || idregistro3.isEmpty())) {
			funcion.agregarEspectador(espectador);
			return espectador.agregarRegistro("registro" + funcion.getNombre() + funcion.getCantEspectadores(), date,
					espec.getCosto(), funcion, null, false, null);

			// registros de canje repetidos
		} else if (idregistro1.equals(idregistro2) || idregistro1.equals(idregistro3)
				|| idregistro2.equals(idregistro3)) {
			throw new BadRequestException("Canje invalido");

			// Registro con canje
		} else if (!(idregistro1 == null) && !(idregistro2 == null) && !(idregistro3 == null)) {

			// esta funcion chequea que el usuario tenga el registro, que no haya sido
			// canjeado y deja el registro como canjeado
			espectador.canjearRegistro(idregistro1);
			espectador.canjearRegistro(idregistro2);
			espectador.canjearRegistro(idregistro3);

			funcion.agregarEspectador(espectador);

			List<String> canje = new ArrayList<String>();
			canje.add(idregistro1);
			canje.add(idregistro2);
			canje.add(idregistro3);

			return espectador.agregarRegistro("registro" + funcion.getNombre() + funcion.getCantEspectadores(), date, 0,
					funcion, canje, true, null);

		}
		// Menos de 3 registros
		else {
			throw new BadRequestException("Canje invalido");

		}
	}

	@Override
	public RegistroFuncionDTO registroFuncionPaquete(String nombrePlataforma, String nombreEspectaculo,
			String nombreFuncion, String espectadorEmail, Date date, String nombrePaquete)
			throws NotFoundException, BadRequestException {
		Espectaculo espec = espectaculoHandler.getByNombre(nombreEspectaculo);
		if (espec == null) {
			throw new NotFoundException(
					String.format("El espectaculo de nombre %s no se encuentra en el sistema", nombreEspectaculo));
		} else if (espec.getEstado()==EstadoEspectaculo.FINALIZADO) {
			throw new BadRequestException(
					String.format("El espectaculo %s se encuentra finalizado", espec.getNombre()));
		}
		Plataforma plat = plataformaHandler.getByNombre(nombrePlataforma);
		if (plat == null) {
			throw new NotFoundException(
					String.format("La plataforma de nombre %s no se encuentra en el sistema", nombrePlataforma));
		}

		Funcion funcion = espec.getFuncion(nombreFuncion);
		if (funcion == null) {
			throw new NotFoundException(
					String.format("La funcion de nombre %s no se encuentra en el sistema", nombreFuncion));
		}

		if (funcion.getCantEspectadores() >= espec.getCantMax()) {
			throw new BadRequestException("La funcion " + nombreFuncion + " esta agotada");
		}

		Espectador espectador = (Espectador) usuarioHandler.getByEmail(espectadorEmail);

		if (espectador == null)
			throw new NotFoundException(
					String.format("El espectador de email %s no se encuentra en el sistema", espectadorEmail));
		if (espectador.estaRegistradoEnFuncion(nombreFuncion)) {
			throw new BadRequestException("El espectador de email " + espectadorEmail + "\n"
					+ " ya esta registrado en la funcion " + nombreFuncion);
		}
		Paquete paquete = paqueteHandler.getByNombre(nombrePaquete);
		if (paquete==null) {
			throw new NotFoundException(
					String.format("El paquete de nombre %s no se encuentra en el sistema", nombrePaquete));
		}
		if (!(espectador.tienePaquete(nombrePaquete))) {
			throw new BadRequestException(String.format("El espectador de email %s no compro el paquete %s.", espectadorEmail, nombrePaquete));
		}
		if (!paquete.tieneEspectaculo(nombreEspectaculo)) {
			throw new BadRequestException(String.format("El paquete %s no tiene el espectaculo %s.", nombrePaquete, nombreEspectaculo));
		}
		if (!paquete.estaVigente(date)) {
			throw new BadRequestException("El paquete no esta vigente");
		}
		funcion.agregarEspectador(espectador);
		return espectador.agregarRegistro("registro" + funcion.getNombre() + funcion.getCantEspectadores(), date,
				espec.getCosto()-(espec.getCosto()*paquete.getDescuento()/100), funcion, null, false, paquete.getNombre());
	}

	@Override
	public void altaCategoria(String category) throws BadRequestException {
		if (categoriaHandler.getByName(category) == null) {
			categoriaHandler.add(new Categoria(category));
		} else {
			throw new BadRequestException("Categoria ya existe");
		}
		
	}

	@Override
	public CategoriaDTO getCategoria(String name) throws NotFoundException {
		if (categoriaHandler.getByName(name) == null) {
			throw new NotFoundException("No se encontro la categoria solicitada");
		} else {
			return new CategoriaDTO(categoriaHandler.getByName(name));
		}
	}

	@Override
	public List<CategoriaDTO> listarCategorias() {
		return categoriaHandler.getAll().stream().map(CategoriaDTO::new).collect(Collectors.toList());
	}

	@Override
	public List<EspectaculoDTO> listarEspectaculosIngresados() throws NotFoundException {
		if (espectaculoHandler.getEspectaculos().isEmpty()) {
			throw new NotFoundException("No hay espectaculos en el sistema");
		} else {
			return espectaculoHandler.getEspectaculos()
					.stream()
					.map(EspectaculoDTO::new)
					.filter(espectaculoDTO -> espectaculoDTO.getEstado().equals(EstadoEspectaculo.INGRESADO))
					.collect(Collectors.toList());
		}
	}

	@Override
	public void cambiarEstadoEspectaculo(EspectaculoDTO espectaculo, EstadoEspectaculo nuevoEstado) {
		espectaculoHandler.getByNombre(espectaculo.getNombre()).setEstado(nuevoEstado);		
	}

	@Override
	public void cambiarEstadoEspectaculo(Espectaculo espectaculo, EstadoEspectaculo nuevoEstado) {
		espectaculoHandler.getByNombre(espectaculo.getNombre()).setEstado(nuevoEstado);
	}

	@Override
	public List<EspectaculoDTO> listarEspectaculosAceptados() throws NotFoundException {
		if (espectaculoHandler.getEspectaculos().isEmpty()) {
			throw new NotFoundException("No hay espectaculos en el sistema");
		} else {
			return espectaculoHandler.getEspectaculos()
					.stream()
					.map(EspectaculoDTO::new)
					.filter(espectaculoDTO -> espectaculoDTO.getEstado().equals(EstadoEspectaculo.ACEPTADO))
					.collect(Collectors.toList());
		}
	}

	@Override
	public List<EspectaculoDTO> buscarEspectaculos(String key) {
		if (key==null) {
			key="";
		}
		List<Espectaculo> espectaculos = espectaculoHandler.getEspectaculos()
						.stream()
						.filter(espectaculo -> espectaculo.getEstado().equals(EstadoEspectaculo.ACEPTADO))
						.collect(Collectors.toList());
		List<EspectaculoDTO> res = new ArrayList<EspectaculoDTO>();
		for (Espectaculo e:espectaculos) {
			if (e.getNombre().toLowerCase().contains(key.toLowerCase()) || e.getDescripcion().toLowerCase().contains(key.toLowerCase())) {
				res.add(new EspectaculoDTO(e));
			}
		}
		return res;
	}
	
	@Override
	public void marcarFavorito(String nombreEspectaculo, String nicknameEspectador){
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombreEspectaculo);
		Espectador espectador = (Espectador) usuarioHandler.getByNickname(nicknameEspectador);
		// Tengo que hacer controles
		espectaculo.agregarEspectadorFavorito(nicknameEspectador);
		espectador.agregarEspectaculoFavorito(nombreEspectaculo);
	}
	
	@Override
	public void desmarcarFavorito(String nombreEspectaculo, String nicknameEspectador){
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombreEspectaculo);
		Espectador espectador = (Espectador) usuarioHandler.getByNickname(nicknameEspectador);
		// Tengo que hacer controles
		espectador.removerEspectaculoFavorito(nombreEspectaculo);
		espectaculo.removerEspectadorFavorito(nicknameEspectador);
	}
	
	@Override
	public boolean esFavorito(String nombreEspectaculo, String nicknameEspectador){
		Espectador espectador = (Espectador) usuarioHandler.getByNickname(nicknameEspectador);
		List<String> favoritos = espectador.getEspectaculosFavoritos();
		return favoritos.contains(nombreEspectaculo);
	}
	
	@Override
	public int cantidadDeFavorito(String nombreEspectaculo){
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombreEspectaculo);
		List<String> favoritos = espectaculo.getEspectadoresFavoritos();
		return favoritos.size();
	}

	public void finalizarEspectaculo(String nombreEspectaculo) throws NotFoundException, BadRequestException {
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombreEspectaculo);
		if (espectaculo == null) {
			throw new NotFoundException("El espectaculo no se encuentra en el sistema");
		} else if (espectaculo.getEstado()!=EstadoEspectaculo.ACEPTADO) {
			throw new BadRequestException("El espectaculo no se puede finalizar");
		} else {
			EstadoEspectaculo nuevoEstado = EstadoEspectaculo.FINALIZADO;
			espectaculoHandler.getByNombre(nombreEspectaculo).setEstado(nuevoEstado);
		}
	}
	
	@Override
	public List<EspectaculoDTO> listarEspectaculosFinalizados(String nicknameArtista) throws NotFoundException {
		if (espectaculoHandler.getEspectaculos().isEmpty()) {
			throw new NotFoundException("No hay espectaculos en el sistema");
		} else {
			return espectaculoHandler.getEspectaculos()
					.stream()
					.map(EspectaculoDTO::new)
					.filter(espectaculo -> nicknameArtista.equals(espectaculo.getArtista().getNickname()))
					.filter(espectaculoDTO -> espectaculoDTO.getEstado().equals(EstadoEspectaculo.FINALIZADO))
					.collect(Collectors.toList());
		}
	}

	@Override
	public PremioDTO sortearPremios(String nombreEspectaculo, String nombreFuncion) throws NotFoundException, BadRequestException {
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombreEspectaculo);
		if(espectaculo==null) {
			throw new NotFoundException("Espectaculo no encontrado");
		}
		if(espectaculo.getCantidadPremios()<=0) {
			throw new BadRequestException("El espectaculo no tiene premios");
		}
		Funcion funcion = espectaculo.getFuncion(nombreFuncion);
		if(funcion==null) {
			throw new NotFoundException("Funcion no encontrada");
		}
		if(funcion.getFecha().compareTo(new Date())>0) {
			throw new BadRequestException("La funcion todavia no fue realizada");
		}
		if(funcion.isSorteoRealizado()) {
			throw new BadRequestException("Ya se realizo el sorteo");
		}
		if(funcion.getEspectadores().size()<=0) {
			throw new BadRequestException("La funcion no tiene espectadores registrados");
		}

		return funcion.realizarSorteo(espectaculo.getDescripcionPremios(), espectaculo.getCantidadPremios());
		
	}
		
	@Override
	public void valorarEspectaculo(String nombreEspectaculo, String nickNameEspectador, int puntaje) throws NotFoundException {
		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombreEspectaculo);
		Espectador espectador = (Espectador) usuarioHandler.getByNickname(nickNameEspectador);
		if(espectaculo==null) {
			throw new NotFoundException("Espectaculo no encontrado");
		}
		if(espectador==null) {
			throw new NotFoundException("Espectador no encontrado");
		}
		
		if(espectador.tieneValoracion(nombreEspectaculo)) {
			throw new NotFoundException("El espectaculo ya fue valorado");
		}
			espectador.agregarValoracion(nombreEspectaculo, puntaje);
			espectaculo.agregarValoracion(puntaje);
	}
	
	// Retorna 0 en caso de que no existan valoraciones.
	
	public List<EspectaculoDTO> listarEspectaculosParaValorar(String nickNameEspectador) throws NotFoundException {
		Espectador usr  = (Espectador) this.usuarioHandler.getByNickname(nickNameEspectador);
		if (usr == null) {
			throw new NotFoundException(String.format("Espectador %s no encontrado", nickNameEspectador));
		}
		
		List<RegistroFuncion> registros = usr.getRegistros();
		
		List<Espectaculo> espectaculos = new ArrayList<Espectaculo>();
		Date hoy = new Date();
		for (RegistroFuncion r: registros) {
			if (r.getFuncion().getFecha().before(hoy) && !usr.tieneValoracion(r.getFuncion().getNombreEspectaculo())) {
				Espectaculo aux = espectaculoHandler.getByNombre(r.getFuncion().getNombreEspectaculo());
				if(!espectaculos.contains(aux))
					espectaculos.add(aux);
			}
		}
		return espectaculos
			   .stream().distinct()
			   .map(EspectaculoDTO::new)
			   .collect(Collectors.toList());
	}
	
}
