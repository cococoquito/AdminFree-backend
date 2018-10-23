package adminfree.utilities;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

/**
 * Clase que contiene los response comunes del sistema
 *
 * @author Carlos andres diaz
 *
 */
public class CommonResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el codigo del response **/
	private int codigo;

	/** Es el mensaje relacionado al codigo **/
	private String mensaje;

	public CommonResponse(int codigo, String mensaje) {
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	/**
	 * Metodo que permite crear un mensaje de exitoso
	 */
	public static CommonResponse getResponseSuccess() {
		return new CommonResponse(
				HttpStatus.OK.value(),
				HttpStatus.OK.getReasonPhrase());
	}

	/**
	 * Metodo que permite crear un mensaje de error business
	 */
	public static CommonResponse getResponseBusiness(String errorBusiness) {
		return new CommonResponse(
				HttpStatus.BAD_REQUEST.value(),
				errorBusiness);
	}

	/**
	 * Metodo que permite crear un mensaje de internal sever error
	 */
	public static CommonResponse getResponseError(String errorTechnical) {
		return new CommonResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				errorTechnical);
	}

	/**
	 * Metodo que permite obtener el valor del atributo codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Metodo que permite configurar el nuevo valor para el atributo @param codigo
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
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
