package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * Contiene el detalle de los campos relacionados a una nomenclatura
 * 
 * @author Carlos Andres Diaz
 *
 */
public class NomenclaturaDetalleCamposDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del campo */
	private Long id;

	/** Es el nombre del campo */
	private String nombre;

	/** Es la descripcion que identifica el campo */
	private String descripcion;

	/** Nombre del tipo de campo */
	private String tipoCampo;

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tipoCampo
	 */
	public String getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tipoCampo
	 */
	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
