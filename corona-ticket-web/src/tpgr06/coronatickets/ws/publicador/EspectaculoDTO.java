
package tpgr06.coronatickets.ws.publicador;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para espectaculoDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="espectaculoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duracion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cantMin" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cantMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="costo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fechaReg" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="plataforma" type="{http://publicador.ws.coronatickets.tpgr06/}plataformaDTO" minOccurs="0"/>
 *         &lt;element name="artista" type="{http://publicador.ws.coronatickets.tpgr06/}artistaDTO" minOccurs="0"/>
 *         &lt;element name="categorias" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://publicador.ws.coronatickets.tpgr06/}estadoEspectaculo" minOccurs="0"/>
 *         &lt;element name="funciones" type="{http://publicador.ws.coronatickets.tpgr06/}funcionDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="paquetes" type="{http://publicador.ws.coronatickets.tpgr06/}paqueteDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="imagen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionPremios" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cantidadPremios" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="video" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="espectadoresFavoritos" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="valoraciones" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "espectaculoDTO", propOrder = {
    "nombre",
    "descripcion",
    "duracion",
    "cantMin",
    "cantMax",
    "url",
    "costo",
    "fechaReg",
    "plataforma",
    "artista",
    "categorias",
    "estado",
    "funciones",
    "paquetes",
    "imagen",
    "descripcionPremios",
    "cantidadPremios",
    "video",
    "espectadoresFavoritos",
    "valoraciones"
})
public class EspectaculoDTO {

    protected String nombre;
    protected String descripcion;
    protected Integer duracion;
    protected Integer cantMin;
    protected Integer cantMax;
    protected String url;
    protected Integer costo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaReg;
    protected PlataformaDTO plataforma;
    protected ArtistaDTO artista;
    @XmlElement(nillable = true)
    protected List<String> categorias;
    @XmlSchemaType(name = "string")
    protected EstadoEspectaculo estado;
    @XmlElement(nillable = true)
    protected List<FuncionDTO> funciones;
    @XmlElement(nillable = true)
    protected List<PaqueteDTO> paquetes;
    protected String imagen;
    protected String descripcionPremios;
    protected Integer cantidadPremios;
    protected String video;
    @XmlElement(nillable = true)
    protected List<String> espectadoresFavoritos;
    @XmlElement(nillable = true)
    protected List<Integer> valoraciones;

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad duracion.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDuracion() {
        return duracion;
    }

    /**
     * Define el valor de la propiedad duracion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDuracion(Integer value) {
        this.duracion = value;
    }

    /**
     * Obtiene el valor de la propiedad cantMin.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantMin() {
        return cantMin;
    }

    /**
     * Define el valor de la propiedad cantMin.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantMin(Integer value) {
        this.cantMin = value;
    }

    /**
     * Obtiene el valor de la propiedad cantMax.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantMax() {
        return cantMax;
    }

    /**
     * Define el valor de la propiedad cantMax.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantMax(Integer value) {
        this.cantMax = value;
    }

    /**
     * Obtiene el valor de la propiedad url.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Define el valor de la propiedad url.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Obtiene el valor de la propiedad costo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCosto() {
        return costo;
    }

    /**
     * Define el valor de la propiedad costo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCosto(Integer value) {
        this.costo = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaReg.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaReg() {
        return fechaReg;
    }

    /**
     * Define el valor de la propiedad fechaReg.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaReg(XMLGregorianCalendar value) {
        this.fechaReg = value;
    }

    /**
     * Obtiene el valor de la propiedad plataforma.
     * 
     * @return
     *     possible object is
     *     {@link PlataformaDTO }
     *     
     */
    public PlataformaDTO getPlataforma() {
        return plataforma;
    }

    /**
     * Define el valor de la propiedad plataforma.
     * 
     * @param value
     *     allowed object is
     *     {@link PlataformaDTO }
     *     
     */
    public void setPlataforma(PlataformaDTO value) {
        this.plataforma = value;
    }

    /**
     * Obtiene el valor de la propiedad artista.
     * 
     * @return
     *     possible object is
     *     {@link ArtistaDTO }
     *     
     */
    public ArtistaDTO getArtista() {
        return artista;
    }

    /**
     * Define el valor de la propiedad artista.
     * 
     * @param value
     *     allowed object is
     *     {@link ArtistaDTO }
     *     
     */
    public void setArtista(ArtistaDTO value) {
        this.artista = value;
    }

    /**
     * Gets the value of the categorias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categorias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategorias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCategorias() {
        if (categorias == null) {
            categorias = new ArrayList<String>();
        }
        return this.categorias;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoEspectaculo }
     *     
     */
    public EstadoEspectaculo getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoEspectaculo }
     *     
     */
    public void setEstado(EstadoEspectaculo value) {
        this.estado = value;
    }

    /**
     * Gets the value of the funciones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the funciones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunciones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FuncionDTO }
     * 
     * 
     */
    public List<FuncionDTO> getFunciones() {
        if (funciones == null) {
            funciones = new ArrayList<FuncionDTO>();
        }
        return this.funciones;
    }

    /**
     * Gets the value of the paquetes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paquetes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaquetes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaqueteDTO }
     * 
     * 
     */
    public List<PaqueteDTO> getPaquetes() {
        if (paquetes == null) {
            paquetes = new ArrayList<PaqueteDTO>();
        }
        return this.paquetes;
    }

    /**
     * Obtiene el valor de la propiedad imagen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Define el valor de la propiedad imagen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagen(String value) {
        this.imagen = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionPremios.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionPremios() {
        return descripcionPremios;
    }

    /**
     * Define el valor de la propiedad descripcionPremios.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionPremios(String value) {
        this.descripcionPremios = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidadPremios.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantidadPremios() {
        return cantidadPremios;
    }

    /**
     * Define el valor de la propiedad cantidadPremios.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantidadPremios(Integer value) {
        this.cantidadPremios = value;
    }

    /**
     * Obtiene el valor de la propiedad video.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideo() {
        return video;
    }

    /**
     * Define el valor de la propiedad video.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideo(String value) {
        this.video = value;
    }

    /**
     * Gets the value of the espectadoresFavoritos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the espectadoresFavoritos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEspectadoresFavoritos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEspectadoresFavoritos() {
        if (espectadoresFavoritos == null) {
            espectadoresFavoritos = new ArrayList<String>();
        }
        return this.espectadoresFavoritos;
    }

    /**
     * Gets the value of the valoraciones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valoraciones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValoraciones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getValoraciones() {
        if (valoraciones == null) {
            valoraciones = new ArrayList<Integer>();
        }
        return this.valoraciones;
    }

}
