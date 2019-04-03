package adminfree.mappers;

import java.sql.ResultSet;
import java.util.Arrays;

import adminfree.constants.CommonConstant;
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
	 * @param parametros que necesita el mapper para ser procesado
	 * @return objecto con sus datos configurado de acuerdo al Mapper
	 */
	@Override
	public Object executeParams(ResultSet res, Object parametros) throws Exception {
		return null;
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
			admin.setId(res.getLong(Numero.UNO.valueI));
			admin.setNombre(res.getString(Numero.DOS.valueI));
		}
		return admin;
	}

	/**
	 * Mapper para obtener los datos del USUARIO cuando inicia sesion
	 */
	private Object getDatosUserAuth(ResultSet res) throws Exception {
		UsuarioDTO user = null;
		if (res.next()) {

			// se configura los datos del USUARIO
			user = new UsuarioDTO();
			user.setId(res.getLong(Numero.UNO.valueI));
			user.setNombre(res.getString(Numero.DOS.valueI));
			user.setCargo(res.getString(Numero.TRES.valueI));

			// se configura los datos del CLIENTE
			ClienteDTO cliente = new ClienteDTO();
			cliente.setId(res.getLong(Numero.CUATRO.valueI));
			cliente.setNombre(res.getString(Numero.CINCO.valueI));
			user.setCliente(cliente);

			// se configura los datos del MODULO
			String modulos = res.getString(Numero.SEIS.valueI);
			if (modulos != null) {
				user.setModulosTokens(Arrays.asList(modulos.split(CommonConstant.PUNTO_COMA)));
			}
		}
		return user;
	}
}
