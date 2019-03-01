package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.CommonConstant;
import adminfree.dtos.correspondencia.FiltroConsecutivosAnioActualDTO;
import adminfree.dtos.transversal.PaginadorDTO;
import adminfree.dtos.transversal.PaginadorResponseDTO;
import adminfree.enums.Estado;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.getConsecutivosAnioActual
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetConsecutivosAnioActualTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite obtener los consecutivos del anio actual de acuerdo al
	 * filtro de busqueda
	 */
	@Test
	public void getConsecutivosAnioActual() {
		try {
			// se construye el filtro para la busqueda de los consecutivos
			FiltroConsecutivosAnioActualDTO filtro = new FiltroConsecutivosAnioActualDTO();

			// filtro por cliente
			filtro.setIdCliente(1l);

			// filtro por fecha inicial de solicitud
			Calendar fechaInicial = Calendar.getInstance();
			fechaInicial.set(Calendar.DATE, 1);
			fechaInicial.set(Calendar.MONTH, Calendar.FEBRUARY);
			filtro.setFechaSolicitudInicial(fechaInicial.getTime());

			// filtro por fecha final de solicitud
			Calendar fechaFinal = Calendar.getInstance();
			fechaFinal.set(Calendar.DATE, 28);
			fechaFinal.set(Calendar.MONTH, Calendar.FEBRUARY);
			filtro.setFechaSolicitudFinal(fechaFinal.getTime());

			// filtro por nomenclaturas
			filtro.setNomenclaturas("da");

			// filtro por consecutivos
			filtro.setConsecutivos("1");

			// filtro por usuarios quien solicito el consecutivo
			filtro.setIdUsuario(CommonConstant.ID_ADMINISTRADOR);

			// filtro por estados del consecutivo
			filtro.setEstado(Estado.ACTIVO.id);

			// se configura el paginador para la consulta
			PaginadorDTO paginador = new PaginadorDTO();
			paginador.setSkip("0");
			paginador.setRowsPage("5");
			paginador.setCantidadTotal(11L);
			filtro.setPaginador(paginador);

			// se procede a invocar el servicio para obtener los consecutivos
			PaginadorResponseDTO response = this.correspondenciaService.getConsecutivosAnioActual(filtro);

			// debe existir los consecutivos
			assertTrue(response != null && response.getCantidadTotal() > 0l);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
