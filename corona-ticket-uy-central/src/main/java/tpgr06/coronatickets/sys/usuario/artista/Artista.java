package tpgr06.coronatickets.sys.usuario.artista;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;

import tpgr06.coronatickets.sys.espectaculo.Espectaculo;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.usuario.Usuario;

public class Artista extends Usuario {

	private String descripcion;
	private String bio;
	private String sitioWeb;
	private List<Espectaculo> espectaculos;

	public Artista(String nickname, String nombre, String apellido, String email, String password, Date fechaNac, String descripcion, String bio, String sitioWeb, Map<String, Usuario> seguidos,
			Map<String, Usuario> seguidores) {
		super(nickname, nombre, apellido, email, fechaNac, password, seguidos, seguidores);
		this.descripcion = descripcion;
		this.bio = bio;
		this.sitioWeb = sitioWeb;
		this.espectaculos = new ArrayList<Espectaculo>();
	}

	public Artista(String nickname, String nombre, String apellido, String email, String password, Date fechaNac, String descripcion, String bio, String sitioWeb, Map<String, Usuario> seguidos,
			Map<String, Usuario> seguidores, String imagen) {
		super(nickname, nombre, apellido, email, fechaNac, password, seguidos, seguidores, imagen);
		this.descripcion = descripcion;
		this.bio = bio;
		this.sitioWeb = sitioWeb;
		this.espectaculos = new ArrayList<Espectaculo>();
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

	public List<EspectaculoDTO> getEspectaculos() {
		List<EspectaculoDTO> res = new ArrayList<EspectaculoDTO>();
		for (Espectaculo e : this.espectaculos) {
			res.add(new EspectaculoDTO(e));
		}
		return res;
	}

	public void agregarEspectaculo(Espectaculo nuevo) {
		this.espectaculos.add(nuevo);
	}


}
