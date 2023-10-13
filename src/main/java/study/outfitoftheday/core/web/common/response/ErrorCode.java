package study.outfitoftheday.core.web.common.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
	DUPLICATE_MEMBER(400, "중복 가입된 유저입니다.");

	private final int status;
	private final String message;

	ErrorCode(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
}
