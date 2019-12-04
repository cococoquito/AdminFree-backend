package adminfree.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import adminfree.enums.Numero;
import adminfree.mappers.Mapper;
import adminfree.utilities.CerrarRecursos;

/**
 * 
 * Clase que contiene las funciones comunes para los procesos de JDBC
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CommonDAO {

	/**
	 * Metodo utilitario para los UPDATES o INSERT con JDBC
	 * 
	 * @param dml, es la sentencia DML a ejecutar
	 * @param valores, valores a insertar o actualizar en la tabla
	 */
	protected int insertUpdate(Connection con, String dml, ValueSQL... valores) throws Exception {
		PreparedStatement pst = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(dml);

			// se recorre cada valor para configurarlo en el PreparedStatement
			int posicion = Numero.UNO.valueI.intValue();
			for (ValueSQL valueSQL : valores) {

				// se valida si se debe configurar NULL en el PreparedStatement
				if (valueSQL.getValor() != null) {
					setValorNotNull(pst, valueSQL, posicion);
				} else {
					pst.setNull(posicion, valueSQL.getTipoDato());
				}
				posicion++;
			}

			// se ejecuta la inserción
			return pst.executeUpdate();
		} finally {
			CerrarRecursos.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo utilitario para las consultas de SELECT con JDBC
	 * 
	 * @param sql, SQL con la consulta configurada
	 * @param mapper, identifica que objecto especifico se debe mappear
	 * @param parametros que necesita el mapper para ser procesado
	 * @param where, contiene los valores para el whereSentence
	 * 
	 * @return registro(s) de acuerdo a la consulta
	 */
	protected Object findParams(Connection con, String sql, Mapper mapper, Object parametros, ValueSQL... where) throws Exception {
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(sql);

			// se configura los parametros para el wheresentence
			if (where != null && where.length > Numero.ZERO.valueI.intValue()) {
				int posicion = Numero.UNO.valueI.intValue();
				for (ValueSQL valor : where) {
					setValorNotNull(pst, valor, posicion);
					posicion++;
				}
			}

			// se ejecuta la consulta y se encapsula el resultado
			res = pst.executeQuery();

			// se configura el resultado en el mapper especifico
			return mapper.executeParams(res, parametros);
		} finally {
			CerrarRecursos.closeResultSet(res);
			CerrarRecursos.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo utilitario para las consultas de SELECT con JDBC
	 * 
	 * @param sql, SQL con la consulta configurada
	 * @param mapper, identifica que objecto especifico se debe mappear
	 * @param where, contiene los valores para el whereSentence
	 * 
	 * @return registro(s) de acuerdo a la consulta
	 */
	protected Object find(Connection con, String sql, Mapper mapper, ValueSQL... where) throws Exception {
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(sql);

			// se configura los parametros para el wheresentence
			if (where != null && where.length > Numero.ZERO.valueI.intValue()) {
				int posicion = Numero.UNO.valueI.intValue();
				for (ValueSQL valor : where) {
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
	 * Metodo utilitario para las consultas de SELECT sin WHERE sentence
	 * 
	 * @param sql, SQL con la consulta configurada
	 * @param mapper, identifica que objecto especifico se debe mappear
	 * 
	 * @return registro(s) de acuerdo a la consulta
	 */
	protected Object findAll(Connection con, String sql, Mapper mapper) throws Exception {
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// se establece el PreparedStatement y el ResulSet
			pst = con.prepareStatement(sql);
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
	 * @param where, contiene los valores para el whereSentence
	 */
	protected void delete(Connection con, String deleteSQL, ValueSQL... where) throws Exception {
		PreparedStatement pst = null;
		try {
			// el where sentence es obligatorio
			if (where != null && where.length > Numero.ZERO.valueI.intValue()) {

				// se establece el PreparedStatement
				pst = con.prepareStatement(deleteSQL);

				// se recorre cada valor para configurarlo en el PreparedStatement
				int posicion = Numero.UNO.valueI.intValue();
				for (ValueSQL valor : where) {
					setValorNotNull(pst, valor, posicion);
					posicion++;
				}

				// se ejecuta la eliminacion
				pst.executeUpdate();
			}
		} finally {
			CerrarRecursos.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo que ejecuta multiples sentencias DML por BATCH de JDBC con Injection
	 * 
	 * @param dml, Es la sentencia DML a ejecutar en el BATCH
	 * @param injections, cantidad de veces que se debe ejecutar este DML
	 */
	protected void batchConInjection(Connection con, String dml, List<List<ValueSQL>> injections) throws Exception {
		PreparedStatement pst = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(dml);

			// constante numerico para el proceso
			final int ZERO = Numero.ZERO.valueI.intValue();
			final Integer UNO = Numero.UNO.valueI;
			final Integer MIL = Numero.MIL.valueI;

			// lleva la cuenta de DML agregados en el BATCH
			int countDML = ZERO;

			// se recorre la cantidad de injections agregar en el BATCH
			int posicion;
			for (List<ValueSQL> values : injections) {

				// se injecta los parametros de esta sentencia y se agrega al BATCH
				posicion = UNO;
				for (ValueSQL valueSQL : values) {

					// se valida si se debe configurar NULL en el PreparedStatement
					if (valueSQL.getValor() != null) {
						setValorNotNull(pst, valueSQL, posicion);
					} else {
						pst.setNull(posicion, valueSQL.getTipoDato());
					}
					posicion++;
				}

				// se agrega el DML al BATCH y se suma a la cuenta
				pst.addBatch();
				countDML++;

				// se valida si se debe ejecutar el BATCH
				if (!UNO.equals(countDML) && (countDML % MIL == ZERO)) {
					pst.executeBatch();
				}
			}

			// se ejecuta el ultimo bloque y se confirman los cambios
			pst.executeBatch();
		} finally {
			CerrarRecursos.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo que ejecuta multiples sentencias DML por BATCH de JDBC sin Injection
	 * 
	 * @param dmls, lista de sentencias DMLS a ejecutar en el BATCH
	 */
	protected void batchSinInjection(Connection con, List<String> dmls) throws Exception {
		// solo aplica si hay DMLS
		if (dmls != null && !dmls.isEmpty()) {
			Statement stm = null;
			try {
				// se establece el Statement
				stm = con.createStatement();

				// constante numerico para el proceso
				final int ZERO = Numero.ZERO.valueI.intValue();
				final Integer UNO = Numero.UNO.valueI;
				final Integer MIL = Numero.MIL.valueI;

				// lleva la cuenta de DML agregados en el BATCH
				int countDML = ZERO;

				// se recorre todos los DMLs que deben ser ejecutados por el BATCH
				for (String dml : dmls) {

					// se agrega el DML al BATCH y se suma a la cuenta
					stm.addBatch(dml);
					countDML++;

					// se valida si se debe ejecutar el BATCH
					if (!UNO.equals(countDML) && (countDML % MIL == ZERO)) {
						stm.executeBatch();
					}
				}

				// se ejecuta el ultimo bloque y se confirman los cambios
				stm.executeBatch();
			} finally {
				CerrarRecursos.closeStatement(stm);
			}
		}
	}

	/**
	 * Metodo que permite settear un valor NOT NULL al PreparedStatement
	 */
	private void setValorNotNull(PreparedStatement pst, ValueSQL valor, int posicion) throws Exception {
		switch (valor.getTipoDato()) {
			case Types.VARCHAR:
				pst.setString(posicion, (String) valor.getValor());
				break;

			case Types.INTEGER:
				pst.setInt(posicion, (Integer) valor.getValor());
				break;

			case Types.BIGINT:
				pst.setLong(posicion, (Long) valor.getValor());
				break;

			case Types.DATE:
				pst.setDate(posicion, new java.sql.Date(((Date) valor.getValor()).getTime()));
				break;

			case Types.TIMESTAMP:
				pst.setTimestamp(posicion, new java.sql.Timestamp(((Date) valor.getValor()).getTime()));
				break;

			case Types.BLOB:
				pst.setBytes(posicion, (byte[]) valor.getValor());
				break;
		}
	}
}
