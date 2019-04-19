package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.enums.Numero;
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
			serie.setCodigo("25.1.2");
			serie.setNombre("DERECHO DE PETICION");
			serie.setAG(1);
			serie.setAC(0);
			serie.setCT(null);
			serie.setM(1);
			serie.setS(0);
			serie.setE(null);
			serie.setProcedimiento("Este es el procedimiento de la serie");
			serie.setIdUsuarioCreacion(1);
			this.archivoGestionService.administrarSerieDocumental(serie);

			// test para la edicion de la serie
			SerieDocumentalDTO editar = new SerieDocumentalDTO();
			editar.setTipoEvento(TipoEvento.EDITAR);
			editar.setIdSerie(1L);
			editar.setNombre("PEPITO PEREZ");
			this.archivoGestionService.administrarSerieDocumental(editar);

			// test para consultar las series
			SerieDocumentalDTO listar = new SerieDocumentalDTO();
			listar.setTipoEvento(TipoEvento.LISTAR);
			PaginadorResponseDTO response = (PaginadorResponseDTO) this.archivoGestionService.administrarSerieDocumental(listar);

			// test para eliminar una serie
			SerieDocumentalDTO eliminar = new SerieDocumentalDTO();
			eliminar.setTipoEvento(TipoEvento.ELIMINAR);
			eliminar.setIdSerie(1L);
			this.archivoGestionService.administrarSerieDocumental(eliminar);

			// debe existir series documentales
			assertTrue(response != null && !Numero.ZERO.valueL.equals(response.getCantidadTotal()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
