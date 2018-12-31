package adminfree.enums;

/**
 * Enums que contiene los KEYS de los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public enum MessagesKey {

	/** 401 - No estas autorizado para acceder a este recurso.*/
	KEY_AUTORIZACION_FALLIDA(Numero.UNO.value.toString()),

	/** 400 - El Usuario y la Contraseña que ingresó no ha sido reconocido.*/
	KEY_AUTENTICACION_FALLIDA_USER(Numero.UNO.value.toString()),

	/** 400 - El Usuario y el Token que ingresó no ha sido reconocido*/
	KEY_AUTENTICACION_FALLIDA_ADMIN(Numero.DOS.value.toString()),

	/** 400 - El valor del usuario de ingreso (?) ya se encuentra asociado a otro usuario*/
	KEY_USUARIO_INGRESO_EXISTE(Numero.TRES.value.toString()),

	/** 400 - La contraseña de verificación no coincide*/
	KEY_CLAVE_VERIFICACION_NO_COINCIDE(Numero.CUATRO.value.toString()),

	/** 400 - La nueva contrasenia debe tener al menos 12 caracteres*/
	KEY_CLAVE_LONGITUD_NO_PERMITIDA(Numero.CINCO.value.toString()),

	/** 400 - La nueva contrasenia no puede contener espacios en blanco*/
	KEY_CLAVE_ESPACIOS_BLANCO(Numero.SEIS.value.toString()),

	/** 400 - La contrasenia actual no coincide con la contraseńa del usuario autenticado*/
	KEY_CLAVE_NO_COINCIDE(Numero.SIETE.value.toString()),

	/** 400 - La nueva contrasenia debe ser diferente a la contrasenia actual*/
	KEY_CLAVE_ACTUAL_IGUAL(Numero.OCHO.value.toString()),

	/** 400 - El usuario de ingreso debe tener al menos 10 caracteres*/
	KEY_USER_INGRESO_LONGITUD_NO_PERMITIDA(Numero.NUEVE.value.toString()),

	/** 400 - El usuario de ingreso no puede contener espacios en blanco*/
	KEY_USER_INGRESO_ESPACIOS_BLANCO(Numero.DIEZ.value.toString()),

	/** 400 - Ya existe un campo de entrada de informacion con el mismo tipo y nombre*/
	KEY_EXISTE_CAMPO_ENTRADA(Numero.ONCE.value.toString()),

	/** 400 - El campo de entrada de información que intenta eliminar tiene una nomenclatura asociada*/
	KEY_DELETE_CAMPO_NOMENCLATURA_ASOCIADA(Numero.DOCE.value.toString()),

	/** 400 - La nomenclatura 'DA' ya se encuentra registrada en el sistema*/
	KEY_NOMENCLAUTARA_EXISTE(Numero.TRECE.value.toString()),

	/** 400 - La nomenclatura que intenta eliminar tiene un consecutivo asociado*/
	KEY_DELETE_NOMENCLATURA_CONSECUTIVO_ASOCIADA(Numero.CATORCE.value.toString());

	public final String value;

	private MessagesKey(String value) {
		this.value = value;
	}
}
