
package tpgr06.coronatickets.ws.publicador;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para usuarioAuthDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="usuarioAuthDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="selector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://publicador.ws.coronatickets.tpgr06/}usuarioDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usuarioAuthDTO", propOrder = {
    "id",
    "selector",
    "validator",
    "usuario"
})
public class UsuarioAuthDTO {

    protected Long id;
    protected String selector;
    protected String validator;
    protected UsuarioDTO usuario;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad selector.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelector() {
        return selector;
    }

    /**
     * Define el valor de la propiedad selector.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelector(String value) {
        this.selector = value;
    }

    /**
     * Obtiene el valor de la propiedad validator.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidator() {
        return validator;
    }

    /**
     * Define el valor de la propiedad validator.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidator(String value) {
        this.validator = value;
    }

    /**
     * Obtiene el valor de la propiedad usuario.
     * 
     * @return
     *     possible object is
     *     {@link UsuarioDTO }
     *     
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * Define el valor de la propiedad usuario.
     * 
     * @param value
     *     allowed object is
     *     {@link UsuarioDTO }
     *     
     */
    public void setUsuario(UsuarioDTO value) {
        this.usuario = value;
    }

}
