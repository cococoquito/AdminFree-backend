package adminfree.b.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import adminfree.e.utilities.Constants;
import adminfree.e.utilities.ConstantsCodigoMessages;
import adminfree.e.utilities.EstrategiaCriptografica;

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

	/** Contiene la clave para la autenticacion del administrador de clientes */
	@Value(Constants.SECURITY_ADMINCLIENTES_USER)
	private String securityAdminClienteClave;

	/** Contiene el usuario para la autenticacion del administrador de clientes */
	@Value(Constants.SECURITY_ADMINCLIENTES_PASS)
	private String securityAdminClienteUser;

	/** Contiene el postToken para la generacion de TOKENS */
	@Value(Constants.SECURITY_POST_TOKEN)
	private String securityPostToken;

	/**
	 * Servicio para la autenticacion del usuario administrar clientes
	 * 
	 * @param clave, clave de la autenticacion
	 * @param usuario, usuario de la autenticacion
	 * @return el TOKEN asociado al usuario
	 */
	public String iniciarSesionAdminClientes(String clave, String usuario) throws Exception {
		try {
			// se valida si son las credenciales correctas
			if (clave != null && usuario !=  null && 
				clave.equals(this.securityAdminClienteClave) && 
				usuario.equals(this.securityAdminClienteUser)) {

				// se procede a generar el TOKEN
				return EstrategiaCriptografica.get().generarTokenAuth(
						this.securityAdminClienteUser,
						this.securityAdminClienteClave, 
						this.securityPostToken);
			}
		} catch (Exception ex) {
			throw new Exception(ConstantsCodigoMessages.MESSAGE_ERROR_TECHNICAL + ex.getMessage());
		}
		throw new Exception(ConstantsCodigoMessages.COD_AUTENTICACION_FALLIDA);
	}
}
