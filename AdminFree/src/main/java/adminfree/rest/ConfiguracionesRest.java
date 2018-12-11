package adminfree.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adminfree.constants.ApiRest;
import adminfree.constants.TipoEvento;
import adminfree.dtos.configuraciones.CambioClaveDTO;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.ConfiguracionesService;
import adminfree.utilities.BusinessException;
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

	/**
	 * Servicio que permite consultar los usuarios con estados (ACTIVO/INACTIVO)
	 * asociados a un cliente especifico
	 *
	 * @param filtro, contiene los datos del filtro de busqueda
	 * @return lista de Usuarios asociados a un cliente
	 */
	@RequestMapping(
			value = ApiRest.GET_CLIENTES_USUARIO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getUsuariosCliente(@RequestBody ClienteDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.getUsuariosCliente(filtro));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getUsuariosCliente ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite crear el usuario con sus privilegios en el sistema
	 *
	 * @param usuario, DTO que contiene los datos del usuarios
	 * @return DTO con los datos del usuario creado
	 */
	@RequestMapping(
			value = ApiRest.CREAR_USUARIO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> crearUsuario(@RequestBody UsuarioDTO usuario) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.crearUsuario(usuario));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(SeguridadRest.class.getSimpleName() + ".crearUsuario ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite cambiar el estado de un usuario
	 *
	 * @param usuario, DTO que contiene los datos del usuario a modificar
	 * @return OK, si todo el proceso se ejecuto sin errores
	 */
	@RequestMapping(
			value = ApiRest.MODIFICAR_ESTADO_USUARIO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> modificarEstadoUsuario(@RequestBody UsuarioDTO usuario) {
		try {
			// se procede a modificar el estado del usuario
			this.configuracionesService.modificarEstadoUsuario(usuario);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".modificarEstadoUsuario ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite modificar los privilegios de un Usuario
	 *
	 * @param usuario, DTO que contiene el identificador y los privilegios a modificar
	 * @return OK, si todo el proceso se ejecuto sin errores
	 */
	@RequestMapping(
			value = ApiRest.MODIFICAR_PRIVILEGIOS_USUARIO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> modificarPrivilegiosUsuario(@RequestBody UsuarioDTO usuario) {
		try {
			// se procede a modificar los privilegios del usuario
			this.configuracionesService.modificarPrivilegiosUsuario(usuario);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".modificarPrivilegiosUsuario ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite generar una nueva clave
	 * de ingreso para el usuario que llega por parametro
	 *
	 * @param usuario, DTO con el identificador del usuario
	 * @return DTO con la clave de ingreso generada
	 */
	@RequestMapping(
			value = ApiRest.GENERAR_CLAVE_USUARIO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> generarClaveIngreso(@RequestBody UsuarioDTO usuario) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.generarClaveIngreso(usuario));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".generarClaveIngreso ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite actualizar los datos de la cuenta del usuario, solamente
	 * aplica (Nombre, Usuario Ingreso)
	 *
	 * @param usuario, DTO que contiene los datos del usuario
	 * @return OK, si todo el proceso se ejecuto sin errores
	 */
	@RequestMapping(
			value = ApiRest.MODIFICAR_DATOS_CUENTA,
			method = RequestMethod.PUT,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> modificarDatosCuenta(@RequestBody UsuarioDTO usuario) {
		try {
			// se procede a modificar los datos de la cuenta user
			this.configuracionesService.modificarDatosCuenta(usuario);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".modificarDatosCuenta ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite soportar el proceso de modificar la clave de ingreso
	 *
	 * @param datos, DTO que contiene los datos para el proceso de la modificacion
	 * @return OK, si todo el proceso se ejecuto sin errores
	 */
	@RequestMapping(
			value = ApiRest.MODIFICAR_CLAVE,
			method = RequestMethod.PUT,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> modificarClaveIngreso(@RequestBody CambioClaveDTO datos) {
		try {
			// se procede a modificar la clave de ingreso del usuario
			this.configuracionesService.modificarClaveIngreso(datos);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".modificarClaveIngreso ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener las restricciones asociados a un tipo de campo
	 *
	 * @param tipoCampo, es el valor del tipo de campo asociado a las restricciones
	 * @return Lista de restricciones parametrizadas en la BD
	 */
	@RequestMapping(
			value = ApiRest.GET_RESTRICCIONES,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getRestriciones(@RequestParam Integer tipoCampo) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.getRestriciones(tipoCampo));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getRestriciones ", e.getMessage());
		}
	}
}
