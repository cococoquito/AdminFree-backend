package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * DTO que se utiliza para ACTIVAR o ANULAR un consecutivo de correspondencia
 *
 * @author Carlos Andres Diaz
 *
 */
public class ActivarAnularConsecutivoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del cliente que contiene la tabla donde esta el consecutivo */
	private Integer idCliente;

	/** Identificador del consecutivo para ACTIVAR o ANULAR */
	private Long idConsecutivo;

	/** Es el nuevo estado (ACTIVO-ANULADO) para el consecutivo */
	private Integer idEstado;

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Integer getIdCliente() {
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
	public void setIdCliente(Integer idCliente) {
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
	 * Metodo que permite obtener el valor del atributo idEstado
	 */
	public Integer getIdEstado() {
		return idEstado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idEstado
	 */
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
}
