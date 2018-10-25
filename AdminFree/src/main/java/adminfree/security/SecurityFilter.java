package adminfree.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import adminfree.constants.ApiRest;
import adminfree.constants.PropertyKey;
import adminfree.enums.MessageBusiness;
import adminfree.enums.Numero;
import adminfree.enums.Security;
import adminfree.utilities.EstrategiaCriptografica;

/**
 * Filtro para los servicios REST donde se valida los tokens permitidos para los
 * usuarios de cualquier tipo de solicitud HTTP
 * 
 * @author Carlos Andres Diaz
 *
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

	/** Variables de seguridad para la autenticacion del sistema */
	@Value(PropertyKey.SECURITY_POST_ANGULAR_AUTH)
	private String securityPostAngularAuth;
	@Value(PropertyKey.SECURITY_POST_ANGULAR)
	private String securityPostAngular;
	@Value(PropertyKey.SECURITY_AUTH_USER)
	private String securityAuthUser;
	@Value(PropertyKey.SECURITY_AUTH_PASS)
	private String securityAuthPass;
	@Value(PropertyKey.SECURITY_AUTH_TOKEN)
	private String securityAuthToken;
	@Value(PropertyKey.SECURITY_POST_TOKEN)
	private String securityPostToken;

	/**
	 * Metodo que permite validar si el request cumple con las reglas de seguridad
	 * para los recursos REST expuestos por ADMINFREE
	 */
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		try {
			final Integer TREINTA = Numero.TREINTA.value;
			final Integer CINCO = Numero.CINCO.value;

			// se obtiene y se valida la nulalidad del TOKEN
			String token = request.getHeader(Security.SECURITY_HTOKEN.value);
			if (token != null && token.length() > TREINTA) {

				// se valida el codigo de postAngular
				String postAngular = token.substring(token.length() - CINCO);
				if (postAngular.equals(this.securityPostAngularAuth) || 
					postAngular.equals(this.securityPostAngular)) {

					// se obtiene y se valida la nulalidad del USUARIO
					String user = request.getHeader(Security.SECURITY_HUSER.value);
					if (user != null && user.length() > TREINTA) {

						// se obtiene y se valida la nulalidad del PASSWORD
						String pass = request.getHeader(Security.SECURITY_HPASS.value);
						if (pass != null && pass.length() > TREINTA) {

							// se obtiene el TOKEN original
							String soloToken = token.substring(Numero.ZERO.value, token.length() - CINCO);

							// se valida que todos los parametros coincidan con la seguridad
							if (isResquestOK(postAngular, soloToken, user, pass)) {
								filterChain.doFilter(request, response);
								return;
							}
						}
					}
				}
			}
		} catch (Exception e) {}
		returnResponseFallido(response);
	}

	/**
	 * Metodo que permite validar si el request cumple con la seguridad
	 */
	private boolean isResquestOK(
			String postAngular, 
			String soloToken, 
			String usuario, 
			String password) throws Exception {
		
		// verifica si la validacion es para la autenticacion en el sistema
		if (postAngular.equals(this.securityPostAngularAuth)) {
			if (usuario.equals(this.securityAuthUser) && 
				password.equals(this.securityAuthPass) && 
				soloToken.equals(this.securityAuthToken)) {
				return true;
			}
		} 
		// para las demas peticiones se debe validar con las credenciales del usuario
		else {
			String tokenValidar = 
					EstrategiaCriptografica.get().generarTokenAuth(usuario, password, this.securityPostToken);
			if (tokenValidar.equals(soloToken)) {
				return true;
			}
		}
		
		// si llega este punto el request es INVALIDO
		return false;
	}

	/**
	 * Metodo que permite construir el response fallido cuando la solicitud no esta
	 * autorizada para acceder al recurso solicitado
	 */
	private void returnResponseFallido(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader(ApiRest.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		response.getWriter().write(MessageBusiness.AUTORIZACION_FALLIDA.value);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
