package study.outfitoftheday.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.sentry.Sentry;

/*
 * @ConditionalOnProperty
 * 외부 설정 파일에 따라 빈을 동적으로 등록/제외할 경우에 사용
 * 외부 설정 파일의 속성 값의 유무에 따라 빈으로 등록할지 정할 수 있다.
 * */
@Configuration
@ConditionalOnProperty(value = "sentry.enabled", havingValue = "true", matchIfMissing = false)
public class SentryConfig {
	
	@Value("${sentry.dsn}")
	private String dsn;
	
	@Bean
	public void init() {
		Sentry.init(dsn);
	}
}
