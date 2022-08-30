package tpgr06.coronatickets.sys.funcion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.funcion.premio.PremioDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class FuncionDTO {

	private String nombre;
	private Date fecha;
	private Date fechaReg;
	private List<ArtistaDTO> invitados;
	private List<String> espectadores;
	private String imagen;
	private boolean sorteoRealizado;
	private PremioDTO premio;
	private String nombreEspectaculo;
	
	public FuncionDTO() {}

	public FuncionDTO(String nombre, Date fecha, Date fechaReg, List<ArtistaDTO> invitados, Integer cantEspectadores) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.fechaReg = fechaReg;
		this.invitados = invitados;
		this.setImagen(null);
		this.setSorteoRealizado(false);
		this.setPremio(null);
	}
	
	public FuncionDTO(Funcion funcion) {
		this.nombre = funcion.getNombre();
		this.fecha = funcion.getFecha();
		this.fechaReg = funcion.getFechaReg();
		this.invitados = funcion.getInvitados()
				.stream()
				.map(ArtistaDTO::new)
				.collect(Collectors.toList());
		this.setImagen(funcion.getImagen());
		this.setSorteoRealizado(funcion.isSorteoRealizado());
		if(funcion.getPremio()!=null)
			this.setPremio(new PremioDTO(funcion.getPremio()));
		if(!funcion.getEspectadores().isEmpty()) {
			this.espectadores=funcion.getEspectadores().stream().map(espectador->espectador.getEmail()).collect(Collectors.toList());
		}else {
			this.espectadores = new ArrayList<String>();
		}
		
		this.setNombreEspectaculo(funcion.getNombreEspectaculo());
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

	public List<ArtistaDTO> getInvitados() {
		return invitados;
	}

	public void setInvitados(List<ArtistaDTO> invitados) {
		this.invitados = invitados;
	}
	
	public Integer getCantEspectadores() {
		return espectadores.size();
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

	public PremioDTO getPremio() {
		return premio;
	}

	public void setPremio(PremioDTO premio) {
		this.premio = premio;
	}

	public List<String> getEspectadores() {
		return espectadores;
	}

	public void setEspectadores(List<String> espectadores) {
		this.espectadores = espectadores;
	}
	
	public String getNombreEspectaculo() {
		return nombreEspectaculo;
	}

	public void setNombreEspectaculo(String nombreEspectaculo) {
		this.nombreEspectaculo = nombreEspectaculo;
	}

	
}
