package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.CommonConstant;
import adminfree.dtos.transversal.SelectItemDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getUsuariosTransferir
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetUsuariosTransferirTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los usuarios para el proceso de transferir consecutivo
	 */
	@Test
	public void getUsuariosTransferir() {
		try {
			Integer idCliente = 1;
			Integer idUsuario = CommonConstant.ID_ADMINISTRADOR;
			List<SelectItemDTO> usuarios = this.correspondenciaService.getUsuariosTransferir(idCliente, idUsuario);
			assertTrue(usuarios != null && !usuarios.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
