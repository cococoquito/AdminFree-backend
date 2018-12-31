package adminfree.constants;

/**
 * 
 * Clase constante que contiene los DML Y DDL para el modulo de Configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
public class SQLConfiguraciones {

	/** SQL que se utiliza para obtener el ultimo ID generado de una tabla especifica*/
	public static final String GET_MAX_ID = "SELECT MAX(?1) FROM ?2";

	/** SQL para obtener todos los CLIENTES del sistema */
	public static final String LISTAR_CLIENTES = "SELECT ID_CLIENTE, TOKEN, NOMBRE, TELEFONOS, EMAILS, FECHA_ACTIVACION, FECHA_INACTIVACION, ESTADO, USUARIO FROM CLIENTES";

	/** SQL para la creacion del CLIENTE */
	public static final String CREAR_CLIENTE = "INSERT INTO CLIENTES (TOKEN, NOMBRE, TELEFONOS, EMAILS, ESTADO, FECHA_ACTIVACION, USUARIO) VALUES (?,?,?,?,?,CURDATE(),?)";

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

	/** SQL para obtener el ID del usuario de acuerdo a sus credenciales */
	public static final String GET_ID_USUARIO = "SELECT ID_USUARIO FROM USUARIOS WHERE USUARIO_INGRESO =? AND CLAVE_INGRESO =?";

	/** SQL para insertar los privilegios de un usuario */
	public static final String INSERTAR_PRIVILEGIOS_USER = "INSERT INTO USUARIOS_MODULOS (ID_USUARIO, TOKEN_MODULO) VALUES (?,?)";

	/** SQL para modificar el estado del usuario */
	public static final String UPDATE_ESTADO_USER = "UPDATE USUARIOS SET ESTADO =? WHERE ID_USUARIO =?";

	/** Se utiliza para eliminar los privilegios del usuario */
	public static final String DELETE_PRIVILEGIOS_USER_ = "DELETE FROM USUARIOS_MODULOS WHERE ID_USUARIO =?";

	/** Se utiliza para insertar los privilegios del usuario sin injection */
	public static final String INSERTAR_PRIVILEGIOS_USER_ = "INSERT INTO USUARIOS_MODULOS (ID_USUARIO, TOKEN_MODULO) VALUES (?1,'?2')";

	/** Se utiliza para actualizar la clave de ingreso del Usuario */
	public static final String ACTUALIZAR_CLAVE_INGRESO = "UPDATE USUARIOS SET CLAVE_INGRESO =? WHERE ID_USUARIO =?";

	/** Se utiliza para actualizar la cuenta del usuario */
	public static final String UPDATE_DATOS_CUENTA = "UPDATE USUARIOS SET NOMBRE =?, USUARIO_INGRESO =? WHERE ID_USUARIO =?";

	/** Se utiliza para actualizar el nombre del usuario */
	public static final String UPDATE_NOMBRE_USER = "UPDATE USUARIOS SET NOMBRE =? WHERE ID_USUARIO =?";

	/** Se utiliza para obtener la clave de ingreso */
	public static final String GET_CLAVE_INGRESO = "SELECT CLAVE_INGRESO FROM USUARIOS WHERE ID_USUARIO =?";

	/** Se utiliza para obtener las restricciones para los campos de ingreso */
	public static final String GET_RESTRICCIONES_CAMPO_INGRESO = "SELECT RE.ID_RESTRICCION, RE.DESCRIPCION FROM RESTRICCIONES RE JOIN RESTRICCIONES_TIPO_CAMPO RT ON (RT.RESTRICCION = RE.ID_RESTRICCION) WHERE RT.TIPO_CAMPO =?";

	/** Se utiliza para la creacion de los campos de entrada de informacion */
	public static final String COUNT_EXISTE_CAMPO_ENTRADA = "SELECT COUNT(*) FROM CAMPOS_ENTRADA WHERE TIPO_CAMPO =? AND NOMBRE =? AND CLIENTE =?";

	/** Se utiliza para la inserccion de los campos de entrada de informacion */
	public static final String INSERTAR_CAMPO_ENTRADA = "INSERT INTO CAMPOS_ENTRADA (CLIENTE,TIPO_CAMPO,NOMBRE,DESCRIPCION) VALUES (?,?,?,?)";

	/** Se utiliza para obtener el ID del campo de entrada */
	public static final String GET_ID_CAMPO_ENTRADA = "SELECT ID_CAMPO FROM CAMPOS_ENTRADA WHERE TIPO_CAMPO =? AND NOMBRE =? AND CLIENTE =?";

	/** Se utiliza para registrar las restricciones del campo */
	public static final String INSERTAR_RESTRICCIONES_CAMPO = "INSERT INTO CAMPOS_ENTRADA_RESTRICCIONES (CAMPO,RESTRICCION) VALUES (?1,?2)";

	/** Se utiliza para registrar los items del campo de entrada tipo lista desplegable */
	public static final String INSERTAR_SELECT_ITEMS = "INSERT INTO SELECT_ITEMS (CAMPO,VALOR) VALUES (?1,'?2')";

	/** Se utiliza para actualizar el valor de un item*/
	public static final String UPDATE_SELECT_ITEMS = "UPDATE SELECT_ITEMS SET VALOR = '?1' WHERE ID_ITEM = ?2";

	/** Se utiliza para eliminar un item*/
	public static final String DELETE_SELECT_ITEMS = "DELETE FROM SELECT_ITEMS WHERE ID_ITEM = ?";

	/** Se utiliza para obtener los campos de entrada de informacion asociado a un cliente */
	public static final String GET_CAMPOS_ENTRADA_INFORMACION = "SELECT ID_CAMPO, TIPO_CAMPO, NOMBRE FROM CAMPOS_ENTRADA WHERE CLIENTE =?";

	/** Se utiliza para obtener el detalle del campo de entrada */
	public static final String GET_DETALLE_CAMPO_ENTRADA = "SELECT CE.ID_CAMPO AS ID_CAMPO, CE.CLIENTE AS ID_CLIENTE, CE.NOMBRE AS NOMBRE_CAMPO, CE.DESCRIPCION AS DESCRIPCION_CAMPO, CE.TIPO_CAMPO AS TIPO_CAMPO, R.ID_RESTRICCION AS ID_RES, R.DESCRIPCION AS DESCRIPCION_RES FROM CAMPOS_ENTRADA CE LEFT JOIN CAMPOS_ENTRADA_RESTRICCIONES RES ON (RES.CAMPO = CE.ID_CAMPO) LEFT JOIN RESTRICCIONES R ON (R.ID_RESTRICCION = RES.RESTRICCION) WHERE CE.ID_CAMPO =?";

	/** Se consulta los items de un campo tipo selectitems */
	public static final String GET_ITEMS = "SELECT ID_ITEM, VALOR FROM SELECT_ITEMS WHERE CAMPO =?";

	/** Se utiliza si intentan eliminar un campo y esta tiene asociado una nomenclatura */
	public static final String COUNT_CAMPO_NOMENCLATURA_ASOCIADA = "SELECT COUNT(*) FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE CAMPO =?";

	/** Se utiliza para eliminar las restricciones de un campo de entrada */
	public static final String DELETE_CAMPO_RESTRICCIONES = "DELETE FROM CAMPOS_ENTRADA_RESTRICCIONES WHERE CAMPO =?";

	/** Se utiliza para eliminar los items de un campo de entrada */
	public static final String DELETE_CAMPO_ITEMS = "DELETE FROM SELECT_ITEMS WHERE CAMPO =?";

	/** Se utiliza para eliminar el campo de entrada de informacion */
	public static final String DELETE_CAMPO_ENTRADA = "DELETE FROM CAMPOS_ENTRADA WHERE ID_CAMPO =?";

	/** Se utiliza para obtener el detalle de un campo de entrada para editar */
	public static final String GET_DETALLE_CAMPO_EDITAR = "SELECT CE.ID_CAMPO AS ID_CAMPO, CE.CLIENTE AS ID_CLIENTE, CE.NOMBRE AS NOMBRE_CAMPO, CE.DESCRIPCION AS DESCRIPCION_CAMPO, CE.TIPO_CAMPO AS TIPO_CAMPO, (SELECT COUNT(*) FROM NOMENCLATURAS_CAMPOS_ENTRADA NCE WHERE NCE.CAMPO = CE.ID_CAMPO) AS NOMENCLATURAS, (SELECT COUNT(*) FROM CAMPOS_ENTRADA_RESTRICCIONES RE WHERE RE.CAMPO = CE.ID_CAMPO) AS RESTRICCIONES, (SELECT COUNT(*) FROM NOMENCLATURAS_SECUENCIA SE WHERE SE.NOMENCLATURA IN (SELECT EN.NOMENCLATURA FROM NOMENCLATURAS_CAMPOS_ENTRADA EN WHERE EN.CAMPO = CE.ID_CAMPO)) AS CONSECUTIVOS FROM CAMPOS_ENTRADA CE WHERE CE.ID_CAMPO =?";

	/** Se utiliza para la edicion de las restricciones */
	public static final String GET_RESTRICCIONES_EDICION = "SELECT RESTRICCION FROM CAMPOS_ENTRADA_RESTRICCIONES WHERE CAMPO =?";

	/** DML para actualizar la descripcion y el nombre del campo de entrada */
	public static final String UPDATE_CAMPO_DESCRIPCION_NOMBRE = "UPDATE CAMPOS_ENTRADA SET DESCRIPCION ='?1', NOMBRE ='?2' WHERE ID_CAMPO =?3";

	/** Se utiliza para obtener todas las nomenclaturas asociadas a un cliente */
	public static final String GET_NOMENCLATURAS = "SELECT ID_NOMENCLATURA, NOMENCLATURA, DESCRIPCION, CONSECUTIVO_INICIAL FROM NOMENCLATURAS WHERE CLIENTE =?";

	/** Se utiliza para crear la nomenclatura */
	public static final String INSERT_NOMENCLATURA = "INSERT INTO NOMENCLATURAS (NOMENCLATURA, DESCRIPCION, CLIENTE, CONSECUTIVO_INICIAL) VALUES (?,?,?,?)";

	/** Se utiliza para insertar los campos asociados a una nomenclatura */
	public static final String INSERT_NOMENCLATURA_CAMPOS = "INSERT INTO NOMENCLATURAS_CAMPOS_ENTRADA (NOMENCLATURA, CAMPO) VALUES (?1,?2)";

	/** Se utiliza para actualizar los datos basicos de la nomenclatura */
	public static final String UPDATE_NOMENCLATURA = "UPDATE NOMENCLATURAS SET NOMENCLATURA=?, DESCRIPCION=?, CONSECUTIVO_INICIAL=? WHERE ID_NOMENCLATURA=?";

	/** Se utiliza para actualizar una nomenclatura campo */
	public static final String UPDATE_NOMENCLAURA_CAMPO = "UPDATE NOMENCLATURAS_CAMPOS_ENTRADA SET CAMPO=?1 WHERE ID_NOME_CAMPO=?2";

	/** Se utiliza para eliminar una nomenclatura campo */
	public static final String DELETE_NOMENCLAURA_CAMPO = "DELETE FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE ID_NOME_CAMPO=?";

	/** Se utiliza para identificar si existe la nomenclatura asociada a un cliente */
	public static final String EXISTE_NOMENCLATURA = "SELECT COUNT(*) FROM NOMENCLATURAS WHERE NOMENCLATURA=? AND CLIENTE=?";

	/** Se utiliza para validar si existe consecutivos asociados a una nomenclatura */
	public static final String COUNT_CONSECUTIVOS_NOMENCLATURA = "SELECT COUNT(*) FROM NOMENCLATURAS_SECUENCIA WHERE NOMENCLATURA=?";

	/** Se utiliza para eliminar los campos asociados a la nomenclatura */
	public static final String DELETE_NOMENCLATURA_CAMPOS = "DELETE FROM NOMENCLATURAS_CAMPOS_ENTRADA WHERE NOMENCLATURA=?";

	/** Se utiliza para eliminar la nomenclatura */
	public static final String DELETE_NOMENCLATURA = "DELETE FROM NOMENCLATURAS WHERE ID_NOMENCLATURA=?";
}
