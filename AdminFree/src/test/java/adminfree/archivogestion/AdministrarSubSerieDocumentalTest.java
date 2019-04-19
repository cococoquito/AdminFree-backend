package adminfree.archivogestion;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.TipoEvento;
import adminfree.dtos.archivogestion.SubSerieDocumentalDTO;
import adminfree.services.ArchivoGestionService;

/**
 * Test para el servicio ArchivoGestionService.administrarSubSerieDocumental
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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
			SubSerieDocumentalDTO subserie = new SubSerieDocumentalDTO();
			subserie.setTipoEvento(TipoEvento.CREAR);
			subserie.setCodigo("25.1.2-1");
			subserie.setNombre("PETICION PARA VALORIZACION");
			subserie.setAG(1);
			subserie.setAC(0);
			subserie.setCT(null);
			subserie.setM(1);
			subserie.setS(0);
			subserie.setE(null);
			subserie.setProcedimiento("Este es el procedimiento de la SUB-serie");
			subserie.setIdUsuarioCreacion(1);
			this.archivoGestionService.administrarSubSerieDocumental(subserie);

			// test para la edicion de la SUB-serie
			SubSerieDocumentalDTO editar = new SubSerieDocumentalDTO();
			editar.setTipoEvento(TipoEvento.EDITAR);
			editar.setIdSubSerie(1l);
			editar.setNombre("PETICION PARA VALORIZACION EDITADO");
			this.archivoGestionService.administrarSubSerieDocumental(editar);

			// test para eliminar una serie
			SubSerieDocumentalDTO eliminar = new SubSerieDocumentalDTO();
			eliminar.setTipoEvento(TipoEvento.ELIMINAR);
			eliminar.setIdSubSerie(1l);
			this.archivoGestionService.administrarSubSerieDocumental(eliminar);

			// si llega a este punto es porque todos los procesos se ejecutaron sin problemas
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
