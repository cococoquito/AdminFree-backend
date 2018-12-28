package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.CampoEntradaEdicionDTO;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.editarCampoEntradaInformacion
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EditarCampoEntradaInformacionTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite editar los campo de entrada de informacion
	 */
	@Test
	public void editarCampoEntradaInformacion() {
		try {
			// identificador del campo a probar
			Long idCampo = 3l;

			// se obtiene el detalle del campo a editar
			CampoEntradaEdicionDTO detalle = this.configuracionesService.getDetalleCampoEntradaEdicion(idCampo);

			// configuracion de los datos basicos
			detalle.getCampoEntrada().setDescripcion("prueva nro uno");
			detalle.getCampoEntrada().setNombre("Campo uno");
			detalle.setDatosBasicosEditar(false);

			// configuracion de las restricciones
			detalle.getCampoEntrada().setRestricciones(null);
			RestriccionDTO restriccion1 = new RestriccionDTO();
			restriccion1.setId(1);
			RestriccionDTO restriccion2 = new RestriccionDTO();
			restriccion2.setId(2);
			detalle.getCampoEntrada().agregarRestriccion(restriccion1);
			detalle.getCampoEntrada().agregarRestriccion(restriccion2);
			detalle.setRestriccionesEditar(false);

			// items del campo
			ItemDTO item1 = new ItemDTO();
			item1.setValor("Mdellin");
			ItemDTO item2 = new ItemDTO();
			item2.setValor("Armenia");
			detalle.getCampoEntrada().agregarItem(item1);
			detalle.getCampoEntrada().agregarItem(item2);
			detalle.setItemsEditar(true);
//			List<ItemDTO> items = detalle.getCampoEntrada().getItems();
//			if (items != null && !items.isEmpty()) {
//				for (ItemDTO item: items) {
//					item.setBorrar(true);
//				}
//				detalle.setItemsEditar(true);
//			}

			// se procede a editar el campo de entrada consultada
			CampoEntradaDTO campo = this.configuracionesService.editarCampoEntradaInformacion(detalle);
			assertTrue(campo != null && campo.getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
