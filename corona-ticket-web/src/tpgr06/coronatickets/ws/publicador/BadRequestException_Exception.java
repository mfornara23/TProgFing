
package tpgr06.coronatickets.ws.publicador;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "BadRequestException", targetNamespace = "http://publicador.ws.coronatickets.tpgr06/")
public class BadRequestException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private BadRequestException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public BadRequestException_Exception(String message, BadRequestException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public BadRequestException_Exception(String message, BadRequestException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: tpgr06.coronatickets.ws.publicador.BadRequestException
     */
    public BadRequestException getFaultInfo() {
        return faultInfo;
    }

}