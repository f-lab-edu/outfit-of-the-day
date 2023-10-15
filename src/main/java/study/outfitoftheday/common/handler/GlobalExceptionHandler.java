package study.outfitoftheday.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.core.domain.auth.exception.NoAccessAuthorizationException;
import study.outfitoftheday.core.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.core.domain.member.exception.MismatchPasswordInSignUpException;
import study.outfitoftheday.core.web.common.response.ApiErrorResponse;
import study.outfitoftheday.core.web.common.response.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {

		BindingResult bindingResult = e.getBindingResult();
		String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
		ApiErrorResponse response = ApiErrorResponse.of(HttpStatus.BAD_REQUEST, message);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({DuplicatedMemberException.class})
	protected ResponseEntity<ApiErrorResponse> handleDuplicateMemberException() {
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.DUPLICATED_MEMBER);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MismatchPasswordInSignUpException.class)
	protected ResponseEntity<ApiErrorResponse> handleMismatchPasswordInSignUpException() {
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoAccessAuthorizationException.class)
	protected ResponseEntity<ApiErrorResponse> handleNoAccessAuthorizationException() {
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.NO_ACCESS_AUTHORIZATION);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
