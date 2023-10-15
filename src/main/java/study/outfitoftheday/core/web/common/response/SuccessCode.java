package study.outfitoftheday.core.web.common.response;

import lombok.Getter;

@Getter
public enum SuccessCode {

	SUCCESS_POST(201, "success"),
	SUCCESS_UPDATE(200, "success"),
	SUCCESS_DELETE(200, "success");

	private final int status;
	private final String message;

	SuccessCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
