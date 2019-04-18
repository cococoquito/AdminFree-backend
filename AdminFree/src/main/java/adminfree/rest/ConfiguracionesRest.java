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
import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.CampoEntradaEdicionDTO;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.configuraciones.GenerarTokenIngresoDTO;
import adminfree.dtos.configuraciones.ModificarCuentaUsuarioDTO;
import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.dtos.configuraciones.NomenclaturaEdicionDTO;
import adminfree.dtos.configuraciones.UsuarioEdicionDTO;
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
				case TipoEvento.EDITAR:
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
	 * Servicio que permite validar los datos del usuario para la creacion o modificacion
	 *
	 * @param usuario, DTO con los datos del usuario a crear o modificar
	 */
	@RequestMapping(
			value = ApiRest.VALIDAR_DATOS_USER,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> validarDatosUsuario(@RequestBody UsuarioDTO usuario) {
		try {
			// se procede a ejecutar las validaciones
			this.configuracionesService.validarDatosUsuario(usuario);

			// si llega a este punto es porque las validaciones pasaron sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".validarDatosUsuario ", e.getMessage());
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
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".crearUsuario ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite editar los datos de un usuario
	 *
	 * @param datos, DTO que contiene los datos a modificar
	 */
	@RequestMapping(
			value = ApiRest.EDITAR_USUARIO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> editarUsuario(@RequestBody UsuarioEdicionDTO datos) {
		try {
			// se procede a editar los datos del usuario
			this.configuracionesService.editarUsuario(datos);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".editarUsuario ", e.getMessage());
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
	 * Servicio que permite generar un nuevo TOKEN de ingreso
	 * para el usuario o cliente que llega por parametro
	 *
	 * @param parametro, DTO que contiene el id del cliente o usuario
	 * @return DTO con el TOKEN de ingreso generada
	 */
	@RequestMapping(
			value = ApiRest.GENERAR_CLAVE_INGRESO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> generarClaveIngreso(@RequestBody GenerarTokenIngresoDTO parametro) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.generarClaveIngreso(parametro));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".generarClaveIngreso ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite soportar el proceso de negocio para
	 * la creacion del campo de entrada de informacion
	 *
	 * @param campo, DTO que contiene los datos del nuevo campo de entrada
	 * @return DTO con los datos del nuevo campo de entrada
	 */
	@RequestMapping(
			value = ApiRest.CREAR_CAMPO_ENTRADA,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> crearCampoEntrada(@RequestBody CampoEntradaDTO campo) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.crearCampoEntrada(campo));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".crearCampoEntrada ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los campos de entrada de informacion asociado a un cliente
	 *
	 * @param idCliente, identificador del cliente que le pertenece los campos de entrada
	 * @param isRestriccion, 1=si se debe consultar las restricciones
	 * @return lista DTO con la informacion de los campos de entrada
	 */
	@RequestMapping(
			value = ApiRest.GET_CAMPOS_ENTRADA,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getCamposEntrada(@RequestParam Long idCliente, @RequestParam Integer isRestriccion) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.getCamposEntrada(idCliente, isRestriccion));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getCamposEntrada ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener el detalle del campo entrada
	 * de informacion asociado a una nomenclatura
	 *
	 * @param idNomenclatura, nomenclatura asociada al campo a consultar
	 * @param idCampo, identificador del campo de entrada informacion
	 * @return DTO con los datos del campo de entrada de informacion
	 */
	@RequestMapping(
			value = ApiRest.GET_DETALLE_NOMENCLATURA_CAMPO,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getDetalleNomenclaturaCampo(@RequestParam Long idNomenclatura, @RequestParam Long idCampo) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.getDetalleNomenclaturaCampo(idNomenclatura, idCampo));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getDetalleNomenclaturaCampo ", e.getMessage());
		}
	}

	/**
	 * Servicio que soporta el proceso de negocio para la eliminacion de un campo de entrada
	 *
	 * @param idCampo, identificador del campo de entrada
	 */
	@RequestMapping(
			value = ApiRest.DELETE_CAMPO_ENTRADA,
			method = RequestMethod.DELETE,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> eliminarCampoEntrada(@RequestParam Long idCampo) {
		try {
			// se procede eliminar el campo de entrada
			this.configuracionesService.eliminarCampoEntrada(idCampo);

			// si llega a este punto es porque el proceso se ejecuto bien
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".eliminarCampoEntrada ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener el detalle de un campo de entrada para edicion
	 *
	 * @param idCampo, identificador del campo de entrada a editar
	 * @return DTO con los datos del campo de entrada de informacion a editar
	 */
	@RequestMapping(
			value = ApiRest.GET_DETALLE_CAMPO_EDITAR,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getDetalleCampoEntradaEdicion(@RequestParam Long idCampo) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.getDetalleCampoEntradaEdicion(idCampo));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getDetalleCampoEntradaEdicion ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite editar un campo de entrada de informacion
	 *
	 * @param datos, DTO que contiene los datos a editar
	 * @return DTO con los datos basico del campo
	 */
	@RequestMapping(
			value = ApiRest.EDITAR_CAMPO_ENTRADA,
			method = RequestMethod.PUT,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> editarCampoEntrada(@RequestBody CampoEntradaEdicionDTO datos) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.editarCampoEntrada(datos));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".editarCampoEntrada ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite validar los datos de campo de entrada
	 * esto aplica para el primer paso al momento de crear o editar el campo
	 *
	 * @param campo, contiene los datos del campo de entrada
	 */
	@RequestMapping(
			value = ApiRest.VALIDAR_DATOS_ENTRADA,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> validarDatosCampoEntrada(@RequestBody CampoEntradaDTO datos) {
		try {
			// se procede a validar las restricciones del campo de entrada
			this.configuracionesService.validarDatosCampoEntrada(datos);

			// si llega a este punto es porque el proceso se ejecuto bien
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".validarDatosCampoEntrada ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener todas las nomenclaturas asociadas a un cliente
	 *
	 * @param idCliente, identificador del cliente quien le pertenece las nomenclaturas
	 * @return lista de nomenclaturas con sus atributos configuradas
	 */
	@RequestMapping(
			value = ApiRest.GET_NOMENCLATURAS,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getNomenclaturas(@RequestParam Long idCliente) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.getNomenclaturas(idCliente));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getNomenclaturas ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite crear una nomenclatura
	 *
	 * @param nomenclatura, contiene los datos de la creacion
	 * @return Nomenclatura con el identificador generado
	 */
	@RequestMapping(
			value = ApiRest.CREAR_NOMENCLATURA,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> crearNomenclatura(@RequestBody NomenclaturaDTO nomenclatura) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.crearNomenclatura(nomenclatura));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".crearNomenclatura ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite editar la nomenclatura
	 *
	 * @param datos, contiene los datos de la edicion
	 * @return OK si todo el proceso se ejecuto sin problemas
	 */
	@RequestMapping(
			value = ApiRest.EDITAR_NOMENCLATURA,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> editarNomenclatura(@RequestBody NomenclaturaEdicionDTO datos) {
		try {
			// se procede a editar la nomenclatura
			this.configuracionesService.editarNomenclatura(datos);

			// si llega a este punto es porque el proceso se ejecuto bien
			return Util.getResponseOk();
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".editarNomenclatura ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite validar si la nomenclatura ya existe en el sistema
	 * 
	 * @param nomenclatura, DTO que contiene los datos para la validacion
	 */
	@RequestMapping(
			value = ApiRest.VALIDAR_EXISTE_NOMENCLATURA,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> validarExisteNomenclatura(@RequestBody NomenclaturaDTO nomenclatura) {
		try {
			// se procede a ejecutar las validaciones
			this.configuracionesService.validarExisteNomenclatura(nomenclatura);

			// si llega a este punto es porque las validaciones pasaron sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".validarExisteNomenclatura ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite eliminar una nomenclatura del sistema
	 *
	 * @param idNomenclatura, identificador de la nomenclatura
	 */
	@RequestMapping(
			value = ApiRest.ELIMINAR_NOMENCLATURA,
			method = RequestMethod.DELETE,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> eliminarNomenclatura(@RequestParam Long idNomenclatura) {
		try {
			// se procede a eliminar la nomenclatura
			this.configuracionesService.eliminarNomenclatura(idNomenclatura);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".eliminarNomenclatura ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite consultar el detalle de la nomenclatura
	 *
	 * @param idNomenclatura, identificador de la nomenclatura 
	 * @return, DTO con el detalle de la nomenclatura
	 */
	@RequestMapping(
			value = ApiRest.GET_DETALLE_NOMENCLATURA,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getDetalleNomenclatura(@RequestParam Long idNomenclatura) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService.getDetalleNomenclatura(idNomenclatura));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getDetalleNomenclatura ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite consultar el detalle de la nomenclatura para su modificacion
	 *
	 * @param idNomenclatura, identificador de la nomenclatura
	 * @param idCliente, identificador del cliente asociado a los campos
	 * @param isGetCampos, 1=se debe consultar los campos con sus restricciones
	 * @return DTO con los atributos configurados
	 */
	@RequestMapping(
			value = ApiRest.GET_DETALLE_NOMENCLATURA_EDITAR,
			method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<Object> getDetalleNomenclaturaEdicion(
			@RequestParam Long idNomenclatura,
			@RequestParam Long idCliente,
			@RequestParam Integer isGetCampos) {
		try {
			return Util.getResponseSuccessful(this.configuracionesService
					.getDetalleNomenclaturaEdicion(idNomenclatura, idCliente, isGetCampos));
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".getDetalleNomenclaturaEdicion ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite procesar la funcionalidad de negocio de modificacion
	 * cuenta del usuario aplica para datos personales, cambio clave o usuario de ingreso
	 *
	 * @param params, contiene los DTOs para las modificaciones correspondiente
	 */
	@RequestMapping(
			value = ApiRest.MODIFICAR_CUENTA_USUARIO,
			method = RequestMethod.POST,
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })	
	public ResponseEntity<Object> modificarCuentaUsuario(@RequestBody ModificarCuentaUsuarioDTO params) {
		try {
			// se procede hacer las modificaciones correspondientes
			this.configuracionesService.modificarCuentaUsuario(params);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionesRest.class.getSimpleName() + ".modificarCuentaUsuario ", e.getMessage());
		}
	}
}
