package study.outfitoftheday.domain.auth.exception;

import study.outfitoftheday.global.exception.ServiceException;
import study.outfitoftheday.global.response.ErrorCode;

public class NoAccessAuthorizationException extends ServiceException {
	public NoAccessAuthorizationException() {
		super(ErrorCode.NO_ACCESS_AUTHORIZATION);
	}

}
