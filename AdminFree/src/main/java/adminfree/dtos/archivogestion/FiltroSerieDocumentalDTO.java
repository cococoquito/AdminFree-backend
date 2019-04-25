package adminfree.dtos.archivogestion;

import java.io.Serializable;

import adminfree.dtos.transversal.PaginadorDTO;

/**
 * DTO que contiene los atributos para los filtros de busqueda de las series documentales
 *
 * @author Carlos Andres Diaz
 *
 */
public class FiltroSerieDocumentalDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** paginador para la consulta de las series documentales **/
	private PaginadorDTO paginador;

	/** Es el cliente autenticado o el cliente asociado al user autenticado */
	private Long idCliente;

	/** Filtro por codigo de la serie documental */
	private String codigoSerieDocumental;

	/** Filtro por nombre de la serie documental */
	private String nombreSerieDocumental;

	/** Filtro por codigo de la sub-serie documental */
	private String codigoSubSerieDocumental;

	/** Filtro por nombre de la sub-serie documental */
	private String nombreSubSerieDocumental;

	/**
	 * Metodo que permite obtener el valor del atributo paginador
	 */
	public PaginadorDTO getPaginador() {
		return paginador;
	}

	/**
	 * Metodo que permite obtener el valor del atributo codigoSerieDocumental
	 */
	public String getCodigoSerieDocumental() {
		return codigoSerieDocumental;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombreSerieDocumental
	 */
	public String getNombreSerieDocumental() {
		return nombreSerieDocumental;
	}

	/**
	 * Metodo que permite obtener el valor del atributo codigoSubSerieDocumental
	 */
	public String getCodigoSubSerieDocumental() {
		return codigoSubSerieDocumental;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombreSubSerieDocumental
	 */
	public String getNombreSubSerieDocumental() {
		return nombreSubSerieDocumental;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param paginador
	 */
	public void setPaginador(PaginadorDTO paginador) {
		this.paginador = paginador;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param codigoSerieDocumental
	 */
	public void setCodigoSerieDocumental(String codigoSerieDocumental) {
		this.codigoSerieDocumental = codigoSerieDocumental;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombreSerieDocumental
	 */
	public void setNombreSerieDocumental(String nombreSerieDocumental) {
		this.nombreSerieDocumental = nombreSerieDocumental;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param codigoSubSerieDocumental
	 */
	public void setCodigoSubSerieDocumental(String codigoSubSerieDocumental) {
		this.codigoSubSerieDocumental = codigoSubSerieDocumental;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param nombreSubSerieDocumental
	 */
	public void setNombreSubSerieDocumental(String nombreSubSerieDocumental) {
		this.nombreSubSerieDocumental = nombreSubSerieDocumental;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCliente
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
}
