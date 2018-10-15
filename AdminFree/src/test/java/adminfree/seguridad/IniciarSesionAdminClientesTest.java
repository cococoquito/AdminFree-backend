package adminfree.seguridad;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.b.services.SeguridadService;
import adminfree.e.utilities.BusinessException;
import adminfree.e.utilities.ConstantsCodigoMessages;

/**
 * Test para el servicio ConfiguracionesService.iniciarSesionAdminClientes
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IniciarSesionAdminClientesTest {

	/** Objecto que contiene todo los servicios relacionado con la seguridad */
	@Autowired
	private SeguridadService seguridadService;

	/**
	 * Test que permite iniciar sesion para administrar los clientes
	 */
	@Test
	public void iniciarSesionAdminClientes() {
		try {
			// se obtiene la clave e usuario para la autenticacion
			String clave = "d104657114ec4d42ff2d54f35fbbb866";
			String usuario = "04eeefae977ca82da5c4d5765c35edcf";

			// se invoca el servicio para la autenticacion
			String token = this.seguridadService.iniciarSesionAdminClientes(clave, usuario);

			// se valida si es exitoso el resultado
			System.out.println("Token:" + token);
			assertTrue(token != null);
		} catch (BusinessException e) {
			System.err.println("Bussines:" + e.getMessage());
			assertTrue(false);
		} catch (Exception e) {
			System.err.println(ConstantsCodigoMessages.COD_ERROR_TECHNICAL + e.getMessage());
			assertTrue(false);
		}
	}
}