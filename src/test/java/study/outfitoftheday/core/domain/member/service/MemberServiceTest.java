package study.outfitoftheday.core.domain.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

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
@Transactional
@Rollback
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
	 * @Test
	 * JUnit Framework에서 테스트 메서드를 지정하는데 사용하는 annotation이다.
	 * 테스트 메서드를 식별하고 실행할 때 사용된다.
	 *
	 * */
	@Test
	@DisplayName("회원가입 - 정상")
	void signUpTest() {

		// when
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(LOGIN_ID).orElseThrow();

		// then
		assertThat(foundMember.getLoginId()).isEqualTo(LOGIN_ID);
		assertThat(foundMember.getPassword()).isNotEqualTo(PASSWORD);
		assertThat(foundMember.getNickname()).isEqualTo(NICKNAME);
	}

	/*
	 * @DisplayName
	 * 테스트 메서드 또는 테스트 클래스에 사용되어서 테스트의 이름을 사용자가 지정한 값으로 표시하는 데 사용된다.
	 * */
	@Test
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId인 경우")
	void isDuplicatedMemberByLoginIdTest1() {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByLoginId(LOGIN_ID);

		// then
		assertThat(isDuplicated).isTrue();
	}

	@Test
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId가 아닌 경우")
	void isDuplicatedMemberByLoginIdTest2() {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByLoginId("test12345");

		// then
		assertThat(isDuplicated).isFalse();

	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname인 경우")
	void isDuplicatedMemberByNicknameTest1() {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByNickname(NICKNAME);
		// then

		assertThat(isDuplicated).isTrue();
	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname이 아닌 경우")
	void isDuplicatedMemberByNicknameTest2() {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByNickname("hello");
		// then

		assertThat(isDuplicated).isFalse();

	}
}