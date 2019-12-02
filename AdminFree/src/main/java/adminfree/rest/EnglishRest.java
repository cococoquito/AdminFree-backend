package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adminfree.constants.ApiRest;
import adminfree.dtos.english.SeriesDTO;
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
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> crearSerie(@ModelAttribute SeriesDTO serie) {
		try {
			return Util.getResponseSuccessful(this.englishService.crearSerie(serie));
		} catch (Exception e) {
			return Util.getResponseError(EnglishRest.class.getSimpleName() + ".crearSerie ", e.getMessage());
		}
	}
}
