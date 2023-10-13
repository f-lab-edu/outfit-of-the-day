package study.outfitoftheday.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.common.exception.DuplicateMemberException;
import study.outfitoftheday.core.web.common.response.ApiErrorResponse;
import study.outfitoftheday.core.web.common.response.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.DUPLICATE_MEMBER);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(DuplicateMemberException.class)
	protected ResponseEntity<ApiErrorResponse> handleDuplicateMemberException(
		DuplicateMemberException e) {
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.DUPLICATE_MEMBER);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
