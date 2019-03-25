package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.DocumentoDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.eliminarDocumento
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EliminarDocumentoTest {

	/** Objecto que contiene los servicios relacionados modulo correspondencia */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test para eliminar un documento asociado al consecutivo
	 */
	@Test
	public void eliminarDocumento() {
		try {
			// se configura los datos del cargue
			DocumentoDTO datos = new DocumentoDTO();
			datos.setIdCliente(1);
			datos.setId(1L);

			// se hace la invocacion del cargue
			List<DocumentoDTO> documentos = this.correspondenciaService.eliminarDocumento(datos);

			// debe existir los documentos asociados al consecutivo
			assertTrue(documentos != null && !documentos.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
