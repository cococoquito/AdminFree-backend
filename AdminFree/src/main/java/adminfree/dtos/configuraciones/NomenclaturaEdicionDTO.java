package adminfree.dtos.configuraciones;

import java.io.Serializable;
import java.util.List;

/**
 *
 * DTO que se utiliza para la edicion de la nomenclatura
 *
 * @author Carlos Andres Diaz
 *
 */
public class NomenclaturaEdicionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Contiene los datos de la nomenclatura a editar */
	private NomenclaturaDTO nomenclatura;

	/** Si no hay campos al momento de editar la nomenclatura*/
	private List<CampoEntradaDTO> campos;

	/** Indica si los datos basicos de la nomenclatura se debe editar */
	private boolean datosBasicosEditar;

	/** Indica si los campos de entrada se deben editar */
	private boolean camposEntradaEditar;

	/** Indica si las restricciones se deben editar */
	private boolean restriccionesEditar;

	/**
	 * Metodo que permite obtener el valor del atributo nomenclatura
	 */
	public NomenclaturaDTO getNomenclatura() {
		return nomenclatura;
	}

	/**
	 * Metodo que permite obtener el valor del atributo datosBasicosEditar
	 */
	public boolean isDatosBasicosEditar() {
		return datosBasicosEditar;
	}

	/**
	 * Metodo que permite obtener el valor del atributo camposEntradaEditar
	 */
	public boolean isCamposEntradaEditar() {
		return camposEntradaEditar;
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
	 * datosBasicosEditar
	 */
	public void setDatosBasicosEditar(boolean datosBasicosEditar) {
		this.datosBasicosEditar = datosBasicosEditar;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * camposEntradaEditar
	 */
	public void setCamposEntradaEditar(boolean camposEntradaEditar) {
		this.camposEntradaEditar = camposEntradaEditar;
	}

	/**
	 * Metodo que permite obtener el valor del atributo campos
	 */
	public List<CampoEntradaDTO> getCampos() {
		return campos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param campos
	 */
	public void setCampos(List<CampoEntradaDTO> campos) {
		this.campos = campos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo restriccionesEditar
	 */
	public boolean isRestriccionesEditar() {
		return restriccionesEditar;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param restriccionesEditar
	 */
	public void setRestriccionesEditar(boolean restriccionesEditar) {
		this.restriccionesEditar = restriccionesEditar;
	}
}
