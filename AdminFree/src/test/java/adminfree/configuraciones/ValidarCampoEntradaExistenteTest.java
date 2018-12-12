package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.enums.TipoCampo;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.validarCampoEntradaExistente
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidarCampoEntradaExistenteTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite validar si el campo de entrada existe para el tipo, nombre y cliente
	 */
	@Test
	public void validarCampoEntradaExistente() {
		try {
			// campo de entrada con los datos a crear
			CampoEntradaDTO campoEntrada = new CampoEntradaDTO();
			campoEntrada.setTipoCampo(TipoCampo.LISTA_DESPLEGABLE.id);
			campoEntrada.setNombre("Nro SAC01");
			campoEntrada.setDescripcion("Este es la descripcion del nuevo campo");
			campoEntrada.setIdCliente(2l);

			// se procede a ejecutar la validacion
			this.configuracionesService.validarCampoEntradaExistente(campoEntrada);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
