package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.NomenclaturaCampoDTO;
import adminfree.dtos.configuraciones.NomenclaturaDTO;
import adminfree.dtos.configuraciones.NomenclaturaEdicionDTO;
import adminfree.dtos.configuraciones.RestriccionDTO;
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
			// datos generales de la nomenclatura
			NomenclaturaDTO nomenclatura = new NomenclaturaDTO();
			nomenclatura.setNomenclatura("NEW");
			nomenclatura.setDescripcion("NEW DESCRIPTION");
			nomenclatura.setConsecutivoInicial(10);
			nomenclatura.setId(1L);

			// restricciones
			RestriccionDTO obligatorio = new RestriccionDTO();
			obligatorio.setId(1);
			RestriccionDTO soloNumeros = new RestriccionDTO();
			soloNumeros.setId(2);
			RestriccionDTO unico = new RestriccionDTO();
			unico.setId(3);
			RestriccionDTO unicoTodas = new RestriccionDTO();
			unicoTodas.setId(4);

			NomenclaturaCampoDTO campo1 = new NomenclaturaCampoDTO();
			campo1.setIdCampo(1l);
			campo1.setOrden(1);
			campo1.setRestricciones(new ArrayList<>());
			campo1.getRestricciones().add(obligatorio);
			campo1.getRestricciones().add(soloNumeros);

			NomenclaturaCampoDTO campo2 = new NomenclaturaCampoDTO();
			campo2.setIdCampo(2l);
			campo2.setOrden(2);

			NomenclaturaCampoDTO campo3 = new NomenclaturaCampoDTO();
			campo3.setIdCampo(3l);
			campo3.setOrden(3);

			NomenclaturaCampoDTO campo4 = new NomenclaturaCampoDTO();
			campo4.setIdCampo(4l);
			campo4.setOrden(4);
			campo4.setRestricciones(new ArrayList<>());
			campo4.getRestricciones().add(unico);
			campo4.getRestricciones().add(unicoTodas);

			nomenclatura.agregarCampo(campo1);
			nomenclatura.agregarCampo(campo2);
			nomenclatura.agregarCampo(campo3);
			nomenclatura.agregarCampo(campo4);

			// son los datos a enviar para la edicion
			NomenclaturaEdicionDTO datos = new NomenclaturaEdicionDTO();
			datos.setDatosBasicosEditar(false);
			datos.setCamposEntradaEditar(true);
			datos.setNomenclatura(nomenclatura);

			// se modifica la nomenclatura
			this.configuracionesService.editarNomenclatura(datos);
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
