package study.outfitoftheday.domain.post.exception;

import study.outfitoftheday.domain.auth.exception.NoAccessAuthorizationException;

public class NoAuthorizationToAccessPostException extends NoAccessAuthorizationException {
	public NoAuthorizationToAccessPostException(String message) {
		super(message);
	}
}
