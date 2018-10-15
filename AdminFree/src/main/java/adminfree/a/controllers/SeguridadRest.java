package adminfree.a.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adminfree.b.services.SeguridadService;
import adminfree.d.model.configuraciones.CredencialAdminClientesDTO;
import adminfree.e.utilities.BusinessException;
import adminfree.e.utilities.Constants;
import adminfree.e.utilities.ConstantsCodigoMessages;

/**
 * 
 * Clase que contiene todos los servicios REST para el modulo de seguridad
 * localhost:puerto/Constants.SEGURIDAD_NOMBRE_API/
 * 
 * @author Carlos Andres Diaz
 *
 */
@RestController
@RequestMapping(Constants.SEGURIDAD_NOMBRE_API)
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
			value = Constants.INICIAR_SESION_ADMIN_CLIENTES, 
			method = RequestMethod.POST, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> iniciarSesionAdminClientes(@RequestBody CredencialAdminClientesDTO credenciales) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.seguridadService
					.iniciarSesionAdminClientes(
							credenciales.getClave(), 
							credenciales.getUsuario()));
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ConstantsCodigoMessages.MESSAGE_ERROR_TECHNICAL + e.getMessage());
		}
	}
}
