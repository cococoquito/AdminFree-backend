package adminfree.enums;

/**
 * 
 * Enums que contiene los valores de seguridad del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public enum Security {

	GRANTS_PERMITS_ALL("/**"), SECURITY_HUSER("huser"), SECURITY_HPASS("hpass"), SECURITY_HTOKEN("htoken");

	public String value;

	private Security(String value) {
		this.value = value;
	}
}
