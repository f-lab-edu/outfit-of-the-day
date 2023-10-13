package study.outfitoftheday.common.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig implements AuditorAware<Long> {
	@Override
	public Optional<Long> getCurrentAuditor() {
		/*
		* Todo
		*  인증/인가 구현 이후에 적용
		* */
		return null;
	}
}
