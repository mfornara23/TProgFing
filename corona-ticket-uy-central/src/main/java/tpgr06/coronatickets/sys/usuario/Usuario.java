package tpgr06.coronatickets.sys.usuario;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Usuario {

	private String nickname;
	private String nombre;
	private String apellido;
	private String email;
	private Date fechaNac;
	private String password;
	private Map<String, Usuario> seguidos;
	private Map<String, Usuario> seguidores;
	private String imagen;
	
	public Usuario(String nickname, String nombre, String apellido, String email, Date fechaNac, String password,
			Map<String, Usuario> seguidos, Map<String, Usuario> seguidores) {

		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fechaNac = fechaNac;
		this.setPassword(password);
		if (seguidos!=null) {
			this.seguidos = seguidos;
		} else {
			this.seguidos = new HashMap<>();
		}
		if (seguidores!=null) {
			this.seguidores = seguidores;
		} else {
			this.seguidores = new HashMap<>();
		}
		this.imagen = null;
	}

	public Usuario(String nickname, String nombre, String apellido, String email, Date fechaNac, String password,
			Map<String, Usuario> seguidos, Map<String, Usuario> seguidores, String imagen) {

		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fechaNac = fechaNac;
		this.setPassword(password);
		if (seguidos!=null) {
			this.seguidos = seguidos;
		} else {
			this.seguidos = new HashMap<>();
		}
		if (seguidores!=null) {
			this.seguidores = seguidores;
		} else {
			this.seguidores = new HashMap<>();
		}
		this.imagen = imagen;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Usuario> getSeguidos() {
		return seguidos;
	}

	public Map<String, Usuario> getSeguidores() {
		return seguidores;
	}

	public void setSeguidos(Map<String, Usuario> seguidos) {
		this.seguidos = seguidos;
	}

	public void setSeguidores(Map<String, Usuario> seguidores) {
		this.seguidores = seguidores;
	}

	public void addSeguido(Usuario seguido) {
		this.seguidos.put(seguido.nickname, seguido);
	}

	public void removeSeguido(String nicknameSeguido) {
		this.seguidos.remove(nicknameSeguido);
	}


	public void addSeguidor(Usuario seguidor) {
		this.seguidores.put(seguidor.nickname, seguidor);
	}

	public void removeSeguidor(String nicknameSeguidor) {
		this.seguidores.remove(nicknameSeguidor);
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
