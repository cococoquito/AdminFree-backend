package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.constants.CommonConstant;
import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.transversal.MessageResponseDTO;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.validarCamposIngresoInformacion
 * 
 * @author Carlos Andres Diaz
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
			campo1.setNombreCampo("Numerso SAC Armenia");
			campo1.setIdCampo(6l);
			campo1.setValue("4312");
			campo1.agregarRestriccion(CommonConstant.KEY_CAMPO_UNICO_NOMENCLATURA);

			CampoEntradaValueDTO campo2 = new CampoEntradaValueDTO();
			campo2.setIdValue(11L);
			campo2.setNombreCampo("Asunto Armenia");
			campo2.setIdCampo(4l);
			campo2.setValue("asunto armenia");
			campo2.agregarRestriccion(CommonConstant.KEY_CAMPO_TODAS_NOMENCLATURA);

			// se agrega los valores para esta solicitud
			solicitud.agregarValor(campo1);
			solicitud.agregarValor(campo2);

			// se hace el llamado para ejecutar las validaciones
			List<MessageResponseDTO> respuesta = this.correspondenciaService.validarCamposIngresoInformacion(solicitud);
			assertTrue(respuesta == null || respuesta.isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
