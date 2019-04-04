package adminfree.dtos.configuraciones;

import java.io.Serializable;

/**
 * DTO para el proceso de negocio de cambio de usuario de ingreso
 *
 * @author Carlos Andres Diaz
 *
 */
public class CambioUsuarioIngresoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del user a modificar el usuario de ingreso **/
	private Long idUsuario;

	/** Es el nuevo usuario a modificar */
	private String usuario;

	/** Es la clave actual que tiene el usuario */
	private String claveActual;

	/**
	 * Metodo que permite obtener el valor del atributo usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo claveActual
	 */
	public String getClaveActual() {
		return claveActual;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * claveActual
	 */
	public void setClaveActual(String claveActual) {
		this.claveActual = claveActual;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idUsuario
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
