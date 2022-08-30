package tpgr06.coronatickets.sys.usuario.espectador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.funcion.premio.PremioDTO;
import tpgr06.coronatickets.sys.paquete.PaqueteDTO;
import tpgr06.coronatickets.sys.usuario.Usuario;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.valoracion.ValoracionDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class EspectadorDTO extends UsuarioDTO {

	private List<RegistroFuncionDTO> registros;
	private List<PaqueteDTO> paquetes;
	private List<PremioDTO> premios;
	private List<String> espectaculosFavoritos;
	private List<ValoracionDTO> valoraciones;


	public EspectadorDTO() {}
	
	public EspectadorDTO(Espectador espectador) {

		super(espectador.getNickname(), espectador.getNombre(), espectador.getApellido(), espectador.getEmail(), espectador.getFechaNac(), espectador.getPassword(), espectador.getSeguidos()
				.values()
				.stream()
				.map(Usuario::getNickname)
				.collect(Collectors.toList()), espectador.getSeguidores()
				.values()
				.stream()
				.map(Usuario::getNickname)
				.collect(Collectors.toList()), espectador.getImagen());
		this.registros =  new LinkedList<RegistroFuncionDTO>();
		if (!espectador.getRegistros().isEmpty()) {
			this.registros = espectador.getRegistros().stream().map(RegistroFuncionDTO::new).collect(Collectors.toList());
		}
		this.paquetes =  new LinkedList<PaqueteDTO>();
		if (!espectador.getPaquetes().isEmpty()) {
			this.paquetes = espectador.getPaquetes().stream().map(compraPaquete -> new PaqueteDTO(compraPaquete.getPaquete())).collect(Collectors.toList());
		}
		if(!espectador.getPremios().isEmpty()) {
			this.premios = espectador.getPremios().stream().map(premio-> new PremioDTO(premio)).collect(Collectors.toList());
		}
		if(!espectador.getEspectaculosFavoritos().isEmpty()) {
			this.espectaculosFavoritos = espectador.getEspectaculosFavoritos().stream().collect(Collectors.toList());
		}
		if(!espectador.getValoraciones().isEmpty()) {
			this.valoraciones = espectador.getValoraciones().stream().collect(Collectors.toList());
		}
	}

	public EspectadorDTO(String nickname, String nombre, String apellido, String email, Date fechaNac, String password,
			List<String> seguidos, List<String> seguidores) {
		super(nickname, nombre, apellido, email, fechaNac,  password, seguidos, seguidores, null);
		this.registros =  new LinkedList<RegistroFuncionDTO>();
	}

	public List<RegistroFuncionDTO> getRegistros() {
		return registros;
	}

	public void setRegistros(List<RegistroFuncionDTO> registros) {
		this.registros = registros;
	}

	public FuncionDTO getFuncionByName(String name) throws NotFoundException {
		Optional<RegistroFuncionDTO> registro =  this.registros.stream().filter(reg -> reg.getFuncion().getNombre().equals(name)).findFirst();
		if (registro.isPresent()) {
			return registro.get().getFuncion();
		}else {
			throw new NotFoundException(String.format("No se encontro la Funcion %s", name));
		}
	}

	public List<PaqueteDTO> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(List<PaqueteDTO> paquetes) {
		this.paquetes = paquetes;
	}

	public List<PremioDTO> getPremios() {
		return premios;
	}

	public void setPremios(List<PremioDTO> premios) {
		this.premios = premios;
	}

	public List<String> getEspectaculosFavoritos() {
		return espectaculosFavoritos;
	}

	public void setEspectaculosFavoritos(List<String> espectaculosFavoritos) {
		this.espectaculosFavoritos = espectaculosFavoritos;
	}

	public List<ValoracionDTO> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<ValoracionDTO> valoraciones) {
		this.valoraciones = valoraciones;
	}
}
