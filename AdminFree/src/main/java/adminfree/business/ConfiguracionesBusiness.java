package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLConfiguraciones;
import adminfree.dtos.configuraciones.CambioClaveDTO;
import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.CampoEntradaEdicionDTO;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.dtos.configuraciones.UsuarioEdicionDTO;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Estado;
import adminfree.enums.Mapper;
import adminfree.enums.MessagesKey;
import adminfree.enums.Numero;
import adminfree.enums.TipoCampo;
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
	 * Metodo que permite validar los datos del usuario para la creacion o modificacion
	 *
	 * @param usuario, DTO con los datos del usuario a crear o modificar
	 */
	public void validarDatosUsuario(UsuarioDTO usuario, Connection connection) throws Exception {
		// se aplica las validaciones de negocio para el usuario de ingreso
		validarUsuarioIngreso(usuario.getUsuarioIngreso(), connection);
	}

	/**
	 * Metodo que permite crear el usuario con sus privilegios en el sistema
	 *
	 * @param usuario, DTO que contiene los datos del usuario
	 * @param securityPostPass, se utiliza para encriptar la clave de ingreso
	 * @return DTO con los datos del usuario creado
	 */
	public UsuarioDTO crearUsuario(
			UsuarioDTO usuario,
			String securityPostPass,
			Connection connection) throws Exception {
		try {
			connection.setAutoCommit(false);
			EstrategiaCriptografica criptografica = EstrategiaCriptografica.get();

			// se genera una nueva clave para el nuevo usuario
			String claveIngreso = criptografica.generarToken();

			// se encripta la nueva clave para ser almacenada en la BD
			String claveIngresoEncriptada = criptografica.encriptarPassword(claveIngreso, securityPostPass);

			// se obtiene el valor del usuario de ingreso
			String usuarioIngreso = usuario.getUsuarioIngreso();

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
	 * Metodo que permite editar los datos de un usuario
	 *
	 * @param datos, DTO que contiene los datos a modificar
	 */
	public void editarUsuario(UsuarioEdicionDTO datos, Connection connection) throws Exception {
		try {
			connection.setAutoCommit(false);

			// se obtiene los datos del usuario
			UsuarioDTO usuario = datos.getUsuario();

			// modificaciones para los datos basicos del usuario
			if (datos.isDatosBasicosEditar()) {
				insertUpdate(connection, SQLConfiguraciones.UPDATE_DATOS_CUENTA,
						ValueSQL.get(usuario.getNombre(), Types.VARCHAR),
						ValueSQL.get(usuario.getUsuarioIngreso(), Types.VARCHAR),
						ValueSQL.get(usuario.getId(), Types.BIGINT));
			}

			// modificaciones de los modulos asignados para el usuario
			if (datos.isModulosEditar()) {
				List<String> dmls = new ArrayList<>();
				String idUser = usuario.getId().toString();

				// DML para eliminar los privilegios asociados al usuario que llega por parametro
				// no hay lio utilizar este delete dado que esta tabla no utiliza autoincrement
				dmls.add(SQLConfiguraciones.DELETE_PRIVILEGIOS_USER_.replace(CommonConstant.INTERROGACION, idUser));

				// se configura los inserts de los nuevos privilegios asociado al usuario
				List<String> tokens = usuario.getModulosTokens();
				for (String token : tokens) {
					dmls.add(SQLConfiguraciones.INSERTAR_PRIVILEGIOS_USER_
							.replace(CommonConstant.INTERROGACION_1, idUser)
							.replace(CommonConstant.INTERROGACION_2, token));
				}

				// se ejecuta el batch modificando los modulos asignados
				batchSinInjection(connection, dmls);
			}

			// se debe confirmar los cambios en BD
			connection.commit();
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
	 * Metodo que permite generar una nueva clave de ingreso
	 * para el usuario que llega por parametro
	 *
	 * @param usuario, DTO con el identificador del usuario
	 * @param securityPostPass, se utiliza para encriptar la clave
	 * @return DTO con la clave de ingreso generada
	 */
	public CredencialesDTO generarClaveIngreso(
			UsuarioDTO usuario,
			String securityPostPass,
			Connection connection) throws Exception {

		// se utiliza para encriptar la clave de ingreso
		EstrategiaCriptografica criptografica = EstrategiaCriptografica.get();

		// se genera una nueva clave para el usuario
		String claveIngreso = criptografica.generarToken();

		// se encripta la nueva clave para ser almacenada en la BD
		String claveIngresoEncriptada = criptografica.encriptarPassword(claveIngreso, securityPostPass);

		// actualiza la clave de ingreso en la BD
		insertUpdate(connection,
				SQLConfiguraciones.ACTUALIZAR_CLAVE_INGRESO,
				ValueSQL.get(claveIngresoEncriptada, Types.VARCHAR),
				ValueSQL.get(usuario.getId(), Types.BIGINT));

		// se retorna las credenciales con la nueva clave
		CredencialesDTO credenciales = new CredencialesDTO();
		credenciales.setClave(claveIngreso);
		return credenciales;
	}

	/**
	 * Metodo que permite actualizar los datos de la cuenta
	 * del usuario, solamente aplica (Nombre, Usuario Ingreso)
	 *
	 * @param usuario, DTO 	que contiene los datos del usuario
	 */
	public void modificarDatosCuenta(UsuarioDTO usuario, Connection connection) throws Exception {
		// se obtiene el valor del usuario de ingreso
		String userIngreso = usuario.getUsuarioIngreso();

		// se aplica las validaciones de negocio para el usuario de ingreso si es modificado
		if (usuario.isUserIngresoModificado()) {
			validarUsuarioIngreso(userIngreso, connection);
		}

		if (usuario.isUserIngresoModificado()) {
			insertUpdate(connection,
					SQLConfiguraciones.UPDATE_DATOS_CUENTA,
					ValueSQL.get(usuario.getNombre(), Types.VARCHAR),
					ValueSQL.get(userIngreso, Types.VARCHAR),
					ValueSQL.get(usuario.getId(), Types.BIGINT));
		} else {
			insertUpdate(connection,
					SQLConfiguraciones.UPDATE_NOMBRE_USER,
					ValueSQL.get(usuario.getNombre(), Types.VARCHAR),
					ValueSQL.get(usuario.getId(), Types.BIGINT));
		}
	}

	/**
	 * Metodo que permite soportar el proceso de modificar la clave de ingreso
	 *
	 * @param datos, DTO que contiene los datos para el proceso de la modificacion
	 * @param securityPostPass, se utiliza para encriptar las claves
	 */
	public void modificarClaveIngreso(
			CambioClaveDTO datos,
			String securityPostPass,
			Connection connection) throws Exception {

		// se obtiene los recursos necesarios para el proceso
		String nuevaClave = datos.getClaveNueva();
		Long idUsuario = datos.getIdUsuario();
		EstrategiaCriptografica criptografica = EstrategiaCriptografica.get();

		// se verifica si La contrasenia de verificación coincide
		if (!nuevaClave.equals(datos.getClaveVerificacion())) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_VERIFICACION_NO_COINCIDE.value);
		}

		// la nueva contrasenia debe tener minimo la cantidad permitida
		if (nuevaClave.length() < Numero.DOCE.value) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_LONGITUD_NO_PERMITIDA.value);
		}

		// la nueva contrasenia no puede contener espacios en blanco
		if (nuevaClave.indexOf(' ') != -1) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_ESPACIOS_BLANCO.value);
		}

		// se verifica si la contrasenia actual coincide con la clave del usuario
		String claveUser = (String) find(connection,
				SQLConfiguraciones.GET_CLAVE_INGRESO,
				MapperJDBC.get(Mapper.GET_SOLO_UN_STRING),
				ValueSQL.get(idUsuario, Types.BIGINT));
		String claveActualMD5 = criptografica.encriptarPassword(datos.getClaveActual(), securityPostPass);
		if (!claveUser.equals(claveActualMD5)) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_NO_COINCIDE.value);
		}

		// se verifica que la clave nueva no sea igual a la clave del usuario
		String nuevaClaveMD5 = criptografica.encriptarPassword(nuevaClave, securityPostPass);
		if (nuevaClaveMD5.equals(claveUser)) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_ACTUAL_IGUAL.value);
		}

		// actualiza la clave de ingreso en la BD
		insertUpdate(connection,
				SQLConfiguraciones.ACTUALIZAR_CLAVE_INGRESO,
				ValueSQL.get(nuevaClaveMD5, Types.VARCHAR),
				ValueSQL.get(idUsuario, Types.BIGINT));
	}

	/**
	 * Metodo que permite soportar el proceso de negocio para
	 * la creacion del campo de entrada de informacion
	 *
	 * @param campo, DTO que contiene los datos del nuevo campo de entrada
	 * @return DTO con los datos del nuevo campo de entrada
	 */
	public CampoEntradaDTO crearCampoEntrada(CampoEntradaDTO campo, Connection connection) throws Exception {
		// se obtiene los datos basico del campo
		Integer tipoCampo = campo.getTipoCampo();
		String nombre = campo.getNombre();
		Long idCliente = campo.getIdCliente();

		// bloque para la creacion del campo de entrada informacion
		try {
			connection.setAutoCommit(false);

			// se procede a crear el campo de entrada informacion
			insertUpdate(connection,
					SQLConfiguraciones.INSERTAR_CAMPO_ENTRADA,
					ValueSQL.get(idCliente, Types.BIGINT),
					ValueSQL.get(tipoCampo, Types.INTEGER),
					ValueSQL.get(nombre, Types.VARCHAR),
					ValueSQL.get(campo.getDescripcion(), Types.VARCHAR));

			// se obtiene el identificador del campo creado
			Long idCampo = (Long) find(connection,
					SQLConfiguraciones.GET_ID_CAMPO_ENTRADA,
					MapperJDBC.get(Mapper.GET_ID),
					ValueSQL.get(tipoCampo, Types.INTEGER),
					ValueSQL.get(nombre, Types.VARCHAR),
					ValueSQL.get(idCliente, Types.BIGINT));
			String idCampoString = idCampo.toString();

			// se utiliza para las inserciones para las restricciones e items si aplica
			List<String> dmls = new ArrayList<>();

			// restriciones para el nuevo campo
			List<RestriccionDTO> restricciones = campo.getRestricciones();
			if (restricciones != null && !restricciones.isEmpty()) {
				for (RestriccionDTO restriccion : restricciones) {
					dmls.add(SQLConfiguraciones.INSERTAR_RESTRICCIONES_CAMPO.
							replace(CommonConstant.INTERROGACION_1, idCampoString).
							replace(CommonConstant.INTERROGACION_2, restriccion.getId().toString()));
				}
			}

			// items para el nuevo campo solo si es lista desplegable
			if (campo.getTipoCampo().equals(TipoCampo.LISTA_DESPLEGABLE.id)) {
				List<ItemDTO> items = campo.getItems();
				if (items != null && !items.isEmpty()) {
					for (ItemDTO item : items) {
						dmls.add(SQLConfiguraciones.INSERTAR_SELECT_ITEMS.
								replace(CommonConstant.INTERROGACION_1, idCampoString).
								replace(CommonConstant.INTERROGACION_2, item.getValor()));
					}
				}
			}

			// se ejecuta las insercciones para los items y restricciones
			if (!dmls.isEmpty()) {
				batchSinInjection(connection, dmls);
			}
			connection.commit();

			// se configura el DTO de retorno
			CampoEntradaDTO resultado = new CampoEntradaDTO();
			resultado.setId(idCampo);
			resultado.setNombre(nombre);
			resultado.setTipoCampo(tipoCampo);
			resultado.setTipoCampoNombre(Util.getTipoCampoNombre(tipoCampo));
			return resultado;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite obtener los campos de entrada de informacion asociado a un cliente
	 *
	 * @param idCliente, identificador del cliente que le pertenece los campos de entrada
	 * @return lista DTO con la informacion de los campos de entrada
	 */
	@SuppressWarnings("unchecked")
	public List<CampoEntradaDTO> getCamposEntrada(Long idCliente, Connection connection) throws Exception {
		return (List<CampoEntradaDTO>) find(connection,
				SQLConfiguraciones.GET_CAMPOS_ENTRADA_INFORMACION,
				MapperJDBC.get(Mapper.GET_CAMPOS_ENTRADA),
				ValueSQL.get(idCliente, Types.BIGINT));
	}

	/**
	 * Metodo que permite obtener el detalle de un campo de entrada de informacion
	 *
	 * @param idCampo, identificador del campo de entrada informacion
	 * @return DTO con los datos del campo de entrada de informacion
	 */
	@SuppressWarnings("unchecked")
	public CampoEntradaDTO getDetalleCampoEntrada(Long idCampo, Connection connection) throws Exception {
		// se consulta los datos del campo con sus restricciones
		CampoEntradaDTO campo = (CampoEntradaDTO) find(connection,
				SQLConfiguraciones.GET_DETALLE_CAMPO_ENTRADA,
				MapperJDBC.get(Mapper.GET_DETALLE_CAMPO_ENTRADA),
				ValueSQL.get(idCampo, Types.BIGINT));

		// se consulta los items si el campo es una lista desplegable
		if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getTipoCampo())) {
			campo.setItems((List<ItemDTO>)find(connection,
							SQLConfiguraciones.GET_ITEMS,
							MapperJDBC.get(Mapper.GET_ITEMS),
							ValueSQL.get(idCampo, Types.BIGINT)));
		}
		return campo;
	}

	/**
	 * Metodo que soporta el proceso de negocio para la eliminacion de un campo de entrada
	 * 
	 * @param idCampo, identificador del campo de entrada
	 */
	public void eliminarCampoEntrada(Long idCampo, Connection connection) throws Exception {
		// se verifica que no exista una nomenclatura asociada al campo
		Long count = (Long) find(connection,
				SQLConfiguraciones.COUNT_CAMPO_NOMENCLATURA_ASOCIADA,
				MapperJDBC.get(Mapper.COUNT),
				ValueSQL.get(idCampo, Types.BIGINT));

		// si existe una nomenclatura asociada no se PUEDE seguir con el proceso
		if (!count.equals(Numero.ZERO.value.longValue())) {
			throw new BusinessException(MessagesKey.KEY_DELETE_CAMPO_NOMENCLATURA_ASOCIADA.value);
		}

		// bloque para la eliminacion del campo
		try {
			connection.setAutoCommit(false);
			String id = idCampo.toString();

			// lista para enviar al batch de eliminacion
			List<String> deletes = new ArrayList<>();

			// SQL para eliminar las restricciones
			deletes.add(SQLConfiguraciones.DELETE_CAMPO_RESTRICCIONES.replace(CommonConstant.INTERROGACION, id));

			// SQL para eliminar los items
			deletes.add(SQLConfiguraciones.DELETE_CAMPO_ITEMS.replace(CommonConstant.INTERROGACION, id));

			// SQL para eliminar el campo de entrada
			deletes.add(SQLConfiguraciones.DELETE_CAMPO_ENTRADA.replace(CommonConstant.INTERROGACION, id));

			// se ejecuta el batch y se confirman los cambios
			batchSinInjection(connection, deletes);
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite obtener el detalle de un campo de entrada para edicion
	 *
	 * @param idCampo, identificador del campo de entrada a editar
	 * @return DTO con los datos del campo de entrada de informacion a editar
	 */
	@SuppressWarnings("unchecked")
	public CampoEntradaEdicionDTO getDetalleCampoEntradaEdicion(Long idCampo, Connection connection) throws Exception {
		// se consulta los datos del campo de entrada a editar
		CampoEntradaEdicionDTO campo = (CampoEntradaEdicionDTO) find(connection,
				SQLConfiguraciones.GET_DETALLE_CAMPO_EDITAR,
				MapperJDBC.get(Mapper.GET_DETALLE_CAMPO_EDITAR),
				ValueSQL.get(idCampo, Types.BIGINT));

		// se obtiene todas las restricciones por el tipo de campo
		List<RestriccionDTO> restricciones = getRestricionesPorTipo(campo.getCampoEntrada().getTipoCampo(), connection);
		campo.getCampoEntrada().setRestricciones(restricciones);

		// si tiene restricciones se procede a consultarlas
		if (campo.isTieneRestricciones()) {
			List<RestriccionDTO> seleccionadas = (List<RestriccionDTO>) find(connection,
					SQLConfiguraciones.GET_RESTRICCIONES_EDICION,
					MapperJDBC.get(Mapper.GET_RESTRICCIONES_EDICION),
					ValueSQL.get(idCampo, Types.BIGINT));

			// se configuran las restricciones seleccionadas
			for (RestriccionDTO seleccionada: seleccionadas) {
				for (RestriccionDTO restriccion: restricciones) {
					if (seleccionada.getId().equals(restriccion.getId())) {
						restriccion.setAplica(true);
						break;
					}
				}
			}
		}

		// se consulta los items si el campo es una lista desplegable
		if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getCampoEntrada().getTipoCampo())) {
			campo.getCampoEntrada().setItems((List<ItemDTO>) find(connection,
					SQLConfiguraciones.GET_ITEMS,
					MapperJDBC.get(Mapper.GET_ITEMS),
					ValueSQL.get(idCampo, Types.BIGINT)));
		}
		return campo;
	}

	/**
	 * Metodo que permite editar un campo de entrada de informacion
	 *
	 * @param datos, DTO que contiene los datos a editar
	 * @return DTO con los datos basico del campo
	 */
	public CampoEntradaDTO editarCampoEntradaInformacion(
			CampoEntradaEdicionDTO datos,
			Connection connection) throws Exception {

		// lista que contiene los DMLS a ejecutar para la edicion
		List<String> dmls = new ArrayList<>();

		// se obtiene el campo de entrada a editar
		CampoEntradaDTO campoEditar = datos.getCampoEntrada();

		// ID del campo entrada, se utiliza para las sentencias
		String idCampoSQL = campoEditar.getId().toString();

		// ************* 01-ACTUALIZACION DE LOS DATOS BASICOS DEL CAMPO *************************
		if (datos.isDatosBasicosEditar()) {
			dmls.add(SQLConfiguraciones.UPDATE_CAMPO_DESCRIPCION_NOMBRE.
					replace(CommonConstant.INTERROGACION_1, campoEditar.getDescripcion()).
					replace(CommonConstant.INTERROGACION_2, campoEditar.getNombre()).
					replace(CommonConstant.INTERROGACION_3, idCampoSQL));
		}

		// ************* 02-ACTUALIZACION DE LAS RESTRICCIONES DEL CAMPO *************************
		if (datos.isRestriccionesEditar()) {

			// se eliminan todas las restricciones asociadas al campo,
			// no hay lio utilizar este delete dado que esta tabla no utiliza autoincrement
			dmls.add(SQLConfiguraciones.DELETE_CAMPO_RESTRICCIONES.replace(CommonConstant.INTERROGACION, idCampoSQL));

			// se recorre todas las restricciones y se agrega en la lista dmls
			List<RestriccionDTO> restricciones = campoEditar.getRestricciones();

			// un campo puede quedar sin restricciones
			if (restricciones != null && !restricciones.isEmpty()) {
				for (RestriccionDTO restriccion : restricciones) {
					dmls.add(SQLConfiguraciones.INSERTAR_RESTRICCIONES_CAMPO.
							replace(CommonConstant.INTERROGACION_1, idCampoSQL).
							replace(CommonConstant.INTERROGACION_2, restriccion.getId().toString()));
				}
			}
		}

		// ************* 03-ACTUALIZACION DE LOS ITEMS DEL CAMPO *********************************
		if (datos.isItemsEditar()) {

			// los items para la lista desplegable son obligatorios
			List<ItemDTO> items = campoEditar.getItems();

			// se recorre todos los items del campo
			for (ItemDTO item : items) {

				// si es creacion del ITEM
				if (item.getId() == null) {
					dmls.add(SQLConfiguraciones.INSERTAR_SELECT_ITEMS.
							replace(CommonConstant.INTERROGACION_1, idCampoSQL).
							replace(CommonConstant.INTERROGACION_2, item.getValor()));
				} else {
					// si es edicion del ITEM
					if (!item.isBorrar()) {
						dmls.add(SQLConfiguraciones.UPDATE_SELECT_ITEMS.
								replace(CommonConstant.INTERROGACION_1, item.getValor()).
								replace(CommonConstant.INTERROGACION_2, item.getId().toString()));
					} else {
						// si es borrar se debe validar que no existan consecutivos asociado al campo
						if (!datos.isTieneConsecutivos()) {
							dmls.add(SQLConfiguraciones.DELETE_SELECT_ITEMS.replace(CommonConstant.INTERROGACION, item.getId().toString()));
						}
					}
				}
			}
		}

		// si hay lista dmls se ejecuta el batch
		if (!dmls.isEmpty()) {
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

		// el proceso se ejecuto sin problemas si llega este punto
		CampoEntradaDTO resultado = new CampoEntradaDTO();
		resultado.setId(campoEditar.getId());
		resultado.setNombre(campoEditar.getNombre());
		resultado.setTipoCampo(campoEditar.getTipoCampo());
		resultado.setTipoCampoNombre(Util.getTipoCampoNombre(campoEditar.getTipoCampo()));
		return resultado;
	}

	/**
	 * Metodo que permite validar los datos de campo de entrada
	 * esto aplica para el primer paso al momento de crear o editar el campo
	 * 
	 * @param campo, contiene los datos del campo de entrada
	 * @return lista restricciones asociada al tipo de campo
	 */
	public List<RestriccionDTO> validarDatosCampoEntrada(
			CampoEntradaDTO campo,
			Connection connection) throws Exception {

		// se valida si el cliente ya tiene el mismo campo con el tipo y nombre
		validarCampoEntradaExistente(campo, connection);

		// si todo es OK se retorna las restricciones asociada al tipo de campo
		List<RestriccionDTO> restricciones = null;
		if (campo.isConsultarRestricciones()) {
			restricciones = getRestricionesPorTipo(campo.getTipoCampo(), connection);
		}
		return restricciones;
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

	/**
	 * Metodo que permite validar el usuario de ingreso al momento
	 * de crear el usuario o modificar la cuenta del usuario
	 *
	 * @param usuarioIngreso, valor del user de ingreso a validar
	 */
	private void validarUsuarioIngreso(
			String usuarioIngreso,
			Connection connection) throws Exception {

		// se verifica la longitud del usuario de ingreso
		if (usuarioIngreso == null  || usuarioIngreso.length() < Numero.DIEZ.value) {
			throw new BusinessException(MessagesKey.KEY_USER_INGRESO_LONGITUD_NO_PERMITIDA.value);
		}

		// el usuario de ingreso no puede contener espacios en blanco
		if (usuarioIngreso.indexOf(' ') != -1) {
			throw new BusinessException(MessagesKey.KEY_USER_INGRESO_ESPACIOS_BLANCO.value);
		}

		// se verifica que no exista un usuario de ingreso registrado en la BD
		Long count = (Long) find(connection,
				SQLConfiguraciones.COUNT_USUARIO_INGRESO,
				MapperJDBC.get(Mapper.COUNT),
				ValueSQL.get(usuarioIngreso, Types.VARCHAR));

		// si existe algun 'usuario de ingreso' registrado en la BD no se PUEDE seguir con el proceso
		if (!count.equals(Numero.ZERO.value.longValue())) {
			throw new BusinessException(MessagesKey.KEY_USUARIO_INGRESO_EXISTE.value);
		}
	}

	/**
	 * Metodo que permite obtener las restricciones asociados a un tipo de campo
	 *
	 * @param tipoCampo, es el valor del tipo de campo asociado a las restricciones
	 * @return Lista de restricciones parametrizadas en la BD
	 */
	@SuppressWarnings("unchecked")
	private List<RestriccionDTO> getRestricionesPorTipo(Integer tipoCampo, Connection connection) throws Exception {
		return (List<RestriccionDTO>) find(connection,
				SQLConfiguraciones.GET_RESTRICCIONES_CAMPO_INGRESO,
				MapperJDBC.get(Mapper.GET_RESTRICCIONES),
				ValueSQL.get(tipoCampo, Types.INTEGER));
	}

	/**
	 * Metodo que permite validar si el campo de entrada existe para el tipo, nombre y cliente
	 *
	 * @param campo, DTO que contiene los datos del nuevo campo de entrada
	 */
	private void validarCampoEntradaExistente(CampoEntradaDTO campo, Connection connection) throws Exception {
		// se verifica que no exista otro campo con el mismo tipo y nombre
		Long count = (Long) find(connection,
				SQLConfiguraciones.COUNT_EXISTE_CAMPO_ENTRADA,
				MapperJDBC.get(Mapper.COUNT),
				ValueSQL.get(campo.getTipoCampo(), Types.INTEGER),
				ValueSQL.get(campo.getNombre(), Types.VARCHAR),
				ValueSQL.get(campo.getIdCliente(), Types.BIGINT));

		// si existe otro campo con el mismo tipo y nombre no se PUEDE seguir con el proceso
		if (!count.equals(Numero.ZERO.value.longValue())) {
			throw new BusinessException(MessagesKey.KEY_EXISTE_CAMPO_ENTRADA.value);
		}
	}
}
