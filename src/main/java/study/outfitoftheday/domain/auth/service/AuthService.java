package study.outfitoftheday.domain.auth.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.auth.exception.NotFoundLoginMemberException;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.exception.MismatchPasswordException;
import study.outfitoftheday.domain.member.repository.MemberQueryRepository;
import study.outfitoftheday.global.config.PasswordEncoder;
import study.outfitoftheday.web.auth.controller.request.AuthLoginRequest;

/*
 * @Service
 * 비즈니스 로직을 수행하는 서비스 계층의 클래스를 나타낸다.
 * 내부적으로 @Component가 포함되어 있어서 컴포넌트 스캔을 통해 빈으로 등록되는 클래스를 지정하는데 사용된다.
 
 * @Transactional
 * Transaction을 시작하고 종료하는데 필요한 모든 작업을 자동으로 처리해준다.
 * 클래스나 메서드, interface등에 지정 가능하다.
 * isolation, readOnly, rollback 등을 설정할 수 있다.
 * */
@Service
@RequiredArgsConstructor
public class AuthService {
	private static final String SESSION_AUTH_KEY = "MEMBER_NICKNAME";
	private final HttpSession httpSession;
	private final PasswordEncoder passwordEncoder;
	private final MemberQueryRepository memberQueryRepository;
	
	public void login(AuthLoginRequest request) {
		Member foundMember = memberQueryRepository
			.findByLoginId(request.getLoginId())
			.orElseThrow(() -> new NotFoundLoginMemberException("찾는 유저의 정보가 존재하지 않습니다."));
		
		if (!passwordEncoder.matches(request.getPassword(), foundMember.getPassword())) {
			throw new MismatchPasswordException("아이디 혹은 비밀번호가 일치하지 않습니다.");
		}
		httpSession.setAttribute(SESSION_AUTH_KEY, foundMember.getNickname());
		
	}
	
	public void logout() {
		httpSession.invalidate();
	}
	
	public Member findLoginMemberInSession() {
		String memberNickname = (String)httpSession.getAttribute(SESSION_AUTH_KEY);
		Member member = memberQueryRepository
			.findByNickname(memberNickname)
			.orElseThrow(() -> new NotFoundLoginMemberException("찾는 유저의 정보가 존재하지 않습니다."));
		
		return member;
	}
	
	public String findMemberNicknameInSession() {
		return (String)httpSession.getAttribute(SESSION_AUTH_KEY);
	}
}

