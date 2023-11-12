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
import study.outfitoftheday.domain.post.exception.InvalidPostException;
import study.outfitoftheday.global.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
	private final static int MAX_SHORT_DESCRIPTION_LENGTH = 255;
	private final static int MAX_TITLE_LENGTH = 255;
	
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
	private Post(String title, String shortDescription, String content, Member member, PostStatus postStatus) {
		validateTitle(title);
		validateShortDescription(shortDescription);
		validateContent(content);
		
		this.title = title;
		this.shortDescription = shortDescription;
		this.content = content;
		this.member = member;
		this.postStatus = postStatus;
		this.isDeleted = false;
	}
	
	public void delete() {
		this.isDeleted = true;
	}
	
	private void validateContent(String content) {
		if (content == null || content.isBlank()) {
			throw new InvalidPostException("내용을 입력해 주시길 바랍니다.");
		}
	}
	
	private void validateShortDescription(String shortDescription) {
		if (shortDescription == null || shortDescription.isBlank()) {
			throw new InvalidPostException("소제목은 필수값 입니다.");
		}
		if (shortDescription.length() > MAX_SHORT_DESCRIPTION_LENGTH) {
			throw new InvalidPostException("최대 길이는 255자 입니다.");
		}
	}
	
	private void validateTitle(String title) {
		if (title == null || title.isBlank()) {
			throw new InvalidPostException("제목은 필수값 입니다.");
		}
		
		if (title.length() > MAX_TITLE_LENGTH) {
			throw new InvalidPostException("최대 길이는 255자 입니다.");
		}
		
	}
	
	@Override
	public String toString() {
		return "Post{" +
			"id=" + id +
			", title=" + title +
			", shortDescription='" + shortDescription + '\'' +
			", content='" + content + '\'' +
			", postStatus=" + postStatus +
			'}';
	}
}
