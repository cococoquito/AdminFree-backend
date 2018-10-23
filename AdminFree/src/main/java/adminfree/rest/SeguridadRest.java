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
import adminfree.model.configuraciones.AutenticacionDTO;
import adminfree.services.SeguridadService;
import adminfree.utilities.BusinessException;
import adminfree.utilities.CommonResponse;

/**
 * 
 * Clase que contiene todos los servicios REST para el modulo de seguridad
 * localhost:puerto/Constants.SEGURIDAD_NOMBRE_API/
 * 
 * @author Carlos Andres Diaz
 *
 */
@RestController
@RequestMapping(ApiRest.SEGURIDAD_API)
public class SeguridadRest {
	
	/** Objecto que contiene todo los servicios relacionado con la seguridad */
	@Autowired
	private SeguridadService seguridadService;	
	
	/**
	 * Servicio que permite soportar el proceso de iniciar sesion de Admin Clientes
	 * 
	 * return el TOKEN asociado al usuario
	 */
	@RequestMapping(
			value = ApiRest.ADMIN_CLIENTES_ENTRAR,
			method = RequestMethod.POST, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> iniciarSesionAdminClientes(@RequestBody AutenticacionDTO credenciales) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(this.seguridadService.iniciarSesionAdminClientes(credenciales));
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(CommonResponse.getResponseBusiness(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(CommonResponse.getResponseError(e.getMessage()));
		}
	}
}
