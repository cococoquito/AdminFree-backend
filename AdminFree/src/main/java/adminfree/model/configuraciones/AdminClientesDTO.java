package adminfree.model.configuraciones;

import java.io.Serializable;
import java.util.List;

/**
 * DTO para transportar los datos iniciales del modulo administrar clientes
 * 
 * @author Carlos Andres Diaz
 *
 */
public class AdminClientesDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** DTO con los datos de la autenticacion */
	private AutenticacionDTO credenciales;

	/** Lista de clientes parametrizados en el sistema */
	private List<ClienteDTO> clientes;

	/**
	 * Metodo que permite obtener el valor del atributo clientes
	 */
	public List<ClienteDTO> getClientes() {
		return clientes;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param clientes
	 */
	public void setClientes(List<ClienteDTO> clientes) {
		this.clientes = clientes;
	}

	/**
	 * Metodo que permite obtener el valor del atributo credenciales
	 */
	public AutenticacionDTO getCredenciales() {
		return credenciales;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * credenciales
	 */
	public void setCredenciales(AutenticacionDTO credenciales) {
		this.credenciales = credenciales;
	}
}
