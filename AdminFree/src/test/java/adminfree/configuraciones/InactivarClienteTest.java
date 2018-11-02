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
 * Test para el servicio ConfiguracionesService.inactivarCliente
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InactivarClienteTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite testiar el servicio de INACTIVAR Cliente
	 */
	@Test
	public void inactivarCliente() {
		try {
			// se configura el CLIENTE INACTIVAR
			ClienteDTO clienteInactivar = new ClienteDTO();
			clienteInactivar.setId(1L);

			// se invoca el llamado del servicio para INACTIVAR el cliente
			this.configuracionesService.inactivarCliente(clienteInactivar);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
