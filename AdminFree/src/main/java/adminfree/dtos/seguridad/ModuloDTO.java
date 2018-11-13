package adminfree.dtos.seguridad;

import java.io.Serializable;

/**
 * DTO que contiene los atributos de un MODULO
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ModuloDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** token del MODULO */
	private String tokenModulo;

	/** Nombre del MODULO */
	private String nombre;

	/**
	 * Metodo que permite obtener el valor del atributo tokenModulo
	 */
	public String getTokenModulo() {
		return tokenModulo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tokenModulo
	 */
	public void setTokenModulo(String tokenModulo) {
		this.tokenModulo = tokenModulo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
