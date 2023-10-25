package study.outfitoftheday.global.exception;

import study.outfitoftheday.global.response.ErrorCode;

public class ServiceException extends RuntimeException {
	public ServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
