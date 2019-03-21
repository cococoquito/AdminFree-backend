package adminfree.dtos.correspondencia;

import java.io.Serializable;

import adminfree.dtos.transversal.PaginadorResponseDTO;

/**
 * DTO que contiene los atributos para el proceso de negocio
 * de transferir un consecutico a otro usuario
 *
 * @author Carlos Andres Diaz
 */
public class TransferirConsecutivoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del cliente que contiene la tabla donde esta el consecutivo */
	private Integer idCliente;

	/** Identificador del consecutivo a transferir */
	private Long idConsecutivo;

	/** Identificador del usuario quien tiene actualmente el consecutivo */
	private Integer idUsuario;

	/** Identificador del usuario a quien se va transferir */
	private Integer idUsuarioTransferir;

	/** La fecha de transferido es la fecha de solicitud para el primer usuario */
	private String fechaSolicitudConsecutivo;

	/** Se utiliza para consultar los consecutivos de acuerdo al filtro establecido */
	private FiltroConsecutivosDTO filtro;

	/** Es el response despues de hacer todo el proceso de negocio de transferir */
	private PaginadorResponseDTO responseConsecutivos;

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
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuarioTransferir
	 */
	public Integer getIdUsuarioTransferir() {
		return idUsuarioTransferir;
	}

	/**
	 * Metodo que permite obtener el valor del atributo fechaSolicitudConsecutivo
	 */
	public String getFechaSolicitudConsecutivo() {
		return fechaSolicitudConsecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo filtro
	 */
	public FiltroConsecutivosDTO getFiltro() {
		return filtro;
	}

	/**
	 * Metodo que permite obtener el valor del atributo responseConsecutivos
	 */
	public PaginadorResponseDTO getResponseConsecutivos() {
		return responseConsecutivos;
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
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idUsuario
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idUsuarioTransferir
	 */
	public void setIdUsuarioTransferir(Integer idUsuarioTransferir) {
		this.idUsuarioTransferir = idUsuarioTransferir;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param fechaSolicitudConsecutivo
	 */
	public void setFechaSolicitudConsecutivo(String fechaSolicitudConsecutivo) {
		this.fechaSolicitudConsecutivo = fechaSolicitudConsecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param filtro
	 */
	public void setFiltro(FiltroConsecutivosDTO filtro) {
		this.filtro = filtro;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param responseConsecutivos
	 */
	public void setResponseConsecutivos(PaginadorResponseDTO responseConsecutivos) {
		this.responseConsecutivos = responseConsecutivos;
	}
}
