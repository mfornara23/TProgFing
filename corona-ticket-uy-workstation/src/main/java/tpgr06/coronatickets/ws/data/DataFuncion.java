package tpgr06.coronatickets.ws.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.funcion.FuncionDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataFuncion {
	
	private List<FuncionDTO> funcion;

	public DataFuncion() {}

	public List<FuncionDTO> getFuncion() {
		return funcion;
	}

	public void setFuncion(List<FuncionDTO> funcion) {
		this.funcion = funcion;
	}
	

}
