package adminfree.g.persistence;

import java.sql.ResultSet;

/**
 * 
 * Clase que contiene los metodos mapper de las consultas JDBC
 * 
 * @author Carlos Andres Diaz
 *
 */
public class MapperJDBC {

	private static final Integer MAPPER_GET_CLIENTES = 1;
	private Integer tipoMapper;

	public MapperJDBC(Integer tipoMapper) {
		this.tipoMapper = tipoMapper;
	}

	public Object execute(ResultSet res) {
		Object result = null;
		if (MAPPER_GET_CLIENTES.equals(this.tipoMapper)) {
			result = getClientes(res);
		}

		return result;
	}

	private Object getClientes(ResultSet res) {
		return null;
	}
}
