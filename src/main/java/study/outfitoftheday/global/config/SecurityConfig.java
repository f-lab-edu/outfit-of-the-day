package study.outfitoftheday.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Slf4j
 * lombok에서 제공하는 어노테이션으로, 개발자로 하여금 Slf4j를 사용하기 위해 작성해야 하는 코드를 생략할 수 있게끔 해준다.
 * @Slf4j를 선언하면 컴파일 시에 아래의 코드를 추가해준다.
 *
 * private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
 *
 * */
@Configuration
public class SecurityConfig {
	/*
	 * @Bean
	 * @Bean을 적용한 메서드가 생성한 객체를 Spring Container에 빈으로 등록하도록 지정하는 Annotation을 의미한다.
	 * */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new OneWayPasswordEncoder();
	}
}
