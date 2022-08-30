package tpgr06.coronatickets.sys.usuario.espectador;

import java.util.Date;

import tpgr06.coronatickets.sys.paquete.Paquete;

public class CompraPaquete {

	private Date fechaCompra;
	private Paquete paquete;


	public CompraPaquete(Paquete paq, Date fechaCompra) {
		this.paquete = paq;
		this.fechaCompra = fechaCompra;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

}
