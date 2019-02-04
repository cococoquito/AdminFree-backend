package adminfree.constants;

/**
 * Clase constante que contiene los DML Y DDL para el modulo de Configuraciones
 *
 * @author Carlos Andres Diaz
 *
 */
public class SQLConfiguraciones {

	/** SQL para obtener todos los CLIENTES del sistema */
	public static final String LISTAR_CLIENTES = "SELECT ID_CLIENTE, TOKEN, NOMBRE, TELEFONOS, EMAILS, FECHA_ACTIVACION, FECHA_INACTIVACION, ESTADO, USUARIO FROM CLIENTES";

	/** SQL para obtener un CLIENTE con base al TOKEN */
	public static final String GET_CLIENTE_TOKEN = "SELECT ID_CLIENTE, TOKEN, NOMBRE, TELEFONOS, EMAILS, FECHA_ACTIVACION, ESTADO, USUARIO FROM CLIENTES WHERE TOKEN =?";

	/** SQL para contar los clientes que contenga un TOKEN especifico */
	public static final String COUNT_CLIENTE_TOKEN = "SELECT COUNT(*) FROM CLIENTES WHERE TOKEN =?";

	/** SQL para ACTUALIZAR los datos del CLIENTE */
	public static final String ACTUALIZAR_CLIENTE = "UPDATE CLIENTES SET NOMBRE=?, EMAILS=?, TELEFONOS=? WHERE ID_CLIENTE=?";

	/** SQL para ACTIVAR un CLIENTE */
	public static final String ACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=NULL, FECHA_ACTIVACION = CURDATE() WHERE ID_CLIENTE=?";

	/** SQL para INACTIVAR un CLIENTE */
	public static final String INACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=CURDATE() WHERE ID_CLIENTE=?";

	/** SQL para obtener todos los USUARIOS ACTIVO/INACTIVO del sistema asociados a un CLIENTE */
	public static final String GET_USUARIOS_CLIENTE = "SELECT US.ID_USUARIO AS ID_USER, US.NOMBRE AS NOMBRE_USER, US.USUARIO_INGRESO AS INGRESO_USER, US.ESTADO AS ESTADO_USER, GROUP_CONCAT(USER_MODULO.TOKEN_MODULO SEPARATOR ';') AS MODULOS FROM USUARIOS US LEFT JOIN USUARIOS_MODULOS USER_MODULO ON(USER_MODULO.ID_USUARIO = US.ID_USUARIO) WHERE US.CLIENTE =? AND (US.ESTADO =? OR US.ESTADO =?) GROUP BY US.ID_USUARIO, US.NOMBRE, US.USUARIO_INGRESO, US.ESTADO ORDER BY US.NOMBRE";

	/** SQL para contar los USUARIO que contenga USUARIO_INGRESO especifico */
	public static final String COUNT_USUARIO_INGRESO = "SELECT COUNT(*) FROM USUARIOS WHERE USUARIO_INGRESO =?";

	/** SQL para la creacion del USUARIO */
	public static final String CREAR_USUARIO = "INSERT INTO USUARIOS (NOMBRE, USUARIO_INGRESO, CLAVE_INGRESO, CLIENTE, ESTADO) VALUES (?,?,?,?,?)";

	/** SQL para insertar los privilegios de un usuario */
	public static final String INSERTAR_PRIVILEGIOS_USER = "INSERT INTO USUARIOS_MODULOS (ID_USUARIO, TOKEN_MODULO) VALUES (?,?)";

	/** SQL para modificar el estado del usuario */
	public static final String UPDATE_ESTADO_USER = "UPDATE USUARIOS SET ESTADO =? WHERE ID_USUARIO =?";

	/** Se utiliza para actualizar la clave de ingreso del Usuario */
	public static final String ACTUALIZAR_CLAVE_INGRESO = "UPDATE USUARIOS SET CLAVE_INGRESO =? WHERE ID_USUARIO =?";

	/** Se utiliza para actualizar la cuenta del usuario */
	public static final String UPDATE_DATOS_CUENTA = "UPDATE USUARIOS SET NOMBRE =?, USUARIO_INGRESO =? WHERE ID_USUARIO =?";

