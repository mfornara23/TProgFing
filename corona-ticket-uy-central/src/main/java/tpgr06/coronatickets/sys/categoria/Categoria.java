package tpgr06.coronatickets.sys.categoria;

import java.util.HashMap;
import java.util.Map;

import tpgr06.coronatickets.sys.espectaculo.Espectaculo;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;

public class Categoria {

	private String nombre;
	private Map<String, Espectaculo> espectaculos;

	public Categoria(String nombre) {
		this.nombre = nombre;
		this.espectaculos = new HashMap<String, Espectaculo>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<String, Espectaculo> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(Map<String, Espectaculo> espectaculos) {
		this.espectaculos = espectaculos;
	}

	public void addEspectaculo(Espectaculo espec) throws BadRequestException {
		if (espectaculos.get(espec.getNombre()) == null) {
			espectaculos.put(espec.getNombre(), espec);
		} else {
			throw new BadRequestException(String.format("Espectaculo %s ya existe en la categoria", espec.getNombre()));
		}
	}

	public Espectaculo getEspectaculo(String nombre) throws NotFoundException {
		if (espectaculos.get(nombre) == null) {
			throw new NotFoundException(String.format("Especatculo %s no encontrado", nombre));
		} else {
			return espectaculos.get(nombre);
		}
	}

}
