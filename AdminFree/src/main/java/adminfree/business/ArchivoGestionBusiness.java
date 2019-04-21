package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.List;

import adminfree.constants.SQLArchivoGestion;
import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
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
	 * Metodo que permite administrar los tipos documentales
	 *
	 * @param tipo, contiene los datos del tipo documental a procesar
	 * @return Objeto con el resultado solicitado
	 */
	public Object administrarTiposDocumentales(TipoDocumentalDTO tipo, Connection connection) throws Exception {
		Object response = null;

		// se captura el tipo de evento
		String tipoEvento = tipo.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.LISTAR:
				response = getTiposDocumentales(connection);
				break;

			case TipoEvento.CREAR:
				crearTipoDocumental(tipo, connection);
				response = Util.getResponseOk();
				break;

			case TipoEvento.EDITAR:
				editarTipoDocumental(tipo, connection);
				response = Util.getResponseOk();
				break;

			case TipoEvento.ELIMINAR:
				eliminarTipoDocumental(tipo, connection);
				response = Util.getResponseOk();
				break;
		}
		return response;
	}

	/**
	 * Metodo que permite administrar la entidad de series documentales
	 *
	 * @param serie, DTO con los datos de la serie documental
	 * @return Objeto con el resultado solicitado
	 */
	public Object administrarSerieDocumental(SerieDocumentalDTO serie, Connection connection) throws Exception {
		Object response = null;

		// se captura el tipo de evento
		String tipoEvento = serie.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.CREAR:
				crearSerieDocumental(serie, connection);
				response = Util.getResponseOk();
				break;

			case TipoEvento.EDITAR:
				editarSerieDocumental(serie, connection);
				response = Util.getResponseOk();
				break;

			case TipoEvento.ELIMINAR:
				eliminarSerieDocumental(serie, connection);
				response = Util.getResponseOk();
				break;
		}
		return response;
	}

	/**
	 * Metodo que permite administrar la entidad de sub-serie documental
	 *
	 * @param subserie, DTO con los datos de la sub-serie documental
	 * @return Objeto con el resultado solicitado
	 */
	public Object administrarSubSerieDocumental(SubSerieDocumentalDTO subserie, Connection connection) throws Exception {
		Object response = null;

		// se captura el tipo de evento
		String tipoEvento = subserie.getTipoEvento();

		// se invoca de acuerdo al tipo de evento
		switch (tipoEvento) {

			case TipoEvento.CREAR:
				crearSubSerieDocumental(subserie, connection);
				response = Util.getResponseOk();
				break;

			case TipoEvento.EDITAR:
				editarSubSerieDocumental(subserie, connection);
				response = Util.getResponseOk();
				break;

			case TipoEvento.ELIMINAR:
				eliminarSubSerieDocumental(subserie, connection);
				response = Util.getResponseOk();
				break;
		}
		return response;
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
		int respuesta = insertUpdate(connection,
				SQLArchivoGestion.INSERT_SUBSERIE,
				ValueSQL.get(subserie.getIdSerie(), Types.BIGINT),
				ValueSQL.get(subserie.getCodigo(), Types.VARCHAR),
				ValueSQL.get(subserie.getNombre(), Types.VARCHAR),
				ValueSQL.get(subserie.getAG(), Types.INTEGER),
				ValueSQL.get(subserie.getAC(), Types.INTEGER),
				ValueSQL.get(subserie.getCT(), Types.INTEGER),
				ValueSQL.get(subserie.getM(), Types.INTEGER),
				ValueSQL.get(subserie.getS(), Types.INTEGER),
				ValueSQL.get(subserie.getE(), Types.INTEGER),
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
				ValueSQL.get(subserie.getCT(), Types.INTEGER),
				ValueSQL.get(subserie.getM(), Types.INTEGER),
				ValueSQL.get(subserie.getS(), Types.INTEGER),
				ValueSQL.get(subserie.getE(), Types.INTEGER),
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
				ValueSQL.get(serie.getCT(), Types.INTEGER),
				ValueSQL.get(serie.getM(), Types.INTEGER),
				ValueSQL.get(serie.getS(), Types.INTEGER),
				ValueSQL.get(serie.getE(), Types.INTEGER),
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
				ValueSQL.get(serie.getCT(), Types.INTEGER),
				ValueSQL.get(serie.getM(), Types.INTEGER),
				ValueSQL.get(serie.getS(), Types.INTEGER),
				ValueSQL.get(serie.getE(), Types.INTEGER),
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
	 * Metodo que permite obtener todos los tipos documentales parametrizados
	 *
	 * @return Lista de tipos documentales
	 */
	private List<TipoDocumentalDTO> getTiposDocumentales(Connection connection) throws Exception {
		return (List<TipoDocumentalDTO>) findAll(connection,
				SQLArchivoGestion.GET_TIPOS_DOCUMENTALES,
				MapperArchivoGestion.get(MapperArchivoGestion.GET_TIPOS_DOCUMENTALES));
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
