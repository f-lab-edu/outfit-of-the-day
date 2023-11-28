package study.outfitoftheday.domain.like.exception;

import study.outfitoftheday.global.exception.NotFoundException;

public class NotFoundPostLikeException extends NotFoundException {

	public NotFoundPostLikeException(String message) {
		super(message);
	}
}
