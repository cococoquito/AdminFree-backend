package adminfree.dtos.seguridad;

import java.io.Serializable;

/**
 * DTO para el proceso de negocio de cambio de clave de ingreso
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CambioClaveDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del user a modificar la clave de ingreso **/
	private Long idUsuario;

	/** Es la clave actual que tiene el usuario */
	private String claveActual;

	/** Representa la nueva clave de ingreso */
	private String claveNueva;

	/** Es la nueva clave de ingreso confirmada */
	private String claveVerificacion;

	/**
	 * Metodo que permite obtener el valor del atributo claveActual
	 */
	public String getClaveActual() {
		return claveActual;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * claveActual
	 */
	public void setClaveActual(String claveActual) {
		this.claveActual = claveActual;
	}

	/**
	 * Metodo que permite obtener el valor del atributo claveNueva
	 */
	public String getClaveNueva() {
		return claveNueva;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * claveNueva
	 */
	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idUsuario
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo claveVerificacion
	 */
	public String getClaveVerificacion() {
		return claveVerificacion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * claveVerificacion
	 */
	public void setClaveVerificacion(String claveVerificacion) {
		this.claveVerificacion = claveVerificacion;
	}
}
