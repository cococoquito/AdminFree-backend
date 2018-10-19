package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.services.ConfiguracionesService;
import adminfree.d.model.configuraciones.ClienteDTO;

/**
 * Test para el servicio ConfiguracionesService.crearCliente
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrearClienteTest {
		
	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;	
	
	/**
	 * Test para la creacion del CLIENTE
	 */
	@Test
	public void crearCliente() {
		try {
			// se construye el cliente a crear
			ClienteDTO cliente = new ClienteDTO();
			cliente.setNombre("cliente prueba");
			cliente.setTelefonos("364935038");
			cliente.setEmails("adiz98@gmail.com");

			// se invoca el service para la creacion del cliente
			cliente = this.configuracionesService.crearCliente(cliente);

			// validaciones requeridas para que pase el test
			assertTrue(cliente != null && cliente.getId() != null);
			assertTrue(cliente.getNombre().equals("cliente prueba"));
			assertTrue(cliente.getTelefonos().equals("364935038"));
			assertTrue(cliente.getEmails().equals("adiz98@gmail.com"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
