package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.ConsecutivoEdicionDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getConsecutivoEdicion
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetConsecutivoEdicionTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite consultar y retornar los datos del consecutivo para su edicion
	 */
	@Test
	public void getConsecutivoEdicion() {
		try {
			// se construye el filtro para consultar el consecutivo para su edicion
			ConsecutivoEdicionDTO filtro = new ConsecutivoEdicionDTO();
			filtro.setIdCliente(1L);
			filtro.setIdConsecutivo(1L);

			// se procede consultar el consecutivo para su edicion
			ConsecutivoEdicionDTO consecutivo = this.correspondenciaService.getConsecutivoEdicion(filtro);

			// debe existir el consecutivo
			assertTrue(consecutivo != null &&
					consecutivo.getConsecutivo() != null &&
					consecutivo.getConsecutivo().getConsecutivo() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
