package tpgr06.coronatickets.sys;

import tpgr06.coronatickets.sys.espectaculo.EspectaculoController;
import tpgr06.coronatickets.sys.espectaculo.IEspectaculoController;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.paquete.PaqueteController;
import tpgr06.coronatickets.sys.usuario.IUsuarioController;
import tpgr06.coronatickets.sys.usuario.UsuarioController;

public class Factory {

	private static Factory instance = null;

	private Factory() {
	}

	public static Factory getInstance() {
		if (instance == null) {
			instance = new Factory();
		}
		return instance;
	}

	public IUsuarioController getIUsuarioController() {
		return new UsuarioController();
	}

	public IEspectaculoController getIEspectaculoController() {
		return new EspectaculoController();
	}

	public IPaqueteController getIPaqueteController() {
		return new PaqueteController();
	}

}
