package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * Este DTO se utiliza para encapsular los atributos de un valor
 * de un consecutivo para su respectiva edicion
 *
 * @author Carlos Andres Diaz
 *
 */
public class ConsecutivoEdicionValueDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del value del consecutivo */
	private Long idValue;

	/** Es el valor ingresado a editar, este valor puede ser nulo */
	private Object value;

	/** Es el valor enviado para actualizar */
	private Object valueUpdate;

	/** Es el detalle del campo, tipo, nombre, ayuda restricciones */
	private CampoEntradaDetalleDTO campo;

	/**
	 * Metodo que permite obtener el valor del atributo idValue
	 */
	public Long getIdValue() {
		return idValue;
	}

	/**
	 * Metodo que permite obtener el valor del atributo value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Metodo que permite obtener el valor del atributo campo
	 */
	public CampoEntradaDetalleDTO getCampo() {
		return campo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idValue
	 */
	public void setIdValue(Long idValue) {
		this.idValue = idValue;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param campo
	 */
	public void setCampo(CampoEntradaDetalleDTO campo) {
		this.campo = campo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo valueUpdate
	 */
	public Object getValueUpdate() {
		return valueUpdate;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param valueUpdate
	 */
	public void setValueUpdate(Object valueUpdate) {
		this.valueUpdate = valueUpdate;
	}
}
