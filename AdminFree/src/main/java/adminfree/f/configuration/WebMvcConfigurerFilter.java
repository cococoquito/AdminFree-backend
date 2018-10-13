package adminfree.f.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import adminfree.e.utilities.Constants;

/**
 * Configuration Class that allows to set up all requests of the application WEB
 * 
 * @author Carlos Andres Diaz
 *
 */
@Configuration
public class WebMvcConfigurerFilter {

	/**
	 * Method that allows to grant the permits for all request of the application WEB
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(Constants.GRANTS_PERMITS);
			}
		};
	}
}