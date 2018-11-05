package adminfree.business;

import java.sql.Connection;
import java.sql.Types;

import adminfree.constants.SQLSeguridad;
import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.AutenticacionDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Estado;
import adminfree.enums.MessageBusiness;
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
	 * @return datos del cliente que es el administrador configurados
	 * si existe en la base datos de acuerdo a las credenciales
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

			// se verifica si las credenciales coincide
			if (clave != null && usuario != null) {
				find(connection, SQLSeguridad.INICIAR_SESION_ADMIN, null, ValueSQL.get(clave, Types.VARCHAR),
						ValueSQL.get(usuario, Types.VARCHAR), ValueSQL.get(Estado.ACTIVO.id, Types.INTEGER));
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

			}

		}
		// si llega a este punto es porque las credenciales son fallidas
		throw new BusinessException(MessageBusiness.AUTENTICACION_FALLIDA_400.value);
	}

	/**
	 * Metodo que soporta el proceso de negocio para iniciar sesion
	 * 
	 * @param credenciales, DTO que contiene los datos de ingreso
	 * @return, DTO con
	 *        los datos del usuario autenticado
	 *        
	 * @param securityPostToken, Contiene el postToken para la generacion de TOKENS
	 * @param securityPostPass, Contiene el postPass para la encriptacion de la clave
	 *         
	 */
	private UsuarioDTO iniciarSesion(AutenticacionDTO credenciales, String securityPostToken, String securityPostPass, Connection con) throws Exception {
		// se valida la nulalidad de las credenciales
		if (credenciales != null) {
			
			// se obtiene los valores
			String usuario = credenciales.getUsuario();
			String clave = credenciales.getClave();
			
			// se verifica si las credenciales coincide
			if (clave != null && usuario != null) {
				
				if (credenciales.isAdministrador()) {

				} else {
					String claveMD5 = EstrategiaCriptografica.get().encriptarPassword(clave, securityPostPass);
					
					
					
				}
				
				
				
				
				
				
				
				
				
				
				
			}
					
			
			
		}
		
		
		
		// si llega a este punto es porque las credenciales son fallidas
		throw new BusinessException(MessageBusiness.AUTENTICACION_FALLIDA_400.value);
	}
}
