package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.ServiceException;

public class NotFoundMemberException extends ServiceException {
	public NotFoundMemberException(String message) {
		super(message);
	}
}
