package adminfree.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
	 * Filtro para la seguridad de los servicios REST
	 */
	@Autowired
	private SecurityFilter securityFilter;

	/**
	 * Metodo que permite agregar los permisos a las solicitudes tales como origen,
	 * metodos http permitidos, parametros header permitidos etc,
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(Security.GRANTS_PERMITS_ALL.value);
	}
	
	/**
	 * Metodo que permite agregar el filtro de seguridad del sistema
	 */
	@Bean
	public FilterRegistrationBean<SecurityFilter> dawsonApiFilter() {
		FilterRegistrationBean<SecurityFilter> registration = new FilterRegistrationBean<SecurityFilter>();
		registration.setFilter(this.securityFilter);
		return registration;
	}
}
