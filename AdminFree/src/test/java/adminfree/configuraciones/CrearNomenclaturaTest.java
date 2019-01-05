package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
			List<Long> idsCampos = new ArrayList<>();
			idsCampos.add(1l);
			idsCampos.add(2l);
			idsCampos.add(3l);
			nomenclatura.setIdsCampos(idsCampos);

			// se procede a crear la nomenclatura
			nomenclatura = this.configuracionesService.crearNomenclatura(nomenclatura);
			assertTrue(nomenclatura != null && nomenclatura.getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
