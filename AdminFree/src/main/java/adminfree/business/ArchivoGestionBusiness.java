package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import adminfree.constants.SQLArchivoGestion;
import adminfree.constants.SQLTransversal;
import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.FiltroSerieDocumentalDTO;
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

/**
 * Clase que contiene los procesos de negocio para el modulo de Archivo de Gestion
 *
 * @author Carlos Andres Diaz
 *
 */
@SuppressWarnings("unchecked")
public class ArchivoGestionBusiness extends CommonDAO {

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
		SQLTransversal.getFilterSubConsultaCount(
				parametros,
				from,
				filtro.getNombreSubSerieDocumental(),
				"SELECT COUNT(*) FROM SUBSERIES_DOCUMENTALES SUB WHERE SUB.ID_SERIE=SE.ID_SERIE AND SUB.NOMBRE");

		// filtro por codigo de la sub-serie documental
		SQLTransversal.getFilterSubConsultaCount(
				parametros,
				from,
				filtro.getCodigoSubSerieDocumental(),
				"SELECT COUNT(*) FROM SUBSERIES_DOCUMENTALES SUB WHERE SUB.ID_SERIE=SE.ID_SERIE AND SUB.CODIGO");

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
			StringBuilder sql = new StringBuilder("SELECT SE.ID_SERIE,SE.CODIGO,SE.NOMBRE,SE.AG,SE.AC,SE.CT,SE.M,SE.S,SE.E,SE.PROCEDIMIENTO ");
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
					SQLArchivoGestion.getSQLSubseries(idsSeries),
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
	 * Metodo que permite obtener todos los tipos documentales parametrizados
	 *
	 * @return Lista de tipos documentales
	 */
	public List<TipoDocumentalDTO> getTiposDocumentales(Connection connection) throws Exception {
		return (List<TipoDocumentalDTO>) findAll(connection,
				SQLArchivoGestion.GET_TIPOS_DOCUMENTALES,
				MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOCUMENTALES));
	}

	/**
	 * Metodo que permite administrar los tipos documentales
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param tipo, contiene los datos del tipo documental a procesar
	 */
	public void administrarTiposDocumentales(TipoDocumentalDTO tipo, Connection connection) throws Exception {

		// se captura el tipo de evento
		String tipoEvento = tipo.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.CREAR:
				crearTipoDocumental(tipo, connection);
				break;

			case TipoEvento.EDITAR:
				editarTipoDocumental(tipo, connection);
				break;

			case TipoEvento.ELIMINAR:
				eliminarTipoDocumental(tipo, connection);
				break;
		}
	}

	/**
	 * Metodo que permite administrar la entidad de series documentales
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param serie, DTO con los datos de la serie documental
	 */
	public void administrarSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {

		// se captura el tipo de evento
		String tipoEvento = serie.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.CREAR:
				crearSerieDocumental(serie, connection);
				break;

			case TipoEvento.EDITAR:
				editarSerieDocumental(serie, connection);
				break;

			case TipoEvento.ELIMINAR:
				eliminarSerieDocumental(serie, connection);
				break;
		}
	}

	/**
	 * Metodo que permite administrar la entidad de sub-serie documental
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param subserie, DTO con los datos de la sub-serie documental
	 */
	public void administrarSubSerieDocumental(SubSerieDocumentalDTO subserie, Connection connection) throws Exception {

		// se captura el tipo de evento
		String tipoEvento = subserie.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.CREAR:
				crearSubSerieDocumental(subserie, connection);
				break;

			case TipoEvento.EDITAR:
				editarSubSerieDocumental(subserie, connection);
				break;

			case TipoEvento.ELIMINAR:
				eliminarSubSerieDocumental(subserie, connection);
				break;
		}
	}

	/**
	 * Metodo que permite crear una subserie documental en el sistema
	 * @param subserie, DTO que contiene los datos de la subserie
	 */
	private void crearSubSerieDocumental(SubSerieDocumentalDTO subserie, Connection connection) throws Exception {

		// se utiliza para varios proceso
		ValueSQL idCliente = ValueSQL.get(subserie.getIdCliente(), Types.INTEGER);

		// se verifica si hay otra subserie con el mismo NOMBRE
		Long count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SUBSERIES_NOMBRE_CREACION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(subserie.getNombre(), Types.VARCHAR),
				idCliente);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_NOMBRE.value);
		}

		// se verifica si hay otra subserie con el mismo CODIGO
		count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SUBSERIES_CODIGO_CREACION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(subserie.getCodigo(), Types.VARCHAR),
				idCliente);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_CODIGO.value);
		}

		// se procede a insertar la subserie
		int respuesta = insertUpdate(
				connection,
				SQLArchivoGestion.INSERT_SUBSERIE,
				ValueSQL.get(subserie.getIdSerie(), Types.BIGINT),
				ValueSQL.get(subserie.getCodigo(), Types.VARCHAR),
				ValueSQL.get(subserie.getNombre(), Types.VARCHAR),
				ValueSQL.get(subserie.getAG(), Types.INTEGER),
				ValueSQL.get(subserie.getAC(), Types.INTEGER),
				ValueSQL.get(subserie.isCT() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.isM() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.isS() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.isE() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.getProcedimiento(), Types.VARCHAR),
				ValueSQL.get(subserie.getIdUsuarioCreacion(), Types.INTEGER));

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite editar una subserie documental en el sistema
	 * @param subserie, DTO que contiene los datos de la subserie
	 */
	private void editarSubSerieDocumental(SubSerieDocumentalDTO subserie, Connection connection) throws Exception {

		// se utilizan para varios proceso
		ValueSQL idSubSerie = ValueSQL.get(subserie.getIdSubSerie(), Types.BIGINT);
		ValueSQL idCliente = ValueSQL.get(subserie.getIdCliente(), Types.INTEGER);

		// se verifica si hay otra subserie con el mismo NOMBRE para EDICION
		Long count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SUBSERIES_NOMBRE_EDICION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(subserie.getNombre(), Types.VARCHAR),
				idCliente, idSubSerie);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_NOMBRE.value);
		}

		// se verifica si hay otra subserie con el mismo CODIGO para EDICION
		count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SUBSERIES_CODIGO_EDICION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(subserie.getCodigo(), Types.VARCHAR),
				idCliente, idSubSerie);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_MISMO_CODIGO.value);
		}

		// se procede a editar la subserie documental
		int respuesta = insertUpdate(connection,
				SQLArchivoGestion.EDIT_SUBSERIE,
				ValueSQL.get(subserie.getIdSerie(), Types.BIGINT),
				ValueSQL.get(subserie.getCodigo(), Types.VARCHAR),
				ValueSQL.get(subserie.getNombre(), Types.VARCHAR),
				ValueSQL.get(subserie.getAG(), Types.INTEGER),
				ValueSQL.get(subserie.getAC(), Types.INTEGER),
				ValueSQL.get(subserie.isCT() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.isM() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.isS() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.isE() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(subserie.getProcedimiento(), Types.VARCHAR),
				idSubSerie);

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite eliminar una subserie documental
	 *
	 * @param subserie, Contiene los datos de la subserie
	 */
	private void eliminarSubSerieDocumental(SubSerieDocumentalDTO subserie, Connection connection) throws Exception {

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
		Long count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SUBSERIE_TRD,
				MapperTransversal.get(MapperTransversal.COUNT),
				idSubSerie);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SUBSERIE_TRD.value);
		}

		// se procede a eliminar la subserie
		int respuesta = insertUpdate(connection, SQLArchivoGestion.DELETE_SUBSERIE, idSubSerie);

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite crear una serie documental en el sistema
	 * @param serie, DTO que contiene los datos de la serie
	 */
	private void crearSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {

		// se utiliza para varios proceso
		ValueSQL idCliente = ValueSQL.get(serie.getIdCliente(), Types.INTEGER);

		// se verifica si hay otra serie con el mismo NOMBRE
		Long count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SERIES_NOMBRE_CREACION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(serie.getNombre(), Types.VARCHAR),
				idCliente);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_NOMBRE.value);
		}

		// se verifica si hay otra serie con el mismo CODIGO
		count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SERIES_CODIGO_CREACION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(serie.getCodigo(), Types.VARCHAR),
				idCliente);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_CODIGO.value);
		}

		// se procede a insertar la serie
		int respuesta = insertUpdate(connection,
				SQLArchivoGestion.INSERT_SERIE,
				idCliente,
				ValueSQL.get(serie.getCodigo(), Types.VARCHAR),
				ValueSQL.get(serie.getNombre(), Types.VARCHAR),
				ValueSQL.get(serie.getAG(), Types.INTEGER),
				ValueSQL.get(serie.getAC(), Types.INTEGER),
				ValueSQL.get(serie.isCT() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.isM() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.isS() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.isE() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.getProcedimiento(), Types.VARCHAR),
				ValueSQL.get(serie.getIdUsuarioCreacion(), Types.INTEGER));

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite editar una serie documental en el sistema
	 * @param serie, DTO que contiene los datos de la serie
	 */
	private void editarSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {

		// se utilizan para varios proceso
		ValueSQL idSerie = ValueSQL.get(serie.getIdSerie(), Types.BIGINT);
		ValueSQL idCliente = ValueSQL.get(serie.getIdCliente(), Types.INTEGER);

		// se verifica si hay otra serie con el mismo NOMBRE
		Long count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SERIES_NOMBRE_EDICION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(serie.getNombre(), Types.VARCHAR),
				idSerie, idCliente);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_NOMBRE.value);
		}

		// se verifica si hay otra serie con el mismo CODIGO
		count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SERIES_CODIGO_EDICION,
				MapperTransversal.get(MapperTransversal.COUNT),
				ValueSQL.get(serie.getCodigo(), Types.VARCHAR),
				idSerie, idCliente);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_MISMO_CODIGO.value);
		}

		// se procede a editar la serie documental
		int respuesta = insertUpdate(connection,
				SQLArchivoGestion.EDIT_SERIE,
				ValueSQL.get(serie.getCodigo(), Types.VARCHAR),
				ValueSQL.get(serie.getNombre(), Types.VARCHAR),
				ValueSQL.get(serie.getAG(), Types.INTEGER),
				ValueSQL.get(serie.getAC(), Types.INTEGER),
				ValueSQL.get(serie.isCT() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.isM() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.isS() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.isE() ? Numero.UNO.valueI : null, Types.INTEGER),
				ValueSQL.get(serie.getProcedimiento(), Types.VARCHAR),
				idSerie);

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite eliminar una serie documental en el sistema
	 * @param serie, DTO que contiene los datos de la serie
	 */
	private void eliminarSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {

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
		Long count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SUBSERIES_SERIE,
				MapperTransversal.get(MapperTransversal.COUNT),
				idSerie);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_TIENE_SUBSERIE.value);
		}

		// se verifica si la serie esta asociado en la TRD
		count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SERIE_TRD,
				MapperTransversal.get(MapperTransversal.COUNT),
				idSerie);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_SERIE_TRD.value);
		}

		// se procede a eliminar la serie
		int respuesta = insertUpdate(connection, SQLArchivoGestion.DELETE_SERIE, idSerie);

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite crear un tipo documental en el sistema
	 *
	 * @param tipo, DTO Con los datos del tipo documental a crear
	 */
	private void crearTipoDocumental(TipoDocumentalDTO tipo, Connection connection) throws Exception {

		// se procede a CREAR el tipo documental
		int respuesta = insertUpdate(connection,
				SQLArchivoGestion.INSERT_TIPO_DOCUMENTAL,
				ValueSQL.get(tipo.getNombre(), Types.VARCHAR));

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite editar un tipo documental en el sistema
	 *
	 * @param tipo, DTO Con los datos del tipo documental a editar
	 */
	private void editarTipoDocumental(TipoDocumentalDTO tipo, Connection connection) throws Exception {

		// se procede a EDITAR el tipo documental
		int respuesta = insertUpdate(connection,
				SQLArchivoGestion.EDITAR_TIPO_DOCUMENTAL,
				ValueSQL.get(tipo.getNombre(), Types.VARCHAR),
				ValueSQL.get(tipo.getId(), Types.INTEGER));

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}

	/**
	 * Metodo que permite eliminar un tipo documental en el sistema
	 *
	 * @param tipo, DTO con los datos del tipo documental a eliminar
	 */
	private void eliminarTipoDocumental(TipoDocumentalDTO tipo, Connection connection) throws Exception {

		// se utiliza para el proceso de eliminacion
		ValueSQL idTipoDocumental = ValueSQL.get(tipo.getId(), Types.INTEGER);

		// se verifica si el tipo documental se encuentra asociado a una SERIE
		Long count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SERIES_TIPO_DOCUMENTAL,
				MapperTransversal.get(MapperTransversal.COUNT),
				idTipoDocumental);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_ELIMINAR_TIPO_DOCUMENTAL.value);
		}

		// se verifica si el tipo documental se encuentra asociado a una SUBSERIE
		count = (Long) find(connection,
				SQLArchivoGestion.COUNT_SUBSERIES_TIPO_DOCUMENTAL,
				MapperTransversal.get(MapperTransversal.COUNT),
				idTipoDocumental);
		if (!count.equals(Numero.ZERO.valueL)) {
			throw new BusinessException(MessagesKey.KEY_ELIMINAR_TIPO_DOCUMENTAL.value);
		}

		// se realiza la eliminacion del tipo documental
		int respuesta = insertUpdate(connection, SQLArchivoGestion.ELIMINAR_TIPO_DOCUMENTAL, idTipoDocumental);

		// se verifica si el proceso se ejecuto sin problemas
		if (respuesta <= Numero.ZERO.valueI.intValue()) {
			throw new BusinessException(MessagesKey.KEY_PROCESO_NO_EJECUTADO.value);
		}
	}
}
