package adminfree.constants;

/**
 * 
 * Clase constante que contiene los DML Y DDL para el modulo de Configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
public class SQLConfiguraciones {

	/** SQL para obtener todos los CLIENTES del sistema */
	public static final String LISTAR_CLIENTES = "SELECT ID_CLIENTE, TOKEN, NOMBRE, TELEFONOS, EMAILS, FECHA_ACTIVACION, FECHA_INACTIVACION, ESTADO, USUARIO FROM CLIENTES";

	/** SQL para la creacion del CLIENTE */
	public static final String CREAR_CLIENTE = "INSERT INTO CLIENTES (TOKEN, NOMBRE, TELEFONOS, EMAILS, ESTADO, FECHA_ACTIVACION, USUARIO) VALUES (?, ?, ?, ?, ?, CURDATE(), ?)";

	/** SQL para obtener un CLIENTE con base al TOKEN */
	public static final String GET_CLIENTE_TOKEN = "SELECT ID_CLIENTE, TOKEN, NOMBRE, TELEFONOS, EMAILS, FECHA_ACTIVACION, ESTADO, USUARIO FROM CLIENTES WHERE TOKEN = ?";

	/** SQL para contar los clientes que contenga un TOKEN especifico */
	public static final String COUNT_CLIENTE_TOKEN = "SELECT COUNT(*) FROM CLIENTES WHERE TOKEN = ?";

	/** SQL para ACTUALIZAR los datos del CLIENTE */
	public static final String ACTUALIZAR_CLIENTE = "UPDATE CLIENTES SET NOMBRE=?, EMAILS=?, TELEFONOS=? WHERE ID_CLIENTE=?";

	/** SQL para ACTIVAR un CLIENTE */
	public static final String ACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=NULL, FECHA_ACTIVACION = CURDATE() WHERE ID_CLIENTE=?";

	/** SQL para INACTIVAR un CLIENTE */
	public static final String INACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=CURDATE() WHERE ID_CLIENTE=?";

	/** SQL para obtener todos los USUARIOS ACTIVO/INACTIVO del sistema asociados a un CLIENTE */
	public static final String GET_USUARIOS_CLIENTE = "SELECT US.ID_USUARIO AS ID_USER, US.NOMBRE AS NOMBRE_USER, US.USUARIO_INGRESO AS INGRESO_USER, US.ESTADO AS ESTADO_USER, GROUP_CONCAT(USER_MODULO.TOKEN_MODULO SEPARATOR ';') AS MODULOS FROM USUARIOS US LEFT JOIN USUARIOS_MODULOS USER_MODULO ON(USER_MODULO.ID_USUARIO = US.ID_USUARIO) WHERE US.CLIENTE = ? AND (US.ESTADO = ? OR US.ESTADO = ?) GROUP BY US.ID_USUARIO, US.NOMBRE, US.USUARIO_INGRESO, US.ESTADO ORDER BY US.NOMBRE";

	/** SQL para contar los USUARIO que contenga USUARIO_INGRESO especifico */
	public static final String COUNT_USUARIO_INGRESO = "SELECT COUNT(*) FROM USUARIOS WHERE USUARIO_INGRESO = ?";

	/** SQL para la creacion del USUARIO */
	public static final String CREAR_USUARIO = "INSERT INTO USUARIOS (NOMBRE, USUARIO_INGRESO, CLAVE_INGRESO, CLIENTE, ESTADO) VALUES (?, ?, ?, ?, ?)";

	/** SQL para obtener el ID del usuario de acuerdo a sus credenciales */
	public static final String GET_ID_USUARIO = "SELECT ID_USUARIO FROM USUARIOS WHERE USUARIO_INGRESO = ? AND CLAVE_INGRESO = ?";

	/** SQL para insertar los privilegios de un usuario */
	public static final String INSERTAR_PRIVILEGIOS_USER = "INSERT INTO USUARIOS_MODULOS (ID_USUARIO, TOKEN_MODULO) VALUES (?, ?)";

	/** SQL para modificar el estado del usuario */
	public static final String UPDATE_ESTADO_USER = "UPDATE USUARIOS SET ESTADO = ? WHERE ID_USUARIO = ?";

	/** Se utiliza para eliminar los privilegios del usuario */
	public static final String DELETE_PRIVILEGIOS_USER_ = "DELETE FROM USUARIOS_MODULOS WHERE ID_USUARIO = ?";

	/** Se utiliza para insertar los privilegios del usuario sin injection */
	public static final String INSERTAR_PRIVILEGIOS_USER_ = "INSERT INTO USUARIOS_MODULOS (ID_USUARIO, TOKEN_MODULO) VALUES (?1, '?2')";

	/** Se utiliza para actualizar la clave de ingreso del Usuario */
	public static final String ACTUALIZAR_CLAVE_INGRESO = "UPDATE USUARIOS SET CLAVE_INGRESO = ? WHERE ID_USUARIO = ?";

	/** Se utiliza para actualizar la cuenta del usuario */
	public static final String UPDATE_DATOS_CUENTA = "UPDATE USUARIOS SET NOMBRE = ?, USUARIO_INGRESO = ? WHERE ID_USUARIO = ?";	
}
