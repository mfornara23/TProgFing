package tpgr06.coronatickets.ws.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.categoria.CategoriaDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataCategoria {
	
	private List<CategoriaDTO> categoria;

	public DataCategoria() {}

	public List<CategoriaDTO> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<CategoriaDTO> categorias) {
		this.categoria = categorias;
	}
	
	

}
