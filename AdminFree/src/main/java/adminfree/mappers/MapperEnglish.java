package adminfree.mappers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adminfree.constants.CommonConstant;
import adminfree.dtos.english.ChapterDTO;
import adminfree.dtos.english.SeasonDTO;
import adminfree.dtos.english.SentenceDTO;
import adminfree.dtos.english.SerieDTO;
import adminfree.enums.Numero;
import adminfree.utilities.Util;

/**
 * Mapper que contiene las implementaciones JDBC para el modulo de learning english
 *
 * @author Carlos Andres Diaz
 *
 */
public class MapperEnglish extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int GET_SERIES = 1;
	public static final int GET_DETAIL_SERIE = 2;
	public static final int GET_CHAPTERS_SEASON = 3;
	public static final int GET_DETAIL_CHAPTER = 4;

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperEnglish instance;

	/**
	 * Constructor del Mapper no instanciable
	 */
	private MapperEnglish() {
	}

	/**
	 * Retorna una instancia de este tipo de Mapper
	 */
	public static MapperEnglish get(int tipoMapper) {
		if (instance == null) {
			instance = new MapperEnglish();
		}
		instance.tipoMapper = tipoMapper;
		return instance;
	}

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 *
	 * @param res, resultado de acuerdo a la consulta
	 * @param parametros que necesita el mapper para ser procesado
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	@Override
	public Object executeParams(ResultSet res, Object parametro) throws Exception {
		Object result = null;
		switch (this.tipoMapper) {
			case MapperEnglish.GET_DETAIL_SERIE:
				result = getDetailSerie(res, parametro);
				break;
			case MapperEnglish.GET_CHAPTERS_SEASON:
				result = getChaptersSeason(res, parametro);
				break;
		}
		return result;
	}

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 *
	 * @param res, resultado de acuerdo a la consulta
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	@Override
	public Object execute(ResultSet res) throws Exception {
		Object result = null;
		switch (this.tipoMapper) {
			case MapperEnglish.GET_SERIES:
				result = getSeries(res);
				break;
			case MapperEnglish.GET_DETAIL_CHAPTER:
				result = getDetailChapter(res);
				break;
		}
		return result;
	}

	/**
	 * Metodo que permite obtener el detalle de un capitulo
	 */
	private Object getDetailChapter(ResultSet res) throws Exception {
		ChapterDTO chapter = null;
		SentenceDTO sentence;
		Long idSentence;
		while (res.next()) {
			if (chapter == null) {
				chapter = new ChapterDTO();
				chapter.setName(res.getString(Numero.UNO.valueI));
				chapter.setUrl(res.getString(Numero.DOS.valueI));
			}
			idSentence = res.getLong(Numero.TRES.valueI);
			if (idSentence != null && !idSentence.equals(Numero.ZERO.valueL)) {
				sentence = new SentenceDTO();
				sentence.setId(idSentence);
				sentence.setSpanish(res.getString(Numero.CUATRO.valueI));
				sentence.setEnglish(res.getString(Numero.CINCO.valueI));
				sentence.setAudio(res.getBytes(Numero.SEIS.valueI));
				sentence.setAudioName(res.getString(Numero.SIETE.valueI));
				chapter.addSentence(sentence);
			}
		}
		return chapter;
	}

	/**
	 * Mapper para obtener la lista de SERIES parametrizados en el sistema
	 */
	private Object getSeries(ResultSet res) throws Exception {
		List<SerieDTO> series = null;
		SerieDTO serie = null;
		while (res.next()) {
			if (series == null) {
				series = new ArrayList<>();
			}
			serie = new SerieDTO();
			serie.setId(res.getLong(Numero.UNO.valueI));
			serie.setName(res.getString(Numero.DOS.valueI));
			serie.setImg(res.getBytes(Numero.TRES.valueI));
			series.add(serie);
		}
		return series;
	}

	/**
	 * Mapper para obtener el detalle de la serie
	 */
	private Object getDetailSerie(ResultSet res, Object parameter) throws Exception {
		SerieDTO serie = new SerieDTO();
		if (res.next()) {

			// se configura los datos generales de la serie
			serie = new SerieDTO();
			serie.setName(res.getString(Numero.UNO.valueI));
			serie.setUrl(res.getString(Numero.DOS.valueI));
			serie.setImg(res.getBytes(Numero.TRES.valueI));
			String idsSeasons = res.getString(Numero.CUATRO.valueI);

			// se verifica si esta serie tiene temporadas
			if (!Util.isNull(idsSeasons)) {

				// se configura los ids separados por coma esto para otra consulta
				((StringBuilder) parameter).append(idsSeasons);

				// se necesita para configurar los capitulos
				final String seasonName = "SEASON # ";
				SeasonDTO season;
				int i = 1;

				// por cada id es una temporada
				List<String> ids = Arrays.asList(idsSeasons.split(CommonConstant.COMA));
				for (String id : ids) {
					season = new SeasonDTO();
					season.setId(Long.valueOf(id));
					season.setName(seasonName + i);
					serie.addSeason(season);
					i++;
				}
			}

		}
		return serie;
	}

	/**
	 * Mapper para obtener los capitulos de una temporada
	 */
	@SuppressWarnings("unchecked")
	private Object getChaptersSeason(ResultSet res, Object parameter) throws Exception {
		List<SeasonDTO> seasons = (List<SeasonDTO>) parameter;
		ChapterDTO chapter = null;
		Long idSeason;
		while (res.next()) {
			chapter = new ChapterDTO();
			chapter.setId(res.getLong(Numero.UNO.valueI));
			chapter.setName(res.getString(Numero.DOS.valueI));
			idSeason = res.getLong(Numero.TRES.valueI);

			// se busca la temporada que contiene este capitulo
			forseason: for (SeasonDTO season : seasons) {
				if (season.getId().equals(idSeason)) {
					season.addChapter(chapter);
					break forseason;
				}
			}
		}
		return null;
	}
}
