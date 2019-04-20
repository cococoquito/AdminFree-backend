package adminfree.dtos.archivogestion;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que contiene los atributos de la serie documental
 *
 * @author Carlos Andres Diaz
 */
public class SerieDocumentalDTO extends Documental {
	private static final long serialVersionUID = 1L;

	/** Identificador de la serie documental */
	private Long idSerie;

	/** Lista de subseries que contiene esta serie documental */
	private List<SubSerieDocumentalDTO> subSeries;

	/**
	 * Metodo que permite agregar una subserie documental
	 */
	public void agregarSubSerie(SubSerieDocumentalDTO subSerie) {
		if (this.subSeries == null) {
			this.subSeries = new ArrayList<>();
		}
		this.subSeries.add(subSerie);
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

	/**
	 * Metodo que permite obtener el valor del atributo subSeries
	 */
	public List<SubSerieDocumentalDTO> getSubSeries() {
		return subSeries;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param subSeries
	 */
	public void setSubSeries(List<SubSerieDocumentalDTO> subSeries) {
		this.subSeries = subSeries;
	}
}
