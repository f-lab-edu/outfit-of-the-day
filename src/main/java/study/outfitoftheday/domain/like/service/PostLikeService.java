package study.outfitoftheday.domain.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.like.entity.PostLike;
import study.outfitoftheday.domain.like.exception.NotFoundPostLikeException;
import study.outfitoftheday.domain.like.repository.PostLikeQueryRepository;
import study.outfitoftheday.domain.like.repository.PostLikeRepository;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;
import study.outfitoftheday.domain.post.service.PostService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {
	private final PostLikeRepository postLikeRepository;
	private final PostLikeQueryRepository postLikeQueryRepository;
	private final PostService postService;

	@Transactional
	public Long add(Member member, Long postId) {
		Post foundPost = postService.findById(postId);
		PostLike postLike = postLikeQueryRepository.findByMemberAndPostIncludeDeleted(member, foundPost)
			.orElse(null);

		// 좋아요가 존재하지 않는 경우
		if (postLike == null) {
			postLike = PostLike.builder()
				.member(member)
				.post(foundPost)
				.build();
			postLikeRepository.save(postLike);
			return postLike.getId();
		}
		// 좋아요가 존재하는데 논리적 삭제된 경우
		if (postLike.getIsDeleted()) {
			postLike.restore();
			return postLike.getId();
		}

		// 좋아요가 존재하는데 논리적 삭제되지 않은 경우
		return postLike.getId();
	}

	@Transactional
	public Long cancel(Member member, Long postId) {

		Post foundPost = postService.findById(postId);

		PostLike postLike = postLikeQueryRepository.findByMemberAndPostIncludeDeleted(member, foundPost)
			.orElseThrow(() -> new NotFoundPostLikeException("좋아요가 존재하지 않습니다."));

		// 이미 논리적 삭제된 좋아요인 경우
		if (postLike.getIsDeleted()) {
			return postLike.getId();
		}

		// 좋아요가 존재하는데 논리작 삭제되지 않은 경우
		postLike.delete();
		return postLike.getId();
	}
}
