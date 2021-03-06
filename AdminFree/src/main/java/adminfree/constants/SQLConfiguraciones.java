package adminfree.constants;

/**
 * Clase constante que contiene los DML Y DDL para el modulo de Configuraciones
 *
 * @author Carlos Andres Diaz
 *
 */
public class SQLConfiguraciones {

	/** SQL para obtener todos los CLIENTES del sistema */
	public static final String LISTAR_CLIENTES = "SELECT ID_CLIENTE,NOMBRE,TELEFONOS,EMAILS,FECHA_ACTIVACION,FECHA_INACTIVACION,ESTADO,USUARIO FROM CLIENTES";

	/** SQL para obtener un CLIENTE con base al TOKEN */
	public static final String GET_CLIENTE_TOKEN = "SELECT ID_CLIENTE,NOMBRE,TELEFONOS,EMAILS,FECHA_ACTIVACION,ESTADO,USUARIO FROM CLIENTES WHERE TOKEN=?";

	/** SQL para verificar si existe un cliente con un TOKEN especifico */
	public static final String NOT_EXISTS_TOKEN_CLIENT = "SELECT NOT EXISTS(SELECT * FROM CLIENTES WHERE TOKEN=?)";

	/** SQL para ACTUALIZAR los datos del CLIENTE */
	public static final String ACTUALIZAR_CLIENTE = "UPDATE CLIENTES SET NOMBRE=?,EMAILS=?,TELEFONOS=? WHERE ID_CLIENTE=?";

	/** SQL para ACTIVAR un CLIENTE */
	public static final String ACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=NULL, FECHA_ACTIVACION = CURDATE() WHERE ID_CLIENTE=?";

	/** SQL para INACTIVAR un CLIENTE */
	public static final String INACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=CURDATE() WHERE ID_CLIENTE=?";

	/** SQL para obtener todos los USUARIOS ACTIVO/INACTIVO del sistema asociados a un CLIENTE */
	public static final String GET_USUARIOS_CLIENTE = "SELECT US.ID_USUARIO AS ID_USER, US.NOMBRE AS NOMBRE_USER, US.USUARIO_INGRESO AS INGRESO_USER, US.ESTADO AS ESTADO_USER, GROUP_CONCAT(USER_MODULO.TOKEN_MODULO SEPARATOR ';') AS MODULOS, US.CARGO FROM USUARIOS US LEFT JOIN USUARIOS_MODULOS USER_MODULO ON(USER_MODULO.ID_USUARIO = US.ID_USUARIO) WHERE US.CLIENTE =? AND (US.ESTADO =? OR US.ESTADO =?) GROUP BY US.ID_USUARIO, US.NOMBRE, US.USUARIO_INGRESO, US.ESTADO ORDER BY US.NOMBRE";

	/** SQL para validar si existe el USUARIO_INGRESO en otro usuario */
	public static final String EXISTS_USUARIO_INGRESO = "SELECT EXISTS(SELECT * FROM USUARIOS WHERE USUARIO_INGRESO=?)";

	/** SQL para la creacion del USUARIO */
	public static final String CREAR_USUARIO = "INSERT INTO USUARIOS (NOMBRE, USUARIO_INGRESO, CLAVE_INGRESO, CLIENTE, ESTADO, CARGO) VALUES (?,?,?,?,?,?)";

	/** SQL para insertar los privilegios de un usuario */
	public static final String INSERTAR_PRIVILEGIOS_USER = "INSERT INTO USUARIOS_MODULOS(ID_USUARIO,TOKEN_MODULO)VALUES(?,?)";

	/** SQL para modificar el estado del usuario */
	public static final String UPDATE_ESTADO_USER = "UPDATE USUARIOS SET ESTADO=? WHERE ID_USUARIO=?";

	/** Se utiliza para actualizar la clave de ingreso del USUARIO */
	public static final String ACTUALIZAR_TOKEN_USUARIO = "UPDATE USUARIOS SET CLAVE_INGRESO=? WHERE ID_USUARIO=?";

	/** Se utiliza para actualizar el user de ingreso del USUARIO */
	public static final String ACTUALIZAR_USUARIO_INGRESO = "UPDATE USUARIOS SET USUARIO_INGRESO=? WHERE ID_USUARIO=?";

