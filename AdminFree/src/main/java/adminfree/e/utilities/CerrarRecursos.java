package adminfree.e.utilities;

import java.sql.PreparedStatement;

/**
 * 
 * Clase que permite cerrar los recursos del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CerrarRecursos {

	/**
	 * Metodo que permite cerrar el recurso PreparedStatement
	 */
	public static void closePreparedStatement(PreparedStatement pst) throws Exception {
		if (pst != null) {
			pst.close();
		}
	}
}
