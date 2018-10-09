package adminfree.g.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
	 * Metodo utilitario para las consultas de SELECT con JDBC
	 * 
	 * @param sql, SQL con la consulta configurada
	 * @param valoresWhere, contiene los valores para el whereSentence
	 * @param mapper, identifica que objecto especifico se debe mappear
	 * 
	 * @return registro(s) de acuerdo a la consulta
	 */
	protected Object find(
			String sql, List<Object> valoresWhere, 
			MapperJDBC mapper, Connection con)
			throws Exception {
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(sql);
			
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
	 * Metodo utilitario para los DELETE con JDBC
	 * DELETE DBUSER WHERE USER_ID = ?
	 * 
	 * @param deleteSQL, es el DELETE SQL a ejecutar
	 * @param valoresWhere, contiene los valores para el whereSentence
	 */
	protected void delete(String deleteSQL, List<Object> valoresWhere, Connection con) throws Exception {
		insert(deleteSQL, valoresWhere, con);
	}
	
	/**
	 * Metodo que ejecuta multiples sentencias DML por BATCH de JDBC con Injection
	 * 
	 * @param dml, Es la sentencia DML a ejecutar en el BATCH
	 */
	protected void batchConInjection(String dml, List<List<Object>> injections, Connection con) throws Exception {
		PreparedStatement pst = null;
		con.setAutoCommit(false);
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(dml);
			
			// lleva la cuenta de DML agregados en el BATCH
			int countDML = ConstantNumeros.ZERO;
			
			// se recorre la cantidad de injections agregar en el BATCH
			int posicion;
			for (List<Object> values : injections) {
				
				// se injecta los parametros de esta sentencia y se agrega al BATCH
				posicion = ConstantNumeros.UNO;
				for (Object value : values) {
					setValorNotNull(pst, value, posicion);
					posicion++;
				}
				
				// se agrega el DML al BATCH y se suma a la cuenta
				pst.addBatch();
				countDML++;
				
				// se valida si se debe ejecutar el BATCH
				if (!ConstantNumeros.UNO.equals(countDML) && 
				    (countDML % ConstantSQL.BATCH_SIZE == ConstantNumeros.ZERO)) {
					pst.executeBatch();
				}
			}
			
			// se ejecuta el ultimo bloque y se confirman los cambios
			pst.executeBatch();
			con.commit();
		} finally {
			con.setAutoCommit(true);
			CerrarRecursos.closePreparedStatement(pst);
		}
	}
	
	/**
	 * Metodo que ejecuta multiples sentencias DML por BATCH de JDBC sin Injection
	 * 
	 * @param dmls, lista de sentencias DMLS a ejecutar en el BATCH
	 */
	protected void batchSinInjection(List<String> dmls, Connection con) throws Exception {
		Statement stm = null;
		con.setAutoCommit(false);
		try {
			// se establece el Statement
			stm = con.createStatement();

			// lleva la cuenta de DML agregados en el BATCH
			int countDML = ConstantNumeros.ZERO;

			// se recorre todos los DMLs que deben ser ejecutados por el BATCH
			for (String dml : dmls) {

				// se agrega el DML al BATCH y se suma a la cuenta
				stm.addBatch(dml);
				countDML++;

				// se valida si se debe ejecutar el BATCH
				if (!ConstantNumeros.UNO.equals(countDML) && 
					(countDML % ConstantSQL.BATCH_SIZE == ConstantNumeros.ZERO)) {
					stm.executeBatch();
				}
			}

			// se ejecuta el ultimo bloque y se confirman los cambios
			stm.executeBatch();
			con.commit();
		} finally {
			con.setAutoCommit(true);
			CerrarRecursos.closeStatement(stm);
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
