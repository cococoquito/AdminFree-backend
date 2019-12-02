package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLEnglish;
import adminfree.dtos.english.SeriesDTO;
import adminfree.mappers.MapperEnglish;
import adminfree.mappers.MapperTransversal;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.ValueSQL;

/**
 * Clase que contiene los procesos de negocio para el modulo de Learning English
 *
 * @author Carlos Andres Diaz
 *
 */
@SuppressWarnings("unchecked")
public class EnglishBusiness extends CommonDAO {

	/**
	 * Metodo que permite crear una serie en el sistema
	 *
	 * @param serie, DTO que contiene los datos de la serie a crear
	 * @return DTO con el identificador de la serie
	 */
	public SeriesDTO crearSerie(SeriesDTO serie, Connection connection) throws Exception {

		// se procede a crear la SERIE
		insertUpdate(connection,
				SQLEnglish.CREAR_SERIE,
				ValueSQL.get(serie.getName(), Types.VARCHAR),
				ValueSQL.get(serie.getUrl(), Types.VARCHAR));

		// se obtiene el identificador de la nueva serie
		Long idSerie = (Long) find(connection,
				CommonConstant.LAST_INSERT_ID,
				MapperTransversal.get(MapperTransversal.GET_ID));

		// DTO con el identificador a retornar
		SeriesDTO response = new SeriesDTO();
		response.setId(idSerie);
		return response;
	}

	/**
	 * Metodo para asociar la imagen a la serie
	 *
	 * @param img, es la imagen para asociar
	 * @param idSerie, identificador de la serie asociar la imagen
	 */
	public void downloadImgSerie(byte[] img ,String idSerie, Connection connection) throws Exception {
		insertUpdate(connection,
				SQLEnglish.ASOCIAR_IMG_SERIE,
				ValueSQL.get(img, Types.BLOB),
				ValueSQL.get(idSerie, Types.VARCHAR));
	}

	/**
	 * Metodo que permite cargar las series parametrizadas en el sistema
	 */
	public List<SeriesDTO> getSeries(Connection connection) throws Exception {
		return (List<SeriesDTO>) findAll(
				connection,
				SQLEnglish.GET_SERIES,
				MapperEnglish.get(MapperEnglish.GET_SERIES));
	}
}
