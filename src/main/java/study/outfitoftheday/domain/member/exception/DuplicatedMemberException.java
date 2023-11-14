package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.global.exception.BadRequestException;

public class DuplicatedMemberException extends BadRequestException {
	public DuplicatedMemberException(String message) {
		super(message);
	}
}
