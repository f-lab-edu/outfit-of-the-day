package study.outfitoftheday.core.domain.auth.exception;

import study.outfitoftheday.common.exception.ServiceException;
import study.outfitoftheday.core.web.common.response.ErrorCode;

public class NotFoundLoginMemberException extends ServiceException {
	public NotFoundLoginMemberException() {
		super(ErrorCode.NOT_FOUND_LOGIN_MEMBER);
	}
}
