package tpgr06.coronatickets.sys.usuario;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioAuthDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String selector;
	private String validator;
	private UsuarioDTO usuario;

	public UsuarioAuthDTO() {
	}

	public UsuarioAuthDTO(UsuarioAuth usuarioAuth) {
		this.selector = usuarioAuth.getSelector();
		this.validator = usuarioAuth.getValidator();
		this.usuario = new UsuarioDTO(usuarioAuth.getUsuario());
	}

	public UsuarioAuthDTO(String selector, String validator, UsuarioDTO usuario) {
		this.selector = selector;
		this.validator = validator;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
}
