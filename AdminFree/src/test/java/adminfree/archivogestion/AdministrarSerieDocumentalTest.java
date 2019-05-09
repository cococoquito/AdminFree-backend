package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.FiltroSerieDocumentalDTO;
import adminfree.dtos.archivogestion.SerieDocumentalDTO;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
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
			serie.setCodigo("90.0.462");
			serie.setNombre("HOJAS DE VIDA9");
			serie.setTiempoArchivoGestion(1);
			serie.setTiempoArchivoCentral(0);
			serie.setConservacionTotal(true);
			serie.setMicrofilmacion(true);
			serie.setSeleccion(true);
			serie.setEliminacion(true);
			serie.setProcedimiento("Este es el procedimiento DE HV");
			serie.setIdUsuarioCreacion(null);

			// se agregan los tipos documentales
			TipoDocumentalDTO doc1 = new TipoDocumentalDTO();
			doc1.setNombre("acta de prubas uno");
			TipoDocumentalDTO doc2 = new TipoDocumentalDTO();
			doc2.setNombre("acta de prubas dos");
			TipoDocumentalDTO doc3 = new TipoDocumentalDTO();
			doc3.setId(28);
			TipoDocumentalDTO doc4 = new TipoDocumentalDTO();
			doc4.setId(29);
			serie.agregarTipoDocumental(doc1);
			serie.agregarTipoDocumental(doc2);
			serie.agregarTipoDocumental(doc3);
			serie.agregarTipoDocumental(doc4);
			this.archivoGestionService.administrarSerieDocumental(serie);

			// test para la edicion de la serie
			SerieDocumentalDTO editar = new SerieDocumentalDTO();
			editar.setTipoEvento(TipoEvento.EDITAR);
			editar.setIdCliente(1);
			editar.setIdSerie(1L);
			editar.setCodigo("25.1.1");
			editar.setNombre("PRIMERA SERIE");
			editar.setTiempoArchivoGestion(null);
			editar.setTiempoArchivoCentral(null);
			editar.setConservacionTotal(false);
			editar.setMicrofilmacion(false);
			editar.setSeleccion(false);
			editar.setEliminacion(false);
			editar.setProcedimiento("procedimiento editado");
			editar.agregarTipoDocumental(doc2);
			this.archivoGestionService.administrarSerieDocumental(editar);

			// test para eliminar una serie
			SerieDocumentalDTO eliminar = new SerieDocumentalDTO();
			eliminar.setTipoEvento(TipoEvento.ELIMINAR);
			eliminar.setIdSerie(2L);

			// se construye el filtro de la consulta
			FiltroSerieDocumentalDTO filtro = new FiltroSerieDocumentalDTO();
			filtro.setIdCliente(1l);

			// se construye el paginador de la consulta
			PaginadorDTO paginador = new PaginadorDTO();
			paginador.setSkip("0");
			paginador.setRowsPage("10");
			filtro.setPaginador(paginador);
			eliminar.setFiltro(filtro);
			PaginadorResponseDTO re = (PaginadorResponseDTO)this.archivoGestionService.administrarSerieDocumental(eliminar);
			assertTrue(re != null);

			// si llega a este punto es porque todos los procesos se ejucutaron sin problemas
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
