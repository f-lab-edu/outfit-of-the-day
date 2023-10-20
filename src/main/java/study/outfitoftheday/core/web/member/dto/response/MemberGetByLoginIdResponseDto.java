package study.outfitoftheday.core.web.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import study.outfitoftheday.core.domain.member.entity.Member;

@Getter
public class MemberGetByLoginIdResponseDto {

	private Long id;
	private String loginId;
	private String nickname;
	private String profileMessage;
	private String profileImageUrl;

	@Builder
	private MemberGetByLoginIdResponseDto(Long id, String loginId, String nickname, String profileMessage,
		String profileImageUrl) {
		this.id = id;
		this.loginId = loginId;
		this.nickname = nickname;
		this.profileMessage = profileMessage;
		this.profileImageUrl = profileImageUrl;
	}

	public static MemberGetByLoginIdResponseDto from(Member member) {
		return MemberGetByLoginIdResponseDto.builder()
			.id(member.getId())
			.loginId(member.getLoginId())
			.nickname(member.getNickname())
			.profileMessage(member.getProfileMessage())
			.profileImageUrl(member.getProfileImageUrl())
			.build();
	}

}
