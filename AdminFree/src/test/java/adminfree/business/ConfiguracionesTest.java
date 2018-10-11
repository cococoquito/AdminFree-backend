package adminfree.business;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.b.services.ConfiguracionesService;
import adminfree.g.persistence.ConstantSQL;

/**
 * 
 * Clase para testear los servicios del modulo de configuraciones
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfiguracionesTest {
	
	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;
	
	/**
	 * Test que permite iniciar sesion para administrar los clientes
	 */
	@Test
	public void iniciarSesionAdminClientes() {
		// se obtiene la clave e usuario para la autenticacion
		String clave = "1234";
		String usuario = "user";

		// se invoca el servicio para la autenticacion
		String resultado = this.configuracionesService.iniciarSesionAdminClientes(clave, usuario);

		// se valida si es exitoso el resultado
		assertTrue(ConstantSQL.SUCCESSFUL.equals(resultado));
	}

//	/**
//	 * Test para la creacion de los TEST
//	 */
//	@Test
//	public void crearCliente() {
//		try {
//			// se construye el cliente a crear
//			ClienteDTO cliente = new ClienteDTO();
//			cliente.setNombre("Secretaria de educacion municipal");
//			cliente.setTelefonos("3124935037;3122345609");
//			cliente.setEstado(ConstantEstado.ID_ESTADO_ACTIVO);
//
//			// se invoca el service para la creacion del cliente
//			ClienteDTO resultado = this.configuracionesService.crearCliente(cliente);
//
//			// debe retornar el cliente con el identificador
//			assertTrue(resultado != null && resultado.getId() != null);
//		} catch (Exception e) {
//			assertTrue(false);
//		}
//	}
//
//	/**
//	 * Test para la lista de todos los clientes
//	 */
//	@Test
//	public void listarTodosClientes() {
//		try {
//			// se invoca el llamado del servicio para obtener todos los clientes
//			List<ClienteDTO> clientes = this.configuracionesService.listarClientes();
//
//			// debe retornar almenos un cliente
//			assertTrue(clientes != null && !clientes.isEmpty());
//		} catch (Exception e) {
//			assertTrue(false);
//		}
//	}
//
//	/**
//	 * Test que permite actualizar los datos del cliente
//	 */
//	@Test
//	public void actualizarCliente() {
//		try {
//			// se configura el CLIENTE actualizar
//			ClienteDTO clienteUpdate = new ClienteDTO();
//			clienteUpdate.setId(1L);
//			clienteUpdate.setNombre("Nombre-test");
//			clienteUpdate.setEmails("Email-test");
//			clienteUpdate.setTelefonos("Telefono-test");
//
//			// se invoca el llamado del servicio para actualizar el cliente
//			this.configuracionesService.actualizarCliente(clienteUpdate);
//
//			// si llega a esta punto es porque todo fue actualizado correctamente
//			assertTrue(true);
//		} catch (Exception e) {
//			assertTrue(false);
//		}
//	}
//
//	/**
//	 * Test que permite testiar el servicio de ACTIVAR Cliente
//	 */
//	@Test
//	public void activarCliente() {
//		try {
//			// se configura el CLIENTE ACTIVAR
//			ClienteDTO clienteActivar = new ClienteDTO();
//			clienteActivar.setId(1L);
//
//			// se invoca el llamado del servicio para ACTIVAR el cliente
//			this.configuracionesService.activarCliente(clienteActivar);
//
//			// si llega a esta punto es porque todo fue procesado correctamente
//			assertTrue(true);
//		} catch (Exception e) {
//			assertTrue(false);
//		}
//	}
//
//	/**
//	 * Test que permite testiar el servicio de INACTIVAR Cliente
//	 */
//	@Test
//	public void inactivarCliente() {
//		try {
//			// se configura el CLIENTE INACTIVAR
//			ClienteDTO clienteInactivar = new ClienteDTO();
//			clienteInactivar.setId(1L);
//
//			// se invoca el llamado del servicio para INACTIVAR el cliente
//			this.configuracionesService.inactivarCliente(clienteInactivar);
//
//			// si llega a esta punto es porque todo fue procesado correctamente
//			assertTrue(true);
//		} catch (Exception e) {
//			assertTrue(false);
//		}
//	}
//	
//	/**
//	 * Test que permite eliminar un CLIENTE del sistema
//	 */	
//	@Test
//	public void eliminarCliente() {
//		try {
//			// se configura el CLIENTE ELIMINAR
//			ClienteDTO clienteEliminar = new ClienteDTO();
//			clienteEliminar.setId(1L);
//			
//			// se invoca el llamado del servicio para ELIMINAR el cliente
//			this.configuracionesService.eliminarCliente(clienteEliminar);
//
//			// si llega a esta punto es porque todo fue procesado correctamente
//			assertTrue(true);			
//		} catch (Exception e) {
//			assertTrue(false);
//		}
//	}
}
