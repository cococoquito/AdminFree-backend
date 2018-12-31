package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.validarExisteNomenclatura
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidarExisteNomenclaturaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite validar si la nomenclatura ya existe en el sistema
	 */
	@Test
	public void validarExisteNomenclatura() {
		try {
			NomenclaturaDTO nomenclatura = new NomenclaturaDTO();
			nomenclatura.setNomenclatura("NOME");
			nomenclatura.setIdCliente(1l);

			// se ejecuta las validaciones
			this.configuracionesService.validarExisteNomenclatura(nomenclatura);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
