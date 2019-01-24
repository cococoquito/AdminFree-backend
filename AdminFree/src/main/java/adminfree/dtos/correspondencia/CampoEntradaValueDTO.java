package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene el valor del campo de entrada para el proceso de 
 * solicitar o edicion de un consecutivo de correspondencia
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CampoEntradaValueDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el valor ingresado por el usuario al momento de solicitar o editar consecutivo */
	private Object value;

	/** Son las restricciones del campo, se utiliza para hacer las validaciones correspondiente */
	private List<String> restricciones;

	/**
	 * Metodo que permite agregar una restriccion para este campo
	 */
	public void agregarRestriccion(String restriccion) {
		if (this.restricciones == null) {
			this.restricciones = new ArrayList<>();
		}
		this.restricciones.add(restriccion);
	}

	/**
	 * Metodo que permite obtener el valor del atributo value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Metodo que permite obtener el valor del atributo restricciones
	 */
	public List<String> getRestricciones() {
		return restricciones;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * restricciones
	 */
	public void setRestricciones(List<String> restricciones) {
		this.restricciones = restricciones;
	}
}
