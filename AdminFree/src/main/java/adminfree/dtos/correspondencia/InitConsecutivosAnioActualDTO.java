package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.List;

import adminfree.dtos.transversal.SelectItemDTO;

/**
 * Clase que contiene los datos iniciales al momento de entrar al submodulo de
 * Consecutivos de correspondencia solicitados para el anio actual
 * 
 * @author Carlos Andres Diaz
 */
public class InitConsecutivosAnioActualDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Lista de consecutivos que se muestra al momento de entrar al submodulo */
	private List<ConsecutivoDTO> consecutivos;

	/** Lista de items para mostrarlo en el componente de filtros por usuarios */
	private List<SelectItemDTO> usuarios;

	/**
	 * Metodo que permite obtener el valor del atributo consecutivos
	 */
	public List<ConsecutivoDTO> getConsecutivos() {
		return consecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo consecutivos
	 */
	public void setConsecutivos(List<ConsecutivoDTO> consecutivos) {
		this.consecutivos = consecutivos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo usuarios
	 */
	public List<SelectItemDTO> getUsuarios() {
		return usuarios;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param usuarios
	 */
	public void setUsuarios(List<SelectItemDTO> usuarios) {
		this.usuarios = usuarios;
	}
}
