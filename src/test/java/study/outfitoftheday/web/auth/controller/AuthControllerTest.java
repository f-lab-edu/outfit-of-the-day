package study.outfitoftheday.web.auth.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static study.outfitoftheday.global.util.UriPrefix.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.outfitoftheday.domain.auth.service.AuthService;
import study.outfitoftheday.web.auth.controller.request.AuthLoginRequest;

/*
 * @AutoConfigureMockMvc
 * MockMvc를 사용할 때 자동으로 구성을 활성화 하는 annotation이다.
 *
 * @AutoConfigureRestDocs
 * Spring REST Docs를 사용하여 API 문서를 자동으로 생성하도록  Spring Boot 테스트를 구성하는데 사용하는 annotation이다.
 *
 * */

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureRestDocs
class AuthControllerTest {

	private static final String LOGIN_ID = "test@naver.com";
	private static final String PASSWORD = "test12345";
	private static final String NICKNAME = "test-nickname";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AuthService authService;

	@DisplayName("로그아웃에 성공하여 HTTP 상태 코드 204를 반환한다.")
	@Test
	void logout() throws Exception {

		// given
		doNothing().when(authService).logout();
		doReturn("nickname").when(authService).findMemberNicknameInSession();

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URI_PREFIX + "/logout")
				.contentType(MediaType.APPLICATION_JSON)
				.session(new MockHttpSession())
			)
			.andDo(print())
			.andExpect(status().isNoContent())
			.andDo(MockMvcRestDocumentation.document("api/auth/logout",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())));

	}

	@DisplayName("로그인에 성공하여 HTTP 상태 코드 204를 반환한다.")
	@Test
	void login() throws Exception {

		// given
		HashMap<String, String> input = new HashMap<>();
		input.put("loginId", LOGIN_ID);
		input.put("password", PASSWORD);

		doNothing()
			.when(authService)
			.login(any(AuthLoginRequest.class));

		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URI_PREFIX + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.session(new MockHttpSession())
				.content(objectMapper.writeValueAsString(input))
			)
			.andDo(print())
			.andExpect(status().isNoContent())
			.andDo(MockMvcRestDocumentation.document("api/auth/login",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())));

	}
}