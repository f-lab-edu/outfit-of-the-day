package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.ServiceException;
import study.outfitoftheday.global.response.ErrorCode;

public class MismatchPasswordInLoginException extends ServiceException {

	public MismatchPasswordInLoginException() {
		super(ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP);
	}
}
