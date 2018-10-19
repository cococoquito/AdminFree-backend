package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.services.ConfiguracionesService;
import adminfree.model.configuraciones.ClienteDTO;

/**
 * Test para el servicio ConfiguracionesService.listarClientes
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListarClientesTest {
	
	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;
	
	/**
	 * Test para validar la lista de clientes
	 */	
	@Test
	public void listarClientes() {
		try {
			// se invoca el llamado del servicio para obtener todos los clientes
			List<ClienteDTO> clientes = this.configuracionesService.listarClientes();

			// debe retornar almenos un cliente
			assertTrue(clientes != null && !clientes.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
