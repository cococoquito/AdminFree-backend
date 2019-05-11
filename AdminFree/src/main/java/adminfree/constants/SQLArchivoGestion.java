package adminfree.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase constante que contiene los DML Y DDL para el modulo de Archivo de Gestion
 *
 * @author Carlos Andres Diaz
 */
public class SQLArchivoGestion {

	/** SQL para obtener los tipos documentales asociados a un cliente*/
	public static final String GET_TIPOS_DOCUMENTALES = "SELECT ID_TIPO_DOC,NOMBRE FROM TIPOS_DOCUMENTALES WHERE CLIENTE=? ORDER BY NOMBRE";

	/** SQL para obtener los tipos documentales asociados a una serie documental*/
	public static final String GET_TIPOS_DOCUMENTALES_SERIE = "SELECT TS.ID_TIPO_DOC,TD.NOMBRE FROM TIPOS_DOCUMENTALES_SERIES TS JOIN TIPOS_DOCUMENTALES TD ON(TD.ID_TIPO_DOC=TS.ID_TIPO_DOC)WHERE TS.ID_SERIE=? ORDER BY TD.NOMBRE ASC";

	/** SQL para obtener los tipos documentales asociados a una sub-serie documental*/
	public static final String GET_TIPOS_DOCUMENTALES_SUBSERIE = "SELECT TS.ID_TIPO_DOC,TD.NOMBRE FROM TIPOS_DOCUMENTALES_SUBSERIES TS JOIN TIPOS_DOCUMENTALES TD ON(TD.ID_TIPO_DOC=TS.ID_TIPO_DOC)WHERE TS.ID_SUBSERIE=? ORDER BY TD.NOMBRE ASC";

	/** SQL para insertar una serie documental*/
	public static final String INSERT_SERIE = "INSERT INTO SERIES_DOCUMENTALES(CLIENTE,CODIGO,NOMBRE,AG,AC,CT,M,S,E,PROCEDIMIENTO,FECHA_CREACION,USUARIO_CREACION)VALUES(?,?,?,?,?,?,?,?,?,?,CURDATE(),?)";

	/** SQL para editar una serie documental*/
	public static final String EDIT_SERIE = "UPDATE SERIES_DOCUMENTALES SET CODIGO=?,NOMBRE=?,AG=?,AC=?,CT=?,M=?,S=?,E=?,PROCEDIMIENTO=? WHERE ID_SERIE=?";

	/** SQL para obtener la cantidad de consecutivos asociados a una serie documental*/
	public static final String GET_CANT_CONSECUTIVOS_SERIE = "SELECT CANT_CONSECUTIVOS FROM SERIES_DOCUMENTALES WHERE ID_SERIE=?";

	/** SQL para verificar si la serie documental esta asociado en la TRD*/
	public static final String EXISTS_SERIE_TRD = "SELECT EXISTS(SELECT * FROM TRDS WHERE ID_SERIE=?)";

	/** SQL para verificar las sub-series que tiene esta serie*/
	public static final String EXISTS_SUBSERIES_SERIE = "SELECT EXISTS(SELECT * FROM SUBSERIES_DOCUMENTALES WHERE ID_SERIE=?)";

	/** SQL para insertar una subserie documental*/
	public static final String INSERT_SUBSERIE = "INSERT INTO SUBSERIES_DOCUMENTALES(ID_SERIE,CODIGO,NOMBRE,AG,AC,CT,M,S,E,PROCEDIMIENTO,FECHA_CREACION,USUARIO_CREACION)VALUES(?,?,?,?,?,?,?,?,?,?,CURDATE(),?)";

	/** SQL para editar una subserie documental*/
	public static final String EDIT_SUBSERIE = "UPDATE SUBSERIES_DOCUMENTALES SET ID_SERIE=?,CODIGO=?,NOMBRE=?,AG=?,AC=?,CT=?,M=?,S=?,E=?,PROCEDIMIENTO=? WHERE ID_SUBSERIE=?";

