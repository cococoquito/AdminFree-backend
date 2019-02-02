package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * DTO que es utilizado para retornar la respuesta al momento de solicitar los
 * consecutivos de correspondencia
 *
 * @author Carlos Andres Diaz
 */
public class SolicitudConsecutivoResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del consecutivo generado al momento de realizar la solicitud */
	private Long idConsecutivo;

	/** Nro consecutivo generado para el proceso */
	private String consecutivo;

	/**
	 * Metodo que permite obtener el valor del atributo idConsecutivo
	 */
	public Long getIdConsecutivo() {
		return idConsecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo consecutivo
	 */
	public String getConsecutivo() {
		return consecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idConsecutivo
	 */
	public void setIdConsecutivo(Long idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * consecutivo
	 */
	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}
}
