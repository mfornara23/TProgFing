package tpgr06.coronatickets.sys.usuario.espectador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;

import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.Funcion;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.funcion.premio.Premio;
import tpgr06.coronatickets.sys.paquete.Paquete;
import tpgr06.coronatickets.sys.usuario.Usuario;
import tpgr06.coronatickets.sys.valoracion.ValoracionDTO;

public class Espectador extends Usuario {

	private List<RegistroFuncion> registros;
	private List<CompraPaquete> paquetes;
	private List<ValoracionDTO> valoraciones;
	private List<String> espectaculosFavoritos;
	private List<Premio> premios;

	public Espectador(String nickname, String nombre, String apellido, String email, Date fechaNac, String password, Map<String, Usuario> seguidos, Map<String, Usuario> seguidores) {
		super(nickname, nombre, apellido, email, fechaNac, password, seguidos, seguidores);
		this.registros = new ArrayList<RegistroFuncion>();
		this.paquetes = new ArrayList<CompraPaquete>();
		this.setValoraciones(new ArrayList<ValoracionDTO>());
		this.espectaculosFavoritos = new ArrayList<String>();
		this.premios = new ArrayList<Premio>();
	}

	public Espectador(String nickname, String nombre, String apellido, String email, Date fechaNac, String password, Map<String, Usuario> seguidos, Map<String, Usuario> seguidores, String imagen) {
		super(nickname, nombre, apellido, email, fechaNac, password, seguidos, seguidores, imagen);
		this.registros = new ArrayList<RegistroFuncion>();
		this.paquetes = new ArrayList<CompraPaquete>();
		this.setValoraciones(new ArrayList<ValoracionDTO>());
		this.premios = new ArrayList<Premio>();
	}

	public List<FuncionDTO> getFunciones() {
		List<FuncionDTO> res = new ArrayList<FuncionDTO>();
		for (RegistroFuncion reg : this.registros) {
			res.add(reg.getFuncion());
		}
		return res;
	}

	public boolean puedeCanjear() {
		int canje = 0;
		for (RegistroFuncion r : this.registros) {
			if (!r.getCanjeado()) {
				canje++;
			}
		}
		return canje > 2;
	}

	public List<String> getRegistrosSinCanjear() {
		List<String> res = new ArrayList<String>();
		for (RegistroFuncion r : this.registros) {
			if (!r.getCanjeado()) {
				res.add(r.getId());
			}
		}
		return res;
	}

	public boolean estaRegistradoEnFuncion(String nombreFuncion) {
		for (RegistroFuncion r : this.registros) {
			if (r.getFuncion()
					.getNombre()
					.equals(nombreFuncion)) {
				return true;
			}
		}
		return false;
	}

	public RegistroFuncionDTO agregarRegistro(String identificador, Date fechaReg, Integer costo, Funcion funcion, List<String> registrosCanje, Boolean canjeado, String paquete) throws BadRequestException {		
		CompraPaquete compra=null;

		if (paquete!=null) {
			List<CompraPaquete> paquetesComprados = this.getPaquetes();
			boolean encontrado = false;
			for (CompraPaquete c:paquetesComprados) {
				 if (c.getPaquete().getNombre().equals(paquete)) {
					 compra=c;
					 encontrado = true;
					 break;
				 }
			 }
			if (!encontrado) {
				throw new BadRequestException("El espectador no compro el paquete");
			}
		}
		RegistroFuncion nuevo = new RegistroFuncion(identificador, fechaReg, costo, funcion, registrosCanje, canjeado, compra);
		this.registros.add(nuevo);
		return new RegistroFuncionDTO(nuevo);
	}

	public void canjearRegistro(String idregistro1) throws NotFoundException, BadRequestException {
		Boolean canjeado = false;
		for (RegistroFuncion r : this.registros) {
			if (r.getId()
					.equals(idregistro1)) {
				if (r.getCanjeado()) {
					throw new BadRequestException("El registro ya fue canjeado");
				}
				if (r.getCosto() == 0) {
					throw new BadRequestException("El registro tiene costo 0");
				}
				r.setCanjeado(true);
				canjeado = true;
			}
		}
		if (!canjeado) {
			throw new NotFoundException("El usuario no tiene registro de id: " + idregistro1);
		}
	}

	public List<RegistroFuncion> getRegistros() {
		return registros;
	}

	public void setRegistros(List<RegistroFuncion> registros) {
		this.registros = registros;
	}

	public boolean tienePaquete(String nombrePaquete) {
		for (CompraPaquete c: this.paquetes) {
			if (c.getPaquete().getNombre().equals(nombrePaquete)) {
				return true;
			}
		}
		return false;
	}

	public void comprarPaquete(Paquete paquete, Date fecha) throws BadRequestException {
		if (this.tienePaquete(paquete.getNombre())) {
			throw new BadRequestException("El espectador ya compro el paquete");
		}
		CompraPaquete compra = new CompraPaquete(paquete, fecha);
		this.paquetes.add(compra);
	}

	public List<CompraPaquete> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(List<CompraPaquete> paquetes) {
		this.paquetes = paquetes;
	}
	public List<String> getEspectaculosFavoritos() {
		return espectaculosFavoritos;
	}

	public void setEspectaculosFavoritos(List<String> espectaculosFavoritos) {
		this.espectaculosFavoritos = espectaculosFavoritos;
	}


	public void agregarEspectaculoFavorito(String nombreEspectaculo) {
		this.espectaculosFavoritos.add(nombreEspectaculo);	
	}
	
	public void removerEspectaculoFavorito(String nombreEspectaculo) {
		this.espectaculosFavoritos.remove(nombreEspectaculo);
	}
	public void agregarPremio(Premio premio) {
		this.premios.add(premio);
	}
	
	public List<Premio> getPremios() {
		return premios;
	}
	
	public void setPremios(List<Premio> premios) {
		this.premios = premios;
	}

	public List<ValoracionDTO> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<ValoracionDTO> valoraciones) {
		this.valoraciones = valoraciones;
	}
	
	public void agregarValoracion(String espectaculo, Integer puntaje) {
		this.valoraciones.add(new ValoracionDTO(puntaje, espectaculo));
	}
	
	public boolean tieneValoracion(String espectaculo) {
		return this.getValoraciones().stream().map(val->val.getEspectaculo()).anyMatch(esp->esp.equals(espectaculo));
	}

	
	
}
