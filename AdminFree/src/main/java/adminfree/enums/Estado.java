package adminfree.enums;

/**
 * Enums utilitaria que define constantes para los estados
 *
 * @author Carlos andres diaz
 *
 */
public enum Estado {

	/** Constante que representa el ESTADO ACTIVO */
	ACTIVO(Numero.UNO.valueI, "Activo"),

	/** Constante que representa el ESTADO INACTIVO */
	INACTIVO(Numero.DOS.valueI, "Inactivo"),

	/** Constante que representa el ESTADO ANULADO */
	ANULADO(Numero.TRES.valueI, "Anulado"),

	/** Constante que representa el ESTADO BORRADO */
	BORRADO(Numero.CUATRO.valueI, "Borrado"),

	/** Constante que representa el ESTADO CERRADO */
	CERRADO(Numero.CINCO.valueI, "Cerrado");

	public final Integer id;
	public final String nombre;

	private Estado(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
}
