package adminfree.dtos.transversal;

import java.io.Serializable;

/**
 * DTO que contiene los atributos del paginador, se debe utilizar este DTO para
 * las consultas masivas del sistema que se muestran en un p-table
 *
 * @author Carlos Andres Diaz
 */
public class PaginadorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** cantidad total de los registros */
	private Long cantidadTotal;

	/** es la cantidad de filas por paginas (0-10rowsPage, 10-10rowsPage, 20-10rowsPage)*/
	private String rowsPage;

	/** indica que cantidad va saltar en la consulta (skip0-10, skip10-10, skip20-10) */
	private String skip;

	/**
	 * Metodo que permite obtener el valor del atributo cantidadTotal
	 */
	public Long getCantidadTotal() {
		return cantidadTotal;
	}

	/**
	 * Metodo que permite obtener el valor del atributo rowsPage
	 */
	public String getRowsPage() {
		return rowsPage;
	}

	/**
	 * Metodo que permite obtener el valor del atributo skip
	 */
	public String getSkip() {
		return skip;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * cantidadTotal
	 */
	public void setCantidadTotal(Long cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param rowsPage
	 */
	public void setRowsPage(String rowsPage) {
		this.rowsPage = rowsPage;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param skip
	 */
	public void setSkip(String skip) {
		this.skip = skip;
	}
}
