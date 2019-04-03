package adminfree.constants;

/**
 * 
 * Clase constante que contiene los DML Y DDL para el modulo de seguridad
 * 
 * @author Carlos Andres Diaz
 *
 */
public class SQLSeguridad {

	/** SQL para obtener los datos del USUARIO cuando inicia sesion */
	public static final String INICIAR_SESION_USER = "SELECT US.ID_USUARIO,US.NOMBRE,US.CARGO,CL.ID_CLIENTE,CL.NOMBRE,GROUP_CONCAT(USER_MODULO.TOKEN_MODULO ORDER BY M.ORDEN SEPARATOR ';') AS T FROM USUARIOS US JOIN CLIENTES CL ON(CL.ID_CLIENTE = US.CLIENTE) JOIN USUARIOS_MODULOS USER_MODULO ON(USER_MODULO.ID_USUARIO = US.ID_USUARIO) JOIN MODULOS M ON(M.TOKEN_MODULO = USER_MODULO.TOKEN_MODULO) WHERE US.USUARIO_INGRESO=? AND US.CLAVE_INGRESO=? AND US.ESTADO=? AND CL.ESTADO=?";
	
	/** SQL para obtener los datos del ADMINISTRADOR cuando inicia sesion */
	public static final String INICIAR_SESION_ADMIN = "SELECT ID_CLIENTE,NOMBRE FROM CLIENTES WHERE TOKEN=? AND USUARIO=? AND ESTADO=?";
}
