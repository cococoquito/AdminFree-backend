package adminfree.enums;

/**
 * 
 * Enums que contiene los valores de seguridad del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public enum Security {

	/** Se utiliza para otorgar todos los posibles opciones */
	PERMITS_ALL("*"),

	/** Se utiliza para otorgar todos los permisos */
	GRANTS_PERMITS_ALL("/**"),

	/** Nombre del key para obtener el usuario en el request.header */
	SECURITY_HUSER("huser"),

	/** Nombre del key para obtener el password en el request.header */
	SECURITY_HPASS("hpass"),

	/** Nombre del key para obtener el TOKEN en el request.header */
	SECURITY_HTOKEN("htoken"),

	/** Tiempo del cache para los Preflighted_requests */
	MAX_AGE("3600");

	public final String value;

	private Security(String value) {
		this.value = value;
	}
}
