package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adminfree.constants.ApiRest;
import adminfree.dtos.archivogestion.FiltroSerieDocumentalDTO;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.services.ArchivoGestionService;
import adminfree.utilities.BusinessException;
import adminfree.utilities.Util;

/**
 * Clase que contiene todos los servicios REST para el modulo de Archivo de Gestión
 * localhost:puerto/Constants.ARCHIVO_GESTION_API/
 *
 * @author Carlos Andres Diaz
 *
 */
@RestController
@RequestMapping(ApiRest.ARCHIVO_GESTION_API)
public class ArchivoGestionRest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * Servicio que permite obtener los datos de inicio para el submodulo de series documentales
	 *
	 * @param idCliente, identificador del cliente autenticado
	 * @return Response con los datos necesarios para el submodulo
	 */
	@RequestMapping(
			value = ApiRest.GET_INIT_ADMIN_SERIES_DOC,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getInitAdminSeriesDocumentales(@RequestBody Long idCliente) {
		try {
			return Util.getResponseSuccessful(this.archivoGestionService.getInitAdminSeriesDocumentales(idCliente));
		} catch (Exception e) {
			return Util.getResponseError(ArchivoGestionRest.class.getSimpleName() + ".getInitAdminSeriesDocumentales ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener las series documentales de acuerdo al filtro de busqueda
	 *
	 * @param filtro, DTO que contiene los datos del filtro de busqueda
	 * @return DTO con los datos del response con la lista de series documentales
	 */
	@RequestMapping(
			value = ApiRest.GET_SERIES,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getSeriesDocumentales(@RequestBody FiltroSerieDocumentalDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.archivoGestionService.getSeriesDocumentales(filtro));
		} catch (Exception e) {
			return Util.getResponseError(ArchivoGestionRest.class.getSimpleName() + ".getSeriesDocumentales ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener todos los tipos documentales parametrizados
	 *
	 * @return Lista de tipos documentales
	 */
	@RequestMapping(
			value = ApiRest.GET_TIPOS_DOCUMENTALES,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getTiposDocumentales() {
		try {
			return Util.getResponseSuccessful(this.archivoGestionService.getTiposDocumentales());
		} catch (Exception e) {
			return Util.getResponseError(ArchivoGestionRest.class.getSimpleName() + ".getTiposDocumentales ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite administrar los tipos documentales
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param tipo, contiene los datos del tipo documental a procesar
	 */
	@RequestMapping(
			value = ApiRest.ADMIN_TIPOS_DOCUMENTAL,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> administrarTiposDocumentales(@RequestBody TipoDocumentalDTO tipo) {
		try {
			this.archivoGestionService.administrarTiposDocumentales(tipo);
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ArchivoGestionRest.class.getSimpleName() + ".administrarTiposDocumentales ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite administrar la entidad de series documentales
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param serie, DTO con los datos de la serie documental
	 */
	@RequestMapping(
			value = ApiRest.ADMIN_SERIES,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> administrarSerieDocumental(@RequestBody SerieDocumentalDTO serie) {
		try {
			this.archivoGestionService.administrarSerieDocumental(serie);
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ArchivoGestionRest.class.getSimpleName() + ".administrarSerieDocumental ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite administrar la entidad de sub-serie documental
	 * aplica solamente para CREAR, EDITAR, ELIMINAR
	 *
	 * @param subserie, DTO con los datos de la sub-serie documental
	 */
	@RequestMapping(
			value = ApiRest.ADMIN_SUBSERIES,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> administrarSubSerieDocumental(@RequestBody SubSerieDocumentalDTO subserie) {
		try {
			this.archivoGestionService.administrarSubSerieDocumental(subserie);
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ArchivoGestionRest.class.getSimpleName() + ".administrarSubSerieDocumental ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite eliminar una serie documental en el sistema
	 *
	 * @param serie, DTO que contiene los datos de la serie a eliminar
	 * @return Response con la lista de series documentales
	 */
	@RequestMapping(
			value = ApiRest.DELETE_SERIE,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> eliminarSerieDocumental(@RequestBody SerieDocumentalDTO serie) {
		try {
			return Util.getResponseSuccessful(this.archivoGestionService.eliminarSerieDocumental(serie));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ArchivoGestionRest.class.getSimpleName() + ".eliminarSerieDocumental ", e.getMessage());
		}
	}
}
