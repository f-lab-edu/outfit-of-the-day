package study.outfitoftheday.core.web.common.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
	DUPLICATED_MEMBER(400, "중복 가입된 유저입니다."),
	MISMATCH_PASSWORD_IN_SIGN_UP(400, "비밀번호가 일치하지 않습니다.");

	private final int status;
	private final String message;

	ErrorCode(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
}
