
package tpgr06.coronatickets.ws.publicador;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoEspectaculo.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="estadoEspectaculo">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INGRESADO"/>
 *     &lt;enumeration value="ACEPTADO"/>
 *     &lt;enumeration value="RECHAZADO"/>
 *     &lt;enumeration value="FINALIZADO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "estadoEspectaculo")
@XmlEnum
public enum EstadoEspectaculo {

    INGRESADO,
    ACEPTADO,
    RECHAZADO,
    FINALIZADO;

    public String value() {
        return name();
    }

    public static EstadoEspectaculo fromValue(String v) {
        return valueOf(v);
    }

}
