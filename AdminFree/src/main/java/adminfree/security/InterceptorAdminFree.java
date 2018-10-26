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
import adminfree.utilities.EstrategiaCriptografica;

/**
 * Interceptor que aplica para las peticiones http de toda la aplicacion excepto
 * el paquete de autenticacion
 * 
 * @author Carlos Andres Diaz
 *
 */
@Component
public class InterceptorAdminFree implements HandlerInterceptor {
	
	/** Variables de seguridad para la autenticacion del sistema */
	@Value(PropertyKey.SECURITY_POST_ANGULAR)
	private String securityPostAngular;
	@Value(PropertyKey.SECURITY_POST_TOKEN)
	private String securityPostToken;	

	/**
	 * Metodo que se ejecuta antes de procesar la peticion HTTP de cualquier modulo
	 * excepto seguridad
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
					String postAngular = token.substring(token.length() - this.securityPostAngular.length());
					if (postAngular.equals(this.securityPostAngular)) {
						
						// se valida si el TOKEN es valido
						String tokenValidar = EstrategiaCriptografica.get().generarTokenAuth(user, pass, this.securityPostToken);
						String soloToken = token.substring(Numero.ZERO.value, token.length() - this.securityPostAngular.length());
						if (tokenValidar.equals(soloToken)) {
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
