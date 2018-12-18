package adminfree.constants;

/**
 *
 * Clase Constante que contiene todos los valores relacionados a los servicios
 * REST
 *
 * @author Carlos Andres Diaz
 *
 */
public class ApiRest {

	/** Constantes para los nombre de los servicios REST del modulo de configuraciones*/
	public static final String CONFIGURACIONES_API = "configuracionesapi";
	public static final String CREAR_CLIENTES = "crearclientes";
	public static final String MODIFICAR_CLIENTE = "modificarcliente";
	public static final String ELIMINAR_CLIENTE = "eliminarcliente";
	public static final String GET_CLIENTES_USUARIO = "clientesusuario";
	public static final String CREAR_USUARIO = "crearuser";
	public static final String MODIFICAR_ESTADO_USUARIO = "updatestateuser";
	public static final String MODIFICAR_PRIVILEGIOS_USUARIO = "updateprivilegiosuser";
	public static final String GENERAR_CLAVE_USUARIO = "generarclaveuser";
	public static final String MODIFICAR_DATOS_CUENTA = "updateaccount";
	public static final String MODIFICAR_CLAVE = "updateclaveuser";
	public static final String CREAR_CAMPO_ENTRADA = "crearcampoin";
	public static final String GET_CAMPOS_ENTRADA = "getcamposin";
	public static final String GET_DETALLE_CAMPO_ENTRADA = "getdtlcampoin";
	public static final String DELETE_CAMPO_ENTRADA = "deletecampoin";
	public static final String GET_DETALLE_CAMPO_EDITAR = "getdtlcampoedi";
	public static final String EDITAR_CAMPO_ENTRADA = "editarcampoin";

	/** Constantes para los nombre de los servicios REST del modulo de seguridad */
	public static final String SEGURIDAD_API = "authapi";
	public static final String ADMIN_CLIENTES_AUTH = "adminclientesauth";
	public static final String AUTH = "auth";
}
