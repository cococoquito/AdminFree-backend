package adminfree.enums;

/**
 * Enums que contiene los KEYS de los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public enum MessagesKey {

	/** 401 - No estas autorizado para acceder a este recurso.*/
	KEY_AUTORIZACION_FALLIDA(Numero.UNO.valueI.toString()),

	/** 400 - El Usuario y la Contraseña que ingresó no ha sido reconocido.*/
	KEY_AUTENTICACION_FALLIDA_USER(Numero.UNO.valueI.toString()),

	/** 400 - El Usuario y el Token que ingresó no ha sido reconocido*/
	KEY_AUTENTICACION_FALLIDA_ADMIN(Numero.DOS.valueI.toString()),

	/** 400 - El valor del usuario de ingreso (?) ya se encuentra asociado a otro usuario*/
	KEY_USUARIO_INGRESO_EXISTE(Numero.TRES.valueI.toString()),

	/** 400 - La contraseña de verificación no coincide*/
	KEY_CLAVE_VERIFICACION_NO_COINCIDE(Numero.CUATRO.valueI.toString()),

	/** 400 - La nueva contrasenia debe tener al menos 12 caracteres*/
	KEY_CLAVE_LONGITUD_NO_PERMITIDA(Numero.CINCO.valueI.toString()),

	/** 400 - La nueva contrasenia no puede contener espacios en blanco*/
	KEY_CLAVE_ESPACIOS_BLANCO(Numero.SEIS.valueI.toString()),

	/** 400 - La contrasenia actual no coincide con la contraseńa del usuario autenticado*/
	KEY_CLAVE_NO_COINCIDE(Numero.SIETE.valueI.toString()),

	/** 400 - La nueva contrasenia debe ser diferente a la contrasenia actual*/
	KEY_CLAVE_ACTUAL_IGUAL(Numero.OCHO.valueI.toString()),

	/** 400 - El usuario de ingreso debe tener al menos 10 caracteres*/
	KEY_USER_INGRESO_LONGITUD_NO_PERMITIDA(Numero.NUEVE.valueI.toString()),

	/** 400 - El usuario de ingreso no puede contener espacios en blanco*/
	KEY_USER_INGRESO_ESPACIOS_BLANCO(Numero.DIEZ.valueI.toString()),

	/** 400 - Ya existe un campo de entrada de informacion con el mismo tipo y nombre*/
	KEY_EXISTE_CAMPO_ENTRADA(Numero.ONCE.valueI.toString()),

	/** 400 - El campo de entrada de información que intenta eliminar tiene una nomenclatura asociada*/
	KEY_DELETE_CAMPO_NOMENCLATURA_ASOCIADA(Numero.DOCE.valueI.toString()),

	/** 400 - La nomenclatura ? ya se encuentra registrada en el sistema*/
	KEY_NOMENCLATURA_EXISTE(Numero.TRECE.valueI.toString()),

	/** 400 - La nomenclatura que intenta eliminar tiene un consecutivo asociado*/
	KEY_DELETE_NOMENCLATURA_CONSECUTIVO_ASOCIADA(Numero.CATORCE.valueI.toString()),

	/** 400 - El consecutivo ? ya tiene asociado un documento con el nombre ?*/
	KEY_CONSECUTIVO_DOCUMENTO_MISMO_NOMBRE(Numero.QUINCE.valueI.toString()),

	/** 400 - El documento que intenta cargar se encuentra vacio*/
	KEY_DOCUMENTO_VACIO(Numero.DIECISEIS.valueI.toString()),

	/** 400 - El documento que intenta descargar no existe en el sistema*/
	KEY_DOCUMENTO_NO_EXISTE(Numero.DIECISIETE.valueI.toString()),

	/** 400 - El nuevo estado no es permitido para el consecutivo debe ser ACTIVO o ANULADO*/
	KEY_ESTADO_NO_PERMITIDO(Numero.DIECIOCHO.valueI.toString()),

	/** 400 - EL proceso no se ejecutó satisfactoriamente, por favor inténtalo de nuevo*/
	KEY_PROCESO_NO_EJECUTADO(Numero.DIECINUEVE.valueI.toString()),

	/** 400 - EL tipo documental que intenta eliminar se encuentra asociado a una serie o subserie documental*/
	KEY_ELIMINAR_TIPO_DOCUMENTAL(Numero.VEINTE.valueI.toString()),

	/** 400 - Ya existe una serie documental con el nombre ?*/
	KEY_SERIE_MISMO_NOMBRE(Numero.VEINTEUNO.valueI.toString()),

	/** 400 - Ya existe una serie documental con el codigo ?*/
	KEY_SERIE_MISMO_CODIGO(Numero.VEINTEDOS.valueI.toString()),

	/** 400 - La serie documental que intenta eliminar tiene asociado consecutivos de correspondencia*/
	KEY_SERIE_CONSECUTIVOS(Numero.VEINTETRES.valueI.toString()),

	/** 400 - La serie documental que intenta eliminar esta asociado en la TRD*/
	KEY_SERIE_TRD(Numero.VEINTECUATRO.valueI.toString()),

	/** 400 - Para eliminar la serie documental debe eliminar primero las subseries relacionadas*/
	KEY_SERIE_TIENE_SUBSERIE(Numero.VEINTECINCO.valueI.toString()),

	/** 400 - Ya existe una subserie documental con el nombre ?*/
	KEY_SUBSERIE_MISMO_NOMBRE(Numero.VEINTESEIS.valueI.toString()),

	/** 400 - Ya existe una subserie documental con el codigo ?*/
	KEY_SUBSERIE_MISMO_CODIGO(Numero.VEINTESIETE.valueI.toString()),

	/** 400 - La subserie documental que intenta eliminar tiene asociado consecutivos de correspondencia*/
	KEY_SUBSERIE_CONSECUTIVOS(Numero.VEINTEOCHO.valueI.toString()),

	/** 400 - La subserie documental que intenta eliminar esta asociado en la TRD*/
	KEY_SUBSERIE_TRD(Numero.VEINTENUEVE.valueI.toString());

	public final String value;

	private MessagesKey(String value) {
		this.value = value;
	}
}
