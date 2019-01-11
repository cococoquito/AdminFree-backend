package adminfree.mappers;

import java.sql.ResultSet;

import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Numero;

/**
 * Mapper que contiene las implementaciones JDBC para el modulo de seguridad
 * 
 * @author Carlos Andres Diaz
 *
 */
public class MapperSeguridad extends Mapper {

	/** Son los tipos de mapper que soporta este modulo */
	public static final int GET_DATOS_ADMIN_AUTH = 1;
	public static final int GET_DATOS_USER_AUTH = 2;

	/** Objecto statica que se comporta como una unica instancia */
	private static MapperSeguridad instance;

	/**
	 * Constructor del Mapper no instanciable
	 */
	private MapperSeguridad() {
	}

	/**
	 * Retorna una instancia de este tipo de Mapper
	 */
	public static MapperSeguridad get(int tipoMapper) {
		if (instance == null) {
			instance = new MapperSeguridad();
		}
		instance.tipoMapper = tipoMapper;
		return instance;
	}

	/**
	 * Metodo que es ejecutado para MAPPEAR los datos de acuerdo a un ResultSet
	 *
	 * @param res, resultado de acuerdo a la consulta
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	@Override
	public Object execute(ResultSet res) throws Exception {
		Object result = null;
		switch (this.tipoMapper) {
			case MapperSeguridad.GET_DATOS_ADMIN_AUTH:
				result = getDatosAdminAuth(res);
				break;
	
			case MapperSeguridad.GET_DATOS_USER_AUTH:
				result = getDatosUserAuth(res);
				break;
			}
		return result;
	}

	/**
	 * Mapper para obtener los datos del ADMIN cuando inicia sesion
	 */
	private Object getDatosAdminAuth(ResultSet res) throws Exception {
		ClienteDTO admin = null;
		if (res.next()) {
			admin = new ClienteDTO();
			admin.setId(res.getLong(Numero.UNO.value));
			admin.setNombre(res.getString(Numero.DOS.value));
		}
		return admin;
	}

	/**
	 * Mapper para obtener los datos del USUARIO cuando inicia sesion
	 */
	private Object getDatosUserAuth(ResultSet res) throws Exception {
		UsuarioDTO user = null;
		while (res.next()) {
			if (user == null) {

				// se configura los datos del USUARIO
				user = new UsuarioDTO();
				user.setId(res.getLong(Numero.UNO.value));
				user.setNombre(res.getString(Numero.DOS.value));

				// se configura los datos del CLIENTE
				ClienteDTO cliente = new ClienteDTO();
				cliente.setId(res.getLong(Numero.TRES.value));
				cliente.setNombre(res.getString(Numero.CUATRO.value));
				user.setCliente(cliente);

				// se configura los datos del MODULO
				user.agregarModuloToken(res.getString(Numero.CINCO.value));
			} else {
				// solamente se configura los datos del MODULO
				user.agregarModuloToken(res.getString(Numero.CINCO.value));
			}
		}
		return user;
	}
}
