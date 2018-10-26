package adminfree.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import adminfree.constants.PropertyKey;
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
public class InterceptorAuthAdminFree extends InterceptorCommon {

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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			// valida si el request es preflighted, donde NO se debe tener en cuenta
			if (isPreflightedRequests(request)) {
				return true;
			}

			// constante para validar el tamano de cada parametro header
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
		} catch (Exception e) {
			return false;
		}
	}
}
