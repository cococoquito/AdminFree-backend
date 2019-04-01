package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.List;

import adminfree.dtos.transversal.MessageResponseDTO;

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

	/** Se utiliza para la edicion de los valores del consecutivo */
	private Long idNomenclatura;

	/** Contiene los valores generales del consecutivo */
	private ConsecutivoDTO consecutivo;

	/** Son los valores a editar de este consecutivo */
	private List<ConsecutivoEdicionValueDTO> values;

	/** Se utiliza al editar values del consecutivo, Valores a validar de acuerdo a sus restricciones */
	private List<CampoEntradaValueDTO> valoresValidar;

	/** Documentos asociados al consecutivos */
	private List<DocumentoDTO> documentos;

	/** Lista de errores encontrados en el proceso de edicion */
	private List<MessageResponseDTO> errores;

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
	 * Metodo que permite obtener el valor del atributo values
	 */
	public List<ConsecutivoEdicionValueDTO> getValues() {
		return values;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param values
	 */
	public void setValues(List<ConsecutivoEdicionValueDTO> values) {
		this.values = values;
	}

	/**
	 * Metodo que permite obtener el valor del atributo valoresValidar
	 */
	public List<CampoEntradaValueDTO> getValoresValidar() {
		return valoresValidar;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param valoresValidar
	 */
	public void setValoresValidar(List<CampoEntradaValueDTO> valoresValidar) {
		this.valoresValidar = valoresValidar;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idNomenclatura
	 */
	public Long getIdNomenclatura() {
		return idNomenclatura;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idNomenclatura
	 */
	public void setIdNomenclatura(Long idNomenclatura) {
		this.idNomenclatura = idNomenclatura;
	}

	/**
	 * Metodo que permite obtener el valor del atributo errores
	 */
	public List<MessageResponseDTO> getErrores() {
		return errores;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param errores
	 */
	public void setErrores(List<MessageResponseDTO> errores) {
		this.errores = errores;
	}
}
