package adminfree.dtos.archivogestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que contiene el response de la edicion de la serie/subserie documental
 *
 * @author Carlos Andres Diaz
 */
public class ResponseEdicionSerieSubserieDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** contiene los datos que fueron actualizados en el sistema **/
	private Documental datosUpdate;

	/** son los nuevos tipos documentales registrados en el sistema **/
	private List<TipoDocumentalDTO> tiposDocumentales;

	/**
	 * Metodo que permite agregar un tipo documental
	 */
	public void agregarTipoDocumental(TipoDocumentalDTO tipoDocumental) {
		if (this.tiposDocumentales == null) {
			this.tiposDocumentales = new ArrayList<>();
		}
		this.tiposDocumentales.add(tipoDocumental);
	}

	/**
	 * Metodo que permite obtener el valor del atributo datosUpdate
	 */
	public Documental getDatosUpdate() {
		return datosUpdate;
	}

	/**
	 * Metodo que permite obtener el valor del atributo tiposDocumentales
	 */
	public List<TipoDocumentalDTO> getTiposDocumentales() {
		return tiposDocumentales;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * datosUpdate
	 */
	public void setDatosUpdate(Documental datosUpdate) {
		this.datosUpdate = datosUpdate;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * tiposDocumentales
	 */
	public void setTiposDocumentales(List<TipoDocumentalDTO> tiposDocumentales) {
		this.tiposDocumentales = tiposDocumentales;
	}
}
