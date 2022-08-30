package tpgr06.coronatickets.sys.paquete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaqueteHandler {
	private static PaqueteHandler instance;
	private Map<String, Paquete> paquetes;

	public static PaqueteHandler getInstance() {
		if (instance == null) {
			instance = new PaqueteHandler();
		}
		return instance;
	}

	private PaqueteHandler() {
		this.paquetes = new HashMap<String, Paquete>();
	}

	public void add(Paquete paquete) {
		this.paquetes.put(paquete.getNombre(), paquete);
	}

	public List<Paquete> getPaquetes() {
		return new ArrayList<Paquete>(this.paquetes.values());
	}

	public Paquete getByNombre(String nombre) {
		return this.paquetes.get(nombre);
	}

	public void clear() {
		this.paquetes.clear();
	}
}
