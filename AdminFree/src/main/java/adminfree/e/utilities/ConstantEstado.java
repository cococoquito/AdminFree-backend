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
	public static final Long ID_ESTADO_ACTIVO = 1L;
	/** Constante que representa el ESTADO ANULADO */
	public static final Long ID_ESTADO_ANULADO = 2L;
	/** Constante que representa el ESTADO BORRADO */
	public static final Long ID_ESTADO_BORRADO = 3L;
	/** Constante que representa el ESTADO CERRADO */
	public static final Long ID_ESTADO_CERRADO = 4L;
	/** Constante que representa el ESTADO EN CURSO */
	public static final Long ID_ESTADO_CURSO = 5L;
	/** Constante que representa el ESTADO ARCHIVADO */
	public static final Long ID_ESTADO_ARCHIVADO = 7L;

	/**************** Son los NOMBRES de los estados *************************************/
	/** Constante que representa el nombre ESTADO ACTIVO */
	public static final String ESTADO_ACTIVO = "Activo";
	/** Constante que representa el nombre ESTADO ANULADO */
	public static final String ESTADO_ANULADO = "Anulado";
	/** Constante que representa el nombre ESTADO BORRADO */
	public static final String ESTADO_BORRADO = "Borrado";
	/** Constante que representa el nombre ESTADO CERRADO */
	public static final String ESTADO_CERRADO = "Cerrado";
	/** Constante que representa el nombre ESTADO EN CURSO */
	public static final String ESTADO_CURSO = "En Curso";
	/** Constante que representa el nombre ESTADO ARCHIVADO */
	public static final String ESTADO_ARCHIVADO = "Archivado";

	/**
	 * metodo que busca el nombre de un estado de acuerdo su id
	 *
	 * @param Integer idEstado, id del estado
	 * @return el nombre del estado
	 */
	public static String getNombreEstado(Long idEstado) {
		String nombreEstado = null;
		if (ID_ESTADO_ACTIVO.equals(idEstado)) {
			nombreEstado = ESTADO_ACTIVO;
		} else if (ID_ESTADO_BORRADO.equals(idEstado)) {
			nombreEstado = ESTADO_BORRADO;
		} else if (ID_ESTADO_ANULADO.equals(idEstado)) {
			nombreEstado = ESTADO_ANULADO;
		} else if (ID_ESTADO_CERRADO.equals(idEstado)) {
			nombreEstado = ESTADO_CERRADO;
		} else if (ID_ESTADO_CURSO.equals(idEstado)) {
			nombreEstado = ESTADO_CURSO;
		} else if (ID_ESTADO_ARCHIVADO.equals(idEstado)) {
			nombreEstado = ESTADO_ARCHIVADO;
		}
		return nombreEstado;
	}
}
