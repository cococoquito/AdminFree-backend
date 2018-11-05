package adminfree.seguridad;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.AdminClientesDTO;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.services.SeguridadService;
import adminfree.utilities.BusinessException;

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
			CredencialesDTO auth = new CredencialesDTO();
			auth.setClave("d104657114ec4d42ff2d54f35fbbb866");
			auth.setUsuario("04eeefae977ca82da5c4d5765c35edcf");
			
			// se invoca el servicio para la autenticacion
			AdminClientesDTO response = this.seguridadService.iniciarSesionAdminClientes(auth);

			// se valida si es exitoso el resultado
			assertTrue(
					response != null && 
					response.getCredenciales() != null &&
					response.getCredenciales().getToken() != null);
		} catch (BusinessException e) {
			System.err.println("Bussines:" + e.getMessage());
			assertTrue(false);
		} catch (Exception e) {
			System.err.println("Technical:" + e.getMessage());
			assertTrue(false);
		}
	}
}