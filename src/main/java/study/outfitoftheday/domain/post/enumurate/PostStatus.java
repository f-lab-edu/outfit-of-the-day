package study.outfitoftheday.domain.post.enumurate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
	DRAFT("게시 전"),
	PUBLIC("공개 게시"),
	PRIVATE("비공개 게시");
	
	private final String description;
	
}
