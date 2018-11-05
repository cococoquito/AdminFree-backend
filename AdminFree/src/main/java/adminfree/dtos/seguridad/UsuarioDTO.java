package adminfree.dtos.seguridad;

import java.io.Serializable;
import java.util.ArrayList;
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

	/** Valor del usuario por la cual el usuario ingresa a la app */
	private String usuarioIngreso;

	/** Clave del usuario por la cual el usuario ingresa a la app */
	private String claveIngreso;

	/** Cliente donde pertenece el usuario */
	private ClienteDTO cliente;

	/** Lista de ROLES que tiene el usuario */
	private List<RolDTO> roles;

	/**
	 * Metodo que permite agregar un ROL para este USUARIO
	 */
	public void agregarROL(RolDTO rol) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		this.roles.add(rol);
	}

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
	 * Metodo que permite obtener el valor del atributo roles
	 */
	public List<RolDTO> getRoles() {
		return roles;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param roles
	 */
	public void setRoles(List<RolDTO> roles) {
		this.roles = roles;
	}
}
