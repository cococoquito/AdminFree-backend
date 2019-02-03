package adminfree.constants;

import adminfree.enums.Estado;

/**
 *
 * Clase constante que contiene los DML Y DDL para el modulo de Correspondencia
 *
 * @author Carlos Andres Diaz
 *
 */
public class SQLCorrespondencia {

	/** SQL para obtener el detalle de una nomenclatura*/
	public static final String GET_DTL_NOMENCLATURA = "SELECT N.ID_NOMENCLATURA, N.NOMENCLATURA, N.DESCRIPCION, N.CONSECUTIVO_INICIAL, N.SECUENCIA FROM NOMENCLATURAS N WHERE N.ID_NOMENCLATURA =?";

	/** SQL para obtener el detalle de los campos asociados a una nomenclatura*/
	public static final String GET_DTL_NOMENCLATURA_CAMPOS = "SELECT CE.ID_CAMPO, CE.NOMBRE, CE.DESCRIPCION, CE.TIPO_CAMPO, GROUP_CONCAT(CER.RESTRICCION SEPARATOR ';') AS RESTRICCIONES, NCE.ID_NOME_CAMPO FROM NOMENCLATURAS_CAMPOS_ENTRADA NCE JOIN CAMPOS_ENTRADA CE ON(CE.ID_CAMPO = NCE.CAMPO) LEFT JOIN CAMPOS_ENTRADA_RESTRICCIONES CER ON(CER.CAMPO = CE.ID_CAMPO) WHERE NCE.NOMENCLATURA=? GROUP BY CE.ID_CAMPO, CE.NOMBRE, CE.DESCRIPCION, CE.TIPO_CAMPO, NCE.ORDEN, NCE.ID_NOME_CAMPO ORDER BY NCE.ORDEN";

	/** SQL para obtener los datos de la nomenclatura para solicitar el consecutivo*/
	public static final String GET_SECUENCIA_NOMENCLATURA = "SELECT CONSECUTIVO_INICIAL, SECUENCIA FROM NOMENCLATURAS WHERE ID_NOMENCLATURA=?";

	/**
	 * Metodo que permite construir el insert para los consecutivos
	 * de correspondencia asociado al cliente autenticado en el sistema
	 */
	public static String getInsertConsecutivo(Long idCliente) {
		StringBuilder insert = new StringBuilder("INSERT INTO CONSECUTIVOS_");
		insert.append(idCliente);
		insert.append("(NOMENCLATURA,CONSECUTIVO,USUARIO,FECHA_SOLICITUD,ESTADO)VALUES(?,?,?,CURDATE(),");
		insert.append(Estado.ACTIVO.id).append(")");
		return insert.toString();
	}

	/**
	 * Metodo que permite construir el insert para los valores del
	 * consecutivo asociado al cliente autenticado en el sistema
	 */
	public static String getInsertConsecutivoValues(Long idCliente) {
		StringBuilder insert = new StringBuilder("INSERT INTO CONSECUTIVOS_VALUES_");
		insert.append(idCliente);
		insert.append("(ID_CONSECUTIVO,CAMPO,VALOR)VALUES(?,?,?)");
		return insert.toString();
	}

	/**
	 * Metodo que permite construir el update para la secuencia asociada a una nomenclatura
	 */
	public static String getUpdateNomenclaturaSecuencia(Integer secuencia, Long idNomenclatura) {
		StringBuilder update = new StringBuilder("UPDATE NOMENCLATURAS SET SECUENCIA=");
		update.append(secuencia);
		update.append(" WHERE ID_NOMENCLATURA=");
		update.append(idNomenclatura);
		return update.toString();
	}

	/**
	 * Metodo que permite construir el update para actualizar la bandera "tiene consecutivos"
	 * de la tabla NOMENCLATURAS_CAMPOS_ENTRADA asociada a la nomenclatura seleccionada
	 */
	public static String getUpdateCamposTieneConsecutivo(Long idNomenclatura) {
		StringBuilder update = new StringBuilder("UPDATE NOMENCLATURAS_CAMPOS_ENTRADA SET TIENE_CONSECUTIVO=1 WHERE NOMENCLATURA=");
		update.append(idNomenclatura);
		return update.toString();
	}
}
