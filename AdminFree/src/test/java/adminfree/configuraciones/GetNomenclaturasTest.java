package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.getNomenclaturas
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetNomenclaturasTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite obtener todas las nomenclaturas asociadas a un cliente
	 */
	@Test
	public void getNomenclaturas() {
		try {
			Long idCliente = 1l;
			List<NomenclaturaDTO> nomenclaturas = this.configuracionesService.getNomenclaturas(idCliente);

			// debe existir las nomenclaturas asociadas al cliente
			assertTrue(nomenclaturas != null && !nomenclaturas.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
