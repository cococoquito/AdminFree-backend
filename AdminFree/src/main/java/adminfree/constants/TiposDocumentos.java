package adminfree.constants;

/**
 * Clase que contiene los tipos de documentos
 *
 * @author Carlos Andres Diaz
 */
public class TiposDocumentos {

	/**
	 * Tipos de documentos en formato tecnico del componente cargue archivo
	 */
	public static final String T_PDF = "application/pdf";
	public static final String T_WORD = "application/msword";
	public static final String T_EXCEL = "application/vnd.ms-excel";
	public static final String T_DOC = "application/vnd.oasis.opendocument.text";

	/**
	 * Metodo que permite obtener el tipo de documento para ser mostrado en pantalla
	 */
	public static String getTipoDocumento(String tipo) {
		String tipoDocumento = "N/A";
		if (tipo != null) {
			switch (tipo) {
				case T_PDF:
					tipoDocumento = "PDF";
					break;
	
				case T_WORD:
					tipoDocumento = "WORD";
					break;
	
				case T_EXCEL:
					tipoDocumento = "EXCEL";
					break;
	
				case T_DOC:
					tipoDocumento = "DOC";
					break;
			}
		}
		return tipoDocumento;
	}
}
