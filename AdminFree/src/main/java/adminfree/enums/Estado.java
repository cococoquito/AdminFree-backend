package adminfree.enums;

/**
 * Enums utilitaria que define constantes para los estados
 *
 * @author Carlos andres diaz
 *
 */
public enum Estado {

	/** Constante que representa el ESTADO ACTIVO */
	ACTIVO(Numero.UNO.value, "Activo"),

	/** Constante que representa el ESTADO INACTIVO */
	INACTIVO(Numero.DOS.value, "Inactivo"),

	/** Constante que representa el ESTADO ANULADO */
	ANULADO(Numero.TRES.value, "Anulado"),

	/** Constante que representa el ESTADO BORRADO */
	BORRADO(Numero.CUATRO.value, "Borrado"),

	/** Constante que representa el ESTADO CERRADO */
	CERRADO(Numero.CINCO.value, "Cerrado");

	public Integer id;
	public String nombre;

	private Estado(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
}
