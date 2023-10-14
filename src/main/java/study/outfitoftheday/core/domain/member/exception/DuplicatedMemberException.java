package study.outfitoftheday.core.domain.member.exception;

public class DuplicatedMemberException extends RuntimeException {
	public DuplicatedMemberException() {
		super();
	}

	public DuplicatedMemberException(String message) {
		super(message);
	}
}
