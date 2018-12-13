package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.eliminarCampoEntrada
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EliminarCampoEntradaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que soporta el proceso de negocio para la eliminacion de un campo de entrada
	 */
	@Test
	public void eliminarCampoEntrada() {
		try {
			// se elimina el campo
			Long idCampo = 1l;
			this.configuracionesService.eliminarCampoEntrada(idCampo);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
