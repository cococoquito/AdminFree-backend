package adminfree.utilities;

import java.io.Serializable;

/**
 * Se utiliza para recibir un solo mensaje del servidor
 *
 * @author Carlos Andres Diaz
 */
public class MessageResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Cuando el error es un Internal server error, 
	 * El mensaje es la descripcion de la exception.
	 *
	 * Cuando el error es provocado por un business exeption,
	 * El mensaje contiene el codigo que representa el mensaje de business error
	 *
	 */
	private String mensaje;
	
	public MessageResponse(String mensaje) {
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
