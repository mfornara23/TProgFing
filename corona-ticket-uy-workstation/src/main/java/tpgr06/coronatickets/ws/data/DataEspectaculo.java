package tpgr06.coronatickets.ws.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataEspectaculo {
	
	private List<EspectaculoDTO> espectaculo;

	public DataEspectaculo() {}

	public List<EspectaculoDTO> getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(List<EspectaculoDTO> espectaculo) {
		this.espectaculo = espectaculo;
	}
	
	

}
