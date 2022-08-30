package tpgr06.coronatickets.sys.espectaculo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EspectaculoHandler {
	private static EspectaculoHandler instance;
	private Map<String, Espectaculo> espectaculos;

	public static EspectaculoHandler getInstance() {
		if (instance == null) {
			instance = new EspectaculoHandler();
		}
		return instance;
	}

	private EspectaculoHandler() {
		this.espectaculos = new HashMap<String, Espectaculo>();
	}

	public void add(Espectaculo espectaculo) {
		this.espectaculos.put(espectaculo.getNombre(), espectaculo);
	}

	public List<Espectaculo> getEspectaculos() {
		return new ArrayList<Espectaculo>(this.espectaculos.values());
	}

	public Espectaculo getByNombre(String nombre) {
		return this.espectaculos.get(nombre);
	}

	public void clear() {
		this.espectaculos.clear();
	}
	
}
