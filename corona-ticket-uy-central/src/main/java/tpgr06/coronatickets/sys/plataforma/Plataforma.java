package tpgr06.coronatickets.sys.plataforma;

import java.util.HashMap;
import java.util.Map;
import tpgr06.coronatickets.sys.espectaculo.Espectaculo;

public class Plataforma {

	private String nombre;
	private String descripcion;
	private String url;
	private Map<String, Espectaculo> espectaculos;

	public Plataforma() {
		this.espectaculos = new HashMap<String, Espectaculo>();
	}

	public Plataforma(String nombre, String descripcion, String url) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.url = url;
		this.espectaculos = new HashMap<String, Espectaculo>();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Espectaculo> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(Map<String, Espectaculo> espectaculos) {
		this.espectaculos = espectaculos;
	}

	public void agregarEspectaculo(Espectaculo nuevo) {
		this.espectaculos.put(nuevo.getNombre(), nuevo);	
	}
}
