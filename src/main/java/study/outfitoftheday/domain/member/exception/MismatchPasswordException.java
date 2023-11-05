package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.BadRequestException;

public class MismatchPasswordException extends BadRequestException {

	public MismatchPasswordException(String message) {
		super(message);
	}
}
