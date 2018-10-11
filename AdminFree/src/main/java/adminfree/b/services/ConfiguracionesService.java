package adminfree.b.services;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import adminfree.c.business.ConfiguracionesBusiness;
import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.e.utilities.CerrarRecursos;
import adminfree.g.persistence.ConstantSQL;

/**
 * 
 * Clase que contiene todos los servicios para el modulo de Configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
@Service("configuracionesService")
public class ConfiguracionesService {

	/** DataSource para las conexiones de la BD de AdminFree */
	@Autowired
	private DataSource adminFreeDS;

	/** Contiene la clave para la autenticacion del administrador de clientes */
	@Value("${adminCliente.clave}")
	private String adminClienteClave;

	/** Contiene el usuario para la autenticacion del administrador de clientes */
	@Value("${adminCliente.user}")
	private String adminClienteUser;

	/**
	 * Servicio que permite soportar el proceso de iniciar sesion de Admin Clientes
	 * 
	 * @param clave, clave de la autenticacion
	 * @param usuario, usuario de la autenticacion
	 * @return 200 si es exitoso, de lo contrario 400
	 */
	public String iniciarSesionAdminClientes(String clave, String usuario) {
		String resultado = ConstantSQL.BAD_REQUEST;
		if (clave != null && usuario != null && 
			clave.equals(this.adminClienteClave) && 
			usuario.equals(this.adminClienteUser)) {
			resultado = ConstantSQL.SUCCESSFUL;
		}
		return resultado;
	}

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
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a crear el cliente en BD
			return new ConfiguracionesBusiness().crearCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio para obtener todos los CLIENTES del sistema
	 * 
	 * @return, lista de CLIENTES configurados
	 */
	public List<ClienteDTO> listarClientes() throws Exception {
		// variable de referencia de la conexion
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a listar todos los clientes del sistema
			return new ConfiguracionesBusiness().listarClientes(connection);
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
		// variable de referencia de la conexion
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
	 * Metodo que permite ACTIVAR un cliente
	 * 
	 * @param cliente, contiene el identificador del cliente
	 */
	public void activarCliente(ClienteDTO cliente) throws Exception {
		// variable de referencia de la conexion
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede activar el CLIENTE
			new ConfiguracionesBusiness().activarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}		
	}
	
	/**
	 * Metodo que permite INACTIVAR un cliente
	 * 
	 * @param cliente, contiene el identificador del cliente
	 */
	public void inactivarCliente(ClienteDTO cliente) throws Exception {
		// variable de referencia de la conexion
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede inactivar el CLIENTE
			new ConfiguracionesBusiness().inactivarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}		
	}	
	
	/**
	 * Servicio que permite ELIMINAR un cliente del sistema
	 * 
	 * @param cliente, DTO que contiene el identificador del cliente ELIMINAR
	 */
	public void eliminarCliente(ClienteDTO cliente) throws Exception {
		// variable de referencia de la conexion
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede ELIMINAR el CLIENTE
			new ConfiguracionesBusiness().eliminarCliente(cliente, connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}
}
