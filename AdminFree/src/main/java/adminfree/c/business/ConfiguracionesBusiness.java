package adminfree.c.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.e.utilities.ConstantBusinessMessages;
import adminfree.e.utilities.ConstantEstado;
import adminfree.e.utilities.ConstantNumeros;
import adminfree.e.utilities.EstrategiaCriptografica;
import adminfree.g.persistence.CommonDAO;
import adminfree.g.persistence.ConstantSQL;
import adminfree.g.persistence.MapperJDBC;
import adminfree.g.persistence.ProceduresJDBC;
import adminfree.g.persistence.SQLConfiguraciones;
import adminfree.g.persistence.ValueSQL;

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
		String token = generarToken(con);

		// se configuran los valores para realizar el INSERT
		List<Object> insertValues = new ArrayList<>();
		insertValues.add(token);
		insertValues.add(cliente.getNombre());
		insertValues.add(cliente.getTelefonos());
		insertValues.add(cliente.getEmails());
		insertValues.add(ConstantEstado.ID_ESTADO_ACTIVO);

		// se procede a realizar el INSERT en la BD
		insert(SQLConfiguraciones.CREAR_CLIENTE, insertValues, con);

		// se configura el WHERE sentence para obtener el cliente por su TOKEN
		List<Object> whereValues = new ArrayList<>();
		whereValues.add(token);

		// se retorna el cliente con sus datos registrados en el sistema
		MapperJDBC mapper = new MapperJDBC(MapperJDBC.MAPPER_GET_CLIENTE_TOKEN);
		Object clienteBD = find(SQLConfiguraciones.GET_CLIENTE_TOKEN, whereValues, mapper, con);
		return (ClienteDTO) clienteBD;
	}
	
	/**
	 * Business para obtener todos los CLIENTES del sistema
	 * 
	 * @return, lista de CLIENTES configurados
	 */	
	@SuppressWarnings("unchecked")
	public List<ClienteDTO> listarTodosClientes(Connection connection) throws Exception {
		// se configura el Mapper especifico para esta solicitud
		MapperJDBC mapper = new MapperJDBC(MapperJDBC.MAPPER_GET_CLIENTES);

		// se obtiene todos los clientes del sistema
		Object clientes = find(SQLConfiguraciones.LISTAR_CLIENTES, null, mapper, connection);
		return (List<ClienteDTO>) clientes;
	}
	
	/**
	 * Business para actualizar los datos del CLIENTE
	 * 
	 * @param clienteUpdate, Datos del cliente actualizar
	 */
	public void actualizarCliente(ClienteDTO clienteUpdate, Connection connection) throws Exception {
		// se configura los valores ACTUALIZAR
		List<ValueSQL> valoresUpdate = new ArrayList<>();
		valoresUpdate.add(new ValueSQL(clienteUpdate.getNombre(), null));
		valoresUpdate.add(new ValueSQL(clienteUpdate.getEmails(), null));
		valoresUpdate.add(new ValueSQL(clienteUpdate.getTelefonos(), null));
		valoresUpdate.add(new ValueSQL(clienteUpdate.getId(), null));

		// se actualiza los datos del cliente en BD
		update(SQLConfiguraciones.ACTUALIZAR_CLIENTE, valoresUpdate, connection);
	}
	
	/**
	 * Business que permite cambiar el estado del CLIENTE
	 * 
	 * @param cliente, DTO que contiene el identificador del cliente ACTUALIZAR
	 * @param estado, nuevo estado del CLIENTE
	 */
	public void cambiarEstadoCliente(ClienteDTO cliente, Integer estado, Connection conn) throws Exception {
		// se configura los valores ACTUALIZAR
		List<ValueSQL> valoresUpdate = new ArrayList<>();
		valoresUpdate.add(new ValueSQL(estado, null));
		valoresUpdate.add(new ValueSQL(cliente.getId(), null));

		// se actualiza el estado del CLIENTE
		update(SQLConfiguraciones.CAMBIAR_ESTADO_CLIENTE, valoresUpdate, conn);
	}
	
	/**
	 * Business que permite ELIMINAR un cliente del sistema
	 * 
	 * @param cliente, DTO que contiene el identificador del cliente ELIMINAR
	 */
	public void eliminarCliente(ClienteDTO cliente, Connection con) throws Exception {
		// se ejecuta el procedimiento almacenado de ELIMINAR CLIENTE
		String resultado = new ProceduresJDBC().eliminarCliente(cliente.getId(), con);

		// se valida que la ejecucion sea EXITOSA
		if (!ConstantSQL.SUCCESSFUL.equals(resultado)) {
			throw new Exception(ConstantBusinessMessages.ERROR_ELIMINAR_CLIENTE + resultado);
		}
	}

	/**
	 * Metodo que permite generar un TOKEN unico
	 */
	private String generarToken(Connection con) throws Exception {
		// es el valor del nuevo TOKEN a retornar
		String token = null;
		
		// es el generador de TOKEN
		EstrategiaCriptografica generador = new EstrategiaCriptografica();
		
		// es el MAPPER para obtener el Count de los clientes asociados a un TOKEN
		MapperJDBC mapper = new MapperJDBC(MapperJDBC.MAPPER_COUNT);

		// el TOKEN debe ser unico en el sistema
		boolean tokenExiste = true;
		List<Object> whereToken;
		Long count;
		while (tokenExiste) {

			// se solicita un nuevo TOKEN
			token = generador.generarToken();

			// se procede a contar los registros que contenga este TOKEN
			whereToken = new ArrayList<>();
			whereToken.add(token);
			count = (Long) find(SQLConfiguraciones.COUNT_CLIENTE_TOKEN, whereToken, mapper, con);

			// valida si el TOKEN es unico en el sistema
			if (count.equals(ConstantNumeros.ZERO.longValue())) {
				tokenExiste = false;
			}
		}
		return token;
	}
}