	/** SQL para obtener la cantidad de consecutivos asociados a una sub-serie documental*/
	public static final String GET_CANT_CONSECUTIVOS_SUBSERIE = "SELECT CANT_CONSECUTIVOS FROM SUBSERIES_DOCUMENTALES WHERE ID_SUBSERIE=?";

	/** SQL para verificar si la sub-serie documental esta asociado en la TRD*/
	public static final String EXISTS_SUBSERIE_TRD = "SELECT EXISTS(SELECT * FROM TRDS WHERE ID_SUBSERIE=?)";

	/**
	 * SQL para obtener las subseries que le pertenece a cada serie documental
	 */
	public static String getSQLSubseries(StringBuilder idsSerie) {
		StringBuilder sql = new StringBuilder("SELECT ID_SUBSERIE,ID_SERIE,CODIGO,LOWER(NOMBRE),AG,AC,CT,M,S,E,PROCEDIMIENTO FROM SUBSERIES_DOCUMENTALES WHERE ID_SERIE IN(");
		sql.append(idsSerie).append(")ORDER BY ID_SERIE ASC,CODIGO ASC");
		return sql.toString();
	}

	/**
	 * SQL para obtener los tipos de documentales que le pertenece a cada serie documental
	 */
	public static String getSQLTiposDocSerie(StringBuilder idsSerie) {
		StringBuilder sql = new StringBuilder("SELECT TS.ID_SERIE,TS.ID_TIPO_DOC,TD.NOMBRE FROM TIPOS_DOCUMENTALES_SERIES TS JOIN TIPOS_DOCUMENTALES TD ON(TD.ID_TIPO_DOC=TS.ID_TIPO_DOC)WHERE TS.ID_SERIE IN(");
		sql.append(idsSerie).append(")ORDER BY TS.ID_SERIE ASC,TD.NOMBRE ASC");
		return sql.toString();
	}

	/**
	 * SQL para obtener los tipos de documentales que le pertenece a cada subserie documental
	 */
	public static String getSQLTiposDocSubSerie(StringBuilder idsSubSerie) {
		StringBuilder sql = new StringBuilder("SELECT TS.ID_SUBSERIE,TS.ID_TIPO_DOC,TD.NOMBRE FROM TIPOS_DOCUMENTALES_SUBSERIES TS JOIN TIPOS_DOCUMENTALES TD ON(TD.ID_TIPO_DOC=TS.ID_TIPO_DOC)WHERE TS.ID_SUBSERIE IN(");
		sql.append(idsSubSerie).append(")ORDER BY TS.ID_SUBSERIE ASC,TD.NOMBRE ASC");
		return sql.toString();
	}

	/**
	 * SQL para validar si existe el valor de un campo especifico en la serie documental
	 */
	public static String existsValorSerie(String campo, Integer idCliente, Long idSerie) {
		StringBuilder sql = new StringBuilder("SELECT EXISTS(SELECT * FROM SERIES_DOCUMENTALES WHERE ");
		sql.append(campo).append("=? AND CLIENTE=").append(idCliente);
		if (idSerie != null) {
			sql.append(" AND ID_SERIE<>").append(idSerie);
		}
		sql.append(")");
		return sql.toString();
	}

	/**
	 * SQL para construir el DELETE de una serie documental con sus tablas asociadas
	 */
	public static List<String> deleteSerieDocumental(Long idSerie) {
		List<String> deletes = new ArrayList<>();
		deletes.add("DELETE FROM TIPOS_DOCUMENTALES_SERIES WHERE ID_SERIE="+idSerie);
		deletes.add("DELETE FROM SERIES_DOCUMENTALES WHERE ID_SERIE="+idSerie);
		return deletes;
	}

