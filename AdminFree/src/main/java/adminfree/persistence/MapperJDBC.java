package adminfree.persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import adminfree.model.configuraciones.ClienteDTO;
import adminfree.utilities.ConstantNumeros;

/**
 * Clase que contiene los metodos MAPPER para las consultas JDBC
 * 
 * @author Carlos Andres Diaz
 *
 */
public class MapperJDBC {

	/** Constantes para los identificadores de los MAPPERS */
	public static final int MAPPER_GET_CLIENTES = 1;
	public static final int MAPPER_COUNT = 2;
	public static final int MAPPER_GET_CLIENTE_TOKEN = 3;

	/** Es el tipo de MAPPER a ejecutar */
	private Integer tipoMapper;
	
	/**
	 * Retorna una instancia de este tipo de Clase
	 */
	public static MapperJDBC get(Integer tipoMapper) {
		return new MapperJDBC(tipoMapper);
	}	

	/**
	 * Constructor del Mapper donde recibe el tipo de mapper a ejecutar
	 */
	private MapperJDBC(Integer tipoMapper) {
		this.tipoMapper = tipoMapper;
	}

	/**
	 * Metodo que es ejecutado para MAPPER los datos de acuerdo a un ResultSet
	 * 
	 * @param res, resultado de acuerdo a la consulta
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	public Object execute(ResultSet res) throws Exception {
		// encapsula el resultado con sus atributo configurados
		Object result = null;
		
		// se ejecuta el metodo de acuerdo al tipo de MAPPER
		switch (this.tipoMapper) {
			case MAPPER_GET_CLIENTES:
				result = getClientes(res);
				break;
	
			case MAPPER_COUNT:
				result = getCount(res);
				break;
				
			case MAPPER_GET_CLIENTE_TOKEN:
				result = getCliente(res);
				break;				
		}
		return result;
	}

	/**
	 * Mapper para configurar los atributos de los CLIENTES
	 */
	private Object getClientes(ResultSet res) throws Exception {
		List<ClienteDTO> resultado = new ArrayList<>();
		ClienteDTO cliente = null;
		while (res.next()) {
			cliente = new ClienteDTO();
			cliente.setId(res.getLong(ConstantNumeros.UNO));
			cliente.setToken(res.getString(ConstantNumeros.DOS));
			cliente.setNombre(res.getString(ConstantNumeros.TRES));
			cliente.setTelefonos(res.getString(ConstantNumeros.CUATRO));
			cliente.setEmails(res.getString(ConstantNumeros.CINCO));
			cliente.setFechaActivacion(res.getDate(ConstantNumeros.SEIS));
			cliente.setFechaInactivacion(res.getDate(ConstantNumeros.SIETE));
			cliente.setEstado(res.getInt(ConstantNumeros.OCHO));
			resultado.add(cliente);
		}
		return resultado;
	}

	/**
	 * Mapper para configurar el COUNT de un SELECT
	 */	
	private Object getCount(ResultSet res) throws Exception {
		if (res.next()) {
            return res.getLong(ConstantNumeros.UNO);
        }
		return ConstantNumeros.ZERO.longValue();
	}
	
	/**
	 * Mapper para configurar los datos de un CLIENTE
	 */
	private Object getCliente(ResultSet res) throws Exception {
		ClienteDTO cliente = null;
		if (res.next()) {
			cliente = new ClienteDTO();
			cliente.setId(res.getLong(ConstantNumeros.UNO));
			cliente.setToken(res.getString(ConstantNumeros.DOS));
			cliente.setNombre(res.getString(ConstantNumeros.TRES));
			cliente.setTelefonos(res.getString(ConstantNumeros.CUATRO));
			cliente.setEmails(res.getString(ConstantNumeros.CINCO));
			cliente.setFechaActivacion(res.getDate(ConstantNumeros.SEIS));
			cliente.setEstado(res.getInt(ConstantNumeros.SIETE));
		}
		return cliente;
	}
}
