package study.outfitoftheday.web.member.controller;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static study.outfitoftheday.global.util.UriPrefix.*;

import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.outfitoftheday.domain.member.service.MemberService;
import study.outfitoftheday.global.response.ErrorCode;
import study.outfitoftheday.global.util.UriPrefix;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class MemberControllerTest {
	private static final String LOGIN_ID = "test@naver.com";
	private static final String PASSWORD = "test12345";
	private static final String NICKNAME = "test-nickname";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberService memberService;
	private MockHttpSession httpSession;

	@DisplayName("회원가입 한다. - 정상")
	@Test
	void signupTest() throws Exception {
		// given
		HashMap<String, String> input = new HashMap<>();
		input.put("loginId", LOGIN_ID);
		input.put("password", PASSWORD);
		input.put("passwordConfirm", PASSWORD);
		input.put("nickname", NICKNAME);

		mockMvc.perform(MockMvcRequestBuilders.post(UriPrefix.MEMBER_URI_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(MockMvcRestDocumentation.document("api/members/sign-up",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@DisplayName("로그인 ID로 유저 가져오기")
	@Test
	void memberGetByLoginIdTest() throws Exception {
		// given

		MemberSignUpRequest request = MemberSignUpRequest
			.builder()
			.loginId(LOGIN_ID)
			.nickname(NICKNAME)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD)
			.build();

		memberService.signUp(request);
		doLogin();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.get(MEMBER_URI_PREFIX + "/" + LOGIN_ID)
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentation.document("api/members/loginId",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())));
	}

	@DisplayName("회원가입 - password가 불일치한 경우")
	@Test
	void signupTest2() throws Exception {
		// given

		HashMap<String, String> input = new HashMap<>();
		input.put("loginId", LOGIN_ID);
		input.put("password", PASSWORD);
		input.put("passwordConfirm", "test1234");
		input.put("nickname", NICKNAME);

		HashMap<String, Object> output = new HashMap<>();
		output.put("success", false);
		output.put("status", 400);
		output.put("message", ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP.getMessage());

		mockMvc.perform(MockMvcRequestBuilders.post(MEMBER_URI_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@DisplayName("멤버가 회원을 탈퇴할 경우")
	@Test
	void withdrawMemberTest() throws Exception {
		// given

		MemberSignUpRequest request = MemberSignUpRequest
			.builder()
			.loginId(LOGIN_ID)
			.nickname(NICKNAME)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD)
			.build();
		memberService.signUp(request);
		doLogin();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.delete(MEMBER_URI_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.session(httpSession)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcRestDocumentation.document("api/members/withdraw-member",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())));

		Assertions.assertThat(memberService.isDeletedMemberByLoginId(LOGIN_ID)).isTrue();

	}

	private void doLogin() throws Exception {

		httpSession = new MockHttpSession();
		HashMap<String, String> input = new HashMap<>();
		input.put("loginId", LOGIN_ID);
		input.put("password", PASSWORD);

		mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URI_PREFIX + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.session(httpSession)
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(print());
	}

}