package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
			// datos de la nomenclatura
			NomenclaturaDTO nomenclatura = new NomenclaturaDTO();
			nomenclatura.setId(10l);
			nomenclatura.setNomenclatura("NOME");
			nomenclatura.setDescripcion("descripcion");
			nomenclatura.setConsecutivoInicial(20);

			// campos de la nomenclatura
			NomenclaturaCampoDTO agregar = new NomenclaturaCampoDTO();
			agregar.setIdCampo(4l);
			NomenclaturaCampoDTO editar = new NomenclaturaCampoDTO();
			editar.setId(1L);
			editar.setIdCampo(3L);
			NomenclaturaCampoDTO borrar = new NomenclaturaCampoDTO();
			borrar.setId(2L);
			borrar.setBorrar(true);
			List<NomenclaturaCampoDTO> campos = new ArrayList<>();
			campos.add(agregar);
			campos.add(editar);
			campos.add(borrar);

			// son los datos de la edicion
			NomenclaturaEdicionDTO datos = new NomenclaturaEdicionDTO();
			datos.setNomenclatura(nomenclatura);
			datos.setCampos(campos);
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
