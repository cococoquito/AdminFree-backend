package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.getSubSeriesDocumental
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetSubSeriesDocumentalTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * Test que permite obtener las subseries documentales relacionadas a una serie documental
	 */
	@Test
	public void getSubSeriesDocumental() {
		try {
			// identificador de la serie documental
			Long idSerie = 1L;

			// se procede a obtener las subseries documentales asociados a la serie documental
			List<SubSerieDocumentalDTO> response = this.archivoGestionService.getSubSeriesDocumental(idSerie);

			// debe existir las subseries documentales
			assertTrue(response != null && !response.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
