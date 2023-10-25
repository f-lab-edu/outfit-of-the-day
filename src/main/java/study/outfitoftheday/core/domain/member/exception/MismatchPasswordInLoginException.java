package study.outfitoftheday.core.domain.member.exception;

import study.outfitoftheday.common.exception.ServiceException;
import study.outfitoftheday.core.web.common.response.ErrorCode;

public class MismatchPasswordInLoginException extends ServiceException {

	public MismatchPasswordInLoginException() {
		super(ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP);
	}
}
