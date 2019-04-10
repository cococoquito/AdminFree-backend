package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.http.HttpStatus;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLConfiguraciones;
import adminfree.dtos.configuraciones.CambioClaveDTO;
import adminfree.dtos.configuraciones.CambioUsuarioIngresoDTO;
import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.CampoEntradaEdicionDTO;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.configuraciones.GenerarTokenIngresoDTO;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.configuraciones.ModificarCuentaUsuarioDTO;
import adminfree.dtos.configuraciones.NomenclaturaCampoDTO;
import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.dtos.configuraciones.NomenclaturaEdicionDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.dtos.configuraciones.UsuarioEdicionDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Estado;
import adminfree.enums.MessagesKey;
import adminfree.enums.Numero;
import adminfree.enums.TipoCampo;
import adminfree.mappers.MapperConfiguraciones;
import adminfree.mappers.MapperTransversal;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.ProceduresJDBC;
import adminfree.persistence.ValueSQL;
import adminfree.utilities.BusinessException;
import adminfree.utilities.EstrategiaCriptografica;
import adminfree.utilities.Util;

/**
 * Clase que contiene los procesos de negocio para el modulo de Configuraciones
 *
 * @author Carlos Andres Diaz
 *
 */
@SuppressWarnings("unchecked")
public class ConfiguracionesBusiness extends CommonDAO {