	/** Se utiliza para actualizar el nombre del usuario */
	public static final String UPDATE_NOMBRE_USER = "UPDATE USUARIOS SET NOMBRE =? WHERE ID_USUARIO =?";

	/** Se utiliza para obtener la clave de ingreso */
	public static final String GET_CLAVE_INGRESO = "SELECT CLAVE_INGRESO FROM USUARIOS WHERE ID_USUARIO =?";

	/** Se utiliza para obtener las restricciones para los campos de ingreso */
	public static final String GET_RESTRICCIONES_CAMPO_INGRESO = "SELECT RE.ID_RESTRICCION, RE.DESCRIPCION, RT.COMPATIBLE FROM RESTRICCIONES RE JOIN RESTRICCIONES_TIPO_CAMPO RT ON (RT.RESTRICCION = RE.ID_RESTRICCION) WHERE RT.TIPO_CAMPO =?";

	/** Se utiliza para la creacion de los campos de entrada de informacion */
	public static final String COUNT_EXISTE_CAMPO_ENTRADA = "SELECT COUNT(*) FROM CAMPOS_ENTRADA WHERE TIPO_CAMPO =? AND NOMBRE =? AND CLIENTE =?";

	/** Se utiliza para la inserccion de los campos de entrada de informacion */
	public static final String INSERTAR_CAMPO_ENTRADA = "INSERT INTO CAMPOS_ENTRADA (CLIENTE,TIPO_CAMPO,NOMBRE,DESCRIPCION) VALUES (?,?,?,?)";

	/** Se utiliza para obtener los campos de entrada de informacion asociado a un cliente */
	public static final String GET_CAMPOS_ENTRADA_INFORMACION = "SELECT ID_CAMPO, TIPO_CAMPO, NOMBRE FROM CAMPOS_ENTRADA WHERE CLIENTE =? ORDER BY TIPO_CAMPO";

	/** Se utiliza para obtener el detalle del campo de entrada */
	public static final String GET_DETALLE_CAMPO_ENTRADA = "SELECT CE.ID_CAMPO AS ID_CAMPO, CE.CLIENTE AS ID_CLIENTE, CE.NOMBRE AS NOMBRE_CAMPO, CE.DESCRIPCION AS DESCRIPCION_CAMPO, CE.TIPO_CAMPO AS TIPO_CAMPO, R.ID_RESTRICCION AS ID_RES, R.DESCRIPCION AS DESCRIPCION_RES FROM CAMPOS_ENTRADA CE LEFT JOIN CAMPOS_ENTRADA_RESTRICCIONES RES ON (RES.CAMPO = CE.ID_CAMPO) LEFT JOIN RESTRICCIONES R ON (R.ID_RESTRICCION = RES.RESTRICCION) WHERE CE.ID_CAMPO =?";

	/** Se consulta los items de un campo tipo selectitems */
	public static final String GET_ITEMS = "SELECT ID_ITEM, VALOR FROM SELECT_ITEMS WHERE CAMPO =?";

	/** Se utiliza si intentan eliminar un campo y esta tiene asociado una nomenclatura */
	public static final String COUNT_CAMPO_NOMENCLATURA_ASOCIADA = "SELECT COUNT(*) FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE CAMPO =?";

	/** Se utiliza para obtener el detalle de un campo de entrada para editar */
	public static final String GET_DETALLE_CAMPO_EDITAR = "SELECT CE.ID_CAMPO AS ID_CAMPO, CE.CLIENTE AS ID_CLIENTE, CE.NOMBRE AS NOMBRE_CAMPO, CE.DESCRIPCION AS DESCRIPCION_CAMPO, CE.TIPO_CAMPO AS TIPO_CAMPO, (SELECT COUNT(*) FROM NOMENCLATURAS_CAMPOS_ENTRADA NCE WHERE NCE.CAMPO = CE.ID_CAMPO AND NCE.TIENE_CONSECUTIVO IS NULL) AS NOMENCLATURAS, (SELECT COUNT(*) FROM CAMPOS_ENTRADA_RESTRICCIONES RE WHERE RE.CAMPO = CE.ID_CAMPO) AS RESTRICCIONES, (SELECT COUNT(*) FROM NOMENCLATURAS_CAMPOS_ENTRADA NCC WHERE NCC.CAMPO = CE.ID_CAMPO AND NCC.TIENE_CONSECUTIVO IS NOT NULL) AS CONSECUTIVOS FROM CAMPOS_ENTRADA CE WHERE CE.ID_CAMPO =?";

