package study.outfitoftheday.web.post.controller;

import static study.outfitoftheday.global.util.UriPrefix.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;
import study.outfitoftheday.domain.post.service.PostService;
import study.outfitoftheday.global.annotation.LoginMember;
import study.outfitoftheday.global.annotation.RequiredAuth;
import study.outfitoftheday.global.response.ApiResponse;
import study.outfitoftheday.web.post.controller.request.PostCreateRequest;
import study.outfitoftheday.web.post.controller.request.PostUpdateRequest;
import study.outfitoftheday.web.post.controller.response.PostFindByIdResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(POST_URI_PREFIX)
@Timed("api.posts")
public class PostController {
	private final PostService postService;
	
	@GetMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	@RequiredAuth
	public ApiResponse<PostFindByIdResponse> findById(
		@PathVariable Long postId
	) {
		Post foundPost = postService.findById(postId);
		return ApiResponse.ok(PostFindByIdResponse.from(foundPost));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@RequiredAuth
	public ApiResponse<Void> create(@LoginMember Member member, @RequestBody @Valid PostCreateRequest request) {
		postService.create(member, request);
		return ApiResponse.created();
	}
	
	@PutMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	@RequiredAuth
	public ApiResponse<Void> update(@LoginMember Member member, @PathVariable Long postId,
		@RequestBody @Valid PostUpdateRequest request) {
		postService.update(member, postId, request);
		return ApiResponse.ok();
	}
	
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	@RequiredAuth
	public ApiResponse<Void> delete(@LoginMember Member member, @PathVariable Long postId) {
		postService.delete(member, postId);
		return ApiResponse.ok();
	}
	
}
