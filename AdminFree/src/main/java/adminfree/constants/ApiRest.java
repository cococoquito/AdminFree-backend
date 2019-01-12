package adminfree.constants;

/**
 *
 * Clase Constante contiene todos los valores relacionados a los servicios REST
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
	public static final String VALIDAR_DATOS_USER = "validardatosuser";
	public static final String CREAR_USUARIO = "crearuser";
	public static final String EDITAR_USUARIO = "editaruser";
	public static final String MODIFICAR_ESTADO_USUARIO = "updatestateuser";
	public static final String GENERAR_CLAVE_USUARIO = "generarclaveuser";
	public static final String MODIFICAR_DATOS_CUENTA = "updateaccount";
	public static final String MODIFICAR_CLAVE = "updateclaveuser";
	public static final String CREAR_CAMPO_ENTRADA = "crearcampoin";
	public static final String GET_CAMPOS_ENTRADA = "getcamposin";
	public static final String GET_DETALLE_CAMPO_ENTRADA = "getdtlcampoin";
	public static final String DELETE_CAMPO_ENTRADA = "deletecampoin";
	public static final String GET_DETALLE_CAMPO_EDITAR = "getdtlcampoedi";
	public static final String EDITAR_CAMPO_ENTRADA = "editarcampoin";
	public static final String VALIDAR_DATOS_ENTRADA = "vdatosin";
	public static final String GET_NOMENCLATURAS = "getnomenclaturas";
	public static final String CREAR_NOMENCLATURA = "crearnomenclatura";
	public static final String EDITAR_NOMENCLATURA = "editarnomenclatura";
	public static final String VALIDAR_EXISTE_NOMENCLATURA = "existenomenclatura";
	public static final String ELIMINAR_NOMENCLATURA = "deletenomenclatura";
	public static final String GET_DETALLE_NOMENCLATURA = "getdtlnomenclatura";

	/** Constantes para los nombre de los servicios REST del modulo de seguridad */
	public static final String SEGURIDAD_API = "authapi";
	public static final String ADMIN_CLIENTES_AUTH = "adminclientesauth";
	public static final String AUTH = "auth";

	/** Constantes para los nombre de los servicios REST del modulo de correspondencia */
	public static final String CORRESPONDENCIA_API = "corresapi";
	public static final String GET_DTL_NOMENCLATURA = "dtlnomenclatura";
}
