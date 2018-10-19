package adminfree.enums;

/**
 * 
 * Clase que contiene las constantes de los KEY para obtener los valores del
 * archivo application.properties
 * 
 * @author Carlos Andres Diaz
 *
 */
public class PropertyKey {
	public static final String SECURITY_ADMINCLIENTES_USER = "${adminfree.security.adminclientes.user}";
	public static final String SECURITY_ADMINCLIENTES_PASS = "${adminfree.security.adminclientes.pass}";
	public static final String SECURITY_AUTH_USER = "${adminfree.security.auth.user}";
	public static final String SECURITY_AUTH_PASS = "${adminfree.security.auth.pass}";
	public static final String SECURITY_AUTH_TOKEN = "${adminfree.security.auth.token}";
	public static final String SECURITY_POST_ANGULAR_AUTH = "${adminfree.security.post.angular.auth}";
	public static final String SECURITY_POST_ANGULAR = "${adminfree.security.post.angular}";
	public static final String SECURITY_POST_PASS = "${adminfree.security.post.pass}";
	public static final String SECURITY_POST_TOKEN = "${adminfree.security.post.token}";
}
