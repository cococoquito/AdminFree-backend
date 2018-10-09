package adminfree.d.model.configuraciones;

import java.io.Serializable;

/**
 * 
 * DTO que contiene los atributos de autenticacion para la pagina administrativa
 * de los clientes
 * 
 * @author Carlos Andres Diaz
 *
 */
public class AdminAutenticacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el TOKEN al ingresar a la pagina admin de clientes */
	private String token;

	/** Es la CLAVE al ingresar a la pagina admin de clientes */
	private String clave;

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
}
