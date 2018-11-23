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

	/** Lista de Tokens de modulos asignados al usuario */
	private List<String> modulosTokens;

	/**
	 * Metodo que permite agregar un MODULO para este USUARIO
	 */
	public void agregarModuloToken(String moduloToken) {
		if (this.modulosTokens == null) {
			this.modulosTokens = new ArrayList<>();
		}
		this.modulosTokens.add(moduloToken);
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
}
