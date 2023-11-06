package study.outfitoftheday.domain.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.exception.DuplicatedMemberException;
import study.outfitoftheday.domain.member.repository.MemberRepository;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;

/*
 * @SpringBootTest
 * Spring Framework에서 테스트를 수행할 때 사용하는 annotation 중 하나이다.
 * 통합 테스트를 위한 설정을 로드하고 애플리케이션 컨텍스트를 시작하는데 사용된다.
 *
 * @Rollback
 * DB Transaction을 Rollback할지 여부를지정하는 annotation이다.
 * default: true, method나 class에 지정 가능하다.
 * */
@SpringBootTest
class MemberServiceTest {

	private static final String LOGIN_ID = "test1234";
	private static final String PASSWORD = "password1234";
	private static final String NICKNAME = "test-nickname";

	/*
	 * @Autowired
	 * Spring Framework에서 사용하는 DI annotation 중 하나이다.
	 * 자동으로 해당 타입의 빈을 주입하도록 스프링 컨테에너에게 지시한다.
	 * */
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	/*
	 * @BeforeEach
	 * @BeforeEach annotation을 붙인 메서드는 @Test annotation이 붙은 각 메서드 매번 수행된다.
	 * */
	@BeforeEach
	void tearDown() {
		memberRepository.deleteAllInBatch();
	}

	/*
	 * @Test
	 * JUnit Framework에서 테스트 메서드를 지정하는데 사용하는 annotation이다.
	 * 테스트 메서드를 식별하고 실행할 때 사용된다.
	 *
	 * */
	@DisplayName("회원가입을 성공적으로 완료하다.")
	@Test
	void signUp() {

		// given & when
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();

		// then
		assertThat(foundMember.getLoginId()).isEqualTo(LOGIN_ID);
		assertThat(foundMember.getPassword()).isNotEqualTo(PASSWORD);
		assertThat(foundMember.getNickname()).isEqualTo(NICKNAME);
		assertThat(foundMember.getIsDeleted()).isFalse();
		assertThat(foundMember.getCreatedBy()).isEqualTo(NICKNAME);
		assertThat(foundMember.getUpdatedBy()).isEqualTo(NICKNAME);
	}

	@DisplayName("회원가입 시 동일한 닉네임으로 가입한 회원이 있을 경우 예외를 발생시킨다.")
	@Test
	void signUp2() {
		// given
		final String loginId = "abc@gmail.com";
		final String password = "akakqojw123";
		final String duplicatedNickname = NICKNAME;
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));

		// when & then
		assertThatThrownBy(() -> memberService.signUp(createMemberSignUpRequest(loginId, duplicatedNickname, password)))
			.isInstanceOf(DuplicatedMemberException.class)
			.hasMessage("중복 가입된 유저입니다.");

	}
	
	/*
	 * @DisplayName
	 * 테스트 메서드 또는 테스트 클래스에 사용되어서 테스트의 이름을 사용자가 지정한 값으로 표시하는 데 사용된다.
	 * */
	@Test
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId인 경우")
	void isDuplicatedMemberByLoginIdTest1() {
		// given
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));

		// when
		boolean isDuplicated = memberService.isDuplicatedByLoginId(LOGIN_ID);

		// then
		assertThat(isDuplicated).isTrue();
	}

	@Test
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId가 아닌 경우")
	void isDuplicatedMemberByLoginIdTest2() {
		// given
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));

		// when
		boolean isDuplicated = memberService.isDuplicatedByLoginId("test12345");

		// then
		assertThat(isDuplicated).isFalse();

	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname인 경우")
	void isDuplicatedMemberByNicknameTest1() {
		// given
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));

		// when
		boolean isDuplicated = memberService.isDuplicatedByNickname(NICKNAME);
		// then

		assertThat(isDuplicated).isTrue();
	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname이 아닌 경우")
	void isDuplicatedMemberByNicknameTest2() {
		// given
		memberService.signUp(createMemberSignUpRequest(LOGIN_ID, NICKNAME, PASSWORD));

		// when
		boolean isDuplicated = memberService.isDuplicatedByNickname("hello");
		// then

		assertThat(isDuplicated).isFalse();

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