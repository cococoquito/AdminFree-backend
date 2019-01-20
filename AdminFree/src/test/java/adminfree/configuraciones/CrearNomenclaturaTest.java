package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.NomenclaturaCampoDTO;
import adminfree.dtos.configuraciones.NomenclaturaDTO;
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
			nomenclatura.setNomenclatura("DT");
			nomenclatura.setDescripcion("DESCRIPCION DE LA DT");
			nomenclatura.setIdCliente(1L);
			nomenclatura.setConsecutivoInicial(1);

			// se construye los campos a insertar
			NomenclaturaCampoDTO campo1 = new NomenclaturaCampoDTO();
			campo1.setIdCampo(1l);
			campo1.setOrden(1);

			NomenclaturaCampoDTO campo2 = new NomenclaturaCampoDTO();
			campo2.setIdCampo(2l);
			campo2.setOrden(2);

			NomenclaturaCampoDTO campo3 = new NomenclaturaCampoDTO();
			campo3.setIdCampo(3l);
			campo3.setOrden(3);

			nomenclatura.agregarCampos(campo1);
			nomenclatura.agregarCampos(campo2);
			nomenclatura.agregarCampos(campo3);

			// se procede a crear la nomenclatura
			nomenclatura = this.configuracionesService.crearNomenclatura(nomenclatura);
			assertTrue(nomenclatura != null && nomenclatura.getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
