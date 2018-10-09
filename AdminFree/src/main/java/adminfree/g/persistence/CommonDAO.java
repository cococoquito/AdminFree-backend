package adminfree.g.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import adminfree.e.utilities.CerrarRecursos;
import adminfree.e.utilities.ConstantNumeros;

/**
 * 
 * Clase que contiene las funciones comunes para los procesos de JDBC
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CommonDAO {

	/**
	 * Metodo utilitario para los INSERTS con JDBC
	 * 
	 * @param insertSQL, es el INSERT SQL a ejecutar
	 * @param valores, valores a insertar en la tabla
	 * @param con, conexión activa de la base datos
	 */
	protected void insert(String insertSQL, List<Object> valores, Connection con) throws Exception {
		PreparedStatement pst = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(insertSQL);

			// se recorre cada valor para configurarlo en el PreparedStatement
			int posicion = ConstantNumeros.UNO;
			for (Object valor : valores) {
				setValorNotNull(pst, valor, posicion);
				posicion++;
			}

			// se ejecuta la inserción
			pst.executeUpdate();
		} finally {
			CerrarRecursos.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo utilitario para los UPDATES con JDBC
	 * 
	 * @param updateSQL, es el UPDATE SQL a ejecutar
	 * @param valores, valores a insertar en la tabla
	 * @param con, conexión activa de la base datos
	 */
	protected void update(String updateSQL, List<ValueSQL> valores, Connection con) throws Exception {
		PreparedStatement pst = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(updateSQL);

			// se recorre cada valor para configurarlo en el PreparedStatement
			int posicion = ConstantNumeros.UNO;
			Object valor;
			for (ValueSQL valueSQL : valores) {
				valor = valueSQL.getValor();

				// se valida si se debe configurar NULL en el PreparedStatement
				if (valor != null) {
					setValorNotNull(pst, valor, posicion);
				} else {
					pst.setNull(posicion, valueSQL.getTipoDato());
				}
				posicion++;
			}

			// se ejecuta la inserción
			pst.executeUpdate();
		} finally {
			CerrarRecursos.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo utilitario para los consultas de registros con JDBC
	 * 
	 * @param listSQL, SQL con la consulta configurada
	 * @param valoresWhere, contiene los valores del whereSentence
	 * @param mapper, identifica que objecto especifico se debe mappear
	 * @param con, conexión activa de la base datos
	 * @return lista de registros de acuerdo a la consulta
	 */
	protected Object list(
			String listSQL, List<Object> valoresWhere, 
			MapperJDBC mapper, Connection con)
			throws Exception {
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(listSQL);
			
			// se configura los parametros para el wheresentence
			if (valoresWhere != null && !valoresWhere.isEmpty()) {
				int posicion = ConstantNumeros.UNO;
				for (Object valor : valoresWhere) {
					setValorNotNull(pst, valor, posicion);
					posicion++;
				}
			}
			
			// se ejecuta la consulta y se encapsula el resultado
			res = pst.executeQuery();
			
			// se configura el resultado en el mapper especifico
			return mapper.execute(res);
		} finally {
			CerrarRecursos.closeResultSet(res);
			CerrarRecursos.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo que permite settear un valor not null al PreparedStatement
	 */
	private void setValorNotNull(PreparedStatement pst, Object valor, int posicion) throws Exception {
		if (valor instanceof String) {
			pst.setString(posicion, (String) valor);
		} else if (valor instanceof Integer) {
			pst.setInt(posicion, (Integer) valor);
		} else if (valor instanceof Long) {
			pst.setLong(posicion, (Long) valor);
		} else if (valor instanceof Date) {
			pst.setTimestamp(posicion, new java.sql.Timestamp(((Date) valor).getTime()));
		}
	}
}
