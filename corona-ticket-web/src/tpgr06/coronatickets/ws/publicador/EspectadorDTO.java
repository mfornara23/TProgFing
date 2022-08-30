
package tpgr06.coronatickets.ws.publicador;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para espectadorDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="espectadorDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://publicador.ws.coronatickets.tpgr06/}usuarioDTO">
 *       &lt;sequence>
 *         &lt;element name="registros" type="{http://publicador.ws.coronatickets.tpgr06/}registroFuncionDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="paquetes" type="{http://publicador.ws.coronatickets.tpgr06/}paqueteDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="premios" type="{http://publicador.ws.coronatickets.tpgr06/}premioDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="espectaculosFavoritos" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="valoraciones" type="{http://publicador.ws.coronatickets.tpgr06/}valoracionDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "espectadorDTO", propOrder = {
    "registros",
    "paquetes",
    "premios",
    "espectaculosFavoritos",
    "valoraciones"
})
public class EspectadorDTO
    extends UsuarioDTO
{

    @XmlElement(nillable = true)
    protected List<RegistroFuncionDTO> registros;
    @XmlElement(nillable = true)
    protected List<PaqueteDTO> paquetes;
    @XmlElement(nillable = true)
    protected List<PremioDTO> premios;
    @XmlElement(nillable = true)
    protected List<String> espectaculosFavoritos;
    @XmlElement(nillable = true)
    protected List<ValoracionDTO> valoraciones;

    /**
     * Gets the value of the registros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegistroFuncionDTO }
     * 
     * 
     */
    public List<RegistroFuncionDTO> getRegistros() {
        if (registros == null) {
            registros = new ArrayList<RegistroFuncionDTO>();
        }
        return this.registros;
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
     * Gets the value of the premios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the premios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPremios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PremioDTO }
     * 
     * 
     */
    public List<PremioDTO> getPremios() {
        if (premios == null) {
            premios = new ArrayList<PremioDTO>();
        }
        return this.premios;
    }

    /**
     * Gets the value of the espectaculosFavoritos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the espectaculosFavoritos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEspectaculosFavoritos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEspectaculosFavoritos() {
        if (espectaculosFavoritos == null) {
            espectaculosFavoritos = new ArrayList<String>();
        }
        return this.espectaculosFavoritos;
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
     * {@link ValoracionDTO }
     * 
     * 
     */
    public List<ValoracionDTO> getValoraciones() {
        if (valoraciones == null) {
            valoraciones = new ArrayList<ValoracionDTO>();
        }
        return this.valoraciones;
    }

}
