package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.List;

/**
 * Contiene el detalle de un consecutivo, consecutivo, valores, documentos
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ConsecutivoDetalleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del cliente, se utiliza para buscar el detalle del consecutivo */
	private Long idCliente;

	/** identificador del consecutivo, se utiliza para buscar el detalle del consecutivo */
	private Long idConsecutivo;

	/** Contiene los valores generales del consecutivo */
	private ConsecutivoDTO consecutivo;

	/** Contiene la informacion del consecutivo */
	private List<CampoEntradaValueDTO> valores;

	/** Documentos asociados al consecutivos */
	private List<DocumentoDTO> documentos;

	/**
	 * Metodo que permite obtener el valor del atributo consecutivo
	 */
	public ConsecutivoDTO getConsecutivo() {
		return consecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo valores
	 */
	public List<CampoEntradaValueDTO> getValores() {
		return valores;
	}

	/**
	 * Metodo que permite obtener el valor del atributo documentos
	 */
	public List<DocumentoDTO> getDocumentos() {
		return documentos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param consecutivo
	 */
	public void setConsecutivo(ConsecutivoDTO consecutivo) {
		this.consecutivo = consecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param valores
	 */
	public void setValores(List<CampoEntradaValueDTO> valores) {
		this.valores = valores;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param documentos
	 */
	public void setDocumentos(List<DocumentoDTO> documentos) {
		this.documentos = documentos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idConsecutivo
	 */
	public Long getIdConsecutivo() {
		return idConsecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idCliente
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idConsecutivo
	 */
	public void setIdConsecutivo(Long idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}
}
