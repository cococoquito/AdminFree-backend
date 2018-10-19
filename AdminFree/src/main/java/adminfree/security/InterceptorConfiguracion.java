package adminfree.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import adminfree.constants.ApiRest;
import adminfree.enums.Security;

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
		
		// Es el path y grant de todos los permisos de seguridad
		String grantSecurity = "/" + ApiRest.SEGURIDAD_API + Security.GRANTS_PERMITS_ALL.value;
				
		// se registra el interceptor para toda la aplicacion excepto seguridad "/**"
		registry.addInterceptor(this.interceptorAdminFree).addPathPatterns(Security.GRANTS_PERMITS_ALL.value)
				.excludePathPatterns(grantSecurity);

		// se registra el interceptor de la autenticacion
		registry.addInterceptor(this.interceptorAuthAdminFree).addPathPatterns(grantSecurity);
	}
}
