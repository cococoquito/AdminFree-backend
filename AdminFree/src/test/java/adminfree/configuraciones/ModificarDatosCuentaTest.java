package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.modificarDatosCuenta
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModificarDatosCuentaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite modificar los datos de la cuenta de un Usuario
	 */
	@Test
	public void modificarDatosCuenta() {
		try {
			// se construye el usuario a modificar su estado
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setId(91l);
			usuario.setNombre("Carlos Andres Diaz Mendoza");
			usuario.setUsuarioIngreso("secreArmenia1");

			// se procede a modificar los datos de la cuenta
			this.configuracionesService.modificarDatosCuenta(usuario);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
