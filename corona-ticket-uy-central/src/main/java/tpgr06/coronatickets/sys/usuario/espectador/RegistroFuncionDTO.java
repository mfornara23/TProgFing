package tpgr06.coronatickets.sys.usuario.espectador;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.funcion.FuncionDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class RegistroFuncionDTO {

	private String identificador;
	private Date fechaReg;
	private Integer costo;
	private FuncionDTO funcion;
	private CompraPaqueteDTO compraPaquete;
	
	public RegistroFuncionDTO() {}
	
	public RegistroFuncionDTO(RegistroFuncion registro){
		this.identificador = registro.getId();
		this.fechaReg = registro.getFechaReg();
		this.costo = registro.getCosto();
		this.funcion = registro.getFuncion();
		if (registro.getCompraPaquete()!=null) {
			this.setCompraPaquete(new CompraPaqueteDTO(registro.getCompraPaquete()));
		}else {
			this.setCompraPaquete(null);
		}
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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
		return funcion;
	}

	public void setFuncion(FuncionDTO funcion ) {
		this.funcion = funcion;
	}

	public CompraPaqueteDTO getCompraPaquete() {
		return compraPaquete;
	}

	public void setCompraPaquete(CompraPaqueteDTO compraPaquete) {
		this.compraPaquete = compraPaquete;
	}


}
