package adminfree.utilities;

/**
 * 
 * Class that contains the Constants of the system
 * 
 * @author Carlos Andres Diaz
 *
 */
public class Constants {
	
	/** Constant that represent the character to grant the permits for all request of the application WEB */
	public static final String GRANTS_PERMITS = "/**";

	/** Constantes de la seguridad del sistema */
	public static final String SECURITY_HUSER = "huser";
	public static final String SECURITY_HPASS = "hpass";
	public static final String SECURITY_HTOKEN = "htoken";
	public static final String CONTENT_TYPE = "Content-Type";
	
	/** Constantes para los nombre de los servicios REST del modulo de configuraciones*/
	public static final String CONFIGURACIONES_NOMBRE_API = "configuracionesapi";
	public static final String CLIENTES = "clientes";
	public static final String CREAR_CLIENTES = "crearclientes";
	public static final String ACTUALIZAR_CLIENTE = "actualizarcliente";
	public static final String ACTIVAR_CLIENTE = "activarcliente";
	public static final String INACTIVAR_CLIENTE = "inactivarcliente";
	public static final String ELIMINAR_CLIENTE = "eliminarcliente";
	
	/** Constantes para los nombre de los servicios REST del modulo de seguridad*/
	public static final String SEGURIDAD_NOMBRE_API = "authapi";
	public static final String INICIAR_SESION_ADMIN_CLIENTES = "iniciarsesionadminclientes";
}
