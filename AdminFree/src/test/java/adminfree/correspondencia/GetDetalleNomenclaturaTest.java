package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.NomenclaturaDetalleDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getDetalleNomenclatura
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDetalleNomenclaturaTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener el detalle de una nomenclatura
	 */
	@Test
	public void getDetalleNomenclatura() {
		try {
			Long idNomenclatura = 1l;
			NomenclaturaDetalleDTO nome = this.correspondenciaService.getDetalleNomenclatura(idNomenclatura);

			// debe existir el detalle de la nomenclatura
			assertTrue(nome != null && nome.getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
