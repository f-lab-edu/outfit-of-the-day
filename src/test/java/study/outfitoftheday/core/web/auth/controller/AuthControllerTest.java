package study.outfitoftheday.core.web.auth.controller;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static study.outfitoftheday.common.enumerate.UriPrefix.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
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

/*
 * @AutoConfigureMockMvc
 * MockMvc를 사용할 때 자동으로 구성을 활성화 하는 annotation이다.
 *
 * @AutoConfigureRestDocs
 * Spring REST Docs를 사용하여 API 문서를 자동으로 생성하도록  Spring Boot 테스트를 구성하는데 사용하는 annotation이다.
 *
 * */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Rollback
@Transactional
class AuthControllerTest {

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

	/*
	 * @BeforeEach
	 * 각각의 테스트 메서드가 실행되기 전에 실행되어야 하는 메서드를 지정할 때 사용한다.
	 * */
	@BeforeEach
	void beforeEach() throws Exception {
		httpSession = new MockHttpSession();
		memberService.signUp(LOGIN_ID, NICKNAME, PASSWORD, PASSWORD);
	}

	@Test
	void logoutTest() throws Exception {
		// given
		doLogin();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URI_PREFIX.getPrefix() + "/logout")
				.contentType(MediaType.APPLICATION_JSON)
				.session(httpSession)
			)
			.andDo(print())
			.andDo(MockMvcRestDocumentation.document("api/auth/logout",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())));
	}

	@Test
	void loginTest() throws Exception {

		// given
		HashMap<String, String> input = new HashMap<>();
		input.put("loginId", LOGIN_ID);
		input.put("password", PASSWORD);

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URI_PREFIX.getPrefix() + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.session(httpSession)
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(print())
			.andDo(MockMvcRestDocumentation.document("api/auth/login",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	private void doLogin() throws Exception {
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