package adminfree.g.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import adminfree.e.utilities.CerrarRecursos;
import adminfree.e.utilities.ConstantNumeros;

/**
 * 
 * Clase que contiene los llamados de los procedimientos almacenados del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ProceduresJDBC {

	/** constante para la firma del procedimiento almacenado de ELIMINAR CLIENTE */
	private static final String PR_ELIMINAR_CLIENTE = "{call ADMINFREE.PR_ELIMINAR_CLIENTE(?,?)}";

	/**
	 * metodo que permite ejecutar el procedimiento almacenado para eliminar un
	 * CLIENTE del sistema
	 * 
	 * @param idCliente, identificador del CLIENTE
	 * @return 200 = OK, de lo contrario el mensaje de error de MYSQL
	 */
	public String eliminarCliente(Long idCliente, Connection con) throws Exception {
		CallableStatement callStatement = null;
		try {
			// se estable el procedimiento de eliminar CLIENTE
			callStatement = con.prepareCall(PR_ELIMINAR_CLIENTE);

			// para el procedimiento se necesita el id del cliente
			callStatement.setLong(ConstantNumeros.UNO, idCliente);

			// se registra el tipo de retorno
			callStatement.registerOutParameter(ConstantNumeros.DOS, Types.VARCHAR);

			// se ejecuta el procedimiento
			callStatement.execute();

			// se obtiene el resultado del procedimiento ELIMINAR CLIENTE
			return callStatement.getString(ConstantNumeros.DOS);
		} finally {
			CerrarRecursos.closeStatement(callStatement);
		}
	}
}
