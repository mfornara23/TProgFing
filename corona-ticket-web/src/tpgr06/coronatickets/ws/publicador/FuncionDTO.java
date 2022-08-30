
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
 * <p>Clase Java para funcionDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="funcionDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaReg" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="invitados" type="{http://publicador.ws.coronatickets.tpgr06/}artistaDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="espectadores" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="imagen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sorteoRealizado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="premio" type="{http://publicador.ws.coronatickets.tpgr06/}premioDTO" minOccurs="0"/>
 *         &lt;element name="nombreEspectaculo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "funcionDTO", propOrder = {
    "nombre",
    "fecha",
    "fechaReg",
    "invitados",
    "espectadores",
    "imagen",
    "sorteoRealizado",
    "premio",
    "nombreEspectaculo"
})
public class FuncionDTO {

    protected String nombre;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaReg;
    @XmlElement(nillable = true)
    protected List<ArtistaDTO> invitados;
    @XmlElement(nillable = true)
    protected List<String> espectadores;
    protected String imagen;
    protected boolean sorteoRealizado;
    protected PremioDTO premio;
    protected String nombreEspectaculo;

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
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
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
     * Gets the value of the invitados property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invitados property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvitados().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArtistaDTO }
     * 
     * 
     */
    public List<ArtistaDTO> getInvitados() {
        if (invitados == null) {
            invitados = new ArrayList<ArtistaDTO>();
        }
        return this.invitados;
    }

    /**
     * Gets the value of the espectadores property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the espectadores property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEspectadores().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEspectadores() {
        if (espectadores == null) {
            espectadores = new ArrayList<String>();
        }
        return this.espectadores;
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
     * Obtiene el valor de la propiedad sorteoRealizado.
     * 
     */
    public boolean isSorteoRealizado() {
        return sorteoRealizado;
    }

    /**
     * Define el valor de la propiedad sorteoRealizado.
     * 
     */
    public void setSorteoRealizado(boolean value) {
        this.sorteoRealizado = value;
    }

    /**
     * Obtiene el valor de la propiedad premio.
     * 
     * @return
     *     possible object is
     *     {@link PremioDTO }
     *     
     */
    public PremioDTO getPremio() {
        return premio;
    }

    /**
     * Define el valor de la propiedad premio.
     * 
     * @param value
     *     allowed object is
     *     {@link PremioDTO }
     *     
     */
    public void setPremio(PremioDTO value) {
        this.premio = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreEspectaculo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEspectaculo() {
        return nombreEspectaculo;
    }

    /**
     * Define el valor de la propiedad nombreEspectaculo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEspectaculo(String value) {
        this.nombreEspectaculo = value;
    }

}
