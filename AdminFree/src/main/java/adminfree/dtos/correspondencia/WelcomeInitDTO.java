package adminfree.dtos.correspondencia;

import java.io.Serializable;
import java.util.List;

/**
 * DTO para transportar los datos para la pagina de bienvenida de la aplicacion
 * 
 * @author Carlos Andres Diaz
 */
public class WelcomeInitDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Son las nomenclaturas a mostrar en la pagina bienvenida */
	private List<WelcomeNomenclaturaDTO> nomenclaturas;

	/** Son los usuarios a mostrar en la pagina bienvenida */
	private List<WelcomeUsuarioDTO> usuarios;

	/**
	 * Metodo que permite obtener el valor del atributo nomenclaturas
	 */
	public List<WelcomeNomenclaturaDTO> getNomenclaturas() {
		return nomenclaturas;
	}

	/**
	 * Metodo que permite obtener el valor del atributo usuarios
	 */
	public List<WelcomeUsuarioDTO> getUsuarios() {
		return usuarios;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * nomenclaturas
	 */
	public void setNomenclaturas(List<WelcomeNomenclaturaDTO> nomenclaturas) {
		this.nomenclaturas = nomenclaturas;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param usuarios
	 */
	public void setUsuarios(List<WelcomeUsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}
}
