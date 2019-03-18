package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.Date;

import adminfree.dtos.transversal.PaginadorResponseDTO;

/**
 * Clase que contiene los datos iniciales al momento de entrar al
 * submodulo de mis consecutivos solicitados para el anio actual
 * 
 * @author Carlos Andres Diaz
 */
public class InitMisConsecutivosDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el reponse inicial de los consecutivos paginados **/
	private PaginadorResponseDTO consecutivos;

	/**
	 * Es la fecha actual del sistema, no se puede tomar directamente desde angular
	 * ya que se tomaria la fecha de la maquina del cliente
	 */
	private Date fechaActual;

	/**
	 * Metodo que permite obtener el valor del atributo consecutivos
	 */
	public PaginadorResponseDTO getConsecutivos() {
		return consecutivos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaActual
	 */
	public Date getFechaActual() {
		return fechaActual;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param consecutivos
	 */
	public void setConsecutivos(PaginadorResponseDTO consecutivos) {
		this.consecutivos = consecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param fechaActual
	 */
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
}
