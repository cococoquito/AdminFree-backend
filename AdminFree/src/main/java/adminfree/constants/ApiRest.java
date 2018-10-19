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
	
	/** Representa el tipo de contenido del response o request de las solicitudes*/
	public static final String CONTENT_TYPE = "Content-Type";

	/** Constantes para los nombre de los servicios REST del modulo de configuraciones*/
	public static final String CONFIGURACIONES_API = "configuracionesapi";
	public static final String CLIENTES = "clientes";
	public static final String CREAR_CLIENTES = "crearclientes";
	public static final String ACTUALIZAR_CLIENTE = "actualizarcliente";
	public static final String ACTIVAR_CLIENTE = "activarcliente";
	public static final String INACTIVAR_CLIENTE = "inactivarcliente";
	public static final String ELIMINAR_CLIENTE = "eliminarcliente";

	/** Constantes para los nombre de los servicios REST del modulo de seguridad */
	public static final String SEGURIDAD_API = "authapi";
	public static final String ADMIN_CLIENTES_ENTRAR = "adminclientesentrar";
}
