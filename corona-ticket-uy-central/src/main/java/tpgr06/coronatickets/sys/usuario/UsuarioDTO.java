package tpgr06.coronatickets.sys.usuario;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;

@XmlSeeAlso({ArtistaDTO.class, EspectadorDTO.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioDTO {

	private String nickname;
	private String nombre;
	private String apellido;
	private String email;
	private Date fechaNac;
	private String password;	
	private List<String> seguidos;
	private List<String> seguidores;
	private String imagen;
	
	public UsuarioDTO() {}

	public UsuarioDTO(Usuario usuario) {
		this.nickname = usuario.getNickname();
		this.nombre =  usuario.getNombre();
		this.apellido = usuario.getApellido();
		this.email = usuario.getEmail();
		this.fechaNac = usuario.getFechaNac();
		this.password = usuario.getPassword();
		if (!usuario.getSeguidos().isEmpty()) {
			this.seguidos = usuario.getSeguidos()
					.values()
					.stream()
					.map(Usuario::getNickname)
					.collect(Collectors.toList());
		} else {
			this.seguidos = new LinkedList<String>();
		}

		if (!usuario.getSeguidores().isEmpty()) {
			this.seguidores = usuario.getSeguidores()
					.values()
					.stream()
					.map(Usuario::getNickname)
					.collect(Collectors.toList());
		} else {
			this.seguidores = new LinkedList<String>();
		}
		this.setImagen(usuario.getImagen());
	}
	
	public UsuarioDTO(String nickname, String nombre, String apellido, String email, Date fechaNac, String password, List<String> seguidos, List<String> seguidores, String imagen) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.fechaNac = fechaNac;
		this.password = password;
		if (seguidos==null) {
			this.seguidos = new LinkedList<String>();
		} else {
			this.seguidos = seguidos;
		}
		if (seguidores==null) {
			this.seguidores = new LinkedList<String>();
		} else {
			this.seguidores = seguidores;
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

	public List<String> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(List<String> seguidos) {
		this.seguidos = seguidos;
	}

	public void addSeguido(String nicknameSeguido) {
		this.seguidos.add(nicknameSeguido);
	}

	public void removeSeguido(String nicknameSeguido) {
		this.seguidos.remove(nicknameSeguido);
	}

	public List<String> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<String> seguidores) {
		this.seguidores = seguidores;
	}

	public void addSeguidor(String nicknameSeguidor) {
		this.seguidores.add(nicknameSeguidor);
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
