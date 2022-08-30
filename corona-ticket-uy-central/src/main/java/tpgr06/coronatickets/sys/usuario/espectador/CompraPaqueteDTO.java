package tpgr06.coronatickets.sys.usuario.espectador;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.paquete.PaqueteDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompraPaqueteDTO {

	private Date fechaCompra;
	private PaqueteDTO paquete;

	public CompraPaqueteDTO() {}
	
	public CompraPaqueteDTO(Date fechaCompra, PaqueteDTO paquete) {
		this.fechaCompra = (Date) fechaCompra.clone();
		this.paquete = paquete;
	}
	
	public CompraPaqueteDTO(CompraPaquete compra) {
			this.fechaCompra = (Date) compra.getFechaCompra().clone();
			this.paquete = new PaqueteDTO(compra.getPaquete());
		}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public PaqueteDTO getPaquete() {
		return paquete;
	}

	public void setPaquete(PaqueteDTO paquete) {
		this.paquete = paquete;
	}
}
