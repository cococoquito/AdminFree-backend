package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adminfree.constants.ApiRest;
import adminfree.services.CorrespondenciaService;
import adminfree.utilities.Util;

/**
 * Clase que contiene todos los servicios REST para el modulo de Correspondencia
 * 
 * @author Carlos Andres Diaz
 *
 */
@RestController
@RequestMapping(ApiRest.CORRESPONDENCIA_API)
public class CorrespondenciaRest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Servicio que permite obtener el detalle de una nomenclatura
	 * 
	 * @param idNomenclatura, identificador de la nomenclatura
	 * @return DTO con los datos de la nomenclatura
	 */
	@RequestMapping(
			value = ApiRest.GET_DTL_NOMENCLATURA,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getDetalleNomenclatura(@RequestParam Long idNomenclatura) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getDetalleNomenclatura(idNomenclatura));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getDetalleNomenclatura ", e.getMessage());
		}
	}
}
