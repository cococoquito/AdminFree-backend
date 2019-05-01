package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adminfree.aws.AdministracionDocumentosS3;
import adminfree.constants.BusinessMessages;
import adminfree.constants.CommonConstant;
import adminfree.constants.SQLCorrespondencia;
import adminfree.constants.SQLTransversal;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.correspondencia.ActivarAnularConsecutivoDTO;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.CampoFiltroDTO;
import adminfree.dtos.correspondencia.ConsecutivoDTO;
import adminfree.dtos.correspondencia.ConsecutivoDetalleDTO;
import adminfree.dtos.correspondencia.ConsecutivoEdicionDTO;
import adminfree.dtos.correspondencia.ConsecutivoEdicionValueDTO;
import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.dtos.correspondencia.FiltroConsecutivosDTO;
import adminfree.dtos.correspondencia.InitConsecutivosAnioActualDTO;
import adminfree.dtos.correspondencia.InitMisConsecutivosDTO;
import adminfree.dtos.correspondencia.InitSolicitarConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoResponseDTO;
import adminfree.dtos.correspondencia.TransferenciaDTO;
import adminfree.dtos.correspondencia.TransferirConsecutivoDTO;
import adminfree.dtos.correspondencia.WelcomeInitDTO;
import adminfree.dtos.correspondencia.WelcomeNomenclaturaDTO;
import adminfree.dtos.correspondencia.WelcomeUsuarioDTO;
import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.dtos.transversal.SelectItemDTO;
import adminfree.enums.Estado;
import adminfree.enums.MessagesKey;
import adminfree.enums.Numero;
import adminfree.enums.TipoCampo;
import adminfree.mappers.MapperCorrespondencia;
import adminfree.mappers.MapperTransversal;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.ValueSQL;
import adminfree.utilities.BusinessException;
import adminfree.utilities.Util;

/**
 * Clase que contiene los procesos de negocio para el modulo de Correspondencia
 *
 * @author Carlos Andres Diaz
 *
 */
@SuppressWarnings("unchecked")
public class CorrespondenciaBusiness extends CommonDAO {

	/**
	 * Metodo que permite obtener los campos de la nomenclatura
	 *
	 * @param idNomenclatura, identificador de la nomenclatura
	 * @return DTO con los campos de la nomenclatura
	 */
	public List<CampoEntradaDetalleDTO> getCamposNomenclatura(Long idNomenclatura, Connection connection) throws Exception {

		// se obtiene los campos asociados a la nomenclatura
		List<CampoEntradaDetalleDTO> resultado = (List<CampoEntradaDetalleDTO>) find(connection,
				SQLCorrespondencia.GET_DTL_NOMENCLATURA_CAMPOS,
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DTL_NOMENCLATURA_CAMPOS),
				ValueSQL.get(idNomenclatura, Types.BIGINT));

