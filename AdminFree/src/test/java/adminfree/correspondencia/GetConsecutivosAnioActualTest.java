package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.CommonConstant;
import adminfree.dtos.correspondencia.ConsecutivoDTO;
import adminfree.dtos.correspondencia.FiltroConsecutivosAnioActualDTO;
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
			filtro.setFechaSolicitudInicial(fechaInicial.getTime());

			// filtro por fecha final de solicitud
			Calendar fechaFinal = Calendar.getInstance();
			fechaFinal.set(Calendar.DATE, 28);
			filtro.setFechaSolicitudFinal(fechaFinal.getTime());

			// filtro por nomenclaturas
			filtro.setNomenclaturas("DA,OF,DASD");

			// filtro por consecutivos
			filtro.setConsecutivos("0037,38,1,2,3");

			// filtro por usuarios quien solicito el consecutivo
			List<Integer> idsUsuarios = new ArrayList<>();
			idsUsuarios.add(1);
			idsUsuarios.add(CommonConstant.ID_ADMINISTRADOR);
			idsUsuarios.add(2);
			idsUsuarios.add(3);
			filtro.setIdsUsuarios(idsUsuarios);

			// filtro por estados del consecutivo
			List<Integer> estados = new ArrayList<>();
			estados.add(1);
			estados.add(4);
			estados.add(5);
			estados.add(6);
			filtro.setEstados(estados);

			// se procede a invocar el servicio para obtener los consecutivos
			List<ConsecutivoDTO> consecutivos = this.correspondenciaService.getConsecutivosAnioActual(filtro);

			// debe existir los consecutivos
			assertTrue(consecutivos != null && !consecutivos.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
