package tpgr06.coronatickets.sys.funcion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.funcion.premio.Premio;
import tpgr06.coronatickets.sys.funcion.premio.PremioDTO;
import tpgr06.coronatickets.sys.usuario.artista.Artista;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;

public class Funcion {

	private String nombre;
	private Date fecha;
	private Date fechaReg;
	private List<Artista> invitados;
	private List<Espectador> espectadores;
	private String imagen;
	private boolean sorteoRealizado;
	private String nombreEspectaculo;
	private Premio premio;


	public Funcion(String nombre, Date fecha, Date fechaReg, List<Artista> invitados, Integer cantEspectadores) {
		super();
		this.nombre = nombre;
		this.fecha = (Date) fecha.clone();
		this.fechaReg = (Date) fechaReg.clone();
		this.invitados = invitados;
		this.setImagen(null);
		this.setSorteoRealizado(false);
		this.espectadores = new ArrayList<Espectador>();
		this.setNombreEspectaculo(null);
		this.setPremio(null);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaReg() {
		return fechaReg;
	}

	public void setFechaReg(Date fechaReg) {
		this.fechaReg = fechaReg;
	}

	public List<Artista> getInvitados() {
		return invitados;
	}

	public void setInvitados(List<Artista> invitados) {
		this.invitados = invitados;
	}

	public void addInivitado(Artista artista) {
		if (!invitados.contains(artista)) {
			invitados.add(artista);
		}else {
			//to-do: lanzar excepcion
		}
	}

	public Integer getCantEspectadores() {
		return espectadores.size();
	}

	public FuncionDTO getFuncionDTO() {
		return new FuncionDTO(this);
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean isSorteoRealizado() {
		return sorteoRealizado;
	}

	public void setSorteoRealizado(boolean sorteoRealizado) {
		this.sorteoRealizado = sorteoRealizado;
	}

	public List<Espectador> getEspectadores() {
		return espectadores;
	}

	public void setEspectadores(List<Espectador> espectadores) {
		this.espectadores = espectadores;
	}

	public void agregarEspectador(Espectador espectador) {
		this.espectadores.add(espectador);
	}

	public String getNombreEspectaculo() {
		return nombreEspectaculo;
	}

	public void setNombreEspectaculo(String nombreEspectaculo) {
		this.nombreEspectaculo = nombreEspectaculo;
	}

	public Premio getPremio() {
		return premio;
	}

	public void setPremio(Premio premio) {
		this.premio = premio;
	}

	public PremioDTO realizarSorteo(String descripcionPremio, Integer cantidadPremios) throws BadRequestException {
		List<Espectador> ganadores = new ArrayList<Espectador>();
		Integer cantidadEspectadores = this.espectadores.size();
		if(cantidadPremios>=cantidadEspectadores) {
			ganadores = this.espectadores.stream().collect(Collectors.toList());
		}else {
			int[] ints = new Random().ints(0, cantidadEspectadores).distinct().limit(cantidadPremios).toArray();
			for(int i:ints) {
				ganadores.add(this.espectadores.get(i));
			}
		}
		Premio premio = new Premio(descripcionPremio, new Date(), this, ganadores);
		this.premio = premio;
		this.setSorteoRealizado(true);
		for(Espectador e: ganadores) {
			e.agregarPremio(premio);
		}
		return new PremioDTO(this.getPremio());
	}

}
