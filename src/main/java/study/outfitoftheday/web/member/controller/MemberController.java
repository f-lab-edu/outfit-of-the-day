package study.outfitoftheday.web.member.controller;

import static study.outfitoftheday.global.util.UriPrefix.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.service.MemberService;
import study.outfitoftheday.global.annotation.LoginMember;
import study.outfitoftheday.global.annotation.RequiredAuth;
import study.outfitoftheday.global.response.ApiResponse;
import study.outfitoftheday.web.member.controller.request.MemberSignUpRequest;
import study.outfitoftheday.web.member.controller.response.MemberFindByIdResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(MEMBER_URI_PREFIX)
public class MemberController {
	private final MemberService memberService;

	/*
	 * @GetMapping
	 * 해당 annotation 내부에는 @RequestMapping(method = RequestMethod.GET)이 지정되어 있다.
	 * Spring MVC에서 특정 GET 요청 URL을 처리하도록 매핑할 때 사용한다.
	 * */
	@GetMapping("/{memberId}")
	public ApiResponse<MemberFindByIdResponse> findById(
		@PathVariable Long memberId
	) {

		Member foundMember = memberService.findById(memberId);
		return ApiResponse.ok(MemberFindByIdResponse.from(foundMember));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<Object> signUp(
		@RequestBody @Valid MemberSignUpRequest request) {

		memberService.signUp(request);
		return ApiResponse.created();
	}

	/*
	 * @DeleteMapping
	 * 해당 annotation 내부에는 @RequestMapping(method = RequestMethod.DELETE)이 지정되어 있다.
	 * Spring MVC에서 특정 DELETE 요청 URL을 처리하도록 매핑할 때 사용한다.
	 * */
	@DeleteMapping
	@RequiredAuth
	public ApiResponse<Object> withdraw(
		/*
		 * @LoginMember
		 * custom anntation이다.
		 * 현재 로그인한 회원의 정보를 가져오고 없다면 exception을 발생시킨다.
		 * */
		@LoginMember Member member
	) {
		memberService.withdraw(member);
		return ApiResponse.ok();
	}

}
