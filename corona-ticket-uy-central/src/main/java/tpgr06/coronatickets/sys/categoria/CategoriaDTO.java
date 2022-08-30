package tpgr06.coronatickets.sys.categoria;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriaDTO {

	private String nombre;
	private Map<String, EspectaculoDTO> espectaculos;
	
	public CategoriaDTO() {}

	public CategoriaDTO(String nombre, Map<String, EspectaculoDTO> espectaculos) {
		this.nombre = nombre;
		this.espectaculos = espectaculos;
	}

	public CategoriaDTO(Categoria category) {
		this.nombre = category.getNombre();

		if (category.getEspectaculos() != null) {
			this.espectaculos = category.getEspectaculos().values().stream().map(EspectaculoDTO::new)
					.collect(Collectors.toMap(EspectaculoDTO::getNombre, espectaculoDTO -> espectaculoDTO));
		} else {
			this.espectaculos = new HashMap<String, EspectaculoDTO>();
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<String, EspectaculoDTO> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(Map<String, EspectaculoDTO> espectaculos) {
		this.espectaculos = espectaculos;
	}

}
