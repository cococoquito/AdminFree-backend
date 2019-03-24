package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.List;

/**
 * Este DTO se utiliza para encapsular los datos de un consecutivo
 * de correspondencia para su respectiva edicion
 *
 * @author Carlos Andres Diaz
 *
 */
public class ConsecutivoEdicionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del cliente, se utiliza para buscar el detalle del consecutivo */
	private Long idCliente;

	/** identificador del consecutivo, se utiliza para buscar el detalle del consecutivo */
	private Long idConsecutivo;

	/** Contiene los valores generales del consecutivo */
	private ConsecutivoDTO consecutivo;

	/** Documentos asociados al consecutivos */
	private List<DocumentoDTO> documentos;

	/** Lista de transferencias que se han realizado para este consecutivo */
	private List<TransferenciaDTO> transferencias;

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
	 * Metodo que permite obtener el valor del atributo consecutivo
	 */
	public ConsecutivoDTO getConsecutivo() {
		return consecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo documentos
	 */
	public List<DocumentoDTO> getDocumentos() {
		return documentos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo transferencias
	 */
	public List<TransferenciaDTO> getTransferencias() {
		return transferencias;
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

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param consecutivo
	 */
	public void setConsecutivo(ConsecutivoDTO consecutivo) {
		this.consecutivo = consecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param documentos
	 */
	public void setDocumentos(List<DocumentoDTO> documentos) {
		this.documentos = documentos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param transferencias
	 */
	public void setTransferencias(List<TransferenciaDTO> transferencias) {
		this.transferencias = transferencias;
	}
}
