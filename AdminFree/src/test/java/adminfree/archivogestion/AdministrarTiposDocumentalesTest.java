package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.administrarTiposDocumentales
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministrarTiposDocumentalesTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * Test que permite administrar los tipos documentales
	 */
	@Test
	public void administrarTiposDocumentales() {
		try {
			// test para crear un tipo documental
			TipoDocumentalDTO crear = new TipoDocumentalDTO();
			crear.setTipoEvento(TipoEvento.CREAR);
			crear.setNombre("nuevo tipo documental");
			this.archivoGestionService.administrarTiposDocumentales(crear);

			// test para editar un tipo documental
			TipoDocumentalDTO editar = new TipoDocumentalDTO();
			editar.setTipoEvento(TipoEvento.EDITAR);
			editar.setId(1);
			editar.setNombre("editado");
			this.archivoGestionService.administrarTiposDocumentales(editar);

			// test para eliminar un tipo documental
			TipoDocumentalDTO eliminar = new TipoDocumentalDTO();
			eliminar.setTipoEvento(TipoEvento.ELIMINAR);
			eliminar.setId(1);
			this.archivoGestionService.administrarTiposDocumentales(eliminar);

			// si llega a este punto es porque todos los procesos se ejucutaron sin problemas
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
