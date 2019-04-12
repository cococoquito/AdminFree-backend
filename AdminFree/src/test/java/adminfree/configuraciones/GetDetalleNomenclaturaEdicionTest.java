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
 * Test para el servicio ConfiguracionesService.getDetalleNomenclaturaEdicion
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDetalleNomenclaturaEdicionTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite consultar el detalle de la nomenclatura para su modificacion
	 */
	@Test
	public void getDetalleNomenclaturaEdicion() {
		try {
			Long idNomenclatura = 3l;
			Long idCliente = 1l;
			Integer isGetCampos = 0;

			// se consulta el detalle de la nomenclatura a editar
			NomenclaturaEdicionDTO detalle =
					this.configuracionesService.getDetalleNomenclaturaEdicion(idNomenclatura, idCliente, isGetCampos);

			// debe existir el detalle de la nomenclatura para editar
			assertTrue(
					detalle != null &&
					detalle.getNomenclatura() != null && 
					detalle.getNomenclatura().getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
