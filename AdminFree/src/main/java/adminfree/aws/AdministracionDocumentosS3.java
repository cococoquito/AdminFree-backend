package adminfree.aws;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Contiene los metodos utilitarios para la administracion de los documentos de
 * la aplicacion en AWS S3
 * 
 * @author Carlos Andres Diaz
 *
 */
public class AdministracionDocumentosS3 {

	/** Es el nombre del bucket donde se persiste todos los documentos */
	private static final String NOMBRE_BUCKET = "/home/adminfree/Documents/AdminFree-workspace/docs/";

	/** Unica instancia de este singleton */
	private static AdministracionDocumentosS3 instance;

	/**
	 * Constructor no instanciable, la utilidad es un singleton
	 */
	private AdministracionDocumentosS3() {}

	/**
	 * Permite obtener una unica instancia del singleton
	 */
	public static AdministracionDocumentosS3 getInstance() {
		if (instance == null) {
			instance = new AdministracionDocumentosS3();
		}
		return instance;
	}

	/**
	 * Metodo que permite almacenar un documento dentro del bucket en S3
	 */
	public synchronized void almacenarDocumento(
			byte[] contenido, String nombreDocumento,
			String idCliente, String idConsecutivo) throws Exception {

		// se configura el nombre del documento almacenar
		StringBuilder nombre = new StringBuilder(idCliente);
		nombre.append("_").append(idConsecutivo);
		nombre.append("_").append(nombreDocumento);

		// Se configura la ruta
		String ruta = NOMBRE_BUCKET + nombre;

		// se almacena en disco
		FileOutputStream fileOutputStream = new FileOutputStream(new File(ruta));
		fileOutputStream.write(contenido);
		fileOutputStream.close();
	}

	/**
	 * Metodo que permite eliminar un documento dentro del bucket en S3
	 */
	public synchronized void eliminarDocumento(
			String nombreDocumento,
			String idCliente,
			String idConsecutivo) throws Exception {

		// se configura el nombre del documento almacenar
		StringBuilder nombre = new StringBuilder(idCliente);
		nombre.append("_").append(idConsecutivo);
		nombre.append("_").append(nombreDocumento);

		// Se configura la ruta
		String ruta = NOMBRE_BUCKET + nombre;

		// se elimina el documento
		File fichero = new File(ruta);
		fichero.delete();
	}
}
