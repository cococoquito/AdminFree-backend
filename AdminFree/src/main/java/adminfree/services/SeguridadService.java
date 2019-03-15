package adminfree.services;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import adminfree.business.ConfiguracionesBusiness;
import adminfree.business.SeguridadBusiness;
import adminfree.constants.PropertyKey;
import adminfree.dtos.configuraciones.AdminClientesDTO;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.WelcomeDTO;
import adminfree.enums.MessagesKey;
import adminfree.utilities.BusinessException;
import adminfree.utilities.CerrarRecursos;
import adminfree.utilities.EstrategiaCriptografica;

/**
 *
 * Clase que contiene todos los servicios para la seguridad del sistema
 *
 * @author Carlos Andres Diaz
 *
 */
@Service
public class SeguridadService {

	/** DataSource para las conexiones de la BD de AdminFree */
	@Autowired
	private DataSource adminFreeDS;

	/** Contiene el usuario para la autenticacion del administrador de clientes */
	@Value(PropertyKey.SECURITY_ADMINCLIENTES_USER)
	private String securityAdminClienteUser;

	/** Contiene la clave para la autenticacion del administrador de clientes */
	@Value(PropertyKey.SECURITY_ADMINCLIENTES_PASS)
	private String securityAdminClienteClave;

	/** Contiene el postToken para la generacion de TOKENS */
	@Value(PropertyKey.SECURITY_POST_TOKEN)
	private String securityPostToken;

	/** Contiene el postPass para la encriptacion de claves */
	@Value(PropertyKey.SECURITY_POST_PASS)
	private String securityPostPass;	

	/**
	 * Servicio para la autenticacion del usuario administrar clientes
	 *
	 * @param clave, clave de la autenticacion
	 * @param usuario, usuario de la autenticacion
	 * @return DTO con los datos iniciales del modulo
	 */
	public AdminClientesDTO iniciarSesionAdminClientes(CredencialesDTO credenciales) throws Exception {
		Connection connection = null;
		try {
			if (credenciales != null) {

				// se obtiene los valores
				String usuario = credenciales.getUsuario();
				String clave = credenciales.getClave();

				// se verifica si las credenciales coincide
				if (clave != null && usuario != null && 
					clave.equals(this.securityAdminClienteClave) && 
					usuario.equals(this.securityAdminClienteUser)) {

					// se procede a generar el TOKEN
					String token = EstrategiaCriptografica.get().generarTokenAuth(
							this.securityAdminClienteUser,
							this.securityAdminClienteClave,
							this.securityPostToken);

					// se configura el TOKEN en el response
					CredencialesDTO autenticacion = new CredencialesDTO();
					autenticacion.setToken(token);

					// se configura el DTO de respuesta
					AdminClientesDTO respuesta = new AdminClientesDTO();
					respuesta.setCredenciales(autenticacion);

					// se procede a consultar los clientes
					connection = this.adminFreeDS.getConnection();
					respuesta.setClientes(new ConfiguracionesBusiness().listarClientes(connection));
					return respuesta;
				}
			}
		} finally {
			CerrarRecursos.closeConnection(connection);
		}

		// si llega a este punto es porque las credenciales son fallidas
		throw new BusinessException(MessagesKey.KEY_AUTENTICACION_FALLIDA_USER.value);
	}

	/**
	 * Servicio que permite iniciar sesion como ADMINISTRADOR
	 *
	 * @param credenciales, DTO que contiene los datos de ingreso
	 *
	 * @return datos de inicio de la aplicacion
	 */
	public WelcomeDTO iniciarSesionAdmin(CredencialesDTO credenciales) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a iniciar sesion como ADMINISTRADOR
			return new SeguridadBusiness().iniciarSesionAdmin(
					credenciales,
					this.securityPostToken,
					this.securityPostPass,
					connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}

	/**
	 * Servicio que permite iniciar sesion como USUARIO
	 *
	 * @param credenciales, DTO que contiene los datos de ingreso
	 *
	 * @return datos de inicio de la aplicacion
	 */	
	public WelcomeDTO iniciarSesionUser(CredencialesDTO credenciales) throws Exception {
		Connection connection = null;
		try {
			// se solicita una conexion de la BD de AdminFree
			connection = this.adminFreeDS.getConnection();

			// se procede a iniciar sesion como USUARIO
			return new SeguridadBusiness().iniciarSesionUser(
					credenciales,
					this.securityPostToken,
					this.securityPostPass,
					connection);
		} finally {
			CerrarRecursos.closeConnection(connection);
		}
	}	
}
