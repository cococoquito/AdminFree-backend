package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.GenerarTokenIngresoDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.generarClaveIngreso
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerarClaveIngresoTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite generar una nueva clave de ingreso
	 */
	@Test
	public void generarClaveIngreso() {
		try {
			// es el usuario o cliente a generar la nueva clave de ingreso
			GenerarTokenIngresoDTO parametro = new GenerarTokenIngresoDTO();
			parametro.setIdCliente(1);
			parametro.setIdUsuario(1);

			// se procede a generar la nueva clave de ingreso
			parametro = this.configuracionesService.generarClaveIngreso(parametro);

			// el servicio debe retornar una nueva clave de ingreso
			assertTrue(parametro != null && parametro.getToken() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
