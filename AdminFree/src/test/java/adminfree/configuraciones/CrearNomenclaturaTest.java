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
import adminfree.dtos.configuraciones.RestriccionDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.crearNomenclatura
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrearNomenclaturaTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test para la creacion de la nomenclatura
	 */
	@Test
	public void crearNomenclatura() {
		try {
			// se construye la nomenclatura a crear
			NomenclaturaDTO nomenclatura = new NomenclaturaDTO();
			nomenclatura.setNomenclatura("DD");
			nomenclatura.setDescripcion("DESCRIPCION DE LA DD");
			nomenclatura.setIdCliente(1L);
			nomenclatura.setConsecutivoInicial(11);

			// restricciones
			RestriccionDTO obligatorio = new RestriccionDTO();
			obligatorio.setId(1);
			RestriccionDTO soloNumeros = new RestriccionDTO();
			soloNumeros.setId(2);
			RestriccionDTO unico = new RestriccionDTO();
			unico.setId(3);
			RestriccionDTO unicoTodas = new RestriccionDTO();
			unicoTodas.setId(4);

			// se construye los campos a insertar
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

			nomenclatura.agregarCampos(campo1);
			nomenclatura.agregarCampos(campo2);
			nomenclatura.agregarCampos(campo3);
			nomenclatura.agregarCampos(campo4);

			// se procede a crear la nomenclatura
			nomenclatura = this.configuracionesService.crearNomenclatura(nomenclatura);
			assertTrue(nomenclatura != null && nomenclatura.getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
