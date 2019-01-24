package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.validarCamposIngresoInformacion
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidarCamposIngresoInformacionTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite validar los campos de ingreso de informacion
	 */
	@Test
	public void validarCamposIngresoInformacion() {
		try {
			// DTO con los datos de la solicitud
			SolicitudConsecutivoDTO solicitud = new SolicitudConsecutivoDTO();
			solicitud.setIdNomenclatura(1l);
			solicitud.setIdCliente(1L);

			// valor uno para la solicitud
			CampoEntradaValueDTO campo1 = new CampoEntradaValueDTO();
			campo1.setValue("texto");
			campo1.agregarRestriccion("1");
			campo1.agregarRestriccion("2");

			// se agrega los valores para esta solicitud
			solicitud.agregarValor(campo1);

			// se hace el llamado para ejecutar las validaciones
			List<MessageResponseDTO> respuesta = this.correspondenciaService.validarCamposIngresoInformacion(solicitud);
			assertTrue(respuesta == null || respuesta.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
