package adminfree.constants;

/**
 * Clase constante que contiene los DML Y DDL para el modulo de Archivo de Gestion
 *
 * @author Carlos Andres Diaz
 */
public class SQLArchivoGestion {

	/** SQL para obtener los tipos documentales parametrizados en el sistema*/
	public static final String GET_TIPOS_DOCUMENTALES = "SELECT ID_TIPO_DOC,NOMBRE FROM TIPOS_DOCUMENTALES ORDER BY NOMBRE";

	/** SQL para insertar un tipo documental*/
	public static final String INSERT_TIPO_DOCUMENTAL = "INSERT INTO TIPOS_DOCUMENTALES(NOMBRE)VALUES(?)";

	/** SQL para editar un tipo documental*/
	public static final String EDITAR_TIPO_DOCUMENTAL = "UPDATE TIPOS_DOCUMENTALES SET NOMBRE=? WHERE ID_TIPO_DOC=?";

	/** SQL para eliminar un tipo documental*/
	public static final String ELIMINAR_TIPO_DOCUMENTAL = "DELETE FROM TIPOS_DOCUMENTALES WHERE ID_TIPO_DOC=?";

	/** SQL para contar las series documentales asociado a un tipo documental*/
	public static final String COUNT_SERIES_TIPO_DOCUMENTAL = "SELECT COUNT(*) FROM TIPOS_DOCUMENTALES_SERIES WHERE ID_TIPO_DOC=?";

	/** SQL para contar las subseries documentales asociado a un tipo documental*/
	public static final String COUNT_SUBSERIES_TIPO_DOCUMENTAL = "SELECT COUNT(*) FROM TIPOS_DOCUMENTALES_SUBSERIES WHERE ID_TIPO_DOC=?";

	/** SQL para contar las series documentales con el mismo nombre para el proceso de creacion*/
	public static final String COUNT_SERIES_NOMBRE_CREACION = "SELECT COUNT(*) FROM SERIES_DOCUMENTALES WHERE NOMBRE=? AND CLIENTE=?";

	/** SQL para contar las series documentales con el mismo codigo para el proceso de creacion*/
	public static final String COUNT_SERIES_CODIGO_CREACION = "SELECT COUNT(*) FROM SERIES_DOCUMENTALES WHERE CODIGO=? AND CLIENTE=?";

	/** SQL para contar las series documentales con el mismo nombre para el proceso de edicion*/
	public static final String COUNT_SERIES_NOMBRE_EDICION = "SELECT COUNT(*) FROM SERIES_DOCUMENTALES WHERE NOMBRE=? AND ID_SERIE<>? AND CLIENTE=?";

	/** SQL para contar las series documentales con el mismo codigo para el proceso de edicion*/
	public static final String COUNT_SERIES_CODIGO_EDICION = "SELECT COUNT(*) FROM SERIES_DOCUMENTALES WHERE CODIGO=? AND ID_SERIE<>? AND CLIENTE=?";

	/** SQL para insertar una serie documental*/
	public static final String INSERT_SERIE = "INSERT INTO SERIES_DOCUMENTALES(CLIENTE,CODIGO,NOMBRE,AG,AC,CT,M,S,E,PROCEDIMIENTO,FECHA_CREACION,USUARIO_CREACION)VALUES(?,?,?,?,?,?,?,?,?,?,CURDATE(),?)";

	/** SQL para editar una serie documental*/
	public static final String EDIT_SERIE = "UPDATE SERIES_DOCUMENTALES SET CODIGO=?,NOMBRE=?,AG=?,AC=?,CT=?,M=?,S=?,E=?,PROCEDIMIENTO=? WHERE ID_SERIE=?";

	/** SQL para obtener la cantidad de consecutivos asociados a una serie documental*/
	public static final String GET_CANT_CONSECUTIVOS_SERIE = "SELECT CANT_CONSECUTIVOS FROM SERIES_DOCUMENTALES WHERE ID_SERIE=?";

	/** SQL para contar cuanta veces esta la serie en el TRD*/
	public static final String COUNT_SERIE_TRD = "SELECT COUNT(*) FROM TRDS WHERE ID_SERIE=?";

	/** SQL para contar las sub-series que tiene esta serie*/
	public static final String COUNT_SUB_SERIES_SERIE = "SELECT COUNT(*) FROM SUBSERIES_DOCUMENTALES WHERE ID_SERIE=?";

	/** SQL para eliminar una serie documental*/
	public static final String DELETE_SERIE = "DELETE FROM SERIES_DOCUMENTALES WHERE ID_SERIE=?";
}
