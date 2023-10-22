package study.outfitoftheday.core.web.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public final class ApiErrorResponse {
	private final boolean success;
	private final int status;
	private final String message;

	private ApiErrorResponse(int status, String message) {
		this.success = false;
		this.status = status;
		this.message = message;
	}

	public static ApiErrorResponse of(int status, String message) {
		return new ApiErrorResponse(status, message);
	}

	public static ApiErrorResponse of(ErrorCode errorCode) {
		return new ApiErrorResponse(errorCode.getStatus(), errorCode.getMessage());
	}

	public static ApiErrorResponse of(HttpStatus httpStatus, String message) {
		return new ApiErrorResponse(httpStatus.value(), message);
	}

}
