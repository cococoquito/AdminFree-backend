package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getItemsSelectFiltro
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetItemsSelectFiltroTest {

	/** Objecto que contiene los servicios relacionados modulo correspondencia */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los items para los filtros tipo LISTA DESPLEGABLE
	 */
	@Test
	public void getItemsSelectFiltro() {
		try {
			// se configura los identificadores
			List<Long> idsCampos = new ArrayList<>();
			idsCampos.add(2L);
			idsCampos.add(7L);
			idsCampos.add(19L);

			// se consulta la lista de items asociados a los identificadores
			List<ItemDTO> items = this.correspondenciaService.getItemsSelectFiltro(idsCampos);

			// debe existir los items asociados a los identificadores
			assertTrue(items != null && !items.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
