package adminfree.constants;

/**
 * Clase que contiene los tipos de documentos
 *
 * @author Carlos Andres Diaz
 */
public class TiposDocumentos {

	/** Extension MIME para documentos PDF */
	public static final String PDF = "application/pdf";

	/** Extension MIME para documentos microsoft EXCEL */
	public static final String XLS = "application/vnd.ms-excel";
	public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/** Extension MIME para documentos microsoft WORD */
	public static final String DOC = "application/msword";
	public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

	/** Extension MIME para documentos de OPEN OFFICE */
	public static final String OPEN_DOC = "application/vnd.oasis.opendocument.text";

	/** Extension MIME para documentos de microsoft POWERPOINT */
	public static final String PPT = "application/vnd.ms-powerpoint";
	public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

	/**
	 * Metodo que permite obtener el tipo de documento para ser mostrado en pantalla
	 */
	public static String getTipoDocumento(String tipo) {
		String tipoDocumento = "N/A";
		if (tipo != null) {
			switch (tipo) {
				case PDF:
					tipoDocumento = "PDF";
					break;
	
				case XLS:
					tipoDocumento = "Excel";
					break;
	
				case XLSX:
					tipoDocumento = "Excel";
					break;
	
				case DOC:
					tipoDocumento = "Word";
					break;
	
				case DOCX:
					tipoDocumento = "Word";
					break;
	
				case OPEN_DOC:
					tipoDocumento = "DOC";
					break;
	
				case PPT:
					tipoDocumento = "PowerPoint";
					break;
	
				case PPTX:
					tipoDocumento = "PowerPoint";
					break;
			}
		}
		return tipoDocumento;
	}
}
