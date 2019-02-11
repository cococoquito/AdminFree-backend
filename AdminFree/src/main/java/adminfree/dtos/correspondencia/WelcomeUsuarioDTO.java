package adminfree.dtos.correspondencia;

import java.io.Serializable;

/**
 * DTO para transportar los usuarios para la pagina de bienvenida
 * 
 * @author Carlos Andres Diaz
 */
public class WelcomeUsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del usuario */
	private Long idUsuario;

	/** Nombre completo del usuario */
	private String nombreCompleto;

	/** Cargo que tiene el usuario */
	private String cargo;

	/** Estado en la que se encuentra el usuario */
	private String estado;

	/** Cantidad de consecutivos que ha solicitado este usuario */
	private Integer cantidadConsecutivos;

	/**
	 * Metodo que permite obtener el valor del atributo idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cantidadConsecutivos
	 */
	public Integer getCantidadConsecutivos() {
		return cantidadConsecutivos;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * idUsuario
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * nombreCompleto
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param cargo
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * cantidadConsecutivos
	 */
	public void setCantidadConsecutivos(Integer cantidadConsecutivos) {
		this.cantidadConsecutivos = cantidadConsecutivos;
	}

	/**
	 * Metodo que permite obtener el valor del atributo estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
