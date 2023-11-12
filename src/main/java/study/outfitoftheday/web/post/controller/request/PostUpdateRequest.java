package study.outfitoftheday.web.post.controller.request;

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
	private Long postId;
	private String title;
	
	private String shortDescription;
	
	private String content;
	
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
