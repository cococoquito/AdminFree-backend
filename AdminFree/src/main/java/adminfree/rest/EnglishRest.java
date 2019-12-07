package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import adminfree.constants.ApiRest;
import adminfree.dtos.english.ChapterDTO;
import adminfree.dtos.english.SentenceDTO;
import adminfree.dtos.english.SerieDTO;
import adminfree.services.EnglishService;
import adminfree.utilities.Util;

/**
 * Clase que contiene todos los servicios REST para el modulo de Learning English
 * localhost:puerto/Constants.ENGLISH_API/
 *
 * @author Carlos Andres Diaz
 *
 */
@RestController
@RequestMapping(ApiRest.ENGLISH_API)
public class EnglishRest {

	/** Objecto que contiene todo los servicios relacionado para el modulo de english */
	@Autowired
	private EnglishService englishService;

	/**
	 * Service que permite crear una serie en el sistema
	 * @param img, Imagen asociada a la serie
	 * @param name, nombre de la serie a crear
	 * @param url, direccion de la pagina de esta serie
	 */
	@RequestMapping(
			value = ApiRest.CREATE_SERIE,
			method = RequestMethod.POST,
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> createSerie(
			@RequestPart("img") MultipartFile img,
			@RequestPart("name") String name,
			@RequestPart("url") String url) {
		try {
			// se procede a crear la serie
			SerieDTO serie = new SerieDTO();
			serie.setImg(img.getBytes());
			serie.setName(name);
			serie.setUrl(url);
			this.englishService.createSerie(serie);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".createSerie ", e.getMessage());
		}
	}

	/**
	 * Service que permite cargar las series parametrizadas en el sistema
	 */
	@RequestMapping(
			value = ApiRest.GET_SERIES,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getSeries() {
		try {
			return Util.getResponseSuccessful(this.englishService.getSeries());
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".getSeries ", e.getMessage());
		}
	}

	/**
	 * Service que permite obtener los detalles de una serie
	 * @param idSerie, identificador de la serie
	 * @return DTO con el detalle de la serie
	 */
	@RequestMapping(
			value = ApiRest.DETAIL_SERIE,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getDetailSerie(@RequestBody Long idSerie) {
		try {
			return Util.getResponseSuccessful(this.englishService.getDetailSerie(idSerie));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".getDetailSerie ", e.getMessage());
		}
	}

	/**
	 * Service que permite agregar una nueva temporada
	 * @param idSerie, identificador de la serie
	 * @return DTO con el detalle de la serie
	 */
	@RequestMapping(
			value = ApiRest.ADD_SEASON,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> addSeason(@RequestBody Long idSerie) {
		try {
			return Util.getResponseSuccessful(this.englishService.addSeason(idSerie));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".addSeason ", e.getMessage());
		}
	}

	/**
	 * Service que permite agregar un capitulo a una temporada
	 * @param chapter, DTO con los datos del capitulo
	 * @return DTO con el detalle de la serie
	 */
	@RequestMapping(
			value = ApiRest.ADD_CHAPTER,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> addChapter(@RequestBody ChapterDTO chapter) {
		try {
			return Util.getResponseSuccessful(this.englishService.addChapter(chapter));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".addChapter ", e.getMessage());
		}
	}

	/**
	 * Service que permite consultar el detalle del capitulo
	 * @param idChapter, identificador del capitulo
	 * @return DTO con los datos del capitulo
	 */
	@RequestMapping(
			value = ApiRest.DETAIL_CHAPTER,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getDetailChapter(@RequestBody Long idChapter) {
		try {
			return Util.getResponseSuccessful(this.englishService.getDetailChapter(idChapter));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".getDetailChapter ", e.getMessage());
		}
	}

	/**
	 * Service que permite crear una sentencia en el sistema
	 * @param audio, es el audio de la sentencia
	 * @param idChapter, identificador del capitulo
	 * @param spanish, la sentencia en espaniol
	 * @param english, la sentencia en ingles
	 * @return detalle del capitulo con todas sus sentencias
	 */
	@RequestMapping(
			value = ApiRest.CREATE_SENTENCE,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> createSentence(
			@RequestPart("audio") MultipartFile audio,
			@RequestPart("idChapter") String idChapter,
			@RequestPart("spanish") String spanish,
			@RequestPart("english") String english) {
		try {
			// se construye el DTO con los datos de la sentencia
			SentenceDTO sentence = new SentenceDTO();
			sentence.setAudio(audio.getBytes());
			sentence.setAudioName(audio.getOriginalFilename());
			sentence.setIdChapter(Long.valueOf(idChapter));
			sentence.setSpanish(spanish);
			sentence.setEnglish(english);

			// se procede a crear la sentencia
			return Util.getResponseSuccessful(this.englishService.createSentence(sentence));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".createSentence ", e.getMessage());
		}
	}
}
