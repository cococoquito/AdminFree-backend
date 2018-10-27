package adminfree.utilities;

import java.io.Serializable;

/**
 * Se utiliza para retornar un solo mensaje al cliente
 *
 * @author Carlos andres diaz
 *
 */
public class ResponseMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el mensaje relacionado al codigo **/
	private String mensaje;

	public ResponseMessage(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Metodo que permite obtener el valor del atributo mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