	/**
	 * SQL para validar si existe el valor de un campo especifico en la sub-serie documental
	 */
	public static String existsValorSubSerie(String campo, Integer idCliente, Long idSubSerie) {
		StringBuilder sql = new StringBuilder("SELECT EXISTS(SELECT * FROM SUBSERIES_DOCUMENTALES SUB JOIN SERIES_DOCUMENTALES SE ON(SE.ID_SERIE=SUB.ID_SERIE)WHERE SUB.");
		sql.append(campo).append("=? AND SE.CLIENTE=").append(idCliente);
		if (idSubSerie != null) {
			sql.append(" AND SUB.ID_SUBSERIE<>").append(idSubSerie);
		}
		sql.append(")");
		return sql.toString();
	}

	/**
	 * SQL para construir el DELETE de una sub-serie documental con sus tablas asociadas
	 */
	public static List<String> deleteSubSerieDocumental(Long idSubSerie) {
		List<String> deletes = new ArrayList<>();
		deletes.add("DELETE FROM TIPOS_DOCUMENTALES_SUBSERIES WHERE ID_SUBSERIE="+idSubSerie);
		deletes.add("DELETE FROM SUBSERIES_DOCUMENTALES WHERE ID_SUBSERIE="+idSubSerie);
		return deletes;
	}

	/**
	 * SQL para construir el INSERT para la tabla TIPOS_DOCUMENTALES_SERIES
	 */
	public static String insertTipoDocumentalSerie(Integer idDoc, Long idSerie) {
		StringBuilder sql = new StringBuilder("INSERT INTO TIPOS_DOCUMENTALES_SERIES(ID_TIPO_DOC,ID_SERIE)VALUES(");
		sql.append(idDoc).append(",").append(idSerie).append(")");
		return sql.toString();
	}

	/**
	 * SQL para construir el INSERT para la tabla TIPOS_DOCUMENTALES_SUBSERIES
	 */
	public static String insertTipoDocumentalSubSerie(Integer idDoc, Long idSubSerie) {
		StringBuilder sql = new StringBuilder("INSERT INTO TIPOS_DOCUMENTALES_SUBSERIES(ID_TIPO_DOC,ID_SUBSERIE)VALUES(");
		sql.append(idDoc).append(",").append(idSubSerie).append(")");
		return sql.toString();
	}

	/**
	 * SQL para construir el INSERT para la tabla TIPOS_DOCUMENTALES
	 */
	public static String insertTipoDocumental(Integer idCliente) {
		StringBuilder sql = new StringBuilder("INSERT INTO TIPOS_DOCUMENTALES(NOMBRE,CLIENTE)VALUES(?,");
		sql.append(idCliente).append(")");
		return sql.toString();
	}

	/**
	 * SQL para construir el INSERT para la tabla TIPOS_DOCUMENTALES_SERIES sin el ID del tipos documentales
	 */
	public static String insertTipoDocumentalSerieSinID(Integer idCliente, Long idSerie) {
		StringBuilder sql = new StringBuilder("INSERT INTO TIPOS_DOCUMENTALES_SERIES(ID_SERIE,ID_TIPO_DOC)VALUES(");
		sql.append(idSerie);
		sql.append(",(SELECT ID_TIPO_DOC FROM TIPOS_DOCUMENTALES WHERE CLIENTE=");
		sql.append(idCliente);
		sql.append(" AND NOMBRE=?))");
		return sql.toString();
	}

	/**
	 * SQL para construir el INSERT para la tabla TIPOS_DOCUMENTALES_SUBSERIES sin el ID del tipos documentales
	 */
	public static String insertTipoDocumentalSubSerieSinID(Integer idCliente, Long idSubSerie) {
		StringBuilder sql = new StringBuilder("INSERT INTO TIPOS_DOCUMENTALES_SUBSERIES(ID_SUBSERIE,ID_TIPO_DOC)VALUES(");
		sql.append(idSubSerie);
		sql.append(",(SELECT ID_TIPO_DOC FROM TIPOS_DOCUMENTALES WHERE CLIENTE=");
		sql.append(idCliente);
		sql.append(" AND NOMBRE=?))");
		return sql.toString();
	}
}
