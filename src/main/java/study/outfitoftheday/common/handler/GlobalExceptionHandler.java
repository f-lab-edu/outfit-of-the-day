package study.outfitoftheday.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import study.outfitoftheday.common.response.ApiResponse;
import study.outfitoftheday.core.domain.auth.exception.NoAccessAuthorizationException;
import study.outfitoftheday.core.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.core.domain.member.exception.MismatchPasswordInSignUpException;
import study.outfitoftheday.core.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.core.web.common.response.ErrorCode;

/*
 * @RestControllerAdvice
 * 전역적인 예외 처리를 담당하는 클래스에 적용하는 Annotation이다.
 * '@ControllerAdvice와 유사하지만 Rest Api에서 발생한 예외에 대한 처리에 더 특화되어 있다.
 *
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * @ExceptionHandler
	 * 해당 annotation에 지정한 xException.class에 예외가 발생했을 경우 특정 예외에 대해 처리를 할 수 있도록 지정하는 annotation.
	 *
	 * @ResponseStatus
	 * Http 응답 상태 코드를 지정하는 annotation
	 * */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Object> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {

		BindingResult bindingResult = e.getBindingResult();
		String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
		return ApiResponse.badRequest(message);
	}

	@ExceptionHandler({DuplicatedMemberException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Object> handleDuplicateMemberException() {
		return ApiResponse.badRequest(ErrorCode.DUPLICATED_MEMBER);
	}

	@ExceptionHandler(MismatchPasswordInSignUpException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Object> handleMismatchPasswordInSignUpException() {
		return ApiResponse.badRequest(ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP);
	}

	@ExceptionHandler(NoAccessAuthorizationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Object> handleNoAccessAuthorizationException() {
		return ApiResponse.badRequest(ErrorCode.NO_ACCESS_AUTHORIZATION);
	}

	@ExceptionHandler(NotFoundMemberException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Object> handleNotFoundMemberException() {
		return ApiResponse.badRequest(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION);
	}

}
