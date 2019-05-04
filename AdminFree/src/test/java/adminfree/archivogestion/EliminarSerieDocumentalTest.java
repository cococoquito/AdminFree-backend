package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.archivogestion.FiltroSerieDocumentalDTO;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.eliminarSerieDocumental
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EliminarSerieDocumentalTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * Test que permite eliminar una serie documental en el sistema
	 */
	@Test
	public void eliminarSerieDocumental() {
		try {
			// se crea la serie a eliminar
			SerieDocumentalDTO serie = new SerieDocumentalDTO();
			serie.setIdSerie(6L);

			// se construye el filtro de la consulta
			FiltroSerieDocumentalDTO filtro = new FiltroSerieDocumentalDTO();
			filtro.setIdCliente(2l);

			// se construye el paginador de la consulta
			PaginadorDTO paginador = new PaginadorDTO();
			paginador.setSkip("0");
			paginador.setRowsPage("10");
			filtro.setPaginador(paginador);
			serie.setFiltro(filtro);

			// se procede eliminar la serie
			PaginadorResponseDTO response = this.archivoGestionService.eliminarSerieDocumental(serie);

			// debe existir el response
			assertTrue(response != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
