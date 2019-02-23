package adminfree.constants;

/**
*
* Clase constante que contiene los DML Y DDL que se utilizan en todos los modulos
*
* @author Carlos Andres Diaz
*/
public class SQLTransversal {

	/** SQL para obtener los usuarios para las listas desplegables */
	public static final String GET_ITEMS_USUARIOS = "SELECT ID_USUARIO,NOMBRE FROM USUARIOS WHERE CLIENTE=?";
}
