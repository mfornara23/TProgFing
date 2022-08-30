package tpgr06.coronatickets.sys.plataforma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlataformaDTO {

	private String nombre;
	private String descripcion;
	private String url;
	
	public PlataformaDTO() {}
	
	public PlataformaDTO(String nombre, String descripcion, String url) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.url = url;
	}

	public PlataformaDTO(Plataforma plataforma) {
		this.nombre = plataforma.getNombre();
		this.descripcion = plataforma.getDescripcion();
		this.url = plataforma.getUrl();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
