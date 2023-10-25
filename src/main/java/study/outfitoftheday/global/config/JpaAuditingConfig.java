package study.outfitoftheday.global.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/*
 * @Configuration
 * 스프링의 설정 클래스라는 의미를 가진다.
 * 내부에는 하나 이상의 @Bean 메서드를 포함하고 있음을 나타낸다.
 * Spring은 해당 어노테이션이 달린 클래스를 찾아서 해당 클래스의 @Bean 메서드들을 호출하고, 그 결과로 생성된 빈 객체들을 Spring Container에 등록한다.
 *
 *
 * @EnableJpaAuditing
 * JpaAuditing은 '감시하다' 뜻 그대로 Entity를 보고 있다가 DB에 저장하거나 업데이트를 하는 경우
 * 생얼일자, 생성자 등 컬럼을 자동으로 DB에 반영해주는 기능을 '활성화'시키는 Annotation이다.
 *
 * @RequiredArgsConstructor
 * private final로 선언한 속성들을 parameter로 갖는 생성자를 컴파일시 만들어주는 Annotation이다.
 *
 * public JpaAuditingConfig(final HttpSession httpSession) {
        this.httpSession = httpSession;
    }

 * */
@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaAuditingConfig {
	private static final String SESSION_AUTH_KEY = "MEMBER_ID";
	private final HttpSession httpSession;

	@Bean
	public AuditorAware<Long> auditorProvider() {
		return () -> Optional.ofNullable((Long)httpSession.getAttribute(SESSION_AUTH_KEY));
	}
}
