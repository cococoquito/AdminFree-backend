package adminfree.c.business;

import java.sql.Connection;

import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.g.persistence.CommonDAO;

/**
 * 
 * Clase que contiene los procesos de negocio para el modulo de Configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ConfiguracionesBusiness extends CommonDAO {

	/**
	 * Business que permite crear un cliente en el sistema
	 * 
	 * @param cliente, DTO con los datos del cliente a crear
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	public ClienteDTO crearCliente(ClienteDTO cliente, Connection conn) throws Exception {
		return null;
	}
}
