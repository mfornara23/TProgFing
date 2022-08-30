package tpgr06.coronatickets.ws.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataPlataforma {

	private List<PlataformaDTO> plataforma;

	public DataPlataforma() { }

	public List<PlataformaDTO> getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(List<PlataformaDTO> plataforma) {
		this.plataforma = plataforma;
	}
	
}
