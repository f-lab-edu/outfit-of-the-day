package study.outfitoftheday.domain.member.exception;

import study.outfitoftheday.domain.auth.exception.NoAccessAuthenticationException;

public class MismatchPasswordException extends NoAccessAuthenticationException {

	public MismatchPasswordException(String message) {
		super(message);
	}
}
