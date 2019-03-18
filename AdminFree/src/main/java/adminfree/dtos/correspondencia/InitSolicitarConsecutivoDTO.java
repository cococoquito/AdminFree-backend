package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import adminfree.dtos.configuraciones.NomenclaturaDTO;

/**
 * Clase que contiene los datos iniciales al momento de entrar al submodulo de
 * solicitar consecutivos de correspondencia
 *
 * @author Carlos Andres Diaz
 */
public class InitSolicitarConsecutivoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Son las nomenclaturas activas a mostrar en pantalla */
	private List<NomenclaturaDTO> nomenclaturas;

	/**
	 * Es la fecha actual del sistema, no se puede tomar directamente desde angular
	 * ya que se tomaria la fecha de la maquina del cliente
	 */
	private Date fechaActual;

	/**
	 * Metodo que permite obtener el valor del atributo nomenclaturas
	 */
	public List<NomenclaturaDTO> getNomenclaturas() {
		return nomenclaturas;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaActual
	 */
	public Date getFechaActual() {
		return fechaActual;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * nomenclaturas
	 */
	public void setNomenclaturas(List<NomenclaturaDTO> nomenclaturas) {
		this.nomenclaturas = nomenclaturas;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * fechaActual
	 */
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
}
