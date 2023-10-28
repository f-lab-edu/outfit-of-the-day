package study.outfitoftheday.domain.auth.exception;

public class NotFoundLoginMemberException extends NoAccessAuthenticationException {
	public NotFoundLoginMemberException(String message) {
		super(message);
	}
}
