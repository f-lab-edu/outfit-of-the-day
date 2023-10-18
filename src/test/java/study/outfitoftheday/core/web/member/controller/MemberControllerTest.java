package study.outfitoftheday.core.web.member.controller;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static study.outfitoftheday.common.enumerate.UriPrefix.*;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.outfitoftheday.core.domain.member.service.MemberService;
import study.outfitoftheday.core.web.common.response.ErrorCode;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Rollback
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

		mockMvc.perform(MockMvcRequestBuilders.post(MEMBER_URI_PREFIX.getPrefix() + "/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(MockMvcRestDocumentation.document("api/members/sign-up",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@DisplayName("로그인한 자신 조회")
	@Test
	void memberGetMySelfTest() throws Exception {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);
		doLogin();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.get(MEMBER_URI_PREFIX.getPrefix() + "/myself")
				.contentType(MediaType.APPLICATION_JSON)
				.session(httpSession)
			)
			.andDo(MockMvcRestDocumentation.document("api/members/myself",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andDo(print())
			.andExpect(status().isOk());
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
		output.put("isSuccess", false);
		output.put("status", 400);
		output.put("message", ErrorCode.MISMATCH_PASSWORD_IN_SIGN_UP.getMessage());

		mockMvc.perform(MockMvcRequestBuilders.post(MEMBER_URI_PREFIX.getPrefix() + "/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(objectMapper.writeValueAsString(output)));
	}

	@DisplayName("멤버가 회원을 탈퇴할 경우")
	@Test
	void withdrawMemberTest() throws Exception {
		// given
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);
		doLogin();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.delete(MEMBER_URI_PREFIX.getPrefix())
				.contentType(MediaType.APPLICATION_JSON)
				.session(httpSession)
			)
			.andDo(print())
			.andDo(MockMvcRestDocumentation.document("api/members/withdraw-member",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		Assertions.assertThat(memberService.isDeletedMemberByLoginId(LOGIN_ID)).isTrue();

	}

	private void doLogin() throws Exception {

		httpSession = new MockHttpSession();
		HashMap<String, String> input = new HashMap<>();
		input.put("loginId", LOGIN_ID);
		input.put("password", PASSWORD);

		mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URI_PREFIX.getPrefix() + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.session(httpSession)
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(print());
	}

}