package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.model.configuraciones.ClienteDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.activarCliente
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivarClienteTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite activar un Cliente
	 */
	@Test
	public void activarCliente() {
		try {
			// se configura el CLIENTE ACTIVAR
			ClienteDTO clienteActivar = new ClienteDTO();
			clienteActivar.setId(1L);

			// se invoca el llamado del servicio para ACTIVAR el cliente
			this.configuracionesService.activarCliente(clienteActivar);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
