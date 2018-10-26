package adminfree.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import adminfree.enums.MessageBusiness;

/**
 * Contiene los metodos comunes para los interceptores de la APP
 * 
 * @author Carlos Andres Diaz
 *
 */
public class InterceptorCommon implements HandlerInterceptor {

	/**
	 * Metodo que permite validar si el request es preflighted, donde
	 * request-preflighted no es el request principal
	 */
	protected boolean isPreflightedRequests(HttpServletRequest request) {
		return HttpMethod.OPTIONS.name().equals(request.getMethod());
	}

	/**
	 * Metodo que permite construir el response fallido cuando la solicitud no esta
	 * autorizada para acceder al recurso solicitado
	 */
	protected boolean returnResponseFallido(HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		response.getWriter().write(MessageBusiness.AUTORIZACION_FALLIDA.value);
		response.getWriter().flush();
		response.getWriter().close();
		return false;
	}
}
