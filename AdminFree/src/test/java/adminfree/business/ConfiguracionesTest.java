package adminfree.business;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import adminfree.b.services.ConfiguracionesService;
import adminfree.d.model.configuraciones.ClienteDTO;
import adminfree.e.utilities.ConstantEstado;

/**
 * 
 * Clase para testear los servicios del modulo de configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
public class ConfiguracionesTest {

	/** Service que contiene las configuraciones del sistema */
	private ConfiguracionesService configuracionesService;

	/** Metodo que se ejecuta antes de iniciar los test */
	@Before
	public void init() {
		// se debe tener esta instancia para los test
		this.configuracionesService = new ConfiguracionesService();
	}

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
			cliente.setEstado(ConstantEstado.ID_ESTADO_ACTIVO);

			// se invoca el service para la creacion del cliente
			ClienteDTO resultado = this.configuracionesService.crearCliente(cliente);

			// debe retornar el cliente con el identificador
			assertTrue(resultado != null && resultado.getId() != null);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Test para la lista de todos los clientes
	 */
	@Test
	public void listarTodosClientes() {
		try {
			// se invoca el llamado del servicio para obtener los todos los clientes
			List<ClienteDTO> clientes = this.configuracionesService.listarTodosClientes();

			// debe retornar almenos un cliente
			assertTrue(clientes != null && !clientes.isEmpty());
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Test que permite actualizar los datos del cliente
	 */
	@Test
	public void actualizarCliente() {
		try {
			// se configura el CLIENTE actualizar
			ClienteDTO clienteUpdate = new ClienteDTO();
			clienteUpdate.setId(1L);
			clienteUpdate.setNombre("Nombre-test");
			clienteUpdate.setEmails("Email-test");
			clienteUpdate.setTelefonos("Telefono-test");

			// se invoca el llamado del servicio para actualizar el cliente
			this.configuracionesService.actualizarCliente(clienteUpdate);

			// si llega a esta punto es porque todo fue actualizado correctamente
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * Test que permite testiar el servicio de ACTIVAR Cliente
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
			assertTrue(false);
		}
	}

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
			assertTrue(false);
		}
	}
}
