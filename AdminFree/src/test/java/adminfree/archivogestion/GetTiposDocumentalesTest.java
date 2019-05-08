package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.getTiposDocumentales
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetTiposDocumentalesTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * Test que permite obtener todos los tipos documentales parametrizados
	 */
	@Test
	public void getTiposDocumentales() {
		try {
			// identificador del cliente a consultar sus tipos documentales
			Long idCliente = 1L;

			// se procede a obtener los tipos documentales asociados al cliente
			List<TipoDocumentalDTO> response = this.archivoGestionService.getTiposDocumentales(idCliente);

			// debe existir los tipos documentales
			assertTrue(response != null && !response.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
