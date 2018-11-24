package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLConfiguraciones;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Estado;
import adminfree.enums.Mapper;
import adminfree.enums.MessageBusiness;
import adminfree.enums.Numero;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.MapperJDBC;
import adminfree.persistence.ProceduresJDBC;
import adminfree.persistence.ValueSQL;
import adminfree.utilities.BusinessException;
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
				ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER),
				ValueSQL.get(cliente.getCredenciales().getUsuario(), Types.VARCHAR));

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
	 * Metodo que permite consultar los usuarios con estados (ACTIVO/INACTIVO)
	 * asociados a un cliente especifico
	 * 
	 * @param filtro, contiene los datos del filtro de busqueda
	 * @return lista de Usuarios asociados a un cliente
	 */
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> getUsuariosCliente(ClienteDTO filtro, Connection connection) throws Exception {
		return (List<UsuarioDTO>) find(connection,
				SQLConfiguraciones.GET_USUARIOS_CLIENTE,
				MapperJDBC.get(Mapper.GET_USUARIOS_CLIENTE),
				ValueSQL.get(filtro.getId(), Types.BIGINT),
				ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER),
				ValueSQL.get(Estado.INACTIVO.id, Types.INTEGER));
	}

	/**
	 * Metodo que permite crear el usuario con sus privilegios en el sistema
	 * 
	 * @param usuario, DTO que contiene los datos del usuarios
	 * @param securityPostPass, se utiliza para encriptar la clave de ingreso
	 * @return DTO con los datos del usuario creado
	 */
	public UsuarioDTO crearUsuario(
			UsuarioDTO usuario,
			String securityPostPass,
			Connection connection) throws Exception {

		// se verifica que no exista un usuario de ingreso registrado en la BD
		String usuarioIngreso = usuario.getUsuarioIngreso();
		Long count = (Long) find(connection,
				SQLConfiguraciones.COUNT_USUARIO_INGRESO,
				MapperJDBC.get(Mapper.COUNT),
				ValueSQL.get(usuarioIngreso, Types.VARCHAR));

		// si existe algun 'usuario de ingreso' registrado en la BD no se PUEDE crear el usuario
		if (!count.equals(Numero.ZERO.value.longValue())) {
			throw new BusinessException(MessageBusiness.USUARIO_INGRESO_EXISTE.value);
		}

		// bloque para la creacion del usuario con sus privilegios
		try {
			connection.setAutoCommit(false);
			EstrategiaCriptografica criptografica = EstrategiaCriptografica.get();

			// se genera una nueva clave para el nuevo usuario
			String claveIngreso = criptografica.generarToken();

			// se encripta la nueva clave para ser almacenada en la BD
			String claveIngresoEncriptada = criptografica.encriptarPassword(claveIngreso, securityPostPass);

			// se procede a crear el USUARIO en la BD
			insertUpdate(connection,
					SQLConfiguraciones.CREAR_USUARIO,
					ValueSQL.get(usuario.getNombre(), Types.VARCHAR),
					ValueSQL.get(usuarioIngreso, Types.VARCHAR),
					ValueSQL.get(claveIngresoEncriptada, Types.VARCHAR),
					ValueSQL.get(usuario.getCliente().getId(), Types.BIGINT),
					ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER));

			// se obtiene el identificador del usuario creado
			Long idUsuario = (Long) find(connection,
					SQLConfiguraciones.GET_ID_USUARIO,
					MapperJDBC.get(Mapper.GET_ID),
					ValueSQL.get(usuarioIngreso, Types.VARCHAR),
					ValueSQL.get(claveIngresoEncriptada, Types.VARCHAR));

			// creacion del los privilegios asignados al nuevo usuario
			List<String> privilegios = usuario.getModulosTokens();
			if (privilegios != null && !privilegios.isEmpty()) {

				// se construye la lista de injections para la insercion
				List<List<ValueSQL>> injections = new ArrayList<>();
				ValueSQL valueIdUser = ValueSQL.get(idUsuario, Types.BIGINT);
				List<ValueSQL> where;
				for (String privilegio : privilegios) {
					where = new ArrayList<>();
					where.add(valueIdUser);
					where.add(ValueSQL.get(privilegio, Types.VARCHAR));
					injections.add(where);
				}

				// se inserta la lista de privilegios asignados al usuario
				batchConInjection(connection, SQLConfiguraciones.INSERTAR_PRIVILEGIOS_USER, injections);
			}

			// se construye los datos del usuario a retornar
			UsuarioDTO nuevoUsuario = new UsuarioDTO();
			nuevoUsuario.setId(idUsuario);
			nuevoUsuario.setNombre(usuario.getNombre());
			nuevoUsuario.setEstado(Estado.ACTIVO.id);
			nuevoUsuario.setEstadoNombre(Util.getEstadoNombre(nuevoUsuario.getEstado()));
			nuevoUsuario.setCliente(usuario.getCliente());
			nuevoUsuario.setModulosTokens(privilegios);
			nuevoUsuario.setUsuarioIngreso(usuarioIngreso);
			nuevoUsuario.setClaveIngreso(claveIngreso);

			// se debe confirmar los cambios en BD
			connection.commit();

			// este DTO contiene todos los datos del nuevo usuario en la BD
			return nuevoUsuario;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite cambiar el estado de un usuario
	 * 
	 * @param usuario, DTO que contiene los datos del usuario a modificar
	 */
	public void modificarEstadoUsuario(UsuarioDTO usuario, Connection connection) throws Exception {
		insertUpdate(connection,
				SQLConfiguraciones.UPDATE_ESTADO_USER,
				ValueSQL.get(usuario.getEstado(), Types.INTEGER),
				ValueSQL.get(usuario.getId(), Types.BIGINT));
	}

	/**
	 * Metodo que permite modificar los privilegios de un Usuario
	 * 
	 * @param usuario, DTO que contiene el identificador y los privilegios a modificar
	 */
	public void modificarPrivilegiosUsuario(UsuarioDTO usuario, Connection connection) throws Exception {
		// lista que contiene los DMLS a ejecutar por el batch
		List<String> dmls = new ArrayList<>();

		// se obtiene el identificador del usuario
		String idUser = usuario.getId().toString();

		// DML para eliminar los privilegios asociados al usuario que llega por parametro
		dmls.add(SQLConfiguraciones.DELETE_PRIVILEGIOS_USER_.replace(CommonConstant.INTERROGACION, idUser));

		// se configura los inserts de los nuevos privilegios asociado al user
		List<String> privilegios = usuario.getModulosTokens();
		if (privilegios != null && !privilegios.isEmpty()) {
			for (String privilegio : privilegios) {
				dmls.add(SQLConfiguraciones.INSERTAR_PRIVILEGIOS_USER_.
						replace(CommonConstant.INTERROGACION_1, idUser).
						replace(CommonConstant.INTERROGACION_2, privilegio));
			}
		}

		// se procede a ejecutar los DMLS atravez del batch de JDBC
		try {
			connection.setAutoCommit(false);
			batchSinInjection(connection, dmls);
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
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
