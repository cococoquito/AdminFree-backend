package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
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
			// es el usuario a generar la nueva clave de ingreso
			UsuarioDTO user = new UsuarioDTO();
			user.setId(1L);

			// se procede a generar la nueva clave de ingreso
			CredencialesDTO credenciales = this.configuracionesService.generarClaveIngreso(user);

			// el servicio debe retornar una nueva clave de ingreso
			assertTrue(
					credenciales != null &&
					credenciales.getClave() != null &&
					credenciales.getClave().length() > 20);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
