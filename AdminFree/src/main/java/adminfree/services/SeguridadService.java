package adminfree.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import adminfree.constants.PropertyKey;
import adminfree.enums.MessageBusiness;
import adminfree.model.configuraciones.AutenticacionDTO;
import adminfree.utilities.BusinessException;
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

	/**
	 * Servicio para la autenticacion del usuario administrar clientes
	 * 
	 * @param clave, clave de la autenticacion
	 * @param usuario, usuario de la autenticacion
	 * @return DTO con el TOKEN asociado al usuario
	 */
	public AutenticacionDTO iniciarSesionAdminClientes(AutenticacionDTO credenciales) throws Exception {
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
				
				// se construye el response con el TOKEN generado
				AutenticacionDTO response = new AutenticacionDTO();
				response.setToken(token);
				return response;
			}
		}

		// se lanza bussines exception si las credenciales son fallidas
		throw new BusinessException(MessageBusiness.AUTENTICACION_FALLIDA.value);
	}
}
