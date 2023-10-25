package study.outfitoftheday.core.domain.auth.exception;

import study.outfitoftheday.common.exception.ServiceException;
import study.outfitoftheday.core.web.common.response.ErrorCode;

public class NoAccessAuthorizationException extends ServiceException {
	public NoAccessAuthorizationException() {
		super(ErrorCode.NO_ACCESS_AUTHORIZATION);
	}

}
