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

	/** Nombre o descripcion del item a mostrar en el componente */
	private String label;

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
}
