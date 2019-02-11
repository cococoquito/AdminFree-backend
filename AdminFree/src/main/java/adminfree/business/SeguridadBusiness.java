package adminfree.business;

import java.sql.Connection;
import java.sql.Types;

import adminfree.constants.SQLSeguridad;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.dtos.seguridad.WelcomeDTO;
import adminfree.enums.Estado;
import adminfree.enums.MessagesKey;
import adminfree.mappers.MapperSeguridad;
import adminfree.persistence.CommonDAO;
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
	 * @return datos de inicio de la aplicacion
	 */
	public WelcomeDTO iniciarSesionAdmin(
			CredencialesDTO credenciales,
			String securityPostToken,
			Connection connection) throws Exception {

		// se valida la nulalidad de las credenciales
		if (credenciales != null) {

			// se obtiene los valores
			String usuario = credenciales.getUsuario();
			String token = credenciales.getClave();

			// se verifica la nulalidad del user y token
			if (token != null && usuario != null) {

				// se procede a buscar el ADMINISTRADOR de acuerdo a sus credenciales
				ClienteDTO admin = (ClienteDTO) find(
						connection,
						SQLSeguridad.INICIAR_SESION_ADMIN,
						MapperSeguridad.get(MapperSeguridad.GET_DATOS_ADMIN_AUTH),
						ValueSQL.get(token, Types.VARCHAR),
						ValueSQL.get(usuario, Types.VARCHAR),
						ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER));

				// se valida si existe el ADMIN
				if (admin != null && admin.getId() != null) {

					// se genera y se configura el TOKEN
					credenciales.setToken(EstrategiaCriptografica.get().generarTokenAuth(usuario, token, securityPostToken));

					// se construye la respuesta a retornar
					WelcomeDTO welcome = new WelcomeDTO();
					welcome.setCredenciales(credenciales);
					welcome.setAdministrador(admin);

					// se consulta los datos de bienvenida
					welcome.setDatosWelcome(new CorrespondenciaBusiness().getDatosBienvenida(admin.getId(), connection));

					// se retorna los datos de inicio de la aplicacion
					return welcome;
				}
			}
		}

		// si llega a este punto es porque las credenciales son fallidas
		throw new BusinessException(MessagesKey.KEY_AUTENTICACION_FALLIDA_ADMIN.value);
	}

	/**
	 * Metodo que permite iniciar sesion como USUARIO
	 *
	 * @param credenciales, DTO que contiene los datos de ingreso
	 * @param securityPostToken, se utiliza para encriptar el token
	 * @param securityPostPass, se utiliza para encriptar la clave
	 *
	 * @return datos de inicio de la aplicacion
	 */
	public WelcomeDTO iniciarSesionUser(
			CredencialesDTO credenciales,
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
						MapperSeguridad.get(MapperSeguridad.GET_DATOS_USER_AUTH),
						ValueSQL.get(usuario, Types.VARCHAR),
						ValueSQL.get(claveMD5, Types.VARCHAR),
						activo, activo);

				// se valida si existe el USER
				if (user != null && user.getId() != null) {

					// se genera y se configura el TOKEN
					credenciales.setToken(EstrategiaCriptografica.get().generarTokenAuth(usuario, claveMD5, securityPostToken));
					credenciales.setClave(claveMD5);

					// se construye la respuesta a retornar
					WelcomeDTO welcome = new WelcomeDTO();
					welcome.setCredenciales(credenciales);

					// este dato se utiliza para el modulo configuracion de cuenta
					user.setUsuarioIngreso(usuario);
					welcome.setUsuario(user);

					// se consulta los datos de bienvenida
					welcome.setDatosWelcome(new CorrespondenciaBusiness().getDatosBienvenida(user.getCliente().getId(), connection));

					// se retorna los datos de inicio de la aplicacion
					return welcome;
				}
			}
		}

		// si llega a este punto es porque las credenciales son fallidas
		throw new BusinessException(MessagesKey.KEY_AUTENTICACION_FALLIDA_USER.value);
	}
}
