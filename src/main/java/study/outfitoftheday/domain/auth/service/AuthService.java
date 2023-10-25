package study.outfitoftheday.domain.auth.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import study.outfitoftheday.domain.auth.exception.NotFoundLoginMemberException;
import study.outfitoftheday.domain.member.entity.Member;
import study.outfitoftheday.domain.member.exception.MismatchPasswordInLoginException;
import study.outfitoftheday.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.domain.member.repository.MemberRepository;
import study.outfitoftheday.global.config.PasswordEncoder;

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
	private final MemberRepository memberRepository;

	public void login(String loginId, String plainPassword) {
		Member foundMember = memberRepository.findByLoginIdAndIsDeletedIsFalse(loginId)
			.orElseThrow(NotFoundMemberException::new);

		if (!passwordEncoder.matches(plainPassword, foundMember.getPassword())) {
			throw new MismatchPasswordInLoginException();
		}
		httpSession.setAttribute(SESSION_AUTH_KEY, foundMember.getNickname());

	}

	public void logout() {
		httpSession.invalidate();
	}

	public Member findLoginMemberInSession() {
		String memberNickname = (String)httpSession.getAttribute(SESSION_AUTH_KEY);
		return memberRepository.findByNicknameAndIsDeletedIsFalse(memberNickname)
			.orElseThrow(NotFoundLoginMemberException::new);
	}

	public String findMemberNicknameInSession() {
		return (String)httpSession.getAttribute(SESSION_AUTH_KEY);
	}
}

