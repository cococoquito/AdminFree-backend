package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.EliminarNomenclatura
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EliminarNomenclaturaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite eliminar una nomenclatura del sistema
	 */
	@Test
	public void EliminarNomenclatura() {
		try {
			// se invoca el llamado del servicio para ELIMINAR la nomenclatura
			Long idNomenclatura = 1L;
			this.configuracionesService.eliminarNomenclatura(idNomenclatura);

			// si todo fue exitoso
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
