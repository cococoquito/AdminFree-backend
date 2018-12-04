package adminfree.enums;

/**
 * Enums que contiene los IDS de los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public enum MessageBusiness {

	/** 401 - No estas autorizado para acceder a este recurso.*/
	AUTORIZACION_FALLIDA(Numero.UNO.value.toString()),

	/** 400 - El Usuario y la Contraseña que ingresó no ha sido reconocido.*/
	AUTENTICACION_FALLIDA_USER(Numero.UNO.value.toString()),

	/** 400 - El Usuario y el Token que ingresó no ha sido reconocido*/
	AUTENTICACION_FALLIDA_ADMIN(Numero.DOS.value.toString()),

	/** 400 - El valor del usuario de ingreso (?) ya se encuentra asociado a otro usuario*/
	USUARIO_INGRESO_EXISTE(Numero.TRES.value.toString()),

	/** 400 - La contraseña de verificación no coincide*/
	CLAVE_VERIFICACION_NO_COINCIDE(Numero.CUATRO.value.toString()),

	/** 400 - La nueva contrasenia debe tener al menos 12 caracteres*/
	CLAVE_LONGITUD_NO_PERMITIDA(Numero.CINCO.value.toString()),

	/** 400 - La nueva contrasenia no puede contener espacios en blanco*/
	CLAVE_ESPACIOS_BLANCO(Numero.SEIS.value.toString()),

	/** 400 - La contrasenia actual no coincide con la contraseńa del usuario autenticado*/
	CLAVE_NO_COINCIDE(Numero.SIETE.value.toString()),

	/** 400 - La nueva contrasenia debe ser diferente a la contrasenia actual*/
	CLAVE_ACTUAL_IGUAL(Numero.OCHO.value.toString());

	public final String value;

	private MessageBusiness(String value) {
		this.value = value;
	}
}
