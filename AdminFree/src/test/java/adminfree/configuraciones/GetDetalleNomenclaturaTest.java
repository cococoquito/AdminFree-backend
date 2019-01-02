package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.NomenclaturaEdicionDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.getDetalleNomenclatura
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDetalleNomenclaturaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite consultar el detalle de la nomenclatura
	 */
	@Test
	public void getDetalleNomenclatura() {
		try {
			Long idNomenclatura = 4l;
			NomenclaturaEdicionDTO datos = this.configuracionesService.getDetalleNomenclatura(idNomenclatura);

			// debe existir el detalle del campo de ingreso
			assertTrue(
					datos != null &&
					datos.getNomenclatura() != null &&
					datos.getNomenclatura().getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
