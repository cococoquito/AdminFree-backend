package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import adminfree.constants.ApiRest;
import adminfree.dtos.correspondencia.DocumentoDTO;
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

	/**
	 * Servicio que permite soportar el proceso de negocio de solicitar
	 * un consecutivo de correspondencia para una nomenclatura
	 *
	 * @param solicitud, DTO que contiene los datos de la solicitud
	 * @return DTO con los datos de la respuesta
	 */
	@RequestMapping(
			value = ApiRest.SOLICITAR_CONSECUTIVO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> solicitarConsecutivo(@RequestBody SolicitudConsecutivoDTO solicitud) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.solicitarConsecutivo(solicitud));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".solicitarConsecutivo ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los datos para la pagina de bienvenida
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos de bienvenida
	 */
	@RequestMapping(
			value = ApiRest.GET_DATOS_WELCOME,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getDatosBienvenida(@RequestParam Long idCliente) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getDatosBienvenida(idCliente));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getDatosBienvenida ", e.getMessage());
		}
	}

	/**
	 * Servicio para el cargue del documento asociado a un consecutivo
	 *
	 * @param idCliente, se utiliza para identificar el documento
	 * @param idConsecutivo, se utiliza para identificar el documento
	 * nombre=idCliente_idConsecutivo_nombreDocumento.extension
	 */
	@RequestMapping(
			value = ApiRest.CARGAR_DOCUMENTO,
			method = RequestMethod.POST,
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> cargarDocumento(
			@RequestPart("documento") MultipartFile documento,
			@RequestPart("idCliente") String idCliente,
			@RequestPart("idConsecutivo") String idConsecutivo) {
		try {
			// se construye los datos para el cargue del documento
			DocumentoDTO datos = new DocumentoDTO();
			datos.setIdCliente(idCliente);
			datos.setIdConsecutivo(idConsecutivo);
			datos.setNombreDocumento(documento.getOriginalFilename());
			datos.setTipoDocumento(documento.getContentType());
			datos.setSizeDocumento(documento.getSize() + "");
			datos.setContenido(documento.getBytes());

			// se procede a realizar el cargue
			return Util.getResponseSuccessful(this.correspondenciaService.cargarDocumento(datos));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".cargarDocumento ", e.getMessage());
		}
	}
}
