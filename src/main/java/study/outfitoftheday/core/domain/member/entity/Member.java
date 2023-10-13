package study.outfitoftheday.core.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.outfitoftheday.core.domain.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "loginId", "nickname", "profileMessage", "profileImageUrl"})
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String loginId;

	@Column(length = 64)
	private String password;

	private String nickname;

	@Column(nullable = true)
	private String profileMessage;

	@Column(nullable = true)
	private String profileImageUrl;

	private Boolean isDeleted;

	@Builder
	public Member(String loginId, String password, String nickname) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.isDeleted = false;
	}
}
