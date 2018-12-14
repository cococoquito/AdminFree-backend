package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CampoEntradaEdicionDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.getDetalleCampoEntradaEdicion
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDetalleCampoEntradaEdicionTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite obtener el detalle de un campo de entrada de informacion
	 */
	@Test
	public void getDetalleCampoEntradaEdicion() {
		try {
			Long idCampo = 1l;
			CampoEntradaEdicionDTO detalle = this.configuracionesService.getDetalleCampoEntradaEdicion(idCampo);

			// debe existir el detalle del campo de ingreso
			assertTrue(
					detalle != null &&
					detalle.getCampoEntrada() != null && 
					detalle.getCampoEntrada().getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
