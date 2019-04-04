package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.CambioClaveDTO;
import adminfree.dtos.configuraciones.CambioUsuarioIngresoDTO;
import adminfree.dtos.configuraciones.ModificarCuentaUsuarioDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.modificarCuentaUsuario
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModificarCuentaUsuarioTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite procesar la funcionalidad de negocio de modificacion
	 * cuenta del usuario aplica para datos personales, cambio clave o usuario de ingreso
	 */
	@Test
	public void modificarCuentaUsuario() {
		try {
			// modificacion de los datos personales
			UsuarioDTO datosPersonales = new UsuarioDTO();
			datosPersonales.setId(10l);
			datosPersonales.setNombre("Carlos Andres Diaz");
			datosPersonales.setCargo("Ingeniero desarrollo de software");

			// modificacion para la clave de ingreso
			CambioClaveDTO cambioClave = new CambioClaveDTO();
			cambioClave.setIdUsuario(10l);
			cambioClave.setClaveActual("6cbcc9a3a13fbf9c083a4b971ab6d127");
			cambioClave.setClaveNueva("chichona1234$");
			cambioClave.setClaveVerificacion("chichona1234$");

			// modificacion para la clave de ingreso
			CambioUsuarioIngresoDTO cambioUsuario = new CambioUsuarioIngresoDTO();
			cambioUsuario.setIdUsuario(10l);
			cambioUsuario.setUsuario("usuariouno20191");
			cambioUsuario.setClaveActual("6cbcc9a3a13fbf9c083a4b971ab6d127");

			// se procede a modificar la cuenta del usuario
			ModificarCuentaUsuarioDTO params = new ModificarCuentaUsuarioDTO();
			params.setDatosPersonales(datosPersonales);
			params.setCambioClave(cambioClave);
			params.setCambioUsuario(cambioUsuario);
			this.configuracionesService.modificarCuentaUsuario(params);

			// si llega a esta punto es porque todo fue procesado correctamente
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
