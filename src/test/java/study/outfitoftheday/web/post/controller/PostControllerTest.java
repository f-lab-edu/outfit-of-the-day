package study.outfitoftheday.web.post.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static study.outfitoftheday.global.util.UriPrefix.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import net.bytebuddy.utility.RandomString;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.outfitoftheday.domain.auth.service.AuthService;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.post.entity.Post;
import study.outfitoftheday.domain.post.enumurate.PostStatus;
import study.outfitoftheday.domain.post.service.PostService;
import study.outfitoftheday.web.post.controller.request.PostCreateRequest;

@WebMvcTest(controllers = {PostController.class})
@AutoConfigureRestDocs
class PostControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PostService postService;
	
	@MockBean
	private AuthService authService;
	
	@DisplayName("게시글 등록에 성공하여 HTTP 상태 코드 201을 반환한다.")
	@Test
	void create() throws Exception {
		// given
		doReturn(RandomString.make())
			.when(authService)
			.findMemberNicknameInSession();
		
		doReturn(1L).when(postService).create(any(Member.class), any(PostCreateRequest.class));
		
		PostCreateRequest request = PostCreateRequest.builder()
			.title(RandomString.make())
			.shortDescription(RandomString.make())
			.postStatus(PostStatus.PUBLIC)
			.content(RandomString.make())
			.build();
		
		// when & then
		mockMvc.perform(MockMvcRequestBuilders.post(POST_URI_PREFIX)
				.contentType(MediaType.APPLICATION_JSON)
				.session(new MockHttpSession())
				.content(objectMapper.writeValueAsString(request))
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value(201))
			.andExpect(jsonPath("$.status").value("CREATED"))
			.andExpect(jsonPath("$.message").value("CREATED"))
			.andDo(MockMvcRestDocumentation.document("api/posts/create",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("제목"),
					fieldWithPath("shortDescription").type(JsonFieldType.STRING)
						.description("요약글"),
					fieldWithPath("content").type(JsonFieldType.STRING)
						.description("본문"),
					fieldWithPath("postStatus").type(Enum.EnumDesc.class)
						.description("게시글 상태")
				),
				responseFields(
					fieldWithPath("success").type(JsonFieldType.BOOLEAN)
						.description("코드"),
					fieldWithPath("code").type(JsonFieldType.NUMBER)
						.description("코드"),
					fieldWithPath("status").type(JsonFieldType.STRING)
						.description("상태"),
					fieldWithPath("message").type(JsonFieldType.STRING)
						.description("메시지"),
					fieldWithPath("data").type(JsonFieldType.NULL)
						.description("응답 데이터")
				)
			));
	}
	
	@DisplayName("등록한 게시물 1개를 성공적으로 조회한다.")
	@Test
	void findById() throws Exception {
		// given
		doReturn(RandomString.make())
			.when(authService)
			.findMemberNicknameInSession();
		
		Member mockMember = Member.builder()
			.loginId(RandomString.make())
			.password(RandomString.make())
			.nickname(RandomString.make())
			.build();
		
		Post mockPost = Post.builder().title(RandomString.make())
			.shortDescription(RandomString.make())
			.content(RandomString.make())
			.postStatus(PostStatus.PUBLIC)
			.member(mockMember)
			.build();
		
		doReturn(mockPost)
			.when(postService).findById(any(Long.class));
		
		Long postId = 1L;
		// when & then
		mockMvc.perform(MockMvcRequestBuilders.get(POST_URI_PREFIX + "/" + postId)
				.param("postId", postId.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.session(new MockHttpSession())
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"))
			.andDo(MockMvcRestDocumentation.document("api/posts/get",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("success").type(JsonFieldType.BOOLEAN)
						.description("코드"),
					fieldWithPath("code").type(JsonFieldType.NUMBER)
						.description("코드"),
					fieldWithPath("status").type(JsonFieldType.STRING)
						.description("상태"),
					fieldWithPath("message").type(JsonFieldType.STRING)
						.description("메시지"),
					fieldWithPath("data").type(JsonFieldType.OBJECT)
						.description("응답 데이터"),
					fieldWithPath("data.postId").type(JsonFieldType.NUMBER)
						.optional()
						.description("게시글 Id"),
					fieldWithPath("data.title").type(JsonFieldType.STRING)
						.description("제목"),
					fieldWithPath("data.shortDescription").type(JsonFieldType.STRING)
						.description("요약글"),
					fieldWithPath("data.content").type(JsonFieldType.STRING)
						.description("본문"),
					fieldWithPath("data.postStatus").type(JsonFieldType.STRING)
						.description("게시글 상태"),
					fieldWithPath("data.writerNickname").type(JsonFieldType.STRING)
						.description("작성자 닉네임"),
					fieldWithPath("data.writerMemberId").type(JsonFieldType.NUMBER)
						.optional()
						.description("작성자 Id"),
					fieldWithPath("data.writerProfileUrl").type(JsonFieldType.STRING)
						.optional()
						.description("작성자 프로필 url"),
					fieldWithPath("data.writerProfileMessage").type(JsonFieldType.STRING)
						.optional()
						.description("작성자 프로필 메세지")
				)
			));
	}
	
	@DisplayName("게시글 삭제에 성공하여 HTTP 상태코드 200을 반환한다.")
	@Test
	void delete() throws Exception {
		// given
		doReturn(RandomString.make())
			.when(authService)
			.findMemberNicknameInSession();
		
		final Long deletedPostId = 1L;
		doReturn(deletedPostId)
			.when(postService)
			.delete(any(Member.class), any(Long.class));
		
		// when & then
		mockMvc.perform(MockMvcRequestBuilders.delete(POST_URI_PREFIX + "/" + deletedPostId)
				.contentType(MediaType.APPLICATION_JSON)
				.session(new MockHttpSession())
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"))
			.andDo(MockMvcRestDocumentation.document("api/posts/delete",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("success").type(JsonFieldType.BOOLEAN)
						.description("코드"),
					fieldWithPath("code").type(JsonFieldType.NUMBER)
						.description("코드"),
					fieldWithPath("status").type(JsonFieldType.STRING)
						.description("상태"),
					fieldWithPath("message").type(JsonFieldType.STRING)
						.description("메시지"),
					fieldWithPath("data").type(JsonFieldType.NULL)
						.description("응답 데이터")
				)
			));
	}
}