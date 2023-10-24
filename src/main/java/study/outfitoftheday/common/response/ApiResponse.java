package study.outfitoftheday.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import study.outfitoftheday.core.web.common.response.ErrorCode;

@Getter
public final class ApiResponse<T> {
	private boolean success;
	private int code;
	private HttpStatus status;
	private String message;
	private T data;

	private ApiResponse(HttpStatus status, String message, T data) {
		this.success = isSuccess(status.value());
		this.code = status.value();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
		return new ApiResponse<>(httpStatus, message, data);
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
		return of(httpStatus, httpStatus.name(), data);
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message) {
		return of(httpStatus, message, null);
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus) {
		return of(httpStatus, httpStatus.name(), null);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return of(HttpStatus.OK, data);
	}

	public static <T> ApiResponse<T> ok() {
		return of(HttpStatus.OK);
	}

	public static <T> ApiResponse<T> created() {
		return of(HttpStatus.CREATED);
	}

	public static <T> ApiResponse<T> badRequest(String message) {
		return of(HttpStatus.BAD_REQUEST, message);
	}

	public static <T> ApiResponse<T> badRequest(ErrorCode errorCode) {
		return of(HttpStatus.BAD_REQUEST, errorCode.getMessage());
	}

	private boolean isSuccess(int code) {
		return code < 400;
	}
}
