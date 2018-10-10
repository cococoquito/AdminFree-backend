package adminfree.b.services;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Service;

import adminfree.c.business.ConfiguracionesBusiness;
import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.e.utilities.CerrarRecursos;
import adminfree.g.persistence.ConnectionFactory;

/**
 * 
 * Clase que contiene todos los servicios para el modulo de Configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
@Service("configuracionesService")
public class ConfiguracionesService {

	/**
	 * Servicio que permite crear un cliente en el sistema
	 * 
	 * @param cliente, DTO con los datos del cliente a crear
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	public ClienteDTO crearCliente(ClienteDTO cliente) throws Exception {
		// variable de referencia de la conexion
		Connection connection = null;
		try {
			// se crea la conexion de la BD
			connection = ConnectionFactory.getConnectionAdminFree();
			
			// se procede a crear el cliente en BD
			return new ConfiguracionesBusiness().crearCliente(cliente, connection);
		} finally {
			// se desconecta la conexion
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio para obtener todos los CLIENTES del sistema
	 * 
	 * @return, lista de CLIENTES configurados
	 */
	public List<ClienteDTO> listarTodosClientes() throws Exception {
		// variable de referencia de la conexion
		Connection connection = null;
		try {
			// se crea la conexion de la BD
			connection = ConnectionFactory.getConnectionAdminFree();

			// se procede a listar todos los clientes del sistema
			return new ConfiguracionesBusiness().listarTodosClientes(connection);
		} finally {
			// se desconecta la conexion
			CerrarRecursos.closeConnection(connection);
		}
	}
}
