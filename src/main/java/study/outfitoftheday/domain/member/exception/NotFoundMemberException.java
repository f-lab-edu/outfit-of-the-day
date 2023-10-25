package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.ServiceException;
import study.outfitoftheday.global.response.ErrorCode;

public class NotFoundMemberException extends ServiceException {
	public NotFoundMemberException() {
		super(ErrorCode.NOT_FOUND_MEMBER);
	}
}
