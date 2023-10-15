package study.outfitoftheday.core.web.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthLogInRequestDto {

	@NotBlank(message = "이메일 주소는 필수값입니다.")
	private String loginId;

	@NotBlank(message = "비밀번호 입력은 필수값입니다.")
	private String password;

}
