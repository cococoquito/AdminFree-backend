package adminfree.e.utilities;

/**
 * Clase utilitaria que define constantes para los estados
 *
 * @author Carlos andres diaz
 *
 */

public class ConstantEstado {

	/**************** Son los IDS de los estados ****************************************/
	/** Constante que representa el ESTADO ACTIVO */
	public static final Integer ID_ESTADO_ACTIVO = ConstantNumeros.UNO;
	/** Constante que representa el ESTADO INACTIVO */
	public static final Integer ID_ESTADO_INACTIVO = ConstantNumeros.DOS;	
	/** Constante que representa el ESTADO ANULADO */
	public static final Integer ID_ESTADO_ANULADO = ConstantNumeros.TRES;
	/** Constante que representa el ESTADO BORRADO */
	public static final Integer ID_ESTADO_BORRADO = ConstantNumeros.CUATRO;
	/** Constante que representa el ESTADO CERRADO */
	public static final Integer ID_ESTADO_CERRADO = ConstantNumeros.CINCO;

	/**************** Son los NOMBRES de los estados *************************************/
	/** Constante que representa el nombre ESTADO ACTIVO */
	private static final String ESTADO_ACTIVO = "Activo";
	/** Constante que representa el nombre ESTADO INACTIVO */
	private static final String ESTADO_INACTIVO = "Inactivo";	
	/** Constante que representa el nombre ESTADO ANULADO */
	private static final String ESTADO_ANULADO = "Anulado";
	/** Constante que representa el nombre ESTADO BORRADO */
	private static final String ESTADO_BORRADO = "Borrado";
	/** Constante que representa el nombre ESTADO CERRADO */
	private static final String ESTADO_CERRADO = "Cerrado";

	/**
	 * metodo que busca el nombre de un estado de acuerdo su id
	 *
	 * @param Integer idEstado, id del estado
	 * @return el nombre del estado
	 */
	public static String getNombreEstado(Integer idEstado) {
		String nombreEstado = null;
		if (ID_ESTADO_ACTIVO.equals(idEstado)) {
			nombreEstado = ESTADO_ACTIVO;
		} else if (ID_ESTADO_INACTIVO.equals(idEstado)) {
			nombreEstado = ESTADO_INACTIVO;
		} else if (ID_ESTADO_BORRADO.equals(idEstado)) {
			nombreEstado = ESTADO_BORRADO;
		} else if (ID_ESTADO_ANULADO.equals(idEstado)) {
			nombreEstado = ESTADO_ANULADO;
		} else if (ID_ESTADO_CERRADO.equals(idEstado)) {
			nombreEstado = ESTADO_CERRADO;
		}
		return nombreEstado;
	}
}
