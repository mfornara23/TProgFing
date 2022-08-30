package tpgr06.coronatickets.sys.usuario.artista;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.usuario.Usuario;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class ArtistaDTO extends UsuarioDTO {

	private String descripcion;
	private String bio;
	private String sitioWeb;

	public ArtistaDTO() {}
	
	public ArtistaDTO(Usuario usuario) {
		super(usuario);
	}

	public ArtistaDTO(String nickname, String nombre, String apellido, String email, Date fechaNac, String password, 
			List<String> seguidos, List<String> seguidores, String descripcion, String bio, String sitioWeb) {
		super(nickname, nombre, apellido, email, fechaNac, password, seguidos, seguidores, null);
		this.descripcion = descripcion;
		this.bio = bio;
		this.sitioWeb = sitioWeb;
	}

	public ArtistaDTO(Artista artista) {
		super(artista.getNickname(), artista.getNombre(), artista.getApellido(), artista.getEmail(), artista.getFechaNac(), artista.getPassword(), artista.getSeguidos()
				.values()
				.stream()
				.map(Usuario::getNickname)
				.collect(Collectors.toList()),
				artista.getSeguidores()
				.values()
				.stream()
				.map(Usuario::getNickname)
				.collect(Collectors.toList()), artista.getImagen());
		this.descripcion = artista.getDescripcion();
		this.bio = artista.getBio();
		this.sitioWeb = artista.getSitioWeb();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}
}
