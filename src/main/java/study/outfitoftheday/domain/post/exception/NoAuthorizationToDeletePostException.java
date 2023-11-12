package study.outfitoftheday.domain.post.exception;

import study.outfitoftheday.domain.auth.exception.NoAccessAuthorizationException;

public class NoAuthorizationToDeletePostException extends NoAccessAuthorizationException {
	public NoAuthorizationToDeletePostException(String message) {
		super(message);
	}
}
