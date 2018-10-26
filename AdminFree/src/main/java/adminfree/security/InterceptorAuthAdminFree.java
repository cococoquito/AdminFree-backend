package adminfree.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import adminfree.constants.PropertyKey;
import adminfree.enums.MessageBusiness;
import adminfree.enums.Numero;
import adminfree.enums.Security;

/**
 * Interceptor que aplica para las peticiones http para la autenticacion del
 * sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
@Component
public class InterceptorAuthAdminFree implements HandlerInterceptor {

	/** Variables de seguridad para la autenticacion del sistema */
	@Value(PropertyKey.SECURITY_POST_ANGULAR_AUTH)
	private String securityPostAngularAuth;
	@Value(PropertyKey.SECURITY_AUTH_USER)
	private String securityAuthUser;
	@Value(PropertyKey.SECURITY_AUTH_PASS)
	private String securityAuthPass;
	@Value(PropertyKey.SECURITY_AUTH_TOKEN)
	private String securityAuthToken;

	/**
	 * Metodo que se ejecuta antes de procesar la peticion HTTP, solo aplica para
	 * las peticiones de autenticacion
	 * 
	 * @return true si la peticion es valida, de lo contrario false
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		final Integer TREINTA = Numero.TREINTA.value;
		
		// se obtiene y se valida la nulalidad del USUARIO
		String user = request.getHeader(Security.SECURITY_HUSER.value);
		if (user != null && user.length() > TREINTA) {

			// se obtiene y se valida la nulalidad del PASSWORD
			String pass = request.getHeader(Security.SECURITY_HPASS.value);
			if (pass != null && pass.length() > TREINTA) {

				// se obtiene y se valida la nulalidad del TOKEN
				String token = request.getHeader(Security.SECURITY_HTOKEN.value);
				if (token != null && token.length() > TREINTA) {

					// se valida el codigo de postAngular
					String postAngular = token.substring(token.length() - this.securityPostAngularAuth.length());
					if (postAngular.equals(this.securityPostAngularAuth)) {
						
						// se valida que todos los parametros coincidan con la seguridad
						String soloToken = token.substring(Numero.ZERO.value, token.length() - this.securityPostAngularAuth.length());
						if (user.equals(this.securityAuthUser) &&
							pass.equals(this.securityAuthPass) &&
							soloToken.equals(this.securityAuthToken)) {
							return true;
						}
					}
				}
			}
		}

		// la solicitud no tiene permisos para el recurso solicitado
		return returnResponseFallido(response);
	}
	
	/**
	 * Metodo que permite construir el response fallido cuando la solicitud no esta
	 * autorizada para acceder al recurso solicitado
	 */
	private boolean returnResponseFallido(HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		response.getWriter().write(MessageBusiness.AUTORIZACION_FALLIDA.value);
		response.getWriter().flush();
		response.getWriter().close();
		return false;
	}	
}
