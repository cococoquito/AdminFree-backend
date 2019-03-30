package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.ConsecutivoEdicionDTO;
import adminfree.dtos.correspondencia.ConsecutivoEdicionValueDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.editarConsecutivoValores
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EditarConsecutivoValoresTest {

	/** Objecto que contiene los servicios relacionados modulo correspondencia */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite editar los valores de un consecutivo
	 */
	@Test
	public void editarConsecutivoValores() {
		try {
			// se configura los datos necesarios para el proceso
			ConsecutivoEdicionDTO datos = new ConsecutivoEdicionDTO();

			// se hace la invocacion del proceso
			List<ConsecutivoEdicionValueDTO> valores = this.correspondenciaService.editarConsecutivoValores(datos);

			// debe existir los documentos asociados al consecutivo
			assertTrue(valores != null && !valores.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
