package study.outfitoftheday.domain.auth.service;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import study.outfitoftheday.domain.auth.exception.NotFoundLoginMemberException;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.exception.MismatchPasswordException;
import study.outfitoftheday.domain.member.repository.MemberRepository;
import study.outfitoftheday.domain.member.service.MemberService;
import study.outfitoftheday.web.auth.controller.request.AuthLoginRequest;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;

@SpringBootTest
class AuthServiceTest {

	private final static String LOGIN_ID = "uncle_ra@gmail.com";
	private final static String NICK_NAME = "uncle_ra";
	private final static String PASSWORD = "test1234";

	@Autowired
	private AuthService authService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	/*
	 *
	 * @AfterEach
	 * 각 @Test 어노테이션이 붙은 메서드가 종료될 때마다 수행된다.
	 * */
	@AfterEach()
	void tearDown() {
		memberRepository.deleteAllInBatch();
	}

	@DisplayName("로그인에 성공하여 아무것도 반환하지 않는다.")
	@Test
	void login() {
		// given

		doSignUp();
		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(LOGIN_ID)
			.password(PASSWORD)
			.build();

		// when
		authService.login(loginRequest);

		// then
		assertThat(authService.findMemberNicknameInSession()).isEqualTo(NICK_NAME);
		assertThatNoException();
	}

	@DisplayName("조회가 되지 않는 정보로 로그인을 시도하여 예외를 발생시킨다.")
	@Test
	void login2() {
		// given

		doSignUp();
		final String NOT_EXIST_LOGIN_ID = "test1010";

		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(NOT_EXIST_LOGIN_ID)
			.password(PASSWORD)
			.build();

		// when & then
		Assertions.assertThatThrownBy(() -> authService.login(loginRequest))
			.isInstanceOf(NotFoundLoginMemberException.class)
			.hasMessage("찾는 유저의 정보가 존재하지 않습니다.");
	}

	@DisplayName("조회는 되었지만, 비밀번호가 일치하지 않아 로그인 시도 시 예외를 발생시킨다.")
	@Test
	void login3() {
		// given

		doSignUp();
		final String MISMATCH_PASSWORD = "test1010";

		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(LOGIN_ID)
			.password(MISMATCH_PASSWORD)
			.build();

		// when & then
		assertThatThrownBy(() -> authService.login(loginRequest))
			.isInstanceOf(MismatchPasswordException.class)
			.hasMessage("아이디 혹은 비밀번호가 일치하지 않습니다.");
	}

	@DisplayName("로그아웃에 성공하여 아무것도 반환하지 않는다.")
	@Test
	void logout() {
		// given
		doSignUp();
		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(LOGIN_ID)
			.password(PASSWORD)
			.build();

		authService.login(loginRequest);

		// when
		authService.logout();

		// then
		assertThat(authService.findMemberNicknameInSession()).isEqualTo(null);
		assertThatNoException();
	}

	@DisplayName("로그인한 회원이 Session을 통해 자신의 정보를 조회한다.")
	@Test
	void findLoginMemberInSession() {
		doSignUp();
		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(LOGIN_ID)
			.password(PASSWORD)
			.build();

		authService.login(loginRequest);

		// when
		Member foundMember = authService.findLoginMemberInSession();

		// then
		assertThat(foundMember.getLoginId()).isEqualTo(LOGIN_ID);
		assertThat(foundMember.getPassword()).isNotEqualTo(PASSWORD);
		assertThat(foundMember.getNickname()).isEqualTo(NICK_NAME);
		assertThat(foundMember.getIsDeleted()).isFalse();
	}

	@DisplayName("로그아웃한 회원이 Session을 통해 자신의 정보를 조회시 예외를 발생시킨다.")
	@Test
	void findLoginMemberInSession2() {
		doSignUp();
		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(LOGIN_ID)
			.password(PASSWORD)
			.build();

		authService.login(loginRequest);
		authService.logout();

		// when & then

		assertThatThrownBy(() -> authService.findLoginMemberInSession())
			.isInstanceOf(NotFoundLoginMemberException.class)
			.hasMessage("찾는 유저의 정보가 존재하지 않습니다.");
	}

	@DisplayName("로그인 한 회원이 세션을 통해 자신의 nickname을 조회한다.")
	@Test
	void findMemberNicknameInSession() {

		// given

		doSignUp();
		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(LOGIN_ID)
			.password(PASSWORD)
			.build();

		authService.login(loginRequest);

		// when
		String sessionAuthKey = authService.findMemberNicknameInSession();
		// then
		assertThat(sessionAuthKey).isEqualTo(NICK_NAME);

	}

	@DisplayName("로그아웃한 회원이 Session을 통해 자신의 정보를 조회시 null을 반환한다.")
	@Test
	void findMemberNicknameInSession2() {

		// given
		doSignUp();
		AuthLoginRequest loginRequest = AuthLoginRequest
			.builder()
			.loginId(LOGIN_ID)
			.password(PASSWORD)
			.build();

		authService.login(loginRequest);
		authService.logout();

		// when
		String sessionAuthKey = authService.findMemberNicknameInSession();

		// then
		assertThat(sessionAuthKey).isEqualTo(null);

	}

	private void doSignUp() {
		MemberSignUpRequest signUpRequest = MemberSignUpRequest
			.builder()
			.loginId(LOGIN_ID)
			.nickname(NICK_NAME)
			.password(PASSWORD)
			.build();

		memberService.signUp(signUpRequest);
	}

}