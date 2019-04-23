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
			SubSerieDocumentalDTO crear = new SubSerieDocumentalDTO();
			crear.setTipoEvento(TipoEvento.CREAR);
			crear.setIdCliente(1);
			crear.setIdSerie(9l);
			crear.setCodigo("25.1.2-2");
			crear.setNombre("PETICION PARA PREDIAL");
			crear.setAG(1);
			crear.setAC(0);
			crear.setCT(true);
			crear.setM(true);
			crear.setS(true);
			crear.setE(true);
			crear.setProcedimiento("Este es el procedimiento de la SUB-serie");
			crear.setIdUsuarioCreacion(1);
			this.archivoGestionService.administrarSubSerieDocumental(crear);

			// test para la edicion de la SUB-serie
			SubSerieDocumentalDTO editar = new SubSerieDocumentalDTO();
			editar.setTipoEvento(TipoEvento.EDITAR);
			editar.setIdSubSerie(1l);
			editar.setIdCliente(2);
			editar.setIdSerie(8l);
			editar.setCodigo("25.1.2-2");
			editar.setNombre("PETICION PARA VALORIZACION");
			editar.setAG(null);
			editar.setAC(null);
			editar.setCT(false);
			editar.setM(false);
			editar.setS(false);
			editar.setE(false);
			editar.setProcedimiento("procedimiento");
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
