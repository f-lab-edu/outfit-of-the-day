package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.ServiceException;

public class MismatchPasswordException extends ServiceException {

	public MismatchPasswordException(String message) {
		super(message);
	}
}
