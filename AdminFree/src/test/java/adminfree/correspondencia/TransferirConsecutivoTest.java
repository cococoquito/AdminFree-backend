package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.CommonConstant;
import adminfree.dtos.correspondencia.FiltroConsecutivosDTO;
import adminfree.dtos.correspondencia.TransferirConsecutivoDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.transferirConsecutivo
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferirConsecutivoTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite transferir un consecutivo hacia otro usuario
	 */
	@Test
	public void transferirConsecutivo() {
		try {
			// se construye los parametros
			TransferirConsecutivoDTO parametro = new TransferirConsecutivoDTO();
			parametro.setIdCliente("1");
			parametro.setIdConsecutivo("3");
			parametro.setIdUsuario("2");
			parametro.setIdUsuarioTransferir("-1");

			// se construye el filtro de busqueda
			FiltroConsecutivosDTO filtro = new FiltroConsecutivosDTO();
			filtro.setIdCliente(1L);
			filtro.setIdUsuario(1);

			// el paginador debe empezar de 0-10 por default
			PaginadorDTO paginador = new PaginadorDTO();
			paginador.setSkip(CommonConstant.SKIP_DEFAULT);
			paginador.setRowsPage(CommonConstant.ROWS_PAGE_DEFAULT);
			filtro.setPaginador(paginador);
			parametro.setFiltro(filtro);

			// se ejecuta el proceso de negocio
			parametro = this.correspondenciaService.transferirConsecutivo(parametro);
			assertTrue(parametro != null && parametro.getResponseConsecutivos() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
