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
}
