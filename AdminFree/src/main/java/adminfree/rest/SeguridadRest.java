package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adminfree.constants.ApiRest;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.services.SeguridadService;
import adminfree.utilities.BusinessException;
import adminfree.utilities.Util;

/**
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
			value = ApiRest.ADMIN_CLIENTES_AUTH,
			method = RequestMethod.POST, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> iniciarSesionAdminClientes(@RequestBody CredencialesDTO credenciales) {
		try {
			return Util.getResponseSuccessful(this.seguridadService.iniciarSesionAdminClientes(credenciales));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(SeguridadRest.class.getSimpleName() + ".iniciarSesionAdminClientes ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite soportar el proceso de iniciar sesion
	 *
	 * return datos del USUARIO o ADMINISTRADOR autenticado
	 */
	@RequestMapping(
			value = ApiRest.AUTH,
			method = RequestMethod.POST, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> iniciarSesion(@RequestBody CredencialesDTO credenciales) {
		try {
			// si el inicio sesion es modo ADMINISTRADOR
			if (credenciales.isAdministrador()) {
				return Util.getResponseSuccessful(this.seguridadService.iniciarSesionAdmin(credenciales));
			}

			// si el inicio sesion es modo USUARIO 
			return Util.getResponseSuccessful(this.seguridadService.iniciarSesionUser(credenciales));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(SeguridadRest.class.getSimpleName() + ".iniciarSesion ", e.getMessage());
		}
	}
}
