package study.outfitoftheday.core.web.common.response;

import lombok.Getter;

@Getter
public final class ApiErrorResponse {
	private final Boolean isSuccess;
	private final Integer status;
	private final String message;

	private ApiErrorResponse(Boolean isSuccess, Integer status, String message) {
		this.isSuccess = isSuccess;
		this.status = status;
		this.message = message;
	}

	public static ApiErrorResponse of(Integer status, String message) {
		return new ApiErrorResponse(false, status, message);
	}

	public static ApiErrorResponse of(ErrorCode errorCode) {
		return new ApiErrorResponse(false, errorCode.getStatus(), errorCode.getMessage());
	}

}
