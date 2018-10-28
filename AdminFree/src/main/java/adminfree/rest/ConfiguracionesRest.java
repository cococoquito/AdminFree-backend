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
import adminfree.utilities.Util;

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
			return Util.getResponseSuccessful(this.configuracionesService.crearCliente(cliente));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".crearCliente ", e.getMessage());
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
			// se utiliza para almacenar el response
			ResponseEntity<Object> response = null;
			
			// se captura el tipo de evento
			String tipoEvento = cliente.getTipoEvento();
			
			// de acuerdo al tipo de evento se procede a modificar el cliente
			switch (tipoEvento) {
				case TipoEvento.ACTUALIZAR:
					this.configuracionesService.actualizarCliente(cliente);
					response = Util.getResponseOk();
					break;
	
				case TipoEvento.ACTIVAR:
					response = Util.getResponseSuccessful(this.configuracionesService.activarCliente(cliente));
					break;
	
				case TipoEvento.INACTIVAR:
					response = Util.getResponseSuccessful(this.configuracionesService.inactivarCliente(cliente));
					break;
			}
			return response;
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".modificarCLiente ", e.getMessage());
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
				return Util.getResponseOk();
			}
			
			// si MYSQL retorna algun error
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".eliminarCliente ", respuesta);
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".eliminarCliente ", e.getMessage());
		}
	}
}
