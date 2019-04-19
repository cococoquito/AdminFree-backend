package adminfree.dtos.archivogestion;

/**
 * DTO que contiene los atributos de la sub-serie documental
 *
 * @author Carlos Andres Diaz
 */
public class SubSerieDocumentalDTO extends Documental {
	private static final long serialVersionUID = 1L;

	/** Identificador de la sub-serie documental */
	private Long idSubSerie;

	/** Identificador serie documental que es propietaria de esta subserie */
	private Long idSerie;

	/**
	 * Metodo que permite obtener el valor del atributo idSubSerie
	 */
	public Long getIdSubSerie() {
		return idSubSerie;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idSubSerie
	 */
	public void setIdSubSerie(Long idSubSerie) {
		this.idSubSerie = idSubSerie;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idSerie
	 */
	public Long getIdSerie() {
		return idSerie;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idSerie
	 */
	public void setIdSerie(Long idSerie) {
		this.idSerie = idSerie;
	}
}
