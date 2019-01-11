package adminfree.constants;

/**
 *
 * Clase constante que contiene los DML Y DDL para el modulo de Correspondencia
 *
 * @author Carlos Andres Diaz
 *
 */
public class SQLCorrespondencia {

	/** SQL para obtener el detalle de una nomenclatura*/
	public static final String GET_DTL_NOMENCLATURA = "SELECT N.ID_NOMENCLATURA AS ID, N.NOMENCLATURA AS NOMEN, N.DESCRIPCION AS NODESC, N.CONSECUTIVO_INICIAL AS NOCON, CE.NOMBRE AS CANOM, CE.DESCRIPCION AS CADES, CE.TIPO_CAMPO AS CATIPO FROM NOMENCLATURAS N LEFT JOIN NOMENCLATURAS_CAMPOS_ENTRADA NC ON(NC.NOMENCLATURA = N.ID_NOMENCLATURA) LEFT JOIN CAMPOS_ENTRADA CE ON(CE.ID_CAMPO = NC.CAMPO) WHERE N.ID_NOMENCLATURA =?";
}
