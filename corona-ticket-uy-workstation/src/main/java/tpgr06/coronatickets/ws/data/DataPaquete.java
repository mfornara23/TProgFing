package tpgr06.coronatickets.ws.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.paquete.PaqueteDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataPaquete {

	private List<PaqueteDTO> paquete;

	public DataPaquete() {}

	public List<PaqueteDTO> getPaquete() {
		return paquete;
	}

	public void setPaquete(List<PaqueteDTO> paquete) {
		this.paquete = paquete;
	}
	
}
