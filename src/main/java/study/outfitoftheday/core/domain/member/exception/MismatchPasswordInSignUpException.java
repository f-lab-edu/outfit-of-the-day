package study.outfitoftheday.core.domain.member.exception;

import study.outfitoftheday.common.exception.ServiceException;
import study.outfitoftheday.core.web.common.response.ErrorCode;

public class MismatchPasswordInSignUpException
	extends ServiceException {

	public MismatchPasswordInSignUpException() {
		super(ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP);
	}
}
