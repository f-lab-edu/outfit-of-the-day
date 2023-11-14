package study.outfitoftheday.domain.post.exception;

import study.outfitoftheday.global.exception.NotFoundException;

public class NotFoundPostException extends NotFoundException {
	public NotFoundPostException(String message) {
		super(message);
	}
}
