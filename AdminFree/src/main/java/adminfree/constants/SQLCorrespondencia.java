package adminfree.constants;

import adminfree.enums.Estado;
import adminfree.enums.TipoCampo;

/**
 *
 * Clase constante que contiene los DML Y DDL para el modulo de Correspondencia
 *
 * @author Carlos Andres Diaz
 *
 */
public class SQLCorrespondencia {

	/** SQL para obtener el detalle de los campos asociados a una nomenclatura*/
	public static final String GET_DTL_NOMENCLATURA_CAMPOS = "SELECT CE.ID_CAMPO,CE.NOMBRE,CE.DESCRIPCION,CE.TIPO_CAMPO,GROUP_CONCAT(CER.RESTRICCION SEPARATOR ';') AS RESTRICCIONES,NCE.ID_NOME_CAMPO FROM NOMENCLATURAS_CAMPOS_ENTRADA NCE JOIN CAMPOS_ENTRADA CE ON(CE.ID_CAMPO = NCE.CAMPO)LEFT JOIN NOMENCLATURAS_CAMPOS_RESTRICS CER ON(CER.ID_NOME_CAMPO = NCE.ID_NOME_CAMPO)WHERE NCE.NOMENCLATURA=? GROUP BY CE.ID_CAMPO,CE.NOMBRE,CE.DESCRIPCION,CE.TIPO_CAMPO,NCE.ORDEN,NCE.ID_NOME_CAMPO ORDER BY NCE.ORDEN";

	/** SQL para obtener los datos de la nomenclatura para solicitar el consecutivo*/
	public static final String GET_SECUENCIA_NOMENCLATURA = "SELECT CONSECUTIVO_INICIAL, SECUENCIA, CONSECUTIVOS_SOLICITADOS FROM NOMENCLATURAS WHERE ID_NOMENCLATURA=?";

	/** SQL para obtener la cantidad de consecutivos del usuario*/
	public static final String GET_CANT_CONSECUTIVOS_USER = "SELECT CONSECUTIVOS_SOLICITADOS FROM USUARIOS WHERE ID_USUARIO=?";

	/** SQL para obtener las nomenclaturas para bienvenida*/
	public static final String GET_WELCOME_NOMENCLATURAS = "SELECT ID_NOMENCLATURA, NOMENCLATURA, DESCRIPCION, CONSECUTIVOS_SOLICITADOS FROM NOMENCLATURAS WHERE CLIENTE=? ORDER BY CONSECUTIVOS_SOLICITADOS DESC, NOMENCLATURA";

	/** SQL para obtener los usuarios para bienvenida*/
	public static final String GET_WELCOME_USUARIOS = "SELECT ID_USUARIO, NOMBRE, CARGO, ESTADO, CONSECUTIVOS_SOLICITADOS FROM USUARIOS WHERE CLIENTE=? ORDER BY NOMBRE";

	/** SQL para obtener los campos de entrada informacion para los filtros de busqueda*/
	public static final String GET_CAMPOS_FILTRO = "SELECT ID_CAMPO,NOMBRE,TIPO_CAMPO FROM CAMPOS_ENTRADA WHERE CLIENTE=? AND TIPO_CAMPO<>" + TipoCampo.CASILLA_VERIFICACION.id + " ORDER BY NOMBRE ASC";

	/** SQL para obtener los usuarios para transferir consecutivos*/
	public static final String GET_USUARIOS_TRANSFERIR = "SELECT ID_USUARIO,NOMBRE,CARGO FROM USUARIOS WHERE CLIENTE=? AND ESTADO=? AND ID_USUARIO<>? ORDER BY NOMBRE";

	/**
	 * Metodo que permite construir el insert para los consecutivos
	 * de correspondencia asociado al cliente autenticado en el sistema
	 */
	public static String getInsertConsecutivo(String idCliente) {
		StringBuilder insert = new StringBuilder("INSERT INTO CONSECUTIVOS_");
		insert.append(idCliente);
		insert.append("(NOMENCLATURA,CONSECUTIVO,USUARIO,FECHA_SOLICITUD,ESTADO)VALUES(?,?,?,CURDATE(),");
		insert.append(Estado.ACTIVO.id).append(")");
		return insert.toString();
	}

