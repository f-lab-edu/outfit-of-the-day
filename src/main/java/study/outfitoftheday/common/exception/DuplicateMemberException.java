package study.outfitoftheday.common.exception;

public class DuplicateMemberException extends RuntimeException {
	public DuplicateMemberException() {
		super();
	}

	public DuplicateMemberException(String message) {
		super(message);
	}
}
