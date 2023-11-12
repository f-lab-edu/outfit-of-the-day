package study.outfitoftheday.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;
import study.outfitoftheday.domain.post.exception.NotFoundPostException;
import study.outfitoftheday.domain.post.repository.PostRepository;
import study.outfitoftheday.web.post.controller.request.PostCreateRequest;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {
	private final PostRepository postRepository;
	
	public Post findById(Long postId) {
		return postRepository.findByIdAndIsDeletedIsFalse(postId)
			.orElseThrow(() -> new NotFoundPostException("게시글이 존재하지 않습니다."));
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
		Post postToDelete = postRepository.findByIdAndMemberAndIsDeletedIsFalse(postId, loginMember)
			.orElseThrow(() -> new NotFoundPostException("삭제할 게시글이 존재하지 않거나 권한이 없습니다."));
		
		postToDelete.delete();
		return postId;
	}
}
