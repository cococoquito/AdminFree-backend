package adminfree.configuraciones;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import adminfree.dtos.configuraciones.ClienteDTO;
import adminfree.dtos.seguridad.UsuarioDTO;
import adminfree.services.ConfiguracionesService;

/**
 * Test para el servicio ConfiguracionesService.crearUsuario
 * 
 * @author Carlos Andres Diaz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrearUsuarioTest {

	/** Service que contiene las configuraciones del sistema */
	@Autowired
	private ConfiguracionesService configuracionesService;

	/**
	 * Test para la creacion del CLIENTE
	 */
	@Test
	public void crearUsuario() {
		try {
			// se construye las entidades asociados
			UsuarioDTO usuarioCrear = new UsuarioDTO();
			ClienteDTO cliente = new ClienteDTO();
			usuarioCrear.setCliente(cliente);

			// se configura los datos necesarios para la insercion
			usuarioCrear.setNombre("Cristopher Diaz");
			usuarioCrear.setUsuarioIngreso("crisdiaz");
			cliente.setId(1L);
			usuarioCrear.agregarModuloToken("9f1124f946de506332f221a01d39c411");
			usuarioCrear.agregarModuloToken("a9ee4d222a05960a00ea9b5eb6fb0a81");
			usuarioCrear.agregarModuloToken("b6177afc6e3818cb7cb663cf71ef1ce4");
			usuarioCrear.agregarModuloToken("4813e117da87e1ae5bdfb0d4967f4387");

			// se crea el usuario
			UsuarioDTO usuarioCreado = this.configuracionesService.crearUsuario(usuarioCrear);

			// el usuario retornado debe contener el identificador
			assertTrue(usuarioCreado != null && usuarioCreado.getId() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
