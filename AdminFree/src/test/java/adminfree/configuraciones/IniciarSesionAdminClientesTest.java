package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.b.services.ConfiguracionesService;
import adminfree.g.persistence.ConstantSQL;

/**
 * Test para el servicio ConfiguracionesService.iniciarSesionAdminClientes
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IniciarSesionAdminClientesTest {

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
}
