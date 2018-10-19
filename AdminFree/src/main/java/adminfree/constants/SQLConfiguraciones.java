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
	public static final String LISTAR_CLIENTES = "SELECT ID_CLIENTE, TOKEN, NOMBRE, TELEFONOS, EMAILS, FECHA_ACTIVACION, FECHA_INACTIVACION, ESTADO FROM CLIENTES";

	/** SQL para la creacion del CLIENTE */
	public static final String CREAR_CLIENTE = "INSERT INTO CLIENTES (TOKEN, NOMBRE, TELEFONOS, EMAILS, ESTADO, FECHA_ACTIVACION) VALUES (?, ?, ?, ?, ?, CURDATE())";

	/** SQL para obtener un CLIENTE con base al TOKEN */
	public static final String GET_CLIENTE_TOKEN = "SELECT ID_CLIENTE, TOKEN, NOMBRE, TELEFONOS, EMAILS, FECHA_ACTIVACION, ESTADO FROM CLIENTES WHERE TOKEN = ?";

	/** SQL para contar los clientes que contenga un TOKEN especifico */
	public static final String COUNT_CLIENTE_TOKEN = "SELECT COUNT(*) FROM CLIENTES WHERE TOKEN = ?";

	/** SQL para ACTUALIZAR los datos del CLIENTE */
	public static final String ACTUALIZAR_CLIENTE = "UPDATE CLIENTES SET NOMBRE=?, EMAILS=?, TELEFONOS=? WHERE ID_CLIENTE=?";

	/** SQL para ACTIVAR un CLIENTE */
	public static final String ACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=NULL, FECHA_ACTIVACION = CURDATE() WHERE ID_CLIENTE=?";
	
	/** SQL para INACTIVAR un CLIENTE */
	public static final String INACTIVAR_CLIENTE = "UPDATE CLIENTES SET ESTADO=?, FECHA_INACTIVACION=CURDATE() WHERE ID_CLIENTE=?";
}