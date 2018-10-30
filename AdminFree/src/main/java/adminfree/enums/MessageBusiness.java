package adminfree.enums;

/**
 * Enums que contiene los IDS de los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public enum MessageBusiness {

	/** 400 - El Usuario y la Contraseña que usted ingresó no ha sido reconocido. Por favor, inténtelo de nuevo.*/
	AUTENTICACION_FALLIDA_400(Numero.UNO.value.toString()),

	/** 401 - No estas autorizado para acceder a este recurso.*/
	AUTORIZACION_FALLIDA_401(Numero.UNO.value.toString());

	public final String value;

	private MessageBusiness(String value) {
		this.value = value;
	}
}
