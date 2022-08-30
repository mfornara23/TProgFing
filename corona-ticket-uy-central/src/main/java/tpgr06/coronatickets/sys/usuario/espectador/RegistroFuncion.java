package tpgr06.coronatickets.sys.usuario.espectador;

import java.util.Date;
import java.util.List;

import tpgr06.coronatickets.sys.funcion.Funcion;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;

public class RegistroFuncion {

	private String identificador;
	private Date fechaReg;
	private Integer costo;
	private Funcion funcion;
	private List<String> registrosCanje;
	private Boolean canjeado;
	private CompraPaquete compraPaquete;

	public RegistroFuncion() {
	}

	public RegistroFuncion(String identificador, Date fechaReg, Integer costo, Funcion funcion, List<String> registrosCanje,
			Boolean canjeado, CompraPaquete comprapaquete) {
		super();
		this.identificador = identificador;
		this.fechaReg = fechaReg;
		this.costo = costo;
		this.funcion = funcion;
		this.registrosCanje = registrosCanje;
		this.canjeado = canjeado;
		this.setCompraPaquete(comprapaquete);
	}

	public Date getFechaReg() {
		return fechaReg;
	}

	public void setFechaReg(Date fechaReg) {
		this.fechaReg = fechaReg;
	}

	public Integer getCosto() {
		return costo;
	}

	public void setCosto(Integer costo) {
		this.costo = costo;
	}

	public FuncionDTO getFuncion() {
		return this.funcion.getFuncionDTO();
	}

	public Boolean getCanjeado() {
		return canjeado;
	}

	public void setCanjeado(Boolean canjeado) {
		this.canjeado = canjeado;
	}

	public String getId() {
		return identificador;
	}

	public void setId(String identificador) {
		this.identificador = identificador;
	}

	public List<String> getRegistrosCanje() {
		return registrosCanje;
	}

	public void setRegistrosCanje(List<String> registrosCanje) {
		this.registrosCanje = registrosCanje;
	}

	public CompraPaquete getCompraPaquete() {
		return compraPaquete;
	}

	public void setCompraPaquete(CompraPaquete compraPaquete) {
		this.compraPaquete = compraPaquete;
	}


}