	/** Se utiliza para la edicion de las restricciones */
	public static final String GET_RESTRICCIONES_EDICION = "SELECT RESTRICCION FROM CAMPOS_ENTRADA_RESTRICCIONES WHERE CAMPO =?";

	/** DML para actualizar la descripcion y el nombre del campo de entrada */
	public static final String UPDATE_CAMPO_DESCRIPCION_NOMBRE = "UPDATE CAMPOS_ENTRADA SET DESCRIPCION=?, NOMBRE=? WHERE ID_CAMPO=?";

	/** Se utiliza para obtener todas las nomenclaturas asociadas a un cliente */
	public static final String GET_NOMENCLATURAS = "SELECT ID_NOMENCLATURA, NOMENCLATURA, DESCRIPCION, CONSECUTIVO_INICIAL FROM NOMENCLATURAS WHERE CLIENTE =?";

	/** Se utiliza para crear la nomenclatura */
	public static final String INSERT_NOMENCLATURA = "INSERT INTO NOMENCLATURAS (NOMENCLATURA, DESCRIPCION, CLIENTE, CONSECUTIVO_INICIAL) VALUES (?,?,?,?)";

	/** Se utiliza para actualizar los datos basicos de la nomenclatura */
	public static final String UPDATE_NOMENCLATURA = "UPDATE NOMENCLATURAS SET NOMENCLATURA=?, DESCRIPCION=?, CONSECUTIVO_INICIAL=? WHERE ID_NOMENCLATURA=?";

	/** Se utiliza para identificar si existe la nomenclatura asociada a un cliente */
	public static final String EXISTE_NOMENCLATURA = "SELECT COUNT(*) FROM NOMENCLATURAS WHERE NOMENCLATURA=? AND CLIENTE=?";

	/** Se utiliza para validar si existe consecutivos asociados a una nomenclatura */
	public static final String GET_SECUENCIA_NOMENCLATURA = "SELECT SECUENCIA FROM NOMENCLATURAS WHERE ID_NOMENCLATURA=?";

	/** Se utiliza para eliminar los campos asociados a la nomenclatura */
	public static final String DELETE_NOMENCLATURA_CAMPOS = "DELETE FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE NOMENCLATURA=?";

	/** Se utiliza para eliminar la nomenclatura */
	public static final String DELETE_NOMENCLATURA = "DELETE FROM NOMENCLATURAS WHERE ID_NOMENCLATURA=?";

	/** Se utiliza para obtener el detalle de la nomenclatura */
	public static final String GET_DETALLE_NOMENCLATURA = "SELECT NOM.ID_NOMENCLATURA,NOM.NOMENCLATURA,NOM.DESCRIPCION,NOM.CONSECUTIVO_INICIAL,NOM.SECUENCIA,NOC.ID_NOME_CAMPO,CP.ID_CAMPO,CP.NOMBRE,CP.TIPO_CAMPO,NOC.TIENE_CONSECUTIVO,NOC.ORDEN FROM NOMENCLATURAS NOM LEFT JOIN NOMENCLATURAS_CAMPOS_ENTRADA NOC ON (NOM.ID_NOMENCLATURA = NOC.NOMENCLATURA) LEFT JOIN CAMPOS_ENTRADA CP ON (CP.ID_CAMPO = NOC.CAMPO) WHERE NOM.ID_NOMENCLATURA=? ORDER BY NOC.ORDEN";

