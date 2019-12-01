package adminfree.business;

import java.sql.Connection;
import java.sql.Types;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLEnglish;
import adminfree.dtos.english.SeriesDTO;
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
}
