package tpgr06.coronatickets.ws.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.usuario.UsuarioDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataUsuario {
	
	private List<UsuarioDTO> usuario;

	public DataUsuario() { }

	public List<UsuarioDTO> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<UsuarioDTO> usuario) {
		this.usuario = usuario;
	}

}
