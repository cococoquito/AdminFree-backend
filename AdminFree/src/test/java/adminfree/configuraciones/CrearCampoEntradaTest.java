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
 * Test para el servicio ConfiguracionesService.crearCampoEntrada
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrearCampoEntradaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite soportar el proceso de negocio para la creacion del campo de
	 * entrada de informacion
	 */
	@Test
	public void crearCampoEntrada() {
		try {

			// se crea el campo de entrada a insertart
			CampoEntradaDTO campoEntrada = new CampoEntradaDTO();
			campoEntrada.setTipoCampo(TipoCampo.CAMPO_TEXTO.id);
			campoEntrada.setNombre("Nro SAC");
			campoEntrada.setDescripcion("Este es la descripcion del nuevo campo");
			campoEntrada.setIdCliente(1l);

			// se procede a invocar el servicio para la creacion
			CampoEntradaDTO campoCreado = this.configuracionesService.crearCampoEntrada(campoEntrada);
			assertTrue(campoCreado != null && campoCreado.getId() != null);
			assertTrue(campoCreado.getNombre().equals(campoEntrada.getNombre()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
