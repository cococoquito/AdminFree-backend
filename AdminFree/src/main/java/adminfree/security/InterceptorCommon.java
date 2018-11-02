package adminfree.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import adminfree.enums.MessageBusiness;
import adminfree.dtos.transversal.MessageResponseDTO;

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
		MessageResponseDTO msjResponse = new MessageResponseDTO(MessageBusiness.AUTORIZACION_FALLIDA_401.value);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(new ObjectMapper().writeValueAsString(msjResponse));
		response.getWriter().flush();
		response.getWriter().close();
		return false;
	}
}
