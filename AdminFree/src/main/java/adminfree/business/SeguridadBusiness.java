package adminfree.business;

import java.sql.Connection;
import java.sql.Types;

import adminfree.constants.SQLSeguridad;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.AutenticacionDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Estado;
import adminfree.enums.Mapper;
import adminfree.enums.MessageBusiness;
import adminfree.persistence.CommonDAO;
import adminfree.persistence.MapperJDBC;
import adminfree.persistence.ValueSQL;
import adminfree.utilities.BusinessException;
import adminfree.utilities.EstrategiaCriptografica;

/**
 * 
 * Clase que contiene los procesos de negocio para el modulo de seguridad de la
 * aplicacion
 * 
 * @author Carlos Andres Diaz
 *
 */
public class SeguridadBusiness extends CommonDAO {

	/**
	 * Metodo que permite iniciar sesion como ADMINISTRADOR
	 * 
	 * @param credenciales, DTO que contiene los datos de ingreso
	 * @param securityPostToken, se utiliza para encriptar el token
	 * 
	 * @return datos del cliente administrador configurados si existe en
	 *  la base datos de acuerdo a las credenciales
	 */
	public ClienteDTO iniciarSesionAdmin(
			AutenticacionDTO credenciales,
			String securityPostToken,
			Connection connection) throws Exception {

		// se valida la nulalidad de las credenciales
		if (credenciales != null) {

			// se obtiene los valores
			String usuario = credenciales.getUsuario();
			String clave = credenciales.getClave();

			// se verifica la nulalidad del user y token
			if (clave != null && usuario != null) {

				// se procede a buscar el ADMINISTRADOR de acuerdo a sus credenciales
				ClienteDTO admin = (ClienteDTO) find(
						connection,
						SQLSeguridad.INICIAR_SESION_ADMIN,
						MapperJDBC.get(Mapper.GET_DATOS_ADMIN_AUTH),
						ValueSQL.get(clave, Types.VARCHAR),
						ValueSQL.get(usuario, Types.VARCHAR),
						ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER));

				// se valida si existe el ADMIN
				if (admin != null && admin.getId() != null) {

					// se genera y se configura el TOKEN
					credenciales.setToken(EstrategiaCriptografica.get().generarTokenAuth(usuario, clave, securityPostToken));
					admin.setCredenciales(credenciales);

					// se retorna el admin con los datos configurados
					return admin;
				}
			}
		}

		// si llega a este punto es porque las credenciales son fallidas
		throw new BusinessException(MessageBusiness.AUTENTICACION_FALLIDA_400.value);
	}

	/**
	 * Metodo que permite iniciar sesion como USUARIO
	 * 
	 * @param credenciales, DTO que contiene los datos de ingreso
	 * @param securityPostToken, se utiliza para encriptar el token
	 * @param securityPostPass, se utiliza para encriptar la clave
	 * 
	 * @return datos del usuario autenticado en el sistema
	 */
	public UsuarioDTO iniciarSesionUser(
			AutenticacionDTO credenciales,
			String securityPostToken,
			String securityPostPass,
			Connection connection) throws Exception {

		// se valida la nulalidad de las credenciales
		if (credenciales != null) {

			// se obtiene los valores
			String usuario = credenciales.getUsuario();
			String clave = credenciales.getClave();

			// se verifica si las credenciales coincide
			if (clave != null && usuario != null) {

				// se encripta la clave de acuerdo al negocio
				String claveMD5 = EstrategiaCriptografica.get().encriptarPassword(clave, securityPostPass);

				// se crea el VALUE SQL Activo
				ValueSQL activo = ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER);

				// se procede a buscar el USUARIO de acuerdo a sus credenciales
				UsuarioDTO user = (UsuarioDTO) find(
						connection,
						SQLSeguridad.INICIAR_SESION_USER,
						MapperJDBC.get(Mapper.GET_DATOS_USER_AUTH),
						ValueSQL.get(usuario, Types.VARCHAR),
						ValueSQL.get(claveMD5, Types.VARCHAR),
						activo, activo);

				// se valida si existe el USER
				if (user != null && user.getId() != null) {

					// se genera y se configura el TOKEN
					credenciales.setToken(EstrategiaCriptografica.get().generarTokenAuth(usuario, claveMD5, securityPostToken));
					credenciales.setClave(claveMD5);
					user.setCredenciales(credenciales);

					// se retorna el admin con los datos configurados
					return user;
				}
			}
		}

		// si llega a este punto es porque las credenciales son fallidas
		throw new BusinessException(MessageBusiness.AUTENTICACION_FALLIDA_400.value);
	}
}
