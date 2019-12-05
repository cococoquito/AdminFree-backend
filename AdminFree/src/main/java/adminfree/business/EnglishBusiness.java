package adminfree.business;

import java.sql.Connection;
import java.sql.Types;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.constants.SQLEnglish;
import adminfree.dtos.english.ChapterDTO;
import adminfree.dtos.english.SeasonDTO;
import adminfree.dtos.english.SerieDTO;
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
	public SerieDTO crearSerie(SerieDTO serie, Connection connection) throws Exception {

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
		SerieDTO response = new SerieDTO();
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
	public List<SerieDTO> getSeries(Connection connection) throws Exception {
		return (List<SerieDTO>) findAll(
				connection,
				SQLEnglish.GET_SERIES,
				MapperEnglish.get(MapperEnglish.GET_SERIES));
	}

	/**
	 * Metodo que permite obtener los detalles de una serie
	 * @param idSerie, identificador de la serie
	 * @return DTO con el detalle de la serie
	 */
	public SerieDTO getDetailSerie(Long idSerie, Connection connection) throws Exception {

		// se utiliza para encapsular los ids de cada temporada
		StringBuilder idsSeason = new StringBuilder();

		// se procede a consultar el detalle de esta serie
		SerieDTO serie = (SerieDTO) findParams(connection,
				SQLEnglish.GET_DETAIL_SERIE,
				MapperEnglish.get(MapperEnglish.GET_DETAIL_SERIE),
				idsSeason,
				ValueSQL.get(idSerie, Types.BIGINT));
		serie.setId(idSerie);

		// se verifica si esta serie tiene temporadas asociadas
		List<SeasonDTO> seasons = serie.getSeasons();
		if (seasons != null && !seasons.isEmpty()) {

			// se consulta los capitulos para cada temporada
			findParams(connection,
					SQLEnglish.getSQLChaptersSeason(idsSeason.toString()),
					MapperEnglish.get(MapperEnglish.GET_CHAPTERS_SEASON),
					seasons);
		}
		return serie;
	}

	/**
	 * Metodo que permite agregar una nueva temporada
	 * @param idSerie, identificador de la serie
	 * @return DTO con el detalle de la serie
	 */
	public SerieDTO addSeason(Long idSerie, Connection connection) throws Exception {

		// se agrega la nueva temporada
		insertUpdate(connection,
				SQLEnglish.ADD_SEASON,
				ValueSQL.get(idSerie, Types.BIGINT));

		// se procede a consultar los detalle de esta serie
		return getDetailSerie(idSerie, connection);
	}

	/**
	 * Metodo que permite agregar un capitulo a una temporada
	 * @param chapter, DTO con los datos del capitulo
	 * @return DTO con el detalle de la serie
	 */
	public SerieDTO addChapter(ChapterDTO chapter, Connection connection) throws Exception {

		// se agrega el nuevo capitulo para esta temporada
		insertUpdate(connection,
				SQLEnglish.ADD_CHAPTER,
				ValueSQL.get(chapter.getIdSeason(), Types.BIGINT),
				ValueSQL.get(chapter.getName(), Types.VARCHAR),
				ValueSQL.get(chapter.getUrl(), Types.VARCHAR));

		// se procede a consultar los detalle de esta serie
		return getDetailSerie(chapter.getIdSerie(), connection);
	}

	/**
	 * Metodo que permite consultar el detalle del capitulo
	 * @param idChapter, identificador del capitulo
	 * @return DTO con los datos del capitulo
	 */
	public ChapterDTO getDetailChapter(Long idChapter, Connection connection) throws Exception {
		ChapterDTO chapter = (ChapterDTO)
				find(connection,
						SQLEnglish.GET_DETAIL_CHAPTER,
						MapperEnglish.get(MapperEnglish.GET_DETAIL_CHAPTER),
						ValueSQL.get(idChapter, Types.BIGINT));
		chapter.setId(idChapter);
		return chapter;
	}
}
