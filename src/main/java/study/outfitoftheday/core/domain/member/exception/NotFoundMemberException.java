package study.outfitoftheday.core.domain.member.exception;

import study.outfitoftheday.common.exception.ServiceException;
import study.outfitoftheday.core.web.common.response.ErrorCode;

public class NotFoundMemberException extends ServiceException {
	public NotFoundMemberException() {
		super(ErrorCode.NOT_FOUND_MEMBER);
	}
}
