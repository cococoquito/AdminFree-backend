package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.NomenclaturaCampoDTO;
import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.dtos.configuraciones.NomenclaturaEdicionDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.editarNomenclatura
 *
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EditarNomenclaturaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test que permite editar los datos de la nomenclatura
	 */
	@Test
	public void editarNomenclatura() {
		try {
			// se obtiene el detalle de la nomenclatura
			Long idNomenclatura = 3l;
			NomenclaturaDTO detalle = this.configuracionesService.getDetalleNomenclatura(idNomenclatura);
			detalle.setNomenclatura("NomeTest");
			detalle.setDescripcion("DescripcionTest");
			detalle.setConsecutivoInicial(20);

			// campos de la nomenclatura
			List<NomenclaturaCampoDTO> campos = detalle.getCampos();
			for (NomenclaturaCampoDTO campo : campos) {
				campo.setOrden(campo.getOrden() + 1);
			}

			// son los datos de la edicion
			NomenclaturaEdicionDTO datos = new NomenclaturaEdicionDTO();
			datos.setNomenclatura(detalle);
			datos.setCamposEntradaEditar(true);
			datos.setDatosBasicosEditar(true);

			// se modifica la nomenclatura
			this.configuracionesService.editarNomenclatura(datos);
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
