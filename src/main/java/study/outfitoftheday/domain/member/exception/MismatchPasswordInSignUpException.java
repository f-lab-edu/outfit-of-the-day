package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.ServiceException;
import study.outfitoftheday.global.response.ErrorCode;

public class MismatchPasswordInSignUpException
	extends ServiceException {

	public MismatchPasswordInSignUpException() {
		super(ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP);
	}
}
