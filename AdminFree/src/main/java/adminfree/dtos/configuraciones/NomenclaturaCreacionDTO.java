package adminfree.dtos.configuraciones;

import java.io.Serializable;
import java.util.List;

/**
 *
 * DTO que se utiliza para la creacion de la nomenclatura
 *
 * @author Carlos Andres Diaz
 *
 */
public class NomenclaturaCreacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Contiene los datos de la nomenclatura a crear */
	private NomenclaturaDTO nomenclatura;

	/** Son los campos asociados a la nomenclatura */
	private List<Long> idsCampos;

	/**
	 * Metodo que permite obtener el valor del atributo nomenclatura
	 */
	public NomenclaturaDTO getNomenclatura() {
		return nomenclatura;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idsCampos
	 */
	public List<Long> getIdsCampos() {
		return idsCampos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * nomenclatura
	 */
	public void setNomenclatura(NomenclaturaDTO nomenclatura) {
		this.nomenclatura = nomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idsCampos
	 */
	public void setIdsCampos(List<Long> idsCampos) {
		this.idsCampos = idsCampos;
	}
}
