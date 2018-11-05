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

	/** Cliente donde pertenece el usuario */
	private ClienteDTO cliente;

	/** Lista de ROLES que tiene el usuario */
	private List<RolDTO> roles;

	/** Son las credenciales del USUARIO */
	private CredencialesDTO credenciales;

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

	/**
	 * Metodo que permite obtener el valor del atributo credenciales
	 */
	public CredencialesDTO getCredenciales() {
		return credenciales;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * credenciales
	 */
	public void setCredenciales(CredencialesDTO credenciales) {
		this.credenciales = credenciales;
	}
}
