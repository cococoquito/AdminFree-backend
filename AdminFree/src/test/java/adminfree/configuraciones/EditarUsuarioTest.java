package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.UsuarioEdicionDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.editarUsuario
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EditarUsuarioTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite editar los datos de un usuario
	 */
	@Test
	public void editarUsuario() {
		try {
			// datos del usuario a modificar
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setId(2l);
			usuario.setNombre("Nombre del usuario1");
			usuario.setUsuarioIngreso("usuarioingreso1");
			usuario.setCargo("nuevo cargo");

			// modulos asignados para el usuario
			List<String> modulosTokens = new ArrayList<>();
			modulosTokens.add("a9ee4d222a05960a00ea9b5eb6fb0a81");
			usuario.setModulosTokens(modulosTokens);

			// DTO para la edicion
			UsuarioEdicionDTO datos = new UsuarioEdicionDTO();
			datos.setUsuario(usuario);
			datos.setDatosBasicosEditar(true);
			datos.setModulosEditar(true);

			// se modifica el usuario
			this.configuracionesService.editarUsuario(datos);
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
