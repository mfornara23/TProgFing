package tpgr06.coronatickets.sys.usuario;

import java.io.Serializable;

public class UsuarioAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String selector;
	private String validator;
	private Usuario usuario;

	public UsuarioAuth() {
	}

	public UsuarioAuth(String selector, String validator, Usuario usuario) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
