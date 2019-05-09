package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.dtos.archivogestion.TipoDocumentalDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.administrarSubSerieDocumental
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("unchecked")
public class AdministrarSubSerieDocumentalTest {

	/** Objecto que contiene los servicios relacionados modulo archivo gestion */
	@Autowired
	private ArchivoGestionService archivoGestionService;

	/**
	 * test que permite administrar la entidad de sub-serie documental
	 */
	@Test
	public void administrarSubSerieDocumental() {
		try {
			// test para la creacion de la serie
			SubSerieDocumentalDTO crear = new SubSerieDocumentalDTO();
			crear.setTipoEvento(TipoEvento.CREAR);
			crear.setIdCliente(1);
			crear.setIdSerie(9l);
			crear.setCodigo("25.1.2-2");
			crear.setNombre("PETICION PARA PREDIAL");
			crear.setTiempoArchivoGestion(1);
			crear.setTiempoArchivoCentral(0);
			crear.setConservacionTotal(true);
			crear.setMicrofilmacion(true);
			crear.setSeleccion(true);
			crear.setEliminacion(true);
			crear.setProcedimiento("Este es el procedimiento de la SUB-serie");
			crear.setIdUsuarioCreacion(1);

			// se agregan los tipos documentales
			TipoDocumentalDTO doc1 = new TipoDocumentalDTO();
			doc1.setId(1);
			TipoDocumentalDTO doc2 = new TipoDocumentalDTO();
			doc2.setId(5);
			TipoDocumentalDTO doc3 = new TipoDocumentalDTO();
			doc3.setId(3);
			crear.agregarTipoDocumental(doc1);
			crear.agregarTipoDocumental(doc2);
			crear.agregarTipoDocumental(doc3);
			this.archivoGestionService.administrarSubSerieDocumental(crear);

			// test para la edicion de la SUB-serie
			SubSerieDocumentalDTO editar = new SubSerieDocumentalDTO();
			editar.setTipoEvento(TipoEvento.EDITAR);
			editar.setIdSubSerie(1l);
			editar.setIdCliente(2);
			editar.setIdSerie(8l);
			editar.setCodigo("25.1.2-2");
			editar.setNombre("PETICION PARA VALORIZACION");
			editar.setTiempoArchivoGestion(null);
			editar.setTiempoArchivoCentral(null);
			editar.setConservacionTotal(false);
			editar.setMicrofilmacion(false);
			editar.setSeleccion(false);
			editar.setEliminacion(false);
			editar.setProcedimiento("procedimiento");
			editar.agregarTipoDocumental(doc2);
			this.archivoGestionService.administrarSubSerieDocumental(editar);

			// test para eliminar una SUB-serie
			SubSerieDocumentalDTO eliminar = new SubSerieDocumentalDTO();
			eliminar.setTipoEvento(TipoEvento.ELIMINAR);
			eliminar.setIdSubSerie(1l);
			eliminar.setIdSerie(1L);
			List<SubSerieDocumentalDTO> response =
					(List<SubSerieDocumentalDTO>)this.archivoGestionService.administrarSubSerieDocumental(eliminar);
			assertTrue(response != null);

			// si llega a este punto es porque todos los procesos se ejecutaron sin problemas
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
