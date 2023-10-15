package study.outfitoftheday.core.web.auth.controller;

import static study.outfitoftheday.core.web.auth.controller.AuthController.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.common.annotation.RequiredAuth;
import study.outfitoftheday.core.domain.auth.service.AuthService;
import study.outfitoftheday.core.web.auth.dto.AuthLogInRequestDto;
import study.outfitoftheday.core.web.common.response.ApiResponseWrapper;
import study.outfitoftheday.core.web.common.response.SuccessCode;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AUTH_URI_PREFIX)
public class AuthController {
	static final String AUTH_URI_PREFIX = "/api/auth";
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponseWrapper<String>> memberLogin(
		@RequestBody @Valid AuthLogInRequestDto requestDto
	) {
		authService.login(requestDto.getLoginId(), requestDto.getPassword());
		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_POST), HttpStatus.OK);
	}

	@RequiredAuth
	@PostMapping("/logout")
	public ResponseEntity<ApiResponseWrapper<String>> memberLogout() {
		authService.logout();
		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_POST), HttpStatus.OK);
	}

}
