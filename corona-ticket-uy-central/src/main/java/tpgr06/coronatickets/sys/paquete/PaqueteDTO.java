package tpgr06.coronatickets.sys.paquete;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class PaqueteDTO {

	private String nombre;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer descuento;
	private List<EspectaculoDTO> espectaculos;
	private String imagen;
	
	public PaqueteDTO() {}

	public PaqueteDTO(String nombre, String descripcion, Date fechaInicio, Date fechaFin, Integer descuento) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.descuento = descuento;
		this.setImagen(null);
		
	}

	public PaqueteDTO(Paquete paquete) {
		this.nombre = paquete.getNombre();
		this.descripcion = paquete.getDescripcion();
		this.fechaInicio = paquete.getFechaInicio();
		this.fechaFin = paquete.getFechaFin();
		this.descuento = paquete.getDescuento();

		if (paquete.getEspectaculos() != null) {
			this.espectaculos = paquete.getEspectaculos()
					.values()
					.stream()
					.map(EspectaculoDTO::new)
					.collect(Collectors.toList());
		}
		this.setImagen(paquete.getImagen());
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

	public List<EspectaculoDTO> getEspectaculos() {
		return espectaculos;
	}
	
	public void setEspectaculos(List<EspectaculoDTO> espectaculos) {
		this.espectaculos = espectaculos;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
