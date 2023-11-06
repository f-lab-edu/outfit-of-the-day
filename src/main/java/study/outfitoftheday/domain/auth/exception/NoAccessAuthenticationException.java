package study.outfitoftheday.domain.auth.exception;

public class NoAccessAuthenticationException extends RuntimeException {
	public NoAccessAuthenticationException(String message) {
		super(message);
	}
}
