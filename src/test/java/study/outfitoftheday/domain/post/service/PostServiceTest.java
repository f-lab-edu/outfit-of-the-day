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
import study.outfitoftheday.domain.post.exception.NoAuthorizationToAccessPostException;
import study.outfitoftheday.domain.post.exception.NotFoundPostException;
import study.outfitoftheday.domain.post.repository.PostRepository;
import study.outfitoftheday.web.auth.controller.request.AuthLoginRequest;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;
import study.outfitoftheday.web.post.controller.request.PostCreateRequest;
import study.outfitoftheday.web.post.controller.request.PostUpdateRequest;

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
	@DisplayName("게시글 등록 시, 소제목을 작성하지 않아서 예외가 발생한다.")
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
	@DisplayName("게시글 등록 시, 본문을 작성하지 않아서 예외가 발생한다.")
	void create5() {
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
	
	@DisplayName("등록된 게시글을 성공적으로 삭제한다.")
	@Test
	void delete() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(SHORT_DESCRIPTION)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		Long createdPostId = postService.create(foundMember, request);
		
		// when
		Long deletedPostId = postService.delete(foundMember, createdPostId);
		Post deletedPost = postRepository.findById(deletedPostId).orElseThrow();
		
		// then
		assertThat(deletedPost.getId()).isEqualTo(deletedPostId);
		assertThat(deletedPost.getContent()).isEqualTo(CONTENT);
		assertThat(deletedPost.getShortDescription()).isEqualTo(SHORT_DESCRIPTION);
		assertThat(deletedPost.getTitle()).isEqualTo(TITLE);
		assertThat(deletedPost.getPostStatus()).isEqualTo(PostStatus.PUBLIC);
		assertThat(deletedPost.getIsDeleted()).isTrue();
		
	}
	
	@DisplayName("조회되지 않는 게시글을 삭제 시도 시 예외를 발생시킨다.")
	@Test
	void delete2() {
		// given
		memberService.signUp(createMemberSignUpRequest());
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		
		// when & then
		final Long notExistPostId = 2323L;
		assertThatThrownBy(() -> postService.delete(foundMember, notExistPostId))
			.isInstanceOf(NotFoundPostException.class)
			.hasMessage("삭제할 게시글이 존재하지 않습니다.");
	}
	
	@DisplayName("memberA가 작성한 게시글을 memberB가 삭제하려고 할 경우 예외를 발생시킨다.")
	@Test
	void delete3() {
		// given
		
		final String memberALoginId = LOGIN_ID;
		final String memberAPassword = PASSWORD;
		final String memberANickname = NICKNAME;
		
		memberService.signUp(createMemberSignUpRequest(memberALoginId, memberANickname, memberAPassword));
		authService.login(AuthLoginRequest.builder().loginId(memberALoginId).password(memberAPassword).build());
		Member memberA = memberRepository.findByLoginIdAndIsDeletedIsFalse(memberALoginId).orElseThrow();
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(SHORT_DESCRIPTION)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		final Long memberAPostId = postService.create(memberA, request);
		
		authService.logout(); // memberA logout
		
		final String memberBLoginId = "member_b@naver.com";
		final String memberBPassword = "mdoiqn123";
		final String memberBNickname = "memberB";
		
		memberService.signUp(createMemberSignUpRequest(memberBLoginId, memberBNickname, memberBPassword));
		authService.login(AuthLoginRequest.builder().loginId(memberBLoginId).password(memberBPassword).build());
		Member memberB = memberRepository.findByLoginIdAndIsDeletedIsFalse(memberBLoginId).orElseThrow();
		
		// when & then
		assertThatThrownBy(() -> postService.delete(memberB, memberAPostId))
			.isInstanceOf(NoAuthorizationToAccessPostException.class)
			.hasMessage("해당 게시글을 삭제할 권한이 없습니다.");
	}
	
	@DisplayName("게시글의 제목, 요약글, 본문, 상태를 성공적으로 변경한다.")
	@Test
	void update() {
		// given
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member loginMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		
		PostCreateRequest postCreateRequest = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(SHORT_DESCRIPTION)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		final Long postId = postService.create(loginMember, postCreateRequest);
		
		final String titleToUpdate = RandomString.make(TITLE_MAX_LENGTH);
		final String contentToUpdate = RandomString.make();
		final String shortDescriptionToUpdate = RandomString.make(SHORT_DESCRIPTION_MAX_LENGTH);
		final PostStatus statusToUpdate = PostStatus.PRIVATE;
		
		PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
			.postId(postId)
			.shortDescription(shortDescriptionToUpdate)
			.postStatus(statusToUpdate)
			.title(titleToUpdate)
			.content(contentToUpdate)
			.build();
		
		// when
		postService.update(loginMember, postUpdateRequest);
		Post updatedPost = postService.findById(postId);
		
		// then
		assertThat(updatedPost.getId()).isEqualTo(postId);
		assertThat(updatedPost.getContent()).isEqualTo(contentToUpdate);
		assertThat(updatedPost.getShortDescription()).isEqualTo(shortDescriptionToUpdate);
		assertThat(updatedPost.getTitle()).isEqualTo(titleToUpdate);
		assertThat(updatedPost.getPostStatus()).isEqualTo(statusToUpdate);
		assertThat(updatedPost.getIsDeleted()).isFalse();
	}
	
	@DisplayName("memberA가 작성한 게시글을 memberB가 변경하려고 할 경우 예외를 발생시킨다.")
	@Test
	void update2() {
		// given
		
		final String memberALoginId = LOGIN_ID;
		final String memberAPassword = PASSWORD;
		final String memberANickname = NICKNAME;
		
		memberService.signUp(createMemberSignUpRequest(memberALoginId, memberANickname, memberAPassword));
		authService.login(AuthLoginRequest.builder().loginId(memberALoginId).password(memberAPassword).build());
		Member memberA = memberRepository.findByLoginIdAndIsDeletedIsFalse(memberALoginId).orElseThrow();
		
		PostCreateRequest request = PostCreateRequest
			.builder()
			.title(TITLE)
			.shortDescription(SHORT_DESCRIPTION)
			.postStatus(PostStatus.PUBLIC)
			.content(CONTENT)
			.build();
		
		final Long memberAPostId = postService.create(memberA, request);
		
		authService.logout(); // memberA logout
		
		final String memberBLoginId = "member_b@naver.com";
		final String memberBPassword = "mdoiqn123";
		final String memberBNickname = "memberB";
		
		memberService.signUp(createMemberSignUpRequest(memberBLoginId, memberBNickname, memberBPassword));
		authService.login(AuthLoginRequest.builder().loginId(memberBLoginId).password(memberBPassword).build());
		Member memberB = memberRepository.findByLoginIdAndIsDeletedIsFalse(memberBLoginId).orElseThrow();
		
		final String titleToUpdate = RandomString.make(TITLE_MAX_LENGTH);
		final String contentToUpdate = RandomString.make();
		final String shortDescriptionToUpdate = RandomString.make(SHORT_DESCRIPTION_MAX_LENGTH);
		final PostStatus statusToUpdate = PostStatus.PRIVATE;
		
		PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
			.postId(memberAPostId)
			.shortDescription(shortDescriptionToUpdate)
			.postStatus(statusToUpdate)
			.title(titleToUpdate)
			.content(contentToUpdate)
			.build();
		
		// when & then
		assertThatThrownBy(() -> postService.update(memberB, postUpdateRequest))
			.isInstanceOf(NoAuthorizationToAccessPostException.class)
			.hasMessage("해당 게시글을 변경할 권한이 없습니다.");
	}
	
	@DisplayName("존재하지 않는 게시글을 변경하려고 할 경우 예외를 발생시킨다.")
	@Test
	void update3() {
		// given
		
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));
		authService.login(AuthLoginRequest.builder().loginId(LOGIN_ID).password(PASSWORD).build());
		Member member = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();
		
		final Long notExistPostId = 2023L;
		final String titleToUpdate = RandomString.make(TITLE_MAX_LENGTH);
		final String contentToUpdate = RandomString.make();
		final String shortDescriptionToUpdate = RandomString.make(SHORT_DESCRIPTION_MAX_LENGTH);
		final PostStatus statusToUpdate = PostStatus.PRIVATE;
		
		PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
			.postId(notExistPostId)
			.shortDescription(shortDescriptionToUpdate)
			.postStatus(statusToUpdate)
			.title(titleToUpdate)
			.content(contentToUpdate)
			.build();
		
		// when & then
		assertThatThrownBy(() -> postService.update(member, postUpdateRequest))
			.isInstanceOf(NotFoundPostException.class)
			.hasMessage("변경할 게시글이 존재하지 않습니다.");
	}
	
	private MemberSignUpRequest createMemberSignUpRequest() {
		return MemberSignUpRequest
			.builder()
			.loginId(LOGIN_ID)
			.nickname(NICKNAME)
			.password(PASSWORD)
			.build();
	}
	
	private MemberSignUpRequest createMemberSignUpRequest(String loginId, String nickname, String password) {
		return MemberSignUpRequest
			.builder()
			.loginId(loginId)
			.nickname(nickname)
			.password(password)
			.build();
	}
}