
package tpgr06.coronatickets.ws.publicador;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dataEspectaculo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dataEspectaculo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="espectaculo" type="{http://publicador.ws.coronatickets.tpgr06/}espectaculoDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEspectaculo", propOrder = {
    "espectaculo"
})
public class DataEspectaculo {

    @XmlElement(nillable = true)
    protected List<EspectaculoDTO> espectaculo;

    /**
     * Gets the value of the espectaculo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the espectaculo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEspectaculo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EspectaculoDTO }
     * 
     * 
     */
    public List<EspectaculoDTO> getEspectaculo() {
        if (espectaculo == null) {
            espectaculo = new ArrayList<EspectaculoDTO>();
        }
        return this.espectaculo;
    }

}
