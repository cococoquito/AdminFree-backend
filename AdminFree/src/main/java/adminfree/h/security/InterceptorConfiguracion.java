package adminfree.h.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import adminfree.e.utilities.Constants;

/**
 * Configuracion que permite registrar los interceptores de la aplicacion
 * 
 * @author Carlos Andres Diaz
 *
 */
@Configuration
public class InterceptorConfiguracion implements WebMvcConfigurer {
	
	/**
	 * Interceptor que aplica para las peticiones http de toda la aplicacion excepto
	 * el paquete de autenticacion
	 */
	@Autowired
	private InterceptorAdminFree interceptorAdminFree;
	
	/**
	 * Interceptor que aplica para las peticiones http para la autenticacion de la app
	 */
	@Autowired
	private InterceptorAuthAdminFree interceptorAuthAdminFree;
	
	/**
	 * Metodo encargado de registrar los interceptores de la app
	 */	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
				
		// se registra el interceptor para toda la aplicacion excepto seguridad
		registry.addInterceptor(this.interceptorAdminFree).addPathPatterns("/**")
				.excludePathPatterns("/" + Constants.SEGURIDAD_NOMBRE_API + "/**");

		// se registra el interceptor de la autenticacion
		registry.addInterceptor(this.interceptorAuthAdminFree)
				.addPathPatterns("/" + Constants.SEGURIDAD_NOMBRE_API + "/**");
	}
}
