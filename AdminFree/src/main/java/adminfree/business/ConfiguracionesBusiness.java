package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;

import adminfree.constants.SQLConfiguraciones;
import adminfree.enums.Estado;
import adminfree.enums.Mapper;
import adminfree.enums.Numero;
import adminfree.model.configuraciones.ClienteDTO;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.MapperJDBC;
import adminfree.persistence.ProceduresJDBC;
import adminfree.persistence.ValueSQL;
import adminfree.utilities.EstrategiaCriptografica;
import adminfree.utilities.Util;

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
	public ClienteDTO crearCliente(ClienteDTO cliente, Connection con) throws Exception {
		// se procede a generar un TOKEN unico para este cliente
		ValueSQL token = generarToken(con);

		// se procede a realizar el INSERT en la BD
		insertUpdate(con, SQLConfiguraciones.CREAR_CLIENTE, 
				token, 
				ValueSQL.get(cliente.getNombre(), Types.VARCHAR),
				ValueSQL.get(cliente.getTelefonos(), Types.VARCHAR), 
				ValueSQL.get(cliente.getEmails(), Types.VARCHAR),
				ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER));

		// se retorna el cliente con sus datos registrados en el sistema
		return (ClienteDTO) find(con, SQLConfiguraciones.GET_CLIENTE_TOKEN,
				MapperJDBC.get(Mapper.GET_CLIENTE), 
				token);
	}
	
	/**
	 * Business para obtener todos los CLIENTES del sistema
	 * 
	 * @return, lista de CLIENTES configurados
	 */	
	@SuppressWarnings("unchecked")
	public List<ClienteDTO> listarClientes(Connection connection) throws Exception {
		return (List<ClienteDTO>) findAll(
				connection, SQLConfiguraciones.LISTAR_CLIENTES,
				MapperJDBC.get(Mapper.GET_CLIENTES));
	}
	
	/**
	 * Business para actualizar los datos del CLIENTE
	 * 
	 * @param clienteUpdate, Datos del cliente actualizar
	 */
	public void actualizarCliente(ClienteDTO clienteUpdate, Connection conn) throws Exception {
		insertUpdate(conn, SQLConfiguraciones.ACTUALIZAR_CLIENTE,
				ValueSQL.get(clienteUpdate.getNombre(), Types.VARCHAR),
				ValueSQL.get(clienteUpdate.getEmails(), Types.VARCHAR),
				ValueSQL.get(clienteUpdate.getTelefonos(), Types.VARCHAR),
				ValueSQL.get(clienteUpdate.getId(), Types.BIGINT));
	}
	
	/**
	 * Metodo que permite ACTIVAR un cliente
	 * 
	 * @return cliente con los nuevos datos de la ACTIVACION
	 * @param cliente, contiene el identificador del cliente
	 */
	public ClienteDTO activarCliente(ClienteDTO cliente, Connection conn) throws Exception {
		insertUpdate(conn, SQLConfiguraciones.ACTIVAR_CLIENTE,
				ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER),
				ValueSQL.get(cliente.getId(), Types.BIGINT));
		
		// se configura los nuevos datos del cliente
		cliente.setEstado(Estado.ACTIVO.id);
		cliente.setEstadoNombre(Util.getEstadoNombre(Estado.ACTIVO.id));
		cliente.setFechaInactivacion(null);
		cliente.setFechaActivacion(Calendar.getInstance().getTime());
		return cliente;
	}
	
	/**
	 * Metodo que permite INACTIVAR un cliente
	 * 
	 * @return cliente con los nuevos datos de la INACTIVACION
	 * @param cliente, contiene el identificador del cliente
	 */
	public ClienteDTO inactivarCliente(ClienteDTO cliente, Connection conn) throws Exception {
		insertUpdate(conn, SQLConfiguraciones.INACTIVAR_CLIENTE,
				ValueSQL.get(Estado.INACTIVO.id, Types.INTEGER),
				ValueSQL.get(cliente.getId(), Types.BIGINT));
		
		// se configura los nuevos datos del cliente
		cliente.setEstado(Estado.INACTIVO.id);
		cliente.setEstadoNombre(Util.getEstadoNombre(Estado.INACTIVO.id));
		cliente.setFechaInactivacion(Calendar.getInstance().getTime());
		return cliente;		
	}
	
	/**
	 * Business que permite ELIMINAR un cliente del sistema
	 * 
	 * @param cliente, DTO que contiene el identificador del cliente ELIMINAR
	 * @return OK, de lo contrario el mensaje de error de MYSQL
	 */
	public String eliminarCliente(ClienteDTO cliente, Connection con) throws Exception {
		// se ejecuta el procedimiento almacenado de ELIMINAR CLIENTE
		return new ProceduresJDBC().eliminarCliente(cliente.getId(), con);
	}

	/**
	 * Metodo que permite generar un TOKEN unico
	 */
	private ValueSQL generarToken(Connection con) throws Exception {
		// es el valor del nuevo TOKEN a retornar
		ValueSQL token = ValueSQL.get(null, Types.VARCHAR);
		
		// es el MAPPER para obtener el count de los clientes asociados a un TOKEN
		MapperJDBC mapper = MapperJDBC.get(Mapper.COUNT);

		// el TOKEN debe ser unico en el sistema
		boolean tokenExiste = true;
		while (tokenExiste) {

			// se solicita un nuevo TOKEN
			token.setValor(EstrategiaCriptografica.get().generarToken());

			// se procede a contar los registros que contenga este TOKEN
			Long count = (Long)find(con, SQLConfiguraciones.COUNT_CLIENTE_TOKEN, mapper, token);

			// valida si el TOKEN es unico en el sistema
			if (count.equals(Numero.ZERO.value.longValue())) {
				tokenExiste = false;
			}
		}
		return token;
	}
}
