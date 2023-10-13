package study.outfitoftheday.core.web.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberSignUpRequestDto {

	@NotEmpty
	private String loginId;

	private String password;

	private String nickname;

}
