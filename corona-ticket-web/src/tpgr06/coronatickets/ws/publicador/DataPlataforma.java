
package tpgr06.coronatickets.ws.publicador;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dataPlataforma complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dataPlataforma">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="plataforma" type="{http://publicador.ws.coronatickets.tpgr06/}plataformaDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataPlataforma", propOrder = {
    "plataforma"
})
public class DataPlataforma {

    @XmlElement(nillable = true)
    protected List<PlataformaDTO> plataforma;

    /**
     * Gets the value of the plataforma property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the plataforma property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlataforma().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlataformaDTO }
     * 
     * 
     */
    public List<PlataformaDTO> getPlataforma() {
        if (plataforma == null) {
            plataforma = new ArrayList<PlataformaDTO>();
        }
        return this.plataforma;
    }

}
