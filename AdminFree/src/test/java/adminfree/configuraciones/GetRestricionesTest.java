package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.getRestriciones
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetRestricionesTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite obtener las restricciones asociados a un tipo de campo
	 */
	@Test
	public void getRestriciones() {
		try {
			Integer tipoCampo = 1;

			// se procede a obtener las restricciones asociada al tipo de campo
			List<RestriccionDTO> restricciones = this.configuracionesService.getRestriciones(tipoCampo);

			// debe existir restricciones asociadas al tipo de campo = 1
			assertTrue(restricciones != null && !restricciones.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
