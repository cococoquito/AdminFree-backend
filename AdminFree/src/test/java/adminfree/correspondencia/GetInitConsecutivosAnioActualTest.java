package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.InitConsecutivosAnioActualDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getInitConsecutivosAnioActual
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetInitConsecutivosAnioActualTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los datos iniciales para el submodulo de
	 * Consecutivos de correspondencia solicitados para el anio actual
	 */
	@Test
	public void getInitConsecutivosAnioActual() {
		try {
			Long idCliente = 1l;

			// se procede a invocar el servicio para obtener los datos del init
			InitConsecutivosAnioActualDTO init = this.correspondenciaService.getInitConsecutivosAnioActual(idCliente);

			// debe existir los consecutivos
			assertTrue(init != null && !init.getConsecutivos().isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
