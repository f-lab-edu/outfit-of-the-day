package study.outfitoftheday.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.sentry.Sentry;

/*
 * @Profile
 * 특정 속성값에 지정한 환경에서만 유효한 클래스임을 지정
 * */
@Configuration
@Profile("prod")
public class SentryConfig {
	@Value("${sentry.dsn}")
	private String dsn;
	
	@Bean
	public void init() {
		Sentry.init(dsn);
	}
}
