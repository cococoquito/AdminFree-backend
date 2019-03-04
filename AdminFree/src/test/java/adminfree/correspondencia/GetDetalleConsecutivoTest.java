package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.ConsecutivoDetalleDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getDetalleConsecutivo
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDetalleConsecutivoTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;
	
	/**
	 * Test que permite consultar el detalle de un consecutivo
	 */
	@Test
	public void getDetalleConsecutivo() {
		try {
			// se construye el filtro para consultar el detalle del consecutivo
			ConsecutivoDetalleDTO filtro = new ConsecutivoDetalleDTO();
			filtro.setIdCliente(1L);
			filtro.setIdConsecutivo(1L);

			// se procede consultar del detalle
			ConsecutivoDetalleDTO detalle = this.correspondenciaService.getDetalleConsecutivo(filtro);

			// debe existir el detalle
			assertTrue(detalle != null &&
					detalle.getConsecutivo() != null &&
					detalle.getConsecutivo().getConsecutivo() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
