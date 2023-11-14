package study.outfitoftheday.web.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;
import study.outfitoftheday.domain.post.enumurate.PostStatus;

@Getter
public class PostFindByIdResponse {
	private Long postId;
	private String title;
	private String shortDescription;
	private String content;
	private PostStatus postStatus;
	private String writerNickname;
	private Long writerMemberId;
	private String writerProfileUrl;
	private String writerProfileMessage;
	
	@Builder
	private PostFindByIdResponse(Long id, String title, String shortDescription, String content, PostStatus postStatus,
		String writerNickname, Long writerMemberId, String writerProfileUrl, String writerProfileMessage) {
		this.postId = id;
		this.title = title;
		this.shortDescription = shortDescription;
		this.content = content;
		this.postStatus = postStatus;
		this.writerNickname = writerNickname;
		this.writerMemberId = writerMemberId;
		this.writerProfileUrl = writerProfileUrl;
		this.writerProfileMessage = writerProfileMessage;
	}
	
	public static PostFindByIdResponse from(Post post) {
		Member writer = post.getMember();
		return PostFindByIdResponse
			.builder()
			.id(post.getId())
			.title(post.getTitle())
			.shortDescription(post.getShortDescription())
			.content(post.getContent())
			.postStatus(post.getPostStatus())
			.writerMemberId(writer.getId())
			.writerNickname(writer.getNickname())
			.writerProfileMessage(writer.getProfileMessage())
			.writerProfileUrl(writer.getProfileImageUrl())
			.build();
	}
}
