package adminfree.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import adminfree.dtos.correspondencia.ActivarAnularConsecutivoDTO;
import adminfree.dtos.correspondencia.ConsecutivoDetalleDTO;
import adminfree.dtos.correspondencia.ConsecutivoEdicionDTO;
import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.dtos.correspondencia.FiltroConsecutivosDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.correspondencia.TransferirConsecutivoDTO;
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
	 * @return lista de documentos asociados al consecutivo
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
			datos.setIdCliente(Integer.valueOf(idCliente));
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

	/**
	 * Servicio que soporta el proceso de negocio para la descarga
	 * de un documento de correspondencia en AWS-S3
	 *
	 * @param idCliente, se utiliza para identificar el cliente que tiene el documento
	 * @param idDocumento, se utiliza para consultar los datos del documento
	 * @return Documento descargado con todos sus atributos
	 */
	@RequestMapping(value = ApiRest.DESCARGAR_DOCUMENTO, method = RequestMethod.GET)
	public ResponseEntity<Object> descargarDocumento(@RequestParam Integer idCliente, @RequestParam Long idDocumento) {
		try {
			// se procede obtener los datos del documento
			DocumentoDTO documento = this.correspondenciaService.descargarDocumento(idCliente, idDocumento);

			// se envia el documento al cliente
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, Util.getAttachmentDocument(documento.getNombreDocumento()))
					.contentType(MediaType.parseMediaType(documento.getTipoDocumento()))
					.contentLength(Long.valueOf(documento.getSizeDocumento()))
					.body(documento.getContenido());
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".descargarDocumento ", e.getMessage());
		}
	}

	/**
	 * Servicio para eliminar un documento asociado al consecutivo
	 *
	 * @param datos, Contiene los datos del documento eliminar
	 * @return lista de documentos asociados al consecutivo
	 */
	@RequestMapping(
			value = ApiRest.ELIMINAR_DOCUMENTO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> eliminarDocumento(@RequestBody DocumentoDTO datos) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.eliminarDocumento(datos));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".eliminarDocumento ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los consecutivos del anio actual de acuerdo al
	 * filtro de busqueda
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de consecutivos paginados y su cantidad total
	 */
	@RequestMapping(
			value = ApiRest.GET_CONSECUTIVOS_ACTUAL,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getConsecutivosAnioActual(@RequestBody FiltroConsecutivosDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getConsecutivosAnioActual(filtro));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getConsecutivosAnioActual ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los datos iniciales para el 
	 * submodulo de Consecutivos de correspondencia solicitados
	 * para el anio actual
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return DTO con los datos iniciales
	 */
	@RequestMapping(
			value = ApiRest.GET_INIT_CONSECUTIVOS_ACTUAL,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getInitConsecutivosAnioActual(@RequestParam Long idCliente) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getInitConsecutivosAnioActual(idCliente));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getInitConsecutivosAnioActual ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los datos iniciales para el
	 * submodulo de Mis Consecutivos de correspondencia solicitados
	 * para el anio actual
	 *
	 * @param idCliente, identificador del cliente asociado al usuario
	 * @param idUsuario, identificador del usuario autenticado en el sistema
	 * @return DTO con los datos iniciales
	 */
	@RequestMapping(
			value = ApiRest.GET_INIT_MIS_CONSECUTIVOS,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getInitMisConsecutivos(@RequestParam Long idCliente, Integer idUsuario) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getInitMisConsecutivos(idCliente, idUsuario));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getInitMisConsecutivos ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite consultar el detalle de un consecutivo
	 *
	 * @param filtro, DTO que contiene los identificadores del cliente y del consecutivo
	 * @return DTO con los datos del consecutivo
	 */
	@RequestMapping(
			value = ApiRest.GET_DETALLE_CONSECUTIVO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getDetalleConsecutivo(@RequestBody ConsecutivoDetalleDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getDetalleConsecutivo(filtro));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getDetalleConsecutivo ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los campos para los filtros de busqueda
	 *
	 * @param idCliente, identificador del cliente que tiene los campos
	 * @return Lista de campos con sus atributos configurados
	 */
	@RequestMapping(
			value = ApiRest.GET_CAMPOS_FILTRO,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getCamposFiltro(@RequestParam Long idCliente) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getCamposFiltro(idCliente));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getCamposFiltro ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los items para los filtros tipo LISTA DESPLEGABLE
	 *
	 * @param idsCampos, lista de identificadores de los campos a consultar sus items
	 * @return lista de items con sus atributos construido
	 */
	@RequestMapping(
			value = ApiRest.GET_ITEMS_SELECT_FILTRO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getItemsSelectFiltro(@RequestBody List<Long> idsCampos) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getItemsSelectFiltro(idsCampos));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getItemsSelectFiltro ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite ACTIVAR o ANULAR un consecutivo de correspondencia
	 *
	 * @param parametro, DTO que contiene los datos necesarios para el proceso
	 */
	@RequestMapping(
			value = ApiRest.ACTIVAR_ANULAR_CONSECUTIVO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> activarAnularConsecutivo(@RequestBody ActivarAnularConsecutivoDTO parametro) {
		try {
			// se procede activar o anular el consecutivo
			this.correspondenciaService.activarAnularConsecutivo(parametro);

			// si llega a este punto es porque el update se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".activarAnularConsecutivo ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite transferir un consecutivo hacia otro usuario
	 *
	 * @param parametro, DTO con los datos necesarios para el proceso
	 * @return DTO con los consecutivos paginado de acuerdo al filtro
	 */
	@RequestMapping(
			value = ApiRest.TRANSFERIR_CONSECUTIVO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> transferirConsecutivo(@RequestBody TransferirConsecutivoDTO parametro) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.transferirConsecutivo(parametro));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".transferirConsecutivo ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los usuarios para el proceso de transferir consecutivo
	 *
	 * @param idCliente, identificador del cliente asociado a los usuarios
	 * @param idUsuario, se deben consultar todos los usuarios activos excepto este usuario
	 * @return Lista de usuarios activos en el sistema
	 */
	@RequestMapping(
			value = ApiRest.GET_USERS_TRANSFERIR,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> getUsuariosTransferir(@RequestParam Integer idCliente, @RequestParam Integer idUsuario) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getUsuariosTransferir(idCliente, idUsuario));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getUsuariosTransferir ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite consultar y retornar los datos del consecutivo para su edicion
	 *
	 * @param filtro, DTO que contiene los valores para el filtro de busqueda
	 * @return DTO Con los atributos del consecutivo configurados
	 */
	@RequestMapping(
			value = ApiRest.GET_CONSECUTIVO_EDICION,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getConsecutivoEdicion(@RequestBody ConsecutivoEdicionDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.getConsecutivoEdicion(filtro));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".getConsecutivoEdicion ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite editar los valores de un consecutivo
	 *
	 * @param datos, contiene todos los valores a editar
	 * @return valores asociados al consecutivo con las modificaciones realizadas
	 */
	@RequestMapping(
			value = ApiRest.EDITAR_CONSECUTIVO_VALUES,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> editarConsecutivoValores(@RequestBody ConsecutivoEdicionDTO datos) {
		try {
			return Util.getResponseSuccessful(this.correspondenciaService.editarConsecutivoValores(datos));
		} catch (Exception e) {
			return Util.getResponseError(CorrespondenciaRest.class.getSimpleName() + ".editarConsecutivoValores ", e.getMessage());
		}
	}
}
