package adminfree.correspondencia;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.correspondencia.CampoEntradaValueDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoDTO;
import adminfree.dtos.correspondencia.SolicitudConsecutivoResponseDTO;
import adminfree.enums.TipoCampo;
import adminfree.services.CorrespondenciaService;

/**
 * Test para el servicio CorrespondenciaService.solicitarConsecutivo
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolicitarConsecutivoTest {

	/**
	 * Objecto que contiene los servicios relacionados modulo correspondencia
	 */
	@Autowired
	private CorrespondenciaService correspondenciaService;

	/**
	 * Test que permite probar el metodo de solicitar consecutivo
	 */
	@Test
	public void solicitarConsecutivo() {
		try {
			// datos basicos de la solicitud
			SolicitudConsecutivoDTO solicitud = new SolicitudConsecutivoDTO();
			solicitud.setIdCliente(1L);
			solicitud.setIdNomenclatura(1L);
			solicitud.setIdUsuario(1L);

			// valores de la solicitud
			CampoEntradaValueDTO campo1 = new CampoEntradaValueDTO();
			campo1.setTipoCampo(TipoCampo.LISTA_DESPLEGABLE.id);
			campo1.setIdCampoNomenclatura(1L);
			campo1.setValue(1);

			CampoEntradaValueDTO campo2 = new CampoEntradaValueDTO();
			campo2.setTipoCampo(TipoCampo.CAMPO_TEXTO.id);
			campo2.setIdCampoNomenclatura(2L);
			campo2.setValue("Este es el valor del campo");

			CampoEntradaValueDTO campo3 = new CampoEntradaValueDTO();
			campo3.setTipoCampo(TipoCampo.CAMPO_FECHA.id);
			campo3.setIdCampoNomenclatura(3L);
			campo3.setValue(Calendar.getInstance().getTime());

			CampoEntradaValueDTO campo4 = new CampoEntradaValueDTO();
			campo4.setTipoCampo(TipoCampo.CASILLA_VERIFICACION.id);
			campo4.setIdCampoNomenclatura(4L);
			campo4.setValue(true);

			solicitud.agregarValor(campo1);
			solicitud.agregarValor(campo2);
			solicitud.agregarValor(campo3);
			solicitud.agregarValor(campo4);

			// se hace el llamado para solicitar un consecutivo
			SolicitudConsecutivoResponseDTO response = this.correspondenciaService.solicitarConsecutivo(solicitud);

			// debe existir el consecutivo para esta invocacion
			assertTrue(response != null && response.getConsecutivo() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
