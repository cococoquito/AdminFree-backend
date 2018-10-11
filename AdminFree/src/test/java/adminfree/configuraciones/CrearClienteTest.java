package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.b.services.ConfiguracionesService;
import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.e.utilities.ConstantEstado;

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
	 * Test para la creacion de los TEST
	 */
	@Test
	public void crearCliente() {
		try {
			// se construye el cliente a crear
			ClienteDTO cliente = new ClienteDTO();
			cliente.setNombre("Secretaria de educacion municipal");
			cliente.setTelefonos("3124935037;3122345609");
			cliente.setEmails("adiz98@hotmail.com;adiz98g@gmail.com");
			cliente.setEstado(ConstantEstado.ID_ESTADO_ACTIVO);

			// se invoca el service para la creacion del cliente
			ClienteDTO resultado = this.configuracionesService.crearCliente(cliente);

			// debe retornar el cliente con el identificador
			assertTrue(resultado != null && resultado.getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}	
}
