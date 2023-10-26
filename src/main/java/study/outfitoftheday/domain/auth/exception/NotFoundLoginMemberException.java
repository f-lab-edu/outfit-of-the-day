package study.outfitoftheday.domain.auth.exception;

import study.outfitoftheday.global.exception.ServiceException;

public class NotFoundLoginMemberException extends ServiceException {
	public NotFoundLoginMemberException(String message) {
		super(message);
	}
}
