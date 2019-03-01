package adminfree.dtos.transversal;

import java.io.Serializable;

/**
 * DTO que contiene los datos del response para los paginadores de la aplicacion
 *
 * @author Carlos Andres Diaz
 */
public class PaginadorResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** son los registros consultados */
	private Object registros;

	/** cantidad total de los registros */
	private Long cantidadTotal;

	/**
	 * Metodo que permite obtener el valor del atributo registros
	 */
	public Object getRegistros() {
		return registros;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cantidadTotal
	 */
	public Long getCantidadTotal() {
		return cantidadTotal;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param registros
	 */
	public void setRegistros(Object registros) {
		this.registros = registros;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param cantidadTotal
	 */
	public void setCantidadTotal(Long cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}
}
