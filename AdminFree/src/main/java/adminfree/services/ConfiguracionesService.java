package adminfree.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adminfree.business.ConfiguracionesBusiness;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.utilities.CerrarRecursos;

/**
 * 
 * Clase que contiene todos los servicios para el modulo de Configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
@Service
public class ConfiguracionesService {

	/** DataSource para las conexiones de la BD de AdminFree */
	@Autowired
	private DataSource adminFreeDS;
	
	/**
	 * Servicio que permite crear un cliente en el sistema
	 * 
	 * @param cliente, DTO con los datos del cliente a crear
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	public ClienteDTO crearCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a crear el cliente en BD
			return new ConfiguracionesBusiness().crearCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio para actualizar los datos del CLIENTE
	 * 
	 * @param clienteUpdate, datos del cliente ACTUALIZAR
	 */
	public void actualizarCliente(ClienteDTO clienteUpdate) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede actualizar los datos del CLIENTE
			new ConfiguracionesBusiness().actualizarCliente(clienteUpdate, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
	
	/**
	 * Servicio que permite ACTIVAR un cliente
	 * 
	 * @return cliente con los nuevos datos de la ACTIVACION
	 * @param cliente, contiene el identificador del cliente
	 */
	public ClienteDTO activarCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede activar el CLIENTE
			return new ConfiguracionesBusiness().activarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite INACTIVAR un cliente
	 * 
	 * @return cliente con los nuevos datos de la INACTIVACION
	 * @param cliente, contiene el identificador del cliente
	 */
	public ClienteDTO inactivarCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede inactivar el CLIENTE
			return new ConfiguracionesBusiness().inactivarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
	
	/**
	 * Servicio que permite ELIMINAR un cliente del sistema
	 * 
	 * @param cliente, DTO que contiene el identificador del cliente ELIMINAR
	 * @return OK, de lo contrario el mensaje de error de MYSQL
	 */
	public String eliminarCliente(ClienteDTO cliente) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede ELIMINAR el CLIENTE
			return new ConfiguracionesBusiness().eliminarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
	
	/**
	 * Servicio que permite consultar los usuarios con estados (ACTIVO/INACTIVO)
	 * asociados a un cliente especifico
	 * 
	 * @param filtro, contiene los datos del filtro de busqueda
	 * @return lista de Usuarios asociados a un cliente
	 */	
	public List<UsuarioDTO> getUsuariosCliente(ClienteDTO filtro) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a consultar los usuarios asociados a un cliente
			return new ConfiguracionesBusiness().getUsuariosCliente(filtro, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
