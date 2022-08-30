package tpgr06.coronatickets.sys.paquete;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import tpgr06.coronatickets.sys.categoria.Categoria;
import tpgr06.coronatickets.sys.categoria.CategoriaDTO;
import tpgr06.coronatickets.sys.espectaculo.Espectaculo;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoHandler;
import tpgr06.coronatickets.sys.espectaculo.EstadoEspectaculo;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.plataforma.Plataforma;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;
import tpgr06.coronatickets.sys.plataforma.PlataformaHandler;
import tpgr06.coronatickets.sys.usuario.UsuarioHandler;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;

public class PaqueteController implements IPaqueteController {

	private PaqueteHandler paqueteHandler;
	private PlataformaHandler plataformaHandler;
	private EspectaculoHandler espectaculoHandler;
	private UsuarioHandler usuarioHandler;

	public PaqueteController() {
		this.paqueteHandler = PaqueteHandler.getInstance();
		this.plataformaHandler = PlataformaHandler.getInstance();
		this.espectaculoHandler = EspectaculoHandler.getInstance();
		this.usuarioHandler = UsuarioHandler.getInstance();
	}

	public List<PaqueteDTO> listarPaquetes() {
		return paqueteHandler.getPaquetes()
				.stream()
				.map(PaqueteDTO::new)
				.collect(Collectors.toList());
	}

	public PaqueteDTO consultaPaquete(String nombre) throws NotFoundException {
		Paquete paquete = paqueteHandler.getByNombre(nombre);
		if (paquete == null) {
			throw new NotFoundException(String.format("El paquete de nombre %s no se encuentra en el sistema", nombre));
		}
		return new PaqueteDTO(paquete);
	}

	public List<PlataformaDTO> listarPlataformas() {
		return plataformaHandler.getPlataformas()
				.stream()
				.map(PlataformaDTO::new)
				.collect(Collectors.toList());
	}

	public List<EspectaculoDTO> listarEspectaculosNoEnPaquete(String nombrePaquete, String nombrePlataforma) throws NotFoundException {
		Plataforma plataforma = plataformaHandler.getByNombre(nombrePlataforma);
		if (plataforma == null) {
			throw new NotFoundException(String.format("La plataforma de nombre %s no se encuentra en el sistema", nombrePlataforma));
		}

		Paquete paquete = paqueteHandler.getByNombre(nombrePaquete);
		if (paquete == null) {
			throw new NotFoundException(String.format("El paquete de nombre %s no se encuentra en el sistema", nombrePaquete));
		}

		return plataforma.getEspectaculos()
				.values()
				.stream()
				.map(EspectaculoDTO::new)
				.filter(espectaculoDTO -> paquete.getEspectaculos().get(espectaculoDTO.getNombre()) == null)
				.filter(espectaculoDTO -> EstadoEspectaculo.ACEPTADO.equals(espectaculoDTO.getEstado()))
				.collect(Collectors.toList());
	}

	public void agregarEspectaculoAPaquete(String nombreEspectaculo, String nombrePaquete) throws NotFoundException, BadRequestException {
		Paquete paquete = paqueteHandler.getByNombre(nombrePaquete);
		if (paquete == null) {
			throw new NotFoundException(String.format("El paquete de nombre %s no se encuentra en el sistema", nombrePaquete));
		}

		Espectaculo espectaculo = espectaculoHandler.getByNombre(nombreEspectaculo);
		if (espectaculo == null) {
			throw new NotFoundException(String.format("El espectaculo %s no se encuentra en el sistema", nombreEspectaculo));
		}

		EstadoEspectaculo estadoEspectaculo = espectaculo.getEstado();
		if ( estadoEspectaculo.equals(EstadoEspectaculo.INGRESADO)) {
			throw new BadRequestException(String.format("El espectaculo %s esta en estado INGRESADO, pero aun no se a ACEPTADO", nombreEspectaculo));
		}

		if ( estadoEspectaculo.equals(EstadoEspectaculo.RECHAZADO)) {
			throw new BadRequestException(String.format("El espectaculo %s se encuentra en estado RECHAZADO", nombreEspectaculo));
		}
		
		if ( estadoEspectaculo.equals(EstadoEspectaculo.FINALIZADO)) {
			throw new BadRequestException(String.format("El espectaculo %s se encuentra en estado FINALIZADO", nombreEspectaculo));
		}
		
		if (paquete.getEspectaculos().get(espectaculo.getNombre())!=null) {
			throw new BadRequestException(String.format("El espectaculo %s ya se encuentra en el paquete", nombreEspectaculo));
		}

		paquete.getEspectaculos().put(espectaculo.getNombre(), espectaculo);
	}

