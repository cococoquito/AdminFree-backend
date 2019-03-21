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
	private String idCliente;

	/** Identificador del consecutivo a transferir */
	private String idConsecutivo;

	/** Identificador del usuario quien tiene actualmente el consecutivo */
	private String idUsuario;

	/** Identificador del usuario a quien se va transferir */
	private String idUsuarioTransferir;

	/** Se utiliza para consultar los consecutivos de acuerdo al filtro establecido */
	private FiltroConsecutivosDTO filtro;

	/** Es el response despues de hacer todo el proceso de negocio de transferir */
	private PaginadorResponseDTO responseConsecutivos;

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public String getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idConsecutivo
	 */
	public String getIdConsecutivo() {
		return idConsecutivo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuarioTransferir
	 */
	public String getIdUsuarioTransferir() {
		return idUsuarioTransferir;
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
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idConsecutivo
	 */
	public void setIdConsecutivo(String idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idUsuario
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param idUsuarioTransferir
	 */
	public void setIdUsuarioTransferir(String idUsuarioTransferir) {
		this.idUsuarioTransferir = idUsuarioTransferir;
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
