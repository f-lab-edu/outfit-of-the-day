package study.outfitoftheday.domain.auth.exception;

import study.outfitoftheday.global.exception.BadRequestException;

public class NotFoundLoginMemberException extends BadRequestException {
	public NotFoundLoginMemberException(String message) {
		super(message);
	}
}
