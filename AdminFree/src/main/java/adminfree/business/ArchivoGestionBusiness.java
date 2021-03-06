package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLArchivoGestion;
import adminfree.constants.SQLTransversal;
import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.Documental;
import adminfree.dtos.archivogestion.FiltroSerieDocumentalDTO;
import adminfree.dtos.archivogestion.InitAdminSeriesDocumentalesDTO;
import adminfree.dtos.archivogestion.ResponseEdicionSerieSubserieDTO;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.enums.MessagesKey;
import adminfree.enums.Numero;
import adminfree.mappers.MapperArchivoGestion;
import adminfree.mappers.MapperTransversal;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.ValueSQL;
import adminfree.utilities.BusinessException;
import adminfree.utilities.Util;

/**
 * Clase que contiene los procesos de negocio para el modulo de Archivo de Gestion
 *
 * @author Carlos Andres Diaz
 *
 */
@SuppressWarnings("unchecked")
public class ArchivoGestionBusiness extends CommonDAO {

	/**
	 * Metodo que permite obtener los datos de inicio para el submodulo de series documentales
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return Response con los datos necesarios para el submodulo
	 */
	public InitAdminSeriesDocumentalesDTO getInitAdminSeriesDocumentales(Long idCliente, Connection connection) throws Exception {

		// es el response con los datos necesarios para iniciar el submodulo
		InitAdminSeriesDocumentalesDTO response = new InitAdminSeriesDocumentalesDTO();

		// se configura el filtro para obtener las series documentales
		FiltroSerieDocumentalDTO filtroSeries = new FiltroSerieDocumentalDTO();
		filtroSeries.setIdCliente(idCliente);

		// el paginador debe empezar de 0-10 por default
		PaginadorDTO paginador = new PaginadorDTO();
		paginador.setSkip(CommonConstant.SKIP_DEFAULT);
		paginador.setRowsPage(CommonConstant.ROWS_PAGE_DEFAULT);
		filtroSeries.setPaginador(paginador);

		// se procede a consultar las primeras 10 series documentales asociadas al cliente
		response.setSeries(getSeriesDocumentales(filtroSeries, connection));
		return response;
	}

	/**
	 * Metodo que permite obtener las series documentales de acuerdo al filtro de busqueda
	 *
	 * @param filtro, DTO que contiene los datos del filtro de busqueda
	 * @return DTO con los datos del response con la lista de series documentales
	 */
	public PaginadorResponseDTO getSeriesDocumentales(FiltroSerieDocumentalDTO filtro, Connection connection) throws Exception {

		// FROM de la consulta donde se configuran los demas filtros
		StringBuilder from = new StringBuilder("FROM SERIES_DOCUMENTALES SE WHERE SE.CLIENTE=?");

		// son los parametros para los filtros de busqueda
		List<ValueSQL> parametros = new ArrayList<>();
		parametros.add(ValueSQL.get(filtro.getIdCliente(), Types.BIGINT));

		// filtro por nombre de la serie documental
		SQLTransversal.getFilterValueString(parametros, from, filtro.getNombreSerieDocumental(), " AND SE.NOMBRE");

		// filtro por codigo de la serie documental
		SQLTransversal.getFilterValueString(parametros, from, filtro.getCodigoSerieDocumental(), " AND SE.CODIGO");

		// filtro por nombre de la sub-serie documental
		SQLTransversal.getFilterSubConsultaExists(
				parametros,
				from,
				filtro.getNombreSubSerieDocumental(),
				"SELECT * FROM SUBSERIES_DOCUMENTALES SUB WHERE SUB.ID_SERIE=SE.ID_SERIE AND SUB.NOMBRE");

		// filtro por codigo de la sub-serie documental
		SQLTransversal.getFilterSubConsultaExists(
				parametros,
				from,
				filtro.getCodigoSubSerieDocumental(),
				"SELECT * FROM SUBSERIES_DOCUMENTALES SUB WHERE SUB.ID_SERIE=SE.ID_SERIE AND SUB.CODIGO");

		// se configura los parametros en array para las consultas
		ValueSQL[] parametrosArray = parametros.toArray(new ValueSQL[parametros.size()]);

		// se utiliza para obtener los datos del paginador
		PaginadorDTO paginador = filtro.getPaginador();

		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se valida si se debe contar las series documentales
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

			// se construye la consulta principal
			StringBuilder sql = new StringBuilder("SELECT SE.ID_SERIE,SE.CODIGO,UPPER(SE.NOMBRE),SE.AG,SE.AC,SE.CT,SE.M,SE.S,SE.E,SE.PROCEDIMIENTO ");
			sql.append(from);

			// se ordena la consulta por nombre de la serie
			sql.append(" ORDER BY SE.NOMBRE ASC");

			// se configura la paginacion de la consulta
			SQLTransversal.getLimitSQL(paginador.getSkip(), paginador.getRowsPage(), sql);

			// se utiliza para consultar las subseries asociadas a cada serie documental
			StringBuilder idsSeries = new StringBuilder();

			// se procede a consultar las series documentales
			List<SerieDocumentalDTO> series =(List<SerieDocumentalDTO>) findParams(
					connection,
					sql.toString(),
					MapperArchivoGestion.get(MapperArchivoGestion.GET_SERIES_DOCUMENTALES),
					idsSeries, parametrosArray);

			// se consultan los tipos documentales asociadas a las series consultadas
			findParams(connection,
					SQLArchivoGestion.getSQLTiposDocSerie(idsSeries),
					MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOC_SERIES),
					series);

