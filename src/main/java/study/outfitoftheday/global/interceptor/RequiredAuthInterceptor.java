package study.outfitoftheday.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.auth.exception.NoAccessAuthorizationException;
import study.outfitoftheday.domain.auth.service.AuthService;
import study.outfitoftheday.global.annotation.RequiredAuth;

/*
 * @Component
 * Spring Framework에서 컴포넌트 스캔을 통해 빈으로 등록될 클래스를 표시하는데 사용된다.
 * '@Controller, @Service, @Repository 등에서 내부적으로 @Component annotation을 포함하고 있기 때문에 컴포넌트 스캔에 대상이 되고 빈으로 등록된다.
 *
 * */
@Component
@RequiredArgsConstructor
public class RequiredAuthInterceptor implements HandlerInterceptor {
	private final AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		if (handler instanceof HandlerMethod && ((HandlerMethod)handler).hasMethodAnnotation(RequiredAuth.class)) {
			checkAuthorization();
		}
		return true;
	}

	private void checkAuthorization() {
		if (authService.findMemberNicknameInSession() == null) {
			throw new NoAccessAuthorizationException("접근 권한이 없습니다.");
		}
	}
}
