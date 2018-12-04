package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CambioClaveDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.modificarClaveIngreso
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModificarClaveIngresoTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite modificar la clave de ingreso del usuario
	 */
	@Test
	public void modificarClaveIngreso() {
		try {
			// se construye el usuario a modificar su clave
			CambioClaveDTO nuevaClave = new CambioClaveDTO();
			nuevaClave.setIdUsuario(91l);
			nuevaClave.setClaveActual("chichona1234");
			nuevaClave.setClaveNueva("chichona12345");
			nuevaClave.setClaveVerificacion("chichona12345");

			// se procede a modificar los datos de la cuenta
			this.configuracionesService.modificarClaveIngreso(nuevaClave);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
