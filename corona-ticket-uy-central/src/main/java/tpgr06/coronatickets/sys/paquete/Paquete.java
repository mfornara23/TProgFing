package tpgr06.coronatickets.sys.paquete;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import tpgr06.coronatickets.sys.espectaculo.Espectaculo;

public class Paquete {

	private String nombre;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer descuento;
	private Map<String, Espectaculo> espectaculos;
	private String imagen;

	public Paquete() {
		this.espectaculos = new HashMap<String, Espectaculo>();
	}

	public Paquete(String nombre, String descripcion, Date fechaInicio, Date fechaFin, Integer descuento) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = (Date) fechaInicio.clone();
		this.fechaFin = (Date) fechaFin.clone();
		this.espectaculos = new HashMap<String, Espectaculo>();
		this.descuento = descuento;
		this.setImagen(null);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

	public Map<String, Espectaculo> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(Map<String, Espectaculo> espectaculos) {
		this.espectaculos = espectaculos;
	}
	
	public boolean tieneEspectaculo(String nombreEspec) {
		return this.espectaculos.get(nombreEspec)!=null;
	}
	
	public boolean estaVigente(Date fecha) {
		return this.fechaInicio.compareTo(fecha)<=0 && this.fechaFin.compareTo(fecha)>0;
	}
	
	public void addEspectaculo(Espectaculo espectaculo) {
		this.espectaculos.put(espectaculo.getNombre(), espectaculo);
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
