package study.outfitoftheday.domain.post.exception;

import study.outfitoftheday.global.exception.BadRequestException;

public class InvalidPostException extends BadRequestException {
	public InvalidPostException(String message) {
		super(message);
	}
}
