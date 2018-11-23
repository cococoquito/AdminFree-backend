package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.getUsuariosClienteTest
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetUsuariosClienteTest {
	
	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;
	
	/**
	 * Test que permite consultar los usuarios con estados (ACTIVO/INACTIVO)
	 * asociados a un cliente especifico
	 */
	@Test
	public void getUsuariosCliente() {
		try {
			// se consultas los usuarios asociados al cliente con el id=1
			ClienteDTO filtro = new ClienteDTO();
			filtro.setId(1L);
			
			// se consulta los usuarios de acuerdo al filtro
			List<UsuarioDTO> usuarios = this.configuracionesService.getUsuariosCliente(filtro);

			// debe existir usuarios para este test asociado al cliente = 1
			assertTrue(usuarios != null && !usuarios.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
