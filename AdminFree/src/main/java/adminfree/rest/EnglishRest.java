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
	 *
	 * @param serie, DTO que contiene los datos de la serie a crear
	 * @return DTO con el identificador de la serie
	 */
	@RequestMapping(
			value = ApiRest.CREATE_SERIE,
			method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> crearSerie(@RequestBody SerieDTO serie) {
		try {
			return Util.getResponseSuccessful(this.englishService.crearSerie(serie));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".crearSerie ", e.getMessage());
		}
	}

	/**
	 * Service para asociar la imagen a la serie
	 *
	 * @param img, es la imagen para asociar
	 * @param idSerie, identificador de la serie asociar la imagen
	 */
	@RequestMapping(
			value = ApiRest.DOWNLOAD_IMG_SERIE,
			method = RequestMethod.POST,
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> downloadImgSerie(
			@RequestPart("img") MultipartFile img,
			@RequestPart("idSerie") String idSerie) {
		try {
			// se procede asociar la imagen
			this.englishService.downloadImgSerie(img.getBytes(), idSerie);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".downloadImgSerie ", e.getMessage());
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
	 * Service que permite ingresar los datos basicos de la sentencia
	 * @param sentence, DTO con los datos de la sentencia
	 * @return DTO con el identificador de la sentencia
	 */
	@RequestMapping(
			value = ApiRest.INSERT_SENTENCE,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> insertSentence(@RequestBody SentenceDTO sentence) {
		try {
			return Util.getResponseSuccessful(this.englishService.insertSentence(sentence));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".insertSentence ", e.getMessage());
		}
	}

	/**
	 * Service para almacenar el sonido a la sentencia
	 * @param sound, sonido almacenar en la BD
	 * @param idSentence, identificador de la sentencia
	 * @param idChapter, identificador del capitulo
	 * @return Detalle del capitulo que contiene esta sentencia
	 */
	@RequestMapping(
			value = ApiRest.DOWNLOAD_SOUND,
			method = RequestMethod.POST,
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> downloadSound(
			@RequestPart("sound") MultipartFile sound,
			@RequestPart("idSentence") String idSentence,
			@RequestPart("idChapter") String idChapter) {
		try {
			return Util.getResponseSuccessful(this.englishService.downloadSound(
					sound.getBytes(),
					sound.getOriginalFilename(),
					idSentence,
					idChapter));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".downloadSound ", e.getMessage());
		}
	}
}
