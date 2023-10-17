package study.outfitoftheday.common.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		return Optional.empty();
	}
}
