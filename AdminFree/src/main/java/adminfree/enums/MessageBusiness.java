package adminfree.enums;

/**
 * Enums que contiene los IDS de los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public enum MessageBusiness {

	/** 400 - El Usuario y la Contraseña que ingresó no ha sido reconocido.*/
	AUTENTICACION_FALLIDA_USER(Numero.UNO.value.toString()),
	
	/** 400 - El Usuario y el Token que ingresó no ha sido reconocido.*/
	AUTENTICACION_FALLIDA_ADMIN(Numero.DOS.value.toString()),	

	/** 401 - No estas autorizado para acceder a este recurso.*/
	AUTORIZACION_FALLIDA(Numero.UNO.value.toString());

	public final String value;

	private MessageBusiness(String value) {
		this.value = value;
	}
}
