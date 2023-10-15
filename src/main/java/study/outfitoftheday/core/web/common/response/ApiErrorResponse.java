package study.outfitoftheday.core.web.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public final class ApiErrorResponse {
	private final Boolean isSuccess;
	private final int status;
	private final String message;

	private ApiErrorResponse(Boolean isSuccess, int status, String message) {
		this.isSuccess = isSuccess;
		this.status = status;
		this.message = message;
	}

	public static ApiErrorResponse of(int status, String message) {
		return new ApiErrorResponse(false, status, message);
	}

	public static ApiErrorResponse of(ErrorCode errorCode) {
		return new ApiErrorResponse(false, errorCode.getStatus(), errorCode.getMessage());
	}

	public static ApiErrorResponse of(HttpStatus httpStatus, String message) {
		return new ApiErrorResponse(false, httpStatus.value(), message);
	}

}
