package adminfree.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import adminfree.enums.Security;

/**
 * Configuraciones generales de spring boot
 * 
 * @author Carlos Andres Diaz
 *
 */
@Configuration
public class ConfiguracionAPP implements WebMvcConfigurer {

	/**
	 * Metodo que permite agregar los permisos a las solicitudes tales como origen,
	 * metodos http permitidos, parametros header permitidos etc,
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(Security.GRANTS_PERMITS_ALL.value);
	}
}
