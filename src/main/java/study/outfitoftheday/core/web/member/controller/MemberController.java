package study.outfitoftheday.core.web.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.core.domain.member.service.MemberService;
import study.outfitoftheday.core.web.common.response.ApiResponseWrapper;
import study.outfitoftheday.core.web.common.response.SuccessCode;
import study.outfitoftheday.core.web.member.dto.MemberSignUpRequestDto;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/api/test")
	public String test() {
		return "test";
	}

	@PostMapping("/api/sign-up")
	public ResponseEntity<ApiResponseWrapper<String>> memberSignUp(
		@RequestBody @Valid MemberSignUpRequestDto requestDto) {
		log.info("what?!! {}", requestDto);
		memberService.signUp(requestDto.getLoginId(), requestDto.getNickname(), requestDto.getPassword());

		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_POST), HttpStatus.OK);
	}
}
