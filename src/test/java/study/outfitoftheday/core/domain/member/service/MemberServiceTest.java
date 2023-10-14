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
@Rollback
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@Transactional
	void signUpTest() throws Exception {
		// given
		final String loginId = "test1234";
		final String password = "test1234";
		final String nickname = "hello";
		final String passwordConfirm = "test1234";

		// when
		memberService.signUp(loginId, nickname, password, passwordConfirm);
		Member foundMember = memberRepository.findByLoginId(loginId).orElseThrow();

		// then
		assertThat(foundMember.getLoginId()).isEqualTo(loginId);
		assertThat(foundMember.getPassword()).isNotEqualTo(password);
		assertThat(foundMember.getNickname()).isEqualTo(nickname);
	}

	@Test
	@Transactional
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId인 경우")
	void isDuplicatedMemberByLoginIdTest1() throws Exception {
		// given
		final String loginId = "test1234";
		final String password = "test1234";
		final String nickname = "hello";
		final String passwordConfirm = "test1234";

		memberService.signUp(loginId, nickname, password, passwordConfirm);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByLoginId(loginId);
		// then

		assertThat(isDuplicated).isTrue();

	}

	@Test
	@Transactional
	@DisplayName("로그인 아이디로 중복된 회원인지 체크 테스트 - 중복된 loginId가 아닌 경우")
	void isDuplicatedMemberByLoginIdTest2() throws Exception {
		// given
		final String loginId = "test1234";
		final String password = "test1234";
		final String nickname = "hello";
		final String passwordConfirm = "test1234";

		memberService.signUp(loginId, nickname, password, passwordConfirm);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByLoginId("test12345");

		// then
		assertThat(isDuplicated).isFalse();

	}

	@Test
	@Transactional
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname인 경우")
	void isDuplicatedMemberByNicknameTest1() throws Exception {
		// given
		final String loginId = "test1234";
		final String password = "test1234";
		final String nickname = "hello";
		final String passwordConfirm = "test1234";

		memberService.signUp(loginId, nickname, password, passwordConfirm);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByNickname(nickname);
		// then

		assertThat(isDuplicated).isTrue();

	}

	@Test
	@Transactional
	@DisplayName("닉네임 중복 체크 테스트 - 중복된 nickname이 아닌 경우")
	void isDuplicatedMemberByNicknameTest2() throws Exception {
		// given
		final String loginId = "test1234";
		final String password = "test1234";
		final String nickname = "hello";
		final String passwordConfirm = "test1234";

		memberService.signUp(loginId, nickname, password, passwordConfirm);

		// when
		boolean isDuplicated = memberService.isDuplicatedMemberByNickname("hello2");
		// then

		assertThat(isDuplicated).isFalse();

	}
}