		// se recorre todos los campos en busqueda de los select-items
		if (resultado != null && !resultado.isEmpty()) {

			// se obtiene los IDs de los campos tipo SELECT-ITEMS
			List<Long> idsCampos = null;
			for (CampoEntradaDetalleDTO campo : resultado) {
				if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getTipoCampo())) {
					if (idsCampos == null) {
						idsCampos = new ArrayList<>();
					}
					idsCampos.add(campo.getId());
				}
			}

			// si hay IDs de SELECT-ITEMS
			if (idsCampos != null) {

				// se procede a consultar los items de todos los SELECT-ITEMS
				List<ItemDTO> items = getItemsSelectFiltro(idsCampos, connection);

				// solo aplica si hay items consultados
				if (items != null && !items.isEmpty()) {

					// se busca el SELECT-ITEM que le pertenece a este item 
					for (ItemDTO item : items) {
						for (CampoEntradaDetalleDTO campo : resultado) {
							if (item.getIdCampo().equals(campo.getId())) {
								campo.agregarItem(item);
								break;
							}
						}
					}
				}
			}
		}
		return resultado;
	}

	/**
	 * Metodo que permite obtener los datos iniciales para las
	 * solicitudes de consecutivos de correspondencia
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos iniciales
	 */
	public InitSolicitarConsecutivoDTO getInitSolicitarConsecutivo(Long idCliente, Connection connection) throws Exception {

		// se configura el DTO de retorno
		InitSolicitarConsecutivoDTO init = new InitSolicitarConsecutivoDTO();
		init.setFechaActual(Calendar.getInstance().getTime());

		// se configura las nomenclaturas de acuerdo al cliente
		init.setNomenclaturas(new ConfiguracionesBusiness().getNomenclaturas(idCliente, connection));
		return init;
	}

	/**
	 * Metodo que permite validar los campos de ingreso de informacion para
	 * el proceso de solicitar o editar un consecutivo de correspondencia
	 *
	 * @param solicitud, DTO con los datos de la solicitud
	 * @return Lista de mensajes con los errores encontrados solo si lo hay
	 */
	public List<MessageResponseDTO> validarCamposIngresoInformacion(
			SolicitudConsecutivoDTO solicitud,
			Connection connection) throws Exception {

		// contiene el valor a retornar
		List<MessageResponseDTO> response = null;

		// se obtiene los valores a validar
		List<CampoEntradaValueDTO> valores = solicitud.getValores();

		// para este proceso los valores son obligatorios
		if (valores != null && !valores.isEmpty()) {

			// se utiliza para la concatenacion de las consultas
			String idCliente_ = solicitud.getIdCliente().toString();
			String idNomenclatura_ = solicitud.getIdNomenclatura().toString();

			// son los parametros de las consultas sql count
			ValueSQL idCampoSQL = ValueSQL.get(null, Types.BIGINT);
			ValueSQL valueSQL = ValueSQL.get(null, Types.VARCHAR);

			// variables que se utilizan dentro del for
			List<String> restricciones;
			String isExistsSQL;
			String value_;

			// se recorre cada valor a validar
			for (CampoEntradaValueDTO valor : valores) {
				restricciones = valor.getRestricciones();

				// se verifica que el campo asociado al valor si tenga restricciones
				if (restricciones != null && !restricciones.isEmpty()) {
					isExistsSQL = null;

					// se configura el tipo de campo y el value
					idCampoSQL.setValor(valor.getIdCampo());
					value_ = valor.getValue().toString();
					valueSQL.setValor(value_);

					// dependiendo de la restriccion se configura SQL y parametros para el EXISTS
					if (restricciones.contains(CommonConstant.KEY_CAMPO_UNICO_NOMENCLATURA)) {
						isExistsSQL = SQLCorrespondencia.isExistsValor(idCliente_, idNomenclatura_);
					} else if (restricciones.contains(CommonConstant.KEY_CAMPO_TODAS_NOMENCLATURA)) {
						isExistsSQL = SQLCorrespondencia.isExistsValor(idCliente_, null);
					}

					// se verifica si hay SQL a procesar
					if (isExistsSQL != null) {

						// se verifica si existe otro valor igual
						if ((boolean) find(connection, isExistsSQL,
								MapperTransversal.get(MapperTransversal.IS_EXISTS),
								idCampoSQL, valueSQL)) {

							// se agrega el mensaje de error en la lista a retornar
							response = (response == null) ? new ArrayList<>() : response;
							response.add(new MessageResponseDTO(
									BusinessMessages.getMsjValorExisteOtroConsecutivo(value_, valor.getNombreCampo())));
						}
					}
				}
			}
		}
		return response;
	}

	/**
	 * Metodo que permite soportar el proceso de negocio de solicitar
	 * un consecutivo de correspondencia para una nomenclatura
	 *
	 * @param solicitud, DTO que contiene los datos de la solicitud
	 * @return DTO con los datos de la respuesta
	 */
	public SolicitudConsecutivoResponseDTO solicitarConsecutivo(
			SolicitudConsecutivoDTO solicitud,
			Connection connection) throws Exception {
		try {
			// para este proceso debe estar bajo transaccionalidad
			connection.setAutoCommit(false);

			// identificadores que se utiliza para varios procesos
			Long idNomenclatura = solicitud.getIdNomenclatura();
			Long idCliente = solicitud.getIdCliente();
			Long idUsuario = solicitud.getIdUsuario();
			idUsuario = (idUsuario != null && idUsuario.longValue() > Numero.ZERO.valueL.longValue()) ? idUsuario : null;
			String idCliente_ = idCliente.toString();
			String idNomenclatura_ = idNomenclatura.toString();

			// se procede a consultar la secuencia actual de la nomenclatura
			List<Integer> respuesta = (List<Integer>) find(connection,
					SQLCorrespondencia.GET_SECUENCIA_NOMENCLATURA,
					MapperCorrespondencia.get(MapperCorrespondencia.GET_SECUENCIA_NOMENCLATURA),
					ValueSQL.get(idNomenclatura, Types.BIGINT));

			// se configura el nro inicial y la secuencia actual de la nomenclatura consultada
			Integer nroInicial = respuesta.get(Numero.ZERO.valueI);
			Integer nroSecuencia = respuesta.get(Numero.UNO.valueI);
			Integer nroSolicitadosNomen = respuesta.get(Numero.DOS.valueI);

			// se establece el nuevo consecutivo para la nomenclatura
			if (nroSecuencia != null && !nroSecuencia.equals(Numero.ZERO.valueI)) {
				nroSecuencia = nroSecuencia + Numero.UNO.valueI;
			} else {
				nroSecuencia = nroInicial;
			}

			// se configura el formato de la nueva secuencia
			String secuenciaFormato = Util.setPrefijoZeros(CommonConstant.PREFIJO_ZEROS_4, nroSecuencia.toString());

			// se realiza el insert de la tabla padre CONSECUTIVOS_ID_CLIENTE
			insertUpdate(connection,
					SQLCorrespondencia.getInsertConsecutivo(idCliente_),
					ValueSQL.get(idNomenclatura, Types.BIGINT),
					ValueSQL.get(secuenciaFormato, Types.VARCHAR),
					ValueSQL.get(idUsuario, Types.BIGINT));

			// se obtiene el identificador del consecutivo creado
			Long idConsecutivo = (Long) find(connection,
					CommonConstant.LAST_INSERT_ID,
					MapperTransversal.get(MapperTransversal.GET_ID));

			// si hay valores para este consecutivo se procede a insertarlo
			List<CampoEntradaValueDTO> valores = solicitud.getValores();
			if (valores != null && !valores.isEmpty()) {

				// se construye la lista de injections para la insercion
				List<List<ValueSQL>> injections = new ArrayList<>();

				// este valor es general para todos los valores
				ValueSQL valueIdConsecutivo = ValueSQL.get(idConsecutivo, Types.BIGINT);

				// se recorre cada valor para construir el INSERT
				List<ValueSQL> values;
				for (CampoEntradaValueDTO value : valores) {
					values = new ArrayList<>();

					// identificador de la tabla padre
					values.add(valueIdConsecutivo);

					// identificador del id del la tabla NOMENCLATURAS_CAMPOS_ENTRADA
					values.add(ValueSQL.get(value.getIdCampoNomenclatura(), Types.BIGINT));

					// se configura el valor del campo
					if (value.getValue() != null) {
						values.add(ValueSQL.get(value.getValue().toString(), Types.VARCHAR));
					} else {
						values.add(ValueSQL.get(null, Types.VARCHAR));
					}

					// se agrega los valores para este INSERT
					injections.add(values);
				}

				// se inserta los valores del consecutivo
				batchConInjection(connection, SQLCorrespondencia.getInsertConsecutivoValues(idCliente_), injections);
			}

			// Lista para la ejecucion de los dmls por batch sin injection
			List<String> dmls = new ArrayList<>();

			// se configura la cantidad de consecutivos solicitados para el usuario,
			// si el usuario es null esto significa que el administrador es de la solicitud
			if (idUsuario != null) {

				// se consulta la cantidad de consecutivos que tiene el usuario
				Long cantidad = (Long) find(connection,
						SQLCorrespondencia.GET_CANT_CONSECUTIVOS_USER,
						MapperTransversal.get(MapperTransversal.GET_ID),
						ValueSQL.get(idUsuario, Types.BIGINT));

				// se configura el UPDATE para ser ejecutado en el batch 
				final Long UNO = Numero.UNO.valueL;
				cantidad = cantidad != null ? cantidad + UNO : UNO;
				dmls.add(SQLCorrespondencia.getUpdateUsuarioCantidadConsecutivos(idUsuario.toString(), cantidad.toString()));
			}

			// se configura la cantidad de consecutivos solicitados para la nomenclatura
			nroSolicitadosNomen = nroSolicitadosNomen != null ? nroSolicitadosNomen + Numero.UNO.valueI : Numero.UNO.valueI;

			// SQL para actualizar la secuencia y cantidad consecutivos para la nomenclatura seleccionada
			dmls.add(SQLCorrespondencia.getUpdateNomenclaturaSecuenciaCantidad(
					nroSecuencia.toString(), nroSolicitadosNomen.toString(), idNomenclatura_));

			// SQL para actualizar la bandera que indica que campos ya tienen asociado un consecutivo
			dmls.add(SQLCorrespondencia.getUpdateCamposTieneConsecutivo(idNomenclatura_));

			// se ejecuta el batch para estos dos actualizaciones
			batchSinInjection(connection, dmls);

			// se debe confirmar los cambios en BD
			connection.commit();

			// se construye la respuesta a retornar
			SolicitudConsecutivoResponseDTO response = new SolicitudConsecutivoResponseDTO();
			response.setConsecutivo(secuenciaFormato);
			response.setIdConsecutivo(idConsecutivo);
			return response;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite obtener los datos para la pagina de bienvenida
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos de bienvenida
	 */
	public WelcomeInitDTO getDatosBienvenida(Long idCliente, Connection connection) throws Exception {

		// DTO con los datos a responder
		WelcomeInitDTO response = new WelcomeInitDTO();

		// se consultan las nomenclaturas
		List<WelcomeNomenclaturaDTO> nomenclaturas = (List<WelcomeNomenclaturaDTO>)
				find(connection,
				SQLCorrespondencia.GET_WELCOME_NOMENCLATURAS,
				MapperCorrespondencia.get(MapperCorrespondencia.GET_WELCOME_NOMENCLATURAS),
				ValueSQL.get(idCliente, Types.BIGINT));

		// se consultan los usuarios
		List<WelcomeUsuarioDTO> usuarios = (List<WelcomeUsuarioDTO>)
				find(connection,
				SQLCorrespondencia.GET_WELCOME_USUARIOS,
				MapperCorrespondencia.get(MapperCorrespondencia.GET_WELCOME_USUARIOS),
				ValueSQL.get(idCliente, Types.BIGINT));

		// se configuran los datos en el response y se retorna
		response.setNomenclaturas(nomenclaturas);
		response.setUsuarios(usuarios);
		return response;
	}

	/**
	 * Metodo para el cargue del documento asociado a un consecutivo
	 *
	 * @param datos, Contiene los datos del cargue del documento
	 * @return lista de documentos asociados al consecutivo
	 */
	public List<DocumentoDTO> cargarDocumento(DocumentoDTO datos, Connection connection) throws Exception {

		// se valida que el contenido del archivo no este vacio
		byte[] contenido = datos.getContenido();
		if (contenido == null || contenido.length == Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_DOCUMENTO_VACIO.value);
		}

		// se obtiene las variables globales para el proceso
		String idCliente = datos.getIdCliente().toString();
		String idConsecutivo = datos.getIdConsecutivo();
		String nombreDocumento = datos.getNombreDocumento();

		// el consecutivo no puede tener otro documento con el mismo nombre
		if ((boolean) find(
				connection,
				SQLCorrespondencia.isExistsNombreDocumento(idCliente, idConsecutivo),
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				ValueSQL.get(nombreDocumento, Types.VARCHAR))) {
			throw new BusinessException(MessagesKey.KEY_CONSECUTIVO_DOCUMENTO_MISMO_NOMBRE.value);
		}

		// para el proceso del cargue debe estar bajo una transaccion
		try {
			connection.setAutoCommit(false);

			// se hace el insert del documento asociado al consecutivo
			insertUpdate(connection,
					SQLCorrespondencia.getSQLInsertDocumento(idCliente),
					ValueSQL.get(Long.valueOf(idConsecutivo), Types.BIGINT),
					ValueSQL.get(nombreDocumento, Types.VARCHAR),
					ValueSQL.get(datos.getTipoDocumento(), Types.VARCHAR),
					ValueSQL.get(datos.getSizeDocumento(), Types.VARCHAR));

			// se procede almacenar el documento en S3 de AWS
			AdministracionDocumentosS3.getInstance().almacenarDocumento(contenido, idCliente, idConsecutivo, nombreDocumento);

			// se debe confirmar los cambios en BD
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}

		// se procede a consultar todos los documentos asociados al consecutivo
		return (List<DocumentoDTO>)
				find(connection,
				SQLCorrespondencia.getSQListDocumentos(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DOCUMENTOS));
	}

	/**
	 * Metodo que soporta el proceso de negocio para la descarga
	 * de un documento de correspondencia en AWS-S3
	 *
	 * @param idCliente, se utiliza para identificar el cliente que tiene el documento
	 * @param idDocumento, se utiliza para consultar los datos del documento
	 * @return DTO con los datos del documento incluyendo el contenido
	 */
	public DocumentoDTO descargarDocumento(Integer idCliente, Long idDocumento, Connection connection) throws Exception {

		// se obtiene los datos del documento
		String idCliente_ = idCliente.toString();
		DocumentoDTO documento = (DocumentoDTO)find(connection,
				SQLCorrespondencia.getSQLDatosDocumentoDescargar(idCliente_),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DATOS_DOCUMENTO_DESCARGAR),
				ValueSQL.get(idDocumento, Types.BIGINT));

		// los datos del documento deben existir en BD
		if (documento != null &&
			documento.getIdConsecutivo() != null &&
			documento.getNombreDocumento() != null &&
			documento.getTipoDocumento() != null &&
			documento.getSizeDocumento() != null) {

			// se procede a descargar el contenido del documento (AWS-S3)
			documento.setContenido(AdministracionDocumentosS3.getInstance()
					.descargarDocumento(
							idCliente_,
							documento.getIdConsecutivo(),
							documento.getNombreDocumento()));

			// En este punto el documento ya esta construido para ser retornado
			return documento;
		}

		// si llega a esta punto es porque el documento no existe
		throw new BusinessException(MessagesKey.KEY_DOCUMENTO_NO_EXISTE.value);
	}

	/**
	 * Metodo para eliminar un documento asociado al consecutivo
	 *
	 * @param datos, Contiene los datos del documento eliminar
	 * @return lista de documentos asociados al consecutivo
	 */
	public List<DocumentoDTO> eliminarDocumento(DocumentoDTO datos, Connection connection) throws Exception {

		// se obtiene las variables globales para el proceso
		String idCliente = datos.getIdCliente().toString();
		String idDocumento = datos.getId().toString();

		// para el proceso de eliminacion debe estar bajo una transaccion
		try {
			connection.setAutoCommit(false);

			// se obtiene los datos del documento eliminar
			List<String> documento = (List<String>) find(connection,
					SQLCorrespondencia.getSQLDatosDocumentoEliminar(idCliente, idDocumento),
					MapperCorrespondencia.get(MapperCorrespondencia.GET_DATOS_DOC_ELIMINAR));
			String idConsecutivo = documento.get(0);
			String nombreDocumento = documento.get(1);

			// se elimina los datos del documento en BD
			insertUpdate(connection, SQLCorrespondencia.getSQLEliminarDocumento(idCliente, idDocumento));

			// se elimina el documento de AWS-S3
			AdministracionDocumentosS3.getInstance().eliminarDocumento(idCliente, idConsecutivo, nombreDocumento);

			// se debe confirmar los cambios en BD
			connection.commit();

			// se procede a consultar todos los documentos asociados al consecutivo
			return (List<DocumentoDTO>)
					find(connection,
					SQLCorrespondencia.getSQListDocumentos(idCliente, idConsecutivo),
					MapperCorrespondencia.get(MapperCorrespondencia.GET_DOCUMENTOS));
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite obtener los consecutivos del anio actual
	 * de acuerdo al filtro de busqueda
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de consecutivos paginados y su cantidad total
	 */
	public PaginadorResponseDTO getConsecutivosAnioActual(FiltroConsecutivosDTO filtro, Connection connection) throws Exception {

		// cada cliente tiene sus propias tablas, se utiliza para identificar que tabla tomar
		String idCliente = filtro.getIdCliente().toString();

		// se obtiene el from de la consulta
		StringBuilder from = SQLCorrespondencia.getSQLFromConsecutivosAnioActual(idCliente);

		// son los parametros para los filtros de busqueda
		List<ValueSQL> parametros = new ArrayList<>();

		// filtro por fecha de solicitud
		SQLTransversal.getFilterFechaSolicitud(filtro.getFechaSolicitudInicial(), filtro.getFechaSolicitudFinal(), from);

		// filtro por nomenclaturas
		SQLTransversal.getFilterNomenclaturas(filtro.getNomenclaturas(), parametros, from);

		// filtro por consecutivos
		SQLTransversal.getFilterConsecutivos(filtro.getConsecutivos(), parametros, from);

		// filtro por usuarios
		Integer idUsuario = filtro.getIdUsuario();
		if (idUsuario != null && !idUsuario.equals(Numero.ZERO.valueI)) {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ids.add(idUsuario);
			SQLTransversal.getFilterUsuarios(ids, from);
		}

		// filtro por estados
		Integer estado = filtro.getEstado();
		if (estado != null && !estado.equals(Numero.ZERO.valueI)) {
			ArrayList<Integer> estados = new ArrayList<Integer>();
			estados.add(estado);
			SQLTransversal.getFilterEstados(estados, from);
		}

		// se valida si hay filtros agregados
		List<CampoFiltroDTO> filtrosAgregados = filtro.getFiltrosAgregados();
		if (filtrosAgregados != null && !filtrosAgregados.isEmpty()) {

			// se recorre todos los filtros agregados
			for (CampoFiltroDTO filtroAgregado : filtrosAgregados) {

				// se identifica que tipo de campos es
				Integer tipoCampo = filtroAgregado.getTipoCampo();
				if (TipoCampo.CAMPO_TEXTO.id.equals(tipoCampo) || TipoCampo.LISTA_DESPLEGABLE.id.equals(tipoCampo)) {
					SQLTransversal.getFilterInputSelectValue(parametros, filtroAgregado, idCliente, from);
				} else if (TipoCampo.CAMPO_FECHA.id.equals(tipoCampo)) {
					SQLTransversal.getFilterDateValue(filtroAgregado, idCliente, from);
				}
			}
		}

		// se configura los parametros en array para las consultas
		ValueSQL[] parametrosArray = !parametros.isEmpty() ? parametros.toArray(new ValueSQL[parametros.size()]) : null;

		// se utiliza para obtener los datos del paginador
		PaginadorDTO paginador = filtro.getPaginador();

		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se valida si se debe contar los consecutivos
		response.setCantidadTotal(paginador.getCantidadTotal());
		if (response.getCantidadTotal() == null) {
			response.setCantidadTotal((Long) find(connection,
					SQLTransversal.getSQLCount(from),
					MapperTransversal.get(MapperTransversal.COUNT),
					parametrosArray));
		}

		// solo se consultan los registros solo si existen de acuerdo al filtro
		if (response.getCantidadTotal() != null &&
			!response.getCantidadTotal().equals(Numero.ZERO.valueL)) {

			// se obtiene la consulta principal
			StringBuilder sql = SQLCorrespondencia.getSQLSelectConsecutivosAnioActual(from);

			// se ordena la consulta
			sql.append(" ORDER BY CON.FECHA_SOLICITUD DESC, NOM.NOMENCLATURA ASC, CON.CONSECUTIVO DESC");

			// se configura la paginacion de la consulta
			SQLTransversal.getLimitSQL(paginador.getSkip(), paginador.getRowsPage(), sql);

			// se procede a consultar los registros
			response.setRegistros(find(connection,
					sql.toString(),
					MapperCorrespondencia.get(MapperCorrespondencia.GET_CONSECUTIVOS_ANIO_ACTUAL),
					parametrosArray));
		}
		return response;
	}

	/**
	 * Metodo que permite obtener los datos iniciales para el 
	 * submodulo de Consecutivos de correspondencia solicitados
	 * para el anio actual
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos iniciales
	 */
	public InitConsecutivosAnioActualDTO getInitConsecutivosAnioActual(Long idCliente, Connection connection) throws Exception {

		// DTO con los datos iniciales para el submodulo
		InitConsecutivosAnioActualDTO response = new InitConsecutivosAnioActualDTO();

		// se procede a configurar el filtro para los consecutivos iniciales a mostrar
		FiltroConsecutivosDTO filtro = new FiltroConsecutivosDTO();
		filtro.setIdCliente(idCliente);

		// el paginador debe empezar de 0-10 por default
		PaginadorDTO paginador = new PaginadorDTO();
		paginador.setSkip(CommonConstant.SKIP_DEFAULT);
		paginador.setRowsPage(CommonConstant.ROWS_PAGE_DEFAULT);
		filtro.setPaginador(paginador);

		// se procede a consultar y configurar los consecutivos del anio actual paginados
		PaginadorResponseDTO consecutivos = getConsecutivosAnioActual(filtro, connection);
		response.setConsecutivos(consecutivos);

		// se consultan los demas datos solamente si hay consecutivos
		if (consecutivos != null &&
			consecutivos.getCantidadTotal() != null &&
			!consecutivos.getCantidadTotal().equals(Numero.ZERO.valueL)) {

			// se configura la fecha actual del sistema
			response.setFechaActual(Calendar.getInstance().getTime());

			// se procede a consultar los usuarios para el filtro de busqueda
			response.setUsuarios((List<SelectItemDTO>) find(
					connection,
					SQLTransversal.GET_ITEMS_USUARIOS,
					MapperTransversal.get(MapperTransversal.GET_ITEMS_USUARIOS),
					ValueSQL.get(idCliente, Types.BIGINT)));
		}
		return response;
	}

	/**
	 * Metodo que permite obtener los datos iniciales para el
	 * submodulo de Mis Consecutivos de correspondencia solicitados
	 * para el anio actual
	 *
	 * @param idCliente, identificador del cliente asociado al usuario
	 * @param idUsuario, identificador del usuario autenticado en el sistema
	 * @return DTO con los datos iniciales
	 */
	public InitMisConsecutivosDTO getInitMisConsecutivos(Long idCliente, Integer idUsuario, Connection connection) throws Exception {

		// DTO con los datos iniciales para el submodulo
		InitMisConsecutivosDTO response = new InitMisConsecutivosDTO();

		// se construye el filtro de busqueda para obtener los consecutivos del usuario
		FiltroConsecutivosDTO filtro = new FiltroConsecutivosDTO();
		filtro.setIdCliente(idCliente);
		filtro.setIdUsuario(idUsuario);

		// el paginador debe empezar de 0-10 por default
		PaginadorDTO paginador = new PaginadorDTO();
		paginador.setSkip(CommonConstant.SKIP_DEFAULT);
		paginador.setRowsPage(CommonConstant.ROWS_PAGE_DEFAULT);
		filtro.setPaginador(paginador);

		// se procede a consultar y configurar los consecutivos del usuario
		PaginadorResponseDTO consecutivos = getConsecutivosAnioActual(filtro, connection);
		response.setConsecutivos(consecutivos);

		// se consultan los demas datos solamente si hay consecutivos
		if (consecutivos != null &&
			consecutivos.getCantidadTotal() != null &&
			!consecutivos.getCantidadTotal().equals(Numero.ZERO.valueL)) {

			// se configura la fecha actual del sistema
			response.setFechaActual(Calendar.getInstance().getTime());
		}
		return response;
	}

	/**
	 * Metodo que permite consultar el detalle de un consecutivo
	 *
	 * @param filtro, DTO que contiene los identificadores del cliente y del consecutivo
	 * @return DTO con los datos del consecutivo
	 */
	public ConsecutivoDetalleDTO getDetalleConsecutivo(ConsecutivoDetalleDTO filtro, Connection connection) throws Exception {

		// DTO que contiene los datos a retornar
		ConsecutivoDetalleDTO detalle = new ConsecutivoDetalleDTO();

		// se configura los identificadores del cliente y consecutivo
		String idCliente = filtro.getIdCliente().toString();
		String idConsecutivo = filtro.getIdConsecutivo().toString();

		// se configura los datos generales del consecutivo
		ConsecutivoDTO consecutivo = (ConsecutivoDTO) find(connection,
				SQLCorrespondencia.getSQLConsecutivo(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_CONSECUTIVO));
		detalle.setConsecutivo(consecutivo);

		// se configura las transferencias que se han realizado a este consecutivo
		consecutivo.setTransferencias((List<TransferenciaDTO>)find(connection,
				SQLCorrespondencia.getSQListTransferencias(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_TRANSFERENCIAS)));

		// se configura los documentos asociados a este consecutivo
		detalle.setDocumentos((List<DocumentoDTO>)find(connection,
				SQLCorrespondencia.getSQListDocumentos(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DOCUMENTOS)));

		// se configura los valores del consecutivo
		detalle.setValores((List<CampoEntradaValueDTO>)find(connection,
				SQLCorrespondencia.getSQLConsecutivoValues(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_CONSECUTIVO_VALUES)));
		return detalle;
	}

	/**
	 * Metodo que permite obtener los campos para los filtros de busqueda
	 *
	 * @param idCliente, identificador del cliente que tiene los campos
	 * @return Lista de campos con sus atributos configurados
	 */
	public List<CampoFiltroDTO> getCamposFiltro(Long idCliente, Connection connection) throws Exception {
		return (List<CampoFiltroDTO>) find(connection,
				SQLCorrespondencia.GET_CAMPOS_FILTRO,
				MapperCorrespondencia.get(MapperCorrespondencia.GET_CAMPOS_FILTRO),
				ValueSQL.get(idCliente, Types.BIGINT));
	}

	/**
	 * Metodo que permite obtener los items para los filtros tipo LISTA DESPLEGABLE
	 *
	 * @param idsCampos, lista de identificadores de los campos a consultar sus items
	 * @return lista de items con sus atributos construido
	 */
	public List<ItemDTO> getItemsSelectFiltro(List<Long> idsCampos, Connection connection) throws Exception {

		// debe existir los identificadores de los campos
		if (idsCampos != null && !idsCampos.isEmpty()) {

			// variables necesarias para la solicitud
			final int ZERO = Numero.ZERO.valueI.intValue();
			List<ValueSQL> parametros = new ArrayList<>();
			StringBuilder parametrosJDBC = new StringBuilder();

			// se recorre cada identificador
			for (Long id : idsCampos) {

				// se agrega el parametro SQL
				parametros.add(ValueSQL.get(id, Types.BIGINT));

				// se agrega el parametro JDBC
				parametrosJDBC.append(parametrosJDBC.length() == ZERO ? CommonConstant.INTERROGACION : CommonConstant.COMA_INTERROGACION);
			}

			// se procede a consultar todos los items ordenados por campo y nombre
			return (List<ItemDTO>) find(connection,
					SQLCorrespondencia.getSQLItemsFiltro(parametrosJDBC.toString()),
					MapperCorrespondencia.get(MapperCorrespondencia.GET_ITEMS_SELECT_FILTRO),
					parametros.toArray(new ValueSQL[parametros.size()]));
		}
		return null;
	}

	/**
	 * Metodo que permite ACTIVAR o ANULAR un consecutivo de correspondencia
	 *
	 * @param parametro, DTO que contiene los datos necesarios para el proceso
	 */
	public void activarAnularConsecutivo(ActivarAnularConsecutivoDTO parametro, Connection connection) throws Exception {

		// se obtiene los datos necesarios para el proceso
		Integer idEstado = parametro.getIdEstado();
		Long idConsecutivo = parametro.getIdConsecutivo();

		// se configura el where sentence dependiendo del estado
		StringBuilder where = new StringBuilder();
		if (Estado.ACTIVO.id.equals(idEstado)) {
			where.append(",FECHA_ANULACION=NULL WHERE ID_CONSECUTIVO=").append(idConsecutivo);
		} else if (Estado.ANULADO.id.equals(idEstado)) {
			where.append(",FECHA_ANULACION=CURDATE() WHERE ID_CONSECUTIVO=").append(idConsecutivo);
		}

		// si hay un where sentence es por que el estado es VALIDO
		if (where.length() > Numero.ZERO.valueI.intValue()) {

			// se complementa el DML a ejecutar
			StringBuilder update = new StringBuilder("UPDATE CONSECUTIVOS_");
			update.append(parametro.getIdCliente());
			update.append(" SET ESTADO=");
			update.append(idEstado);
			update.append(where);

			// se ejecuta el update sobre el estado del consecutivo
			int resultado = insertUpdate(connection, update.toString());

			// EL proceso no se ejecuto satisfactoriamente
			if (resultado <= Numero.ZERO.valueI.intValue()) {
				throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
			}
		} else {
			// estado no es permitido para el consecutivo, debe ser ACTIVO o ANULADO
			throw new BusinessException(MessagesKey.KEY_ESTADO_NO_PERMITIDO.value);			
		}
	}

	/**
	 * Metodo que permite transferir un consecutivo hacia otro usuario
	 *
	 * @param parametro, DTO con los datos necesarios para el proceso
	 * @return DTO con los consecutivos paginado de acuerdo al filtro
	 */
	public TransferirConsecutivoDTO transferirConsecutivo(
			TransferirConsecutivoDTO parametro,
			Connection connection) throws Exception {
		try {
			connection.setAutoCommit(false);

			// se obtiene las variables necesarias para el proceso
			String idCliente = parametro.getIdCliente().toString();
			String idConsecutivo = parametro.getIdConsecutivo().toString();
			String idUsuario = parametro.getIdUsuario().toString();
			String idUsuarioTransferir = parametro.getIdUsuarioTransferir().toString();

			// El ADMIN no tiene ID por lo tanto debe ser NULL
			final String ID_ADMIN = CommonConstant.ID_ADMINISTRADOR.toString();
			idUsuario = ID_ADMIN.equals(idUsuario) ? null : idUsuario;
			idUsuarioTransferir = ID_ADMIN.equals(idUsuarioTransferir) ? null : idUsuarioTransferir;

			// se construye SQL para contar las transferencia realizadas para este consecutivo
			StringBuilder countSQL = new StringBuilder ("SELECT COUNT(*) FROM CONSECUTIVOS_TRANS_")
					.append(idCliente)
					.append(" WHERE ID_CONSECUTIVO=")
					.append(idConsecutivo);

			// se obtiene la cantidad de transferencia realizadas para este consecutivo
			Long transferencias = (Long) find(connection, countSQL.toString(), MapperTransversal.get(MapperTransversal.COUNT));

			// se utiliza para encapsular todos los dmls para ser ejecutado por el batch
			List<String> dmls = new ArrayList<>();

			// si el consecutivo no tiene transferencia se debe insertar el user actual
			if (transferencias.equals(Numero.ZERO.valueL)) {

				// la fecha transferido es la fecha de la solicitud del consecutivo
				StringBuilder fechaSolicitud = new StringBuilder("(SELECT CON.FECHA_SOLICITUD FROM CONSECUTIVOS_")
						.append(idCliente)
						.append(" CON WHERE CON.ID_CONSECUTIVO=")
						.append(idConsecutivo)
						.append(")");

				// se construye el INSERT para este USUARIO
				dmls.add(getInsertTransferencia(idCliente, idConsecutivo, idUsuario, fechaSolicitud.toString()));
			}

			// se construye el INSERT para el nuevo usuario
			dmls.add(getInsertTransferencia(idCliente, idConsecutivo, idUsuarioTransferir, "CURDATE()"));

			// se construye el UPDATE del consecutivo para el nuevo usuario
			StringBuilder updateUser = new StringBuilder("UPDATE CONSECUTIVOS_")
					.append(idCliente)
					.append(" SET USUARIO=")
					.append(idUsuarioTransferir)
					.append(" WHERE ID_CONSECUTIVO=")
					.append(idConsecutivo);
			dmls.add(updateUser.toString());

			// se ejecuta el batch con los dmls construidos
			batchSinInjection(connection, dmls);

			// se debe confirmar los cambios en BD
			connection.commit();

			// se construye el response con sus consecutivos
			TransferirConsecutivoDTO response = new TransferirConsecutivoDTO();
			response.setResponseConsecutivos(getConsecutivosAnioActual(parametro.getFiltro(), connection));
			return response;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite construir el SQL para el INSERT de la
	 * tabla de transferencia de consecutivos e usuarios
	 */
	private String getInsertTransferencia(
			String idCliente, String idConsecutivo,
			String idUsuario, String fechaTransferido) {

		// dependiento de la nulalidad del usuario se procede a construir el INSERT
		StringBuilder insert = new StringBuilder("INSERT INTO CONSECUTIVOS_TRANS_");
		if (idUsuario != null) {
			insert.append(idCliente)
			.append("(ID_CONSECUTIVO,USUARIO,FECHA_TRANSFERIDO)VALUES(")
			.append(idConsecutivo).append(",")
			.append(idUsuario).append(",")
			.append(fechaTransferido)
			.append(")");
		} else {
			insert.append(idCliente)
			.append("(ID_CONSECUTIVO,FECHA_TRANSFERIDO)VALUES(")
			.append(idConsecutivo).append(",")
			.append(fechaTransferido)
			.append(")");
		}
		return insert.toString();
	}

	/**
	 * Metodo que permite obtener los usuarios para el proceso de transferir consecutivo
	 *
	 * @param idCliente, identificador del cliente asociado a los usuarios
	 * @param idUsuario, se deben consultar todos los usuarios activos excepto este usuario
	 * @return Lista de usuarios activos en el sistema
	 */
	public List<SelectItemDTO> getUsuariosTransferir(Integer idCliente, Integer idUsuario, Connection connection) throws Exception {

		// lista de usuarios a retornar
		List<SelectItemDTO> usuarios = new ArrayList<>();

		// si el usuario autenticado no es el ADMIN se debe configurar el item del ADMIN
		if (!CommonConstant.ID_ADMINISTRADOR.equals(idUsuario)) {
			SelectItemDTO item = new SelectItemDTO();
			item.setId(CommonConstant.ID_ADMINISTRADOR.longValue());
			item.setLabel(CommonConstant.ADMINISTRADOR);
			item.setDescripcion(CommonConstant.ADMINISTRADOR_CARGO);
			usuarios.add(item);
		}

		// se procede a consultar los usuarios activos en el sistema
		findParams(connection,
				SQLCorrespondencia.GET_USUARIOS_TRANSFERIR,
				MapperCorrespondencia.get(MapperCorrespondencia.GET_USUARIOS_TRANSFERIR),
				usuarios,
				ValueSQL.get(idCliente, Types.INTEGER),
				ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER),
				ValueSQL.get(idUsuario, Types.INTEGER));
		return usuarios;
	}

	/**
	 * Metodo que permite consultar y retornar los datos del consecutivo para su edicion
	 *
	 * @param filtro, DTO que contiene los valores para el filtro de busqueda
	 * @return DTO Con los atributos del consecutivo configurados
	 */
	public ConsecutivoEdicionDTO getConsecutivoEdicion(ConsecutivoEdicionDTO filtro, Connection connection) throws Exception {

		// DTO que contiene los datos a retornar
		ConsecutivoEdicionDTO response = new ConsecutivoEdicionDTO();

		// se configura los identificadores del cliente y consecutivo
		String idCliente = filtro.getIdCliente().toString();
		String idConsecutivo = filtro.getIdConsecutivo().toString();

		// se configura los datos generales del consecutivo
		ConsecutivoDTO consecutivo = (ConsecutivoDTO) find(connection,
				SQLCorrespondencia.getSQLConsecutivo(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_CONSECUTIVO));
		response.setConsecutivo(consecutivo);

		// se configura las transferencias que se han realizado a este consecutivo
		consecutivo.setTransferencias((List<TransferenciaDTO>)find(connection,
				SQLCorrespondencia.getSQListTransferencias(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_TRANSFERENCIAS)));

		// se configura los values de este consecutivo para editar
		response.setValues(getValuesEditar(idCliente, idConsecutivo, consecutivo.getIdNomenclatura(), connection));

		// se configura los documentos asociados a este consecutivo
		response.setDocumentos((List<DocumentoDTO>)find(connection,
				SQLCorrespondencia.getSQListDocumentos(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DOCUMENTOS)));
		return response;
	}

	/**
	 * Metodo que permite consultar y configurar los valores de un consecutivo para su edicion
	 */
	private List<ConsecutivoEdicionValueDTO> getValuesEditar(
			String idCliente,
			String idConsecutivo,
			Long idNomenclatura,
			Connection connection) throws Exception {

		// se consulta los campos de la nomenclatura asociada al consecutivo
		List<CampoEntradaDetalleDTO> campos = getCamposNomenclatura(idNomenclatura, connection);

		// puede existir consecutivos sin campos asociados
		if (campos != null && !campos.isEmpty()) {

			// se consultan los valores ingresados de este consecutivo
			List<ConsecutivoEdicionValueDTO> values =
					(List<ConsecutivoEdicionValueDTO>) findParams(connection,
					SQLCorrespondencia.getSQLValuesEdicion(idCliente, idConsecutivo),
					MapperCorrespondencia.get(MapperCorrespondencia.GET_VALUES_EDICION),
					campos);

			// si la cantidad de campos y valores son diferentes es porque hay campos sin valores
			int cantidadCampos = campos.size();
			int cantidadValues = values.size();
			if (cantidadCampos != cantidadValues) {

				// se busca los campos que no tiene valores
				for (CampoEntradaDetalleDTO campo : campos) {
					if (!campo.isTieneValor()) {
						ConsecutivoEdicionValueDTO valueResponse = new ConsecutivoEdicionValueDTO();
						valueResponse.setCampo(campo);
						values.add(valueResponse);
					}
				}
			}
			return values;
		}
		return null;
	}

	/**
	 * Metodo que permite editar los valores de un consecutivo
	 *
	 * @param datos, contiene todos los valores a editar
	 * @return valores asociados al consecutivo con las modificaciones realizadas
	 */
	public ConsecutivoEdicionDTO editarConsecutivoValores(ConsecutivoEdicionDTO datos, Connection connection) throws Exception {

		// variables necesarias para el proceso
		Long idCliente = datos.getIdCliente();
		Long idConsecutivo = datos.getIdConsecutivo();
		Long idNomenclatura = datos.getIdNomenclatura();

		// se obtiene los valores a validar a nivel de negocio dependiendo sus restricciones
		List<CampoEntradaValueDTO> valoresValidar = datos.getValoresValidar();

		// puede llegar nulo, no todas las validaciones se aplican a nivel de negocio
		if (valoresValidar != null && !valoresValidar.isEmpty()) {

			// se construye la solicitud para invocar el validador
			SolicitudConsecutivoDTO solicitud = new SolicitudConsecutivoDTO();
			solicitud.setValores(valoresValidar);
			solicitud.setIdCliente(idCliente);
			solicitud.setIdNomenclatura(idNomenclatura);

			// se invoca el validador retornando una lista de errores encontrados
			List<MessageResponseDTO> errores = validarCamposIngresoInformacion(solicitud, connection);

			// si hay errores el proceso termina aqui.
			if (errores != null && !errores.isEmpty()) {
				ConsecutivoEdicionDTO response = new ConsecutivoEdicionDTO();
				response.setErrores(errores);
				return response;
			}
		}

		// variables necesarios para el proceso de edicion
		List<List<ValueSQL>> injInserts = new ArrayList<>();
		List<List<ValueSQL>> injUpdates = new ArrayList<>();
		ValueSQL valueIdConsecutivo = ValueSQL.get(idConsecutivo, Types.BIGINT);

		// se recorre cada valor a editar
		Long idValue;
		List<ValueSQL> params;
		List<ConsecutivoEdicionValueDTO> values = datos.getValues();
		for (ConsecutivoEdicionValueDTO value : values) {

			// se obtiene el ID del VALUE y se crea la lista de parametros
			idValue = value.getIdValue();
			params = new ArrayList<>();

			// si el ID del VALUE existe solamente se hace el UPDATE
			if (idValue != null && !idValue.equals(Numero.ZERO.valueL)) {
				params.add(ValueSQL.get(value.getValueUpdate() != null ? value.getValueUpdate().toString() : null, Types.VARCHAR));
				params.add(ValueSQL.get(idValue, Types.BIGINT));
				injUpdates.add(params);
			} 
			// si el ID del VALUE NO existe se hace el INSERT
			else {
				params.add(valueIdConsecutivo);
				params.add(ValueSQL.get(value.getCampo().getIdCampoNomenclatura(), Types.BIGINT));
				params.add(ValueSQL.get(value.getValueUpdate() != null ? value.getValueUpdate().toString() : null, Types.VARCHAR));
				injInserts.add(params);
			}
		}

		// se ejecuta los batch para los diferentes DMLs
		try {
			connection.setAutoCommit(false);

			// las consultas necesitan el id del cliente en tipo String
			String idCliente_ = idCliente.toString();

			// se verifica si hay INSERTs a procesar
			if (!injInserts.isEmpty()) {
				// se hace los inserts correspondiente
				batchConInjection(connection, SQLCorrespondencia.getInsertConsecutivoValues(idCliente_), injInserts);

				// Se actualiza la bandera que indica que campos ya tienen asociado un consecutivo
				insertUpdate(connection, SQLCorrespondencia.getUpdateCamposTieneConsecutivo(idNomenclatura.toString()));
			}

			// se verifica si hay UPDATEs a procesar
			if (!injUpdates.isEmpty()) {
				batchConInjection(connection, SQLCorrespondencia.getUpdateConsecutivoValues(idCliente_), injUpdates);
			}
			connection.commit();

			// se retorna los valores de este consecutivo
			ConsecutivoEdicionDTO response = new ConsecutivoEdicionDTO();
			response.setValues(getValuesEditar(idCliente_, idConsecutivo.toString(), idNomenclatura, connection));
			return response;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}
}
