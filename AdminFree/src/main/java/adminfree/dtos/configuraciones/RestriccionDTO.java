package adminfree.dtos.configuraciones;

import java.io.Serializable;

/**
 * 
 * DTO que contiene los atributos para las restricciones de los campos de
 * ingreso para solicitar consecutivos
 * 
 * @author Carlos Andres Diaz
 *
 */
public class RestriccionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el identificador de la restriccion */
	private Integer id;

	/** Nombre de la restriccion */
	private String nombre;

	/** Descripcion de la restriccion */
	private String descripcion;

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

	/**
	 * Metodo que permite obtener el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
