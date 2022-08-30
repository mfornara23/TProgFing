package tpgr06.coronatickets.sys.funcion.premio;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PremioDTO {

	private String descripcion;
	private Date fechaSorteo;
	private String funcion;
	private String espectaculo;
	private List<String> ganadores;

	public PremioDTO(Premio premio) {
		if(premio!=null) {
			this.descripcion = premio.getDescripcion();
			this.fechaSorteo = premio.getFechaSorteo();
			this.funcion = premio.getFuncion().getNombre();
			this.espectaculo = premio.getFuncion().getNombreEspectaculo();
			this.ganadores = premio.getGanadores().stream().map(esp->esp.getEmail()).collect(Collectors.toList());
		}
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaSorteo() {
		return fechaSorteo;
	}
	public void setFechaSorteo(Date fechaSorteo) {
		this.fechaSorteo = fechaSorteo;
	}
	public String getFuncion() {
		return funcion;
	}
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}
	public String getEspectaculo() {
		return espectaculo;
	}
	public void setEspectaculo(String espectaculo) {
		this.espectaculo = espectaculo;
	}
	public List<String> getGanadores() {
		return ganadores;
	}
	public void setGanadores(List<String> ganadores) {
		this.ganadores = ganadores;
	}
	
	
}
