package study.outfitoftheday.core.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.outfitoftheday.core.domain.auth.exception.NotFoundLoginMemberException;
import study.outfitoftheday.core.domain.member.entity.Member;
import study.outfitoftheday.core.domain.member.exception.MismatchPasswordInLoginException;
import study.outfitoftheday.core.domain.member.exception.NotFoundMemberException;
import study.outfitoftheday.core.domain.member.repository.MemberRepository;

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
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {
	private static final String MEMBER_ID = "MEMBER_ID";
	private final HttpSession httpSession;
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public void login(String loginId, String plainPassword) {
		Member foundMember = memberRepository.findByLoginId(loginId).orElseThrow(NotFoundMemberException::new);

		if (!passwordEncoder.matches(plainPassword, foundMember.getPassword())) {
			throw new MismatchPasswordInLoginException();
		}
		httpSession.setAttribute(MEMBER_ID, foundMember.getId());

	}

	@Override
	public void logout() {
		httpSession.invalidate();
	}

	@Override
	public Member findLoginMemberInSession() {
		Long memberId = (Long)httpSession.getAttribute(MEMBER_ID);
		return memberRepository.findById(memberId).orElseThrow(NotFoundLoginMemberException::new);
	}

	@Override
	public Long findMemberIdInSession() {
		return (Long)httpSession.getAttribute(MEMBER_ID);
	}
}

