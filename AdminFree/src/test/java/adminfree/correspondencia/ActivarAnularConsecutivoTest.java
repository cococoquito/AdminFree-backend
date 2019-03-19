package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.ActivarAnularConsecutivoDTO;
import adminfree.enums.Estado;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.activarAnularConsecutivo
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivarAnularConsecutivoTest {

	/** Objecto que contiene los servicios relacionados modulo correspondencia */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite ACTIVAR o ANULAR un consecutivo de correspondencia
	 */
	@Test
	public void activarAnularConsecutivo() {
		try {
			// se construye los parametros necesarios para el cambio de estado
			ActivarAnularConsecutivoDTO parametro = new ActivarAnularConsecutivoDTO();
			parametro.setIdCliente(1);
			parametro.setIdConsecutivo(1l);
			parametro.setIdEstado(Estado.ANULADO.id);

			// se ejecuta el proceso de negocio
			this.correspondenciaService.activarAnularConsecutivo(parametro);

			// si llega este punto es porque el proceso se ejecuto sin problemas
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
