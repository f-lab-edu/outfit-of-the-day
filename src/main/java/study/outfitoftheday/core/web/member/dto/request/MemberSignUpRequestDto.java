package study.outfitoftheday.core.web.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/*
 *
 * https://velog.io/@lovi0714/%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0-Validated-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-%EC%88%9C%EC%84%9C-%EC%A0%95%ED%95%98%EA%B8%B0
 * */
@Getter
public class MemberSignUpRequestDto {

	/*
	 * @NotNull: null만 허용X, "", " " 가능
	 * @NotEmpty: null, "" 허용X, " " 가능
	 * @NotBlank: null, "", " " 허용X
	 * 따라서 @NotBlank 적용
	 * */
	@NotBlank
	/*
	 * Email 형식이 아닌 경우 허용X
	 * */
	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String loginId;

	@NotNull
	@Size(min = 2, max = 16, message = "닉네임은 2자리 이상 16자리 이하로 작성해주세요.")
	private String nickname;

	/*
	 * @Size
	 * length 길이 제한을 둔다.
	 * ~이상 ~이하 기준이다.
	 * */
	@NotNull(message = "비밀번호는 필수 입력값입니다.")
	@Size(min = 8, max = 16, message = "비밀번호 확인은 8자리 이상 16자리 이하로 작성해주세요.")
	// @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;

	@NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
	@Size(min = 8, max = 16, message = "비밀번호 확인은 8자리 이상 16자리 이하로 작성해주세요.")
	private String passwordConfirm;
}
