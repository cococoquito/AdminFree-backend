package adminfree.d.model.configuraciones;

import java.io.Serializable;

/**
 * 
 * DTO para mappear las credenciales del la pagina administrar clientes
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CredencialAdminClientesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el usuario de la autenticacion para administrar clientes */
	private String usuario;

	/** Es el clave de la autenticacion para administrar clientes */
	private String clave;

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
}
