package study.outfitoftheday.core.domain.member.exception;

import study.outfitoftheday.common.exception.ServiceException;
import study.outfitoftheday.core.web.common.response.ErrorCode;

public class DuplicatedMemberException extends ServiceException {
	public DuplicatedMemberException() {
		super(ErrorCode.DUPLICATED_MEMBER);
	}
}
