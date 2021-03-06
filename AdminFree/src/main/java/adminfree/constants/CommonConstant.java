package adminfree.constants;

/**
 * 
 * Clase que contiene las constantes que no se definen en ningun criterio de
 * entidad y que son transversales para toda la aplicacion
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CommonConstant {
	public static final String COMA = ",";
	public static final String PUNTO_COMA = ";";
	public static final String INTERROGACION = "?";
	public static final String COMA_INTERROGACION = ",?";
	public static final String INTERROGACION_1 = "?1";
	public static final String INTERROGACION_2 = "?2";
	public static final String INTERROGACION_3 = "?3";
	public static final String INTERROGACION_4 = "?4";
	public static final String ID_NOMENCLATURA = "ID_NOMENCLATURA";
	public static final String NOMENCLATURAS = "NOMENCLATURAS";
	public static final String PREFIJO_ZEROS_4 = "0000";
	public static final String PREFIJO_ZEROS_2 = "00";
	public static final String LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	public static final Integer ID_ADMINISTRADOR = -1;
	public static final String ADMINISTRADOR = "Administrador";
	public static final String ADMINISTRADOR_CARGO = "Administrador del Sistema";
	public static final String FORMATO_FECHA_MSYQL = "%d/%M/%Y";
	public static final String FORMATO_FECHA_SQL = "'%Y-%m-%d'";
	public static final String SI = "1";
	public static final char WITH_LIKE = '*';

	/** Constantes para las restricciones de los campos */
	public static final String KEY_CAMPO_UNICO_NOMENCLATURA = "3";
	public static final String KEY_CAMPO_TODAS_NOMENCLATURA = "4";

	/** Constantes para los paginadores - inicio */
	public static final String ROWS_PAGE_DEFAULT = "10";
	public static final String SKIP_DEFAULT = "0";
	/** Constantes para los paginadores - final */
}
