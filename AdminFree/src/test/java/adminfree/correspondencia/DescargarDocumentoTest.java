package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.descargarDocumento
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DescargarDocumentoTest {

	/** Objecto que contiene los servicios relacionados modulo correspondencia */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test para la descarga de un documento de correspondencia
	 */
	@Test
	public void descargarDocumento() {
		try {
			// identificadores necesarios para la descarga
			Integer idCliente = 1;
			Long idDocumento = 1l;

			// se procede a descargar el documento
			DocumentoDTO documento = this.correspondenciaService.descargarDocumento(idCliente, idDocumento);

			// debe existir el documento
			assertTrue(documento != null && documento.getContenido().length > 0);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
