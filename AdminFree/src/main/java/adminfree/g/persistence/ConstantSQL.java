package adminfree.g.persistence;

/**
 * 
 * Clase que contiene las constantes para las sentencias DML, DDL
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ConstantSQL {

	/** Codigo que identifica que algun proceso se ejecuto exitosamente */
	public static final String SUCCESSFUL = "200";

	/** Es el tamanio maximo a ejecutar el BATCH de JDBC */
	public static final int BATCH_SIZE = 1000;
}
