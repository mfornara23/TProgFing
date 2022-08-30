package tpgr06.coronatickets.sys.funcion.premio;

import java.util.Date;
import java.util.List;

import tpgr06.coronatickets.sys.funcion.Funcion;
import tpgr06.coronatickets.sys.usuario.espectador.Espectador;

public class Premio {

	private String descripcion;
	private Date fechaSorteo;
	private Funcion funcion;
	private List<Espectador> ganadores;

	public Premio(String descripcion, Date fechaSorteo, Funcion funcion, List<Espectador> ganadores) {
		this.descripcion = descripcion;
		this.fechaSorteo = fechaSorteo;
		this.funcion = funcion;
		this.ganadores = ganadores;
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
	public Funcion getFuncion() {
		return funcion;
	}
	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}
	public List<Espectador> getGanadores() {
		return ganadores;
	}
	public void setGanadores(List<Espectador> ganadores) {
		this.ganadores = ganadores;
	}
	
	
	
	
}
