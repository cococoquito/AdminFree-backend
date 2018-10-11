package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.b.services.ConfiguracionesService;
import adminfree.d.model.configuraciones.ClienteDTO;

/**
 * Test para el servicio ConfiguracionesService.actualizarCliente
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActualizarClienteTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite actualizar los datos del cliente
	 */
	@Test
	public void actualizarCliente() {
		try {
			// se configura el CLIENTE actualizar
			ClienteDTO clienteUpdate = new ClienteDTO();
			clienteUpdate.setId(2L);
			clienteUpdate.setNombre("este es el campo nombre");
			clienteUpdate.setEmails("este es el campo email");
			clienteUpdate.setTelefonos("este es el campo telefono");

			// se invoca el llamado del servicio para actualizar el cliente
			this.configuracionesService.actualizarCliente(clienteUpdate);

			// si llega a esta punto es porque todo fue actualizado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
