package study.outfitoftheday.core.domain.member.exception;

public class DuplicateMemberException extends RuntimeException {
	public DuplicateMemberException() {
		super();
	}

	public DuplicateMemberException(String message) {
		super(message);
	}
}
