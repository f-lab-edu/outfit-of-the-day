package study.outfitoftheday.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

/*
 * @Slf4j
 * lombok에서 제공하는 어노테이션으로, 개발자로 하여금 Slf4j를 사용하기 위해 작성해야 하는 코드를 생략할 수 있게끔 해준다.
 * @Slf4j를 선언하면 컴파일 시에 아래의 코드를 추가해준다.
 *
 * private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
 *
 * */
@Configuration
@Slf4j
public class SecurityConfig {

	/*
	 * @Value
	 * 스프링에서 제공하는 Annotation으로 설정파일(application.properties or .yml)에서 정의한 값을 코드상으로 의존성 주입할 때 사용한다.
	 * 스프링의 BeanPostProcessor를 통해 이루어진다.
	 * */
	@Value("${security.bcrypt.strength}")
	private int strength;

	/*
	 * @Bean
	 * @Bean을 적용한 메서드가 생성한 객체를 Spring Container에 빈으로 등록하도록 지정하는 Annotation을 의미한다.
	 * */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}
}
