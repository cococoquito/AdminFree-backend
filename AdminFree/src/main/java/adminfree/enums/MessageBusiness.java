package adminfree.enums;

/**
 * Enums que contiene los IDS de los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public enum MessageBusiness {

	/** 400 - El Usuario y la Contraseña que usted ingresó no ha sido reconocido. Por favor, inténtelo de nuevo.*/
	AUTENTICACION_FALLIDA(Numero.UNO.getValor().toString()),

	/** 400 - No estas autorizado para acceder a este recurso.*/
	AUTORIZACION_FALLIDA(Numero.DOS.getValor().toString()),

	/** 500 - Se produjo un error en el sistema:*/
	ERROR_TECHNICAL(Numero.UNO.getValor().toString());

	private String valor;

	private MessageBusiness(String valor) {
		this.valor = valor;
	}

	/**
	 * Metodo que permite obtener el valor del atributo code
	 */
	public String getValor() {
		return valor;
	}
}
