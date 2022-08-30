package tpgr06.coronatickets.sys.espectaculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tpgr06.coronatickets.sys.categoria.Categoria;
import tpgr06.coronatickets.sys.categoria.CategoriaDTO;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.paquete.PaqueteDTO;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class EspectaculoDTO {

	private String nombre;
	private String descripcion;
	private Integer duracion;
	private Integer cantMin;
	private Integer cantMax;
	private String url;
	private Integer costo;
	private Date fechaReg;
	private PlataformaDTO plataforma;
	private ArtistaDTO artista;
	private List<String> categorias;
	private EstadoEspectaculo estado;
	private List<FuncionDTO> funciones;
	private List<PaqueteDTO> paquetes;
	private String imagen;
	private String descripcionPremios;
	private Integer cantidadPremios;
	private String video;
	private List<String> espectadoresFavoritos;
	private List<Integer> valoraciones;

	
	public EspectaculoDTO() {}

	public EspectaculoDTO(String nombre, String descripcion, Integer duracion, Integer cantMin, Integer cantMax,
			String url, Integer costo, Date fechaReg, PlataformaDTO plataforma, ArtistaDTO artista, List<CategoriaDTO> categorias, EstadoEspectaculo estado) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.cantMin = cantMin;
		this.cantMax = cantMax;
		this.url = url;
		this.costo = costo;
		this.fechaReg = fechaReg;
		this.plataforma = plataforma;
		this.artista = artista;
		this.estado = estado;
		this.espectadoresFavoritos = new ArrayList<String>();

		if (categorias.isEmpty()) {
			this.categorias = new LinkedList<String>();
		} else {
			LinkedList<String> cats = new LinkedList<String>();
			for (CategoriaDTO c : categorias) {
				cats.add(c.getNombre());
			}
			this.categorias = cats;
		}
		this.imagen=null;
		this.descripcionPremios = null;
		this.cantidadPremios = 0;
		this.video = null;
		
	}

	public EspectaculoDTO(Espectaculo espectaculo) {
		this.nombre = espectaculo.getNombre();
		this.descripcion = espectaculo.getDescripcion();
		this.duracion = espectaculo.getDuracion();
		this.cantMin = espectaculo.getCantMin();
		this.cantMax = espectaculo.getCantMax();
		this.url = espectaculo.getUrl();
		this.costo = espectaculo.getCosto();
		this.fechaReg = espectaculo.getFechaReg();
		this.estado = espectaculo.getEstado();

		if (espectaculo.getPlataforma() != null ) {
			this.plataforma = new PlataformaDTO(espectaculo.getPlataforma());
		}

		if (espectaculo.getArtista() != null) {
			this.artista = new ArtistaDTO(espectaculo.getArtista());
		}

		if (espectaculo.getCategorias() != null) {
			LinkedList<String> cats = new LinkedList<String>();
			for (Categoria c : espectaculo.getCategorias().values()) {
				cats.add(c.getNombre());
			}
			this.categorias = cats;
		} else {
			this.categorias =  new LinkedList<String>();
		}

		if (espectaculo.getFunciones() != null) {
			this.funciones = espectaculo.getFunciones()
					.values()
					.stream()
					.map(FuncionDTO::new)
					.collect(Collectors.toList());
		} else {
			this.funciones = new LinkedList<FuncionDTO>();
		}
		
		if (espectaculo.getPaquetes() != null) {
			this.paquetes = espectaculo.getPaquetes()
					.values()
					.stream()
					.map(PaqueteDTO::new)
					.collect(Collectors.toList());
		} else {
			this.paquetes = new LinkedList<PaqueteDTO>();
		}
		this.setImagen(espectaculo.getImagen());
		this.setDescripcionPremios(espectaculo.getDescripcionPremios());
		this.setCantidadPremios(espectaculo.getCantidadPremios());
		this.setVideo(espectaculo.getVideo());
		this.espectadoresFavoritos = espectaculo.getEspectadoresFavoritos();
		this.setValoraciones(espectaculo.getValoraciones());
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Integer getCantMin() {
		return cantMin;
	}

	public void setCantMin(Integer cantMin) {
		this.cantMin = cantMin;
	}

	public Integer getCantMax() {
		return cantMax;
	}

	public void setCantMax(Integer cantMax) {
		this.cantMax = cantMax;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCosto() {
		return costo;
	}

	public void setCosto(Integer costo) {
		this.costo = costo;
	}

	public Date getFechaReg() {
		return fechaReg;
	}

	public void setFechaReg(Date fechaReg) {
		this.fechaReg = fechaReg;
	}

	public PlataformaDTO getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(PlataformaDTO plataforma) {
		this.plataforma = plataforma;
	}
	
	public ArtistaDTO getArtista() {
		return artista;
	}

	public void setArtista(ArtistaDTO artista) {
		this.artista = artista;
	}
	
	public List<FuncionDTO> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<FuncionDTO> funciones) {
		this.funciones = funciones;
	}
	
	public List<PaqueteDTO> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(List<PaqueteDTO> paquetes) {
		this.paquetes = paquetes;
	}
	
	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public EstadoEspectaculo getEstado() {
		return estado;
	}

	public void setEstado(EstadoEspectaculo estado) {
		this.estado = estado;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDescripcionPremios() {
		return descripcionPremios;
	}

	public void setDescripcionPremios(String descripcionPremios) {
		this.descripcionPremios = descripcionPremios;
	}

	public Integer getCantidadPremios() {
		return cantidadPremios;
	}

	public void setCantidadPremios(Integer cantidadPremios) {
		this.cantidadPremios = cantidadPremios;
	}

	public List<String> getEspectadoresFavoritos() {
		return espectadoresFavoritos;
	}

	public void setEspectadoresFavoritos(List<String> espectadoresFavoritos) {
		this.espectadoresFavoritos = espectadoresFavoritos;
	}

	public List<Integer> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<Integer> valoraciones) {
		this.valoraciones = valoraciones;
	}
}
