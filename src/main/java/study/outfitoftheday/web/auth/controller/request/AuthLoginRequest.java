package study.outfitoftheday.web.auth.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthLoginRequest {

	/*
	 * @NotBlank
	 * Jakarta Bean Validation에서 제공하는 annotation이다.
	 * 문자열 유효성 검사를 위해 사용한다.
	 * 해당 값이 null이거나 공백 문자로만 이루어져 있는지 검사한다.
	 * */

	@NotBlank(message = "이메일 주소는 필수값입니다.")
	private String loginId;

	@NotBlank(message = "비밀번호 입력은 필수값입니다.")
	private String password;

	@Builder
	private AuthLoginRequest(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}
}