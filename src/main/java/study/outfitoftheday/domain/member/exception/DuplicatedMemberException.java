package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.ServiceException;

public class DuplicatedMemberException extends ServiceException {
	public DuplicatedMemberException(String message) {
		super(message);
	}
}
