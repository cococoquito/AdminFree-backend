package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.administrarSerieDocumental
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministrarSerieDocumentalTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * test que permite administrar la entidad de series documentales
	 */
	@Test
	public void administrarSerieDocumental() {
		try {
			// test para la creacion de la serie
			SerieDocumentalDTO serie = new SerieDocumentalDTO();
			serie.setTipoEvento(TipoEvento.CREAR);
			serie.setIdCliente(1);
			serie.setCodigo("25.2.1");
			serie.setNombre("ACTAS");
			serie.setAG(1);
			serie.setAC(0);
			serie.setCT(true);
			serie.setM(true);
			serie.setS(true);
			serie.setE(true);
			serie.setProcedimiento("Este es el procedimiento de la serie DE ACTAS");
			serie.setIdUsuarioCreacion(1);
			this.archivoGestionService.administrarSerieDocumental(serie);

			// test para la edicion de la serie
			SerieDocumentalDTO editar = new SerieDocumentalDTO();
			editar.setTipoEvento(TipoEvento.EDITAR);
			editar.setIdCliente(1);
			editar.setIdSerie(1L);
			editar.setCodigo("25.1.1");
			editar.setNombre("PRIMERA SERIE");
			editar.setAG(null);
			editar.setAC(null);
			editar.setCT(false);
			editar.setM(false);
			editar.setS(false);
			editar.setE(false);
			editar.setProcedimiento("procedimiento editado");
			this.archivoGestionService.administrarSerieDocumental(editar);

			// test para eliminar una serie
			SerieDocumentalDTO eliminar = new SerieDocumentalDTO();
			eliminar.setTipoEvento(TipoEvento.ELIMINAR);
			eliminar.setIdSerie(1L);
			this.archivoGestionService.administrarSerieDocumental(eliminar);

			// si llega a este punto es porque todos los procesos se ejucutaron sin problemas
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
