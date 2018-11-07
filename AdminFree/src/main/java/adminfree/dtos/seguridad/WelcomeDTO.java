package adminfree.dtos.seguridad;

import java.io.Serializable;

import adminfree.dtos.configuraciones.ClienteDTO;

/**
 * DTO para transportar los datos de inicio cuando el user o admin se autentique
 * frente al sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class WelcomeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el USUARIO autenticado en el sistema */
	private UsuarioDTO usuario;

	/** Es el ADMIN autenticado en el sistema */
	private ClienteDTO administrador;

	/** Son las credenciales del USUARIO o ADMIN */
	private CredencialesDTO credenciales;

	/**
	 * Metodo que permite obtener el valor del atributo usuario
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param usuario
	 */
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo administrador
	 */
	public ClienteDTO getAdministrador() {
		return administrador;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * administrador
	 */
	public void setAdministrador(ClienteDTO administrador) {
		this.administrador = administrador;
	}

	/**
	 * Metodo que permite obtener el valor del atributo credenciales
	 */
	public CredencialesDTO getCredenciales() {
		return credenciales;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * credenciales
	 */
	public void setCredenciales(CredencialesDTO credenciales) {
		this.credenciales = credenciales;
	}
}
