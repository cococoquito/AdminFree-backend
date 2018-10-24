package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adminfree.constants.ApiRest;
import adminfree.constants.TipoEvento;
import adminfree.model.configuraciones.ClienteDTO;
import adminfree.services.ConfiguracionesService;
import adminfree.utilities.CommonResponse;

/**
 * 
 * Clase que contiene todos los servicios REST para el modulo de Configuraciones
 * localhost:puerto/Constants.CONFIGURACIONES_NOMBRE_API/
 * 
 * @author Carlos Andres Diaz
 *
 */
@RestController
@RequestMapping(ApiRest.CONFIGURACIONES_API)
public class ConfiguracionesRest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Servicio REST que permite obtener los CLIENTES del sistema
	 * 
	 * @return lista de CLIENTES parametrizados en el sistema
	 */
	@RequestMapping(
			value = ApiRest.CLIENTES,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getClientes() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.configuracionesService.listarClientes());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CommonResponse.getResponseError(e.getMessage()));
		}
	}

	/**
	 * Servicio que permite crear un cliente en el sistema
	 * 
	 * @param cliente, DTO con los datos del cliente a crear
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	@RequestMapping(
			value = ApiRest.CREAR_CLIENTES,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> crearCliente(@RequestBody ClienteDTO cliente) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.configuracionesService.crearCliente(cliente));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CommonResponse.getResponseError(e.getMessage()));
		}
	}

	/**
	 * Servicio para actualizar los datos del CLIENTE
	 * 
	 * @param cliente, datos del cliente a MODIFICAR
	 */
	@RequestMapping(
			value = ApiRest.MODIFICAR_CLIENTE,
			method = RequestMethod.PUT,
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> modificarCLiente(@RequestBody ClienteDTO cliente) {
		try {
			// se captura el tipo de evento
			String tipoEvento = cliente.getTipoEvento();
			
			// de acuerdo al tipo de evento se procede a modificar el cliente
			switch (tipoEvento) {
				case TipoEvento.ACTUALIZAR:
					this.configuracionesService.actualizarCliente(cliente);
					break;
	
				case TipoEvento.ACTIVAR:
					this.configuracionesService.activarCliente(cliente);
					break;
	
				case TipoEvento.INACTIVAR:
					this.configuracionesService.inactivarCliente(cliente);
					break;
			}
			// al llegar a este punto significa que el proceso es OK
			return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.getResponseSuccess());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CommonResponse.getResponseError(e.getMessage()));
		}
	}

	/**
	 * Servicio que permite ELIMINAR un cliente del sistema
	 * 
	 * @param cliente, DTO que contiene el identificador del cliente ELIMINAR
	 * @return OK, de lo contrario el mensaje de error de MYSQL
	 */
	@RequestMapping(
			value = ApiRest.ELIMINAR_CLIENTE,
			method = RequestMethod.PUT,
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> eliminarCliente(@RequestBody ClienteDTO cliente) {
		try {
			// se elimina el cliente del sistema
			String respuesta = this.configuracionesService.eliminarCliente(cliente);
			
			// se valida si el proceso fue exitoso
			if (HttpStatus.OK.getReasonPhrase().equals(respuesta)) {
				return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.getResponseSuccess());
			}
			
			// si MYSQL retorna algun error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CommonResponse.getResponseError(respuesta));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CommonResponse.getResponseError(e.getMessage()));
		}
	}
}
