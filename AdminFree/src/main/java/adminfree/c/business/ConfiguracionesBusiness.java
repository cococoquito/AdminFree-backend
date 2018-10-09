package adminfree.c.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.g.persistence.CommonDAO;
import adminfree.g.persistence.MapperJDBC;
import adminfree.g.persistence.SQLConfiguraciones;

/**
 * 
 * Clase que contiene los procesos de negocio para el modulo de Configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ConfiguracionesBusiness extends CommonDAO {

	/**
	 * Business que permite crear un cliente en el sistema
	 * 
	 * @param cliente, DTO con los datos del cliente a crear
	 * @return el nuevo cliente con el token, id y demas atributos
	 */
	public ClienteDTO crearCliente(ClienteDTO cliente, Connection con) throws Exception {

		// se configuran los valores para realizar el INSERT
		List<Object> insertValues = new ArrayList<>();
		insertValues.add(cliente.getNombre());
		insertValues.add(cliente.getTelefonos());
		insertValues.add(cliente.getEstado());
		insertValues.add(cliente.getToken());

		// se procede a realizar el INSERT en la BD
		insert(SQLConfiguraciones.CREAR_CLIENTE, insertValues, con);

		// se configura el WHERE sentence para obtener el cliente por su TOKEN
		List<Object> whereValues = new ArrayList<>();
		whereValues.add(cliente.getToken());

		// se retorna el cliente con sus datos registrados en el sistema
		MapperJDBC mapper = new MapperJDBC(MapperJDBC.MAPPER_GET_CLIENTE_TOKEN);
		Object clienteBD = find(SQLConfiguraciones.GET_CLIENTE_TOKEN, whereValues, mapper, con);
		return (ClienteDTO) clienteBD;
	}
}
