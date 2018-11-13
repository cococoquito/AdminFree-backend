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

	/** Identificador del MODULO */
	private Integer id;

	/** Nombre del MODULO */
	private String nombre;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Integer id) {
		this.id = id;
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
