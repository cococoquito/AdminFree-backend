package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.ConsecutivoDTO;
import adminfree.dtos.correspondencia.FiltroConsecutivosAnioActualDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getConsecutivosAnioActual
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetConsecutivosAnioActualTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los consecutivos del anio actual de acuerdo al
	 * filtro de busqueda
	 */
	@Test
	public void getConsecutivosAnioActual() {
		try {
			// se construye el filtro para la busqueda de los consecutivos
			FiltroConsecutivosAnioActualDTO filtro = new FiltroConsecutivosAnioActualDTO();
			filtro.setIdCliente(1l);

			// se procede a invocar el servicio para obtener los consecutivos
			List<ConsecutivoDTO> consecutivos = this.correspondenciaService.getConsecutivosAnioActual(filtro);

			// debe existir los consecutivos
			assertTrue(consecutivos != null && !consecutivos.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
