package adminfree.c.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.e.utilities.ConstantNumeros;
import adminfree.e.utilities.EstrategiaCriptografica;
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
		// se procede a generar un TOKEN unico para este cliente
		String token = generarToken(con);

		// se configuran los valores para realizar el INSERT
		List<Object> insertValues = new ArrayList<>();
		insertValues.add(cliente.getNombre());
		insertValues.add(cliente.getTelefonos());
		insertValues.add(cliente.getEstado());
		insertValues.add(cliente.getEmails());
		insertValues.add(token);

		// se procede a realizar el INSERT en la BD
		insert(SQLConfiguraciones.CREAR_CLIENTE, insertValues, con);

		// se configura el WHERE sentence para obtener el cliente por su TOKEN
		List<Object> whereValues = new ArrayList<>();
		whereValues.add(token);

		// se retorna el cliente con sus datos registrados en el sistema
		MapperJDBC mapper = new MapperJDBC(MapperJDBC.MAPPER_GET_CLIENTE_TOKEN);
		Object clienteBD = find(SQLConfiguraciones.GET_CLIENTE_TOKEN, whereValues, mapper, con);
		return (ClienteDTO) clienteBD;
	}

	/**
	 * Metodo que permite generar un TOKEN unico
	 */
	private String generarToken(Connection con) throws Exception {
		// es el valor del nuevo TOKEN a retornar
		String token = null;

		// el TOKEN debe ser unico en el sistema
		boolean tokenExiste = true;
		MapperJDBC mapper;
		List<Object> whereToken;
		Long count;
		while (tokenExiste) {

			// se solicita un nuevo TOKEN
			token = new EstrategiaCriptografica().generarToken();

			// se procede a contar los registros que contenga este TOKEN
			mapper = new MapperJDBC(MapperJDBC.MAPPER_COUNT);
			whereToken = new ArrayList<>();
			whereToken.add(token);
			count = (Long) find(SQLConfiguraciones.COUNT_CLIENTE_TOKEN, whereToken, mapper, con);

			// valida si el TOKEN es unico en el sistema
			if (count.equals(ConstantNumeros.ZERO.longValue())) {
				tokenExiste = false;
			}
		}
		return token;
	}
}