	/** Se utiliza para actualizar la clave de ingreso del CLIENTE */
	public static final String ACTUALIZAR_TOKEN_CLIENTE = "UPDATE CLIENTES SET TOKEN=? WHERE ID_CLIENTE=?";

	/** Se utiliza para actualizar la cuenta del usuario */
	public static final String UPDATE_DATOS_CUENTA = "UPDATE USUARIOS SET NOMBRE=?,USUARIO_INGRESO=?,CARGO=? WHERE ID_USUARIO=?";

	/** Se utiliza para actualizar los datos personales del Usuario */
	public static final String UPDATE_DATOS_PERSONALES = "UPDATE USUARIOS SET NOMBRE=?,CARGO=? WHERE ID_USUARIO=?";

	/** Se utiliza para obtener la clave de ingreso */
	public static final String GET_CLAVE_INGRESO = "SELECT CLAVE_INGRESO FROM USUARIOS WHERE ID_USUARIO=?";

	/** Se utiliza para la inserccion de los campos de entrada de informacion */
	public static final String INSERTAR_CAMPO_ENTRADA = "INSERT INTO CAMPOS_ENTRADA (CLIENTE,TIPO_CAMPO,NOMBRE,DESCRIPCION) VALUES (?,?,?,?)";

	/** Se utiliza para obtener los campos de entrada de informacion asociado a un cliente */
	public static final String GET_CAMPOS_ENTRADA_INFORMACION = "SELECT ID_CAMPO,TIPO_CAMPO,NOMBRE FROM CAMPOS_ENTRADA WHERE CLIENTE=? ORDER BY NOMBRE";

	/** Se utiliza para obtener el detalle del campo de entrada asociado a una nomenclatura */
	public static final String GET_DETALLE_NOMENCLATURA_CAMPO = "SELECT CE.ID_CAMPO,CE.NOMBRE,CE.DESCRIPCION,CE.TIPO_CAMPO,R.ID_RESTRICCION,R.DESCRIPCION FROM CAMPOS_ENTRADA CE JOIN NOMENCLATURAS_CAMPOS_ENTRADA NCE ON(NCE.CAMPO=CE.ID_CAMPO) LEFT JOIN NOMENCLATURAS_CAMPOS_RESTRICS NRE ON(NRE.ID_NOME_CAMPO=NCE.ID_NOME_CAMPO) LEFT JOIN RESTRICCIONES R ON (R.ID_RESTRICCION = NRE.RESTRICCION) WHERE NCE.NOMENCLATURA=? AND NCE.CAMPO=?";

	/** Se consulta los items de un campo tipo selectitems */
	public static final String GET_ITEMS = "SELECT ID_ITEM,VALOR FROM SELECT_ITEMS WHERE CAMPO=? ORDER BY VALOR ASC";

	/** Se utiliza si intentan eliminar un campo y esta tiene asociado una nomenclatura */
	public static final String EXISTS_CAMPO_NOMENCLATURA_ASOCIADA = "SELECT EXISTS(SELECT * FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE CAMPO=?)";

	/** Se utiliza para obtener el detalle de un campo de entrada para editar */
	public static final String GET_DETALLE_CAMPO_EDITAR = "SELECT CE.ID_CAMPO,CE.CLIENTE,CE.NOMBRE,CE.DESCRIPCION,CE.TIPO_CAMPO,(SELECT EXISTS(SELECT * FROM NOMENCLATURAS_CAMPOS_ENTRADA NCE WHERE NCE.CAMPO=CE.ID_CAMPO AND NCE.TIENE_CONSECUTIVO IS NULL))AS NOMENCLATURAS,(SELECT EXISTS(SELECT * FROM NOMENCLATURAS_CAMPOS_ENTRADA NCC WHERE NCC.CAMPO=CE.ID_CAMPO AND NCC.TIENE_CONSECUTIVO IS NOT NULL))AS CONSECUTIVOS FROM CAMPOS_ENTRADA CE WHERE CE.ID_CAMPO=?";