	/**
	 * Metodo que permite construir el INSERT para los valores del
	 * consecutivo asociado al cliente autenticado en el sistema
	 */
	public static String getInsertConsecutivoValues(String idCliente) {
		StringBuilder insert = new StringBuilder("INSERT INTO CONSECUTIVOS_VALUES_");
		insert.append(idCliente);
		insert.append("(ID_CONSECUTIVO,ID_NOME_CAMPO,VALOR)VALUES(?,?,?)");
		return insert.toString();
	}

	/**
	 * Metodo que permite construir el UPDATE para los valores del
	 * consecutivo asociado al cliente autenticado en el sistema
	 */
	public static String getUpdateConsecutivoValues(String idCliente) {
		StringBuilder insert = new StringBuilder("UPDATE CONSECUTIVOS_VALUES_");
		insert.append(idCliente);
		insert.append(" SET VALOR=? WHERE ID_VALUE=?");
		return insert.toString();
	}

	/**
	 * Metodo que permite construir el update para la secuencia asociada a una nomenclatura
	 */
	public static String getUpdateNomenclaturaSecuenciaCantidad(String secuencia, String cantidadConsecutivos, String idNomenclatura) {
		StringBuilder update = new StringBuilder("UPDATE NOMENCLATURAS SET SECUENCIA=");
		update.append(secuencia);
		update.append(", CONSECUTIVOS_SOLICITADOS=");
		update.append(cantidadConsecutivos);
		update.append(" WHERE ID_NOMENCLATURA=");
		update.append(idNomenclatura);
		return update.toString();
	}

	/**
	 * Metodo que permite construir el update para actualizar la cantidad de consecutivos del usuario
	 */
	public static String getUpdateUsuarioCantidadConsecutivos(String idUsuario, String cantidadConsecutivos) {
		StringBuilder update = new StringBuilder("UPDATE USUARIOS SET CONSECUTIVOS_SOLICITADOS=");
		update.append(cantidadConsecutivos);
		update.append(" WHERE ID_USUARIO=");
		update.append(idUsuario);
		return update.toString();
	}

	/**
	 * Metodo que permite construir el update para actualizar la bandera "tiene consecutivos"
	 * de la tabla NOMENCLATURAS_CAMPOS_ENTRADA asociada a la nomenclatura seleccionada
	 */
	public static String getUpdateCamposTieneConsecutivo(String idNomenclatura) {
		StringBuilder update = new StringBuilder("UPDATE NOMENCLATURAS_CAMPOS_ENTRADA SET TIENE_CONSECUTIVO=1 WHERE NOMENCLATURA=");
		update.append(idNomenclatura);
		return update.toString();
	}

