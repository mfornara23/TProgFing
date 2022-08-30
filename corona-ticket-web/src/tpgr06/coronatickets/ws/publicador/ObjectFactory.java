
package tpgr06.coronatickets.ws.publicador;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the tpgr06.coronatickets.ws.publicador package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BadRequestException_QNAME = new QName("http://publicador.ws.coronatickets.tpgr06/", "BadRequestException");
    private final static QName _NotFoundException_QNAME = new QName("http://publicador.ws.coronatickets.tpgr06/", "NotFoundException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: tpgr06.coronatickets.ws.publicador
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CategoriaDTO }
     * 
     */
    public CategoriaDTO createCategoriaDTO() {
        return new CategoriaDTO();
    }

    /**
     * Create an instance of {@link CategoriaDTO.Espectaculos }
     * 
     */
    public CategoriaDTO.Espectaculos createCategoriaDTOEspectaculos() {
        return new CategoriaDTO.Espectaculos();
    }

    /**
     * Create an instance of {@link BadRequestException }
     * 
     */
    public BadRequestException createBadRequestException() {
        return new BadRequestException();
    }

    /**
     * Create an instance of {@link NotFoundException }
     * 
     */
    public NotFoundException createNotFoundException() {
        return new NotFoundException();
    }

    /**
     * Create an instance of {@link UsuarioAuthDTO }
     * 
     */
    public UsuarioAuthDTO createUsuarioAuthDTO() {
        return new UsuarioAuthDTO();
    }

    /**
     * Create an instance of {@link EspectaculoDTO }
     * 
     */
    public EspectaculoDTO createEspectaculoDTO() {
        return new EspectaculoDTO();
    }

    /**
     * Create an instance of {@link ValoracionDTO }
     * 
     */
    public ValoracionDTO createValoracionDTO() {
        return new ValoracionDTO();
    }

    /**
     * Create an instance of {@link PaqueteDTO }
     * 
     */
    public PaqueteDTO createPaqueteDTO() {
        return new PaqueteDTO();
    }

    /**
     * Create an instance of {@link CompraPaqueteDTO }
     * 
     */
    public CompraPaqueteDTO createCompraPaqueteDTO() {
        return new CompraPaqueteDTO();
    }

    /**
     * Create an instance of {@link PremioDTO }
     * 
     */
    public PremioDTO createPremioDTO() {
        return new PremioDTO();
    }

    /**
     * Create an instance of {@link DataPlataforma }
     * 
     */
    public DataPlataforma createDataPlataforma() {
        return new DataPlataforma();
    }

    /**
     * Create an instance of {@link UsuarioDTO }
     * 
     */
    public UsuarioDTO createUsuarioDTO() {
        return new UsuarioDTO();
    }

    /**
     * Create an instance of {@link FuncionDTO }
     * 
     */
    public FuncionDTO createFuncionDTO() {
        return new FuncionDTO();
    }

    /**
     * Create an instance of {@link DataEspectaculo }
     * 
     */
    public DataEspectaculo createDataEspectaculo() {
        return new DataEspectaculo();
    }

    /**
     * Create an instance of {@link DataUsuario }
     * 
     */
    public DataUsuario createDataUsuario() {
        return new DataUsuario();
    }

    /**
     * Create an instance of {@link DataFuncion }
     * 
     */
    public DataFuncion createDataFuncion() {
        return new DataFuncion();
    }

    /**
     * Create an instance of {@link DataPaquete }
     * 
     */
    public DataPaquete createDataPaquete() {
        return new DataPaquete();
    }

    /**
     * Create an instance of {@link StringListWrapper }
     * 
     */
    public StringListWrapper createStringListWrapper() {
        return new StringListWrapper();
    }

    /**
     * Create an instance of {@link DataCategoria }
     * 
     */
    public DataCategoria createDataCategoria() {
        return new DataCategoria();
    }

    /**
     * Create an instance of {@link ArtistaDTO }
     * 
     */
    public ArtistaDTO createArtistaDTO() {
        return new ArtistaDTO();
    }

    /**
     * Create an instance of {@link PlataformaDTO }
     * 
     */
    public PlataformaDTO createPlataformaDTO() {
        return new PlataformaDTO();
    }

    /**
     * Create an instance of {@link EspectadorDTO }
     * 
     */
    public EspectadorDTO createEspectadorDTO() {
        return new EspectadorDTO();
    }

    /**
     * Create an instance of {@link RegistroFuncionDTO }
     * 
     */
    public RegistroFuncionDTO createRegistroFuncionDTO() {
        return new RegistroFuncionDTO();
    }

    /**
     * Create an instance of {@link CategoriaDTO.Espectaculos.Entry }
     * 
     */
    public CategoriaDTO.Espectaculos.Entry createCategoriaDTOEspectaculosEntry() {
        return new CategoriaDTO.Espectaculos.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BadRequestException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://publicador.ws.coronatickets.tpgr06/", name = "BadRequestException")
    public JAXBElement<BadRequestException> createBadRequestException(BadRequestException value) {
        return new JAXBElement<BadRequestException>(_BadRequestException_QNAME, BadRequestException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://publicador.ws.coronatickets.tpgr06/", name = "NotFoundException")
    public JAXBElement<NotFoundException> createNotFoundException(NotFoundException value) {
        return new JAXBElement<NotFoundException>(_NotFoundException_QNAME, NotFoundException.class, null, value);
    }

}
