package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.enums.Estado;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.modificarEstadoUsuario
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModificarEstadoUsuarioTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite cambiar el estado de un usuario
	 */
	@Test
	public void modificarEstadoUsuario() {
		try {
			// se construye le usuario a modificar su estado
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setId(1l);
			usuario.setEstado(Estado.ACTIVO.id);

			// se procede a modificar el estado del usuario
			this.configuracionesService.modificarEstadoUsuario(usuario);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