	public void altaPaquete(String nombre, String descripcion, Date fechaInicio, Date fechaFin, Integer descuento, String imagen) throws BadRequestException {
		if (fechaInicio.after(fechaFin)) {
			throw new BadRequestException("La fecha de inicio ingresada es posterior a la final");
		}
		Paquete paquete = paqueteHandler.getByNombre(nombre);
		if (paquete != null) {
			throw new BadRequestException(String.format("Ya existe el paquete de nombre %s en el sistema", nombre));
		}
		
		if (descuento>100 || descuento<0 ) {
			throw new BadRequestException("El descuento debe ser un valor entre 0 y 100");
		}
		
		paquete = new Paquete(nombre, descripcion, fechaInicio, fechaFin, descuento);
		paquete.setImagen(imagen);
		paqueteHandler.add(paquete);
	}

	@Override
	public void compraPaquete(String nombrePaquete, String emailEspectador, Date fechaCompra) throws NotFoundException, BadRequestException {
		Paquete paquete = paqueteHandler.getByNombre(nombrePaquete);
		if (paquete==null) {
			throw new NotFoundException(String.format("El paquete de nombre %s no existe", nombrePaquete));
		}
		Espectador espectador = (Espectador) usuarioHandler.getByEmail(emailEspectador);
		if (espectador==null) {
			throw new NotFoundException(String.format("El espectador de email %s no esta registrado", emailEspectador ));
		}
		espectador.comprarPaquete(paquete, fechaCompra);
	}

	public PlataformaDTO consultaPlataforma(String nombre) throws NotFoundException {
		Plataforma plataforma = plataformaHandler.getByNombre(nombre);
		if (plataforma == null) {
			throw new NotFoundException(String.format("El paquete de nombre %s no se encuentra en el sistema", nombre));
		}
		return new PlataformaDTO(plataforma);
	}

	public boolean existePlataforma(String nombre) {
		Plataforma plataforma = plataformaHandler.getByNombre(nombre);
		boolean existe = true;
		if (plataforma == null) {
			existe = false;
		}
		return existe;
	}

	public void altaPlataforma(String nombre, String descripcion, String url) throws BadRequestException {
		Plataforma plataforma = plataformaHandler.getByNombre(nombre);
		if (plataforma != null) {
			throw new BadRequestException(String.format("Ya existe plataforma de nombre %s en el sistema", nombre));
		}
		Plataforma p_nueva = new Plataforma(nombre, descripcion, url);
		plataformaHandler.add(p_nueva);
	}

	// La fechaVigente sale del lado de la Web
	public List<PaqueteDTO> listarPaquetesVigentes(Date fechaVigente) { 
		return paqueteHandler.getPaquetes()
				.stream()
				.map(PaqueteDTO::new)
				.filter(PaqueteDTO -> PaqueteDTO.getFechaInicio().compareTo(fechaVigente) <= 0
				&& PaqueteDTO.getFechaFin().compareTo(fechaVigente) >= 0 )
				.collect(Collectors.toList());
	}


	public List<CategoriaDTO> listarCategoriasByPaquete(String nombre) throws NotFoundException { 
		Paquete paquete = paqueteHandler.getByNombre(nombre);
		if (paquete == null) {
			throw new NotFoundException(String.format("El paquete de nombre %s no se encuentra en el sistema", nombre));
		}
		List<CategoriaDTO> categoriasDTO = new ArrayList<CategoriaDTO>();
		Iterator<Entry<String, Espectaculo>> iterator = paquete.getEspectaculos().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Espectaculo> pair = (Map.Entry<String, Espectaculo>) iterator.next();
			List<Categoria> categorias = pair.getValue().getCategorias().values().stream().collect(Collectors.toList());

			for (Categoria cat: categorias) {
				CategoriaDTO catDTO = new CategoriaDTO(cat);

				categoriasDTO.add(catDTO);
			}
		}
		return categoriasDTO;
	}

	@Override
	public List<PaqueteDTO> buscarPaquetes(String key) {
		if (key==null) {
			key="";
		}
		List<Paquete> paquetes = paqueteHandler.getPaquetes();
		List<PaqueteDTO> res = new ArrayList<PaqueteDTO>();
		for (Paquete p:paquetes) {
			if (p.getNombre().toLowerCase().contains(key.toLowerCase()) || p.getDescripcion().toLowerCase().contains(key.toLowerCase())) {
				res.add(new PaqueteDTO(p));
			}
		}
		return res;
	}

	@Override
	public List<PaqueteDTO> listarPaquetesByEspecatculo(String espectaculo) {
		List<Paquete> paquetes = paqueteHandler.getPaquetes();
		List<PaqueteDTO> paquetesDTO = new LinkedList<PaqueteDTO>();
		for (Paquete paquete : paquetes) {
			for (Espectaculo spectacle : paquete.getEspectaculos().values()) {
				if (espectaculo.equals(spectacle.getNombre())) paquetesDTO.add(new PaqueteDTO(paquete));
			}
		}

		return paquetesDTO;
	}

}
