package adminfree.seguridad;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.seguridad.CredencialesDTO;
import adminfree.dtos.seguridad.WelcomeDTO;
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
			credenciales.setClave("06cfb91e7212d06ba19d3e9f91ca2637");
			credenciales.setUsuario("usuario cliente");
			credenciales.setAdministrador(true);

			// se inicia sesion como ADMINISTRADOR
			WelcomeDTO inicio = this.seguridadService.iniciarSesionAdmin(credenciales);

			// se valida si es exitoso el resultado
			assertTrue(
					inicio != null &&
					inicio.getCredenciales() != null &&
					inicio.getCredenciales().getToken() != null);
		} catch (BusinessException e) {
			System.err.println("Bussines:" + e.getMessage());
			assertTrue(false);
		} catch (Exception e) {
			System.err.println("Technical:" + e.getMessage());
			assertTrue(false);
		}
	}
}
