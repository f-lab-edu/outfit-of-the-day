package study.outfitoftheday.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import study.outfitoftheday.common.annotation.RequiredAuth;
import study.outfitoftheday.core.domain.auth.exception.NoAccessAuthorizationException;
import study.outfitoftheday.core.domain.auth.service.AuthService;

@Component
@RequiredArgsConstructor
public class RequriedAuthInterceptor implements HandlerInterceptor {
	private final AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		if (handler instanceof HandlerMethod && ((HandlerMethod)handler).hasMethodAnnotation(RequiredAuth.class)) {
			checkAuthorization();
		}
		return true;
	}

	private void checkAuthorization() {
		if (authService.findMemberIdInSession() == null) {
			throw new NoAccessAuthorizationException();
		}
	}
}
