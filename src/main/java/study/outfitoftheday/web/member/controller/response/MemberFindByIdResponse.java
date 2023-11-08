package study.outfitoftheday.web.member.controller.response;

import lombok.Builder;
import lombok.Getter;
import study.outfitoftheday.domain.member.entity.Member;

@Getter
public class MemberFindByIdResponse {

	private Long id;
	private String loginId;
	private String nickname;
	private String profileMessage;
	private String profileImageUrl;

	@Builder
	private MemberFindByIdResponse(Long id, String loginId, String nickname, String profileMessage,
		String profileImageUrl) {
		this.id = id;
		this.loginId = loginId;
		this.nickname = nickname;
		this.profileMessage = profileMessage;
		this.profileImageUrl = profileImageUrl;
	}

	public static MemberFindByIdResponse from(Member member) {
		return MemberFindByIdResponse.builder()
			.id(member.getId())
			.loginId(member.getLoginId())
			.nickname(member.getNickname())
			.profileMessage(member.getProfileMessage())
			.profileImageUrl(member.getProfileImageUrl())
			.build();
	}

}