			// se construye los parametros que necesita el mapper para obtener las subseries
			List<Object> paramsGetSubSeries = new ArrayList<>();
			StringBuilder idsSubSerie = new StringBuilder();
			paramsGetSubSeries.add(series);
			paramsGetSubSeries.add(idsSubSerie);

			// se consultan las subseries asociadas a las series consultadas
			findParams(connection,
					SQLArchivoGestion.getSQLSubSeriesDocumentalesIN(idsSeries),
					MapperArchivoGestion.get(MapperArchivoGestion.GET_SUBSERIES_SERIES),
					paramsGetSubSeries);

			// si hay subseries consultadas se procede a obtener sus tipos documentales
			if (idsSubSerie.length() > Numero.ZERO.valueI.intValue()) {
				findParams(connection,
						SQLArchivoGestion.getSQLTiposDocSubSerie(idsSubSerie),
						MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOC_SUBSERIES),
						series);
			}

			// se configuran las series consultadas en el response
			response.setRegistros(series);
		}
		return response;
	}

	/**
	 * Metodo que permite obtener todos los tipos documentales asociados a un cliente
	 *
	 * @param idCliente, cada cliente tiene sus propios tipos documentales
	 * @return Lista de tipos documentales asociados al cliente
	 */
	public List<TipoDocumentalDTO> getTiposDocumentales(Long idCliente, Connection connection) throws Exception {
		return (List<TipoDocumentalDTO>) find(connection,
				SQLArchivoGestion.GET_TIPOS_DOCUMENTALES,
				MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOCUMENTALES),
				ValueSQL.get(idCliente, Types.BIGINT));
	}

	/**
	 * Metodo que permite administrar la entidad de series documentales
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param serie, DTO con los datos de la serie documental
	 * @return Objecto con la respuesta del proceso
	 */
	public Object administrarSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {

		// es el response del proceso a retornar
		Object response = null;

		// se captura el tipo de evento
		String tipoEvento = serie.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.CREAR:
				response = crearSerieDocumental(serie, connection);
				break;

			case TipoEvento.EDITAR:
				response = editarSerieDocumental(serie, connection);
				break;

			case TipoEvento.ELIMINAR:
				response = eliminarSerieDocumental(serie, connection);
				break;
		}
		return response;
	}

	/**
	 * Metodo que permite administrar la entidad de sub-serie documental
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param subserie, DTO con los datos de la sub-serie documental
	 * @return Objecto con la respuesta del proceso
	 */
	public Object administrarSubSerieDocumental(SubSerieDocumentalDTO subserie, Connection connection) throws Exception {

		// es el response del proceso a retornar
		Object response = null;

		// se captura el tipo de evento
		String tipoEvento = subserie.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.CREAR:
				response = crearSubSerieDocumental(subserie, connection);
				break;

			case TipoEvento.EDITAR:
				response = editarSubSerieDocumental(subserie, connection);
				break;

			case TipoEvento.ELIMINAR:
				response = eliminarSubSerieDocumental(subserie, connection);
				break;
		}
		return response;
	}

	/**
	 * Metodo que permite obtener las subseries documentales relacionadas a una serie documental
	 *
	 * @param idSerie, identificador de la serie documental
	 * @return lista de subseries documentales relacionadas a una serie documental
	 */
	public List<SubSerieDocumentalDTO> getSubSeriesDocumental(Long idSerie, Connection connection) throws Exception {

		// objecto para encapsular el identificador de la serie documental
		SerieDocumentalDTO serie = new SerieDocumentalDTO();
		serie.setIdSerie(idSerie);

		// se necesita esta lista para el mapper de obtener las subseries documentales
		List<SerieDocumentalDTO> series = new ArrayList<>();
		series.add(serie);

		// son los parametros a enviar al mapper para obtener las subseries documentales
		StringBuilder idsSubSerie = new StringBuilder();
		List<Object> paramsGetSubSeries = new ArrayList<>();
		paramsGetSubSeries.add(series);
		paramsGetSubSeries.add(idsSubSerie);

		// se procede a consultar las sub-series asociadas a la serie documental
		findParams(connection,
				SQLArchivoGestion.getSQLSubSeriesDocumentales(idSerie),
				MapperArchivoGestion.get(MapperArchivoGestion.GET_SUBSERIES_SERIES),
				paramsGetSubSeries);

		// si hay subseries consultadas se procede a obtener sus tipos documentales
		if (idsSubSerie.length() > Numero.ZERO.valueI.intValue()) {
			findParams(connection,
					SQLArchivoGestion.getSQLTiposDocSubSerie(idsSubSerie),
					MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOC_SUBSERIES),
					series);
		}

		// se retornan las subseries consultadas
		return serie.getSubSeries();
	}

	/**
	 * Metodo que permite crear una subserie documental en el sistema
	 *
	 * @param subserie, DTO que contiene los datos de la subserie
	 * @return tipos documentales que fueron asociados a la sub-serie y no existian en BD
	 */
	private List<TipoDocumentalDTO> crearSubSerieDocumental(SubSerieDocumentalDTO subserie, Connection connection) throws Exception {

		// se utiliza para varios proceso
		Integer idCliente = subserie.getIdCliente();

		// se verifica si hay otra subserie con el mismo NOMBRE
		if ((boolean) find(
				connection,
				SQLArchivoGestion.existsValorSubSerie("NOMBRE", idCliente, null),
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				ValueSQL.get(subserie.getNombre(), Types.VARCHAR))) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_NOMBRE.value);
		}

		// se verifica si hay otra subserie con el mismo CODIGO
		if ((boolean) find(
				connection,
				SQLArchivoGestion.existsValorSubSerie("CODIGO", idCliente, null),
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				ValueSQL.get(subserie.getCodigo(), Types.VARCHAR))) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_CODIGO.value);
		}

		// para este proceso debe estar bajo transaccionalidad
		try {
			connection.setAutoCommit(false);

			// identificador de la nueva sub-serie documental
			Long idSubSerie = null;

			// si esta variable contiene informacion indica que se debe consultar los tipos documentales
			List<List<ValueSQL>> insertsTiposDocSerieINJ = null;

			// se procede a insertar la subserie
			int respuesta = insertUpdate(
					connection,
					SQLArchivoGestion.INSERT_SUBSERIE,
					ValueSQL.get(subserie.getIdSerie(), Types.BIGINT),
					ValueSQL.get(subserie.getCodigo(), Types.VARCHAR),
					ValueSQL.get(subserie.getNombre(), Types.VARCHAR),
					ValueSQL.get(subserie.getTiempoArchivoGestion(), Types.INTEGER),
					ValueSQL.get(subserie.getTiempoArchivoCentral(), Types.INTEGER),
					ValueSQL.get(subserie.isConservacionTotal() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(subserie.isMicrofilmacion() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(subserie.isSeleccion() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(subserie.isEliminacion() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(subserie.getProcedimiento(), Types.VARCHAR),
					ValueSQL.get(subserie.getIdUsuarioCreacion(), Types.INTEGER));

			// se verifica si el proceso se ejecuto sin problemas
			if (respuesta <= Numero.ZERO.valueI.intValue()) {
				throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
			}

			// se verifica si hay tipos documentales asociados a la sub-serie
			List<TipoDocumentalDTO> tiposDocumentales = subserie.getTiposDocumentales();
			if (tiposDocumentales != null && !tiposDocumentales.isEmpty()) {

				// se obtiene el identificador de la sub-serie documental creada
				idSubSerie = (Long) find(connection,
						CommonConstant.LAST_INSERT_ID,
						MapperTransversal.get(MapperTransversal.GET_ID));

				// se utiliza para insertar la relacion entre tipos documentales y la sub-serie documental SIN injections
				List<String> insertsTiposDocSerie = null;

				// se recorre cada tipo documental asociada a esta sub-serie
				List<ValueSQL> params;
				for (TipoDocumentalDTO tipoDocumental : tiposDocumentales) {

					// debe existir el ID o el NOMBRE del tipo documental para proceder con la asociacion
					if (tipoDocumental.getId() != null || !Util.isNull(tipoDocumental.getNombre())) {

						// puede llegar tipos documentales que no existan en BD
						if (tipoDocumental.getId() == null) {

							// se agrega lista para insertar los tipos documentales y la relacion de estos y la sub-serie documentales
							params = new ArrayList<>();
							params.add(ValueSQL.get(tipoDocumental.getNombre(), Types.VARCHAR));
							if (insertsTiposDocSerieINJ == null) {
								insertsTiposDocSerieINJ = new ArrayList<>();
							}
							insertsTiposDocSerieINJ.add(params);
						} else {
							// se agrega lista para insertar la relacion entre tipos documentales y sub-serie documental SIN injections
							if (insertsTiposDocSerie == null) {
								insertsTiposDocSerie = new ArrayList<>();
							}
							insertsTiposDocSerie.add(SQLArchivoGestion.insertTipoDocumentalSubSerie(tipoDocumental.getId(), idSubSerie));
						}
					}
				}

				// inserta los tipos documentales que no existen en BD y la relacion de estos con la sub-serie
				if (insertsTiposDocSerieINJ != null) {
					batchConInjection(connection,
							SQLArchivoGestion.insertTipoDocumental(idCliente),
							insertsTiposDocSerieINJ);
					batchConInjection(connection,
							SQLArchivoGestion.insertTipoDocumentalSubSerieSinID(idCliente, idSubSerie),
							insertsTiposDocSerieINJ);
				}

				// se inserta la relacion entre los tipos documentales y la sub-serie documental SIN injections
				if (insertsTiposDocSerie != null) {
					batchSinInjection(connection, insertsTiposDocSerie);
				}
			}
			connection.commit();

			// se verifica si se debe consultar los tipos documentales asociados a la nueva sub-serie
			if (insertsTiposDocSerieINJ != null) {
				return (List<TipoDocumentalDTO>) findParams(connection,
						SQLArchivoGestion.GET_TIPOS_DOCUMENTALES_SUBSERIE,
						MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOCUMENTALES_FILTRO),
						insertsTiposDocSerieINJ,
						ValueSQL.get(idSubSerie, Types.BIGINT));
			}
			return null;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite editar una subserie documental en el sistema
	 *
	 * @param subserie, DTO que contiene los datos de la subserie
	 * @return DTO con los datos del los tipos documentales y los atributos actualizados
	 */
	private ResponseEdicionSerieSubserieDTO editarSubSerieDocumental(
			SubSerieDocumentalDTO subserie,
			Connection connection) throws Exception {

		// se utilizan para varios proceso
		Long idSubSerie = subserie.getIdSubSerie();
		Integer idCliente = subserie.getIdCliente();

		// se valida el nombre y codigo solo si hay modificaciones en los datos generales
		if (subserie.isModificarDatosGenerales()) {

			// se verifica si hay otra subserie con el mismo NOMBRE para la EDICION
			if ((boolean) find(connection,
					SQLArchivoGestion.existsValorSubSerie("NOMBRE", idCliente, idSubSerie),
					MapperTransversal.get(MapperTransversal.IS_EXISTS),
					ValueSQL.get(subserie.getNombre(), Types.VARCHAR))) {
				throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_NOMBRE.value);
			}

			// se verifica si hay otra subserie con el mismo CODIGO para la EDICION
			if ((boolean) find(connection,
					SQLArchivoGestion.existsValorSubSerie("CODIGO", idCliente, idSubSerie),
					MapperTransversal.get(MapperTransversal.IS_EXISTS),
					ValueSQL.get(subserie.getCodigo(), Types.VARCHAR))) {
				throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_CODIGO.value);
			}
		}

		// para este proceso debe estar bajo transaccionalidad
		try {
			connection.setAutoCommit(false);

			// se verifica si se debe actualizar los datos generales de la subserie documental
			if (subserie.isModificarDatosGenerales()) {

				// se procede a editar la subserie documental
				int respuesta = insertUpdate(connection,
						SQLArchivoGestion.EDIT_SUBSERIE,
						ValueSQL.get(subserie.getCodigo(), Types.VARCHAR),
						ValueSQL.get(subserie.getNombre(), Types.VARCHAR),
						ValueSQL.get(subserie.getTiempoArchivoGestion(), Types.INTEGER),
						ValueSQL.get(subserie.getTiempoArchivoCentral(), Types.INTEGER),
						ValueSQL.get(subserie.isConservacionTotal() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(subserie.isMicrofilmacion() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(subserie.isSeleccion() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(subserie.isEliminacion() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(subserie.getProcedimiento(), Types.VARCHAR),
						ValueSQL.get(subserie.getIdSubSerie(), Types.BIGINT));

				// se verifica si el proceso se ejecuto sin problemas
				if (respuesta <= Numero.ZERO.valueI.intValue()) {
					throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
				}
			}

			// se utiliza para almacenar los nombres de los nuevos tipos documentales
			List<String> nombreTiposDocumentales = null;

			// se verifica si se debe modificar los tipos documentales
			if (subserie.isModificarTiposDocumentales()) {

				// se eliminan todos los tipos documentales de esta subserie
				List<String> dmls = new ArrayList<>();
				dmls.add(SQLArchivoGestion.deleteTiposDocSerieSubserie("TIPOS_DOCUMENTALES_SUBSERIES", "ID_SUBSERIE", idSubSerie));

				// se utiliza para insertar los tipos documentales y la relacion de estos y la subserie documentales
				List<List<ValueSQL>> insertsTiposDocSerieINJ = null;

				// se verifica si hay tipos documentales asociados a la subserie
				List<TipoDocumentalDTO> tiposDocumentales = subserie.getTiposDocumentales();
				if (tiposDocumentales != null && !tiposDocumentales.isEmpty()) {

					// se recorre cada tipo documental asociada a esta subserie
					List<ValueSQL> params;
					for (TipoDocumentalDTO tipoDocumental : tiposDocumentales) {

						// debe existir el ID o el NOMBRE del tipo documental para proceder con la asociacion
						if (tipoDocumental.getId() != null || !Util.isNull(tipoDocumental.getNombre())) {

							// puede llegar tipos documentales que no existan en BD
							if (tipoDocumental.getId() == null) {

								// se agrega lista para insertar los tipos documentales y la relacion de estos y la subserie documentales
								params = new ArrayList<>();
								params.add(ValueSQL.get(tipoDocumental.getNombre(), Types.VARCHAR));
								if (insertsTiposDocSerieINJ == null) {
									insertsTiposDocSerieINJ = new ArrayList<>();
								}
								insertsTiposDocSerieINJ.add(params);

								// se agrega el nombre como nuevo tipo documental
								if (nombreTiposDocumentales == null) {
									nombreTiposDocumentales = new ArrayList<>();
								}
								nombreTiposDocumentales.add(tipoDocumental.getNombre());
							} else {
								// se agrega lista para insertar la relacion entre tipos documentales y subserie documental SIN injections
								dmls.add(SQLArchivoGestion.insertTipoDocumentalSubSerie(tipoDocumental.getId(), idSubSerie));
							}
						}
					}
				}

				// se ejecuta los DML SIN injections
				batchSinInjection(connection, dmls);

				// inserta los tipos documentales que no existen en BD y la relacion de estos con la subserie
				if (insertsTiposDocSerieINJ != null) {
					batchConInjection(connection, SQLArchivoGestion.insertTipoDocumental(idCliente),
							insertsTiposDocSerieINJ);
					batchConInjection(connection,
							SQLArchivoGestion.insertTipoDocumentalSubSerieSinID(idCliente, idSubSerie),
							insertsTiposDocSerieINJ);
				}
			}

			// se inidica los cambios sobre la BD
			connection.commit();

			// DTO que contiene los datos a retornar
			ResponseEdicionSerieSubserieDTO response = new ResponseEdicionSerieSubserieDTO();

			// si hay modificaciones en los datos generales se procede a consultarlos
			Documental datosUpdate = null;
			if (subserie.isModificarDatosGenerales()) {
				datosUpdate = (Documental) find(connection,
						SQLArchivoGestion.getSQLDatosSerieSubserie(false, idSubSerie),
						MapperArchivoGestion.get(MapperArchivoGestion.GET_DATOS_DOCUMENTAL));
			}

			// si hay modificaciones en los tipos documentales se procede a consultarlos
			if (subserie.isModificarTiposDocumentales()) {

				// se obtiene los tipos documentales asociados a la subserie
				List<TipoDocumentalDTO> tiposDocumentales = (List<TipoDocumentalDTO>) findParams(
						connection,
						SQLArchivoGestion.GET_TIPOS_DOCUMENTALES_SUBSERIE,
						MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOCUMENTALES_FILTRO),
						null,
						ValueSQL.get(idSubSerie, Types.BIGINT));

				// se verifica si hay tipos documentales nuevos
				if (tiposDocumentales != null && !tiposDocumentales.isEmpty()) {

					// se configura los tipos documentales en el response
					datosUpdate = (datosUpdate == null) ? new Documental() : datosUpdate;
					datosUpdate.setTiposDocumentales(tiposDocumentales);

					// se configura los nuevos tipos documentales en el response
					if (nombreTiposDocumentales != null) {
						for (String nombre : nombreTiposDocumentales) {
							for (TipoDocumentalDTO tipoDocumental : tiposDocumentales) {
								if (tipoDocumental.getNombre().equals(nombre)) {
									response.agregarTipoDocumental(tipoDocumental);
									break;
								}
							}
						}
					}
				}
			}

			// se retorna el DTO con el response
			response.setDatosUpdate(datosUpdate);
			return response;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite eliminar una subserie documental
	 *
	 * @param subserie, Contiene los datos de la subserie
	 * @return lista de subseries asociados a la serie documental
	 */
	private List<SubSerieDocumentalDTO> eliminarSubSerieDocumental(
			SubSerieDocumentalDTO subserie,
			Connection connection) throws Exception {

		// se utiliza para varios proceso
		ValueSQL idSubSerie = ValueSQL.get(subserie.getIdSubSerie(), Types.BIGINT);

		// se verifica si la subserie tiene consecutivos asociados
		Long cant = (Long) find(connection,
				SQLArchivoGestion.GET_CANT_CONSECUTIVOS_SUBSERIE,
				MapperTransversal.get(MapperTransversal.GET_ID),
				idSubSerie);
		if (cant != null && !cant.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_CONSECUTIVOS.value);
		}

		// se verifica si la subserie esta asociado en la TRD
		if ((boolean) find(
				connection,
				SQLArchivoGestion.EXISTS_SUBSERIE_TRD,
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				idSubSerie)) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_TRD.value);
		}

		// se procede a eliminar la sub-serie con sus tablas asociadas
		batchSinInjection(connection, SQLArchivoGestion.deleteSubSerieDocumental(subserie.getIdSubSerie()));

		// se consultan las subseries asociadas a la serie propietaria de la subserie eliminada
		return getSubSeriesDocumental(subserie.getIdSerie(), connection);
	}

	/**
	 * Metodo que permite crear una serie documental en el sistema
	 *
	 * @param serie, DTO que contiene los datos de la serie
	 * @return tipos documentales que fueron asociados a la serie y no existian en BD
	 */
	private List<TipoDocumentalDTO> crearSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {

		// se utiliza para varios proceso
		Integer idCliente = serie.getIdCliente();

		// se verifica si hay otra serie con el mismo NOMBRE
		if ((boolean) find(connection,
				SQLArchivoGestion.existsValorSerie("NOMBRE", idCliente, null),
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				ValueSQL.get(serie.getNombre(), Types.VARCHAR))) {
			throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_NOMBRE.value);
		}

		// se verifica si hay otra serie con el mismo CODIGO
		if ((boolean) find(connection,
				SQLArchivoGestion.existsValorSerie("CODIGO", idCliente, null),
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				ValueSQL.get(serie.getCodigo(), Types.VARCHAR))) {
			throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_CODIGO.value);
		}

		// para este proceso debe estar bajo transaccionalidad
		try {
			connection.setAutoCommit(false);

			// identificador de la nueva serie documental
			Long idSerie = null;

			// si esta variable contiene informacion indica que se debe consultar los tipos documentales
			List<List<ValueSQL>> insertsTiposDocSerieINJ = null;

			// se procede a insertar la serie
			int respuesta = insertUpdate(connection,
					SQLArchivoGestion.INSERT_SERIE,
					ValueSQL.get(idCliente, Types.INTEGER),
					ValueSQL.get(serie.getCodigo(), Types.VARCHAR),
					ValueSQL.get(serie.getNombre(), Types.VARCHAR),
					ValueSQL.get(serie.getTiempoArchivoGestion(), Types.INTEGER),
					ValueSQL.get(serie.getTiempoArchivoCentral(), Types.INTEGER),
					ValueSQL.get(serie.isConservacionTotal() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(serie.isMicrofilmacion() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(serie.isSeleccion() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(serie.isEliminacion() ? Numero.UNO.valueI : null, Types.INTEGER),
					ValueSQL.get(serie.getProcedimiento(), Types.VARCHAR),
					ValueSQL.get(serie.getIdUsuarioCreacion(), Types.INTEGER));

			// se verifica si el proceso se ejecuto sin problemas
			if (respuesta <= Numero.ZERO.valueI.intValue()) {
				throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
			}

			// se verifica si hay tipos documentales asociados a la serie
			List<TipoDocumentalDTO> tiposDocumentales = serie.getTiposDocumentales();
			if (tiposDocumentales != null && !tiposDocumentales.isEmpty()) {

				// se obtiene el identificador de la serie documental creada
				idSerie = (Long) find(connection,
						CommonConstant.LAST_INSERT_ID,
						MapperTransversal.get(MapperTransversal.GET_ID));

				// se utiliza para insertar la relacion entre tipos documentales y serie documental SIN injections
				List<String> insertsTiposDocSerie = null;

				// se recorre cada tipo documental asociada a esta serie
				List<ValueSQL> params;
				for (TipoDocumentalDTO tipoDocumental : tiposDocumentales) {

					// debe existir el ID o el NOMBRE del tipo documental para proceder con la asociacion
					if (tipoDocumental.getId() != null || !Util.isNull(tipoDocumental.getNombre())) {

						// puede llegar tipos documentales que no existan en BD
						if (tipoDocumental.getId() == null) {

							// se agrega lista para insertar los tipos documentales y la relacion de estos y la serie documentales
							params = new ArrayList<>();
							params.add(ValueSQL.get(tipoDocumental.getNombre(), Types.VARCHAR));
							if (insertsTiposDocSerieINJ == null) {
								insertsTiposDocSerieINJ = new ArrayList<>();
							}
							insertsTiposDocSerieINJ.add(params);
						} else {
							// se agrega lista para insertar la relacion entre tipos documentales y serie documental SIN injections
							if (insertsTiposDocSerie == null) {
								insertsTiposDocSerie = new ArrayList<>();
							}
							insertsTiposDocSerie.add(SQLArchivoGestion.insertTipoDocumentalSerie(tipoDocumental.getId(), idSerie));
						}
					}
				}

				// inserta los tipos documentales que no existen en BD y la relacion de estos con la serie
				if (insertsTiposDocSerieINJ != null) {
					batchConInjection(connection,
							SQLArchivoGestion.insertTipoDocumental(idCliente),
							insertsTiposDocSerieINJ);
					batchConInjection(connection,
							SQLArchivoGestion.insertTipoDocumentalSerieSinID(idCliente, idSerie),
							insertsTiposDocSerieINJ);
				}

				// se inserta la relacion entre los tipos documentales y la serie documental SIN injections
				if (insertsTiposDocSerie != null) {
					batchSinInjection(connection, insertsTiposDocSerie);
				}
			}
			connection.commit();

			// se verifica si se debe consultar los tipos documentales asociados a la nueva serie
			if (insertsTiposDocSerieINJ != null) {
				return (List<TipoDocumentalDTO>) findParams(connection,
						SQLArchivoGestion.GET_TIPOS_DOCUMENTALES_SERIE,
						MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOCUMENTALES_FILTRO),
						insertsTiposDocSerieINJ,
						ValueSQL.get(idSerie, Types.BIGINT));
			}
			return null;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite editar una serie documental en el sistema
	 *
	 * @param serie, DTO que contiene los datos de la serie
	 * @return DTO con los datos del los tipos documentales y los atributos actualizados
	 */
	private ResponseEdicionSerieSubserieDTO editarSerieDocumental(
			SerieDocumentalDTO serie,
			Connection connection) throws Exception {

		// se utilizan para varios proceso
		Integer idCliente = serie.getIdCliente();
		Long idSerie = serie.getIdSerie();

		// se valida el nombre y codigo solo si hay modificaciones en los datos generales
		if (serie.isModificarDatosGenerales()) {

			// se verifica si hay otra serie con el mismo NOMBRE
			if ((boolean) find(connection,
					SQLArchivoGestion.existsValorSerie("NOMBRE", idCliente, idSerie),
					MapperTransversal.get(MapperTransversal.IS_EXISTS),
					ValueSQL.get(serie.getNombre(), Types.VARCHAR))) {
				throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_NOMBRE.value);
			}

			// se verifica si hay otra serie con el mismo CODIGO
			if ((boolean) find(connection,
					SQLArchivoGestion.existsValorSerie("CODIGO", idCliente, idSerie),
					MapperTransversal.get(MapperTransversal.IS_EXISTS),
					ValueSQL.get(serie.getCodigo(), Types.VARCHAR))) {
				throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_CODIGO.value);
			}
		}

		// para este proceso debe estar bajo transaccionalidad
		try {
			connection.setAutoCommit(false);

			// se verifica si se debe actualizar los datos generales de la serie documental
			if (serie.isModificarDatosGenerales()) {

				// se procede a editar los datos generales de la serie
				int respuesta = insertUpdate(connection,
						SQLArchivoGestion.EDIT_SERIE,
						ValueSQL.get(serie.getCodigo(), Types.VARCHAR),
						ValueSQL.get(serie.getNombre(), Types.VARCHAR),
						ValueSQL.get(serie.getTiempoArchivoGestion(), Types.INTEGER),
						ValueSQL.get(serie.getTiempoArchivoCentral(), Types.INTEGER),
						ValueSQL.get(serie.isConservacionTotal() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(serie.isMicrofilmacion() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(serie.isSeleccion() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(serie.isEliminacion() ? Numero.UNO.valueI : null, Types.INTEGER),
						ValueSQL.get(serie.getProcedimiento(), Types.VARCHAR),
						ValueSQL.get(idSerie, Types.BIGINT));

				// se verifica si el proceso se ejecuto sin problemas
				if (respuesta <= Numero.ZERO.valueI.intValue()) {
					throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
				}
			}

			// se utiliza para almacenar los nombres de los nuevos tipos documentales
			List<String> nombreTiposDocumentales = null;

			// se verifica si se debe modificar los tipos documentales
			if (serie.isModificarTiposDocumentales()) {

				// se eliminan todos los tipos documentales de esta serie
				List<String> dmls = new ArrayList<>();
				dmls.add(SQLArchivoGestion.deleteTiposDocSerieSubserie("TIPOS_DOCUMENTALES_SERIES", "ID_SERIE", idSerie));

				// se utiliza para insertar los tipos documentales y la relacion de estos y la serie documentales
				List<List<ValueSQL>> insertsTiposDocSerieINJ = null;

				// se verifica si hay tipos documentales asociados a la serie
				List<TipoDocumentalDTO> tiposDocumentales = serie.getTiposDocumentales();
				if (tiposDocumentales != null && !tiposDocumentales.isEmpty()) {

					// se recorre cada tipo documental asociada a esta serie
					List<ValueSQL> params;
					for (TipoDocumentalDTO tipoDocumental : tiposDocumentales) {

						// debe existir el ID o el NOMBRE del tipo documental para proceder con la asociacion
						if (tipoDocumental.getId() != null || !Util.isNull(tipoDocumental.getNombre())) {

							// puede llegar tipos documentales que no existan en BD
							if (tipoDocumental.getId() == null) {

								// se agrega lista para insertar los tipos documentales y la relacion de estos y la serie documentales
								params = new ArrayList<>();
								params.add(ValueSQL.get(tipoDocumental.getNombre(), Types.VARCHAR));
								if (insertsTiposDocSerieINJ == null) {
									insertsTiposDocSerieINJ = new ArrayList<>();
								}
								insertsTiposDocSerieINJ.add(params);

								// se agrega el nombre como nuevo tipo documental
								if (nombreTiposDocumentales == null) {
									nombreTiposDocumentales = new ArrayList<>();
								}
								nombreTiposDocumentales.add(tipoDocumental.getNombre());
							} else {
								// se agrega lista para insertar la relacion entre tipos documentales y serie documental SIN injections
								dmls.add(SQLArchivoGestion.insertTipoDocumentalSerie(tipoDocumental.getId(), idSerie));
							}
						}
					}
				}

				// se ejecuta los DML SIN injections
				batchSinInjection(connection, dmls);

				// inserta los tipos documentales que no existen en BD y la relacion de estos con la serie
				if (insertsTiposDocSerieINJ != null) {
					batchConInjection(connection, SQLArchivoGestion.insertTipoDocumental(idCliente),
							insertsTiposDocSerieINJ);
					batchConInjection(connection,
							SQLArchivoGestion.insertTipoDocumentalSerieSinID(idCliente, idSerie),
							insertsTiposDocSerieINJ);
				}
			}

			// se inidica los cambios sobre la BD
			connection.commit();

			// DTO que contiene los datos a retornar
			ResponseEdicionSerieSubserieDTO response = new ResponseEdicionSerieSubserieDTO();

			// si hay modificaciones en los datos generales se procede a consultarlos
			Documental datosUpdate = null;
			if (serie.isModificarDatosGenerales()) {
				datosUpdate = (Documental) find(connection,
						SQLArchivoGestion.getSQLDatosSerieSubserie(true, idSerie),
						MapperArchivoGestion.get(MapperArchivoGestion.GET_DATOS_DOCUMENTAL));
			}

			// si hay modificaciones en los tipos documentales se procede a consultarlos
			if (serie.isModificarTiposDocumentales()) {

				// se obtiene los tipos documentales asociados a al serie
				List<TipoDocumentalDTO> tiposDocumentales = (List<TipoDocumentalDTO>) findParams(
						connection,
						SQLArchivoGestion.GET_TIPOS_DOCUMENTALES_SERIE,
						MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOCUMENTALES_FILTRO),
						null,
						ValueSQL.get(idSerie, Types.BIGINT));

				// se verifica si hay tipos documentales nuevos
				if (tiposDocumentales != null && !tiposDocumentales.isEmpty()) {

					// se configura los tipos documentales en el response
					datosUpdate = (datosUpdate == null) ? new Documental() : datosUpdate;
					datosUpdate.setTiposDocumentales(tiposDocumentales);

					// se configura los nuevos tipos documentales en el response
					if (nombreTiposDocumentales != null) {
						for (String nombre : nombreTiposDocumentales) {
							for (TipoDocumentalDTO tipoDocumental : tiposDocumentales) {
								if (tipoDocumental.getNombre().equals(nombre)) {
									response.agregarTipoDocumental(tipoDocumental);
									break;
								}
							}
						}
					}
				}
			}

			// se retorna el DTO con el response
			response.setDatosUpdate(datosUpdate);
			return response;
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Metodo que permite eliminar una serie documental en el sistema
	 *
	 * @param serie, DTO que contiene los datos de la serie a eliminar
	 * @return Response con la lista de series documentales
	 */
	private PaginadorResponseDTO eliminarSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {

		// se utiliza para varios proceso
		ValueSQL idSerie = ValueSQL.get(serie.getIdSerie(), Types.BIGINT);

		// se verifica si la serie tiene consecutivos asociados
		Long cant = (Long) find(connection,
				SQLArchivoGestion.GET_CANT_CONSECUTIVOS_SERIE,
				MapperTransversal.get(MapperTransversal.GET_ID),
				idSerie);
		if (cant != null && !cant.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_CONSECUTIVOS.value);
		}

		// se verifica si tiene subseries documentales
		if ((boolean) find(
				connection,
				SQLArchivoGestion.EXISTS_SUBSERIES_SERIE,
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				idSerie)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_TIENE_SUBSERIE.value);
		}

		// se verifica si la serie esta asociado en la TRD
		if ((boolean) find(
				connection,
				SQLArchivoGestion.EXISTS_SERIE_TRD,
				MapperTransversal.get(MapperTransversal.IS_EXISTS),
				idSerie)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_TRD.value);
		}

		// se procede a eliminar la serie con sus tablas asociadas
		batchSinInjection(connection, SQLArchivoGestion.deleteSerieDocumental(serie.getIdSerie()));

		// se retorna las series documentales de acuerdo al filtro
		return getSeriesDocumentales(serie.getFiltro(), connection);
	}
}
