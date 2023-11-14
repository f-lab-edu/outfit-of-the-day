package study.outfitoftheday.domain.auth.exception;

import study.outfitoftheday.global.exception.BadRequestException;

public class NoAccessAuthorizationException extends BadRequestException {
	public NoAccessAuthorizationException(String message) {
		super(message);
	}
}
