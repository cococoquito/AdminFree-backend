package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.getDetalleNomenclaturaCampo
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDetalleNomenclaturaCampoTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite obtener el detalle de un campo de entrada de informacion
	 */
	@Test
	public void getDetalleNomenclaturaCampo() {
		try {
			Long idNomenclatura = 1l;
			Long idCampo = 4l;
			CampoEntradaDTO detalle = this.configuracionesService.getDetalleNomenclaturaCampo(idNomenclatura, idCampo);

			// debe existir el detalle del campo
			assertTrue(detalle != null && detalle.getId() != null );
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
