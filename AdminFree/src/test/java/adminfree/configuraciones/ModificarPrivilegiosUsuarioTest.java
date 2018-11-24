package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.modificarPrivilegiosUsuario
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModificarPrivilegiosUsuarioTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite modificar los privilegios del usuario
	 */
	@Test
	public void modificarPrivilegiosUsuario() {
		try {
			// se construye el usuario a modificar su estado
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.agregarModuloToken("9f1124f946de506332f221a01d39c411");
			usuario.agregarModuloToken("a9ee4d222a05960a00ea9b5eb6fb0a81");
			usuario.agregarModuloToken("b6177afc6e3818cb7cb663cf71ef1ce4");
			usuario.agregarModuloToken("4813e117da87e1ae5bdfb0d4967f4387");
			usuario.setId(1l);

			// se procede a modificar los privilegios del usuario
			this.configuracionesService.modificarPrivilegiosUsuario(usuario);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
