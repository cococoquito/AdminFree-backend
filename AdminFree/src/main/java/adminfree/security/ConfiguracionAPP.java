package adminfree.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import adminfree.constants.ApiRest;
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
	 * Interceptor que aplica para las peticiones http de toda la aplicacion excepto
	 * el paquete de autenticacion
	 */
	@Autowired
	private InterceptorAdminFree interceptorAdminFree;

	/**
	 * Interceptor que aplica para las peticiones http para la autenticacion de APP
	 */
	@Autowired
	private InterceptorAuthAdminFree interceptorAuthAdminFree;

	/**
	 * Metodo que permite agregar los permisos a las solicitudes tales como origen,
	 * metodos http permitidos, parametros header permitidos etc,
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		/**
		 * MaxAge: tiempo que dura la cache por parte del cliente en mantener la
		 * peticion segura y no hacer un Preflighted_requests
		 * 
		 * AllowedHeaders: Si el cliente manda alguna data en el header, esta debe ser
		 * los permitidos por la configuracion establecida
		 * 
		 * AllowedMethods: Se especifica los metodos http permitidos
		 */
		registry.addMapping(Security.GRANTS_PERMITS_ALL.value)
				.allowedHeaders(
						HttpHeaders.CONTENT_TYPE,
						Security.SECURITY_HUSER.value,
						Security.SECURITY_HPASS.value,
						Security.SECURITY_HTOKEN.value)
				.allowedMethods(Security.PERMITS_ALL.value)
				.maxAge(Long.valueOf(Security.MAX_AGE.value));
	}

	/**
	 * Metodo encargado de registrar los interceptores de la APP
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// Es el path y grant de todos los permisos de seguridad
		String grantSecurity = "/" + ApiRest.SEGURIDAD_API + Security.GRANTS_PERMITS_ALL.value;

		// se registra el interceptor para toda la aplicacion excepto seguridad "/**"
		registry.addInterceptor(this.interceptorAdminFree)
				.addPathPatterns(Security.GRANTS_PERMITS_ALL.value)
				.excludePathPatterns(grantSecurity);

		// se registra el interceptor de la autenticacion
		registry.addInterceptor(this.interceptorAuthAdminFree).addPathPatterns(grantSecurity);
	}
}
