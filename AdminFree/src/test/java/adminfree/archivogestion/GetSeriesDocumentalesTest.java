package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.archivogestion.FiltroSerieDocumentalDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.enums.Numero;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.getSeriesDocumentales
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetSeriesDocumentalesTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * Test que permite obtener las series documentales de acuerdo al filtro de busqueda
	 */
	@Test
	public void getSeriesDocumentales() {
		try {
			// se construye el filtro de busqueda
			FiltroSerieDocumentalDTO filtro = new FiltroSerieDocumentalDTO();
			filtro.setCodigoSerieDocumental("25.0.1");
			filtro.setNombreSerieDocumental("ACTAS");
			filtro.setCodigoSubSerieDocumental("25.0.4");
			filtro.setNombreSubSerieDocumental("INFORMES");

			// se construye el paginador de la consulta
			PaginadorDTO paginador = new PaginadorDTO();
			paginador.setSkip("0");
			paginador.setRowsPage("10");
			filtro.setPaginador(paginador);

			// se procede a obtener las series documentales
			PaginadorResponseDTO response = this.archivoGestionService.getSeriesDocumentales(filtro);

			// debe existir series documentales
			assertTrue(
					response != null &&
					response.getCantidadTotal() != null &&
					!response.getCantidadTotal().equals(Numero.ZERO.valueL));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
