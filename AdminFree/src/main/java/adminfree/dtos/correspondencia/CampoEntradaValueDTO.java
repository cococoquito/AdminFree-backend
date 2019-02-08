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

	/** Es el identificador del value CONSECUTIVOS_VALUES.ID_VALUE */
	private Long idValue;

	/** Es el identificador del campo CAMPOS_ENTRADA.ID_CAMPO */
	private Long idCampo;

	/** Es el valor ingresado por el usuario al momento de solicitar o editar consecutivo */
	private Object value;

	/** Es el nombre del campo, se utiliza para las validaciones */
	private String nombreCampo;

	/** Identificador de la siguiente tabla NOMENCLATURAS_CAMPOS_ENTRADA.ID_NOME_CAMPO */
	private Long idCampoNomenclatura;

	/** Se utiliza para hacer las validaciones correspondiente al momento de editar o solicitar */
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

	/**
	 * Metodo que permite obtener el valor del atributo idCampoNomenclatura
	 */
	public Long getIdCampoNomenclatura() {
		return idCampoNomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCampoNomenclatura
	 */
	public void setIdCampoNomenclatura(Long idCampoNomenclatura) {
		this.idCampoNomenclatura = idCampoNomenclatura;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idValue
	 */
	public Long getIdValue() {
		return idValue;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idValue
	 */
	public void setIdValue(Long idValue) {
		this.idValue = idValue;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombreCampo
	 */
	public String getNombreCampo() {
		return nombreCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombreCampo
	 */
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCampo
	 */
	public Long getIdCampo() {
		return idCampo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCampo
	 */
	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}
}
