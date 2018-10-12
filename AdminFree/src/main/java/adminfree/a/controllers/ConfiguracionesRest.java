package adminfree.a.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adminfree.b.services.ConfiguracionesService;
import adminfree.e.utilities.Constants;

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
	 * Servicio REST que permite obtener los CLIENTES del sistema
	 * 
	 * @return lista de CLIENTES parametrizados en el sistema
	 */
	@RequestMapping(
			value = Constants.GET_CLIENTES, 
			method = RequestMethod.GET, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getClientes() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.configuracionesService.listarClientes());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
