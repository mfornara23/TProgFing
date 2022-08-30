package tpgr06.coronatickets.sys.plataforma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlataformaHandler {
	private static PlataformaHandler instance;
	private Map<String, Plataforma> plataformas;

	public static PlataformaHandler getInstance() {
		if (instance == null) {
			instance = new PlataformaHandler();
		}
		return instance;
	}

	private PlataformaHandler() {
		this.plataformas = new HashMap<String, Plataforma>();
	}

	public void add(Plataforma plataforma) {
		this.plataformas.put(plataforma.getNombre(), plataforma);
	}

	public List<Plataforma> getPlataformas() {
		return new ArrayList<Plataforma>(this.plataformas.values());
	}

	public Plataforma getByNombre(String nombre) {
		return this.plataformas.get(nombre);
	}

	public void clear() {
		this.plataformas.clear();
	}
}
