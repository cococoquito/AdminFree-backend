package adminfree.dtos.seguridad;

import java.io.Serializable;

/**
 * 
 * DTO para mappear las credenciales para la autenticacion en el sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CredencialesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el usuario para la autenticacion del sistema */
	private String usuario;

	/** Es la clave para la autenticacion del sistema */
	private String clave;

	/** Es el token generado desde el servidor */
	private String token;

	/** Indica si el usuario es un administrador */
	private boolean administrador;

	/**
	 * Metodo que permite obtener el valor del atributo clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param clave
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Metodo que permite obtener el valor del atributo usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Metodo que permite obtener el valor del atributo administrador
	 */
	public boolean isAdministrador() {
		return administrador;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * administrador
	 */
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
}
