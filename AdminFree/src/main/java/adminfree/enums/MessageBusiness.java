package adminfree.enums;

/**
 * Enums que contiene los IDS de los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public enum MessageBusiness {

	/** 400 - El Usuario y la Contraseña que usted ingresó no ha sido reconocido. Por favor, inténtelo de nuevo.*/
	AUTENTICACION_FALLIDA(Numero.UNO.value.toString()),

	/** 400 - No estas autorizado para acceder a este recurso.*/
	AUTORIZACION_FALLIDA(Numero.DOS.value.toString()),

	/** 500 - Se produjo un error en el sistema:*/
	ERROR_TECHNICAL(Numero.UNO.value.toString());

	public String value;

	private MessageBusiness(String value) {
		this.value = value;
	}
}
