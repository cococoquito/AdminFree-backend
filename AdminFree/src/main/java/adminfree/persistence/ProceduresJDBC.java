package adminfree.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.enums.Numero;
import adminfree.utilities.CerrarRecursos;

/**
 * 
 * Clase que contiene los llamados de los procedimientos almacenados del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ProceduresJDBC {

	/** constante para la firma del procedimiento almacenado de CREAR CLIENTE */
	private static final String PR_CREAR_CLIENTE = "{call ADMINFREE.PR_CREAR_CLIENTE(?,?,?,?,?,?)}";

	/** constante para la firma del procedimiento almacenado de ELIMINAR CLIENTE */
	private static final String PR_ELIMINAR_CLIENTE = "{call ADMINFREE.PR_ELIMINAR_CLIENTE(?,?)}";

	/**
	 * Metodo que permite ejecutar el procedimiento para la creacion del cliente
	 *
	 * @param cliente, DTO con los datos del cliente a crear
	 * @param token, token que identifica el cliente ante el sistema
	 * @return OK, de lo contrario el mensaje de error de MYSQL
	 */
	public String crearCliente(ClienteDTO cliente, String token, Connection con) throws Exception {
		CallableStatement callStatement = null;
		try {
			// se estable el procedimiento de crear CLIENTE
			callStatement = con.prepareCall(PR_CREAR_CLIENTE);

			// parametro TOKEN
			callStatement.setString(Numero.UNO.valueI, token);

			// parametro NOMBRE
			callStatement.setString(Numero.DOS.valueI, cliente.getNombre());

			// parametro TELEFONO
			callStatement.setString(Numero.TRES.valueI, cliente.getTelefonos());

			// parametro EMAIL
			callStatement.setString(Numero.CUATRO.valueI, cliente.getEmails());

			// parametro USER
			callStatement.setString(Numero.CINCO.valueI, cliente.getCredenciales().getUsuario());

			// se registra el tipo de retorno
			callStatement.registerOutParameter(Numero.SEIS.valueI, Types.VARCHAR);

			// se ejecuta el procedimiento
			callStatement.execute();

			// se obtiene el resultado del procedimiento CREAR CLIENTE
			return callStatement.getString(Numero.SEIS.valueI);
		} finally {
			CerrarRecursos.closeStatement(callStatement);
		}
	}

	/**
	 * Metodo que permite ejecutar el procedimiento almacenado para eliminar un
	 * CLIENTE del sistema
	 *
	 * @param idCliente, identificador del CLIENTE
	 * @return OK, de lo contrario el mensaje de error de MYSQL
	 */
	public String eliminarCliente(Long idCliente, Connection con) throws Exception {
		CallableStatement callStatement = null;
		try {
			// se estable el procedimiento de eliminar CLIENTE
			callStatement = con.prepareCall(PR_ELIMINAR_CLIENTE);

			// para el procedimiento se necesita el id del cliente
			callStatement.setLong(Numero.UNO.valueI, idCliente);

			// se registra el tipo de retorno
			callStatement.registerOutParameter(Numero.DOS.valueI, Types.VARCHAR);

			// se ejecuta el procedimiento
			callStatement.execute();

			// se obtiene el resultado del procedimiento ELIMINAR CLIENTE
			return callStatement.getString(Numero.DOS.valueI);
		} finally {
			CerrarRecursos.closeStatement(callStatement);
		}
	}
}
