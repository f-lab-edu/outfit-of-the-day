package study.outfitoftheday.domain.post.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bytebuddy.utility.RandomString;

import study.outfitoftheday.domain.auth.service.AuthService;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.repository.MemberRepository;
import study.outfitoftheday.domain.member.service.MemberService;
import study.outfitoftheday.domain.post.entity.Post;
import study.outfitoftheday.domain.post.enumurate.PostStatus;
import study.outfitoftheday.domain.post.exception.InvalidPostException;
import study.outfitoftheday.domain.post.repository.PostRepository;
import study.outfitoftheday.web.auth.controller.request.AuthLoginRequest;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;
import study.outfitoftheday.web.post.controller.request.PostCreateRequest;

@SpringBootTest
class PostServiceTest {
	private static final int TITLE_MAX_LENGTH = 255;
	private static final int SHORT_DESCRIPTION_MAX_LENGTH = 255;
	private static final String LOGIN_ID = "test1234";
	private static final String PASSWORD = "password1234";
	private static final String NICKNAME = "test-nickname";
	private static final String CONTENT = "test-content";
	private static final String SHORT_DESCRIPTION = "test-short-description";
	private static final String TITLE = "test-title";
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@BeforeEach
	void tearDown() {
		postRepository.deleteAllInBatch();
		memberRepository.deleteAllInBatch();
	}
	
	@Test
	@DisplayName("공개 게시글 등록에 성공한다.")
	void create() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		final String maxLengthTitle = RandomString.make(TITLE_MAX_LENGTH);
		final String maxLengthShortDescription = RandomString.make(SHORT_DESCRIPTION_MAX_LENGTH);
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(maxLengthTitle)
			.shortDescription(maxLengthShortDescription)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		// when
		Long createdPostId = postService.create(foundMember, request);
		Post createdPost = postService.findById(createdPostId);
		
		// then
		assertThat(createdPost.getId()).isEqualTo(createdPostId);
		assertThat(createdPost.getContent()).isEqualTo(CONTENT);
		assertThat(createdPost.getShortDescription()).isEqualTo(maxLengthShortDescription);
		assertThat(createdPost.getTitle()).isEqualTo(maxLengthTitle);
		assertThat(createdPost.getPostStatus()).isEqualTo(PostStatus.PUBLIC);
		assertThat(createdPost.getIsDeleted()).isFalse();
		
	}
	
	@Test
	@DisplayName("게시글 등록시 제목의 최대 길이 255자를 넘어서 예외가 발생한다.")
	void create2() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		final String titleExceededMaxLength = RandomString.make(TITLE_MAX_LENGTH + 1);
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(titleExceededMaxLength)
			.shortDescription(SHORT_DESCRIPTION)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		// when & then
		assertThatThrownBy(() -> postService.create(foundMember, request))
			.hasMessage("최대 길이는 255자 입니다.")
			.isInstanceOf(InvalidPostException.class);
	}
	
	@Test
	@DisplayName("게시글 등록 시, 요약글의 최대 길이 255자를 넘어서 예외가 발생한다.")
	void create3() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		final String shortDescriptionExceededMaxLength = RandomString.make(SHORT_DESCRIPTION_MAX_LENGTH + 1);
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(shortDescriptionExceededMaxLength)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		// when & then
		assertThatThrownBy(() -> postService.create(foundMember, request))
			.hasMessage("최대 길이는 255자 입니다.")
			.isInstanceOf(InvalidPostException.class);
	}
	
	@Test
	@DisplayName("게시글 등록 시, 요약글을 작성하지 않아서 예외가 발생한다.")
	void create4() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		final String emptyShortDescription = "";
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(emptyShortDescription)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		// when & then
		assertThatThrownBy(() -> postService.create(foundMember, request))
			.hasMessage("소제목은 필수값 입니다.")
			.isInstanceOf(InvalidPostException.class);
	}
	
	@Test
	@DisplayName("게시글 등록 시, 요약글을 작성하지 않아서 예외가 발생한다.")
	void create5() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		final String emptyShortDescription = "";
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(emptyShortDescription)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		// when & then
		assertThatThrownBy(() -> postService.create(foundMember, request))
			.hasMessage("소제목은 필수값 입니다.")
			.isInstanceOf(InvalidPostException.class);
	}
	
	@Test
	@DisplayName("게시글 등록 시, 본문을 작성하지 않아서 예외가 발생한다.")
	void create6() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		final String emptyContent = "";
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(SHORT_DESCRIPTION)
			.postStatus(PostStatus.PUBLIC)
			.content(emptyContent)
			.build();
		
		// when & then
		assertThatThrownBy(() -> postService.create(foundMember, request))
			.hasMessage("내용을 입력해 주시길 바랍니다.")
			.isInstanceOf(InvalidPostException.class);
	}
	
	private MemberSignUpRequest createMemberSignUpRequest() {
		return MemberSignUpRequest
			.builder()
			.loginId(LOGIN_ID)
			.nickname(NICKNAME)
			.password(PASSWORD)
			.build();
	}
}