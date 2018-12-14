package adminfree.dtos.configuraciones;

import java.io.Serializable;

/**
 * DTO que se utiliza para la edicion de los campos de entrada
 * 
 * @author Carlos Andres Diaz
 *
 */
public class CampoEntradaEdicionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Contiene los datos del campo de entrada a editar */
	private CampoEntradaDTO campoEntrada;

	/** indica si el campo de entrada tiene nomenclaturas */
	private boolean tieneNomenclaturas;

	/** indica si el campo de entrada tiene restricciones */
	private boolean tieneRestricciones;

	/** Es el identificador del campo de entrada */
	private boolean tieneConsecutivos;

	/**
	 * Metodo que permite obtener el valor del atributo campoEntrada
	 */
	public CampoEntradaDTO getCampoEntrada() {
		return campoEntrada;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * campoEntrada
	 */
	public void setCampoEntrada(CampoEntradaDTO campoEntrada) {
		this.campoEntrada = campoEntrada;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tieneNomenclaturas
	 */
	public boolean isTieneNomenclaturas() {
		return tieneNomenclaturas;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tieneNomenclaturas
	 */
	public void setTieneNomenclaturas(boolean tieneNomenclaturas) {
		this.tieneNomenclaturas = tieneNomenclaturas;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tieneConsecutivos
	 */
	public boolean isTieneConsecutivos() {
		return tieneConsecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tieneConsecutivos
	 */
	public void setTieneConsecutivos(boolean tieneConsecutivos) {
		this.tieneConsecutivos = tieneConsecutivos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tieneRestricciones
	 */
	public boolean isTieneRestricciones() {
		return tieneRestricciones;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tieneRestricciones
	 */
	public void setTieneRestricciones(boolean tieneRestricciones) {
		this.tieneRestricciones = tieneRestricciones;
	}
}
