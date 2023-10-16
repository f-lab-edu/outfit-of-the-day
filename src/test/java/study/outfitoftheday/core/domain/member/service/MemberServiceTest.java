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

@SpringBootTest
@Transactional
@Rollback
class MemberServiceTest {

	private static final String LOGIN_ID = "test1234";
	private static final String PASSWORD = "password1234";
	private static final String NICKNAME = "test-nickname";
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void signUpTest() throws Exception {

		// when
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);
		Member foundMember = memberRepository.findByLoginId(LOGIN_ID).orElseThrow();

		// then
		assertThat(foundMember.getLoginId()).isEqualTo(LOGIN_ID);
		assertThat(foundMember.getPassword()).isNotEqualTo(PASSWORD);
		assertThat(foundMember.getNickname()).isEqualTo(NICKNAME);
	}

	@Test
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId인 경우")
	void isDuplicatedMemberByLoginIdTest1() throws Exception {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByLoginId(LOGIN_ID);
		// then

		assertThat(isDuplicated).isTrue();

	}

	@Test
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId가 아닌 경우")
	void isDuplicatedMemberByLoginIdTest2() throws Exception {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByLoginId("test12345");

		// then
		assertThat(isDuplicated).isFalse();

	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname인 경우")
	void isDuplicatedMemberByNicknameTest1() throws Exception {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByNickname(NICKNAME);
		// then

		assertThat(isDuplicated).isTrue();

	}

	@Test
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname이 아닌 경우")
	void isDuplicatedMemberByNicknameTest2() throws Exception {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByNickname("hello");
		// then

		assertThat(isDuplicated).isFalse();

	}
}