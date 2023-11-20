package study.outfitoftheday.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;
import study.outfitoftheday.domain.post.exception.NoAuthorizationToAccessPostException;
import study.outfitoftheday.domain.post.exception.NotFoundPostException;
import study.outfitoftheday.domain.post.repository.PostQueryRepository;
import study.outfitoftheday.domain.post.repository.PostRepository;
import study.outfitoftheday.web.post.controller.request.PostCreateRequest;
import study.outfitoftheday.web.post.controller.request.PostUpdateRequest;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {
	private final PostRepository postRepository;
	private final PostQueryRepository postQueryRepository;

	public Post findById(Long postId) {
		return findById(postId, "게시글이 존재하지 않습니다.");
	}

	@Transactional
	public Long create(Member member, PostCreateRequest request) {
		Post builder = Post
			.builder()
			.title(request.getTitle())
			.shortDescription(request.getShortDescription())
			.content(request.getContent())
			.postStatus(request.getPostStatus())
			.member(member)
			.build();

		return postRepository.save(builder).getId();
	}

	@Transactional
	public Long delete(Member loginMember, Long postId) {
		Post postToDelete = findById(postId, "삭제할 게시글이 존재하지 않습니다.");

		if (!postToDelete.getMember().equals(loginMember)) {
			throw new NoAuthorizationToAccessPostException("해당 게시글을 삭제할 권한이 없습니다.");
		}

		postToDelete.delete();
		return postId;
	}

	@Transactional
	public Long update(Member loginMember, Long postId, PostUpdateRequest request) {

		Post postToUpdate = findById(postId, "변경할 게시글이 존재하지 않습니다.");

		if (!postToUpdate.getMember().equals(loginMember)) {
			throw new NoAuthorizationToAccessPostException("해당 게시글을 변경할 권한이 없습니다.");
		}

		postToUpdate.update(request.getTitle(), request.getShortDescription(), request.getContent(),
			request.getPostStatus());

		return postToUpdate.getId();
	}

	private Post findById(Long postId, String exceptionMessage) {
		return postQueryRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException(exceptionMessage));
	}
}

