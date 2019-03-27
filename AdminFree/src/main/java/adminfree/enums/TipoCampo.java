package adminfree.enums;

/**
 * Enums para los tipo de campos de ingreso de informacion
 *
 * @author Carlos andres diaz
 *
 */
public enum TipoCampo {

	/** Constante que representa el campo texto */
	CAMPO_TEXTO(Numero.UNO.valueI, "Campo de Texto"),

	/** Constante que representa el campo lista desplegable */
	LISTA_DESPLEGABLE(Numero.DOS.valueI, "Lista Desplegable"),

	/** Constante que representa el campo casilla de verificacion */
	CASILLA_VERIFICACION(Numero.TRES.valueI, "Casilla de Verificación"),

	/** Constante que representa el campo tipo fecha */
	CAMPO_FECHA(Numero.CUATRO.valueI, "Campo de Fecha");

	public final Integer id;
	public final String nombre;

	private TipoCampo(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
}
