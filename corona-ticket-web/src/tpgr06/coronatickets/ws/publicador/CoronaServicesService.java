
package tpgr06.coronatickets.ws.publicador;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import com.tpgr06.coronatickets.utils.ConfigUtils;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CoronaServicesService", targetNamespace = "http://publicador.ws.coronatickets.tpgr06/", wsdlLocation = "http://localhost:8083/coronaservices?wsdl")
public class CoronaServicesService
    extends Service
{

	private final static String ENDPOINT = "http://" + ConfigUtils.getInstance().getProperty("service.ip") + ":"
			+ ConfigUtils.getInstance().getProperty("service.port") + "/"
			+ ConfigUtils.getInstance().getProperty("service.path");
    private final static URL CORONASERVICESSERVICE_WSDL_LOCATION;
    private final static WebServiceException CORONASERVICESSERVICE_EXCEPTION;
    private final static QName CORONASERVICESSERVICE_QNAME = new QName("http://publicador.ws.coronatickets.tpgr06/", "CoronaServicesService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(ENDPOINT);
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CORONASERVICESSERVICE_WSDL_LOCATION = url;
        CORONASERVICESSERVICE_EXCEPTION = e;
    }

    public CoronaServicesService() {
        super(__getWsdlLocation(), CORONASERVICESSERVICE_QNAME);
    }

    public CoronaServicesService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CORONASERVICESSERVICE_QNAME, features);
    }

    public CoronaServicesService(URL wsdlLocation) {
        super(wsdlLocation, CORONASERVICESSERVICE_QNAME);
    }

    public CoronaServicesService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CORONASERVICESSERVICE_QNAME, features);
    }

    public CoronaServicesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CoronaServicesService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CoronaServices
     */
    @WebEndpoint(name = "CoronaServicesPort")
    public CoronaServices getCoronaServicesPort() {
        return super.getPort(new QName("http://publicador.ws.coronatickets.tpgr06/", "CoronaServicesPort"), CoronaServices.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CoronaServices
     */
    @WebEndpoint(name = "CoronaServicesPort")
    public CoronaServices getCoronaServicesPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://publicador.ws.coronatickets.tpgr06/", "CoronaServicesPort"), CoronaServices.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CORONASERVICESSERVICE_EXCEPTION!= null) {
            throw CORONASERVICESSERVICE_EXCEPTION;
        }
        return CORONASERVICESSERVICE_WSDL_LOCATION;
    }

}
