package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adminfree.services.ConfiguracionesService;
import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.utilities.Constants;
import adminfree.utilities.ConstantsCodigoMessages;

/**
 * 
 * Clase que contiene todos los servicios REST para el modulo de Configuraciones
 * localhost:puerto/Constants.CONFIGURACIONES_NOMBRE_API/
 * 
 * @author Carlos Andres Diaz
 *
 */
@RestController
@RequestMapping(Constants.CONFIGURACIONES_NOMBRE_API)
public class ConfiguracionesRest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Servicio que permite crear un cliente en el sistema
	 * 
	 * @param cliente, DTO con los datos del cliente a crear
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	@RequestMapping(
			value = Constants.CREAR_CLIENTES,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> crearCliente(@RequestBody ClienteDTO cliente) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.configuracionesService.crearCliente(cliente));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantsCodigoMessages.COD_ERROR_TECHNICAL + e.getMessage());
		}
	}

	/**
	 * Servicio REST que permite obtener los CLIENTES del sistema
	 * 
	 * @return lista de CLIENTES parametrizados en el sistema
	 */
	@RequestMapping(
			value = Constants.CLIENTES,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getClientes() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.configuracionesService.listarClientes());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantsCodigoMessages.COD_ERROR_TECHNICAL + e.getMessage());
		}
	}
	
	/**
	 * Servicio para actualizar los datos del CLIENTE
	 * 
	 * @param cliente, datos del cliente ACTUALIZAR
	 */
	@RequestMapping(
			value = Constants.ACTUALIZAR_CLIENTE,
			method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> actualizarCliente(@RequestBody ClienteDTO cliente) {
		try {
			// se actualiza los datos del cliente
			this.configuracionesService.actualizarCliente(cliente);
			
			// al llegar a este punto significa que el proceso es OK
			return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantsCodigoMessages.COD_ERROR_TECHNICAL + e.getMessage());
		}
	}
	
	/**
	 * Servicio que permite ACTIVAR un cliente
	 * 
	 * @param cliente, contiene el identificador del cliente
	 */
	@RequestMapping(
			value = Constants.ACTIVAR_CLIENTE,
			method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> activarCliente(@RequestBody ClienteDTO cliente) {
		try {
			// se procede activar el cliente
			this.configuracionesService.activarCliente(cliente);
			
			// al llegar a este punto significa que el proceso es OK
			return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantsCodigoMessages.COD_ERROR_TECHNICAL + e.getMessage());
		}
	}
	
	/**
	 * Servicio que permite INACTIVAR un cliente
	 * 
	 * @param cliente, contiene el identificador del cliente
	 */
	@RequestMapping(
			value = Constants.INACTIVAR_CLIENTE,
			method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> inactivarCliente(@RequestBody ClienteDTO cliente) {
		try {
			// se procede inactivar el CLIENTE
			this.configuracionesService.inactivarCliente(cliente);
			
			// al llegar a este punto significa que el proceso es OK
			return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantsCodigoMessages.COD_ERROR_TECHNICAL + e.getMessage());
		}
	}
	
	/**
	 * Servicio que permite ELIMINAR un cliente del sistema
	 * 
	 * @param cliente, DTO que contiene el identificador del cliente ELIMINAR
	 * @return 200 = OK, de lo contrario el mensaje de error de MYSQL
	 */
	@RequestMapping(
			value = Constants.ELIMINAR_CLIENTE,
			method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> eliminarCliente(@RequestBody ClienteDTO cliente) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.configuracionesService.eliminarCliente(cliente));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantsCodigoMessages.COD_ERROR_TECHNICAL + e.getMessage());
		}
	}
}
