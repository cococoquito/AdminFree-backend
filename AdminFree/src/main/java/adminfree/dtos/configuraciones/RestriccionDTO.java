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

	/** Descripcion de la restriccion */
	private String descripcion;

	/** indica si la restriccion aplica para algun campo */
	private boolean aplica;

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

	/**
	 * Metodo que permite obtener el valor del atributo aplica
	 */
	public boolean isAplica() {
		return aplica;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param aplica
	 */
	public void setAplica(boolean aplica) {
		this.aplica = aplica;
	}
}
