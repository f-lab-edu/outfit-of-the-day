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
import study.outfitoftheday.common.annotation.RequiredAuth;
import study.outfitoftheday.core.domain.auth.service.AuthService;
import study.outfitoftheday.core.web.auth.dto.AuthLogInRequestDto;
import study.outfitoftheday.core.web.common.response.ApiResponseWrapper;
import study.outfitoftheday.core.web.common.response.SuccessCode;

/*
 * @Controller
 * 웹 애플리케이션에서 사용자의 요청을 처리하는 컨트롤러 클래스를 정의할 때 사용한다.
 * 내부적으로 @Component가 포함되어 있어서 컴포넌트 스캔을 통해 빈으로 등록되는 클래스를 지정하는데 사용된다.

 * @RequestMapping
 * 클래스 레벨에서 사용하면 해당 클래스 내부에 모든 핸들러 메서드에 대한 기본 경로를 지정하는데 사용한다.
 * 메서드 레벨에서 @RequestMapping(value = "/hello", method = RequestMethod.GET)
 * 와 같이 Spring MVC에서 특정 요청 URL을 처리하도록 매핑할 때 사용한다.
 * */
@Controller
@RequiredArgsConstructor
@RequestMapping(AUTH_URI_PREFIX)
public class AuthController {
	static final String AUTH_URI_PREFIX = "/api/auth";
	private final AuthService authService;

	/*
	 * @PostMapping
	 * 해당 annotation 내부에는 @RequestMapping(method = RequestMethod.POST)이 지정되어 있다.
	 * Spring MVC에서 특정 POST 요청 URL을 처리하도록 매핑할 때 사용한다.
	 * */

	@PostMapping("/login")
	public ResponseEntity<ApiResponseWrapper<String>> memberLogin(
		@RequestBody @Valid AuthLogInRequestDto requestDto
	) {
		authService.login(requestDto.getLoginId(), requestDto.getPassword());
		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_POST), HttpStatus.OK);
	}

	/*
	 * @RequiredAuth
	 * custom annotation이다.
	 * 인증/인가가 필요한 method인 경우에 사용한다.
	 * */

	@RequiredAuth
	@PostMapping("/logout")
	public ResponseEntity<ApiResponseWrapper<String>> memberLogout() {
		authService.logout();
		return new ResponseEntity<>(ApiResponseWrapper.of(SuccessCode.SUCCESS_POST), HttpStatus.OK);
	}

}
