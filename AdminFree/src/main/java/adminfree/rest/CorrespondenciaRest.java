package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adminfree.constants.ApiRest;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.services.CorrespondenciaService;
import adminfree.utilities.BusinessException;
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

	/**
	 * Servicio que permite obtener los campos de la nomenclatura
	 *
	 * @param idNomenclatura, identificador de la nomenclatura
	 * @return DTO con los campos de la nomenclatura
	 */
	@RequestMapping(
			value = ApiRest.GET_DTL_NOMENCLATURA_CAMPOS,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getCamposNomenclatura(@RequestParam Long idNomenclatura) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getCamposNomenclatura(idNomenclatura));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getCamposNomenclatura ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los datos iniciales para las
	 * solicitudes de consecutivos de correspondencia
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos iniciales
	 */
	@RequestMapping(
			value = ApiRest.INIT_CORRESPONDENCIA,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getInitSolicitarConsecutivo(@RequestParam Long idCliente) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getInitSolicitarConsecutivo(idCliente));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getInitSolicitarConsecutivo ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite validar los campos de ingreso de informacion para el proceso de 
	 * solicitar o editar un consecutivo de correspondencia
	 *
	 * @param solicitud, DTO con los datos de la solicitud
	 * @return Lista de mensajes con los errores encontrados solo si lo hay
	 */
	@RequestMapping(
			value = ApiRest.VALIDAR_CAMPOS_INGRESO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> validarCamposIngresoInformacion(@RequestBody SolicitudConsecutivoDTO solicitud) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.validarCamposIngresoInformacion(solicitud));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".validarCamposIngresoInformacion ", e.getMessage());
		}
	}
}
