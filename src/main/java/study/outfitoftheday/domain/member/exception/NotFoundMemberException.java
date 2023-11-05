package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.NotFoundException;

public class NotFoundMemberException extends NotFoundException {
	public NotFoundMemberException(String message) {
		super(message);
	}
}
