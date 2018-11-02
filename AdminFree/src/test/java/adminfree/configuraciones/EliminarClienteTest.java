package adminfree.configuraciones;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.eliminarCliente
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EliminarClienteTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite eliminar un CLIENTE del sistema
	 */
	@Test
	public void eliminarCliente() {
		try {
			// se configura el CLIENTE ELIMINAR
			ClienteDTO clienteEliminar = new ClienteDTO();
			clienteEliminar.setId(1L);

			// se invoca el llamado del servicio para ELIMINAR el cliente
			String resultado = this.configuracionesService.eliminarCliente(clienteEliminar);

			// si verifica que el resultado es EXITOSO
			if ("OK".equals(resultado)) {
				assertTrue(true);
			} else {
				System.err.println(resultado);
				assertTrue(false);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
