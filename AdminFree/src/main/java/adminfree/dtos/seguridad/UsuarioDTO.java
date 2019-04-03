package adminfree.dtos.seguridad;

import java.io.Serializable;
import java.util.List;

import adminfree.dtos.configuraciones.ClienteDTO;

/**
 * DTO para transportar los datos del usuario del sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del usuario */
	private Long id;

	/** Nombre del usuario */
	private String nombre;

	/** Usuario de ingreso para el USER */
	private String usuarioIngreso;

	/** Clave de ingreso para el USER */
	private String claveIngreso;

	/** Es el cargo del usuario */
	private String cargo;

	/** Estado que se encuentra el USUARIO */
	private Integer estado;

	/** Es el nombre del Estado que se encuentra el USUARIO */
	private String estadoNombre;

	/** Cliente donde pertenece el usuario */
	private ClienteDTO cliente;

	/** Lista de Tokens de modulos asignados al usuario */
	private List<String> modulosTokens;

	/** Indica si el usuario de ingreso fue modificado */
	private boolean userIngresoModificado;

	/**
	 * Metodo que permite obtener el valor del atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo que permite obtener el valor del atributo nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cliente
	 */
	public ClienteDTO getCliente() {
		return cliente;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param cliente
	 */
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	/**
	 * Metodo que permite obtener el valor del atributo modulosTokens
	 */
	public List<String> getModulosTokens() {
		return modulosTokens;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * modulosTokens
	 */
	public void setModulosTokens(List<String> modulosTokens) {
		this.modulosTokens = modulosTokens;
	}

	/**
	 * Metodo que permite obtener el valor del atributo estado
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param estado
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * Metodo que permite obtener el valor del atributo estadoNombre
	 */
	public String getEstadoNombre() {
		return estadoNombre;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * estadoNombre
	 */
	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}

	/**
	 * Metodo que permite obtener el valor del atributo usuarioIngreso
	 */
	public String getUsuarioIngreso() {
		return usuarioIngreso;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * usuarioIngreso
	 */
	public void setUsuarioIngreso(String usuarioIngreso) {
		this.usuarioIngreso = usuarioIngreso;
	}

	/**
	 * Metodo que permite obtener el valor del atributo claveIngreso
	 */
	public String getClaveIngreso() {
		return claveIngreso;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * claveIngreso
	 */
	public void setClaveIngreso(String claveIngreso) {
		this.claveIngreso = claveIngreso;
	}

	/**
	 * Metodo que permite obtener el valor del atributo userIngresoModificado
	 */
	public boolean isUserIngresoModificado() {
		return userIngresoModificado;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * userIngresoModificado
	 */
	public void setUserIngresoModificado(boolean userIngresoModificado) {
		this.userIngresoModificado = userIngresoModificado;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor 
	 * para el atributo @param cargo
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}