	/** DML para actualizar la descripcion y el nombre del campo de entrada */
	public static final String UPDATE_CAMPO_DESCRIPCION_NOMBRE = "UPDATE CAMPOS_ENTRADA SET DESCRIPCION=?,NOMBRE=? WHERE ID_CAMPO=?";

	/** Se utiliza para obtener todas las nomenclaturas asociadas a un cliente */
	public static final String GET_NOMENCLATURAS = "SELECT ID_NOMENCLATURA,NOMENCLATURA,DESCRIPCION,CONSECUTIVO_INICIAL,CONSECUTIVOS_SOLICITADOS FROM NOMENCLATURAS WHERE CLIENTE=? ORDER BY NOMENCLATURA ASC";

	/** Se utiliza para crear la nomenclatura */
	public static final String INSERT_NOMENCLATURA = "INSERT INTO NOMENCLATURAS (NOMENCLATURA,DESCRIPCION,CLIENTE,CONSECUTIVO_INICIAL) VALUES (?,?,?,?)";

	/** Se utiliza para actualizar los datos basicos de la nomenclatura */
	public static final String UPDATE_NOMENCLATURA = "UPDATE NOMENCLATURAS SET NOMENCLATURA=?,DESCRIPCION=?,CONSECUTIVO_INICIAL=? WHERE ID_NOMENCLATURA=?";

	/** Se utiliza para validar si existe consecutivos asociados a una nomenclatura */
	public static final String GET_SECUENCIA_NOMENCLATURA = "SELECT SECUENCIA FROM NOMENCLATURAS WHERE ID_NOMENCLATURA=?";

	/** Se utiliza para obtener el detalle de la nomenclatura */
	public static final String GET_DETALLE_NOMENCLATURA = "SELECT NOM.ID_NOMENCLATURA,NOM.NOMENCLATURA,NOM.DESCRIPCION,NOM.CONSECUTIVO_INICIAL,NOM.SECUENCIA,NOM.CONSECUTIVOS_SOLICITADOS,NOC.ID_NOME_CAMPO,CP.ID_CAMPO,CP.NOMBRE,CP.TIPO_CAMPO,NOC.TIENE_CONSECUTIVO,NOC.ORDEN FROM NOMENCLATURAS NOM LEFT JOIN NOMENCLATURAS_CAMPOS_ENTRADA NOC ON (NOM.ID_NOMENCLATURA = NOC.NOMENCLATURA) LEFT JOIN CAMPOS_ENTRADA CP ON (CP.ID_CAMPO = NOC.CAMPO) WHERE NOM.ID_NOMENCLATURA=? ORDER BY NOC.ORDEN";

	/** Se utiliza para obtener las restricciones parametrizadas */
	public static final String GET_RESTRICCIONES = "SELECT RE.ID_RESTRICCION,RE.DESCRIPCION,RT.COMPATIBLE,RT.TIPO_CAMPO FROM RESTRICCIONES_TIPO_CAMPO RT JOIN RESTRICCIONES RE ON(RE.ID_RESTRICCION = RT.RESTRICCION) ORDER BY RT.TIPO_CAMPO,RE.ID_RESTRICCION";

	/** Se utiliza para insertar los campos asociados a una nomenclatura */
	public static final String INSERT_NOMENCLATURA_CAMPOS = "INSERT INTO NOMENCLATURAS_CAMPOS_ENTRADA(NOMENCLATURA,CAMPO,ORDEN)VALUES(?,?,?)";

