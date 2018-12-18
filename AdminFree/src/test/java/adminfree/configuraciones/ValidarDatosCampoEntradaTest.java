package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.enums.TipoCampo;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.validarDatosCampoEntrada
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidarDatosCampoEntradaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite validar los datos de campo de entrada
	 */
	@Test
	public void validarDatosCampoEntrada() {
		try {
			CampoEntradaDTO campo = new CampoEntradaDTO();
			campo.setTipoCampo(TipoCampo.LISTA_DESPLEGABLE.id);
			campo.setNombre("Nro SAC");
			campo.setIdCliente(1l);

			List<RestriccionDTO> restricciones = this.configuracionesService.validarDatosCampoEntrada(campo);
			assertTrue(restricciones != null && !restricciones.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
