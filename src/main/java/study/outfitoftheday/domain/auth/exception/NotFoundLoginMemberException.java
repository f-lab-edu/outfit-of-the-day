package study.outfitoftheday.domain.auth.exception;

import study.outfitoftheday.global.exception.ServiceException;
import study.outfitoftheday.global.response.ErrorCode;

public class NotFoundLoginMemberException extends ServiceException {
	public NotFoundLoginMemberException() {
		super(ErrorCode.NOT_FOUND_LOGIN_MEMBER);
	}
}