	/** SQL para obtener el detalle de la nomenclatura a editar */
	public static final String GET_DETALLE_NOMENCLATURA_EDITAR = "SELECT NOM.NOMENCLATURA,NOM.DESCRIPCION,NOM.CONSECUTIVO_INICIAL,NOM.CONSECUTIVOS_SOLICITADOS,NCE.ID_NOME_CAMPO,NCE.TIENE_CONSECUTIVO,NCE.CAMPO,NCE.ORDEN,GROUP_CONCAT(CER.RESTRICCION SEPARATOR ';') AS RESTRICCIONES FROM NOMENCLATURAS NOM LEFT JOIN NOMENCLATURAS_CAMPOS_ENTRADA NCE ON(NOM.ID_NOMENCLATURA = NCE.NOMENCLATURA)LEFT JOIN NOMENCLATURAS_CAMPOS_RESTRICS CER ON(CER.ID_NOME_CAMPO = NCE.ID_NOME_CAMPO)WHERE NOM.ID_NOMENCLATURA=? GROUP BY NCE.ID_NOME_CAMPO ORDER BY NCE.ORDEN";

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
	 * Metodo que construye el SQL para insertar los items para un campo lista desplegable
	 */
	public static String getSQLInsertSelectItems(String idCampo, String valor) {
		StringBuilder sql = new StringBuilder("INSERT INTO SELECT_ITEMS (CAMPO,VALOR) VALUES (");
		sql.append(idCampo).append(",'");
		sql.append(valor).append("')");
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
	 * Metodo que construye el SQL para eliminar las restricciones de los campos asociados nomenclatura
	 */
	public static String getSQLDeleteRestricciones(String idNomenclatura) {
		StringBuilder sql = new StringBuilder("DELETE FROM NOMENCLATURAS_CAMPOS_RESTRICS WHERE ID_NOME_CAMPO");
		sql.append(" IN(SELECT N.ID_NOME_CAMPO FROM NOMENCLATURAS_CAMPOS_ENTRADA N WHERE N.NOMENCLATURA=");
		sql.append(idNomenclatura).append(")");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para eliminar los campos asociados a una nomenclatura
	 */
	public static String getSQLDeleteCamposNomenclatura(String idNomenclatura, boolean isNull) {
		StringBuilder sql = new StringBuilder("DELETE FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE NOMENCLATURA=");
		sql.append(idNomenclatura);
		if (isNull) {
			sql.append(" AND TIENE_CONSECUTIVO IS NULL");
		}
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para actualizar el orden un campo de una nomenclatura
	 */
	public static String getSQLUpdateOrdenCampos(String orden, String idCampoNomenclatura) {
		StringBuilder sql = new StringBuilder("UPDATE NOMENCLATURAS_CAMPOS_ENTRADA SET ORDEN=");
		sql.append(orden).append(" WHERE ID_NOME_CAMPO=");
		sql.append(idCampoNomenclatura);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para eliminar una nomenclatura
	 */
	public static String getSQLDeleteNomenclatura(String idNomenclatura) {
		StringBuilder sql = new StringBuilder("DELETE FROM NOMENCLATURAS WHERE ID_NOMENCLATURA=");
		sql.append(idNomenclatura);
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para el insert de la tabla NOMENCLATURAS_CAMPOS_RESTRICS
	 */
	public static String getSQLInsertRestriccion(String idNomenclaturaCampo, Integer idRestriccion) {
		StringBuilder sql = new StringBuilder("INSERT INTO NOMENCLATURAS_CAMPOS_RESTRICS(ID_NOME_CAMPO,RESTRICCION)VALUES(");
		sql.append(idNomenclaturaCampo).append(",").append(idRestriccion).append(")");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para validar si un campo de entrada ya existe con su nombre y tipo
	 */
	public static String isExistsCampoEntrada(Integer tipoCampo, Long idCliente, Long idCampo) {
		StringBuilder sql = new StringBuilder("SELECT EXISTS(SELECT * FROM CAMPOS_ENTRADA WHERE TIPO_CAMPO=");
		sql.append(tipoCampo).append(" AND NOMBRE=? AND CLIENTE=").append(idCliente);
		if (idCampo != null) {
			sql.append(" AND ID_CAMPO<>").append(idCampo);
		}
		sql.append(")");
		return sql.toString();
	}

	/**
	 * Metodo que construye el SQL para validar si una nomenclatura ya existe
	 */
	public static String isExistsNomenclatura(Long idCliente, Long idNomenclatura) {
		StringBuilder sql = new StringBuilder("SELECT EXISTS(SELECT * FROM NOMENCLATURAS WHERE NOMENCLATURA=? AND CLIENTE=");
		sql.append(idCliente);
		if (idNomenclatura != null) {
			sql.append(" AND ID_NOMENCLATURA<>").append(idNomenclatura);
		}
		sql.append(")");
		return sql.toString();
	}
}
