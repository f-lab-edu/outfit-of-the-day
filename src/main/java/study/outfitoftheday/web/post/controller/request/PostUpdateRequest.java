package study.outfitoftheday.web.post.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.outfitoftheday.domain.post.enumurate.PostStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostUpdateRequest {
	@NotBlank(message = "postId는 필수값입니다.")
	private Long postId;
	
	@NotBlank(message = "제목은 필수값입니다.")
	private String title;
	
	@NotBlank(message = "요약글은 필수값입니다.")
	private String shortDescription;
	
	@NotBlank(message = "본문은 필수값입니다.")
	private String content;
	
	@NotNull(message = "게시글 상태는 필수값입니다.")
	private PostStatus postStatus;
	
	@Builder
	private PostUpdateRequest(Long postId, String title, String shortDescription, String content, PostStatus postStatus) {
		this.postId = postId;
		this.title = title;
		this.shortDescription = shortDescription;
		this.content = content;
		this.postStatus = postStatus;
	}
}