	/**
	 * Business que permite crear un cliente en el sistema
	 *
	 * @param cliente, DTO con los datos del cliente a crear
	 * @param securityPostPass, se utiliza para encriptar el token del cliente
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	public ClienteDTO crearCliente(
			ClienteDTO cliente,
			String securityPostPass,
			Connection connection) throws Exception {

		// se utilizan para guardar los valores del TOKEN y el TOKEN encriptado
		String token = null;
		String tokenEncriptada = null;
		ValueSQL tokenEncriptadaSQL = ValueSQL.get(null, Types.VARCHAR);

		// se utiliza para verificar si el TOKEN generado ya existe en otro usuario
		MapperTransversal mapperCount = MapperTransversal.get(MapperTransversal.COUNT);

		// se utiliza para la generacion y encriptacion del TOKEN
		EstrategiaCriptografica strategia = EstrategiaCriptografica.get();

		// se genera el TOKEN y este debe ser unico entre todos los clientes
		boolean tokenExiste = true;
		while (tokenExiste) {

			// se solicita un TOKEN y se procede a encriptarlo
			token = strategia.generarToken();
			tokenEncriptada = strategia.encriptarPassword(token, securityPostPass);

			// se utiliza para hacer el COUNT
			tokenEncriptadaSQL.setValor(tokenEncriptada);

			// se procede a contar los registros que contenga este TOKEN encriptado
			Long count = (Long) find(connection,
					SQLConfiguraciones.COUNT_CLIENTE_TOKEN,
					mapperCount,
					tokenEncriptadaSQL);

			// valida si el TOKEN es unico entre todos los clientes
			if (count.equals(Numero.ZERO.valueL)) {
				tokenExiste = false;
			}
		}

		// se procede a llamar el procedimiento para la creacion del cliente
		String respuesta = new ProceduresJDBC().crearCliente(cliente, tokenEncriptada, connection);

		// si es exitoso se procede retorna el cliente con sus datos registrados en el sistema
		if (HttpStatus.OK.getReasonPhrase().equals(respuesta)) {
			ClienteDTO nuevoCliente = (ClienteDTO) find(connection,
					SQLConfiguraciones.GET_CLIENTE_TOKEN,
					MapperConfiguraciones.get(MapperConfiguraciones.GET_CLIENTE),
					tokenEncriptadaSQL);
			nuevoCliente.getCredenciales().setToken(token);
			return nuevoCliente;
		}
		throw new Exception(respuesta);
	}

	/**
	 * Business para obtener todos los CLIENTES del sistema
	 *
	 * @return, lista de CLIENTES configurados
	 */	
	public List<ClienteDTO> listarClientes(Connection connection) throws Exception {
		return (List<ClienteDTO>) findAll(
				connection,
				SQLConfiguraciones.LISTAR_CLIENTES,
				MapperConfiguraciones.get(MapperConfiguraciones.GET_CLIENTES));
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
	public List<UsuarioDTO> getUsuariosCliente(ClienteDTO filtro, Connection connection) throws Exception {
		return (List<UsuarioDTO>) find(connection,
				SQLConfiguraciones.GET_USUARIOS_CLIENTE,
				MapperConfiguraciones.get(MapperConfiguraciones.GET_USUARIOS_CLIENTE),
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
					ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER),
					ValueSQL.get(usuario.getCargo(), Types.VARCHAR));

			// se obtiene el identificador del usuario creado
			Long idUsuario = (Long) find(connection,
					CommonConstant.LAST_INSERT_ID,
					MapperTransversal.get(MapperTransversal.GET_ID));

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
			nuevoUsuario.setCargo(usuario.getCargo());
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
			String idUser_ = usuario.getId().toString();

			// modificaciones para los datos basicos del usuario
			if (datos.isDatosBasicosEditar()) {
				insertUpdate(connection, SQLConfiguraciones.UPDATE_DATOS_CUENTA,
						ValueSQL.get(usuario.getNombre(), Types.VARCHAR),
						ValueSQL.get(usuario.getUsuarioIngreso(), Types.VARCHAR),
						ValueSQL.get(usuario.getCargo(), Types.VARCHAR),
						ValueSQL.get(usuario.getId(), Types.BIGINT));
			}

			// modificaciones de los modulos asignados para el usuario
			if (datos.isModulosEditar()) {
				List<String> dmls = new ArrayList<>();

				// DML para eliminar los privilegios asociados al usuario que llega por parametro
				// no hay lio utilizar este delete dado que esta tabla no utiliza autoincrement
				dmls.add(SQLConfiguraciones.getSQlDeletePrivilegiosUser(idUser_));

				// se configura los inserts de los nuevos privilegios asociado al usuario
				List<String> tokens = usuario.getModulosTokens();
				for (String token : tokens) {
					dmls.add(SQLConfiguraciones.getSQLInsertPrivilegiosUser(idUser_, token));
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
	 * Metodo que permite generar un nuevo TOKEN de ingreso
	 * para el usuario o cliente que llega por parametro
	 *
	 * @param parametro, DTO que contiene el id del cliente o usuario
	 * @param securityPostPass, se utiliza para encriptar la clave
	 * @return DTO con el TOKEN de ingreso generada
	 */
	public GenerarTokenIngresoDTO generarClaveIngreso(
			GenerarTokenIngresoDTO parametro,
			String securityPostPass,
			Connection connection) throws Exception {

		// se toma los identificadores de las entidades
		Integer idCliente = parametro.getIdCliente();
		Integer idUsuario = parametro.getIdUsuario();

		// debe existir alguno de los dos identificadores de las entidades
		if (idCliente != null || idUsuario != null) {

			// se utiliza para encriptar la clave de ingreso
			EstrategiaCriptografica criptografica = EstrategiaCriptografica.get();

			// se genera un nuevo TOKEN para el usuario o cliente
			String claveIngreso = criptografica.generarToken();

			// se encripta la nueva clave para ser almacenada en la BD
			String claveIngresoEncriptada = criptografica.encriptarPassword(claveIngreso, securityPostPass);

			// actualiza la nueva clave de ingreso en la BD dependiendo de la entidad
			if (idUsuario != null && !idUsuario.equals(Numero.ZERO.valueI)) {
				insertUpdate(connection,
						SQLConfiguraciones.ACTUALIZAR_TOKEN_USUARIO,
						ValueSQL.get(claveIngresoEncriptada, Types.VARCHAR),
						ValueSQL.get(idUsuario, Types.INTEGER));
			} else if (idCliente != null && !idCliente.equals(Numero.ZERO.valueI)) {
				insertUpdate(connection,
						SQLConfiguraciones.ACTUALIZAR_TOKEN_CLIENTE,
						ValueSQL.get(claveIngresoEncriptada, Types.VARCHAR),
						ValueSQL.get(idCliente, Types.INTEGER));
			}

			// se retorna el nuevo TOKEN generado
			GenerarTokenIngresoDTO nuevoToken = new GenerarTokenIngresoDTO();
			nuevoToken.setToken(claveIngreso);
			return nuevoToken;
		}
		return null;
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
					CommonConstant.LAST_INSERT_ID,
					MapperTransversal.get(MapperTransversal.GET_ID));
			String idCampo_ = idCampo.toString();

			// se utiliza para las inserciones de los items si aplica
			List<String> dmls = new ArrayList<>();

			// items para el nuevo campo solo si es lista desplegable
			if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getTipoCampo())) {
				List<ItemDTO> items = campo.getItems();
				if (items != null && !items.isEmpty()) {
					for (ItemDTO item : items) {
						dmls.add(SQLConfiguraciones.getSQLInsertSelectItems(idCampo_, item.getValor()));
					}
				}
			}

			// se ejecuta las insercciones para los items
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
	 * @param isRestriccion, 1=si se debe consultar las restricciones
	 * @return lista DTO con la informacion de los campos de entrada
	 */
	public List<CampoEntradaDTO> getCamposEntrada(Long idCliente, Integer isRestriccion, Connection connection) throws Exception {

		// se consultan los campos parametrizados en el sistema
		List<CampoEntradaDTO> campos = (List<CampoEntradaDTO>) find(connection,
				SQLConfiguraciones.GET_CAMPOS_ENTRADA_INFORMACION,
				MapperConfiguraciones.get(MapperConfiguraciones.GET_CAMPOS_ENTRADA),
				ValueSQL.get(idCliente, Types.BIGINT));

		// se verifica si se debe consultar las restricciones para cada campo
		if (Numero.UNO.valueI.equals(isRestriccion) && campos != null && !campos.isEmpty()) {

			// se consultan todas las restricciones que aplica para cada tipo de campo
			List<RestriccionDTO> restricciones = (List<RestriccionDTO>) find(connection,
					SQLConfiguraciones.GET_RESTRICCIONES,
					MapperConfiguraciones.get(MapperConfiguraciones.GET_RESTRICCIONES));

			// se configura las restricciones de cada campo parametrizado
			for (CampoEntradaDTO campo : campos) {
				for (RestriccionDTO restriccion : restricciones) {
					if (campo.getTipoCampo().equals(restriccion.getTipoCampo())) {
						campo.agregarRestriccion(restriccion);
					}
				}
			}
		}
		return campos;
	}

	/**
	 * Metodo que permite obtener el detalle del campo entrada
	 * de informacion asociado a una nomenclatura
	 *
	 * @param idNomenclatura, nomenclatura asociada al campo a consultar
	 * @param idCampo, identificador del campo de entrada informacion
	 * @return DTO con los datos del campo de entrada de informacion
	 */
	public CampoEntradaDTO getDetalleNomenclaturaCampo(Long idNomenclatura, Long idCampo, Connection connection) throws Exception {

		// se consulta los datos generales del campo asociado a la nomenclatura
		CampoEntradaDTO campo = (CampoEntradaDTO) find(connection,
				SQLConfiguraciones.GET_DETALLE_NOMENCLATURA_CAMPO,
				MapperConfiguraciones.get(MapperConfiguraciones.GET_DETALLE_NOMENCLATURA_CAMPO),
				ValueSQL.get(idNomenclatura, Types.BIGINT),
				ValueSQL.get(idCampo, Types.BIGINT));

		// se consulta los items si el campo es una lista desplegable
		if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getTipoCampo())) {
			campo.setItems((List<ItemDTO>)find(connection,
							SQLConfiguraciones.GET_ITEMS,
							MapperConfiguraciones.get(MapperConfiguraciones.GET_ITEMS),
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
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(idCampo, Types.BIGINT));

		// si existe una nomenclatura asociada no se PUEDE seguir con el proceso
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_DELETE_CAMPO_NOMENCLATURA_ASOCIADA.value);
		}

