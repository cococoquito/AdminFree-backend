package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.CampoFiltroDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getCamposFiltro
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetCamposFiltroTest {

	/** Objecto que contiene los servicios relacionados modulo correspondencia */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los campos para los filtros de busqueda
	 */
	@Test
	public void getCamposFiltro() {
		try {
			// identificador del cliente
			Long idCliente = 1L;

			// se hace la invocacion para obtener los campos
			List<CampoFiltroDTO> campos = this.correspondenciaService.getCamposFiltro(idCliente);

			// debe existir los campos asociado al cliente
			assertTrue(campos != null && !campos.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
