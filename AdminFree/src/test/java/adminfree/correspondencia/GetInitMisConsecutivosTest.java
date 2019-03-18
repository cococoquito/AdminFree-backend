package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.InitMisConsecutivosDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getInitMisConsecutivos
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetInitMisConsecutivosTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los datos iniciales para el 
	 * submodulo de Mis Consecutivos de correspondencia solicitados
	 * para el anio actual
	 */
	@Test
	public void getInitMisConsecutivos() {
		try {
			Long idCliente = 1l;
			Integer idUsuario = 2;

			// se procede a invocar el servicio para obtener los datos del init
			InitMisConsecutivosDTO init = this.correspondenciaService.getInitMisConsecutivos(idCliente, idUsuario);

			// debe existir los consecutivos
			assertTrue(init != null &&
					init.getConsecutivos() != null &&
					init.getConsecutivos().getCantidadTotal() != null &&
					init.getConsecutivos().getCantidadTotal() > 0l);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
