package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.ServiceException;
import study.outfitoftheday.global.response.ErrorCode;

public class DuplicatedMemberException extends ServiceException {
	public DuplicatedMemberException() {
		super(ErrorCode.DUPLICATED_MEMBER);
	}
}
