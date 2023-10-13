package study.outfitoftheday.core.web.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

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

	@NotBlank
	@Size(min = 2, max = 16, message = "닉네임은 2자리 이상 16자리 이하로 작성해주세요.")
	private String nickname;

	@NotBlank(message = "비밀번호는 8자리 이상 16자리 이하로 작성해주세요.")
	/*
	 * @Size
	 * length 길이 제한을 둔다.
	 * ~이상 ~이하 기준이다.
	 * */
	@Size(min = 8, max = 16, message = "비밀번호는 8자리 이상 16자리 이하로 작성해주세요.")

	private String password;

	@NotBlank(message = "비밀번호는 8자리 이상 16자리 이하로 작성해주세요.")
	@Size(min = 8, max = 16, message = "비밀번호는 8자리 이상 16자리 이하로 작성해주세요.")

	private String passwordConfirm;

}