	/**
	 * Metodo que construye el SQL para eliminar los privilegios asociados a un usuario
	 */
	public static String getSQlDeletePrivilegiosUser(String idUser) {
		StringBuilder sql = new StringBuilder("DELETE FROM USUARIOS_MODULOS WHERE ID_USUARIO=");
		sql.append(idUser);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para insertar los privilegios de un usuario
	 */
	public static String getSQLInsertPrivilegiosUser(String idUser, String token) {
		StringBuilder sql = new StringBuilder("INSERT INTO USUARIOS_MODULOS (ID_USUARIO, TOKEN_MODULO) VALUES (");
		sql.append(idUser).append(",'");
		sql.append(token).append("')");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para insertar las restricciones de los campos de ingreso
	 */
	public static String getSQLInsertRestriccionesCampo(String idCampo, String idRestriccion) {
		StringBuilder sql = new StringBuilder("INSERT INTO CAMPOS_ENTRADA_RESTRICCIONES (CAMPO,RESTRICCION) VALUES (");
		sql.append(idCampo).append(",");
		sql.append(idRestriccion).append(")");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para insertar los items para un campo lista desplegable
	 */
	public static String getSQLInsertSelectItems(String idCampo, String valor) {
		StringBuilder sql = new StringBuilder("INSERT INTO SELECT_ITEMS (CAMPO,VALOR) VALUES (");
		sql.append(idCampo).append(",'");
		sql.append(valor).append("')");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para eliminar las restricciones de un campo de ingreso
	 */
	public static String getSQLDeleteCampoRestricciones(String idCampo) {
		StringBuilder sql = new StringBuilder("DELETE FROM CAMPOS_ENTRADA_RESTRICCIONES WHERE CAMPO=");
		sql.append(idCampo);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para eliminar los items de una lista desplegable
	 */
	public static String getSQLDeleteSelectItems(String idItem) {
		StringBuilder sql = new StringBuilder("DELETE FROM SELECT_ITEMS WHERE ID_ITEM=");
		sql.append(idItem);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para actualizar el valor de un item
	 */
	public static String getSQLUpdateSelectItems(String valor, String idItem) {
		StringBuilder sql = new StringBuilder("UPDATE SELECT_ITEMS SET VALOR='");
		sql.append(valor).append("' WHERE ID_ITEM=");
		sql.append(idItem);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para eliminar los items de un campo
	 */
	public static String getSQLDeleteCamposItems(String idCampo) {
		StringBuilder sql = new StringBuilder("DELETE FROM SELECT_ITEMS WHERE CAMPO=");
		sql.append(idCampo);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para eliminar el campo de entrada
	 */
	public static String getSQLDeleteCampoEntrada(String idCampo) {
		StringBuilder sql = new StringBuilder("DELETE FROM CAMPOS_ENTRADA WHERE ID_CAMPO=");
		sql.append(idCampo);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para insertar los campos asociados a una nomenclatura
	 */
	public static String getSQLInsertCamposNomenclatura(String idNomenclatura, String idCampo, String orden) {
		StringBuilder sql = new StringBuilder("INSERT INTO NOMENCLATURAS_CAMPOS_ENTRADA (NOMENCLATURA, CAMPO, ORDEN) VALUES (");
		sql.append(idNomenclatura).append(",");
		sql.append(idCampo).append(",");
		sql.append(orden).append(")");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para eliminar los campos asociados a una nomenclatura
	 */
	public static String getSQLDeleteCamposNomenclatura(String idNomenclatura) {
		StringBuilder sql = new StringBuilder("DELETE FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE NOMENCLATURA=");
		sql.append(idNomenclatura);
		sql.append(" AND TIENE_CONSECUTIVO IS NULL");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para actualizar el orden un campo de una nomenclatura
	 */
	public static String getSQLUpdateOrdenCamposNomenclatura(String orden, String idCampoNomenclatura) {
		StringBuilder sql = new StringBuilder("UPDATE NOMENCLATURAS_CAMPOS_ENTRADA SET ORDEN=");
		sql.append(orden).append(" WHERE ID_NOME_CAMPO=");
		sql.append(idCampoNomenclatura);
		return sql.toString();
	}
}
