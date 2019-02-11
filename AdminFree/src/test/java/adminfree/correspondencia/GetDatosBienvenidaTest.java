package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.WelcomeInitDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getDatosBienvenida
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDatosBienvenidaTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los campos de la nomenclatura
	 */
	@Test
	public void getDatosBienvenida() {
		try {
			Long idCliente = 1l;

			// se procede a buscar los datos de bienvenida
			WelcomeInitDTO datos = this.correspondenciaService.getDatosBienvenida(idCliente);

			// debe existir nomenclatura e usuarios para este cliente
			assertTrue(
					datos != null &&
					datos.getNomenclaturas() != null &&
					datos.getUsuarios() != null &&
					!datos.getNomenclaturas().isEmpty() &&
					!datos.getUsuarios().isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
