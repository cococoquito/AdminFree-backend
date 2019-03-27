package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.correspondencia.CampoEntradaDetalleDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getCamposNomenclatura
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetCamposNomenclaturaTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los campos de la nomenclatura
	 */
	@Test
	public void getCamposNomenclatura() {
		try {
			Long idNomenclatura = 9l;
			List<CampoEntradaDetalleDTO> campos = 
					this.correspondenciaService.getCamposNomenclatura(idNomenclatura);

			for(CampoEntradaDetalleDTO campo :campos) {
				System.out.println("*****************************************************");
				System.out.println("Nombre:"+campo.getNombre() + " ID:"+campo.getId());
				for(ItemDTO i:campo.getItems()) {
					System.out.println("Item:"+i.getValor() + " ID:" + i.getId() + " ID Campo:" + i.getIdCampo());
				}
			}

			// debe existir campos asociados a la nomenclatura
			assertTrue(campos != null && !campos.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
