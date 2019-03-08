package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adminfree.aws.AdministracionDocumentosS3;
import adminfree.constants.BusinessMessages;
import adminfree.constants.CommonConstant;
import adminfree.constants.SQLConfiguraciones;
import adminfree.constants.SQLCorrespondencia;
import adminfree.constants.SQLTransversal;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.CampoFiltroDTO;
import adminfree.dtos.correspondencia.ConsecutivoDTO;
import adminfree.dtos.correspondencia.ConsecutivoDetalleDTO;
import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.dtos.correspondencia.FiltroConsecutivosAnioActualDTO;
import adminfree.dtos.correspondencia.InitConsecutivosAnioActualDTO;
import adminfree.dtos.correspondencia.InitSolicitarConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoResponseDTO;
import adminfree.dtos.correspondencia.WelcomeInitDTO;
import adminfree.dtos.correspondencia.WelcomeNomenclaturaDTO;
import adminfree.dtos.correspondencia.WelcomeUsuarioDTO;
import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.dtos.transversal.SelectItemDTO;
import adminfree.enums.MessagesKey;
import adminfree.enums.Numero;
import adminfree.enums.TipoCampo;
import adminfree.mappers.MapperConfiguraciones;
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
		List<CampoEntradaDetalleDTO> resultado = (List<CampoEntradaDetalleDTO>)
				find(connection,
						SQLCorrespondencia.GET_DTL_NOMENCLATURA_CAMPOS,
						MapperCorrespondencia.get(MapperCorrespondencia.GET_DTL_NOMENCLATURA_CAMPOS),
						ValueSQL.get(idNomenclatura, Types.BIGINT));

		// se recorre todos los campos en busqueda de los select-items
		if (resultado != null && !resultado.isEmpty()) {
			for (CampoEntradaDetalleDTO campo : resultado) {

				// se consulta los items para esta lista desplegable
				if (TipoCampo.LISTA_DESPLEGABLE.id.equals(campo.getTipoCampo())) {
					campo.setItems((List<ItemDTO>)
							find(connection,
									SQLConfiguraciones.GET_ITEMS,
									MapperConfiguraciones.get(MapperConfiguraciones.GET_ITEMS),
									ValueSQL.get(campo.getId(), Types.BIGINT)));
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
			String countSQL;
			String value_;

			// se recorre cada valor a validar
			for (CampoEntradaValueDTO valor : valores) {
				restricciones = valor.getRestricciones();

				// se verifica que el campo asociado al valor si tenga restricciones
				if (restricciones != null && !restricciones.isEmpty()) {
					countSQL = null;

					// se configura el tipo de campo y el value
					idCampoSQL.setValor(valor.getIdCampo());
					value_ = valor.getValue().toString();
					valueSQL.setValor(value_);

					// dependiendo de la restriccion se configura SQL y parametros para el count
					if (restricciones.contains(CommonConstant.KEY_CAMPO_UNICO_NOMENCLATURA)) {
						countSQL = SQLCorrespondencia.getSQLValorUnico(idCliente_, idNomenclatura_, valor.getIdValue());
					} else if (restricciones.contains(CommonConstant.KEY_CAMPO_TODAS_NOMENCLATURA)) {
						countSQL = SQLCorrespondencia.getSQLValorUnico(idCliente_, null, valor.getIdValue());
					}

					// se verifica si hay SQL a procesar
					if (countSQL != null) {

						// se hace el count para identificar si existe otro valor igual
						Long count = (Long) find(connection, countSQL,
								MapperTransversal.get(MapperTransversal.COUNT),
								idCampoSQL, valueSQL);

						// si el count es mayor que zero es por que existe otro valor igual
						if (count != null && count > Numero.ZERO.value.longValue()) {
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
			idUsuario = (idUsuario != null && idUsuario > Numero.ZERO.value.longValue()) ? idUsuario : null;
			String idCliente_ = idCliente.toString();
			String idNomenclatura_ = idNomenclatura.toString();

			// se procede a consultar la secuencia actual de la nomenclatura
			List<Integer> respuesta = (List<Integer>) find(connection,
					SQLCorrespondencia.GET_SECUENCIA_NOMENCLATURA,
					MapperCorrespondencia.get(MapperCorrespondencia.GET_SECUENCIA_NOMENCLATURA),
					ValueSQL.get(idNomenclatura, Types.BIGINT));

			// se configura el nro inicial y la secuencia actual de la nomenclatura consultada
			Integer nroInicial = respuesta.get(Numero.ZERO.value);
			Integer nroSecuencia = respuesta.get(Numero.UNO.value);
			Integer nroSolicitadosNomen = respuesta.get(Numero.DOS.value);

			// se establece el nuevo consecutivo para la nomenclatura
			if (nroSecuencia != null && nroSecuencia > Numero.ZERO.value) {
				nroSecuencia = nroSecuencia + Numero.UNO.value;
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
				final Long UNO = Numero.UNO.value.longValue();
				cantidad = cantidad != null ? cantidad + UNO : UNO;
				dmls.add(SQLCorrespondencia.getUpdateUsuarioCantidadConsecutivos(idUsuario.toString(), cantidad.toString()));
			}

			// se configura la cantidad de consecutivos solicitados para la nomenclatura
			nroSolicitadosNomen = nroSolicitadosNomen != null ? nroSolicitadosNomen + Numero.UNO.value : Numero.UNO.value;

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
		if (contenido == null || !(contenido.length > Numero.ZERO.value.intValue())) {
			throw new BusinessException(MessagesKey.KEY_DOCUMENTO_VACIO.value);
		}

		// se obtiene las variables globales para el proceso
		String idCliente = datos.getIdCliente();
		String idConsecutivo = datos.getIdConsecutivo();
		String nombreDocumento = datos.getNombreDocumento();

		// se cuenta los documentos que tiene el consecutivo con el mismo nombre
		Long countNombre = (Long)
				find(connection,
				SQLCorrespondencia.getSQLCountNombreDocumento(idCliente, idConsecutivo),
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(nombreDocumento, Types.VARCHAR));

		// el consecutivo no puede tener otro documento con el mismo nombre
		if (countNombre != null && countNombre > Numero.ZERO.value.longValue()) {
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
	public DocumentoDTO descargarDocumento(
			String idCliente,
			String idDocumento,
			Connection connection) throws Exception {

		// se obtiene los datos del documento
		DocumentoDTO documento = (DocumentoDTO)find(connection,
				SQLCorrespondencia.getSQLDatosDocumentoDescargar(idCliente, idDocumento),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_DATOS_DOCUMENTO_DESCARGAR));

		// los datos del documento deben existir en BD
		if (documento != null &&
			documento.getIdConsecutivo() != null &&
			documento.getNombreDocumento() != null &&
			documento.getTipoDocumento() != null &&
			documento.getSizeDocumento() != null) {

			// se procede a descargar el contenido del documento (AWS-S3)
			documento.setContenido(AdministracionDocumentosS3.getInstance()
					.descargarDocumento(
							idCliente,
							documento.getIdConsecutivo(),
							documento.getNombreDocumento()));

			// En este punto el documento ya esta construido para ser retornado
			return documento;
		}

		// si llega a esta punto es porque el documento no existe
		throw new BusinessException(MessagesKey.DOCUMENTO_NO_EXISTE.value);
	}

	/**
	 * Metodo para eliminar un documento asociado al consecutivo
	 *
	 * @param datos, Contiene los datos del documento eliminar
	 * @return lista de documentos asociados al consecutivo
	 */
	public List<DocumentoDTO> eliminarDocumento(DocumentoDTO datos, Connection connection) throws Exception {

		// se obtiene las variables globales para el proceso
		String idCliente = datos.getIdCliente();
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
	public PaginadorResponseDTO getConsecutivosAnioActual(
			FiltroConsecutivosAnioActualDTO filtro,
			Connection connection) throws Exception {

		// se obtiene el from de la consulta
		StringBuilder from = SQLCorrespondencia.getSQLFromConsecutivosAnioActual(filtro.getIdCliente().toString());

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
		if (idUsuario != null && idUsuario != Numero.ZERO.value) {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ids.add(idUsuario);
			SQLTransversal.getFilterUsuarios(ids, from);
		}

		// filtro por estados
		Integer estado = filtro.getEstado();
		if (estado != null && estado > Numero.ZERO.value) {
			ArrayList<Integer> estados = new ArrayList<Integer>();
			estados.add(estado);
			SQLTransversal.getFilterEstados(estados, from);
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
			response.getCantidadTotal() > Numero.ZERO.value.longValue()) {

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
		FiltroConsecutivosAnioActualDTO filtro = new FiltroConsecutivosAnioActualDTO();
		filtro.setIdCliente(idCliente);
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
			consecutivos.getCantidadTotal() > Numero.ZERO.value.longValue()) {

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
		detalle.setConsecutivo((ConsecutivoDTO) find(connection,
				SQLCorrespondencia.getSQLConsecutivo(idCliente, idConsecutivo),
				MapperCorrespondencia.get(MapperCorrespondencia.GET_CONSECUTIVO)));

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
}
