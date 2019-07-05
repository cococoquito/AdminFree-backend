package adminfree.aws;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contiene los metodos utilitarios para la administracion de los documentos de
 * correspondencia, donde se utiliza la tecnologia de AWS-S3
 * 
 * @author Carlos Andres Diaz
 *
 */
public class AdministracionDocumentosS3 {

	/** Es el nombre del bucket donde se persiste todos los documentos */
	private static final String NOMBRE_BUCKET = "/home/andres/Documents/workspace/docs/";

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
	 * Metodo que permite almacenar un documento dentro del bucket en AWS-S3
	 */
	public synchronized void almacenarDocumento(
			byte[] contenido,
			String idCliente,
			String idConsecutivo,
			String nombreDocumento) throws Exception {

		// se configura el nombre del documento almacenar
		String nombre = getNombreDocumento(idCliente, idConsecutivo, nombreDocumento);

		// Se configura la ruta
		String ruta = NOMBRE_BUCKET + nombre;

		// se almacena en disco
		FileOutputStream fileOutputStream = new FileOutputStream(new File(ruta));
		fileOutputStream.write(contenido);
		fileOutputStream.close();
	}

	/**
	 * Metodo que permite descargar un documento que se encuentra dentro del bucket en AWS-S3
	 */
	public synchronized byte[] descargarDocumento(
			String idCliente,
			String idConsecutivo,
			String nombreDocumento) throws Exception {

		// se configura el nombre del documento a descargar
		String nombre = getNombreDocumento(idCliente, idConsecutivo, nombreDocumento);

		// Se configura la ruta y se procede a obtener los datos del documento
		String ruta = NOMBRE_BUCKET + nombre;
		Path pdfPath = Paths.get(ruta);

		// se retorna el arreglo de bytes del contenido del documento
		return Files.readAllBytes(pdfPath);
	}

	/**
	 * Metodo que permite eliminar un documento dentro del bucket en AWS-S3
	 */
	public synchronized void eliminarDocumento(
			String idCliente,
			String idConsecutivo,
			String nombreDocumento) throws Exception {

		// se configura el nombre del documento eliminar
		String nombre = getNombreDocumento(idCliente, idConsecutivo, nombreDocumento);

		// Se configura la ruta
		String ruta = NOMBRE_BUCKET + nombre;

		// se elimina el documento
		File fichero = new File(ruta);
		fichero.delete();
	}

	/**
	 * Metodo que permite construir el nombre del documento
	 */
	private String getNombreDocumento(
			String idCliente,
			String idConsecutivo,
			String nombreDocumento) {

		// se configura el nombre del documento (idCliente_idConsecutivo_nombreDocumento)
		StringBuilder nombre = new StringBuilder(idCliente);
		nombre.append("_").append(idConsecutivo);
		nombre.append("_").append(nombreDocumento);

		// se retornar el nombre construido
		return nombre.toString();
	}
}
