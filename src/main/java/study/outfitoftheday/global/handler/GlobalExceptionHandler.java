package study.outfitoftheday.global.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.domain.auth.exception.NoAccessAuthenticationException;
import study.outfitoftheday.domain.auth.exception.NoAccessAuthorizationException;
import study.outfitoftheday.global.exception.BadRequestException;
import study.outfitoftheday.global.exception.NotFoundException;
import study.outfitoftheday.global.response.ApiResponse;

/*
 * @RestControllerAdvice
 * 전역적인 예외 처리를 담당하는 클래스에 적용하는 Annotation이다.
 * '@ControllerAdvice와 유사하지만 Rest Api에서 발생한 예외에 대한 처리에 더 특화되어 있다.
 *
 * */
@RestControllerAdvice
@Slf4j
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
	protected ApiResponse<Void> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		
		BindingResult bindingResult = e.getBindingResult();
		String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
		return ApiResponse.badRequest(message);
	}
	
	@ExceptionHandler({BadRequestException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Void> handleServiceException(BadRequestException e) {
		return ApiResponse.badRequest(e.getMessage());
	}
	
	@ExceptionHandler({NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ApiResponse<Void> handleNotFoundException(NotFoundException e) {
		return ApiResponse.notFound(e.getMessage());
	}
	
	@ExceptionHandler({NoAccessAuthorizationException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	protected ApiResponse<Void> handleNoAccessAuthorizationException(NoAccessAuthorizationException e) {
		return ApiResponse.forbidden(e.getMessage());
	}
	
	@ExceptionHandler({NoAccessAuthenticationException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	protected ApiResponse<Void> handleNoAccessAuthenticationException(NoAccessAuthenticationException e) {
		return ApiResponse.unauthorized(e.getMessage());
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ApiResponse<Void> handleException(Exception e) {
		log.error(e.getMessage());
		return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
