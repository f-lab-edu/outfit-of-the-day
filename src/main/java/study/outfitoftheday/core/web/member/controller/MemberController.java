package study.outfitoftheday.core.web.member.controller;

import static study.outfitoftheday.core.web.member.controller.MemberController.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.common.annotation.LoginMember;
import study.outfitoftheday.common.annotation.RequiredAuth;
import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.service.MemberService;
import study.outfitoftheday.core.web.common.response.ApiResponseWrapper;
import study.outfitoftheday.core.web.common.response.SuccessCode;
import study.outfitoftheday.core.web.member.dto.request.MemberSignUpRequestDto;
import study.outfitoftheday.core.web.member.dto.response.MemberGetByLoginIdResponseDto;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(MEMBER_URI_PREFIX)
public class MemberController {
	static final String MEMBER_URI_PREFIX = "/api/members";
	private final MemberService memberService;

	/*
	 * @GetMapping
	 * 해당 annotation 내부에는 @RequestMapping(method = RequestMethod.GET)이 지정되어 있다.
	 * Spring MVC에서 특정 GET 요청 URL을 처리하도록 매핑할 때 사용한다.
	 * */
	@GetMapping("/{loginId}")
	public ResponseEntity<ApiResponseWrapper<MemberGetByLoginIdResponseDto>> memberGetByLoginId(
		@PathVariable String loginId
	) {

		Member foundMember = memberService.findMemberByLoginId(loginId);
		return new ResponseEntity<>(ApiResponseWrapper.of(MemberGetByLoginIdResponseDto.from(foundMember)),
			HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ApiResponseWrapper<String>> memberSignUp(
		@RequestBody @Valid MemberSignUpRequestDto requestDto) {

		memberService.signUp(requestDto.getLoginId(), requestDto.getNickname(), requestDto.getPassword(),
			requestDto.getPasswordConfirm());

		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_POST), HttpStatus.CREATED);
	}

	/*
	 * @DeleteMapping
	 * 해당 annotation 내부에는 @RequestMapping(method = RequestMethod.DELETE)이 지정되어 있다.
	 * Spring MVC에서 특정 DELETE 요청 URL을 처리하도록 매핑할 때 사용한다.
	 * */
	@DeleteMapping
	@RequiredAuth
	public ResponseEntity<ApiResponseWrapper<String>> memberWithdraw(
		/*
		 * @LoginMember
		 * custom anntation이다.
		 * 현재 로그인한 회원의 정보를 가져오고 없다면 exception을 발생시킨다.
		 * */
		@LoginMember Member member
	) {
		memberService.withdrawMember(member);
		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_DELETE), HttpStatus.OK);
	}

}
