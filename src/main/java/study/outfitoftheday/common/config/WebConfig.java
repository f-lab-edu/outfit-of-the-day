package study.outfitoftheday.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.common.interceptor.RequiredAuthInterceptor;
import study.outfitoftheday.common.resolver.LoginMemberArgumentResolver;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final RequiredAuthInterceptor requriedAuthInterceptor;
	private final LoginMemberArgumentResolver loginMemberArgumentResolver;

	/*
	 * @Override
	 * 부모 클래스 혹은 interface로 부터 메서드를 제정의를 할 때 적용하는 Annotation으로
	 * 컴파일 시 재정의 조건을 만족시키지 못한 method일 경우 error를 발생시킨다.
	 *
	 * */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requriedAuthInterceptor);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginMemberArgumentResolver);
	}
}
