package adminfree.dtos.transversal;

import java.io.Serializable;

/**
 * Se utiliza para configurar los atributos de un item para el componente selectItem
 *
 * @author Carlos Andres Diaz
 */
public class SelectItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del item */
	private Long id;

	/** Nombre del item a mostrar en el componente */
	private String label;

	/** descripcion del item, valor opcional */
	private String descripcion;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Metodo que permite obtener el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
