package adminfree.dtos.configuraciones;

import java.io.Serializable;

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

	/** Indica si los datos basicos de la nomenclatura se debe editar */
	private boolean datosBasicosEditar;

	/** Indica si los campos de entrada se debe editar */
	private boolean camposEntradaEditar;

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
}
