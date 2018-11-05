package adminfree.seguridad;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.services.SeguridadService;
import adminfree.utilities.BusinessException;

/**
 * Test para el servicio ConfiguracionesService.iniciarSesionAdmin
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IniciarSesionAdminTest {

	/** Objecto que contiene todo los servicios relacionado con la seguridad */
	@Autowired
	private SeguridadService seguridadService;

	/**
	 * Metodo que permite iniciar sesion como ADMINISTRADOR
	 */
	@Test
	public void IniciarSesionAdmin() {
		try {
			// se configura las credenciales de autenticacion
			CredencialesDTO credenciales = new CredencialesDTO();
			credenciales.setClave("46ad9b60f64831a86004643590485c1c");
			credenciales.setUsuario("usuario uno");

			// se inicia sesion como ADMINISTRADOR
			ClienteDTO admin = this.seguridadService.iniciarSesionAdmin(credenciales);

			// se valida si es exitoso el resultado
			assertTrue(
					admin != null &&
					admin.getCredenciales() != null &&
					admin.getCredenciales().getToken() != null);
		} catch (BusinessException e) {
			System.err.println("Bussines:" + e.getMessage());
			assertTrue(false);
		} catch (Exception e) {
			System.err.println("Technical:" + e.getMessage());
			assertTrue(false);
		}
	}
}
