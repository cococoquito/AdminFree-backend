package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.archivogestion.InitAdminSeriesDocumentalesDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.getInitAdminSeriesDocumentales
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetInitAdminSeriesDocumentalesTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * Test que permite obtener los datos de inicio para el submodulo de series documentales
	 */
	@Test
	public void getInitAdminSeriesDocumentales() {
		try {
			Long idCliente = 1l;
			InitAdminSeriesDocumentalesDTO response = this.archivoGestionService.getInitAdminSeriesDocumentales(idCliente);

			// debe existir los datos iniciales para el submodulo
			assertTrue(response != null && response.getSeries() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
