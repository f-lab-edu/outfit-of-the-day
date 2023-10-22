package study.outfitoftheday.core.web.common.response;

import lombok.Getter;

@Getter
public final class ApiResponseWrapper<T> {

	private final boolean success;
	private final T data;
	private final int status;
	private final String message;

	private ApiResponseWrapper(T data, int status, String message) {
		this.success = true;
		this.data = data;
		this.status = status;
		this.message = message;
	}

	public static <T> ApiResponseWrapper of(T data, int status, String message) {
		return new ApiResponseWrapper<>(data, status, message);
	}

	public static <T> ApiResponseWrapper of(T data) {
		return new ApiResponseWrapper<>(data, 200, "success");
	}

	public static <T> ApiResponseWrapper of(SuccessCode successCode) {
		return new ApiResponseWrapper<>(null, successCode.getStatus(), successCode.getMessage());
	}
}
