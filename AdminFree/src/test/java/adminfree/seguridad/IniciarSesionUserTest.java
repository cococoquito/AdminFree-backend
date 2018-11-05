package adminfree.seguridad;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.SeguridadService;
import adminfree.utilities.BusinessException;

/**
 * Test para el servicio ConfiguracionesService.iniciarSesionUser
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IniciarSesionUserTest {

	/** Objecto que contiene todo los servicios relacionado con la seguridad */
	@Autowired
	private SeguridadService seguridadService;

	/**
	 * Metodo que permite iniciar sesion como USUARIO
	 */
	@Test
	public void iniciarSesionUser() {
		try {
			// se configura las credenciales de autenticacion
			CredencialesDTO credenciales = new CredencialesDTO();
			credenciales.setClave("chichona1");
			credenciales.setUsuario("USER USUARIO 1");

			// se inicia sesion como USUARIO
			UsuarioDTO user = this.seguridadService.iniciarSesionUser(credenciales);

			// se valida si es exitoso el resultado
			assertTrue(
					user != null && 
					user.getCredenciales() != null && 
					user.getCredenciales().getToken() != null);
		} catch (BusinessException e) {
			System.err.println("Bussines:" + e.getMessage());
			assertTrue(false);
		} catch (Exception e) {
			System.err.println("Technical:" + e.getMessage());
			assertTrue(false);
		}
	}
}
