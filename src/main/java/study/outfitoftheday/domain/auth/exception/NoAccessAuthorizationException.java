package study.outfitoftheday.domain.auth.exception;

import study.outfitoftheday.global.exception.ServiceException;

public class NoAccessAuthorizationException extends ServiceException {
	public NoAccessAuthorizationException(String message) {
		super(message);
	}
}
