package adminfree.dtos.archivogestion;

import java.io.Serializable;

import adminfree.dtos.transversal.PaginadorResponseDTO;

/**
 * Clase que contiene los datos iniciales al momento de entrar al submodulo de
 * administrar series documentales
 *
 * @author Carlos Andres Diaz
 */
public class InitAdminSeriesDocumentalesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el response inicial de las series paginados **/
	private PaginadorResponseDTO series;

	/**
	 * Metodo que permite obtener el valor del atributo series
	 */
	public PaginadorResponseDTO getSeries() {
		return series;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param series
	 */
	public void setSeries(PaginadorResponseDTO series) {
		this.series = series;
	}
}
