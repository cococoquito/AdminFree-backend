package adminfree.utilities;

/**
 * Clase constante que contiene los Mensajes del negocio
 *
 * @author Carlos andres diaz
 *
 */
public class ConstantsCodigoMessages {

	/************* CODIGOS PARA LOS ERRORES DE NEGOCIO, RESPONSE ERROR 400 *************/
	/** 01 - El Usuario y la Contraseña que usted ingresó no ha sido reconocido. Por favor, inténtelo de nuevo. */
	public static final String COD_AUTENTICACION_FALLIDA = "1";
	/** 02 - No estas autorizado para acceder a este recurso. */
	public static final String COD_AUTORIZACION_FALLIDA = "2";
	
	/************* CODIGOS PARA LOS ERRORES TECNICO, RESPONSE ERROR 500 ****************/
	/** 01 - Se produjo un error en el sistema: */
	public static final String COD_ERROR_TECHNICAL = "1";
}