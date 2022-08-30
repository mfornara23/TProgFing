package tpgr06.coronatickets.sys.espectaculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tpgr06.coronatickets.sys.categoria.Categoria;
import tpgr06.coronatickets.sys.funcion.Funcion;
import tpgr06.coronatickets.sys.paquete.Paquete;
import tpgr06.coronatickets.sys.plataforma.Plataforma;
import tpgr06.coronatickets.sys.usuario.artista.Artista;

public class Espectaculo {

	private String nombre;
	private String descripcion;
	private Integer duracion;
	private Integer cantMin;
	private Integer cantMax;
	private String url;
	private Integer costo;
	private Date fechaReg;
	private Plataforma plataforma;
	private Artista artista;
	private Map<String, Categoria> categorias;
	private EstadoEspectaculo estado;
	private Map<String, Funcion> funciones;
	private Map<String, Paquete> paquetes;
	private String imagen;
	private List<Integer> valoraciones;
	private List<String> espectadoresFavoritos;
	private String descripcionPremios;
	private Integer cantidadPremios;
	private String video;

	public Espectaculo(String nombre, String descripcion, Integer duracion, Integer cantMin, Integer cantMax,
			String url, Integer costo, Date fechaReg, Plataforma plataforma, Artista artista, List<Categoria> categorias) {
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
		this.estado = EstadoEspectaculo.INGRESADO;
		this.funciones = new HashMap<>();
		this.paquetes = new HashMap<>();
		this.categorias = new HashMap<String, Categoria>();
		this.setValoraciones(new ArrayList<Integer>());
		this.espectadoresFavoritos = new ArrayList<String>();;

		categorias.forEach(cat -> this.categorias.put(cat.getNombre(), cat));

		this.imagen=null;
		this.descripcionPremios=null;
		this.cantidadPremios=0;
		this.video= null;
	}

	public Espectaculo() {
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

	public Plataforma getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public Map<String, Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(Map<String, Funcion> funciones) {
		this.funciones = funciones;
	}

	public Map<String, Paquete> getPaquetes() {
		return paquetes;
	}

	public void setPaquetes(Map<String, Paquete> paquetes) {
		this.paquetes = paquetes;
	}

	public void agregarFuncion(Funcion nuevaFuncion) {
		this.funciones.put(nuevaFuncion.getNombre(), nuevaFuncion);	
		nuevaFuncion.setNombreEspectaculo(this.getNombre());
	}

	public Funcion getFuncion(String nombreFuncion) {
		return this.funciones.get(nombreFuncion);
	}

	public Map<String, Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Map<String, Categoria> categorias) {
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

	public void agregarValoracion(Integer valoracion) {
		this.valoraciones.add(valoracion);	
	}
		
	public List<String> getEspectadoresFavoritos() {
		return espectadoresFavoritos;
	}

	public void setEspectadoresFavoritos(List<String> espectadoresFavoritos) {
		this.espectadoresFavoritos = espectadoresFavoritos;
	}

	public void agregarEspectadorFavorito(String nickName) {
		this.espectadoresFavoritos.add(nickName);	
	}

	public void removerEspectadorFavorito(String nickname) {
		this.espectadoresFavoritos.remove(nickname);	
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

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	public List<Integer> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<Integer> valoraciones) {
		this.valoraciones = valoraciones;
	}



}
