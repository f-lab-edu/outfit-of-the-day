package study.outfitoftheday.core.domain.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

@SpringBootTest
@Rollback(value = false)
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void signUpTest() throws Exception {
		// given
		final String loginId = "test1234";
		final String password = "test1234";
		final String nickname = "hello";

		// when
		memberService.signUp(loginId, nickname, password);
		Member foundMember = memberRepository.findByLoginId(loginId).orElseThrow();

		// then
		assertThat(foundMember.getLoginId()).isEqualTo(loginId);
		assertThat(foundMember.getPassword()).isNotEqualTo(password);
		assertThat(foundMember.getNickname()).isEqualTo(nickname);

	}
}