
package tpgr06.coronatickets.ws.publicador;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para compraPaqueteDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="compraPaqueteDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaCompra" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="paquete" type="{http://publicador.ws.coronatickets.tpgr06/}paqueteDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compraPaqueteDTO", propOrder = {
    "fechaCompra",
    "paquete"
})
public class CompraPaqueteDTO {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaCompra;
    protected PaqueteDTO paquete;

    /**
     * Obtiene el valor de la propiedad fechaCompra.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaCompra() {
        return fechaCompra;
    }

    /**
     * Define el valor de la propiedad fechaCompra.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaCompra(XMLGregorianCalendar value) {
        this.fechaCompra = value;
    }

    /**
     * Obtiene el valor de la propiedad paquete.
     * 
     * @return
     *     possible object is
     *     {@link PaqueteDTO }
     *     
     */
    public PaqueteDTO getPaquete() {
        return paquete;
    }

    /**
     * Define el valor de la propiedad paquete.
     * 
     * @param value
     *     allowed object is
     *     {@link PaqueteDTO }
     *     
     */
    public void setPaquete(PaqueteDTO value) {
        this.paquete = value;
    }

}