		// bloque para la eliminacion del campo
		try {
			connection.setAutoCommit(false);
			String idCampo_ = idCampo.toString();

			// lista para enviar al batch de eliminacion
			List<String> deletes = new ArrayList<>();

			// SQL para eliminar los items
			deletes.add(SQLConfiguraciones.getSQLDeleteCamposItems(idCampo_));

			// SQL para eliminar el campo de entrada
			deletes.add(SQLConfiguraciones.getSQLDeleteCampoEntrada(idCampo_));

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
	public CampoEntradaEdicionDTO getDetalleCampoEntradaEdicion(Long idCampo, Connection connection) throws Exception {

		// se consulta los datos del campo de entrada a editar
		CampoEntradaEdicionDTO campo = (CampoEntradaEdicionDTO) find(connection,
				SQLConfiguraciones.GET_DETALLE_CAMPO_EDITAR,
				MapperConfiguraciones.get(MapperConfiguraciones.GET_DETALLE_CAMPO_EDITAR),
				ValueSQL.get(idCampo, Types.BIGINT));

		// se consulta los items si el campo es una lista desplegable
		if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getCampoEntrada().getTipoCampo())) {
			campo.getCampoEntrada().setItems((List<ItemDTO>) find(connection,
					SQLConfiguraciones.GET_ITEMS,
					MapperConfiguraciones.get(MapperConfiguraciones.GET_ITEMS),
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
	public CampoEntradaDTO editarCampoEntrada(
			CampoEntradaEdicionDTO datos,
			Connection connection) throws Exception {

		// lista que contiene los DMLS a ejecutar para la edicion
		List<String> dmls = new ArrayList<>();

		// se obtiene el campo de entrada a editar
		CampoEntradaDTO campoEditar = datos.getCampoEntrada();

		// ID del campo entrada, se utiliza para las sentencias
		Long idCampo = campoEditar.getId();
		String idCampo_ = idCampo.toString();

		// ************* 01-ACTUALIZACION DE LOS DATOS BASICOS DEL CAMPO *************************
		if (datos.isDatosBasicosEditar()) {
			insertUpdate(connection,
					SQLConfiguraciones.UPDATE_CAMPO_DESCRIPCION_NOMBRE,
					ValueSQL.get(campoEditar.getDescripcion(), Types.VARCHAR),
					ValueSQL.get(campoEditar.getNombre(), Types.VARCHAR),
					ValueSQL.get(idCampo, Types.BIGINT));
		}

		// ************* 02-ACTUALIZACION DE LOS ITEMS DEL CAMPO *********************************
		if (datos.isItemsEditar()) {

			// los items para la lista desplegable son obligatorios
			List<ItemDTO> items = campoEditar.getItems();

			// se recorre todos los items del campo
			for (ItemDTO item : items) {

				// si es creacion del ITEM
				if (item.getId() == null) {
					dmls.add(SQLConfiguraciones.getSQLInsertSelectItems(idCampo_, item.getValor()));
				} else {
					// si es edicion del ITEM
					if (!item.isBorrar()) {
						dmls.add(SQLConfiguraciones.getSQLUpdateSelectItems(item.getValor(), item.getId().toString()));
					} else {
						// si es borrar se debe validar que no existan consecutivos asociado al campo
						if (!datos.isTieneConsecutivos()) {
							dmls.add(SQLConfiguraciones.getSQLDeleteSelectItems(item.getId().toString()));
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
		resultado.setId(idCampo);
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
	 */
	public void validarDatosCampoEntrada(CampoEntradaDTO campo, Connection connection) throws Exception {

		// se verifica que no exista otro campo con el mismo tipo y nombre
		Long count = (Long) find(connection,
				SQLConfiguraciones.COUNT_EXISTE_CAMPO_ENTRADA,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(campo.getTipoCampo(), Types.INTEGER),
				ValueSQL.get(campo.getNombre(), Types.VARCHAR),
				ValueSQL.get(campo.getIdCliente(), Types.BIGINT));

		// si existe otro campo con el mismo tipo y nombre no se PUEDE seguir con el proceso
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_EXISTE_CAMPO_ENTRADA.value);
		}
	}

	/**
	 * Metodo que permite obtener todas las nomenclaturas asociadas a un cliente
	 *
	 * @param idCliente, identificador del cliente quien le pertenece las nomenclaturas
	 * @return lista de nomenclaturas con sus atributos configuradas
	 */
	public List<NomenclaturaDTO> getNomenclaturas(Long idCliente, Connection connection) throws Exception {
		return (List<NomenclaturaDTO>) find(connection,
				SQLConfiguraciones.GET_NOMENCLATURAS,
				MapperConfiguraciones.get(MapperConfiguraciones.GET_NOMENCLATURAS),
				ValueSQL.get(idCliente, Types.BIGINT));
	}

	/**
	 * Metodo que permite validar si la nomenclatura ya existe en el sistema
	 *
	 * @param nomenclatura, DTO que contiene los datos para la validacion
	 * @throws Exception, se lanza si existe la nomenclatura parametrizada en el sistema
	 */
	public void validarExisteNomenclatura(NomenclaturaDTO nomenclatura, Connection connection) throws Exception {
		Long count = (Long) find(connection,
				SQLConfiguraciones.EXISTE_NOMENCLATURA,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(nomenclatura.getNomenclatura(), Types.VARCHAR),
				ValueSQL.get(nomenclatura.getIdCliente(), Types.BIGINT));
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_NOMENCLATURA_EXISTE.value);
		}
	}

	/**
	 * Metodo que permite crear una nomenclatura
	 *
	 * @param nomenclatura, contiene los datos de la creacion
	 * @return Nomenclatura con el identificador generado
	 */
	public NomenclaturaDTO crearNomenclatura(NomenclaturaDTO nomenclatura, Connection connection) throws Exception {
		try {
			connection.setAutoCommit(false);

			// se procede a insertar los datos de la nomenclatura
			insertUpdate(connection, SQLConfiguraciones.INSERT_NOMENCLATURA,
					ValueSQL.get(nomenclatura.getNomenclatura(), Types.VARCHAR),
					ValueSQL.get(nomenclatura.getDescripcion(), Types.VARCHAR),
					ValueSQL.get(nomenclatura.getIdCliente(), Types.BIGINT),
					ValueSQL.get(nomenclatura.getConsecutivoInicial(), Types.INTEGER));

			// se obtiene el identificador de la nomenclatura
			Long idNomenclatura = (Long) find(connection,
					CommonConstant.LAST_INSERT_ID,
					MapperTransversal.get(MapperTransversal.GET_ID));

			// se verifica si esta nomenclatura tiene campos asociados
			List<NomenclaturaCampoDTO> campos = nomenclatura.getCampos();
			if (campos != null && !campos.isEmpty()) {

				// se utiliza para insertar las restricciones asociadas a cada campo
				List<String> dmlsRestricciones = null;

				// se recorre todos los campos
				List<RestriccionDTO> restricciones;
				for (NomenclaturaCampoDTO campo : campos) {

					// se procede a insertar el campo para esta nomenclatura
					insertUpdate(connection,
							SQLConfiguraciones.INSERT_NOMENCLATURA_CAMPOS,
							ValueSQL.get(idNomenclatura, Types.BIGINT),
							ValueSQL.get(campo.getIdCampo(), Types.BIGINT),
							ValueSQL.get(campo.getOrden(), Types.INTEGER));

					// se verifica si este campo tiene restricciones
					restricciones = campo.getRestricciones();
					if (restricciones != null && !restricciones.isEmpty()) {

						// se obtiene el identificador de la nomenclatura campo
						String idNomenclaturaCampo = find(connection,
								CommonConstant.LAST_INSERT_ID,
								MapperTransversal.get(MapperTransversal.GET_ID)).toString();

						// se recorre cada restriccion
						for (RestriccionDTO restriccion : restricciones) {
							if (dmlsRestricciones == null) {
								dmlsRestricciones = new ArrayList<>();
							}
							dmlsRestricciones.add(SQLConfiguraciones.getSQLInsertRestriccion(idNomenclaturaCampo, restriccion.getId()));
						}
					}
				}

				// si hay INSERTs para las restricciones se procede a llamar el BATCH
				if (dmlsRestricciones != null && !dmlsRestricciones.isEmpty()) {
					batchSinInjection(connection, dmlsRestricciones);
				}
			}
			connection.commit();

			// se construye el resultado a retornar
			NomenclaturaDTO resultado = new NomenclaturaDTO();
			resultado.setId(idNomenclatura);
			resultado.setNomenclatura(nomenclatura.getNomenclatura());
			resultado.setDescripcion(nomenclatura.getDescripcion());
			resultado.setIdCliente(nomenclatura.getIdCliente());
			resultado.setConsecutivoInicial(nomenclatura.getConsecutivoInicial());
			return resultado;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite editar la nomenclatura
	 *
	 * @param datos, contiene los datos de la edicion
	 */
	public void editarNomenclatura(NomenclaturaEdicionDTO datos, Connection connection) throws Exception {
		try {
			connection.setAutoCommit(false);

			// se obtiene los datos de la nomenclatura
			NomenclaturaDTO nomenclatura = datos.getNomenclatura();

			// modificaciones para los datos basicos de la nomenclatura
			if (datos.isDatosBasicosEditar()) {
				insertUpdate(connection, SQLConfiguraciones.UPDATE_NOMENCLATURA,
						ValueSQL.get(nomenclatura.getNomenclatura(), Types.VARCHAR),
						ValueSQL.get(nomenclatura.getDescripcion(), Types.VARCHAR),
						ValueSQL.get(nomenclatura.getConsecutivoInicial(), Types.INTEGER),
						ValueSQL.get(nomenclatura.getId(), Types.BIGINT));
			}

			// modificaciones de los campos de entrada asociados a la nomenclatura
			if (datos.isCamposEntradaEditar()) {
				String idNomenclatura_ = nomenclatura.getId().toString();

				// se eliminan todas las restricciones y campos asociadas a la nomenclatura
				List<String> deletes = new ArrayList<>();
				boolean isTieneConsecutivoNull = true;
				deletes.add(SQLConfiguraciones.getSQLDeleteRestricciones(idNomenclatura_, isTieneConsecutivoNull));
				deletes.add(SQLConfiguraciones.getSQLDeleteCamposNomenclatura(idNomenclatura_, isTieneConsecutivoNull));
				batchSinInjection(connection, deletes);

				// se verifica si hay campos asociados a esta nomenclatura
				List<NomenclaturaCampoDTO> campos = nomenclatura.getCampos();
				if (campos != null && !campos.isEmpty()) {

					// se utiliza para insert restricciones o update orden
					List<String> dmls = new ArrayList<>();

					// se recorre cada campo
					String idNomenclaturaCampo = null;
					List<RestriccionDTO> restricciones;
					for (NomenclaturaCampoDTO campo : campos) {

						// si no tiene consecutivo se procede a INSERTAR el campo para esta nomenclatura
						if (!campo.isTieneConsecutivo()) {
							insertUpdate(connection,
									SQLConfiguraciones.INSERT_NOMENCLATURA_CAMPOS,
									ValueSQL.get(nomenclatura.getId(), Types.BIGINT),
									ValueSQL.get(campo.getIdCampo(), Types.BIGINT),
									ValueSQL.get(campo.getOrden(), Types.INTEGER));
						} else {
							// si tiene consecutivo solo se edita el orden
							idNomenclaturaCampo = campo.getId().toString();
							dmls.add(SQLConfiguraciones.getSQLUpdateOrdenCampos(campo.getOrden().toString(), idNomenclaturaCampo));
						}

						// se verifica si hay restricciones para este campo
						restricciones = campo.getRestricciones();
						if (restricciones != null && !restricciones.isEmpty()) {

							// se define el identificador de la nomenclatura campo
							if (!campo.isTieneConsecutivo()) {
								idNomenclaturaCampo = find(connection,
										CommonConstant.LAST_INSERT_ID,
										MapperTransversal.get(MapperTransversal.GET_ID)).toString();
							}

							// se configura los INSERTs de las restricciones
							for (RestriccionDTO restriccion : restricciones) {
								dmls.add(SQLConfiguraciones.getSQLInsertRestriccion(idNomenclaturaCampo, restriccion.getId()));
							}
						}
					}
					batchSinInjection(connection, dmls);
				}
			}
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite eliminar una nomenclatura del sistema
	 *
	 * @param idNomenclatura, identificador de la nomenclatura
	 */
	public void eliminarNomenclatura(Long idNomenclatura, Connection connection) throws Exception {

		// se verifica que no exista un consecutivo asociada a la nomenclatura
		Long secuencia = (Long) find(connection,
				SQLConfiguraciones.GET_SECUENCIA_NOMENCLATURA,
				MapperTransversal.get(MapperTransversal.GET_ID),
				ValueSQL.get(idNomenclatura, Types.BIGINT));
		if (secuencia != null && !secuencia.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_DELETE_NOMENCLATURA_CONSECUTIVO_ASOCIADA.value);
		}

		// bloque para la eliminacion del campo
		try {
			connection.setAutoCommit(false);
			String idNomenclatura_ = idNomenclatura.toString();

			// lista para enviar al batch de eliminacion
			List<String> deletes = new ArrayList<>();
			boolean isTieneConsecutivoNull = false;

			// SQL para eliminar las restricciones
			deletes.add(SQLConfiguraciones.getSQLDeleteRestricciones(idNomenclatura_, isTieneConsecutivoNull));

			// SQL para eliminar los campos asociados
			deletes.add(SQLConfiguraciones.getSQLDeleteCamposNomenclatura(idNomenclatura_, isTieneConsecutivoNull));

			// SQL para eliminar la nomenclaura
			deletes.add(SQLConfiguraciones.getSQLDeleteNomenclatura(idNomenclatura_));

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
	 * Metodo que permite consultar el detalle de la nomenclatura
	 *
	 * @param idNomenclatura, identificador de la nomenclatura 
	 * @return, DTO con el detalle de la nomenclatura
	 */
	public NomenclaturaDTO getDetalleNomenclatura(Long idNomenclatura, Connection connection) throws Exception {
		return (NomenclaturaDTO) find(connection,
				SQLConfiguraciones.GET_DETALLE_NOMENCLATURA,
				MapperConfiguraciones.get(MapperConfiguraciones.GET_DETALLE_NOMENCLATURA),
				ValueSQL.get(idNomenclatura, Types.BIGINT));
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
		if (usuarioIngreso == null  || usuarioIngreso.length() < Numero.DIEZ.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_USER_INGRESO_LONGITUD_NO_PERMITIDA.value);
		}

		// el usuario de ingreso no puede contener espacios en blanco
		if (usuarioIngreso.indexOf(' ') != -1) {
			throw new BusinessException(MessagesKey.KEY_USER_INGRESO_ESPACIOS_BLANCO.value);
		}

		// se verifica que no exista un usuario de ingreso igual en la BD
		Long count = (Long) find(connection,
				SQLConfiguraciones.COUNT_USUARIO_INGRESO,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(usuarioIngreso, Types.VARCHAR));

		// si existe otro 'usuario de ingreso' igual registrado en la BD no se PUEDE seguir con el proceso
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_USUARIO_INGRESO_EXISTE.value);
		}
	}

	/**
	 * Metodo que permite procesar la funcionalidad de negocio de modificacion
	 * cuenta del usuario aplica para datos personales, cambio clave o usuario de ingreso
	 *
	 * @param params, contiene los DTOs para las modificaciones correspondiente
	 * @param securityPostPass, se utiliza para el cambio de clave o usuario ingreso
	 */
	public void modificarCuentaUsuario(ModificarCuentaUsuarioDTO params, String securityPostPass, Connection connection) throws Exception {

		// se obtiene los tres DTOs para las modificaciones
		UsuarioDTO datosPersonales = params.getDatosPersonales();
		CambioClaveDTO cambioClave = params.getCambioClave();
		CambioUsuarioIngresoDTO cambioUsuario = params.getCambioUsuario();

		// se valida si se debe modificar los datos personales
		if (datosPersonales != null &&
			datosPersonales.getId() != null &&
			!Numero.ZERO.valueL.equals(datosPersonales.getId())) {
			modificarDatosPersonales(datosPersonales, connection);
		}

		// se valida si se debe modificar la clave de ingreso
		if (cambioClave != null &&
			cambioClave.getIdUsuario() != null &&
			!Numero.ZERO.valueL.equals(cambioClave.getIdUsuario())) {
			modificarClave(cambioClave, securityPostPass, connection);
		}

		// se valida si se debe modificar el usuario de ingreso
		if (cambioUsuario != null &&
			cambioUsuario.getIdUsuario() != null &&
			!Numero.ZERO.valueL.equals(cambioUsuario.getIdUsuario())) {
			modificarUsuarioIngreso(cambioUsuario, securityPostPass, connection);
		}
	}

	/**
	 * Metodo que permite modificar los datos personales del usuario
	 * @param datosPersonales, DTO que contiene los datos para el proceso
	 */
	private void modificarDatosPersonales(UsuarioDTO datosPersonales, Connection connection) throws Exception {

		// se procede actualizar los datos personales del usuario
		int resultado = insertUpdate(connection,
				SQLConfiguraciones.UPDATE_DATOS_PERSONALES,
				ValueSQL.get(datosPersonales.getNombre(), Types.VARCHAR),
				ValueSQL.get(datosPersonales.getCargo(), Types.VARCHAR),
				ValueSQL.get(datosPersonales.getId(), Types.BIGINT));

		// se verifica si la actualizacion se proceso sin problemas
		if (resultado <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite modificar el usuario de ingreso al sistema
	 *
	 * @param cambioUsuario, DTO que contiene los datos para el proceso
	 * @param securityPostPass, se utiliza para encriptar las claves
	 */
	private void modificarUsuarioIngreso(
			CambioUsuarioIngresoDTO cambioUsuario,
			String securityPostPass,
			Connection connection) throws Exception {

		// se obtiene los valores para el proceso
		String userIngreso = cambioUsuario.getUsuario();
		Long idUsuario = cambioUsuario.getIdUsuario();

		// se aplica las validaciones de negocio para el usuario de ingreso
		validarUsuarioIngreso(userIngreso, connection);

		// se verifica si la contrasenia actual coincide con la clave del usuario
		String claveUser = (String) find(connection,
				SQLConfiguraciones.GET_CLAVE_INGRESO,
				MapperTransversal.get(MapperTransversal.GET_SOLO_UN_STRING),
				ValueSQL.get(idUsuario, Types.BIGINT));
		String claveActualMD5 = EstrategiaCriptografica.get().encriptarPassword(cambioUsuario.getClaveActual(), securityPostPass);
		if (!claveUser.equals(claveActualMD5)) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_NO_COINCIDE.value);
		}

		// se procede actualizar el usuario de ingreso
		int resultado = insertUpdate(connection,
				SQLConfiguraciones.ACTUALIZAR_USUARIO_INGRESO,
				ValueSQL.get(userIngreso, Types.VARCHAR),
				ValueSQL.get(idUsuario, Types.BIGINT));

		// se verifica si la actualizacion se proceso sin problemas
		if (resultado <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite modificar la clave de ingreso al sistema
	 *
	 * @param datos, DTO que contiene los datos para el proceso
	 * @param securityPostPass, se utiliza para encriptar las claves
	 */
	private void modificarClave(CambioClaveDTO datos, String securityPostPass, Connection connection) throws Exception {

		// se obtiene los recursos necesarios para el proceso
		String nuevaClave = datos.getClaveNueva();
		Long idUsuario = datos.getIdUsuario();
		EstrategiaCriptografica criptografica = EstrategiaCriptografica.get();

		// se verifica si la contrasenia de verificación coincide
		if (!nuevaClave.equals(datos.getClaveVerificacion())) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_VERIFICACION_NO_COINCIDE.value);
		}

		// la nueva contrasenia debe tener minimo la cantidad permitida
		if (nuevaClave.length() < Numero.DOCE.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_LONGITUD_NO_PERMITIDA.value);
		}

		// la nueva contrasenia no puede contener espacios en blanco
		if (nuevaClave.indexOf(' ') != -1) {
			throw new BusinessException(MessagesKey.KEY_CLAVE_ESPACIOS_BLANCO.value);
		}

		// se verifica si la contrasenia actual coincide con la clave del usuario
		String claveUser = (String) find(connection,
				SQLConfiguraciones.GET_CLAVE_INGRESO,
				MapperTransversal.get(MapperTransversal.GET_SOLO_UN_STRING),
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

		// se actualiza la clave de ingreso en la BD
		int resultado = insertUpdate(connection,
				SQLConfiguraciones.ACTUALIZAR_TOKEN_USUARIO,
				ValueSQL.get(nuevaClaveMD5, Types.VARCHAR),
				ValueSQL.get(idUsuario, Types.BIGINT));

		// se verifica si la actualizacion se proceso sin problemas
		if (resultado <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.PROCESO_NO_EJECUTADO.value);
		}
	}
}
