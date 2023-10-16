package study.outfitoftheday.core.web.common.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
	DUPLICATED_MEMBER(400, "중복 가입된 유저입니다."),
	MISMATCH_PASSWORD_IN_SIGN_UP(400, "비밀번호가 일치하지 않습니다."),
	NO_ACCESS_AUTHORIZATION(400, "접근 권한이 없습니다."),
	NOT_FOUND_MEMBER_EXCEPTION(400, "로그인 정보가 일치하지 않습니다.");

	private final int status;
	private final String message;

	ErrorCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
