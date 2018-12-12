package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CampoEntradaDTO;
import adminfree.dtos.configuraciones.ItemDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
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
			// se crea el campo de entrada a insertar
			CampoEntradaDTO campoEntrada = new CampoEntradaDTO();
			campoEntrada.setTipoCampo(TipoCampo.CAMPO_FECHA.id);
			campoEntrada.setNombre("Nro SAC");
			campoEntrada.setDescripcion("Este es la descripcion del nuevo campo");
			campoEntrada.setIdCliente(1l);

			// restricciones del campo
			RestriccionDTO restriccion1 = new RestriccionDTO();
			restriccion1.setId(1);
			RestriccionDTO restriccion2 = new RestriccionDTO();
			restriccion2.setId(2);
			RestriccionDTO restriccion3 = new RestriccionDTO();
			restriccion3.setId(3);
			campoEntrada.agregarRestriccion(restriccion1);
			campoEntrada.agregarRestriccion(restriccion2);
			campoEntrada.agregarRestriccion(restriccion3);

			// items para este campo tipo lista desplegable
			ItemDTO item1 = new ItemDTO();
			item1.setValor("Item 1 campo 2");
			ItemDTO item2 = new ItemDTO();
			item2.setValor("Item 2 campo 2");
			ItemDTO item3 = new ItemDTO();
			item3.setValor("Item 3 campo 2");
			campoEntrada.agregarItem(item1);
			campoEntrada.agregarItem(item2);
			campoEntrada.agregarItem(item3);

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
