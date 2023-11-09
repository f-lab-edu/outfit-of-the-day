package study.outfitoftheday.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.enumurate.PostStatus;
import study.outfitoftheday.global.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	private String title;

	private String shortDescription;

	private String content;

	@Enumerated(EnumType.STRING)
	private PostStatus postStatus;

	private Boolean isDeleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_member_id")
	private Member member;

	@Builder
	public Post(Member member, String title, String shortDescription, String content) {
		this.member = member;
		this.title = title;
		this.shortDescription = shortDescription;
		this.content = content;
		this.postStatus = PostStatus.DRAFT;
		this.isDeleted = false;
	}
}
