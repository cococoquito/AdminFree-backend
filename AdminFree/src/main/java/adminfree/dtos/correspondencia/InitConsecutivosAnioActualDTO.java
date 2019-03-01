package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.dtos.transversal.SelectItemDTO;

/**
 * Clase que contiene los datos iniciales al momento de entrar al submodulo de
 * Consecutivos de correspondencia solicitados para el anio actual
 * 
 * @author Carlos Andres Diaz
 */
public class InitConsecutivosAnioActualDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el reponse inicial de los consecutivos paginados **/
    private PaginadorResponseDTO consecutivos;

	/** Lista de items para mostrarlo en el componente de filtros por usuarios */
	private List<SelectItemDTO> usuarios;

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
	 * Metodo que permite obtener el valor del atributo usuarios
	 */
	public List<SelectItemDTO> getUsuarios() {
		return usuarios;
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
	 * para el atributo @param usuarios
	 */
	public void setUsuarios(List<SelectItemDTO> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param fechaActual
	 */
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
}
