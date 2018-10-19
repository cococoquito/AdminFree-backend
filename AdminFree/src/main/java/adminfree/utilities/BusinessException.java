package adminfree.utilities;

/**
 * Clase que identifica el tipo de exception de negocio
 *
 * @author Carlos andres diaz
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la Exception
	 * 
	 * @param msj, es el mensaje de la exception ocurrido
	 */
	public BusinessException(String msj) {
		super(msj);
	}
}
