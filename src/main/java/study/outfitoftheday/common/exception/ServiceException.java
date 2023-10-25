package study.outfitoftheday.common.exception;

import study.outfitoftheday.core.web.common.response.ErrorCode;

public class ServiceException extends RuntimeException {
	public ServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
