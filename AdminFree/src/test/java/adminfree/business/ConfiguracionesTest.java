package adminfree.business;

import static org.junit.Assert.assertTrue;

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
	 * Test para la creación de los TEST
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
}
