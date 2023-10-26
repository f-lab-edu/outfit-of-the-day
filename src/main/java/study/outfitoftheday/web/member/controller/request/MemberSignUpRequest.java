package study.outfitoftheday.web.member.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSignUpRequest {

	@NotBlank

	/*
	 * @Email
	 * Jakarta Bean Validation에서 제공하는 annotation이다.
	 * 문자열 유효성 검사를 위해 사용한다.
	 * 해당 값이 Email형식인지 여부를 검사한다.
	 * */
	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String loginId;

	/*
	 * @NotNull
	 * Jakarta Bean Validation에서 제공하는 annotation이다.
	 * 문자열 유효성 검사를 위해 사용한다.
	 * 해당 문자열이 null인지 여부를 검사하다.(빈 공백으로 이루어져 있는 문자열은 무시)
	 * */
	@NotNull
	@Size(min = 2, max = 16, message = "닉네임은 2자리 이상 16자리 이하로 작성해주세요.")
	private String nickname;

	/*
	 * @Size
	 * Jakarta Bean Validation에서 제공하는 annotation이다.
	 * 문자열 유효성 검사를 위해 사용한다.
	 * min field와 max field를 이용해 해당 길이 안에 있는 문자열인지 여부를 검사한다.
	 * */
	@NotNull(message = "비밀번호는 필수 입력값입니다.")
	@Size(min = 8, max = 16, message = "비밀번호 확인은 8자리 이상 16자리 이하로 작성해주세요.")
	// @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;

	@NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
	@Size(min = 8, max = 16, message = "비밀번호 확인은 8자리 이상 16자리 이하로 작성해주세요.")
	private String passwordConfirm;

	@Builder
	private MemberSignUpRequest(String loginId, String nickname, String password, String passwordConfirm) {
		this.loginId = loginId;
		this.nickname = nickname;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}
}
