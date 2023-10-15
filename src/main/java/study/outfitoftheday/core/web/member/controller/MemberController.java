package study.outfitoftheday.core.web.member.controller;

import static study.outfitoftheday.core.web.member.controller.MemberController.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import study.outfitoftheday.core.web.member.dto.response.MemberGetMySelfResponseDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(MEMBER_URI_PREFIX)
public class MemberController {
	static final String MEMBER_URI_PREFIX = "/api/members";
	private final MemberService memberService;

	@GetMapping("/myself")
	@RequiredAuth
	public ResponseEntity<ApiResponseWrapper<MemberGetMySelfResponseDto>> memberGetMySelf(
		@LoginMember Member member
	) {
		return new ResponseEntity<>(ApiResponseWrapper.of(MemberGetMySelfResponseDto.from(member)), HttpStatus.OK);
	}

	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponseWrapper<String>> memberSignUp(
		@RequestBody @Valid MemberSignUpRequestDto requestDto) {

		memberService.signUp(requestDto.getLoginId(), requestDto.getNickname(), requestDto.getPassword(),
			requestDto.getPasswordConfirm());

		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_POST), HttpStatus.OK);
	}

	@DeleteMapping("/")
	@RequiredAuth
	public ResponseEntity<ApiResponseWrapper<String>> memberWithdraw(
		@LoginMember Member member
	) {
		memberService.withdrawMember(member);
		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_DELETE), HttpStatus.OK);
	}

}