	/**
	 * Metodo que permite construir el SQL para validar si existe otro valor igual
	 * para los consecutivos de correspondencia solicitados
	 */
	public static String isExistsValor(String idCliente, String idNomenclatura) {
		StringBuilder sql = new StringBuilder("SELECT EXISTS(SELECT * FROM CONSECUTIVOS_VALUES_");
		sql.append(idCliente).append(" VL ");
		sql.append("JOIN NOMENCLATURAS_CAMPOS_ENTRADA NCE ON(NCE.ID_NOME_CAMPO = VL.ID_NOME_CAMPO)");
		sql.append("WHERE NCE.CAMPO=? AND VL.VALOR=?");

		// se configura el identificador de la nomenclatura
		if (idNomenclatura != null) {
			sql.append(" AND NCE.NOMENCLATURA=").append(idNomenclatura);
		}
		sql.append(")");
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el SQL para validar si existe otro
	 * documento con el mismo nombre para el mismo consecutivo
	 */
	public static String isExistsNombreDocumento(String idCliente, String idConsecutivo) {
		StringBuilder sql = new StringBuilder("SELECT EXISTS(SELECT * FROM CONSECUTIVOS_DOCUMENTOS_");
		sql.append(idCliente);
		sql.append(" WHERE ID_CONSECUTIVO=");
		sql.append(idConsecutivo);
		sql.append(" AND NOMBRE_DOCUMENTO=?)");
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el insert para los documentos asociados a un cliente
	 */
	public static String getSQLInsertDocumento(String idCliente) {
		StringBuilder sql = new StringBuilder("INSERT INTO CONSECUTIVOS_DOCUMENTOS_");
		sql.append(idCliente);
		sql.append("(ID_CONSECUTIVO,NOMBRE_DOCUMENTO,TIPO_DOCUMENTO,SIZE_DOCUMENTO,FECHA_CARGUE)VALUES(?,?,?,?,CURDATE())");
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el select para listar los documentos de un consecutivo
	 */
	public static String getSQListDocumentos(String idCliente, String idConsecutivo) {
		StringBuilder sql = new StringBuilder("SELECT ID_DOC,NOMBRE_DOCUMENTO,TIPO_DOCUMENTO,SIZE_DOCUMENTO,DATE_FORMAT(FECHA_CARGUE,'");
		sql.append(CommonConstant.FORMATO_FECHA_MSYQL);
		sql.append("')AS FECH FROM CONSECUTIVOS_DOCUMENTOS_");
		sql.append(idCliente);
		sql.append(" WHERE ID_CONSECUTIVO=");
		sql.append(idConsecutivo);
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el delete para la eliminacion de un documento
	 */
	public static String getSQLEliminarDocumento(String idCliente, String idDocumento) {
		StringBuilder sql = new StringBuilder("DELETE FROM CONSECUTIVOS_DOCUMENTOS_");
		sql.append(idCliente);
		sql.append(" WHERE ID_DOC=");
		sql.append(idDocumento);
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el select para obtener los datos del documento eliminar
	 */
	public static String getSQLDatosDocumentoEliminar(String idCliente, String idDocumento) {
		StringBuilder sql = new StringBuilder("SELECT ID_CONSECUTIVO,NOMBRE_DOCUMENTO FROM CONSECUTIVOS_DOCUMENTOS_");
		sql.append(idCliente);
		sql.append(" WHERE ID_DOC=");
		sql.append(idDocumento);
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el FROM para obtener los consecutivos del anio actual
	 */
	public static StringBuilder getSQLFromConsecutivosAnioActual(String idCliente) {
		StringBuilder sql = new StringBuilder(" FROM CONSECUTIVOS_");
		sql.append(idCliente);
		sql.append(" CON LEFT JOIN NOMENCLATURAS NOM ON(NOM.ID_NOMENCLATURA = CON.NOMENCLATURA)LEFT JOIN USUARIOS US ON(US.ID_USUARIO = CON.USUARIO) WHERE");
		return sql;
	}

	/**
	 * Metodo que permite construir el SQL para obtener los consecutivos del anio actual
	 */
	public static StringBuilder getSQLSelectConsecutivosAnioActual(StringBuilder from) {
		StringBuilder sql = new StringBuilder("SELECT CON.ID_CONSECUTIVO,CON.CONSECUTIVO,NOM.NOMENCLATURA,COALESCE(US.NOMBRE, 'Administrador')AS USUARIO,DATE_FORMAT(CON.FECHA_SOLICITUD,'");
		sql.append(CommonConstant.FORMATO_FECHA_MSYQL);
		sql.append("')AS FECHA_SOLI,CON.ESTADO");
		sql.append(from);
		return sql;
	}

	/**
	 * Metodo que permite construir el SQL para obtener los datos generales del consecutivo
	 */
	public static String getSQLConsecutivo(String idCliente, String idConsecutivo) {
		StringBuilder sql = new StringBuilder("SELECT CON.ID_CONSECUTIVO,CON.CONSECUTIVO,NOM.NOMENCLATURA,COALESCE(US.NOMBRE,'Administrador')AS USUARIO,DATE_FORMAT(CON.FECHA_SOLICITUD,'");
		sql.append(CommonConstant.FORMATO_FECHA_MSYQL);
		sql.append("')AS FECHA_SOLI,CON.ESTADO,NOM.DESCRIPCION,DATE_FORMAT(CON.FECHA_ANULACION,'");
		sql.append(CommonConstant.FORMATO_FECHA_MSYQL);
		sql.append("')AS FECHA_NULA,US.CARGO,NOM.ID_NOMENCLATURA FROM CONSECUTIVOS_");
		sql.append(idCliente);
		sql.append(" CON LEFT JOIN NOMENCLATURAS NOM ON(NOM.ID_NOMENCLATURA = CON.NOMENCLATURA)LEFT JOIN USUARIOS US ON(US.ID_USUARIO = CON.USUARIO) WHERE CON.ID_CONSECUTIVO=");
		sql.append(idConsecutivo);
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el SQL para obtener los valores del consecutivo
	 */
	public static String getSQLConsecutivoValues(String idCliente, String idConsecutivo) {
		StringBuilder sql = new StringBuilder("SELECT CV.ID_VALUE,CA.NOMBRE,CA.DESCRIPCION,");
		SQLTransversal.getSQLSelectCampoValue(sql);
		sql.append(" FROM CONSECUTIVOS_VALUES_");
		sql.append(idCliente);
		sql.append(" CV LEFT JOIN NOMENCLATURAS_CAMPOS_ENTRADA NC ON(NC.ID_NOME_CAMPO=CV.ID_NOME_CAMPO)LEFT JOIN CAMPOS_ENTRADA CA ON(CA.ID_CAMPO=NC.CAMPO)WHERE CV.ID_CONSECUTIVO=");
		sql.append(idConsecutivo);
		sql.append(" ORDER BY NC.ORDEN");
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el SQL para listar las transferencia realizadas para un consecutivo
	 */
	public static String getSQListTransferencias(String idCliente, String idConsecutivo) {
		StringBuilder sql = new StringBuilder("SELECT COALESCE(US.NOMBRE,'Administrador')AS USER,US.CARGO,DATE_FORMAT(TR.FECHA_TRANSFERIDO,'");
		sql.append(CommonConstant.FORMATO_FECHA_MSYQL);
		sql.append("')AS FE FROM CONSECUTIVOS_TRANS_");
		sql.append(idCliente);
		sql.append(" TR LEFT JOIN USUARIOS US ON(US.ID_USUARIO = TR.USUARIO)");
		sql.append("WHERE TR.ID_CONSECUTIVO=").append(idConsecutivo).append(" ORDER BY TR.ID_TRANS ASC");
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el SQL para obtener los datos del documento a descargar
	 */
	public static String getSQLDatosDocumentoDescargar(String idCliente) {
		StringBuilder sql = new StringBuilder("SELECT ID_CONSECUTIVO,NOMBRE_DOCUMENTO,TIPO_DOCUMENTO,SIZE_DOCUMENTO FROM CONSECUTIVOS_DOCUMENTOS_");
		sql.append(idCliente);
		sql.append(" WHERE ID_DOC=?");
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el SQL para obtener los items para los campos filtro busqueda
	 */
	public static String getSQLItemsFiltro(String parametrosJDBC) {
		StringBuilder sql = new StringBuilder("SELECT ID_ITEM,CAMPO,VALOR FROM SELECT_ITEMS WHERE CAMPO IN(");
		sql.append(parametrosJDBC);
		sql.append(")ORDER BY CAMPO,VALOR ASC");
		return sql.toString();
	}

	/**
	 * Metodo que permite construir el SQL para obtener los values de un consecutivo para su edicion
	 */
	public static String getSQLValuesEdicion(String idCliente, String idConsecutivo) {
		StringBuilder sql = new StringBuilder("SELECT VL.ID_VALUE,VL.ID_NOME_CAMPO,CASE WHEN CE.TIPO_CAMPO=");
		sql.append(TipoCampo.CAMPO_FECHA.id);
		sql.append(" THEN (STR_TO_DATE(VL.VALOR,");
		sql.append(CommonConstant.FORMATO_FECHA_SQL);
		sql.append(")) ELSE VL.VALOR END AS VALOR FROM CONSECUTIVOS_VALUES_");
		sql.append(idCliente);
		sql.append(" VL JOIN NOMENCLATURAS_CAMPOS_ENTRADA NC ON(NC.ID_NOME_CAMPO=VL.ID_NOME_CAMPO)");
		sql.append("JOIN CAMPOS_ENTRADA CE ON(CE.ID_CAMPO=NC.CAMPO) WHERE VL.ID_CONSECUTIVO=");
		sql.append(idConsecutivo);
		sql.append(" ORDER BY NC.ORDEN");
		return sql.toString();
	}
}
