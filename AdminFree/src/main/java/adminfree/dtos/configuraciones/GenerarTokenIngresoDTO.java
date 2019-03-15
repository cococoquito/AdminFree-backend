package adminfree.dtos.configuraciones;

import java.io.Serializable;

/**
 * DTO que se utiliza para la creacion de nuevos TOKENs para el ingreso en el
 * sistema tanto para usuarios o clientes
 * 
 * @author Carlos Andres Diaz
 *
 */
public class GenerarTokenIngresoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el identificador del cliente para generar el nuevo TOKEN de ingreso */
	private Integer idCliente;

	/** Es el identificador del usuario para generar el nuevo TOKEN de ingreso */
	private Integer idUsuario;

	/** Es el TOKEN generado para el cliente o el usuario */
	private String token;

	/**
	 * Metodo que permite obtener el valor del atributo idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo token
	 */
	public String getToken() {
		return token;
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
	 * para el atributo @param idUsuario
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
