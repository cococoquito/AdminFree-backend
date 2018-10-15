package adminfree.h.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor que aplica para las peticiones http para la autenticacion del
 * sistema
 * 
 * @author Carlos Andres Diaz
 *
 */
@Component
public class InterceptorAuthAdminFree implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("InterceptorAuthAdminFree fue invocado");
		return true;
	}
}
