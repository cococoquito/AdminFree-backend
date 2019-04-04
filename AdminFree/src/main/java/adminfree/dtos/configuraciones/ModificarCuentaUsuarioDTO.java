package adminfree.dtos.configuraciones;

import java.io.Serializable;

import adminfree.dtos.seguridad.UsuarioDTO;

/**
 * DTO para el proceso de negocio de cambiar la cuenta de usuario esto aplica
 * para datos personales, usuario ingreso o contrasenia
 *
 * @author Carlos Andres Diaz
 *
 */
public class ModificarCuentaUsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** DTO que contiene los datos personales a modificar **/
	private UsuarioDTO datosPersonales;

	/** DTO que contiene los datos para el cambio de la contrasenia **/
	private CambioClaveDTO cambioClave;

	/** DTO que contiene los datos para el cambio del usuario de ingreso **/
	private CambioUsuarioIngresoDTO cambioUsuario;

	/**
	 * Metodo que permite obtener el valor del atributo datosPersonales
	 */
	public UsuarioDTO getDatosPersonales() {
		return datosPersonales;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cambioClave
	 */
	public CambioClaveDTO getCambioClave() {
		return cambioClave;
	}

	/**
	 * Metodo que permite obtener el valor del atributo cambioUsuario
	 */
	public CambioUsuarioIngresoDTO getCambioUsuario() {
		return cambioUsuario;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * datosPersonales
	 */
	public void setDatosPersonales(UsuarioDTO datosPersonales) {
		this.datosPersonales = datosPersonales;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * cambioClave
	 */
	public void setCambioClave(CambioClaveDTO cambioClave) {
		this.cambioClave = cambioClave;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param
	 * cambioUsuario
	 */
	public void setCambioUsuario(CambioUsuarioIngresoDTO cambioUsuario) {
		this.cambioUsuario = cambioUsuario;
	}
}
