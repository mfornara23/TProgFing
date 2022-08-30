package tpgr06.coronatickets.ws.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class StringListWrapper {
	
	private List<String> datos;

	public List<String> getDatos() {
		return datos;
	}

	public void setDatos(List<String> datos) {
		this.datos = datos;
	}

}
