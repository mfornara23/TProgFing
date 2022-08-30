package tpgr06.coronatickets.sys.paquete;

import java.util.Date;
import java.util.List;

import tpgr06.coronatickets.sys.categoria.CategoriaDTO;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;

public interface IPaqueteController {

	/**
	 * Lista todos los paquetes registrados en el sistema
	 *
	 * @return lista de PaqueteDTO o emptyList
	 */
	List<PaqueteDTO> listarPaquetes();

	/**
	 * Consulta por nombre un paquete.
	 *
	 * @param nombre
	 * @return
	 * @throws NotFoundException si el paquete no existe en el sistema
	 */
	PaqueteDTO consultaPaquete(String nombre) throws NotFoundException;

	List<PlataformaDTO> listarPlataformas();

	/**
	 * El sistema lista los espectaculos existentes en
	 * esa plataforma que no forman parte del paquete seleccionado.
	 *
	 * @param nombrePaquete
	 * @param nombrePlataforma
	 * @return Espectaculos
	 * @throws NotFoundException si el paquete o plataforma no existen en el sistema
	 */
	List<EspectaculoDTO> listarEspectaculosNoEnPaquete(String nombrePaquete, String nombrePlataforma) throws NotFoundException;

	/**
	 * El sistema ingresa el espectaculo en el paquete seleccionado
	 *
	 * @param nombreEspectaculo
	 * @param nombrePaquete
	 * @throws NotFoundException si el espectaculo o paquete no existen en el sistema
	 */
	void agregarEspectaculoAPaquete(String nombreEspectaculo, String nombrePaquete) throws NotFoundException, BadRequestException;


	/**
	 * Crea un paquete de espectaculos
	 *
	 * @param nombre
	 * @param descripcion
	 * @param fechaInicio
	 * @param fechaFin
	 * @throws BadRequestException si el nombre del paquete ya existe
	 */
	void altaPaquete(String nombre, String descripcion, Date fechaInicio, Date fechaFin, Integer descuento, String imagen) throws BadRequestException;

	/**
	 * Agrega el paquete a la lista de compras de paquetes del espectador
	 * @param nombrePaquete
	 * @param emailEspectador
	 * @param fechaCompra
	 * @throws NotFoundException 
	 * @throws BadRequestException 
	 */
	void compraPaquete(String nombrePaquete, String emailEspectador, Date fechaCompra) throws NotFoundException, BadRequestException;
	
	/**
	 *
	 * @param nombre
	 * @return
	 * @throws NotFoundException si el nombre de la plataforma no existe en el sistema
	 */
	PlataformaDTO consultaPlataforma(String nombre) throws NotFoundException;

	/**
	 *  Devuelve True si existe Plataforma con dicho nombre
	 *
	 * @param nombre
	 * @return
	 */
	boolean existePlataforma(String nombre);

	/**
	 *
	 * @param nombre
	 * @param descripcion
	 * @param url
	 * @throws BadRequestException si el nombre del paquete ya existe
	 */
	void altaPlataforma(String nombre, String descripcion, String url) throws BadRequestException;
	
	/**
	 * 
	 * @param nombre
	 * @return
	 * @throws NotFoundException si el nombre del paquete no se encuentra en el sistema
	 */
	public List<CategoriaDTO> listarCategoriasByPaquete(String nombre) throws NotFoundException; 
	

	/**
	 * 
	 * @param fechaVigente
	 * @return
	 */
	public List<PaqueteDTO> listarPaquetesVigentes(Date fechaVigente); /* Andres */

	/**
	 * 
	 * @param key
	 * @return
	 */
	public List<PaqueteDTO> buscarPaquetes(String key);
	
	
	/**
	 * 
	 * @return
	 */
	public List<PaqueteDTO> listarPaquetesByEspecatculo(String espectaculo);
}
